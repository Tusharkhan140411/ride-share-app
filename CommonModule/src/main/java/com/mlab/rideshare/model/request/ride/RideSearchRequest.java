package com.mlab.rideshare.model.request.ride;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RideSearchRequest {

    @JsonProperty("source_latitude")
    private double sourceLatitude;

    @JsonProperty("source_longitude")
    private double sourceLongitude;

    @JsonProperty("destination_latitude")
    private double destinationLatitude;

    @JsonProperty("destination_longitude")
    private double destinationLongitude;
}
