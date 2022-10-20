package com.mlab.rideshare.repo;


import com.mlab.rideshare.entity.RideNotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RideNotificationRepository extends JpaRepository<RideNotificationEntity, Long> {
    Optional<RideNotificationEntity> findByRideId(long rideId);
    List<RideNotificationEntity> findByActiveStatusTrue();
}
