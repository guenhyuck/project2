
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head data-theme="light">
<meta charset="UTF-8">
<title>문화의 도시 대전</title>
<!-- 제이쿼리 불러오기 -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.1/jquery.min.js"></script>

<!-- 노드SCSS 불러오기 -->
<link rel="stylesheet" href="output.css">

<link rel="shortcut icon" href="/resource/favicon.ico" />

<!-- 테일윈드 불러오기 -->
<!-- <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/tailwindcss/2.2.7/tailwind.min.css" /> -->
<link href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2/dist/tailwind.min.css" rel="stylesheet" type="text/css" />

<!-- 데이지 UI -->
<link href="https://cdn.jsdelivr.net/npm/daisyui@2.51.5/dist/full.css" rel="stylesheet" type="text/css" />

<!-- 폰트어썸 불러오기 -->
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.2.0/css/all.min.css" />


<!-- 배경색 -->
<div class="gradient">
    <svg version="1.1" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink">
      <rect width="150%" height="150%" x="-65%" y="-65%" fill="url(#gradient1a)"></rect>
      <rect width="200%" height="200%" x="10%" y="-80%" fill="url(#gradient1b)"></rect>
      <rect width="200%" height="150%" x="-50%" y="30%" fill="url(#gradient1c)"></rect>
      <defs>
        <radialGradient id="gradient1a">
          <stop offset="0%" stop-color="#F2C9C9">
            <animate attributeName="stop-color" values="#F2C9C9;#FFFFFF;#DCD5F5;#FFE7C1;#FFFFFF;#FFE7C1;#F2C9C9" dur="20s" repeatCount="indefinite">
            </animate>
          </stop>
          <stop offset="100%" stop-color="#FFFFFF"></stop>
        </radialGradient>
        <radialGradient id="gradient1b">
          <stop offset="0%" stop-color="#CBEBF0">
            <animate attributeName="stop-color" values="#CBEBF0;#DCD5F5;#FFFFFF;#CBEBF0;#DCD5F5;#FFFFFF;#CBEBF0" dur="20s" repeatCount="indefinite">
            </animate>
          </stop>
          <stop offset="100%" stop-color="#FFFFFF" stop-opacity="0"></stop>
        </radialGradient>
        <radialGradient id="gradient1c">
          <stop offset="0%" stop-color="#FFFFFF">
            <animate attributeName="stop-color" values="#FFFFFF;#FFE7C1;#F2C9C9;#FFFFFF;#CBEBF0;#F2C9C9;#FFFFFF" dur="20s" repeatCount="indefinite">
            </animate>
          </stop>
          <stop offset="100%" stop-color="#FFFFFF" stop-opacity="0"></stop>
        </radialGradient>
      </defs>
    </svg>
  </div>
  
<!--   배경 css 부 -->
  
<style>
.gradient {
  position: fixed;
  height: 100%;
  width: 100%;
  overflow: hidden;
  z-index:  -2;
}
.gradient svg {
  position: absolute;
  top: 0;
  right: 0;
  bottom: 0;
  left: 0;
  width: 100%;
  height: 100%;
  z-index: -1;
}
</style>
<!-- 배경색끝 -->







<!-- sns아이콘 -->

<div class="icon_bts">
		<a href="https://www.instagram.com/daejeon_official/">
				<i class="fab fa-instagram"></i>
				인스타
		</a>
		<a href="https://www.facebook.com/daejeonstory/?locale=ko_KR">
				<i class="fab fa-facebook"></i>
				페북
		</a>
		<a href="https://www.daejeon.go.kr/">
				<i class="fas fa-home"></i>
				공홈
		</a>
		<a href="https://www.youtube.com/channel/UCzIgmDjE0lFDu2IhEF_ewOA">
				<i class="fa-brands fa-youtube"></i>
				유튜브
		</a>
</div>
<!-- sns아이콘 끝 -->




<!-- 카드 이미지 -->

