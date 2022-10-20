package com.mlab.rideshare.controller;

import com.mlab.rideshare.model.request.ride.*;
import com.mlab.rideshare.model.response.ApiResponse;
import com.mlab.rideshare.model.response.ride.RideCancelResponse;
import com.mlab.rideshare.model.response.ride.RideInitiateResponse;
import com.mlab.rideshare.model.response.ride.RideSearchResponse;
import com.mlab.rideshare.model.response.ride.RideStatusResponse;
import com.mlab.rideshare.service.ride.CustomerRideService;
import com.mlab.rideshare.util.response.ResponseBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class CustomerRideController extends BaseController {

    private final CustomerRideService customerRideService;

    @GetMapping("/customers/ride")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<ApiResponse<List<RideSearchResponse>>> searchRide(@RequestBody @Valid RideSearchRequest request,
                                                                            BindingResult bindingResult){
        super.throwIfHasError(bindingResult);
        return ResponseEntity.ok(ResponseBuilder.buildOkResponse(customerRideService.initiateRideSearch(request)));
    }

    @PostMapping("/customers/ride")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<ApiResponse<RideInitiateResponse>> requestRide(@RequestBody @Valid RideInitiateRequest request,
                                                                               BindingResult bindingResult){
        super.throwIfHasError(bindingResult);
        return ResponseEntity.ok(ResponseBuilder.buildOkResponse(customerRideService.initiateRideRequest(request)));
    }

    @PutMapping("/customers/ride/cancellation")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<ApiResponse<RideCancelResponse>> cancelRide(@RequestBody @Valid RideCancelRequest request,
                                                                      BindingResult bindingResult){
        super.throwIfHasError(bindingResult);
        return ResponseEntity.ok(ResponseBuilder.buildOkResponse(customerRideService.cancelRide(request)));
    }

    @GetMapping("/customers/ride/status")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<ApiResponse<RideStatusResponse>> checkRideStatus(@RequestBody @Valid RideStatusRequest request,
                                                                           BindingResult bindingResult){
        super.throwIfHasError(bindingResult);
        return ResponseEntity.ok(ResponseBuilder.buildOkResponse(customerRideService.getRideStatus(request)));
    }

    @PostMapping("/customers/ride/rating")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<ApiResponse> reviewRide(@RequestBody @Valid RideReviewRequest request,
                                                                         BindingResult bindingResult){
        super.throwIfHasError(bindingResult);
        customerRideService.reviewRide(request);
        return ResponseEntity.ok(ResponseBuilder.buildOkResponse());
    }

}
