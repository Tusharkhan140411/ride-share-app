package com.mlab.rideshare.controller;

import com.mlab.rideshare.model.request.ride.*;
import com.mlab.rideshare.model.response.ApiResponse;
import com.mlab.rideshare.model.response.ride.RideNotificationResponse;
import com.mlab.rideshare.service.ride.DriverRideService;
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
public class DriverRideController extends BaseController {

    private final DriverRideService driverRideService;

    @GetMapping("/drivers/ride/notification")
    @PreAuthorize("hasRole('DRIVER')")
    public ResponseEntity<ApiResponse<List<RideNotificationResponse>>> getActiveRides(){
        return ResponseEntity.ok(ResponseBuilder.buildOkResponse(driverRideService.getRidesNotification()));
    }

    @PutMapping("/drivers/ride/acceptance")
    @PreAuthorize("hasRole('DRIVER')")
    public ResponseEntity<ApiResponse> acceptRide(@RequestBody @Valid RideAcceptRequest request,
                                                                     BindingResult bindingResult){
        super.throwIfHasError(bindingResult);
        driverRideService.acceptRide(request);
        return ResponseEntity.ok(ResponseBuilder.buildOkResponse());
    }

    @PutMapping("/drivers/ride/cancellation")
    @PreAuthorize("hasRole('DRIVER')")
    public ResponseEntity<ApiResponse> cancelRide(@RequestBody @Valid RideCancelRequest request,
                                                                      BindingResult bindingResult){
        super.throwIfHasError(bindingResult);
        driverRideService.cancelRide(request);
        return ResponseEntity.ok(ResponseBuilder.buildOkResponse());
    }

    @PutMapping("/drivers/ride/start")
    @PreAuthorize("hasRole('DRIVER')")
    public ResponseEntity<ApiResponse> startRide(@RequestBody @Valid RideUpdateRequest request,
                                                                     BindingResult bindingResult){
        super.throwIfHasError(bindingResult);
        driverRideService.startRide(request);
        return ResponseEntity.ok(ResponseBuilder.buildOkResponse());
    }

    @PutMapping("/drivers/ride/completion")
    @PreAuthorize("hasRole('DRIVER')")
    public ResponseEntity<ApiResponse> completeRide(@RequestBody @Valid RideUpdateRequest request,
                                                                     BindingResult bindingResult){
        super.throwIfHasError(bindingResult);
        driverRideService.completeRide(request);
        return ResponseEntity.ok(ResponseBuilder.buildOkResponse());
    }

}
