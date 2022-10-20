package com.mlab.rideshare.service;


import com.mlab.rideshare.entity.RideReviewEntity;
import com.mlab.rideshare.repo.RideReviewRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RideReviewEntityService extends BaseCRUDService<RideReviewEntity, RideReviewRepository> {

    public RideReviewEntityService(RideReviewRepository repository) {
        super(repository);
    }

    public Optional<RideReviewEntity> getByRideId(long rideId){
        return repository.findByRideId(rideId);
    }
}
