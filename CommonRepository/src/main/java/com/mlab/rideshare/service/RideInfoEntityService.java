package com.mlab.rideshare.service;


import com.mlab.rideshare.entity.RideInfoEntity;
import com.mlab.rideshare.entity.UserEntity;
import com.mlab.rideshare.repo.RideInfoRepository;
import com.mlab.rideshare.repo.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RideInfoEntityService extends BaseCRUDService<RideInfoEntity, RideInfoRepository> {

    public RideInfoEntityService(RideInfoRepository repository) {
        super(repository);
    }
}
