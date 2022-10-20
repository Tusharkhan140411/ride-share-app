package com.mlab.rideshare.props;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("application")
@Getter
@Setter
public class ApplicationProperties {
    private String requestIdHeader;
    private int tokenExpiryMinute;
    private String tokenPrefix;
    private String jwtSecret;
    private String authHeaderName;
    private String tokenValidationRegex;
    private boolean seederEnabled;
    private String apiVersion;
    private double nearestDriverDistanceKm;
}
