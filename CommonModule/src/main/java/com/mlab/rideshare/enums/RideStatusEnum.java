package com.mlab.rideshare.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.stream.Collectors;

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

    public static String getStatusById(int id){
        return Arrays.stream(RideStatusEnum.values())
                .filter(v->v.getId() == id)
                .map(f->String.valueOf(f.getValue()))
                .collect(Collectors.joining());
    }
}
