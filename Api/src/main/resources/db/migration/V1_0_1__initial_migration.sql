create table driver_additional_info
(
    id         bigint auto_increment
        primary key,
    created_at datetime not null,
    updated_at datetime not null,
    user_id bigint not null,
    vehicle_reg_plate_no varchar(255) not null,
    vehicle_licence_info varchar(255) not null
);

