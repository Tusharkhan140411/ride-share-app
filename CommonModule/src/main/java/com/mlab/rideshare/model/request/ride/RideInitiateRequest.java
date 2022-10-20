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
public class RideInitiateRequest extends RideSearchRequest {
    /*@NotBlank(message = "validation.constraints.username.NotNull.message")
    @JsonProperty("user_name")
    private String username;*/

    @JsonProperty("vehicle_type")
    private int vehicleType;
}