<div class="main">
		<a href="/" class="card_image">
				<img class="first_image" src="https://www.daejeon.go.kr/images/drh/sub07/disu_img01.png">
		</a>
		<ul class="cards">
				<li class="cards_item">
						<div class="card">
								<div class="flex card_image">
										<img
												src="https://www.wellnessnculture.co.kr/news/photo/202110/3989_6566_5950.jpg">
								</div>
								<div class="card_content">
										<h2 class="card_title">대전 숙박</h2>
										<p class="card_text">"대전, 쉼터"</p>
										<a href="https://daejeontour.co.kr/ko/mapbase/mapbaseList.do?menuIdx=128&mapClId=roms" target="_blank" class="btn card_btn">바로 가기</a>
								</div>
						</div>
				</li>
				<li class="cards_item">
						<div class="card">
								<div class="card_image">
										<img
												src="https://img.freepik.com/free-photo/rainbow-fountain-show-at-expo-bridge-in-south-korea_335224-491.jpg?w=1380&t=st=1684468890~exp=1684469490~hmac=570872144e073c8a2c8d4fbcf91eaabf8456474ae42cb46274dcafb13f962cb1">
								</div>
								<div class="card_content">
										<h2 class="card_title">대전 축제 바로가기</h2>
										<p class="card_text">"대전, 문화로 만나는 새로운 세상"</p>
										<a href="https://daejeontour.co.kr/ko/festival/festivalList.do?menuIdx=147" target="_blank"  class="btn card_btn">바로 가기</a>
								</div>
						</div>
				</li>
				<li class="cards_item">
						<div class="card">
								<div class="card_image">
										<img
												src="https://img.freepik.com/premium-vector/different-kinds-of-city-and-intercity-public-transport-set-transportation-illustration-car-bus-and-truck-icons_263016-380.jpg">
								</div>
								<div class="card_content">
										<h2 class="card_title">대중 교통상황</h2>
										<p class="card_text">"대전의 교통"</p>
										<a href="http://traffic.daejeon.go.kr/mainFront/atmsMain.do" target="_blank"  class="btn card_btn">바로 가기</a>
								</div>
						</div>
				</li>
				<li class="cards_item">
						<div class="card">
								<div class="card_image">
										<img
												src="https://ak-d.tripcdn.com/images/1i61q2215as5snj2o8B1D.jpg?proc=source/trip">
								</div>
								<div class="card_content">
										<h2 class="card_title">대전의 맛집</h2>
										<p class="card_text">"대전 문화, 맛으로 느끼기"</p>
										<a href="https://daejeontour.co.kr/ko/mapbase/mapbaseList.do?menuIdx=126&mapClId=restrnt01" target="_blank"  class="btn card_btn">바로 가기</a>
								</div>
						</div>
				</li>
				<li class="cards_item">
						<div class="card">
								<div class="card_image">
										<img
												src="https://cdn.cctoday.co.kr/news/photo/202203/2159314_591447_2750.jpg">
								</div>
								<div class="card_content">
										<h2 class="card_title">대전 전통문화와 역사</h2>
										<p class="card_text">"대전 문화, 체험하며 즐기기"</p>
										<a href="https://daejeontour.co.kr/ko/page.do?menuIdx=155" target="_blank"  class="btn card_btn">바로 가기</a>
								</div>
						</div>
				</li>
				<li class="cards_item">
						<div class="card">
								<div class="card_image">
										<img
												src="https://mblogthumb-phinf.pstatic.net/MjAyMjEyMDlfMTkx/MDAxNjcwNTM3NjE3MzYz.49mbTxeEDY6aDt5b3X1gq_GSYBM1tf74y2YSzOujhlEg.Bn47zvOg1D5XP3T5irnP5whTAq1bTTU0RtEySR0ADSgg.JPEG.first_seogu/%EC%9E%A5%ED%83%9C%EC%82%B0%EC%9E%90%EC%97%B0%ED%9C%B4%EC%96%91%EB%A6%BC2.jpg?type=w800">
								</div>
								<div class="card_content">
										<h2 class="card_title">대전의 휴양지</h2>
										<p class="card_text">"휴식으로 되돌아보다"</p>
										<a href="https://daejeontour.co.kr/ko/mapbase/mapbaseList.do?menuIdx=118&mapClId=tour" target="_blank"  class="btn card_btn">바로 가기</a>
								</div>
						</div>
				</li>
		</ul>
</div>
<!-- 카드 이미지 끝-->

    <div class="slider fe">
        <div class="image">
            <input type="radio" name="input" id="img1" checked>
            <input type="radio" name="input" id="img2">
            <input type="radio" name="input" id="img3">
            <input type="radio" name="input" id="img4">

            <img src="https://a.top4top.io/p_19435rd9e1.jpg" alt="" class="m1">
            <img src="https://b.top4top.io/p_19431v2lm2.jpg" alt="" class="m2">
            <img src="https://c.top4top.io/p_19433ew8l3.jpg" alt="" class="m3">
            <img src="https://d.top4top.io/p_1943hmvlk4.jpg" alt="" class="m4">
        </div>
        <div class="label">
            <label for="img1"></label>
            <label for="img2"></label>
            <label for="img3"></label>
            <label for="img4"></label>
        </div>
    </div>

<style>



/* 커서 */
body {
  cursor: url("https://www.sungsimdangmall.co.kr/data/sungsimdang/goods/sungsimdang/other/IMG001.png"), auto;
}

/* Design */

*, *::before, *::after {
	box-sizing: border-box;
}

