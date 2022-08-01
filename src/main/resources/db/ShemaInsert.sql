create table employee (
    id serial primary key not null,
    name varchar(2000),
    surname varchar(2000),
    inn varchar(2000),
    employmentDate TIMESTAMP DEFAULT current_timestamp
);

create table person (
    id serial primary key not null,
    login varchar(2000),
    password varchar(2000),
    employee_id int references employee(id)
);

insert into employee(name, surname, inn, employmentDate)
VALUES ('Дима', 'Красин', 12345678, now());
insert into employee(name, surname, inn, employmentDate)
VALUES ('Вася', 'Майснер', 87654321, now());

insert into person(login, password, employee_id)
values('parsentev', '123', (select distinct id from employee where inn = '12345678'));
insert into person(login, password, employee_id)
values('ban', '123', (select distinct id from employee where inn = '12345678'));
insert into person(login, password, employee_id)
values('ivan', '123', (select distinct id from employee where inn = '87654321'));