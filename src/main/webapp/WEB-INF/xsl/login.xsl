<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
	<xsl:output
		encoding="UTF-8"
		media-type="text/html"
		method="html"
		indent="no"
		omit-xml-declaration="yes"
	/>
	<!-- TODO customize transformation rules 
	     syntax recommendation http://www.w3.org/TR/xslt 
	-->
	<xsl:template match="/">
		<HTML lang="en">
			<xsl:template match="document">
				<HEAD>
					<META charset="utf-8" />
					<META name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
					<SCRIPT src="https://code.jquery.com/jquery-3.3.1.min.js" crossorigin="anonymous"></SCRIPT>
					<SCRIPT src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></SCRIPT>
					<SCRIPT src="/resources/js/jquery-3.3.1.min.js"></SCRIPT>
					<SCRIPT src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></SCRIPT>
					<SCRIPT src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></SCRIPT>
					<LINK rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous"></LINK>
					
					<!-- FB Login -->
					<!--<script type="text/javascript" src="http://connect.facebook.net/zh_TW/all.js"></script>-->
					<!--<SCRIPT async="1" defer="1" src="https://connect.facebook.net/zh_TW/sdk.js#xfbml=1&amp;version=v3.2&amp;appId=324639151729654&amp;autoLogAppEvents=1"></SCRIPT>-->
					<!--<SCRIPT id="facebook-jssdk" src="https://connect.facebook.net/zh_TW/sdk.js#xfbml=1&amp;version=v3.2&amp;appId=324639151729654&amp;autoLogAppEvents=1"></SCRIPT>
					<SCRIPT src="https://connect.facebook.net/zh_TW/sdk.js?hash=703112b0b83be0a69fe420e25bbf97ad&amp;ua=modern_es6" async=""></SCRIPT>-->
					<SCRIPT src="/resources/js/fbLogIn.js"></SCRIPT>
				
					<!-- Google Sign-In-->
					<META name="google-signin-scope" content="profile email"/>
					<META name="google-signin-client_id" content="386872756196-ct0oldlv5utvq3oi0lulegsgubrkjkn8.apps.googleusercontent.com"/>
					<SCRIPT src="https://apis.google.com/js/platform.js?onload=init"></SCRIPT>
					<SCRIPT src="/resources/js/googleSignIn.js"></SCRIPT>
					
					<!-- Line Log-In-->					
					<SCRIPT src="/resources/js/lineLogIn.js"></SCRIPT>
								
					<TITLE>Login Api</TITLE>
				</HEAD>
				<BODY>
					<MAIN class="container">
						<!--<DIV style="height:60px;">
							<DIV id="status"></DIV>
							<DIV id="accountNickname"></DIV>
						</DIV>-->
						<DIV style="width:400px;height:120px;float:left;padding:15px;">
							<DIV id="fbStatus"></DIV>
							<DIV id="fbName"></DIV>			
							<DIV id="fb-root"></DIV>
							<!--<fb:login-button scope="public_profile,email" autologoutlink="true" onlogin="checkLoginState();"></fb:login-button>-->
							<!--<DIV class="fb-login-button" data-size="medium" data-button-type="continue_with" data-auto-logout-link="true" data-use-continue-as="true" onlogin="checkLoginState();"></DIV>-->
							<DIV class="fb-login-button" data-max-rows="1" data-size="medium" data-button-type="continue_with" data-auto-logout-link="true" data-use-continue-as="true" data-onlogin="checkLoginState();"></DIV><!--data-scope="public_profile,email"-->
							<DIV class="fb-like" data-share="true" data-width="450" data-show-faces="true"></DIV>
							<!--<DIV>
								<BUTTON class="btn btn-secondary" name="fbLogin">FB Login</BUTTON>
								<BUTTON class="btn btn-secondary" name="fbLogout">FB Logout</BUTTON>
							</DIV>-->
						</DIV>
						<DIV style="width:360px;height:120px;float:left;padding:15px;">
							<DIV id="googleStatus"></DIV>
							<DIV id="googleName"></DIV>
							<DIV class="g-signin2" name="googleSignIn" data-onsuccess="onSignIn" data-theme="dark" data-longtitle="true"></DIV>
							<A href="javascript:void(0)" name="googleSignOut">Sign out</A>
						</DIV>
						<DIV style="height:100px;padding:15px;">
							<DIV id="lineStatus"></DIV>
							<DIV id="lineName"></DIV>
							<!--https://access.line.me/oauth2/v2.1/authorize?
							response_type=code&
							client_id=1648812380&
							redirect_uri=http%3A%2F%2Flocalhost%3A8080%2Flogin&
							state=opybJ409iX9OLmzpTV428tOoeRTkCVkoJjpkaEWBstk&
							scope=openid%20profile&
							nonce=HuAeZDDC3FN0-ov3_CBWQ6Wk9dmGrJLVKa91Lv42wRg-->
							<FORM action="https://local-login.herokuapp.com/" method="GET">
								<!--<FORM action="https://access.line.me/oauth2/v2.1/authorize" method="GET">
								<INPUT name="response_type" type="hidden" value="code"/>
								<INPUT name="client_id" type="hidden" value="1648812380"/>
								<INPUT name="redirect_uri" type="hidden" value="1648812380"/>
								<INPUT name="state" type="hidden" value="opybJ409iX9OLmzpTV428tOoeRTkCVkoJjpkaEWBstk"/>
								<INPUT name="scope" type="hidden" value="openid%20email%20profile"/>
								<INPUT name="nonce" type="hidden" value="HuAeZDDC3FN0-ov3_CBWQ6Wk9dmGrJLVKa91Lv42wRg"/>-->
								<BUTTON type="submit" class="btn btn-success">Line</BUTTON>
							</FORM>
						</DIV>
					</MAIN>
				</BODY>
			</xsl:template>
		</HTML>
	</xsl:template>
</xsl:stylesheet>
