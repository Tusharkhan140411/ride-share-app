package com.mlab.rideshare.model.request.ride;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RideReviewRequest {
    @NotBlank(message = "validation.constraints.username.NotNull.message")
    @JsonProperty("user_name")
    private String username;

    @NotBlank(message = "validation.constraints.tracking.no.NotNull.message")
    @JsonProperty("tracking_no")
    private String trackingNo;

    private int rating;

    private String review;
}
