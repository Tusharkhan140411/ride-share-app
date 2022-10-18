alter table driver_current_info modify current_latitude double not null;

alter table driver_current_info modify current_longitude double not null;

alter table driver_current_info modify old_latitude double null;

alter table driver_current_info modify old_longitude double null;

alter table ride_info modify source_latitude double not null;

alter table ride_info modify source_longitude double not null;

alter table ride_info modify destination_latitude double not null;

alter table ride_info modify destination_longitude double not null;