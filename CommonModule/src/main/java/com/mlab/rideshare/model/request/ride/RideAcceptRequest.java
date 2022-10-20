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
public class RideAcceptRequest {
    @NotBlank(message = "validation.constraints.username.NotNull.message")
    @JsonProperty("user_name")
    private String username;

    @NotBlank(message = "validation.constraints.tracking.no.NotNull.message")
    @JsonProperty("tracking_no")
    private String trackingNo;
}
