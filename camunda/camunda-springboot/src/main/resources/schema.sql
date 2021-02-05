create table zz_role
(
    role_id   varchar(64) not null comment '职务ID',
    role_name varchar(64) comment '职务名称',
    primary key (role_id)
);

create table zz_department
(
    department_id   varchar(64) NOT NULL comment '部门ID',
    department_name varchar(64) comment '部门名称',
    primary key (department_id)
);



create table zz_user
(
    user_id       varchar(64) not null comment '用户ID',
    department_id varchar(64) comment '部门ID',
    role_id       varchar(64) comment '角色ID',
    username      varchar(64) comment '用户名',
    psd           varchar(64) comment '密码',
    primary key (user_id)
);