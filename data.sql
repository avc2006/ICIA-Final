/*카테고리 테이블*/
insert into tbl_category(c_type) values('한식');
insert into tbl_category(c_type) values('일식');
insert into tbl_category(c_type) values('중식');
insert into tbl_category(c_type) values('양식');
insert into tbl_category(c_type) values('아시안');
insert into tbl_category(c_type) values('분식');
insert into tbl_category(c_type) values('패스트푸드');
insert into tbl_category(c_type) values('디저트');
insert into tbl_category(c_type) values('기타');

/*유저 타입 테이블*/
insert into tbl_user_type(u_type) values('관리자');
insert into tbl_user_type(u_type) values('점주');
insert into tbl_user_type(u_type) values('일반사용자');
insert into tbl_user_type(u_type) values('배달');


/*등급 테이블*/
insert into tbl_grade(standard,grade) values(0,'동');
insert into tbl_grade(standard,grade) values(100,'은');
insert into tbl_grade(standard,grade) values(200,'금');

/* 유저 테이블 */
/*age,gender,token,birthday 필요*/
insert into tbl_user(u_code,u_name,u_address,u_id,u_pass)
values('d0', '삭제된 사용자 입니다.', '삭제된 사용자 입니다.', 'delete', 'pass');

insert into tbl_user(u_code,u_name,u_address,u_id,u_pass)
values('a1', '관리자1', '관리자1', 'admin1', 'pass');

insert into tbl_user(u_code,u_name,u_address,u_id,u_pass)
values('a2', '관리자2', '관리자2', 'admin2', 'pass');

update tbl_user set u_type=1 where u_code='d0';
update tbl_user set u_type=1 where u_code='a1';
update tbl_user set u_type=1 where u_code='a2';

insert into tbl_user(u_code,u_name,u_address,u_id,u_pass)
values('u1', '김한식', '인천시 미추홀구 문학동', 'red', 'pass');

insert into tbl_user(u_code,u_name,u_address,u_id,u_pass)	
values('u2', '이일식', '대구시 수성구 연호동', 'orange', 'pass');

insert into tbl_user(u_code,u_name,u_address,u_id,u_pass)
values('u3', '박중식', '광주시 북구 임동', 'yellow', 'pass');

insert into tbl_user(u_code,u_name,u_address,u_id,u_pass)
values('u4', '최양식', '인천시 미추홀구 학익동', 'green', 'pass');

insert into tbl_user(u_code,u_name,u_address,u_id,u_pass)
values('u5', '아시안', '인천시 미추홀구 문학동', 'blue', 'pass');

insert into tbl_user(u_code,u_name,u_address,u_id,u_pass)
values('u6', '강분식', '대구시 수성구 연호동', 'navy', 'pass');

insert into tbl_user(u_code,u_name,u_address,u_id,u_pass)
values('u7', '홍길동', '광주시 북구 임동', 'purple', 'pass');

insert into tbl_user(u_code,u_name,u_address,u_id,u_pass)
values('u8', '강감찬', '인천시 미추홀구 학익동', 'black', 'pass');

insert into tbl_user(u_code,u_name,u_address,u_id,u_pass)
values('u9', '심청이', '인천시 미추홀구 문학동', 'white', 'pass');

insert into tbl_user(u_code,u_name,u_address,u_id,u_pass)
values('u10', '나점주', '대구시 수성구 연호동', 'bronze', 'pass');

insert into tbl_user(u_code,u_name,u_address,u_id,u_pass)
values('u11', '김점주', '광주시 북구 임동', 'silver', 'pass');

insert into tbl_user(u_code,u_name,u_address,u_id,u_pass)
values('u12', '이점주', '인천시 미추홀구 학익동', 'paltinum', 'pass');

insert into tbl_user(u_code,u_name,u_address,u_id,u_pass)
values('u13', '최한식', '인천시 미추홀구 문학동', 'diamond', 'pass');

