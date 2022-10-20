package com.mlab.rideshare.helper.mapper;


import com.mlab.rideshare.entity.*;
import com.mlab.rideshare.enums.UserRoleEnum;
import com.mlab.rideshare.model.auth.CurrentUser;
import com.mlab.rideshare.model.dto.DriverDto;
import com.mlab.rideshare.model.dto.EmailDto;
import com.mlab.rideshare.model.dto.VehicleDto;
import com.mlab.rideshare.model.request.user.BaseUserRegRequest;
import com.mlab.rideshare.model.request.driver.DriverCurrentInfoSyncRequest;
import com.mlab.rideshare.model.request.driver.DriverRegistrationRequest;
import com.mlab.rideshare.model.response.ride.RideNotificationResponse;
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

    public UserEntity mapToUserEntity(BaseUserRegRequest customerCreateRequest, RoleEntity roleEntity) {
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

    public DriverAdditionalInfoEntity mapToDriverAdditionalInfoEntity(long userId, VehicleInfoEntity vehicleInfoEntity,
                                                                      DriverCurrentInfoEntity driverCurrentInfoEntity){
        DriverAdditionalInfoEntity driverAdditionalInfoEntity = DriverAdditionalInfoEntity.builder()
                .userId(userId)
                .driverCurrentInfo(driverCurrentInfoEntity)
                .build();
        driverAdditionalInfoEntity.setVehicleInfo(vehicleInfoEntity);

        return driverAdditionalInfoEntity;
    }

    public List<DriverDto> mapToDriverDto(List<DriverCurrentInfoEntity> eligibleDrivers, List<UserEntity> drivers){
        return eligibleDrivers.stream()
                .map(e-> DriverDto.builder()
                        .name(drivers.stream().filter(d->d.getId() == e.getDriverAdditionalInfo().getUserId()).findFirst().get().getFullName())
                        .mobileNo(drivers.stream().filter(d->d.getId() == e.getDriverAdditionalInfo().getUserId()).findFirst().get().getMobile())
                        .id(drivers.stream().filter(d->d.getId() == e.getDriverAdditionalInfo().getUserId()).findFirst().get().getId())
                        .vehicleDto(
                                VehicleDto.builder()
                                        .vehicleType(e.getDriverAdditionalInfo().getVehicleInfo().getVehicleType())
                                        .vehicleTypeName(e.getDriverAdditionalInfo().getVehicleInfo().getVehicleName())
                                        .build()
                        ).build()
                        ).collect(Collectors.toList());
    }

    public void fillUpdatedRideInfoValues(RideInfoEntity entity, long driverId, int status){
        entity.setDriverId(driverId);
        entity.setStatus(status);
    }

    public void fillUpdatedRideInfoValues(RideInfoEntity entity, int status, String dateString){
        entity.setStatus(status);
        entity.setRideStartTime(dateString);
    }

    public void fillUpdatedRideInfoValues(RideInfoEntity entity, int status, String dateString, int paymentStatus){
        entity.setStatus(status);
        entity.setRideEndTime(dateString);
        entity.setPaymentStatus(paymentStatus);
    }

    public void fillUpdatedDriverCurrInfoValues(DriverCurrentInfoEntity entity,DriverCurrentInfoSyncRequest request){
        entity.setOldLatitude(entity.getCurrentLatitude());
        entity.setOldLongitude(entity.getCurrentLongitude());
        entity.setCurrentLatitude(request.getCurrentLatitude());
        entity.setCurrentLongitude(request.getCurrentLongitude());
        entity.setActiveStatus(request.isActive());
    }

    public List<RideNotificationResponse> mapToRideNotificationResponse(List<RideInfoEntity> entities){
        return entities.stream().map(
                e-> RideNotificationResponse.builder()
                        .fare(e.getFare())
                        .trackingId(e.getTrackingId())
                        .sourceLatitude(e.getSourceLatitude())
                        .sourceLongitude(e.getSourceLongitude())
                        .destinationLatitude(e.getDestinationLatitude())
                        .destinationLongitude(e.getDestinationLongitude())
                .build()
        ).collect(Collectors.toList());
    }

    public EmailDto mapToEmailDto(String email, String body){
        return EmailDto.builder()
                .email(email)
                .body(body)
                .build();
    }

}
