insert into trip (id, pu_zid, do_zid, pickup, dropoff, yellow) values (nextval('trip_seq'), 23, 45, timestamp '2018-01-01 00:18:50', '2018-01-01 00:24:39', true);
insert into trip (id, pu_zid, do_zid, pickup, dropoff, yellow) values (nextval('trip_seq'), 23, 46, timestamp '2018-01-02 00:17:50', '2018-01-02 00:24:39', true);
insert into trip (id, pu_zid, do_zid, pickup, dropoff, yellow) values (nextval('trip_seq'), 24, 46, timestamp '2018-01-01 00:10:50', '2018-01-01 00:24:39', false);
insert into trip (id, pu_zid, do_zid, pickup, dropoff, yellow) values (nextval('trip_seq'), 22, 23, timestamp '2018-01-01 20:18:50', '2018-01-01 00:24:39', true);
insert into zone (id, name) values (23, 'Jamaica Bay');
insert into zone (id, name) values (22, 'Battery Park');
insert into zone (id, name) values (24, 'Brooklyn Heights');
insert into zone (id, name) values (47, 'Claremont/Bathgate');
insert into zone (id, name) values (46, 'City Island');
insert into zone (id, name) values (45, 'Chinatown');
