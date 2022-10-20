package com.mlab.rideshare.model.response.ride;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mlab.rideshare.model.dto.DriverDto;
import com.mlab.rideshare.model.dto.DriverInfoDto;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class RideStatusResponse {
    private String status;
    private double fare;
    @JsonProperty("initiated_at")
    private String initiatedAt;
    @JsonProperty("driver_info")
    private DriverInfoDto driverInfoDto;
}
