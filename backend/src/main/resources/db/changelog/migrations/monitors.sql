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

--changeset iliya132:added-next-run-column
alter table monitors add column next_run timestamp with time zone default now();

create index next_run_idx on monitors(next_run);

--changeset iliya132:added-monitor-type
create type monitor_type as enum ('PING');
alter table monitors add column if not exists monitor_type monitor_type not null;

--changeset iliya132:processors-add-service-name-field
alter table monitors add column if not exists service varchar(50) not null;

--changeset iliya132:processors-add-response-time-processor
alter type monitor_type add value 'RESPONSE_TIME';

--changeset iliya132:processors-add-response-processor
alter type monitor_type add value 'RESPONSE';

--changeset iliya132:added-user-id-index
create index user_id_idx on monitors(owner);

