package com.company.loginapi.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

	@RequestMapping(value = "/")
	public ModelAndView goHome(HttpServletResponse response) throws IOException {
		return new ModelAndView("home");
	}

	public ModelAndView viewLine(HttpServletRequest request,
		HttpServletResponse response) throws IOException {
		String xmlFile = "resources/xml.xml";
		String contextPath = request.getServletContext().getRealPath("");
		String xmlFilePath = contextPath + File.separator + xmlFile;
		Source source = new StreamSource(new File(xmlFilePath));

		String code = request.getQueryString();

		System.out.println(code);

		ModelAndView model = new ModelAndView("line");
		model.addObject("xmlSource", source);
		return model;
	}

	@RequestMapping(value = "/getToken")
	public void getToken(HttpServletRequest request,
		HttpServletResponse response, String code) throws IOException, ParserConfigurationException {

		//建立 CloseableHttpClient & HttpPost
		CloseableHttpClient httpclient = HttpClients.createDefault();

		//取得 access token
		HttpPost httppost = new HttpPost("https://api.line.me/oauth2/v2.1/token");

		//設定 Request parameters and other properties.
		//取得 access_token 所需參數如下:
		@SuppressWarnings("Convert2Diamond")
		List<NameValuePair> params = new ArrayList<NameValuePair>(2);
		params.add(new BasicNameValuePair("grant_type", "authorization_code"));
		params.add(new BasicNameValuePair("code", code));
		params.add(new BasicNameValuePair("redirect_uri", "http://localhost:8080/login"));
		params.add(new BasicNameValuePair("client_id", "1648812380"));
		params.add(new BasicNameValuePair("client_secret", "b94aa493b9bb45066b531b3b46efad30"));
		httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
		httppost.setHeader("Content-Type", "application/x-www-form-urlencoded");

		//Execute and get the response.
		CloseableHttpResponse tokenResponse = httpclient.execute(httppost);
		HttpEntity tokenEntity = tokenResponse.getEntity();

		String tokenResult = "";

		System.out.println("token--getStatusLine: " + tokenResponse.getStatusLine());
		if (tokenResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			if (tokenEntity != null) {
				tokenResult = EntityUtils.toString(tokenEntity, "UTF-8");
				System.out.println("token: " + tokenResult);
			}
		} else {
			System.out.println("token--ERROR");

		}
		System.out.println("token--httppost: " + httppost.toString());
//		tokenResponse.close();

		JSONObject tokenJSONObject = new JSONObject(tokenResult);
		System.out.println("token--finished");

		//取得 user profiles
		HttpGet httpget = new HttpGet("https://api.line.me/v2/profile");

		//設定 Authorization
		httpget.setHeader("Authorization", "Bearer " + tokenJSONObject.get("access_token"));

		CloseableHttpResponse profileResponse = httpclient.execute(httpget);
		HttpEntity profileEntity = profileResponse.getEntity();

		String profileResult = "";

		System.out.println("profile--getStatusLine: " + profileResponse.getStatusLine());
		if (profileResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			if (profileEntity != null) {
				profileResult = EntityUtils.toString(profileEntity, "UTF-8");
				System.out.println("profile: " + profileResult);
			}
		} else {
			System.out.println("profile--ERROR");

		}
		System.out.println("profile--httppost: " + httpget.toString());
		//profileResponse.close();

		JSONObject profileJSONObject = new JSONObject(profileResult);

		//傳回前端
		response.getWriter().print(profileJSONObject.toString());
		System.out.println("profile--finished");
	}

	@SuppressWarnings("null")
	@RequestMapping(value = "/login")
	public ModelAndView view(HttpServletRequest request,
		HttpServletResponse response, @RequestParam(required = false) String code, @RequestParam(required = false) String state) throws IOException {

		if (code != null && state != null) {
			return viewLine(request, response);
		}

		// builds absolute path of the XML file
		String xmlFile = "resources/xml.xml";
		String contextPath = request.getServletContext().getRealPath("");
		String xmlFilePath = contextPath + File.separator + xmlFile;
		Source source = new StreamSource(new File(xmlFilePath));

		ModelAndView model = new ModelAndView("login");
		model.addObject("xmlSource", source);
		return model;
	}

	@RequestMapping(value = "/jwtTest", method = RequestMethod.POST)
	public void jwtTest(HttpServletRequest request,
		HttpServletResponse response, String idtoken) throws IOException, ParserConfigurationException {

		String jwtToken = idtoken;
		System.out.println("------------ Decode JWT ------------");
		String[] split_string = jwtToken.split("\\.");
		String base64EncodedHeader = split_string[0];
		String base64EncodedBody = split_string[1];
		String base64EncodedSignature = split_string[2];

		Base64 base64Url = new Base64(true);
		String header = new String(base64Url.decode(base64EncodedHeader));
		System.out.println("JWT Header : " + header);

		String body = new String(base64Url.decode(base64EncodedBody));
		System.out.println("JWT Body : " + body);
		System.out.println("JWT Body.sub : " + new JSONObject(body).get("sub").toString());

		String signature = new String(base64Url.decode(base64EncodedSignature));
		System.out.println("JWT Signature : " + signature);
	}
}
