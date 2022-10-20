package com.mlab.rideshare.service;


import com.mlab.rideshare.entity.DriverCurrentInfoEntity;
import com.mlab.rideshare.entity.RideInfoEntity;
import com.mlab.rideshare.repo.DriverCurrentInfoRepository;
import com.mlab.rideshare.repo.RideInfoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DriverCurrentInfoEntityService extends BaseCRUDService<DriverCurrentInfoEntity, DriverCurrentInfoRepository> {

    public DriverCurrentInfoEntityService(DriverCurrentInfoRepository repository) {
        super(repository);
    }

    public List<DriverCurrentInfoEntity> getActiveDrivers(){
        return repository.findByActiveStatusTrue();
    }

    public Optional<DriverCurrentInfoEntity> getDriverByUserId(long userId){
        return repository.findByDriverId(userId);
    }
}
