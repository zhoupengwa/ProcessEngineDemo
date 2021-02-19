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


create table z_paiche
(
    id              varchar(64)  not null comment '派车ID',
    pid             varchar(64)  not null comment '对应流程实例ID',
    user_id         varchar(64)  not null comment '用户ID',
    start_date_time datetime     not null comment '开始时间',
    persons         int          not null comment '乘车人数',
    phone           varchar(25) comment '乘车电话',
    start_position  varchar(255) not null comment '乘车地点',
    end_position    varchar(255) not null comment '到达目的地',
    driver          varchar(25) comment '司机姓名',
    car             varchar(25) comment '车号',
    bzhu            varchar(255) comment '备注',
    create_time     datetime     not null comment '记录创建时间',
    primary key (id)
);


create table z_log
(
    id          varchar(64)  not null comment '日志ID',
    task        varchar(128) not null comment '任务名称',
    task_id     varchar(64)  not null comment '任务ID',
    user_id     varchar(64)  not null comment '用户ID',
    isagreed    varchar(10)  not null comment '操作步骤,对应zz_code中关键字',
    log         varchar(500) comment '日志记录',
    create_time datetime     not null comment '记录创建时间',
    primary key (id)
);

