package com.mlab.rideshare.model.response.ride;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RideNotificationResponse {

    @JsonProperty("tracking_id")
    private String trackingId;

    private double fare;

    @JsonProperty("source_latitude")
    private double sourceLatitude;

    @JsonProperty("source_longitude")
    private double sourceLongitude;

    @JsonProperty("destination_latitude")
    private double destinationLatitude;

    @JsonProperty("destination_longitude")
    private double destinationLongitude;
}
