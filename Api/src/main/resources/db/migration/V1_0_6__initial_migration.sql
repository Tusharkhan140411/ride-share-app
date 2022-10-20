
alter table driver_additional_info
    add driver_current_info_id bigint not null;

alter table ride_notification change eligible_driver_list nearest_driver_list varchar(20000) not null;
