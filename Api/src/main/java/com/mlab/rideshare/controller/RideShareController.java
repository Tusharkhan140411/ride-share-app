package com.mlab.rideshare.controller;

import com.mlab.rideshare.model.request.ride.RideCancelRequest;
import com.mlab.rideshare.model.request.ride.RideInitiateRequest;
import com.mlab.rideshare.model.request.ride.RideReviewRequest;
import com.mlab.rideshare.model.request.ride.RideSearchRequest;
import com.mlab.rideshare.model.response.ApiResponse;
import com.mlab.rideshare.model.response.ride.RideCancelResponse;
import com.mlab.rideshare.model.response.ride.RideInitiateResponse;
import com.mlab.rideshare.model.response.ride.RideSearchResponse;
import com.mlab.rideshare.model.response.ride.RideStatusResponse;
import com.mlab.rideshare.service.ride.RideShareService;
import com.mlab.rideshare.util.response.ResponseBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class RideShareController extends BaseController {

    private final RideShareService rideShareService;

    @GetMapping("/ride/search")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<ApiResponse<List<RideSearchResponse>>> searchRide(@RequestBody @Valid RideSearchRequest request,
                                                                            BindingResult bindingResult){
        super.throwIfHasError(bindingResult);
        return ResponseEntity.ok(ResponseBuilder.buildOkResponse(rideShareService.initiateRideSearch(request)));
    }

    @PostMapping("/ride/request")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<ApiResponse<RideInitiateResponse>> requestRide(@RequestBody @Valid RideInitiateRequest request,
                                                                               BindingResult bindingResult){
        super.throwIfHasError(bindingResult);
        return ResponseEntity.ok(ResponseBuilder.buildOkResponse(rideShareService.initiateRideRequest(request)));
    }

    @PutMapping("/ride/cancellation")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<ApiResponse<RideCancelResponse>> cancelRide(@RequestBody @Valid RideCancelRequest request,
                                                                      BindingResult bindingResult){
        super.throwIfHasError(bindingResult);
        return ResponseEntity.ok(ResponseBuilder.buildOkResponse(rideShareService.cancelRide(request)));
    }

    @GetMapping("/ride/status/{tracking_no}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<ApiResponse<RideStatusResponse>> checkRideStatus(@PathVariable("tracking_no") @NotBlank String trackingNo){
        return ResponseEntity.ok(ResponseBuilder.buildOkResponse(rideShareService.getRideStatus(trackingNo)));
    }

    @PostMapping("/ride/review")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<ApiResponse> reviewRide(@RequestBody @Valid RideReviewRequest request,
                                                                         BindingResult bindingResult){
        super.throwIfHasError(bindingResult);
        rideShareService.reviewRide(request);
        return ResponseEntity.ok(ResponseBuilder.buildOkResponse());
    }

}