update tbl_user set u_type=2 where u_code like '%u%';
/*---------------------------일반사용자(앱에서 가입)------------------------------------*/
/*
insert into tbl_user(u_code,u_name,u_address,u_id,u_pass)
values('u14', '김유저', '인천시 미추홀구 학익동', 'user1', 'pass');

insert into tbl_user(u_code,u_name,u_address,u_id,u_pass)
values('u15', '이유저', '인천시 미추홀구 학익동', 'user2', 'pass');

insert into tbl_user(u_code,u_name,u_address,u_id,u_pass)
values('u16', '최유저', '인천시 미추홀구 주안동', 'user3', 'pass');

insert into tbl_user(u_code,u_name,u_address,u_id,u_pass)
values('u17', '박유저', '인천시 미추홀구 문학동', 'user4', 'pass');

insert into tbl_user(u_code,u_name,u_address,u_id,u_pass)
values('u18', '강유저', '인천시 남동구 구월동', 'user5', 'pass');*/
/*---------------------------------------------------------------*/

/*가게 테이블*/
insert into tbl_store(s_code, s_name, s_location, s_tel,s_c_code, s_photo, s_admin,u_id,u_pass,x,y,minimal,cost)
values('s1','롯데리아 인천학익점','인천 미추홀구 매소홀로 466','032-873-4115',7,'/upload/store/photo/s1.png','홍길동','purple',(select u_pass from tbl_user where u_id='purple'),37.4395 ,126.6736,10000,3000);

insert into tbl_store(s_code, s_name, s_location, s_tel,s_c_code, s_photo, s_admin,u_id,u_pass,x,y,minimal,cost)
values('s2','육심','인천 미추홀구 소성로 251','032-873-2564',1,'/upload/store/photo/s2.png','김한식','red',(select u_pass from tbl_user where u_id='red'),37.438494 ,126.675656,10000,3000);

insert into tbl_store(s_code, s_name, s_location, s_tel,s_c_code, s_photo, s_admin,u_id,u_pass,x,y,minimal,cost)
values('s3','안짱스시','인천 미추홀구 매소홀로 429','032-866-4523',2,'/upload/store/photo/s3.jpg','이일식','orange',(select u_pass from tbl_user where u_id='orange'),37.4401 ,126.6697,10000,3000);

insert into tbl_store(s_code, s_name, s_location, s_tel,s_c_code, s_photo, s_admin,u_id,u_pass,x,y,minimal,cost)
values('s4','칭칭차이나 문학점','인천 미추홀구 미추홀대로 486-31','032-432-2221',3,'/upload/store/photo/s4.jpg','박중식','yellow',(select u_pass from tbl_user where u_id='yellow'),37.4409 ,126.6787,10000,3000);

insert into tbl_store(s_code, s_name, s_location, s_tel,s_c_code, s_photo, s_admin,u_id,u_pass,x,y,minimal,cost)
values('s5','쿡대디','인천 미추홀구 소성로 191 1층','0507-1313-8454',4,'/upload/store/photo/s5.jpg','최양식','green',(select u_pass from tbl_user where u_id='green'),37.4442 ,126.654,10000,3000);

insert into tbl_store(s_code, s_name, s_location, s_tel,s_c_code, s_photo, s_admin,u_id,u_pass,x,y,minimal,cost)
values('s6','대나무베트남명품쌀국수 학익점','인천 미추홀구 소성로 192 1층 대나무베트남쌀국수','032-876-2627',5,'/upload/store/photo/s6.jpg','아시안','blue',(select u_pass from tbl_user where u_id='blue'),37.4407 ,126.6693,10000,3000);

insert into tbl_store(s_code, s_name, s_location, s_tel,s_c_code, s_photo, s_admin,u_id,u_pass,x,y,minimal,cost)
values('s7','소끔만두','인천 미추홀구 매소홀로 474 수빈빌딩 105호','0507-1342-3992',6,'/upload/store/photo/s7.jpg','강분식','navy',(select u_pass from tbl_user where u_id='navy'),37.439382 ,126.674759,10000,3000);

insert into tbl_store(s_code, s_name, s_location, s_tel,s_c_code, s_photo, s_admin,u_id,u_pass,x,y,minimal,cost)
values('s8','메가MGC커피 인천학익신동아점','인천 미추홀구 매소홀로 476','032-875-0126',8,'/upload/store/photo/s8.jpg','강감찬','black',(select u_pass from tbl_user where u_id='black'),37.439188 ,126.675028,10000,3000);

