drop table if exists users_info CASCADE;
create table users_info (
    name varchar(255) not null,
    salary double,
    primary key (name)
);