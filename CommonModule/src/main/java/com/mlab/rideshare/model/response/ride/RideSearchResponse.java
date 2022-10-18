package com.mlab.rideshare.model.response.ride;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class RideSearchResponse {

    @JsonProperty("vehicle_type")
    private int vehicleType;
    @JsonProperty("vehicle_type_name")
    private String vehicleTypeName;
    private double fare;
}
