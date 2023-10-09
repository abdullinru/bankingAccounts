-- liquebase formatted sql
-- changeset abdullinru:1

create table accounts
(
    id          BigSerial      primary key,
    name       varchar     not null,
    balance double     not null,
    pin_code integer not null
)