package com.mlab.rideshare.repo;


import com.mlab.rideshare.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findDistinctByUsername(String username);
    List<UserEntity> findByRoleName(String name);
}
