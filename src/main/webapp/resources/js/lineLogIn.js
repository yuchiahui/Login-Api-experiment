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
				alert("access_token: " + data);
				document.getElementById('userProfile').innerHTML = data;
			});
	});
});