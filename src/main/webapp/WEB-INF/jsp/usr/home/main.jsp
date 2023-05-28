<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="대전 문화 관광" />
<%@ include file="../common/head.jspf"%>
<!-- 제이쿼리 불러오기 -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.1/jquery.min.js"></script>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

<!-- 슬라이드 효과자리 -->

<script>
  $(document).ready(function() {
    setInterval(function() {
      var current = $('.slider.fe .image input:checked');
      var next = current.next('input');
      var currentLabel = $('.slider.fe .label label[for="' + current.attr('id') + '"]');
      var nextLabel = currentLabel.next('label');

      if (next.length === 0) {
        next = $('.slider.fe .image input:first-child');
        nextLabel = $('.slider.fe .label label:first-child');
      }

      current.removeAttr('checked');
      next.prop('checked', true);

      currentLabel.removeClass('active');
      nextLabel.addClass('active');
    }, 3000); // 3초마다 다음 이미지로 전환 (원하는 시간 간격으로 변경 가능)
  });
</script>


<!-- 슬라이더2 js -->

<script>
$(document).ready(function() {
	  // 슬라이드 요소들을 변수에 할당
	  var options = $(".options .option");
	  var activeOptionIndex = 0; // 현재 활성화된 슬라이드의 인덱스

	  // 이미지 클릭 시 다음 이미지로 슬라이드
	  options.click(function() {
	    options.removeClass("active"); // 모든 슬라이드의 'active' 클래스 제거
	    $(this).addClass("active"); // 클릭한 슬라이드에 'active' 클래스 추가

	    activeOptionIndex = $(this).index(); // 클릭한 슬라이드의 인덱스를 가져옴
	  });

	  // 다음 이미지로 슬라이드하는 함수
	  function slideToNextImage() {
	    options.removeClass("active"); // 모든 슬라이드의 'active' 클래스 제거
	    activeOptionIndex = (activeOptionIndex + 1) % options.length; // 다음 슬라이드의 인덱스 계산
	    options.eq(activeOptionIndex).addClass("active"); // 다음 슬라이드에 'active' 클래스 추가
	  }

	  // 이미지 클릭 시 다음 이미지로 슬라이드되도록 이벤트 리스너 등록
	  options.on("click", slideToNextImage);


	});

</script>


<hr />






<!-- 변경사항 -->




<div class="#">대전의 실시간 축제는??</div>
<div class="layer-bg"></div>
<div class="layer">
		<div class="flex justify-between">
				<div class="close-btn">
						
						<div></div>
				</div>
		</div>
		
	<!-- 	팝업 구현부 -->
		
		<div>
		
		
		</div>

</div>

<!-- 슬라이더 부  -->
    <div class="slider fe">
        <div class="image">
            <input type="radio" name="input" id="img1" checked>
            <input type="radio" name="input" id="img2">
            <input type="radio" name="input" id="img3">
            <input type="radio" name="input" id="img4">
           
             
            <img src="https://daejeontour.co.kr/ko/atch/atchFileStreamOut.do?atchId=1346&fileSeCode=MAIN_IMAGE_FILE" alt="" class="m1">
            <img src="https://daejeontour.co.kr/ko/atch/atchFileStreamOut.do?atchId=1344&fileSeCode=MAIN_IMAGE_FILE" alt="" class="m2">
            <img src="https://daejeontour.co.kr/ko/atch/atchFileStreamOut.do?atchId=1341&fileSeCode=MAIN_IMAGE_FILE" alt="" class="m3">
            <img src="https://daejeontour.co.kr/ko/atch/atchFileStreamOut.do?atchId=1339&fileSeCode=MAIN_IMAGE_FILE" alt="" class="m4">
        </div>
        <div class="label">
            <label for="img1"></label>
            <label for="img2"></label>
            <label for="img3"></label>
            <label for="img4"></label>
        </div>
    </div>
		
<style>	

.slider.fe .label label {
  display: inline-block;
  width: 10px;
  height: 10px;
  background-color: #ccc;
  border-radius: 50%;
  margin: 0 5px;
  cursor: pointer;
}

.slider.fe .label label:hover,
.slider.fe .label input:checked + label {
  background-color: #f00;
}

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
    height: 500px;
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



				
			<!-- 	아이콘 빨리가기 -->
				