insert into tbl_store(s_code, s_name, s_location, s_tel,s_c_code, s_photo, s_admin,u_id,u_pass,x,y,minimal,cost)
values('s9','두찜 학익점','인천 미추홀구 매소홀로 470','0507-1408-3047',1,'/upload/store/photo/s9.jpg','심청이','white',(select u_pass from tbl_user where u_id='white'),37.439258 ,126.674227,10000,3000);

insert into tbl_store(s_code, s_name, s_location, s_tel,s_c_code, s_photo, s_admin,u_id,u_pass,x,y,minimal,cost)
values('s10','약선당감자탕 학익점','인천 미추홀구 매소홀로 446','032-863-7725',1,'/upload/store/photo/s10.jpg','나점주','bronze',(select u_pass from tbl_user where u_id='bronze'),37.439460 ,126.671653,10000,3000);

insert into tbl_store(s_code, s_name, s_location, s_tel,s_c_code, s_photo, s_admin,u_id,u_pass,x,y,minimal,cost)
values('s11','숯골그집쭈꾸미','인천 미추홀구 학익소로 29 석목빌딩 106호~107호','032-862-6418',1,'/upload/store/photo/s11.jpg','김점주','silver',(select u_pass from tbl_user where u_id='silver'),37.444819 ,126.670045,10000,3000);

insert into tbl_store(s_code, s_name, s_location, s_tel,s_c_code, s_photo, s_admin,u_id,u_pass,x,y,minimal,cost)
values('s12','명륜진사갈비 인천학익점','인천 미추홀구 소성로 153','032-876-1402',1,'/upload/store/photo/s12.jpg','이점주','paltinum',(select u_pass from tbl_user where u_id='paltinum'),37.442533 ,126.665652,10000,3000);

insert into tbl_store(s_code, s_name, s_location, s_tel,s_c_code, s_photo, s_admin,u_id,u_pass,x,y,minimal,cost)
values('s13','진천순대','인천 미추홀구 소성로 314','032-421-0053',1,'/upload/store/photo/s13.jpg','최한식','diamond',(select u_pass from tbl_user where u_id='diamond'),37.437511 ,126.682000,10000,3000);


/*메뉴 테이블*/
insert into tbl_menu(s_code,m_name,m_price,m_photo,m_code) values('s1', '힙&핫치킨버거 세트', 8300,'/upload/menu/m1.jpg',1);
insert into tbl_menu(s_code,m_name,m_price,m_photo,m_code) values('s1', '더블 한우불고기 세트', 13700,'/upload/menu/m2.jpg',2);
insert into tbl_menu(s_code,m_name,m_price,m_photo,m_code) values('s1', '한우트러플머쉬룸', 9300,'/upload/menu/m3.jpg',3);

insert into tbl_menu(s_code,m_name,m_price,m_code) values('s2', '삼겹살', 9000,1);
insert into tbl_menu(s_code,m_name,m_price,m_code) values('s2', '항정살', 9000,2);
insert into tbl_menu(s_code,m_name,m_price,m_code) values('s2', '우삼겹', 9000,3);
insert into tbl_menu(s_code,m_name,m_price,m_code) values('s2', '목살', 9000,4);
insert into tbl_menu(s_code,m_name,m_price,m_code) values('s2', '소등심', 12000,5);

insert into tbl_menu(s_code,m_name,m_price,m_photo,m_code) values('s3', '연어명란(12pcs)', 20000,'/upload/menu/m4.jpg',1);
insert into tbl_menu(s_code,m_name,m_price,m_photo,m_code) values('s3', '특모듬초밥(14pcs)', 20000,'/upload/menu/m5.jpg',2);
insert into tbl_menu(s_code,m_name,m_price,m_code) values('s3', '추천초밥(15개)', 27000,3);

insert into tbl_menu(s_code,m_name,m_price,m_photo,m_code) values('s4', '옛날고기짬뽕(보통)', 10000,'/upload/menu/m6.jpg',1);
insert into tbl_menu(s_code,m_name,m_price,m_photo,m_code) values('s4', '칭칭쟁반짜장', 8000,'/upload/menu/m7.jpg',2);
insert into tbl_menu(s_code,m_name,m_price,m_photo,m_code) values('s4', '탕수육(소)', 17000,'/upload/menu/m8.jpg',3);

