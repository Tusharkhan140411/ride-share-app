package com.mlab.rideshare.repo;


import com.mlab.rideshare.entity.RoleEntity;
import com.mlab.rideshare.entity.VehicleInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VehicleInfoRepository extends JpaRepository<VehicleInfoEntity, Long> {

    Optional<VehicleInfoEntity> findByVehicleType(int type);
}
