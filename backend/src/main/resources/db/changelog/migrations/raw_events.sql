--liquibase formatted sql

--changeset iliya132:init
create table raw_events (
    id bigserial primary key,
    monitor bigint references monitors(id) not null,
    result jsonb not null,
    occured_at timestamp with time zone not null
);
