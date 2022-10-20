package com.mlab.rideshare.repo;


import com.mlab.rideshare.entity.RideReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RideReviewRepository extends JpaRepository<RideReviewEntity, Long> {

}
