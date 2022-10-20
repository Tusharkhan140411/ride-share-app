package com.mlab.rideshare.model.request.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Getter
@Setter
public class AuthenticationRequest implements Serializable {
    @NotBlank(message = "validation.constraints.username.NotNull.message")
    @JsonProperty("user_name")
    private String username;

    @NotBlank(message = "validation.constraints.password.NotNull.message")
    @JsonProperty("password")
    private String password;
}
