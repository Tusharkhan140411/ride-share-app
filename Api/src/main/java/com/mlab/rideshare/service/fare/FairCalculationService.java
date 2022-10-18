package com.mlab.rideshare.service.fare;

import com.mlab.rideshare.exception.BadRequestException;
import com.mlab.rideshare.service.base.BaseService;
import com.mlab.rideshare.util.DistanceCalculatorUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FairCalculationService extends BaseService {

    public double calculateRideFare(double sourceLatitude,double sourceLongitude,
                                    double destinationLatitude,double destinationLongitude,double farePerKm){
        double calculatedFare = DistanceCalculatorUtils
                .distance(sourceLatitude,sourceLongitude,destinationLatitude,destinationLongitude) * farePerKm;

        if (calculatedFare <= 0){
            throw new BadRequestException(messageHelper.getLocalMessage("validation.constraints.invalid.location.input.message"));
        }

        return calculatedFare;
    }
}
