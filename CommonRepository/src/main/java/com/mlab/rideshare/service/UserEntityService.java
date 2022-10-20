package com.mlab.rideshare.service;


import com.mlab.rideshare.entity.UserEntity;
import com.mlab.rideshare.repo.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserEntityService extends BaseCRUDService<UserEntity, UserRepository> {

    public UserEntityService(UserRepository repository) {
        super(repository);
    }

    public Optional<UserEntity> findUserByUsername(String userName){
        return repository.findDistinctByUsername(userName);
    }

    public List<UserEntity> getByRoleName(String name){
        return repository.findByRoleName(name);
    }
}
