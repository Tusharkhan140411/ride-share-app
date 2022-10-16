package com.mlab.rideshare.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateRequest {
    @NotBlank(message = "validation.constraints.username.NotNull.message")
    @JsonProperty("user_name")
    private String username;
    @NotBlank(message = "validation.constraints.user.email.empty.message")
    private String password;
    @NotBlank(message = "validation.constraints.user.fullName.NotNull.message")
    @JsonProperty("full_name")
    private String fullName;
    @Email(message = "validation.constraints.user.email.Invalid.message")
    private String email;
    @NotBlank(message = "validation.constraints.user.mobile.no.Invalid.message")
    @JsonProperty("mobile_no")
    private String mobileNo;
}
