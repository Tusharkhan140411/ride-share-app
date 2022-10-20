package com.mlab.rideshare.service.ride;

import com.mlab.rideshare.entity.RideInfoEntity;
import com.mlab.rideshare.entity.RideNotificationEntity;
import com.mlab.rideshare.entity.UserEntity;
import com.mlab.rideshare.enums.PaymentStatusEnum;
import com.mlab.rideshare.enums.RideStatusEnum;
import com.mlab.rideshare.exception.RecordNotFoundException;
import com.mlab.rideshare.helper.mapper.Mapper;
import com.mlab.rideshare.model.request.ride.RideAcceptRequest;
import com.mlab.rideshare.model.request.ride.RideCancelRequest;
import com.mlab.rideshare.model.request.ride.RideUpdateRequest;
import com.mlab.rideshare.model.response.ride.RideNotificationResponse;
import com.mlab.rideshare.service.RideInfoEntityService;
import com.mlab.rideshare.service.base.BaseService;
import com.mlab.rideshare.service.ride.notification.RideNotificationService;
import com.mlab.rideshare.util.DateTimeUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DriverRideService extends BaseService {

    private final RideInfoEntityService rideInfoEntityService;
    private final RideNotificationService rideNotificationService;
    private final Mapper mapper;

    public List<RideNotificationResponse> getRidesNotification(){
        UserEntity driverEntity = getUserByUserName(getCurrentUserName());

        List<RideNotificationEntity> rideNotificationEntities = rideNotificationService
                .getAllActiveNotifications();

        List<RideNotificationEntity> eligibleNotifications = rideNotificationEntities.stream()
                .filter(
                n->n.getNearestDriverList().contains(String.valueOf(driverEntity.getId()))
        ).collect(Collectors.toList());

        if (CollectionUtils.isEmpty(eligibleNotifications))
            throw new RecordNotFoundException(messageHelper.getLocalMessage("ride.notification.not.found.message"));

        Collection<Long> rideIdList = eligibleNotifications.stream().map(
                n-> n.getRideId()
        ).collect(Collectors.toList());

        List<RideInfoEntity> rideInfoEntities = rideInfoEntityService.findAllByIdIn(rideIdList);

        return mapper.mapToRideNotificationResponse(rideInfoEntities);
    }

    @Transactional
    public void acceptRide(RideAcceptRequest request){
        UserEntity driverEntity = getUserByUserName(getCurrentUserName());

        RideInfoEntity rideInfoEntity = rideInfoEntityService
                .findByTrackingId(request.getTrackingNo())
                .orElseThrow(
                        () ->
                                new RecordNotFoundException(
                                        messageHelper.getLocalMessage("ride.not.exists.message")
                                ));

        if (rideInfoEntity.getStatus() != RideStatusEnum.INITIATED.getId())
            throw new RecordNotFoundException(messageHelper.getLocalMessage("ride.not.available.message"));

        RideNotificationEntity rideNotificationEntity = rideNotificationService
                .getNotificationEntityByRideId(rideInfoEntity.getId());

        mapper.fillUpdatedRideInfoValues(rideInfoEntity, driverEntity.getId(), RideStatusEnum.ACCEPTED.getId());
        rideInfoEntityService.save(rideInfoEntity);

        rideNotificationService.closeNotification(rideNotificationEntity);

    }

    @Transactional
    public void cancelRide(RideCancelRequest request){
        UserEntity driverEntity = getUserByUserName(getCurrentUserName());

        RideInfoEntity rideInfoEntity = rideInfoEntityService
                .findByTrackingId(request.getTrackingNo())
                .orElseThrow(
                        () ->
                                new RecordNotFoundException(
                                        messageHelper.getLocalMessage("ride.not.exists.message")
                                ));

        if (rideInfoEntity.getStatus() != RideStatusEnum.ACCEPTED.getId()
                || rideInfoEntity.getDriverId() != driverEntity.getId())
            throw new RecordNotFoundException(messageHelper.getLocalMessage("driver.not.eligible.to.cancel.message"));

        RideNotificationEntity rideNotificationEntity = rideNotificationService
                .getNotificationEntityByRideId(rideInfoEntity.getId());

        rideNotificationService.enableNotification(rideNotificationEntity,rideInfoEntity.getDriverId());

        mapper.fillUpdatedRideInfoValues(rideInfoEntity, 0, RideStatusEnum.INITIATED.getId());

        rideInfoEntityService.save(rideInfoEntity);



    }

    @Transactional
    public void startRide(RideUpdateRequest request){
        UserEntity driverEntity = getUserByUserName(getCurrentUserName());

        RideInfoEntity rideInfoEntity = rideInfoEntityService
                .findByTrackingId(request.getTrackingNo())
                .orElseThrow(
                        () ->
                                new RecordNotFoundException(
                                        messageHelper.getLocalMessage("ride.not.exists.message")
                                ));

        if (rideInfoEntity.getStatus() != RideStatusEnum.ACCEPTED.getId()
                || rideInfoEntity.getDriverId() != driverEntity.getId()){
            throw new RecordNotFoundException(messageHelper.getLocalMessage("driver.not.eligible.to.start.message"));
        }

        mapper.fillUpdatedRideInfoValues(rideInfoEntity,RideStatusEnum.IN_PROGRESS.getId(),
                DateTimeUtils.formatDateToDBFormat(new Date()));

        rideInfoEntityService.save(rideInfoEntity);

    }

    @Transactional
    public void completeRide(RideUpdateRequest request){
        UserEntity driverEntity = getUserByUserName(getCurrentUserName());

        RideInfoEntity rideInfoEntity = rideInfoEntityService
                .findByTrackingId(request.getTrackingNo())
                .orElseThrow(
                        () ->
                                new RecordNotFoundException(
                                        messageHelper.getLocalMessage("ride.not.exists.message")
                                ));

        if (rideInfoEntity.getStatus() != RideStatusEnum.IN_PROGRESS.getId()
                || rideInfoEntity.getDriverId() != driverEntity.getId()){
            throw new RecordNotFoundException(messageHelper.getLocalMessage("driver.not.eligible.to.complete.message"));
        }

        mapper.fillUpdatedRideInfoValues(rideInfoEntity,RideStatusEnum.COMPLETED.getId(),
                DateTimeUtils.formatDateToDBFormat(new Date()), PaymentStatusEnum.COMPLETE.getId());

        rideInfoEntityService.save(rideInfoEntity);

        //send mail to customer
    }
}
