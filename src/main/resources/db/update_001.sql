create table site (
    id serial primary key,
    site varchar(2000),
    login varchar(2000),
    password varchar(2000)
);

create table links (
    id serial primary key,
    url varchar(2000),
    code varchar(100),
    count int,
    site_id int not null references site(id)
);