<hr class="line">
				<div class="logo_2">
						<em class="blind">대전광역시 빨리보기</em>
				</div>
		</div>
</div>

<div class="container">

<div class="cslider">
		

		<a href="/usr/home/APITest2" class="fa-solid fa-map-location-dot">위치찾기</a>
		
		<a href="https://www.daejeon.go.kr/fod/index.do" target="_blank"  class="fa-solid fa-bowl-food">맛집 추천</a>
		
		<a href="http://traffic.daejeon.go.kr/mainFront/atmsMain.do" target="_blank"  class="fa-solid fa-car">교통 정보</a>
		
		<a href="https://daejeontour.co.kr/ko/mapbase/mapbaseList.do?menuIdx=128&mapClId=roms target="_blank"  class="fa-solid fa-bed">숙박 시설</a>
		</div>
       

</div>
<hr class="line">

   
<hr class="line">



<!-- 슬라이드2 구현부 -->

<div class="#">대전의 다양한 볼거리들</div>
<div class="main2">

<div class="options">
   <div class="option active" style="--optionBackground:url(https://daejeontour.co.kr/boardImageStreamOut.do?file_idx=143);">
      <div class="shadow"></div>
      <div class="label">
         <div class="icon">
            <i class="fas fa-walking"></i>
         </div>
         <div class="info">
            <div class="main">#가을 풍경</div>
            <div class="sub">대전의 아름다운 가을</div>
         </div>
      </div>
   </div>
   <div class="option" style="--optionBackground:url(https://daejeontour.co.kr/boardImageStreamOut.do?file_idx=141);">
      <div class="shadow"></div>
      <div class="label">
         <div class="icon">
            <i class="fas fa-snowflake"></i>
         </div>
         <div class="info">
            <div class="main">#역사 탐방</div>
            <div class="sub">유서 깊은 대전의 유적지</div>
         </div>
      </div>
   </div>
   <div class="option" style="--optionBackground:url(https://daejeontour.co.kr/boardImageStreamOut.do?file_idx=140);">
      <div class="shadow"></div>
      <div class="label">
         <div class="icon">
            <i class="fas fa-tree"></i>
         </div>
         <div class="info">
            <div class="main">#장태산 휴양림</div>
            <div class="sub">도시 속에서 만나는 힐링a</div>
         </div>
      </div>
   </div>
   <div class="option" style="--optionBackground:url(https://daejeontour.co.kr/boardImageStreamOut.do?file_idx=146);">
      <div class="shadow"></div>
      <div class="label">
         <div class="icon">
            <i class="fas fa-tint"></i>
         </div>
         <div class="info">
            <div class="main">#계족 산성</div>
            <div class="sub">자연미가 주는 아름다움</div>
         </div>
      </div>
   </div>
   <div class="option" style="--optionBackground:url(https://daejeontour.co.kr/boardImageStreamOut.do?file_idx=142);">
      <div class="shadow"></div>
      <div class="label">
         <div class="icon">
            <i class="fas fa-sun"></i>
         </div>
         <div class="info">
            <div class="main">#한밭 수목원</div>
            <div class="sub">대전 시내 최대규모의 수목원</div>
         </div>
      </div>
   </div>
</div>

</div>

<div class ="flex  justify-content: center;
  align-items: center;">대전의 정취를 느껴보세요</div>
<style>

/* 아이콘 구현부 */
.cslider{

font-size: 40px;
}
.cslider a{
margin-left: 5%;

}
.slider2 .container .fa-solid {
	
	flex-direction: row;
	font-size: 4rem;
}

.slider2 .container .fa-solid > * {
 
	margin-right: 20px; /* 원하는 간격 설정 */
}

.slider2 .container{
  display: flex;
  justify-content: center; /* 가로 중앙 정렬 */
  align-items: center; /* 세로 중앙 정렬 */
}
.slider2 .container a {
  display: flex justify-between;
  align-items: center; /* a 요소들을 세로 중앙 정렬 */
  margin-right: 20px; /* 간격 설정 */
}

.slider2 .container a:last-child {
  margin-right: 0; /* 마지막 a 요소에는 간격을 주지 않음 */
}


