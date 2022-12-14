package com.mlab.rideshare.advice;

import com.mlab.rideshare.exception.AuthException;
import com.mlab.rideshare.exception.BadRequestException;
import com.mlab.rideshare.exception.RecordNotFoundException;
import com.mlab.rideshare.helper.locale.LocaleMessageHelper;
import com.mlab.rideshare.util.response.ResponseBuilder;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.HibernateException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final LocaleMessageHelper messageHelper;

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<?> badRequestExceptionHandler(BadRequestException ex, WebRequest request) {
        this.logException(ex);
        return this.buildResponseEntity(HttpStatus.BAD_REQUEST, ex);
    }

    @ExceptionHandler(RecordNotFoundException.class)
    public ResponseEntity<?> recordNotFoundExceptionHandler(RecordNotFoundException ex, WebRequest request) {
        this.logException(ex);
        return this.buildResponseEntity(HttpStatus.NOT_FOUND, ex);
    }

    @ExceptionHandler(AuthException.class)
    public ResponseEntity<?> authExceptionHandler(AuthException ex, WebRequest request) {
        this.logException(ex);
        return this.buildResponseEntity(HttpStatus.UNAUTHORIZED, ex);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> handleAccessDeniedException(AccessDeniedException ex, WebRequest request) {
        this.logException(ex);
        return this.buildResponseEntity(HttpStatus.UNAUTHORIZED, messageHelper.getLocalMessage("forbidden.message"));
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<?> jwtTokenExpiredExceptionHandler(ExpiredJwtException ex, WebRequest request) {
        this.logException(ex);
        return this.buildResponseEntity(HttpStatus.UNAUTHORIZED, messageHelper.getLocalMessage("token.expired.message"));
    }

    @ExceptionHandler(HibernateException.class)
    public ResponseEntity<?> handleHibernateException(HibernateException ex, WebRequest request) {
        this.logException(ex);
        return this.buildResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, messageHelper.getLocalMessage("db.exception.message"));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> globalExceptionHandler(Exception ex, WebRequest request) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ResponseBuilder
                        .buildResponse(
                                HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage()));
    }

    private ResponseEntity<?> buildResponseEntity(HttpStatus status, Exception ex){
        return buildResponseEntity(status, ex.getLocalizedMessage());
    }

    private ResponseEntity<?> buildResponseEntity(HttpStatus status, String message){
        return ResponseEntity
                .status(status)
                .body(ResponseBuilder.buildResponse(status, message));
    }

    private void logException(Exception e){
        log.error(e.getMessage());
    }
}
