package com.mlab.rideshare.service;


import com.mlab.rideshare.entity.RideInfoEntity;
import com.mlab.rideshare.entity.UserEntity;
import com.mlab.rideshare.repo.RideInfoRepository;
import com.mlab.rideshare.repo.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class RideInfoEntityService extends BaseCRUDService<RideInfoEntity, RideInfoRepository> {

    public RideInfoEntityService(RideInfoRepository repository) {
        super(repository);
    }

    public List<RideInfoEntity> findByCustomerIdAndStatus(long customerId, int status){
        return repository.findByCustomerIdAndStatus(customerId,status);
    }

    public Optional<RideInfoEntity> findByCustomerIdAndTrackingId(long customerId, String trackingId){
        return repository.findByCustomerIdAndTrackingId(customerId, trackingId);
    }

    public Optional<RideInfoEntity> findByTrackingId(String trackingId){
        return repository.findByTrackingId(trackingId);
    }

    public List<RideInfoEntity> findAllByIdIn(Collection<Long> idList){
        return repository.findByIdIn(idList);
    }
}
