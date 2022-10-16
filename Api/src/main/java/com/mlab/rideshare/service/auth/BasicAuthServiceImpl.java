package com.mlab.rideshare.service.auth;

import com.mlab.rideshare.model.request.auth.AuthenticationRequest;
import com.mlab.rideshare.model.response.auth.TokenResponse;
import com.mlab.rideshare.util.jwt.JWTUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;


@Service
public class BasicAuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;

    public BasicAuthServiceImpl(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public TokenResponse authenticate(AuthenticationRequest request) {
        try{
            Authentication auth = new UsernamePasswordAuthenticationToken(
                    request.getUsername(),
                    request.getPassword()
            );
            Authentication authentication = authenticationManager.authenticate(auth);
            String token = JWTUtils.generateToken(authentication.getName(),authentication.getAuthorities());
            return new TokenResponse(token);
        } catch (BadCredentialsException e){
            throw new BadCredentialsException("Bad Credential", e);
        }
    }
}
