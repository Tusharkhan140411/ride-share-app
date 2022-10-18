package com.mlab.rideshare.repo;


import com.mlab.rideshare.entity.DriverCurrentInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DriverCurrentInfoRepository extends JpaRepository<DriverCurrentInfoEntity, Long> {

    List<DriverCurrentInfoEntity> findByActiveStatusTrue();
}
