package com.mlab.rideshare.entity;

import com.mlab.rideshare.entity.base.BaseEntity;
import lombok.*;

import javax.persistence.*;


@Entity
@Table(name = "driver_additional_info")
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Builder(toBuilder = true)
public class DriverAdditionalInfoEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "user_id")
    private long userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_info_id")
    private VehicleInfoEntity vehicleInfo;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "driver_current_info_id")
    private DriverCurrentInfoEntity driverCurrentInfo;

    @Column(name = "vehicle_reg_plate_no")
    private String vehicleRegPlateNo;

    @Column(name = "vehicle_licence_info")
    private String vehicleLicenceInfo;

    public void setVehicleInfo(VehicleInfoEntity vehicleInfo){
        this.vehicleInfo = vehicleInfo;
        this.vehicleInfo.getDriverAdditionalInfos().add(this);
    }
}
