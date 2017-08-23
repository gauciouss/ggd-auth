use ggd;
create table Adm_User(
	account			varchar(20) primary key,
	pwd				varchar(50),
	name			varchar(50),
	email			varchar(50),
	address			varchar(50),
	tel				varchar(20),
	phone			varchar(20),
    group_id		varchar(10),
	create_date		datetime,
	update_date		datetime,
	isEnabled		bit(1),
	isApproved		bit(1)
);

create table Adm_Group(
	group_id		varchar(10) primary key,
	group_name		varchar(10),
	create_date		datetime,
	update_date		datetime,
	isEnabled		bit(1),
	isApproved		bit(1)
);


create table Adm_Func (
	func_id			varchar(10) primary key,
	func_name		varchar(20),
	parent_id		varchar(10),	
	is_root			bit(1),
	url				varchar(1000),
	sort			int,
	create_date		datetime,
	update_date		datetime,
	isEnabled		bit(1),
	isApproved		bit(1)
);

create table Adm_Group_Func_Map (
	group_id		varchar(10),
	func_id			varchar(10),
	constraint A_GF_PK primary key (group_id, func_id)
);

insert into Adm_User values ('admin', '123456', '系統管理者', 'gauciouss@gmail.com', '', '', '', 'GRP0000001', now(), null, 1, 1);
insert into Adm_Group values ('GRP0000001', '系統管理者', now(), null, 1, 1);
insert into Adm_Func values ('FUN0000001', '權限管理', '', 1, '', 1, now(), null, 1, 1);
insert into Adm_Func values ('FUN0000002', '使用者管理', 'FUN0000001', 0, 'auth/user/edit', 1, now(), null, 1, 1);
insert into Adm_Func values ('FUN0000003', '群組管理', 'FUN0000001', 0, 'auth/grp/edit', 2, now(), null, 1, 1);
insert into Adm_Func values ('FUN0000004', '功能管理', 'FUN0000001', 0, 'auth/func/edit', 3, now(), null, 1, 1);
insert into Adm_Group_Func_Map values ('GRP0000001', 'FUN0000001');
insert into Adm_Group_Func_Map values ('GRP0000001', 'FUN0000002');
insert into Adm_Group_Func_Map values ('GRP0000001', 'FUN0000003');
insert into Adm_Group_Func_Map values ('GRP0000001', 'FUN0000004');
