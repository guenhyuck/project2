<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script>
	var historyBack = '${historyBack}' == 'true';
	var msg = '${msg}'.trim();
	if (msg) {
		alert(msg);
	}
	if (historyBack) {
		history.back();
	}
	var replaceUri = '${replaceUri}'.trim();
	if (replaceUri) {
		location.replace(replaceUri);
	}
</script>

<script>
var swiper = new Swiper(".mySwiper", {
	  navigation: {
	    nextEl: ".swiper-next-button",
	    prevEl: ".swiper-prev-button"
	  },
	  effect: "fade",
	  loop: "infinite",
	  pagination: {
	    el: ".swiper-pagination",
	    type: "fraction"
	  }
	});

	swiper.on("slideChange", function (sld) {
	  document.body.setAttribute("data-sld", sld.realIndex);
	});

</script>