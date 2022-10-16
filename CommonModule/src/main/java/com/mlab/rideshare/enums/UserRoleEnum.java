package com.mlab.rideshare.enums;

import com.mlab.rideshare.exception.RecordNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;

@Getter
@AllArgsConstructor
public enum UserRoleEnum {
    CUSTOMER("CUSTOMER","ROLE_CUSTOMER"),
    DRIVER("DRIVER","ROLE_DRIVER");

    private String name;
    private String value;

    public static String getValueByName(String name){
        UserRoleEnum userRole = Arrays.stream(UserRoleEnum.values())
                .filter(x-> Objects.equals(x.name, name))
                .findFirst()
                .orElseThrow(() -> new RecordNotFoundException("No Role Found for this argument"));

        return userRole.getValue();
    }
}
