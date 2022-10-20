package com.mlab.rideshare.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class DriverInfoDto {
    private String name;
    @JsonProperty("mobile_no")
    private String mobileNo;
}
