//$(document).ready(function () {
// This is called with the results from from FB.getLoginStatus().
function statusChangeCallback(response) {
	console.log('statusChangeCallback');
	console.log(JSON.stringify(response));

	if (response.status === 'connected') {
		// Logged into your app and Facebook.
		testAPI();
	} else {
		// The person is not logged into your app or we are unable to tell.
		document.getElementById('fbStatus').innerHTML = 'Please log into this app.';
		document.getElementById('fbName').innerHTML = '';
	}
}

// This function is called when someone finishes with the Login
// Button.  See the onlogin handler attached to it in the sample
// code below.
function checkLoginState() {
	FB.getLoginStatus(function (response) {
		statusChangeCallback(response);
	});
}
window.fbAsyncInit = function () {
	FB.init({
		appId: '324639151729654',
		cookie: true, // enable cookies to allow the server to access 
		// the session
		xfbml: true, // parse social plugins on this page
		version: 'v3.2' // The Graph API version to use for the call
	});

	FB.getLoginStatus(function (response) {
		statusChangeCallback(response);
	});
};

// Load the SDK asynchronously
(function (d, s, id) {
	var js, fjs = d.getElementsByTagName(s)[0];
	if (d.getElementById(id))
		return;
	js = d.createElement(s);
	js.id = id;
	js.src = "https://connect.facebook.net/zh_TW/sdk.js";
	fjs.parentNode.insertBefore(js, fjs);
}(document, 'script', 'facebook-jssdk'));

// Here we run a very simple test of the Graph API after login is
// successful.  See statusChangeCallback() for when this call is made.
function testAPI() {
	console.log('Welcome!  Fetching your information.... ');
	FB.api('/me', {fields: 'id,name,email'}, function (response) {
		console.log('Successful login for: ' + response.name);

		document.getElementById('fbStatus').innerHTML =
			'Thanks for logging in, ' + response.name + '!';
		document.getElementById('fbName').innerHTML = response.name + " " + response.email;

		console.log(JSON.stringify(response));
	});

	//印出被拒絕存取的權限
	FB.api('/me/permissions', function (response) {
		var permissions = [];

		for (i = 0; i < response.data.length; i++) {
			if (response.data[i].status === 'declined')
			{
				permissions.push(response.data[i].permission) + "\n";
			}
		}
		if (permissions.toString().length > 0)
			alert(permissions.toString());
	});
}

//	//使用自己客製化的按鈕來登入
//	function FBLogin() {
//		FB.login(function (response) {
//			//debug用
//			console.log(response);
//			if (response.status === 'connected') {
//				//user已登入FB
//				//抓userID
//				let FB_ID = response["authResponse"]["userID"];
//				console.log("userID:" + FB_ID);
//
//				testAPI();
//			} else if (response.status === 'not_authorized') {
//				// The person is logged into Facebook, but not your app.
//				document.getElementById('status').innerHTML = 'Please log ' +
//					'into this app.';
//			} else {
//				// The person is not logged into Facebook, so we're not sure if
//				// they are logged into this app or not.
//				document.getElementById('status').innerHTML = 'Please log ' +
//					'into Facebook.';
//			}
//		}, {scope: 'public_profile,email,gender'});
//	}
//
//	//使用自己客製化的按鈕來登出
//	function FBLogout() {
//		FB.logout(function (response) {
//			// user is now logged out
//			alert('已成功登出!');
//			window.location.reload();
//		});
//	}
//
//});