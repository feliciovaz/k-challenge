create sequence trip_seq start with 1 increment by 20;

create table trip (
    id bigint not null,
    pu_zid int,
    do_zid int,
    pickup timestamp(6) not null,
    dropoff timestamp(6) not null,
    yellow boolean not null,
    primary key(id)
);

create table zone (
    id int not null,
    name varchar(50) not null,
    primary key (id)
);

