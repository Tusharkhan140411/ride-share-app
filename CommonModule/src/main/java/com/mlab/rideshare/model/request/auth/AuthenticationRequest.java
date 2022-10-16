package com.mlab.rideshare.model.request.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class AuthenticationRequest implements Serializable {
    @JsonProperty("user_name")
    private String username;
    @JsonProperty("password")
    private String password;
}
