package com.mlab.rideshare.model.response.ride;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RideStatusResponse {

    private String status;
    @JsonProperty("initiated_at")
    private String initiatedAt;
}