insert into tbl_menu(s_code,m_name,m_price,m_photo,m_code) values('s5', '베이컨까르보나라', 8900,'/upload/menu/m9.jpg',1);
insert into tbl_menu(s_code,m_name,m_price,m_photo,m_code) values('s5', '쭈새 빠네', 15000,'/upload/menu/m10.jpg',2);
insert into tbl_menu(s_code,m_name,m_price,m_photo,m_code) values('s5', '쉬림프 로제 파스타', 11900,'/upload/menu/m11.jpg',3);

insert into tbl_menu(s_code,m_name,m_price,m_photo,m_code) values('s6', '소고기 쌀국수(기본(Pho))', 9000,'/upload/menu/m12.jpg',1);
insert into tbl_menu(s_code,m_name,m_price,m_photo,m_code) values('s6', '고기구이국수(분짜)', 11000,'/upload/menu/m13.jpg',2);
insert into tbl_menu(s_code,m_name,m_price,m_photo,m_code) values('s6', '짜조(6개)', 7000,'/upload/menu/m14.jpg',3);

insert into tbl_menu(s_code,m_name,m_price,m_photo,m_code) values('s7', '고기만두', 4500,'/upload/menu/m15.jpg',1);
insert into tbl_menu(s_code,m_name,m_price,m_photo,m_code) values('s7', '김치만두', 4500,'/upload/menu/m16.jpg',2);
insert into tbl_menu(s_code,m_name,m_price,m_photo,m_code) values('s7', '새우만두', 4500,'/upload/menu/m17.jpg',3);

insert into tbl_menu(s_code,m_name,m_price,m_photo,m_code) values('s8', '아메리카노', 1500,'/upload/menu/m18.jpg',1);
insert into tbl_menu(s_code,m_name,m_price,m_photo,m_code) values('s8', '퐁크러쉬', 3900,'/upload/menu/m19.jpg',2);
insert into tbl_menu(s_code,m_name,m_price,m_photo,m_code) values('s8', '딸기라뗴', 3500,'/upload/menu/m20.jpg',3);

insert into tbl_menu(s_code,m_name,m_price,m_photo,m_code) values('s9', '트러플크림찜닭', 31800,'/upload/menu/m21.jpg',1);
insert into tbl_menu(s_code,m_name,m_price,m_photo,m_code) values('s9', '불마왕불닭', 31800,'/upload/menu/m22.jpg',2);
insert into tbl_menu(s_code,m_name,m_price,m_photo,m_code) values('s9', '까만/빨간찜닭두마리', 38800,'/upload/menu/m23.jpg',3);
insert into tbl_menu(s_code,m_name,m_price,m_code) values('s9', '한마리찜닭', 25800,4);
insert into tbl_menu(s_code,m_name,m_price,m_code) values('s9', '로제찜닭', 29800,5);

insert into tbl_menu(s_code,m_name,m_price,m_photo,m_code) values('s10', '감자탕(대)', 42000,'/upload/menu/m24.jpg',1);
insert into tbl_menu(s_code,m_name,m_price,m_photo,m_code) values('s10', '등뼈찜(순한맛)(대)', 53000,'/upload/menu/m25.jpg',2);
insert into tbl_menu(s_code,m_name,m_price,m_photo,m_code) values('s10', '약선삼계탕', 14000,'/upload/menu/m26.jpg',3);
insert into tbl_menu(s_code,m_name,m_price,m_code) values('s10', '뼈해장국', 9000,4);
insert into tbl_menu(s_code,m_name,m_price,m_code) values('s10', '돈가스', 9000,5);

insert into tbl_menu(s_code,m_name,m_price,m_photo,m_code) values('s11', '쭈꾸미볶음(1인분)', 8900,'/upload/menu/m27.jpg',1);
insert into tbl_menu(s_code,m_name,m_price,m_photo,m_code) values('s11', '삼겹살볶음(1인분)', 8900,'/upload/menu/m28.jpg',2);
insert into tbl_menu(s_code,m_name,m_price,m_photo,m_code) values('s11', '생등심돈까스(국내산)', 9000,'/upload/menu/m29.jpg',3);
insert into tbl_menu(s_code,m_name,m_price,m_code) values('s11', '왕새우튀김(4마리)', 9500,4);
insert into tbl_menu(s_code,m_name,m_price,m_code) values('s11', '해물파전(대 사이즈)', 16000,5);

