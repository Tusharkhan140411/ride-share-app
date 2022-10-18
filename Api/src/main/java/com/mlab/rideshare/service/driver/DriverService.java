package com.mlab.rideshare.service.driver;

import com.mlab.rideshare.entity.DriverCurrentInfoEntity;
import com.mlab.rideshare.model.dto.DriverDto;
import com.mlab.rideshare.model.dto.VehicleDto;
import com.mlab.rideshare.model.request.driver.DriverRegistrationRequest;
import com.mlab.rideshare.service.base.BaseService;
import com.mlab.rideshare.util.DistanceCalculatorUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DriverService extends BaseService {

    private final DriverRegistrationService driverRegistrationService;

    public void initiateDriverRegistration(DriverRegistrationRequest driverRegistrationRequest){
        driverRegistrationService.registerDriver(driverRegistrationRequest);
    }

    public List<DriverDto> getEligibleDriversAroundLocation(List<DriverCurrentInfoEntity> activeDrivers,
                                                            double sourceLatitude,double sourceLongitude,
                                                            int vehicleType,String name,String mobileNo){
        return activeDrivers.stream().filter(
                a -> DistanceCalculatorUtils
                .distance(sourceLatitude,sourceLongitude,a.getCurrentLatitude(),a.getCurrentLongitude()) <= 2 //need to get from prop
                && a.getDriverAdditionalInfo().getVehicleInfo().getVehicleType() == vehicleType
        ).map(
                e -> DriverDto.builder()
                        .vehicleDto(
                                VehicleDto.builder()
                                        .vehicleType(e.getDriverAdditionalInfo().getVehicleInfo().getVehicleType())
                                        .vehicleTypeName(e.getDriverAdditionalInfo().getVehicleInfo().getVehicleName())
                                        .vehicleLicenceInfo(e.getDriverAdditionalInfo().getVehicleLicenceInfo())
                                        .vehicleRegPlateNo(e.getDriverAdditionalInfo().getVehicleRegPlateNo())
                                        .build()
                        )
                        .name(name)
                        .mobileNo(mobileNo)
                        .build()
        ).collect(Collectors.toList());
    }
}
