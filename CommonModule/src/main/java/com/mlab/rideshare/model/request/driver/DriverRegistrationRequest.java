package com.mlab.rideshare.model.request.driver;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mlab.rideshare.model.request.user.BaseUserRegRequest;
import lombok.Data;

@Data
public class DriverRegistrationRequest extends BaseUserRegRequest {

    /*@NotBlank(message = "validation.constraints.vehicle.reg.plate.NotNull.message")
    @JsonProperty("vehicle_reg_plate_no")
    private String vehicleRegPlateNo;

    @NotBlank(message = "validation.constraints.vehicle.licence.info.NotNull.message")
    @JsonProperty("vehicle_licence_info")
    private String vehicleLicenceInfo;*/

    @JsonProperty("vehicle_type")
    private int vehicleType;

    @JsonProperty("current_latitude")
    private double currentLatitude;

    @JsonProperty("current_longitude")
    private double currentLongitude;
}
