/*---------------------------------------*/
update tbl_user_delete set d_date=('2022-10-10') where d_code='u4';


/*댓글 작성 유저 변경*/
update tbl_review 
set u_code='u000' 
where u_code = (select d_code from tbl_user_delete where DATEDIFF(now(),(tbl_user_delete.d_date)) >=14);


/*유저 완전 삭제*/
delete from tbl_user,tbl_user_delete
USING tbl_user
LEFT JOIN tbl_user_delete
ON tbl_user.u_code = tbl_user_delete.d_code
where (select DATEDIFF(now(),tbl_user_delete.d_date) >=14);

delete from tbl_user where u_code='u4';

select tbl_review.*,tbl_user.u_id from tbl_review, tbl_user where tbl_user.u_code = tbl_review.u_code;

update tbl_user set u_id = '삭제된 사용자 입니다.' where u_code='u000';

insert into tbl_store(s_code,s_name,s_admin,s_location,s_tel,s_c_code) 
values('s12','테스트','담당자','인천 미추홀구 매소홀로 476','032-875-0126',4);

select * from tbl_store;

/*메뉴 검색*/
select s.*, m.m_name
from tbl_store s, tbl_menu m
where s.s_code=m.s_code and (s.s_name like '%점%' or m.m_name like '%점%') group by s.s_code;

/*getS_code*/
select s_code from tbl_cart where u_code='u14' group by s_code;

update tbl_store set s_waiting=(select count(*) from tbl_post where s_code='s11') where s_code='s11';

select * from tbl_cart where u_code='a1' and m_name='등뼈찜(순한맛)(대)';

select * from tbl_cart;

desc tbl_store;

select * from tbl_store;


alter table tbl_store add column x double not null;
alter table tbl_store add column y double not null;

update tbl_store set x=37.4395, y=126.6736 where s_code='s1';
update tbl_store set x=37.438494 , y=126.675656 where s_code='s2';
update tbl_store set x=37.4401  , y=126.6697 where s_code='s3';
update tbl_store set x=37.4409  , y=126.6787 where s_code='s4';
update tbl_store set x=37.4442   , y=126.654 where s_code='s5';
update tbl_store set x=37.4407   , y=126.6693 where s_code='s6';
update tbl_store set x=37.439382  , y=126.674759 where s_code='s7';
update tbl_store set x=37.439188  , y=126.675028 where s_code='s8';
update tbl_store set x=37.439258, y=126.674227 where s_code='s9';
update tbl_store set x=37.439460,  y=126.671653 where s_code='s10';
update tbl_store set x=37.444819,  y=126.670045 where s_code='s11';
update tbl_store set x=37.442533,  y=126.665652 where s_code='s12';
update tbl_store set x=37.437511  , y=126.682000 where s_code='s13';


/*유저 - 나이, 성별 컬럼 추가+*/
alter table tbl_user add column age int not null;
alter table tbl_user add column gender varchar(4) not null;

/*가게 - 최소주문비용, 배달비 추가*/
alter table tbl_store add column minimal int not null;
alter table tbl_store add column cost int not null;


select ifnull(
   concat('s10', concat('r', max((substr(r_code, 5)) + 1))),
    concat('s10', concat('r', 1))
) as new_code
from tbl_review
where s_code = 's10';

select * from tbl_review;

select ifnull(max(c_code)+1, 1) from tbl_post;




select * from tbl_post;
select * from tbl_condition;

desc tbl_post;
desc tbl_condition;

delete from tbl_post;
delete from tbl_condition;

alter table tbl_post auto_increment=1;
alter table tbl_condition auto_increment=1;



select s_waiting from tbl_store where s_code='s1';

/*게시글 작성시 s_waiting 증가*/
update tbl_store set s_waiting=(select s_waiting from (select s_waiting from tbl_store where s_code='s1') as s_waiting)+1 where s_code='s1';
/*update tbl_store set s_waiting=0;*/

/*즐겨찾기 */
select * 
from tbl_store 
where s_code=any(select s_code from tbl_liked where u_code='u18');



select count(*) from (select s_code from tbl_liked where u_code='u18')as liked;

alter table tbl_user add column token varchar(250) not null;


/* review 테이블에 u_id 추가 */
alter table tbl_review add column u_id varchar(20) not null;

select p.*,c.age,c.gender,c.headcount 
from tbl_post p,tbl_condition c
where p_code='1' and p.c_code=c.c_code;


/*11.21일 추가*/
alter table tbl_user add column birthday datetime not null;
alter table tbl_user modify column birthday date not null;

select d_code from tbl_user_delete where DATEDIFF(now(),(tbl_user_delete.d_date)) >=14;

select timestampdiff(year, '19970302', now());

select token from tbl_user where u_id = 'test';

alter table tbl_review drop column u_id;



/* 2022-11-21 장하우 작성 */
update tbl_post set p_headcount = p_headcount + 1 where p_code = 1;

select * from tbl_post;
select * from tbl_condition;
select * from tbl_user;
select * from tbl_order;
select * from tbl_cart;

select p.*, c.age, c.gender, c.headcount
from tbl_post p, tbl_condition c
where p_code = 20 and p.c_code = c.c_code;

select token from tbl_order where p_code = 13;

update tbl_post set p_headcount = 2 where p_code = 8;
update tbl_user set token = 'eV7-7N8zSrGc9Y-NxVnDj0:APA91bGU_F7rsYFtdoy2ubMSvVpCpdORYTMlJ0PCZl0kEAbunVVwN9kZHgxc4DGzbPHIbXPFGDckNR4jwpXzvtBieJttEds8NhPjQLhUBa093ri7yTQbkIMnHo3jMFU-AwORQ25lf1tU' where u_code = 'u14';

delete from tbl_order where p_code = 14;

insert into tbl_order(p_code, u_code, token)
values(1, 'u14', ( select token from tbl_user where u_code = 'u14' ));


delete from tbl_order where p_code=3;
delete from tbl_post where p_code=3;
delete from tbl_condition where c_code=3;
delete from tbl_cart;

insert into tbl_cart(u_code,s_code,m_name,amount,m_photo,m_price) values('u15','s10','감자탕(대)',1,'/upload/menu/m24.jpg',42000);
insert into tbl_cart(u_code,s_code,m_name,amount,m_photo,m_price) values('u16','s10','약선삼계탕',1,'/upload/menu/m26.jpg',14000);

alter table tbl_post auto_increment=1;
alter table tbl_condition auto_increment=1;

select * from tbl_store;


insert into tbl_cart(u_code, s_code, m_name, m_price)
values('u20', 's10', '쭈꾸미볶음(1인분)', 8900);


update tbl_order
set o_status = '결제완료'
where p_code = 1 and u_code = 'u25';


select max(p_code) + 1 from tbl_post;