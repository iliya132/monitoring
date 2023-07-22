--liquibase formatted sql

--changeset iliya132:init
create table monitors (
    id bigserial primary key,
    owner varchar(50) references users(name) not null,
    cron_expression varchar(30) not null,
    description text,
    url text not null,
    last_run timestamp with time zone
);
