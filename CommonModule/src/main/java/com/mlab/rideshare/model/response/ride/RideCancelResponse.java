package com.mlab.rideshare.model.response.ride;

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
public class RideCancelResponse {

    private String status;
    @JsonProperty("tracking_no")
    private String trackingNo;
    @JsonProperty("initiated_at")
    private String initiatedAt;
    @JsonProperty("cancelled_at")
    private String cancelledId;
}
