package com.mlab.rideshare.controller;

import com.mlab.rideshare.model.request.customer.CustomerRegistrationRequest;
import com.mlab.rideshare.model.response.ApiResponse;
import com.mlab.rideshare.service.customer.CustomerRegistrationService;
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
public class CustomerController extends BaseController {

    private final CustomerRegistrationService customerRegistrationService;

    @PostMapping("/customers/registration")
    public ResponseEntity<ApiResponse> createUser(@RequestBody @Valid CustomerRegistrationRequest request,
                                                  BindingResult bindingResult){
        super.throwIfHasError(bindingResult);
        customerRegistrationService.registerCustomer(request);
        return ResponseEntity.ok(ResponseBuilder.buildOkResponse());
    }
}
