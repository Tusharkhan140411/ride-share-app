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
    @NotBlank(message = "validation.constraints.source.latitude.NotNull.message")
    @JsonProperty("source_latitude")
    private double sourceLatitude;

    @NotBlank(message = "validation.constraints.source.longitude.NotNull.message")
    @JsonProperty("source_longitude")
    private double sourceLongitude;

    @NotBlank(message = "validation.constraints.destination.latitude.NotNull.message")
    @JsonProperty("destination_latitude")
    private double destinationLatitude;

    @NotBlank(message = "validation.constraints.destination.longitude.NotNull.message")
    @JsonProperty("destination_longitude")
    private double destinationLongitude;
}
