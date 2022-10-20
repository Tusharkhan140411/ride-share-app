package com.mlab.rideshare.mapper;

import com.mlab.rideshare.entity.RideInfoEntity;
import com.mlab.rideshare.entity.RoleEntity;
import com.mlab.rideshare.entity.UserEntity;
import com.mlab.rideshare.model.request.ride.RideReviewRequest;
import lombok.experimental.UtilityClass;

@UtilityClass
public class TestObjectSupplier {

    public UserEntity getUserEntity(){
        return UserEntity.builder()
                .role(RoleEntity.builder().name("TEST").id(1).build())
                .fullName("Test test")
                .mobile("01987654780")
                .email("test@gmail.com")
                .id(1)
                .username("test")
                .build();
    }

    public RideInfoEntity getRideInfoEntity(){
        return RideInfoEntity.builder()
                .status(5)
                .fare(25)
                .trackingId("6768-676767-bvvhhf")
                .customerId(1)
                .sourceLatitude(20.23)
                .build();
    }

    public RideReviewRequest getRevReq(){
        return new RideReviewRequest("6768-676767-bvvhhf",6,"Nice!");
    }
}
