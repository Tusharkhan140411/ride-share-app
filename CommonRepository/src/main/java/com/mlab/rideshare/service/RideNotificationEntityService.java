package com.mlab.rideshare.service;


import com.mlab.rideshare.entity.RideNotificationEntity;
import com.mlab.rideshare.repo.RideNotificationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RideNotificationEntityService extends BaseCRUDService<RideNotificationEntity, RideNotificationRepository> {

    public RideNotificationEntityService(RideNotificationRepository repository) {
        super(repository);
    }

    public Optional<RideNotificationEntity> getByRideId(long rideId){
        return repository.findByRideId(rideId);
    }

    public List<RideNotificationEntity> getAllActiveNotifications(){
        return repository.findByActiveStatusTrue();
    }
}
