package com.mlab.rideshare.repo;


import com.mlab.rideshare.entity.DriverCurrentInfoEntity;
import com.mlab.rideshare.entity.RideInfoEntity;
import com.mlab.rideshare.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface RideInfoRepository extends JpaRepository<RideInfoEntity, Long> {
    List<RideInfoEntity> findByCustomerIdAndStatus(long customerId, int status);
    Optional<RideInfoEntity> findByCustomerIdAndTrackingId(long customerId, String trackingId);
    Optional<RideInfoEntity> findByTrackingId(String trackingId);
    List<RideInfoEntity> findByIdIn(Collection<Long> id);
}