insert into tbl_menu(s_code,m_name,m_price,m_photo,m_code) values('s12', '무한리필 숯불돼지갈비', 16500,'/upload/menu/m30.jpg',1);
insert into tbl_menu(s_code,m_name,m_price,m_photo,m_code) values('s12', '소갈비살', 7000,'/upload/menu/m31.jpg',2);
insert into tbl_menu(s_code,m_name,m_price,m_photo,m_code) values('s12', '돼지껍데기', 3000,'/upload/menu/m32.jpg',3);

insert into tbl_menu(s_code,m_name,m_price,m_code) values('s13', '순대국', 8000,1);
insert into tbl_menu(s_code,m_name,m_price,m_code) values('s13', '매운순대국', 8500,2);
insert into tbl_menu(s_code,m_name,m_price,m_code) values('s13', '모듬전골 대', 32000,3);
insert into tbl_menu(s_code,m_name,m_price,m_code) values('s13', '곱창전골 대', 31000,4);
insert into tbl_menu(s_code,m_name,m_price,m_code) values('s13', '곱창볶음 1인분', 13000,5);


/*즐겨찾기 테이블 (앱에서 가입 후 등록)*/
insert into tbl_liked(u_code,s_code) values('u14', 's1');
insert into tbl_liked(u_code,s_code) values('u14', 's3');
insert into tbl_liked(u_code,s_code) values('u14', 's5');
insert into tbl_liked(u_code,s_code) values('u14', 's7');
insert into tbl_liked(u_code,s_code) values('u14', 's9');
insert into tbl_liked(u_code,s_code) values('u14', 's11');

insert into tbl_liked(u_code,s_code) values('u15', 's2');
insert into tbl_liked(u_code,s_code) values('u15', 's4');
insert into tbl_liked(u_code,s_code) values('u15', 's6');

insert into tbl_liked(u_code,s_code) values('u16', 's3');
insert into tbl_liked(u_code,s_code) values('u16', 's6');
insert into tbl_liked(u_code,s_code) values('u16', 's9');

insert into tbl_liked(u_code,s_code) values('u17', 's5');
insert into tbl_liked(u_code,s_code) values('u17', 's10');
insert into tbl_liked(u_code,s_code) values('u17', 's13');

insert into tbl_liked(u_code,s_code) values('u18', 's13');
insert into tbl_liked(u_code,s_code) values('u18', 's12');
insert into tbl_liked(u_code,s_code) values('u18', 's11');
insert into tbl_liked(u_code,s_code) values('u18', 's1');



/*신고 테이블*/
insert into tbl_report(r_code,suer,defender,r_result, r_type) values('r1', 'u14', 'u15', '신고 사유를 적는 테스트입니다.', '지각');
insert into tbl_report(r_code,suer,defender,r_result, r_type) values('r2', 'u15', 'u16', '신고 사유가 잘 전달되고 있나요?', '지각');
insert into tbl_report(r_code,suer,defender,r_result, r_type) values('r3', 'u14', 'u16', '욕설 신고합니다!', '욕설');

/*리뷰 테이블*/
insert into tbl_review(s_code,u_code, r_content, r_photo, r_rating,r_code) values('s10', 'u1', '추천합니다', '/upload/menu/m24.jpg', '4.5','s10r1');
insert into tbl_review(s_code,u_code, r_content, r_photo, r_rating,r_code) values('s10', 'u2', '맛있습니다', '/upload/menu/m25.jpg', '5.0','s10r2');
insert into tbl_review(s_code,u_code, r_content, r_photo, r_rating,r_code) values('s10', 'u3', 'Good', '/upload/menu/m26.jpg', '4.0','s10r3');


