package com.mlab.rideshare.service.driver;

import com.mlab.rideshare.entity.DriverCurrentInfoEntity;
import com.mlab.rideshare.entity.UserEntity;
import com.mlab.rideshare.exception.RecordNotFoundException;
import com.mlab.rideshare.helper.mapper.Mapper;
import com.mlab.rideshare.model.dto.DriverDto;
import com.mlab.rideshare.model.dto.VehicleDto;
import com.mlab.rideshare.model.request.driver.DriverCurrentInfoSyncRequest;
import com.mlab.rideshare.model.request.driver.DriverRegistrationRequest;
import com.mlab.rideshare.service.DriverCurrentInfoEntityService;
import com.mlab.rideshare.service.base.BaseService;
import com.mlab.rideshare.util.DistanceCalculatorUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DriverService extends BaseService {

    private final DriverRegistrationService driverRegistrationService;
    private final DriverCurrentInfoEntityService driverCurrentInfoEntityService;
    private final Mapper mapper;

    public void initiateDriverRegistration(DriverRegistrationRequest driverRegistrationRequest){
        driverRegistrationService.registerDriver(driverRegistrationRequest);
    }

    public List<DriverCurrentInfoEntity> getEligibleDriversAroundLocation(List<DriverCurrentInfoEntity> activeDrivers,
                                                            double sourceLatitude,double sourceLongitude,
                                                            int vehicleType){
        return activeDrivers.stream().filter(
                a -> DistanceCalculatorUtils.distance(sourceLatitude,sourceLongitude,a.getCurrentLatitude(),a.getCurrentLongitude()) != 0
                        && DistanceCalculatorUtils.distance(sourceLatitude,sourceLongitude,a.getCurrentLatitude(),a.getCurrentLongitude()) <= 2
                        && a.getDriverAdditionalInfo().getVehicleInfo().getVehicleType() == vehicleType
        ).collect(Collectors.toList());
    }

    public void syncDriverCurrentInfo(DriverCurrentInfoSyncRequest request){
        UserEntity driverEntity = getUserByUserName(request.getUsername());

        DriverCurrentInfoEntity driverCurrentInfoEntity = driverCurrentInfoEntityService
                .getDriverByUserId(driverEntity.getId())
                .orElseThrow(
                        () -> new RecordNotFoundException(messageHelper.getLocalMessage("driver.current.info.not.found.message"))
                );

        mapper.fillUpdatedDriverCurrInfoValues(driverCurrentInfoEntity,request);

        driverCurrentInfoEntityService.save(driverCurrentInfoEntity);
    }
}
