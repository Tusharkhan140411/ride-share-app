package com.mlab.rideshare.service.auth;


import com.mlab.rideshare.model.request.auth.AuthenticationRequest;
import com.mlab.rideshare.model.response.auth.TokenResponse;

public interface AuthService {
    TokenResponse authenticate(AuthenticationRequest request);
}
