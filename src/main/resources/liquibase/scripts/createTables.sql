-- liquebase formatted sql
-- changeset abdullinru:1

create table accounts
(
    id         BigSerial      primary key,
    name       varchar     not null,
    balance    numeric     not null,
    pin_code   varchar not null
);
INSERT INTO accounts (id, name, balance, pin_code) VALUES
    (100, 'first', 100.99, 1111),
    (200, 'second', 175.88, 2222),
    (300, 'third', 444.44, 2222);