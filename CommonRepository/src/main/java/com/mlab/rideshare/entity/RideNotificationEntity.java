package com.mlab.rideshare.entity;

import com.mlab.rideshare.entity.base.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "ride_notification")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
public class RideNotificationEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "ride_id")
    private long rideId;

    @Column(name = "nearest_driver_list", nullable = false)
    private String nearestDriverList;

    @Column(name = "is_active")
    private boolean activeStatus;
}
