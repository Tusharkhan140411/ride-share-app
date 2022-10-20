package com.mlab.rideshare.service.ride;

import com.mlab.rideshare.entity.*;
import com.mlab.rideshare.enums.RideStatusEnum;
import com.mlab.rideshare.enums.UserRoleEnum;
import com.mlab.rideshare.exception.NotUniqueException;
import com.mlab.rideshare.exception.RecordNotFoundException;
import com.mlab.rideshare.helper.mapper.Mapper;
import com.mlab.rideshare.model.dto.DriverDto;
import com.mlab.rideshare.model.dto.DriverInfoDto;
import com.mlab.rideshare.model.request.ride.*;
import com.mlab.rideshare.model.response.ride.RideCancelResponse;
import com.mlab.rideshare.model.response.ride.RideInitiateResponse;
import com.mlab.rideshare.model.response.ride.RideSearchResponse;
import com.mlab.rideshare.model.response.ride.RideStatusResponse;
import com.mlab.rideshare.service.*;
import com.mlab.rideshare.service.base.BaseService;
import com.mlab.rideshare.service.driver.DriverService;
import com.mlab.rideshare.service.fare.FairCalculationService;
import com.mlab.rideshare.service.ride.notification.RideNotificationService;
import com.mlab.rideshare.util.DateTimeUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerRideService extends BaseService {

    private final VehicleInfoEntityService vehicleInfoEntityService;
    private final FairCalculationService fairCalculationService;
    private final UserEntityService userEntityService;
    private final DriverCurrentInfoEntityService driverCurrentInfoEntityService;
    private final RideInfoEntityService rideInfoEntityService;
    private final DriverService driverService;
    private final RideNotificationService rideNotificationService;
    private final RideReviewEntityService rideReviewEntityService;
    private final Mapper mapper;

    public List<RideSearchResponse> initiateRideSearch(RideSearchRequest request){

        List<VehicleInfoEntity> vehicleInfoEntityList = vehicleInfoEntityService.findAll();

        if(CollectionUtils.isEmpty(vehicleInfoEntityList))
            throw new RecordNotFoundException(messageHelper.getLocalMessage("vehicle.not.found.message"));

        return vehicleInfoEntityList.stream()
                .map(item -> RideSearchResponse.builder()
                        .vehicleType(item.getVehicleType())
                        .vehicleTypeName(item.getVehicleName())
                        .fare(fairCalculationService.calculateRideFare(request.getSourceLatitude(), request.getSourceLongitude(),
                                request.getDestinationLatitude(), request.getDestinationLongitude(), item.getFarePerKm()))
                        .build())
                .collect(Collectors.toList());
    }

    @Transactional
    public RideInitiateResponse initiateRideRequest(RideInitiateRequest request){

        UserEntity userEntity = userEntityService
                .findUserByUsername(request.getUsername())
                .orElseThrow(
                        () ->
                new RecordNotFoundException(
                        messageHelper.getLocalMessage("user.not.exists.message"))
                );

        List<RideInfoEntity> rideInfoEntities = rideInfoEntityService
                .findByCustomerIdAndStatus(userEntity.getId(), RideStatusEnum.INITIATED.getId());

        if (!CollectionUtils.isEmpty(rideInfoEntities))
            throw new NotUniqueException(messageHelper.getLocalMessage("active.ride.already.exist.message"));

        List<UserEntity> userEntities = userEntityService.findAll(); //improvement

        if(CollectionUtils.isEmpty(userEntities))
            throw new RecordNotFoundException(messageHelper.getLocalMessage("users.not.found.message"));

        List<UserEntity> driverEntities = userEntities.stream()
                .filter(
                        u -> u.getRole().getName().equals(UserRoleEnum.DRIVER.getName())
                ).collect(Collectors.toList()
                );

        if(CollectionUtils.isEmpty(driverEntities))
            throw new RecordNotFoundException(messageHelper.getLocalMessage("driver.not.found.message"));

        List<DriverCurrentInfoEntity> activeDriverEntities = driverCurrentInfoEntityService.getActiveDrivers();

        if(CollectionUtils.isEmpty(activeDriverEntities))
            throw new RecordNotFoundException(messageHelper.getLocalMessage("driver.not.active.message"));

        List<DriverCurrentInfoEntity> eligibleDriverEntities = driverService.getEligibleDriversAroundLocation(activeDriverEntities,
                request.getSourceLatitude(), request.getSourceLongitude(), request.getVehicleType());

        if(CollectionUtils.isEmpty(eligibleDriverEntities))
            throw new RecordNotFoundException(messageHelper.getLocalMessage("eligible.driver.not.found.message"));

        List<DriverDto> eligibleDriverDtos = mapper.mapToDriverDto(eligibleDriverEntities,driverEntities);

        VehicleInfoEntity vehicleInfoEntity = vehicleInfoEntityService
                .findVehicleByType(request.getVehicleType())
                .orElseThrow(
                        () ->
                                new RecordNotFoundException(
                                        messageHelper.getLocalMessage("vehicle.type.not.exists.message"))
                );

        double calculatedFare = fairCalculationService.calculateRideFare(request.getSourceLatitude(), request.getSourceLongitude(),
                request.getDestinationLatitude(), request.getDestinationLongitude(), vehicleInfoEntity.getFarePerKm());

        RideInfoEntity rideInfoEntity = RideInfoEntity.builder()
                .customerId(userEntity.getId())
                .sourceLatitude(request.getSourceLatitude())
                .sourceLongitude(request.getSourceLongitude())
                .destinationLatitude(request.getDestinationLatitude())
                .destinationLongitude(request.getDestinationLongitude())
                .fare(calculatedFare)
                .status(RideStatusEnum.INITIATED.getId())
                .trackingId(UUID.randomUUID().toString())
                .build();

        rideInfoEntityService.save(rideInfoEntity);

        rideNotificationService.sendNotificationToNearestDrivers(eligibleDriverDtos, rideInfoEntity.getId());

        return RideInitiateResponse.builder()
                .trackingNo(rideInfoEntity.getTrackingId())
                .initiatedAt(DateTimeUtils.formatDateToApiFormat(rideInfoEntity.getCreatedAt())) //need to fix
                .status(RideStatusEnum.getStatusById(rideInfoEntity.getStatus()))
                .drivers(eligibleDriverDtos)
                .build();
    }

    @Transactional
    public RideCancelResponse cancelRide(RideCancelRequest request){
        UserEntity userEntity = userEntityService
                .findUserByUsername(request.getUsername())
                .orElseThrow(
                        () ->
                                new RecordNotFoundException(
                                        messageHelper.getLocalMessage("user.not.exists.message"))
                );

        RideInfoEntity rideInfoEntity = rideInfoEntityService
                .findByCustomerIdAndTrackingId(userEntity.getId(), request.getTrackingNo())
                .orElseThrow(
                        () ->
                                new RecordNotFoundException(
                                        messageHelper.getLocalMessage("ride.not.exists.message")
                                ));

        if (rideInfoEntity.getCustomerId() != userEntity.getId()
                || (rideInfoEntity.getStatus() != RideStatusEnum.INITIATED.getId()
                        && rideInfoEntity.getStatus() != RideStatusEnum.ACCEPTED.getId())){
            throw new RecordNotFoundException(messageHelper.getLocalMessage("customer.not.eligible.cancel.message"));
        }

        RideNotificationEntity rideNotificationEntity = rideNotificationService
                .getNotificationEntityByRideId(rideInfoEntity.getId());

        rideInfoEntity
                .setStatus(RideStatusEnum.CANCELLED.getId());

        rideInfoEntityService.save(rideInfoEntity);

        rideNotificationService.closeNotification(rideNotificationEntity);

        return RideCancelResponse.builder()
                .status(RideStatusEnum.getStatusById(rideInfoEntity.getStatus()))
                .initiatedAt(DateTimeUtils.formatDateToApiFormat(rideInfoEntity.getCreatedAt())) //need to fix date
                .trackingNo(rideInfoEntity.getTrackingId())
                .cancelledAt(DateTimeUtils.formatDateToApiFormat(new Date())) //need to fix date
                .build();
    }

    public RideStatusResponse getRideStatus(RideStatusRequest request){
        UserEntity userEntity = userEntityService
                .findUserByUsername(request.getUsername())
                .orElseThrow(
                        () ->
                                new RecordNotFoundException(
                                        messageHelper.getLocalMessage("user.not.exists.message"))
                );

        RideInfoEntity rideInfoEntity = rideInfoEntityService
                .findByCustomerIdAndTrackingId(userEntity.getId(), request.getTrackingNo())
                .orElseThrow(
                        () ->
                                new RecordNotFoundException(
                                        messageHelper.getLocalMessage("ride.not.exists.message")
                ));

        UserEntity driverEntity = userEntityService
                .findById(rideInfoEntity.getDriverId())
                .orElse(null);

        DriverInfoDto driverInfoDto = null;
        if (driverEntity != null){
                driverInfoDto = DriverInfoDto.builder()
                    .name(driverEntity.getFullName())
                    .mobileNo(driverEntity.getMobile())
                    .build();
        }

        return RideStatusResponse.builder()
                .status(RideStatusEnum.getStatusById(rideInfoEntity.getStatus()))
                .fare(rideInfoEntity.getFare())
                .initiatedAt(DateTimeUtils.formatDateToApiFormat(rideInfoEntity.getCreatedAt())) //need to fix
                .driverInfoDto(driverInfoDto)
                .build();
    }

    public void reviewRide(RideReviewRequest request){
        UserEntity userEntity = getUserByUserName(request.getUsername());

        RideInfoEntity rideInfoEntity = rideInfoEntityService
                .findByCustomerIdAndTrackingId(userEntity.getId(), request.getTrackingNo())
                .orElseThrow(
                        () ->
                                new RecordNotFoundException(
                                        messageHelper.getLocalMessage("ride.not.exists.message")
                                ));

        if (rideInfoEntity.getStatus() != RideStatusEnum.COMPLETED.getId())
            throw new RecordNotFoundException(messageHelper.getLocalMessage("ride.not.completed.message"));

        RideReviewEntity rideReviewEntity = RideReviewEntity.builder()
                .rideId(rideInfoEntity.getId())
                .rating(request.getRating())
                .review(request.getReview())
                .build();

        rideReviewEntityService.save(rideReviewEntity);
    }

}