html {
	background-color: #ecf9ff;
}

body {
	color: #272727;
	font-family: 'Quicksand', serif;
	font-style: normal;
	font-weight: 400;
	letter-spacing: 0;
	padding: 1rem;
}

.main {
	max-width: 1200px;
	margin: 0 auto;
}

h1 {
	font-size: 24px;
	font-weight: 400;
	text-align: center;
}

img {
	height: auto;
	max-width: 100%;
	vertical-align: middle;
}

.main .cards_item .card img {
 height: 350px;
 width: 450px;

}

.btn {
	color: #ffffff;
	padding: 0.8rem;
	font-size: 14px;
	text-transform: uppercase;
	border-radius: 4px;
	font-weight: 400;
	display: block;
	width: 100%;
	cursor: pointer;
	border: 1px solid rgba(255, 255, 255, 0.2);
	background: transparent;
}

.btn:hover {
	background-color: rgba(255, 255, 255, 0.12);
}

.cards {
	display: flex;
	flex-wrap: wrap;
	list-style: none;
	margin: 0;
	padding: 0;
}

.cards_item {
	display: flex;
	padding: 1rem;
	border-radius: 33%;
	
}

@media ( min-width : 40rem) {
	.cards_item {
		width: 50%;
	}
}

@media ( min-width : 56rem) {
	.cards_item {
		width: 33.3333%;
	}
}

.card {

	background-color: white;
	border-radius: 0.25rem;
	box-shadow: 0 20px 40px -14px rgba(0, 0, 0, 0.25);
	display: flex;
	flex-direction: column;
	overflow: hidden;
}

.card_content {
	padding: 1rem;
 	background: #53B597;
}

.card_title {
	color: #ffffff;
	font-size: 1.1rem;
	font-weight: 700;
	letter-spacing: 1px;
	text-transform: capitalize;
	margin: 0px;
}

.card_text {
	color: #ffffff;
	font-size: 0.875rem;
	line-height: 1.5;
	margin-bottom: 1.25rem;
	font-weight: 400;
}

.card_image {

	display: flex;
	justify-content: center;
}

.made_by {
	font-weight: 400;
	font-size: 13px;
	margin-top: 35px;
	text-align: center;
}

.icon_bts div {
	display: flex;
	align-items: center;
}

.icon_bts i {
	margin-right: 5px;
}


	@keyframes blink {
		0% { color: red; }
		50% { color: black; }
		100% { color: red; }
	}

	.icon_bts a:hover {
		animation: blink 1s infinite;
	}
	.div .card_image>img{
	-webkit-animation: pulse 1.5s infinite;
	
	}
.main .card_image .first_image{
  border-radius: 45px;
  letter-spacing: 5px;
  font-weight: 500;
  transition: all 0.5s ease 0s;
  cursor: pointer;
  }

.main .card_image .first_image:hover{
  background-color: #2EE59D;
  box-shadow: 0px 15px 20px rgba(46, 229, 157, 0.4);
/*   color: #fff; */
  transform: translateY(-14px);
}




/* bgi 테스트용 . 화면 밑에 구성 되어있음 */

.slider {
    padding: 0;
    margin: 0;
    box-sizing: border-box;
    font-family: 'Cairo', sans-serif;
}

.slider {
    width: 600px;
    height: 450px;
    margin: 100px auto;
    border-radius: 10px;
    -webkit-border-radius: 10px;
    -moz-border-radius: 10px;
    -ms-border-radius: 10px;
    -o-border-radius: 10px;
}

.slider .image {
    position: relative;
}

.slider .image img {
    width: 100%;
    height: 400px;
    position: absolute;
    top: 0;
    left: 0;
    opacity: 0;
    transition: .5s linear;
    -webkit-transition: .5s linear;
    -moz-transition: .5s linear;
    -ms-transition: .5s linear;
    -o-transition: .5s linear;
    border-radius: 10px;
    -webkit-border-radius: 10px;
    -moz-border-radius: 10px;
    -ms-border-radius: 10px;
    -o-border-radius: 10px;
    animation-name: slider;
    animation-duration: 15s;
}

.slider .image input {
    display: none;
}

.label {
    display: flex;
    justify-content: center;
    height: 100%;
    align-items: flex-end;
  
}

.label label {
    width: 50px;
    height: 20px;
    border: 3px solid #222;
    margin: 5px;
    cursor: pointer;
    border-radius: 15%;
}

.label label:hover {
    background-color: #222;
}

#img1:checked~.m1 {
    opacity: 1;
}

#img2:checked~.m2 {
    opacity: 1;
}

#img3:checked~.m3 {
    opacity: 1;
}

#img4:checked~.m4 {
    opacity: 1;
}


</style>