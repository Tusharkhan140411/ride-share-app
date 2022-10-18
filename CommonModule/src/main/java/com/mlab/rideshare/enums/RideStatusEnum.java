package com.mlab.rideshare.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RideStatusEnum {
    INITIATED(1,"Initiated"),
    ACCEPTED(2,"Accepted"),
    IN_PROGRESS(3,"In Progress"),
    CANCELLED(4,"Cancelled"),
    COMPLETED(5,"Completed");

    private int id;
    private String value;
}
