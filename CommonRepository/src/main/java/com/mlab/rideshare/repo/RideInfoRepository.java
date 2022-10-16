package com.mlab.rideshare.repo;


import com.mlab.rideshare.entity.RideInfoEntity;
import com.mlab.rideshare.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RideInfoRepository extends JpaRepository<RideInfoEntity, Long> {

}
