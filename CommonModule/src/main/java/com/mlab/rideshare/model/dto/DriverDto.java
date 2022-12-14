package com.mlab.rideshare.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class DriverDto {
    @JsonIgnore
    private long id;
    private String name;
//    @JsonProperty("mobile_no")
    @JsonIgnore
    private String mobileNo;

    @JsonProperty("vehicle_info")
    private VehicleDto vehicleDto;
}
