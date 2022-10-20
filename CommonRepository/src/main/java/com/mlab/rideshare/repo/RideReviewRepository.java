package com.mlab.rideshare.repo;


import com.mlab.rideshare.entity.RideReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RideReviewRepository extends JpaRepository<RideReviewEntity, Long> {
    Optional<RideReviewEntity> findByRideId(long rideId);
}
