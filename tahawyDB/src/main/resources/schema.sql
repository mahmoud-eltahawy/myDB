create table if not exists users(
	user_name varchar(100) not null primary key,
	password varchar(150) not null,
	homedir varchar(200) not null
);

create table if not exists files(
	file_name varchar(150) not null primary key,
	owner_name varchar(100) not null,
	file_place varchar(250) not null,
	file_type varchar(50) not null,
	foreign key(owner_name) references users(user_name) on delete cascade
);