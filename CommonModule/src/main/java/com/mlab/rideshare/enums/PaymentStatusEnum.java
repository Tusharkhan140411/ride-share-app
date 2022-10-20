package com.mlab.rideshare.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public enum PaymentStatusEnum {
    INCOMPLETE(0,"Incomplete"),
    COMPLETE(1,"Complete");

    private int id;
    private String value;

    public static String getStatusById(int id){
        return Arrays.stream(PaymentStatusEnum.values())
                .filter(v->v.getId() == id)
                .map(f->String.valueOf(f.getValue()))
                .collect(Collectors.joining());
    }
}
