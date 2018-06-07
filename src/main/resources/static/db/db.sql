-- auto-generated definition
create table students
(
  student_id     int auto_increment
    primary key,
  first_name     varchar(32) not null,
  last_name      varchar(32) not null,
  patronymic     varchar(32) null,
  birthday       date        not null,
  sex            char        not null,
  group_id       int         not null,
  education_year int         not null,
  constraint students_groups_group_id_fk
  foreign key (group_id) references groups (group_id)
);

create index students_groups_group_id_fk
  on students (group_id);