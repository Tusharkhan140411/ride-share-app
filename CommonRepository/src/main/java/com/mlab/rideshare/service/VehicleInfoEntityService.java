package com.mlab.rideshare.service;

import com.mlab.rideshare.entity.VehicleInfoEntity;
import com.mlab.rideshare.repo.VehicleInfoRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VehicleInfoEntityService extends BaseCRUDService<VehicleInfoEntity, VehicleInfoRepository> {

    public VehicleInfoEntityService(VehicleInfoRepository repository) {
        super(repository);
    }

    public Optional<VehicleInfoEntity> findVehicleByType (int type){
        return repository.findByVehicleType(type);
    }
}
