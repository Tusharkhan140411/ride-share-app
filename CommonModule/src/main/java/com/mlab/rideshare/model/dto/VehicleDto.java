package com.mlab.rideshare.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class VehicleDto {
    @JsonProperty("vehicle_type")
    private int vehicleType;

    @JsonProperty("vehicle_type_name")
    private String vehicleTypeName;

    @JsonProperty("vehicle_reg_plate_no")
    private String vehicleRegPlateNo;

    @JsonProperty("vehicle_licence_info")
    private String vehicleLicenceInfo;
}
