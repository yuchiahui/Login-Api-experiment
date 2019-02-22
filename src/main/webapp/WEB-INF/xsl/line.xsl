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
					<TITLE>line</TITLE>
				</HEAD>
				<BODY>
					<MAIN class="container">
						<DIV style="height:100px;padding:15px;">已登入
						</DIV>
					</MAIN>
				</BODY>
			</xsl:template>
		</HTML>
	</xsl:template>
</xsl:stylesheet>
