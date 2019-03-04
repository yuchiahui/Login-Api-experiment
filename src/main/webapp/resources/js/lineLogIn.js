$(document).ready(function () {
	var url_string = window.location.href; //window.location.href
	var url = new URL(url_string);
	var code = url.searchParams.get("code");
	$('INPUT[name="code"]').val(code);
	console.log("code = " + code);
	var jdata = {code: code};
	$('FORM[method="POST"]').submit(function (e) {
		e.preventDefault();
		$.post(
			$(this).attr("action"),
			jdata,
			function (data) {
				alert("data: " + data);
//				alert("access_token: " + JSON.stringify(data));
				$('P').html(data);
			});
	});
	$('BUTTON[name="login"]').on("click", function () {
		$.get(
			"/linelogin",
			function (data) {
				alert("SUCCESS");
//				alert("頁面轉換中 網址: " + data);
//				$.get(data,
//					function () {
//						alert("SUCCESS");
//					});
//				alert("access_token: " + JSON.stringify(data));
//				$('P').html(data);
			});
	});
}
);