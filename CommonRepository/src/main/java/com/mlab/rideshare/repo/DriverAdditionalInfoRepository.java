package com.mlab.rideshare.repo;


import com.mlab.rideshare.entity.DriverAdditionalInfoEntity;
import com.mlab.rideshare.entity.DriverCurrentInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DriverAdditionalInfoRepository extends JpaRepository<DriverAdditionalInfoEntity, Long> {

}
