$(function() {
    $(".iteminfo_haeder").click(function() {
        $(this).next(".iteminfo_haeder_content").stop().slideToggle(250);
    });
    $(".review_contentbox").click(function() {
        var open = $(this).children().children(".review_content").height();
            var el = $(this).children().children(".review_content"),
                curHeight = el.height(),
                autoHeight = el.css('height', 'auto').height();
            
            if(autoHeight < 60) {
                autoHeight = 60;
            }
            if( open == 60){
                el.height(curHeight).animate({height: autoHeight}, 100);
                $(this).children().children(".review_img_mini").css('display', 'none');
            }else{
                el.animate({height: '60px'}, 100);
                $(this).children().children(".review_img_mini").css('display', 'block');
            }
    });
    
    $(".aside_rating").click(function() {
    	var offset = $(".review_container").offset();
    	$('html, body').animate({scrollTop : offset.top}, 400);    	
    });
    
    $(".aside_btn_plus").on(
    	"click",
    	function() {
    		var quantity = $(".aside_quantity");
    		quantity.val(Number(quantity.val()) + 1);
    		if(quantity.val() > 9) {
    			quantity.val(9);
    		}
    	}
    );
    $(".aside_btn_minus").on(
    	"click", 
    	function() {
			var quantity = $(".aside_quantity");
			quantity.val(Number(quantity.val()) - 1);
			if(quantity.val() < 1) {
				quantity.val(1);
			}
		}
    );
});

function btn_cart(member_idx, item_idx) {
	if(member_idx == 0) {	// 비로그인 접근제한
		location.href = "Login.do";
		return;
	}

	alert("장바구니에 담겼습니다.");
	var item_count = Number($(".aside_quantity").val());
	if(existsNameCookie(member_idx)) {	// 해당 멤버의 쿠키 정보 있으면 쿠키 업데이트
		updateCookie(member_idx, item_idx, item_count);
	} else {	// 해당 멤버의 쿠키 정보가 없으면 새로운 쿠키 생성
		insertCookie(member_idx, item_idx, item_count);
	}
}



