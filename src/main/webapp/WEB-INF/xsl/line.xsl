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
					<SCRIPT src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></SCRIPT>
					<SCRIPT src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></SCRIPT>
					<LINK rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous"></LINK>
					<SCRIPT src="/resources/js/jquery-3.3.1.min.js"></SCRIPT>
					
					<!-- Line Log-In-->					
					<SCRIPT src="/resources/js/lineLogIn.js"></SCRIPT>
					<TITLE>line</TITLE>
				</HEAD>
				<BODY>
					<MAIN class="container">
						<FORM action="/getToken/" method="POST">
							<DIV style="padding:15px;">
								<DIV style="padding:15px;">
									<B>已登入Line</B>
								</DIV>
								<BUTTON type="submit" class="btn btn-success">取得用戶資料</BUTTON>
								<DIV style="padding:15px;">
									<P></P>
								</DIV>
							</DIV>
						</FORM>
					</MAIN>
				</BODY>
			</xsl:template>
		</HTML>
	</xsl:template>
</xsl:stylesheet>
