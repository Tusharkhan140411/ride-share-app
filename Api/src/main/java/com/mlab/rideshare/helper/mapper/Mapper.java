package com.mlab.rideshare.helper.mapper;


import com.mlab.rideshare.entity.*;
import com.mlab.rideshare.enums.UserRoleEnum;
import com.mlab.rideshare.model.auth.CurrentUser;
import com.mlab.rideshare.model.request.UserCreateRequest;
import com.mlab.rideshare.model.request.driver.DriverRegistrationRequest;
import com.mlab.rideshare.service.base.BaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class Mapper extends BaseService {

    private final PasswordEncoder passwordEncoder;

    public static User mapToUserDetails(UserEntity entity) {
        return new CurrentUser(
                entity.getId(),
                entity.getUsername(),
                entity.getPassword(),
                getGrantedAuthorities(Collections.singletonList(entity.getRole()))
        );
    }

    private static Collection<? extends GrantedAuthority> getGrantedAuthorities(Collection<RoleEntity> roles) {
            return roles.stream()
                    .map(roleEntity -> new SimpleGrantedAuthority(UserRoleEnum.getValueByName(roleEntity.getName())))
                    .collect(Collectors.toList());

    }

    public UserEntity mapToUserEntity(UserCreateRequest customerCreateRequest, RoleEntity roleEntity) {
       UserEntity userEntity =  UserEntity.builder()
               .username(customerCreateRequest.getUsername())
               .password(passwordEncoder.encode(customerCreateRequest.getPassword()))
               .email(customerCreateRequest.getEmail())
               .mobile(customerCreateRequest.getMobileNo())
               .fullName(customerCreateRequest.getFullName())
               .build();

       userEntity.setRole(roleEntity);

       return userEntity;
    }

    public DriverAdditionalInfoEntity mapToDriverAdditionalInfoEntity(DriverRegistrationRequest driverRegistrationRequest, long userId,
                                                                      VehicleInfoEntity vehicleInfoEntity, DriverCurrentInfoEntity driverCurrentInfoEntity){
        DriverAdditionalInfoEntity driverAdditionalInfoEntity = DriverAdditionalInfoEntity.builder()
                .userId(userId)
                .vehicleLicenceInfo(driverRegistrationRequest.getVehicleLicenceInfo())
                .vehicleRegPlateNo(driverRegistrationRequest.getVehicleRegPlateNo())
                .driverCurrentInfo(driverCurrentInfoEntity)
                .build();
        driverAdditionalInfoEntity.setVehicleInfo(vehicleInfoEntity);

        return driverAdditionalInfoEntity;
    }

}
