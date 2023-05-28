
 <!-- JSP 파일 내용 -->

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

<title>날씨 팝업</title>
<!-- https://parkjh7764.tistory.com/11 미세먼지 참고 -->


<script>
$(document).ready(function() {
  $.ajax({
    url: '/src/main/resources/weather.json', // JSON 파일의 경로
    dataType: 'json',
    type: 'GET',
    success: function(data) {
      // JSON 데이터를 사용하는 로직 작성
    },
    error: function() {
      console.log('Error occurred while fetching JSON data');
    }
  });
});
</script>
<script>
//오늘 날짜출력
$(document).ready(function () {

    function convertTime() {
        var now = new Date();


        var month = now.getMonth() + 1;
        var date = now.getDate();
        var year = now.getYeat();

        return year + '년'+ month + '월' + date + '일';
    }

    var currentTime = convertTime();
    $('.nowtime').append(currentTime);
});
</script>
<script>
//제이쿼리사용
$.getJSON('https://api.openweathermap.org/data/2.5/weather?q=Daejeon,kr&appid=9dfed2f326354341b5cfd541cb39e05d&units=metric',
function (WeatherResult) {
    //기온출력
    $('.DajeonNowtemp').append(WeatherResult.main.temp);
    $('.DajeonLowtemp').append(WeatherResult.main.temp_min);
    $('.DajeonHightemp').append(WeatherResult.main.temp_max);

    //날씨아이콘출력
    //WeatherResult.weater[0].icon
    var weathericonUrl =
        '<img src= "http://openweathermap.org/img/wn/'
        + WeatherResult.weather[0].icon +
        '.png" alt="' + WeatherResult.weather[0].description + '"/>'

    $('.DajeonIcon').html(weathericonUrl);
});
</script>

<span class="nowtime"></span>
<span>현재날씨</span>
        
        <h3>대전날씨</h3>
        <h3 class="DajeonIcon"></h3>
        <h3 class="DajeonNowtemp">현재기온:</h3>
        <h3 class="DajeonLowtemp">최저기온:</h3>
        <h3 class="DajeonHightemp">최대기온:</h3>

