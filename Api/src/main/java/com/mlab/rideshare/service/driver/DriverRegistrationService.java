package com.mlab.rideshare.service.driver;

import com.mlab.rideshare.entity.*;
import com.mlab.rideshare.enums.UserRoleEnum;
import com.mlab.rideshare.exception.NotUniqueException;
import com.mlab.rideshare.exception.RecordNotFoundException;
import com.mlab.rideshare.helper.mapper.Mapper;
import com.mlab.rideshare.model.request.driver.DriverRegistrationRequest;
import com.mlab.rideshare.service.DriverAdditionalInfoEntityService;
import com.mlab.rideshare.service.RoleEntityService;
import com.mlab.rideshare.service.UserEntityService;
import com.mlab.rideshare.service.VehicleInfoEntityService;
import com.mlab.rideshare.service.base.BaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DriverRegistrationService extends BaseService {

    private final UserEntityService userEntityService;
    private final RoleEntityService roleEntityService;
    private final DriverAdditionalInfoEntityService driverAdditionalInfoEntityService;
    private final VehicleInfoEntityService vehicleInfoEntityService;
    private final Mapper mapper;

    @Transactional
    public void registerDriver(DriverRegistrationRequest driverRegistrationRequest){
        userEntityService.findUserByUsername(driverRegistrationRequest.getUsername())
                .ifPresent(u -> {
                    throw new NotUniqueException(
                            messageHelper.getLocalMessage("user.exists.message"));
                });

        RoleEntity roleEntity = roleEntityService
                .findRoleByName(UserRoleEnum.DRIVER.getName())
                .orElseThrow(
                        () ->
                                new RecordNotFoundException(
                                        messageHelper.getLocalMessage("roles.not.exists.message"))
                );

        VehicleInfoEntity vehicleInfoEntity = vehicleInfoEntityService
                .findVehicleByType(driverRegistrationRequest.getVehicleType())
                .orElseThrow(
                        () ->
                                new RecordNotFoundException(
                                        messageHelper.getLocalMessage("vehicle.type.not.exists.message"))
                );

        UserEntity user = mapper.mapToUserEntity(driverRegistrationRequest, roleEntity);
        userEntityService.save(user);

        DriverCurrentInfoEntity driverCurrentInfoEntity = DriverCurrentInfoEntity.builder()
                .driverId(user.getId())
                .currentLatitude(driverRegistrationRequest.getCurrentLatitude())
                .currentLongitude(driverRegistrationRequest.getCurrentLongitude())
                .oldLatitude(driverRegistrationRequest.getCurrentLatitude())
                .oldLongitude(driverRegistrationRequest.getCurrentLongitude())
                .activeStatus(Boolean.TRUE)
                .build();

        DriverAdditionalInfoEntity driverAdditionalInfoEntity = mapper.mapToDriverAdditionalInfoEntity(driverRegistrationRequest,
                user.getId(),vehicleInfoEntity,driverCurrentInfoEntity);

        driverAdditionalInfoEntityService.save(driverAdditionalInfoEntity);

    }


}