insert into tbl_review(s_code,u_code, r_content, r_photo, r_rating,r_code) values('s10', 'u14', '추천합니다.', '/upload/menu/m24.jpg', '4.5','s10r5');
insert into tbl_review(s_code,u_code, r_content, r_photo, r_rating,r_code) values('s8', 'u14', '맛있습니다.', '/upload/menu/m24.jpg', '4.5','s8r1');
insert into tbl_review(s_code,u_code, r_content, r_photo, r_rating,r_code) values('s9', 'u14', '배달이 빨라요', '/upload/menu/m24.jpg', '4.5','s9r1');
insert into tbl_review(s_code,u_code, r_content, r_photo, r_rating,r_code) values('s10', 'u14', '감사합니다', '/upload/menu/m24.jpg', '4.5','s10r1');
insert into tbl_review(s_code,u_code, r_content, r_photo, r_rating,r_code) values('s11', 'u14', '잘먹었습니다', '/upload/menu/m24.jpg', '4.5','s11r1');


insert into tbl_review(s_code,u_code,r_rating,r_code) values('s3', 'u1', '5','s3r1');
insert into tbl_review(s_code,u_code,r_rating,r_code) values('s5', 'u1', '5','s5r1');
insert into tbl_review(s_code,u_code,r_rating,r_code) values('s7', 'u1', '4','s7r1');
insert into tbl_review(s_code,u_code,r_rating,r_code) values('s9', 'u1', '4.5','s9r1');
insert into tbl_review(s_code,u_code,r_rating,r_code) values('s11', 'u1', '5','s11r1');


insert into tbl_review(s_code,u_code,r_rating,r_code) values('s2', 'u2', '4','s2r1');
insert into tbl_review(s_code,u_code,r_rating,r_code) values('s4', 'u2', '4.5','s4r1');
insert into tbl_review(s_code,u_code,r_rating,r_code) values('s6', 'u2', '5','s6r1');
insert into tbl_review(s_code,u_code,r_rating,r_code) values('s8', 'u2', '5','s8r1');
insert into tbl_review(s_code,u_code,r_rating,r_code) values('s10', 'u2', '5','s10r1');
insert into tbl_review(s_code,u_code,r_rating,r_code) values('s12', 'u2', '5','s12r1');


insert into tbl_review(s_code,u_code,r_rating,r_code) values('s3', 'u3', '4','s3r2');
insert into tbl_review(s_code,u_code,r_rating,r_code) values('s6', 'u3', '4','s6r2');
insert into tbl_review(s_code,u_code,r_rating,r_code) values('s9', 'u3', '4','s9r2');


insert into tbl_review(s_code,u_code,r_rating,r_code) values('s5', 'u4', '4.5','s5r2');	
insert into tbl_review(s_code,u_code,r_rating,r_code) values('s13', 'u4', '5','s13r1');


insert into tbl_review(s_code,u_code,r_rating,r_code) values('s1', 'u5', '3','s1r2');
insert into tbl_review(s_code,u_code,r_rating,r_code) values('s2', 'u5', '2','s2r2');
insert into tbl_review(s_code,u_code,r_rating,r_code) values('s3', 'u5', '1','s3r3');

/*유저 삭제 테이블 (임의의 유저 등록)*/
insert into tbl_user_delete(d_code) values('u4');
update tbl_user set u_status=false where u_code='u4';


/* 게시글 테이블 (직접 게시글 등록)*/
/*
insert into tbl_post(u_code,s_code,m_name,m_photo) values('u13','s10','감자탕(大)','/upload/menu/m24.jpg');
insert into tbl_post(u_code,s_code,m_name,m_photo) values('u10','s10','감자탕(大)','/upload/menu/m24.jpg');
insert into tbl_post(u_code,s_code,m_name,m_photo) values('u11','s10','감자탕(大)','/upload/menu/m24.jpg');
insert into tbl_post(u_code,s_code,m_name,m_photo) values('u12','s10','감자탕(大)','/upload/menu/m24.jpg');

insert into tbl_post(u_code,s_code,m_name,m_photo) values('u13','s11','쭈꾸미볶음(1인분)','/upload/menu/m24.jpg');
insert into tbl_post(u_code,s_code,m_name,m_photo) values('u10','s11','쭈꾸미볶음(1인분)','/upload/menu/m24.jpg');
insert into tbl_post(u_code,s_code,m_name,m_photo) values('u11','s11','쭈꾸미볶음(1인분)','/upload/menu/m24.jpg');
insert into tbl_post(u_code,s_code,m_name,m_photo) values('u12','s11','쭈꾸미볶음(1인분)','/upload/menu/m24.jpg');
*/




