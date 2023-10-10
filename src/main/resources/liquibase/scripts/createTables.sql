-- liquebase formatted sql
-- changeset abdullinru:1

create table accounts
(
    id          BigSerial      primary key,
    name       varchar     not null,
    balance double     not null,
    pin_code integer not null
);
INSERT INTO accounts (id, name, balance, pin_code) VALUES
    (1, 'first', 100.99, 1111),
    (2, 'second', 175.88, 2222),
    (3, 'third', 444.44, 2222);