package com.mlab.rideshare.service;


import com.mlab.rideshare.entity.DriverAdditionalInfoEntity;
import com.mlab.rideshare.entity.DriverCurrentInfoEntity;
import com.mlab.rideshare.repo.DriverAdditionalInfoRepository;
import com.mlab.rideshare.repo.DriverCurrentInfoRepository;
import org.springframework.stereotype.Service;

@Service
public class DriverAdditionalInfoEntityService extends BaseCRUDService<DriverAdditionalInfoEntity, DriverAdditionalInfoRepository> {

    public DriverAdditionalInfoEntityService(DriverAdditionalInfoRepository repository) {
        super(repository);
    }
}
