$(document).ready(function () {
//	var mycode = new URL(window.location.href).searchParams.get("code");
//	$('INPUT[name="code"]').val(code);
//	console.log("code = " + code);
//	var jdata = {code: code};
	$('FORM[method="POST"]').submit(function (e) {
		e.preventDefault();
		var a = function (data) {
			alert("data: " + data);
			console.log(data);
			$('P').html(data);
		};
		var b = function (data) {
//			var data = "123456";
			alert(data);
			console.log(data);
			$('P').html(data);
		};
		//"/getToken2"
		$.post($(this).attr("action"), {code: new URL(window.location.href).searchParams.get("code")}, a, "text");
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