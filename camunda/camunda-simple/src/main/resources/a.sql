create table rd
(

    id bigint not null  primary key ,

    user_id int not null comment '123',

    count int not null,

    last_count_change int not null comment '5',


    creat_time datetime not null


);

1 123  0  0

2 123  5  5

3 123  3  -2



