create table if not exists course (id uuid primary key,
 title varchar(100) not null ,
 summary varchar(255) not null ,
 catalog_number bigint not null,
 created datetime,
 updated datetime,
 is_new boolean);

