package com.mlab.rideshare.service;


import com.mlab.rideshare.entity.DriverCurrentInfoEntity;
import com.mlab.rideshare.entity.RideInfoEntity;
import com.mlab.rideshare.repo.DriverCurrentInfoRepository;
import com.mlab.rideshare.repo.RideInfoRepository;
import org.springframework.stereotype.Service;

@Service
public class DriverCurrentInfoEntityService extends BaseCRUDService<DriverCurrentInfoEntity, DriverCurrentInfoRepository> {

    public DriverCurrentInfoEntityService(DriverCurrentInfoRepository repository) {
        super(repository);
    }
}
