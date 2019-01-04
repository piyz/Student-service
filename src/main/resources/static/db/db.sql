create table `groups`
(
  group_id     int auto_increment
    primary key,
  group_name   varchar(26)          not null,
  curator_name varchar(26)          not null,
  specialty    varchar(26)          not null,
  enabled      tinyint(1) default 1 not null
);

create table hibernate_sequence
(
  next_val bigint null
)
  engine = MyISAM;

create table roles
(
  role_id   int auto_increment
    primary key,
  role_name varchar(32) not null,
  constraint roles_role_name_uindex
    unique (role_name)
);

create table students
(
  student_id     int auto_increment
    primary key,
  username       varchar(32)          null,
  password       varchar(255)         null,
  enabled        tinyint(1) default 1 not null,
  gender         char                 null,
  birthday       date                 null,
  education_year int                  null,
  group_id       int                  null,
  first_name     varchar(32)          not null,
  last_name      varchar(32)          not null,
  patronymic     varchar(32)          null,
  constraint students_groups_group_id_fk
    foreign key (group_id) references `groups` (group_id)
      on delete set null
);

create table students_roles
(
  student_id int not null,
  role_id    int not null,
  constraint role_id_fk
    foreign key (role_id) references roles (role_id),
  constraint student_id_fk
    foreign key (student_id) references students (student_id)
);

