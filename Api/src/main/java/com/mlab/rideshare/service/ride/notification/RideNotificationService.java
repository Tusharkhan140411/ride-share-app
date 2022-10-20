package com.mlab.rideshare.service.ride.notification;

import com.mlab.rideshare.entity.RideNotificationEntity;
import com.mlab.rideshare.exception.RecordNotFoundException;
import com.mlab.rideshare.model.dto.DriverDto;
import com.mlab.rideshare.service.RideNotificationEntityService;
import com.mlab.rideshare.service.base.BaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RideNotificationService extends BaseService {

    private final RideNotificationEntityService rideNotificationEntityService;

    public void sendNotificationToNearestDrivers(List<DriverDto> eligibleDrivers, long rideId){

        String eligibleDriversAsString = eligibleDrivers.stream().map(
                e -> String.valueOf(e.getId())
        ).collect(Collectors.joining(","));

        RideNotificationEntity rideNotificationEntity = RideNotificationEntity.builder()
                .rideId(rideId)
                .nearestDriverList(eligibleDriversAsString)
                .activeStatus(Boolean.TRUE)
                .build();

        rideNotificationEntityService.save(rideNotificationEntity);
    }

    public RideNotificationEntity getNotificationEntityByRideId(long rideId){
        return rideNotificationEntityService.getByRideId(rideId).orElseThrow
                (
                        () -> new RecordNotFoundException(
                        messageHelper.getLocalMessage("ride.notification.not.exists.message"))
        );
    }

    public List<RideNotificationEntity> getAllActiveNotifications(){
        List<RideNotificationEntity> entities = rideNotificationEntityService.getAllActiveNotifications();

        if (CollectionUtils.isEmpty(entities))
            throw new RecordNotFoundException(messageHelper.getLocalMessage("ride.notification.not.found.message"));

        return entities;
    }

    public void closeNotification(RideNotificationEntity entity){
        entity.setActiveStatus(Boolean.FALSE);

        rideNotificationEntityService.save(entity);
    }

    public void enableNotification(RideNotificationEntity entity,long driverId){
        String nearestDrivers = entity.getNearestDriverList()
                .replaceAll(String.valueOf(driverId),"");

        entity.setActiveStatus(Boolean.TRUE);
        entity.setNearestDriverList(nearestDrivers);

        rideNotificationEntityService.save(entity);
    }

}
