package com.mlab.rideshare.util.jwt;

import com.mlab.rideshare.helper.ApplicationContextHolder;
import com.mlab.rideshare.props.ApplicationProperties;
import com.mlab.rideshare.util.DateTimeUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;

import java.util.*;
import java.util.function.Function;

@UtilityClass
public class JWTUtils {

    private final static ApplicationProperties prop;
    static {
        prop = ApplicationContextHolder.getContext().getBean(ApplicationProperties.class);
    }

    /* PUBLIC METHODS */
    public static String generateToken(String username, Collection<? extends GrantedAuthority> authorities){
        Map<String, Object> claims = new HashMap<>();
        claims.put("authorities",authorities);
        return createToken(claims, username);
    }

    public static String extractUserName(String token){
        return extractClaim(token, Claims::getSubject);
    }

    public static Date extractExpiration(String token){
        return extractClaim(token, Claims::getExpiration);
    }

    public static String trimToken(String bearerToken){
        if(StringUtils.startsWith(bearerToken, prop.getTokenPrefix())){
            return StringUtils.replace(bearerToken, prop.getTokenPrefix(), "").trim();
        }
        return bearerToken;
    }

    public static boolean isTokenValid(String token, String username){
        boolean isValidUsername = StringUtils.equals(extractUserName(token), username);
        return ( isValidUsername && !isTokenExpired(token));
    }

    public static boolean isTokenInvalidOrExpired(String token, String username){
        return !isTokenValid(token, username);
    }

    public static boolean isTokenInvalidOrExpired(String token){
        return !isTokenValid(StringUtils.trimToEmpty(token), extractUserName(token));
    }

    public static boolean isTokenFormatValid(String bearerToken){
        return StringUtils.isNotBlank(bearerToken) &&
                bearerToken.matches(prop.getTokenValidationRegex());
    }

    /* PRIVATES */
    private static String createToken(Map<String, Object> claims, String subject){
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + getExpiryMilli()))
                .signWith(SignatureAlgorithm.HS256, prop.getJwtSecret())
                .compact();
    }

    private static int getExpiryMilli(){
        return DateTimeUtils.convertToMilli(prop.getTokenExpiryMinute(), Calendar.MINUTE);
    }

    private static <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        return claimsResolver.apply(extractAllClaims(token));
    }

    public static Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(prop.getJwtSecret())
                .parseClaimsJws(trimToken(token))
                .getBody();
    }

    private static boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }
}
