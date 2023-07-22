--liquibase formatted sql

--changeset iliya132:init
create table users (
    name varchar(50) primary key,
    password varchar(100) not null
);
