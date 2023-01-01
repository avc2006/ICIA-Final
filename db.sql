use testdb;

/*카테고리 테이블*/
drop table tbl_category;

create table tbl_category(
	c_code int auto_increment primary key,
    c_type varchar(6) not null
);

desc tbl_category;

select * from tbl_category;


/*유저 타입 테이블*/
drop table tbl_user_type;

create table tbl_user_type(
	u_t_code int auto_increment primary key,
    u_type varchar(6) not null
);


desc tbl_user_type;

select * from tbl_user_type;

/*등급 테이블*/
drop table tbl_grade;

create table tbl_grade(
	standard int,
    grade varchar(6) not null primary key
);


desc tbl_grade;

select * from tbl_grade;

/*가게 테이블*/
drop table tbl_store;

create table tbl_store(
	s_code varchar(10) not null primary key,
    s_name varchar(50) not null,
    s_admin varchar(20) not null,
    s_location varchar(200) not null,
    s_tel varchar(20) not null,
    s_c_code int not null,
    s_waiting int default 0,
    s_order int default 0,
    s_rating double default 0,
    s_status boolean default true,
    s_photo varchar(200),
    u_id varchar(20) not null,
    u_pass varchar(255) not null,
    x double not null,
    y double not null,
    minimal int not null,
    cost int not null,
    foreign key (s_c_code) references tbl_category(c_code)
);

desc tbl_store;

select * from tbl_store;

/*메뉴 테이블*/
drop table tbl_menu;

create table tbl_menu(
	m_code int,
	s_code varchar(10) not null,
    m_name varchar(50) not null,
    m_price int not null,
    m_photo varchar(200),
    primary key (s_code, m_name),
    foreign key (s_code) references tbl_store(s_code)
);


desc tbl_menu;

select * from tbl_menu;

/* 유저 테이블 */
drop table tbl_user;

create table tbl_user(
	u_code varchar(100) not null primary key, 
    u_name varchar (20) not null,
    u_address varchar(200) not null,
    u_id varchar(20) not null,
    u_pass varchar(255) not null,
    u_adult boolean default false,
    u_date datetime default now(),
    manner int default 0,
    r_count int default 0,
    u_type int default 3,
    u_photo varchar(200),
    u_status boolean default true,
    age int not null,
    gender varchar(4) not null,
    token varchar(250) not null,
    birthday date not null,
	foreign key (u_type) references tbl_user_type(u_t_code) 
);

desc tbl_user;

select * from tbl_user;

delete from tbl_user where u_code='u14';
delete from tbl_user where u_code='u15';
delete from tbl_liked where u_code='u15';
delete from tbl_review where u_code='u14';

/*즐겨찾기 테이블*/

drop table tbl_liked;

create table tbl_liked(
	s_code varchar(10) not null,
    u_code varchar(100) not null,
    foreign key (s_code) references tbl_store(s_code),
    foreign key (u_code) references tbl_user(u_code),
    primary key(s_code,u_code)
);

desc tbl_liked;

select * from tbl_liked;

/*신고 테이블*/
drop table tbl_report;

create table tbl_report(
	r_code varchar(100) not null primary key,
    suer varchar(100) not null,
    defender varchar(100) not null,
    report_date  datetime default now(),
    r_result varchar(500) not null,
    r_state varchar(10) default '접수수리',
    r_type varchar(10) not null,
    foreign key (suer) references tbl_user(u_code),
    foreign key (defender) references tbl_user(u_code)
);

desc tbl_report;

select * from tbl_report;


/*리뷰 테이블*/
drop table tbl_review;

create table tbl_review(
	s_code varchar(10) not null,
	u_code varchar(100) not null,
    r_content varchar(200),
    r_photo varchar(200),
    r_rating double not null,
    review_date datetime default now(),
    r_code varchar(100) not null,
	r_reply boolean default false,
    foreign key (s_code) references tbl_store(s_code),
    foreign key (u_code) references tbl_user(u_code),
    primary key(r_code,u_code)
);


desc tbl_review;

select * from tbl_review;


/*유저 삭제 테이블*/

drop table tbl_user_delete;

create table tbl_user_delete(
	d_code varchar(100) not null primary key,
    d_date datetime default now(),
    foreign key (d_code) references tbl_user(u_code)
);

select * from tbl_user_delete;
desc tbl_user_delete;


/*댓글 테이블*/
drop table tbl_reply;

create table tbl_reply (
	r_code varchar(100) not null,
	s_code varchar(10) not null,
    u_code varchar(100) not null,
    re_content varchar(200),
    re_date datetime default now(),
    foreign key (r_code) references tbl_review(r_code),
    foreign key (s_code) references tbl_store(s_code),
    foreign key (u_code) references tbl_user(u_code),
    primary key(r_code, u_code)
);

select * from tbl_reply;
desc tbl_reply;


/*장바구니 테이블*/
drop table tbl_cart;

create table tbl_cart(
	u_code varchar(100) not null,
	s_code varchar(10) not null,
    m_name varchar(50) not null,
    amount int default 0,
    m_photo varchar(200),
    m_price int not null,
	foreign key (u_code) references tbl_user(u_code),
    foreign key (s_code) references tbl_store(s_code),
	primary key(u_code,s_code,m_name)
);

select * from tbl_cart;

desc tbl_cart;

/* 2022.11.09 (수) 추가함 */
/* 게시글 테이블 */

drop table tbl_post;

create table tbl_post(
	p_code int auto_increment primary key,
	c_code int,	
	s_code varchar(10) not null,
	u_code varchar(20) not null,	
	p_title varchar(100) not null,	
	p_content varchar(500),	
	p_date datetime default now(),
	p_headcount int default 1,	
	p_status boolean default false,
	foreign key (c_code) references tbl_condition (c_code),
	foreign key (s_code) references tbl_store (s_code),
	foreign key (u_code) references tbl_user (u_code)
);


desc tbl_post;

select  * from tbl_post;


/* 게시글 조건 테이블 */
drop table tbl_condition;

create table tbl_condition (
	c_code int auto_increment primary key,
	headcount int default 2,
	gender varchar(4) default '무관',
	age int
);

desc tbl_condition;

select  * from tbl_condition;


/* 주문 테이블 */

drop table tbl_order;

create table tbl_order (
	p_code int not null,						/* 어떤 게시글의 매칭인지 */
    u_code varchar(100) not null,
    token varchar(250) not null,
    o_status varchar(10) default '결제대기',		/* 결제대기 -> 조리중 -> 배달중 -> 배달완료 */
    primary key (p_code, u_code),
    foreign key (u_code) references tbl_user (u_code)
);

desc tbl_order;

select  * from tbl_order;



