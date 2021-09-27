function addEtc() {
	var date = new Date();
	date.setTime(date.getTime() + 12*60*60*1000);	// 12시간 유효
	return ';expires='+date.toUTCString()+';path=/';	// 유효시간 + path
}

function existsNameCookie(member_idx) {
	var value = document.cookie.match(member_idx + '=([^;]*)(;|$)');
	return value? true : false;
}

function insertCookie(member_idx, item_idx, item_count) {
	var etc = addEtc();
	document.cookie = member_idx + "=" + item_idx + "C" + item_count + etc;
}

function updateCookie(member_idx, item_idx, count) {
	var etc = addEtc();
	var curVal = document.cookie.match(member_idx + '=([^;]*)(;|$)');
	if(curVal[1].indexOf(item_idx+"C") >= 0) {
		var temp = curVal[1].split("//");
		var sum = "";
		for(var i=0; i<temp.length; i++) {
			var itemIdx = temp[i].split("C")[0];
			var itemCnt = Number(temp[i].split("C")[1]);
			if(itemIdx == item_idx) {
				itemCnt = count;
			}
			sum += itemIdx+"C"+itemCnt+"//";
		}
		sum = sum.substring(0, sum.lastIndexOf("//"));
		document.cookie = member_idx + '=' + sum + etc;
	} 
	else {
		document.cookie = member_idx + '=' + curVal[1] + "//" +item_idx + "C" + count + etc;
	}
}

function deleteValueCookie(member_idx, item_idx) {
	var etc = addEtc();
	var curVal = document.cookie.match(member_idx + '=([^;]*)(;|$)');
	var temp = curVal[1].split("//");
	var sum = "";
	for(var i=0; i<temp.length; i++) {
		var itemIdx = temp[i].split("C")[0];
		var itemCnt = Number(temp[i].split("C")[1]);
		if(itemIdx == item_idx) {
			continue;
		}
		sum += itemIdx+"C"+itemCnt+"//";
	}
	if(sum == "") {
		deleteNameCookie(member_idx);
	} else {
		sum = sum.substring(0, sum.lastIndexOf("//"));
		document.cookie = member_idx + '=' + sum + etc;			
	}
}

function deleteNameCookie(member_idx) {
	document.cookie = member_idx + "=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;";
}