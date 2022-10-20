package com.mlab.rideshare.entity;

import com.mlab.rideshare.entity.base.BaseEntity;
import lombok.*;

import javax.persistence.*;


@Entity
@Table(name = "ride_review")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
public class RideReviewEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "ride_id")
    private long rideId;

    @Column(name = "review")
    private String review;

    @Column(name = "rating")
    private int rating;
}
