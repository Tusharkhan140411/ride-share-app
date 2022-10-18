package com.mlab.rideshare.service.ride;

import com.mlab.rideshare.entity.DriverCurrentInfoEntity;
import com.mlab.rideshare.entity.RideInfoEntity;
import com.mlab.rideshare.entity.UserEntity;
import com.mlab.rideshare.entity.VehicleInfoEntity;
import com.mlab.rideshare.enums.RideStatusEnum;
import com.mlab.rideshare.exception.RecordNotFoundException;
import com.mlab.rideshare.model.dto.DriverDto;
import com.mlab.rideshare.model.request.ride.RideCancelRequest;
import com.mlab.rideshare.model.request.ride.RideInitiateRequest;
import com.mlab.rideshare.model.request.ride.RideReviewRequest;
import com.mlab.rideshare.model.request.ride.RideSearchRequest;
import com.mlab.rideshare.model.response.ride.RideCancelResponse;
import com.mlab.rideshare.model.response.ride.RideInitiateResponse;
import com.mlab.rideshare.model.response.ride.RideSearchResponse;
import com.mlab.rideshare.model.response.ride.RideStatusResponse;
import com.mlab.rideshare.service.DriverCurrentInfoEntityService;
import com.mlab.rideshare.service.RideInfoEntityService;
import com.mlab.rideshare.service.UserEntityService;
import com.mlab.rideshare.service.VehicleInfoEntityService;
import com.mlab.rideshare.service.base.BaseService;
import com.mlab.rideshare.service.driver.DriverService;
import com.mlab.rideshare.service.fare.FairCalculationService;
import com.mlab.rideshare.util.DateTimeUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RideShareService extends BaseService {

    private final VehicleInfoEntityService vehicleInfoEntityService;
    private final FairCalculationService fairCalculationService;
    private final UserEntityService userEntityService;
    private final DriverCurrentInfoEntityService driverCurrentInfoEntityService;
    private final RideInfoEntityService rideInfoEntityService;
    private final DriverService driverService;

    public List<RideSearchResponse> initiateRideSearch(RideSearchRequest request){

        List<VehicleInfoEntity> vehicleInfoList = vehicleInfoEntityService.findAll();

        if(CollectionUtils.isEmpty(vehicleInfoList))
            throw new RecordNotFoundException(messageHelper.getLocalMessage("api.response.not.found.message"));

        return vehicleInfoList.stream()
                .map(item -> RideSearchResponse.builder()
                        .vehicleType(item.getVehicleType())
                        .vehicleTypeName(item.getVehicleName())
                        .fare(fairCalculationService.calculateRideFare(request.getSourceLatitude(), request.getSourceLongitude(),
                                request.getDestinationLatitude(), request.getDestinationLongitude(), item.getFarePerKm()))
                        .build())
                .collect(Collectors.toList());
    }

    public RideInitiateResponse initiateRideRequest(RideInitiateRequest request){

        UserEntity userEntity = userEntityService
                .findUserByUsername(request.getUsername())
                .orElseThrow(
                        () ->
                new RecordNotFoundException(
                        messageHelper.getLocalMessage("user.not.exists.message"))
                );

        List<DriverCurrentInfoEntity> activeDrivers = driverCurrentInfoEntityService.getActiveDrivers();

        if(CollectionUtils.isEmpty(activeDrivers))
            throw new RecordNotFoundException(messageHelper.getLocalMessage("driver.not.active.message"));

        VehicleInfoEntity vehicleInfo = vehicleInfoEntityService
                .findVehicleByType(request.getVehicleType())
                .orElseThrow(
                        () ->
                                new RecordNotFoundException(
                                        messageHelper.getLocalMessage("vehicle.type.not.exists.message"))
                );

        double calculatedFare = fairCalculationService.calculateRideFare(request.getSourceLatitude(), request.getSourceLongitude(),
                request.getDestinationLatitude(), request.getDestinationLongitude(), vehicleInfo.getFarePerKm());

        List<DriverDto> eligibleDrivers = driverService.getEligibleDriversAroundLocation(activeDrivers, request.getSourceLatitude(),
                request.getSourceLongitude(), request.getVehicleType(), userEntity.getFullName(), userEntity.getMobile());

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

        return RideInitiateResponse.builder()
                .trackingNo(rideInfoEntity.getTrackingId())
                .initiatedAt(DateTimeUtils.formatDate(rideInfoEntity.getCreatedAt(),"MM-DD-YYYY"))
                .status(rideInfoEntity.getStatus())
                .drivers(eligibleDrivers)
                .build();
    }

    public RideCancelResponse cancelRide(RideCancelRequest request){
        RideCancelResponse response = new RideCancelResponse();
        return response;
    }

    public RideStatusResponse getRideStatus(String trackingNo){
        return new RideStatusResponse();
    }

    public void reviewRide(RideReviewRequest request){

    }

}
