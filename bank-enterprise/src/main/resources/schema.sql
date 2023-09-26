--DROP TABLE IF EXISTS ENTERPRISE;

create table if not exists enterprise (
  id bigserial PRIMARY KEY,
  code varchar(255) not null,
  name varchar(255) not null,
  description varchar(255) not null,
  active boolean default true not null
);