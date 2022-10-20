package com.mlab.rideshare.unit;


import com.mlab.rideshare.entity.RideInfoEntity;
import com.mlab.rideshare.entity.RideReviewEntity;
import com.mlab.rideshare.entity.UserEntity;
import com.mlab.rideshare.helper.mapper.Mapper;
import com.mlab.rideshare.mapper.TestObjectSupplier;
import com.mlab.rideshare.model.request.ride.RideReviewRequest;
import com.mlab.rideshare.service.*;
import com.mlab.rideshare.service.base.BaseService;
import com.mlab.rideshare.service.driver.DriverService;
import com.mlab.rideshare.service.fare.FairCalculationService;
import com.mlab.rideshare.service.ride.CustomerRideService;
import com.mlab.rideshare.service.ride.notification.RideNotificationService;
import nonapi.io.github.classgraph.utils.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CustomerRideServiceTest {

    @InjectMocks
    private CustomerRideService customerRideService;
    @Mock
    private VehicleInfoEntityService vehicleInfoEntityService;
    @Mock
    private FairCalculationService fairCalculationService;
    @Mock
    private UserEntityService userEntityService;
    @Mock
    private DriverCurrentInfoEntityService driverCurrentInfoEntityService;
    @Mock
    private RideInfoEntityService rideInfoEntityService;
    @Mock
    private DriverService driverService;
    @Mock
    private RideNotificationService rideNotificationService;
    @Mock
    private RideReviewEntityService rideReviewEntityService;
    @Mock
    private PasswordEncoder passwordEncoder;

    private BaseService baseService;

    private Mapper mapper;

    @BeforeEach
    void setup(){
       mapper = new Mapper(passwordEncoder);
       baseService= Mockito.mock(BaseService.class, Mockito.CALLS_REAL_METHODS);
        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    void testCustomerRideReview(){

        UserEntity userEntity = TestObjectSupplier.getUserEntity();
        RideInfoEntity rideInfoEntity =TestObjectSupplier.getRideInfoEntity();


        when(rideInfoEntityService
                .findByCustomerIdAndTrackingId(anyLong(),anyString())).thenReturn(java.util.Optional.ofNullable(rideInfoEntity));
        when(rideReviewEntityService
                .getByRideId(anyLong())).thenReturn(Optional.empty());

        RideReviewRequest rideReviewRequest = TestObjectSupplier.getRevReq();
        customerRideService.reviewRide(rideReviewRequest);
        ArgumentCaptor<RideReviewEntity> argumentCaptor = ArgumentCaptor.forClass(RideReviewEntity.class);
        verify(rideReviewEntityService,times(1)).save(argumentCaptor.capture());
    }
}
