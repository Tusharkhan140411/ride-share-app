create table ride_info
(
    id         bigint auto_increment
        primary key,
    created_at datetime not null,
    updated_at datetime not null,
    ride_start_time datetime null,
    ride_end_time datetime null,
    customer_id bigint not null,
    driver_id bigint not null,
    source_latitude varchar(255) not null,
    source_longitude varchar(255) not null,
    destination_latitude varchar(255) not null,
    destination_longitude varchar(255) not null,
    fare double ,
    tracking_id varchar(255) not null,
    status int ,
    payment_status int
);

create table driver_current_info
(
    id         bigint auto_increment
        primary key,
    created_at datetime not null,
    updated_at datetime not null,
    driver_id bigint not null,
    current_latitude varchar(255) not null,
    current_longitude varchar(255) not null,
    old_latitude varchar(255) null,
    old_longitude varchar(255) null,
    active_status int
);

