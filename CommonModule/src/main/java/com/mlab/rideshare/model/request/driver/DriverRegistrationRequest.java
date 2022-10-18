package com.mlab.rideshare.model.request.driver;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mlab.rideshare.model.request.UserCreateRequest;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
public class DriverRegistrationRequest extends UserCreateRequest {

    @NotBlank(message = "validation.constraints.vehicle.reg.plate.NotNull.message")
    @JsonProperty("vehicle_reg_plate_no")
    private String vehicleRegPlateNo;

    @NotBlank(message = "validation.constraints.vehicle.licence.info.NotNull.message")
    @JsonProperty("vehicle_licence_info")
    private String vehicleLicenceInfo;

    @JsonProperty("vehicle_type")
    private int vehicleType;

    @JsonProperty("current_latitude")
    private double currentLatitude;

    @JsonProperty("current_longitude")
    private double currentLongitude;
}
