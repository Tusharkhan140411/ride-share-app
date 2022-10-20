package com.mlab.rideshare.entity;

import com.mlab.rideshare.entity.base.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "driver_current_info")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
public class DriverCurrentInfoEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "current_latitude", nullable = false)
    private double currentLatitude;

    @Column(name = "current_longitude", nullable = false)
    private double currentLongitude;

    @Column(name = "old_latitude")
    private double oldLatitude;

    @Column(name = "old_longitude")
    private double oldLongitude;

    @Column(name = "active_status")
    private boolean activeStatus;

    @OneToOne(mappedBy = "driverCurrentInfo")
    private DriverAdditionalInfoEntity driverAdditionalInfo;

}
