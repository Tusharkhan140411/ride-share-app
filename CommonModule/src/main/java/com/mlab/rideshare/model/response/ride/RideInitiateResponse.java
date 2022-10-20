package com.mlab.rideshare.model.response.ride;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mlab.rideshare.model.dto.DriverDto;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class RideInitiateResponse {
    @JsonProperty("tracking_no")
    private String trackingNo;
    private String status;
    @JsonProperty("initiated_at")
    private String initiatedAt;
    @JsonProperty("nearest_notified_drivers")
    private List<DriverDto> drivers;
}
