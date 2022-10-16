create table user
(
    id         bigint auto_increment
        primary key,
    created_at datetime     not null,
    updated_at datetime     not null,
    email      varchar(255) null,
    mobile      varchar(255) not null,
    full_name  varchar(255) not null,
    user_name  varchar(255) not null,
    password   varchar(255) not null,
    role_id bigint not null
);

create table role
(
    id         bigint auto_increment
        primary key,
    created_at datetime not null,
    updated_at datetime not null,
    name    varchar(255)   not null
);

