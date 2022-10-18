package com.mlab.rideshare.entity;

import com.mlab.rideshare.entity.base.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "ride_info")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
public class RideInfoEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "customer_id")
    private long customerId;

    @Column(name = "driver_id")
    private long driverId;

    @Column(name = "source_latitude", nullable = false)
    private double sourceLatitude;

    @Column(name = "source_longitude", nullable = false)
    private double sourceLongitude;

    @Column(name = "destination_latitude", nullable = false)
    private double destinationLatitude;

    @Column(name = "destination_longitude", nullable = false)
    private double destinationLongitude;

    @Column(name = "fare")
    private double fare;

    @Column(name = "tracking_id", nullable = false)
    private String trackingId;

    @Column(name = "status")
    private int status;

    @Column(name = "payment_status")
    private int paymentStatus;

    @Column(name = "ride_start_time")
    private Date rideStartTime;

    @Column(name = "ride_end_time")
    private Date rideEndTime;
}
