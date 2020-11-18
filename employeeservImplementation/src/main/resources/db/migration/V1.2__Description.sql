create table employee
(
   id long not null,
   first_name varchar(255) , 
   last_name varchar(255) ,
   date_of_birth date,
   primary key(id)
);

create table address(
line1 varchar(255),
line2 varchar(255),
city varchar(255),
state varchar(255),
country varchar(255),
zip_code numeric(10),
id long,
    foreign key(id) references employee
);

insert into employee(id,first_name,last_name,date_of_birth) values(1,'a','b','2020-01-01');
insert into address(line1,line2,city,state,country,zip_code,id) values('abc','xyz','sj','ca','usa',581324,1);