/* 슬라이드2 구현부 */
.main2 {
    display: flex;
    flex-direction: row;
    justify-content: center;
    align-items: center;
    overflow: hidden;
    height: 100vh;
    font-family: 'Roboto', sans-serif;
    transition: .25s;
}
.main2  .credit {
    position: absolute;
    bottom: 20px;
    left: 20px;
    color: inherit;
}
.main2  .options {
    display: flex;
    flex-direction: row;
    align-items: stretch;
    overflow: hidden;
    min-width: 600px;
    max-width: 900px;
    width: calc(100% - 100px);
    height: 400px;
}
@media screen and (max-width: 718px) {
    .main2  .options {
        min-width: 520px;
    }
    .main2  .options .option:nth-child(5) {
        display: none;
    }
}
@media screen and (max-width: 638px) {
    .main2  .options {
        min-width: 440px;
    }
    .main2  .options .option:nth-child(4) {
        display: none;
    }
}
@media screen and (max-width: 558px) {
    .main2  .options {
        min-width: 360px;
    }
    .main2  .options .option:nth-child(3) {
        display: none;
    }
}
@media screen and (max-width: 478px) {
    .main2  .options {
        min-width: 280px;
    }
    .main2  .options .option:nth-child(2) {
        display: none;
    }
}
.main2  .options .option {
    position: relative;
    overflow: hidden;
    min-width: 60px;
    margin: 10px;
    background: var(--optionBackground, var(--defaultBackground, #E6E9ED));
    background-size: auto 120%;
    background-position: center;
    cursor: pointer;
    transition: 0.5s cubic-bezier(0.05, 0.61, 0.41, 0.95);
}
.main2  .options .option:nth-child(1) {
    --defaultBackground:#ED5565;
}
.main2  .options .option:nth-child(2) {
    --defaultBackground:#FC6E51;
}
.main2  .options .option:nth-child(3) {
    --defaultBackground:#FFCE54;
}
.main2  .options .option:nth-child(4) {
    --defaultBackground:#2ECC71;
}
.main2  .options .option:nth-child(5) {
    --defaultBackground:#5D9CEC;
}
.main2  .options .option:nth-child(6) {
    --defaultBackground:#AC92EC;
}
.main2  .options .option.active {
    flex-grow: 10000;
    transform: scale(1);
    max-width: 600px;
    margin: 0px;
    border-radius: 40px;
    background-size: auto 100%;
}
.main2  .options .option.active .shadow {
    box-shadow: inset 0 -120px 120px -120px black, inset 0 -120px 120px -100px black;
}
.main2  .options .option.active .label {
    bottom: 20px;
    left: 20px;
}
.main2  .options .option.active .label .info > div {
    left: 0px;
    opacity: 1;
}
.main2  .options .option:not(.active) {
    flex-grow: 1;
    border-radius: 30px;
}
.main2  .options .option:not(.active) .shadow {
    bottom: -40px;
    box-shadow: inset 0 -120px 0px -120px black, inset 0 -120px 0px -100px black;
}
.main2  .options .option:not(.active) .label {
    bottom: 10px;
    left: 10px;
}
.main2  .options .option:not(.active) .label .info > div {
    left: 20px;
    opacity: 0;
}
.main2  .options .option .shadow {
    position: absolute;
    bottom: 0px;
    left: 0px;
    right: 0px;
    height: 120px;
    transition: 0.5s cubic-bezier(0.05, 0.61, 0.41, 0.95);
}
.main2  .options .option .label {
    display: flex;
    position: absolute;
    right: 0px;
    height: 40px;
    transition: 0.5s cubic-bezier(0.05, 0.61, 0.41, 0.95);
}
.main2  .options .option .label .icon {
    display: flex;
    flex-direction: row;
    justify-content: center;
    align-items: center;
    min-width: 40px;
    max-width: 40px;
    height: 40px;
    border-radius: 100%;
    background-color: white;
    color: var(--defaultBackground);
}
.main2  .options .option .label .info {
    display: flex;
    flex-direction: column;
    justify-content: center;
    margin-left: 10px;
    color: white;
    white-space: pre;
}
.main2  .options .option .label .info > div {
    position: relative;
    transition: 0.5s cubic-bezier(0.05, 0.61, 0.41, 0.95), opacity 0.5s ease-out;
}
.main2  .options .option .label .info .main {
    font-weight: bold;
    font-size: 1.2rem;
}
.main2  .options .option .label .info .sub {
    transition-delay: .1s;
}


</stlye>






<%@ include file="../common/foot.jspf"%>