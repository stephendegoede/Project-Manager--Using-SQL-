-- create table projects (pNumber varchar(255), 
-- pName varchar(255), 
-- bType varchar(255), 
-- pAddress varchar(255), 
-- erfNumber varchar(255), 
-- totalCharge float, 
-- paidToDate float, 
-- deadline varchar(255), 
-- architect varchar(255), 
-- contractor varchar(255), 
-- customer varchar(255), 
-- primary key (pNumber));

-- create table person (personName varchar(255), 
-- jobDescription varchar(255), 
-- telephone varchar(255), 
-- email varchar(255), 
-- personAddress varchar (255),
-- primary key (personName));

insert into projects values ('1001', 
'The Highline', 
'Apartment', 
'20 Peters Road', 
'435', 
2500000.00, 
0.00, 
'25062020', 
'Unfinalized', 
'', 
'Willem Pieters', 
'Piet Joubert', 
'Lourens Tratford');

insert into projects values ('1002', 
'The Vista', 
'Mall', 
'1 Herald Road', 
'840', 
5000000.00, 
0.00, 
'25092020', 
'Unfinalized', 
'', 
'Phillip Govendor', 
'Marais Nel', 
'Peet Botha');

insert into person values('Willem Pieters', 
'Architect', 
'0820822082', 
'willem.pieters@gmail.com', 
'20 Pieters Road');

insert into person values('Piet Joubert', 
'Contractor', 
'0720722072', 
'piet.joubert@gmail.com', 
'21 Joubert Road');

insert into person values('Lourens Tratford', 
'Customer', 
'0830833083', 
'lourens.tratford@gmail.com', 
'22 Tratford Road');

insert into person values('Phillip Govendor', 
'Architect', 
'0820822082', 
'phillip.govendor@gmail.com', 
'23 Phillips Road');

insert into person values('Marais Nel', 
'Contractor', 
'0720722072', 
'marais.nel@gmail.com', 
'24 Marais Road');

insert into person values('Peet Botha', 
'Customer', 
'0830833083', 
'peet.botha@gmail.com', 
'25 Peet Road');