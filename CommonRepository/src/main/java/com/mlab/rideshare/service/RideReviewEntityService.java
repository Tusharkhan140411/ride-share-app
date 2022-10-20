package com.mlab.rideshare.service;


import com.mlab.rideshare.entity.RideReviewEntity;
import com.mlab.rideshare.repo.RideReviewRepository;
import org.springframework.stereotype.Service;

@Service
public class RideReviewEntityService extends BaseCRUDService<RideReviewEntity, RideReviewRepository> {

    public RideReviewEntityService(RideReviewRepository repository) {
        super(repository);
    }
}
