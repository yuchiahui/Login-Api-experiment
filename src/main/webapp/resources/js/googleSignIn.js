function getStatus() {
	var rv = gapi.auth2.getAuthInstance().isSignedIn.get();
	console.log("isSignedIn: " + rv);
}
function onSignIn(googleUser) {
// Useful data for your client-side scripts:
	var profile = googleUser.getBasicProfile();

	console.log("ID: " + profile.getId()); // Don't send this directly to your server!
	console.log('Full Name: ' + profile.getName());
	console.log('Given Name: ' + profile.getGivenName());
	console.log('Family Name: ' + profile.getFamilyName());
	console.log("Image URL: " + profile.getImageUrl());

	console.log("Email: " + profile.getEmail());
	console.log("Profile: " + JSON.stringify(profile));
	getStatus();
	//	GoogleAuth.currentUser.get()

	document.getElementById('googleStatus').innerHTML = 'Thanks for logging in, ' + profile.getGivenName();
	document.getElementById('googleName').innerHTML = profile.getName() + ' ' + profile.getEmail();
	// The ID token you need to pass to your backend:
	var id_token = googleUser.getAuthResponse().id_token;
	console.log("ID Token: " + id_token);

	var xhr = new XMLHttpRequest();
	xhr.open('POST', 'http://localhost:8080/jwtTest');
	xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
//	xhr.onload = function () {
//		console.log('Signed in as: ' + xhr.responseText);
//	};
	xhr.send('idtoken=' + id_token);
}

function signOut() {
	var auth2 = gapi.auth2.getAuthInstance();
	auth2.signOut().then(function () {
		console.log("User signed out.");
	});

	auth2.disconnect().then(function () {
		console.log("User disconnect.");
	});
	alert("User signed out and disconnect.");

	document.getElementById('googleStatus').innerHTML = 'Please log into this app.';
	document.getElementById('googleName').innerHTML = '';
}

function Google_disconnect() {
	var auth2 = gapi.auth2.getAuthInstance();
	auth2.disconnect().then(function () {
		console.log('User disconnect.');
	});
}

$(document).ready(function () {
//	getStatus();
	$('A[name="googleSignOut"]').on('click', function () {
		signOut();
	});
});

//https://console.developers.google.com/
//let Google_appId = "386872756196-ct0oldlv5utvq3oi0lulegsgubrkjkn8.apps.googleusercontent.com";
////http://usefulangle.com/post/55/google-login-javascript-api 
//// Called when Google Javascript API Javascript is loaded
//function HandleGoogleApiLibrary() {
//	// Load "client" & "auth2" libraries
//	gapi.load('client:auth2', {
//		callback: function () {
//			// Initialize client & auth libraries
//			gapi.client.init({
//				clientId: Google_appId,
//				scope: 'https://www.googleapis.com/auth/userinfo.profile https://www.googleapis.com/auth/userinfo.email https://www.googleapis.com/auth/plus.me'
//			}).then(
//				function (success) {
//					// Google Libraries are initialized successfully
//					// You can now make API calls 
//					console.log("Google Libraries are initialized successfully");
//				},
//				function (error) {
//					// Error occurred
//					console.log(error);// to find the reason 
//				}
//			);
//		},
//		onerror: function () {
//			// Failed to load libraries
//			console.log("Failed to load libraries");
//		}
//	});
//}
//
//function GoogleLogin() {
//	// API call for Google login  
//	gapi.auth2.getAuthInstance().signIn().then(
//		function (success) {
//			// Login API call is successful 
//			console.log(success);
//			let Google_ID = success["El"];
//
//
//		},
//		function (error) {
//			// Error occurred
//			// console.log(error) to find the reason
//			console.log(error);
//		}
//	);
//
//}