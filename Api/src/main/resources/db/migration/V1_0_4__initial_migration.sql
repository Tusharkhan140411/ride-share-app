create table driver_additional_info
(
    id         bigint auto_increment
        primary key,
    created_at datetime not null,
    updated_at datetime not null,
    user_id bigint not null,
    vehicle_reg_plate_no varchar(255) not null,
    vehicle_licence_info varchar(255) not null,
    vehicle_info_id bigint
);

create table vehicle_info
(
    id         bigint auto_increment
        primary key,
    created_at datetime not null,
    updated_at datetime not null,
    vehicle_type int,
    vehicle_name varchar(25) not null,
    fare_per_km double
);

create table ride_cancel_info
(
    id         bigint auto_increment
        primary key,
    created_at datetime not null,
    updated_at datetime not null,
    ride_id bigint,
    canceller_id bigint,
    canceller_type varchar(10) not null,
    cancelled_at datetime not null
);

create table ride_review
(
    id         bigint auto_increment
        primary key,
    created_at datetime not null,
    updated_at datetime not null,
    ride_id bigint,
    rating int,
    review varchar(1000) null
);

create table ride_notification
(
    id         bigint auto_increment
        primary key,
    created_at datetime not null,
    updated_at datetime not null,
    ride_id bigint,
    is_active int,
    eligible_driver_list varchar(20000)
);

