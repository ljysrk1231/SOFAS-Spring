$(document).ready(function() {
	total_price();
	$(".plus_btn, .minus_btn").on({
		click: function() {
			var item_idx = $(this).parent().prev().children(".item_idx");
			var item_count = $(this).parent().prev().children(".item_count");
			var item_count_val = parseInt(item_count.val());
			var getPrice = $(this).parent().parent().next().children(".getPrice");						
			var getPrice_val = $(this).parent().parent().next().children(".getPrice_val");
			var price = $(this).parent().parent().next().children(".price");
			
			if($(this).attr("value")=="+") {
				item_count_val += 1;
		        if(item_count_val > 9) item_count_val = 9;
			} else {
				item_count_val -= 1;
		        if(item_count_val < 1) item_count_val = 1;
			}
			getPrice.text("￦ "+(price.val()*item_count_val).toString().replace(/\B(?=(\d{3})+(?!\d))/g,","));
			getPrice_val.val(price.val()*item_count_val);
			
			total_price();
			item_count.val(item_count_val);
			
			var member_idx = $(this).parent().children(".member_idx").val();
			btn_proc(member_idx, item_idx.val(), item_count.val());
		}
	});
});

function total_price() {
	let sum = 0; 
	$('.getPrice_val').each(function(){
		// type으로 시작하는 input을 순차적으로 loop 
		if(!isNaN($(this).val())){ 
			// CASE 값에 문자가 없는 경우 (숫자인 경우만 합산) 
			sum += parseInt($(this).val());
		}
	});
	// 합산한 값을 name="sum"인 input에 넣어줌
	$(".totalPrice").text("￦ "+sum.toString().replace(/\B(?=(\d{3})+(?!\d))/g,","));
}

function btn_proc(member_idx, item_idx, item_cnt) {
	updateCookie(member_idx, item_idx, item_cnt);	// 쿠키 value 값 수량 조정
}

function delete_cart(this_, member_idx, item_idx) {
	$(this_).parent().parent().parent().parent().remove();
	deleteValueCookie(member_idx, item_idx);	// 쿠키 삭제
	total_price();
	
	// test code - 삭제 후 바로 결제하기 누르면 목록 갱신이 안되서 넘어갈때 500 에러
	// 임시방편으로 새로고침 시켜서 해결했으나 더 나은 해결방안 찾는 중
	location.reload();
}

