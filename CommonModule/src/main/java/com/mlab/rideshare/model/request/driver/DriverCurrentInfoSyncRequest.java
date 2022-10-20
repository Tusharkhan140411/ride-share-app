package com.mlab.rideshare.model.request.driver;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class DriverCurrentInfoSyncRequest {

    @NotBlank(message = "validation.constraints.username.NotNull.message")
    @JsonProperty("user_name")
    private String username;

    @JsonProperty("is_active")
    private boolean isActive;

    @JsonProperty("current_latitude")
    private double currentLatitude;

    @JsonProperty("current_longitude")
    private double currentLongitude;
}
