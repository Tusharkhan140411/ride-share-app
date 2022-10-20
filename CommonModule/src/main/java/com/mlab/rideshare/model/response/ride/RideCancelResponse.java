package com.mlab.rideshare.model.response.ride;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RideCancelResponse {

    private String status;
    @JsonProperty("tracking_no")
    private String trackingNo;
    @JsonProperty("initiated_at")
    private String initiatedAt;
    @JsonProperty("cancelled_at")
    private String cancelledAt;
}
