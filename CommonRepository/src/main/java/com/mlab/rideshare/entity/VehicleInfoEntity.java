package com.mlab.rideshare.entity;

import com.mlab.rideshare.entity.base.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;

@Entity
@Table(name = "vehicle_info")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class VehicleInfoEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "vehicle_type")
    private int vehicleType;

    @Column(name = "vehicle_name")
    private String vehicleName;

    @OneToMany(mappedBy = "vehicleInfo")
    private Collection<DriverAdditionalInfoEntity> driverAdditionalInfos = new HashSet<>();

    @Column(name = "fare_per_km")
    private double farePerKm;
}
