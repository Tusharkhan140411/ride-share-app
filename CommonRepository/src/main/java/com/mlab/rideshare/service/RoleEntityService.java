package com.mlab.rideshare.service;

import com.mlab.rideshare.entity.RoleEntity;
import com.mlab.rideshare.repo.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleEntityService extends BaseCRUDService<RoleEntity, RoleRepository> {

    public RoleEntityService(RoleRepository repository) {
        super(repository);
    }

    public Optional<RoleEntity> findRoleByName (String roleName){
        return repository.findByName(roleName);
    }
}
