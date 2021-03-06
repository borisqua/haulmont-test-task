--1
create table patients (
id bigint generated by default as identity (start with 1, increment by 1) not null primary key,
name varchar(50) not null,
surname varchar(128) not null,
patronymic varchar(50),
phone varchar(30));

--2
create table doctors (
id bigint generated by default as identity (start with 1, increment by 1) not null primary key,
name varchar(50) not null,
surname varchar(128) not null,
patronymic varchar(50),
specialization varchar(128));

--3
create table prescriptions (
id bigint generated by default as identity (start with 1, increment by 1) not null primary key,
description varchar(1024) not null,
patientId bigint not null,
doctorId bigint not null,
creationDate date default current_date not null,
validityLength integer default 7 not null,
priority varchar(50) default 'Нормальный' not null,  -- 'Un Solet (Нормальный)', 'Cito (Срочный)', 'Statim (Немедленный)'
foreign key (patientId) references patients(id),
foreign key (doctorId) references doctors(id));

--4
create view valid_prescriptions as
  (select
    p.id as id,
    trim(both from trim(both from trim(both from d.name)+' '+d.patronymic)+' '+d.surname) as doctor,
    trim(both from trim(both from trim(both from t.name)+' '+t.patronymic)+' '+t.surname) as patient,
    p.description as prescription,
    p.priority as priority,
    dateadd('day', p.validitylength, p.creationdate) as expiration
  from prescriptions as p
    inner join doctors as d on p.doctorid = d.id
    inner join patients as t on p.patientid = t.id
  where DATEDIFF('day', p.creationDate, current_date) <= p.validityLength);

--5
create view all_prescriptions as
  (select
    p.id as id,
    trim(both from trim(both from trim(both from d.name)+' '+d.patronymic)+' '+d.surname) as doctor,
    trim(both from trim(both from trim(both from t.name)+' '+t.patronymic)+' '+t.surname) as patient,
    p.description as prescription,
    p.priority as priority,
    dateadd('day', p.validitylength, p.creationDate) as expiration
  from prescriptions as p
    inner join doctors as d on p.doctorid = d.id
    inner join patients as t on p.patientid = t.id);

--6
create view all_patients as
  (select
    id,
    trim(both from trim(both from trim(both from name)+' '+patronymic)+' '+surname) as fullname,
    phone
  from patients);

--7
create view all_doctors as
  (select
    id,
    trim(both from trim(both from trim(both from name)+' '+patronymic)+' '+surname) as fullname,
    specialization
  from doctors);

--8
CREATE VIEW DOCTORS_ALL_RESULTS AS
SELECT d.id                                                                                     as id,
       TRIM(BOTH FROM +TRIM(BOTH FROM TRIM(BOTH FROM name) + ' ' + patronymic) + ' ' + surname) AS doctor,
       COUNT(*)                                                                                 AS number
FROM prescriptions AS p
       INNER JOIN doctors AS d ON d.id = p.doctorId
GROUP BY d.id
ORDER BY number DESC;

--9
CREATE VIEW DOCTORS_CHARTS AS
  SELECT id, doctor, number, REPEAT(CHAR(9606), number)+'  '+number as chart FROM DOCTORS_ALL_RESULTS;
