package com.mlab.rideshare.controller;

import com.mlab.rideshare.model.request.driver.DriverRegistrationRequest;
import com.mlab.rideshare.model.response.ApiResponse;
import com.mlab.rideshare.service.driver.DriverService;
import com.mlab.rideshare.util.response.ResponseBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class DriverController extends BaseController {

    private final DriverService driverService;

    @PostMapping("/drivers/registration")
    public ResponseEntity<ApiResponse> createUser(@RequestBody @Valid DriverRegistrationRequest request,
                                                  BindingResult bindingResult){
        super.throwIfHasError(bindingResult);
        driverService.initiateDriverRegistration(request);
        return ResponseEntity.ok(ResponseBuilder.buildOkResponse());
    }
}
