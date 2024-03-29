package com.company.loginapi.controller;

import com.sun.javafx.util.Utils;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
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

	/**
	 * 產生隨機字串
	 *
	 * @return 隨機字串
	 */
	public String getRandomString() {
		SecureRandom RANDOM = new SecureRandom();
		byte[] bytes = new byte[32];
		RANDOM.nextBytes(bytes);

		return java.util.Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
	}

//	@CrossOrigin(origins = "https://access.line.me", maxAge = 3600)
	@RequestMapping(value = "/linelogin", method = RequestMethod.GET)
	@SuppressWarnings("ConvertToTryWithResources")
//	@ResponseBody
	public String linelogin(HttpServletRequest request,
		HttpServletResponse response) throws IOException, ParserConfigurationException, URISyntaxException {

		//建立 CloseableHttpClient & HttpGet
		CloseableHttpClient httpclient = HttpClients.createDefault();
		URIBuilder builder = new URIBuilder("https://access.line.me/oauth2/v2.1/authorize");

		//Line Log in 身分驗證, 取得 code
//		HttpGet httpGet = new HttpGet("https://access.line.me/oauth2/v2.1/authorize");
		//設定 Request parameters and other properties.
		//取得 code 所需參數如下:
		//參數值為 https://developers.line.biz/console/channel/... 設定之內容
		@SuppressWarnings("Convert2Diamond")
		List<NameValuePair> params = new ArrayList<NameValuePair>(2);
		params.add(new BasicNameValuePair("response_type", "code"));
		params.add(new BasicNameValuePair("client_id", "1648812380"));
		params.add(new BasicNameValuePair("redirect_uri", "http://localhost:8080/login"));
		params.add(new BasicNameValuePair("state", getRandomString()));

//		System.out.println(Utils.convertUnicode("%"));
		String item = "openid profile email";

		byte[] bytes = item.getBytes(StandardCharsets.ISO_8859_1);
		item = new String(bytes, StandardCharsets.UTF_8);

		System.out.println(Utils.convertUnicode("%"));

		String scope = URLEncoder.encode("openid profile email", "ISO-8859-1");

		params.add(new BasicNameValuePair("scope", item));
		params.add(new BasicNameValuePair("nonce", getRandomString()));
		builder.setParameters(params);

		HttpGet httpget = new HttpGet(builder.build());

//		URI uri = new URIBuilder("https://access.line.me/oauth2/v2.1/authorize").addParameters(params).build();
//		httpGet.setHeader("Access-Control-Allow-Origin", "*");
		//Execute and get the response.
		CloseableHttpResponse codeResponse = httpclient.execute(httpget);
//		codeResponse.setHeader("Location", builder.build().toString());
		HttpEntity codeEntity = codeResponse.getEntity();

		String codeResult = "";

//		CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
		HttpClientContext context = HttpClientContext.create();
//		HttpGet httpGet = new HttpGet("http://localhost:8080/");
//		CloseableHttpResponse response = closeableHttpClient.execute(httpGet, context);

		HttpHost target = context.getTargetHost();
		List<URI> redirectLocations = context.getRedirectLocations();
		URI location = URIUtils.resolve(httpget.getURI(), target, redirectLocations);
		System.out.println("Final HTTP location: " + location.toASCIIString());
		// Expected to be an absolute URI

		System.out.println("code--getStatusLine: " + codeResponse.getStatusLine());
		if (codeResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			if (codeEntity != null) {
				codeResult = EntityUtils.toString(codeEntity, "UTF-8");
				System.out.println(codeEntity.getContentEncoding());
				System.out.println("code: " + codeResult);
			}
		} else {
			System.out.println("code--ERROR");
			codeResult = codeResponse.getStatusLine().toString();
		}

		System.out.println("code--httppost: " + httpget.toString());
//		closeableHttpResponseOfToken.close();

		System.out.println("code--finished");
		codeResponse.close();

//		return codeResult;
//		return uri.toString();
//		return new ModelAndView("redirect:" + httpGet.getURI().toString());
		System.out.println(httpget.getURI().toString());
		return "redirect:" + httpget.getURI().toString();

	}

	@RequestMapping(value = "/getToken", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@SuppressWarnings("ConvertToTryWithResources")
//	@ResponseBody
	public String getToken(HttpServletRequest request, HttpServletResponse response, String code) throws IOException, ParserConfigurationException {
		JSONObject jSONObject = new JSONObject();

		//建立 CloseableHttpClient & HttpPost
		CloseableHttpClient closeableHttpClient = HttpClients.createDefault();

		//取得 access token
		HttpPost httpPost = new HttpPost("https://api.line.me/oauth2/v2.1/token");

		//設定 Request parameters and other properties.
		//取得 access_token 所需參數如下:
		//參數值為 https://developers.line.biz/console/channel/... 設定之內容
		//108.03.04 以下設定為系統參數(tomcat setenv.bat)
		List<NameValuePair> params = new ArrayList<>(2);
		params.add(new BasicNameValuePair("grant_type", System.getenv("LINE_GRANT_TYPE")));
		params.add(new BasicNameValuePair("code", code));
		params.add(new BasicNameValuePair("redirect_uri", System.getenv("LINE_REDIRECT_URI")));
		params.add(new BasicNameValuePair("client_id", System.getenv("LINE_CLIENT_ID")));
		params.add(new BasicNameValuePair("client_secret", System.getenv("LINE_CLIENT_SECRET")));
		httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
		httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");

		//Execute and get the response.
		CloseableHttpResponse closeableHttpResponseOfToken = closeableHttpClient.execute(httpPost);
		HttpEntity httpEntityOfToken = closeableHttpResponseOfToken.getEntity();

		String stringToken = "";

		System.out.println("token--getStatusLine: " + closeableHttpResponseOfToken.getStatusLine());
		if (closeableHttpResponseOfToken.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			if (httpEntityOfToken != null) {
				stringToken = EntityUtils.toString(httpEntityOfToken, "UTF-8");
				System.out.println("token: " + stringToken);
			} else {
				jSONObject.put("errorMessage", "ERROR! closeableHttpResponseOfToken.getEntity() = null");
				return jSONObject.toString();
			}
		} else {
			System.out.println("token--ERROR");
			jSONObject.put("errorMessage", "ERROR! closeableHttpResponseOfToken.getStatusLine() = " + closeableHttpResponseOfToken.getStatusLine());
			return jSONObject.toString();
		}

		System.out.println("token--httppost: " + httpPost.toString());

		JSONObject jsonObjectOfToken = new JSONObject(stringToken);
		System.out.println("token--finished");
		closeableHttpResponseOfToken.close();

		String[] jwtArray = decodeJWT(jsonObjectOfToken.get("id_token").toString(), "Line");
		if (jwtArray == null) {
			System.out.println("decodeJWT Error!--getToken");
			jSONObject.put("errorMessage", "ERROR! decode JWT failed");
			return jSONObject.toString();
		} else {
			System.out.println(Arrays.toString(jwtArray));
		}

		//取得 user profiles
		HttpGet httpGet = new HttpGet("https://api.line.me/v2/profile");

		//設定 Authorization
		httpGet.setHeader("Authorization", "Bearer " + jsonObjectOfToken.get("access_token").toString());

		CloseableHttpResponse closehttpResponseOfProfile = closeableHttpClient.execute(httpGet);
		HttpEntity httpEntityOfProfile = closehttpResponseOfProfile.getEntity();

		String stringProfile = "";

		System.out.println("profile--getStatusLine: " + closehttpResponseOfProfile.getStatusLine());
		if (closehttpResponseOfProfile.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			if (httpEntityOfProfile != null) {
				stringProfile = EntityUtils.toString(httpEntityOfProfile, "UTF-8");

				JSONObject jsonObjectProfile = new JSONObject(stringProfile);

				System.out.println("profile: " + stringProfile);
				System.out.println("--------------------------");
				System.out.println("userId: " + jsonObjectProfile.getString("userId"));
				System.out.println("displayName: " + jsonObjectProfile.getString("displayName"));
				System.out.println("pictureUrl: " + jsonObjectProfile.getString("pictureUrl"));
				System.out.println("statusMessage: " + jsonObjectProfile.getString("statusMessage"));
				System.out.println("--------------------------");
				
				jSONObject.put("profile", stringProfile);
			} else {
				jSONObject.put("errorMessage", "ERROR! closehttpResponseOfProfile.getEntity() = null");
				return jSONObject.toString();
			}
		} else {
			System.out.println("profile--ERROR");
			jSONObject.put("errorMessage", "ERROR! closehttpResponseOfProfile.getStatusLine() = " + closehttpResponseOfProfile.getStatusLine());
			return jSONObject.toString();
		}
		System.out.println("profile--httppost: " + httpGet.toString());

		//傳回前端
//		response.getWriter().print(jsonObject.toString());
//		System.out.println("profile--finished");
//		response.getWriter().close();
//		httpResponseOfProfile.close();
//		closeableHttpClient.close();
		return jSONObject.toString();
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

	/**
	 *
	 * @param request
	 * @param response
	 * @param idtoken Google id token
	 * @return
	 * @throws IOException
	 * @throws ParserConfigurationException
	 */
	@RequestMapping(value = "/jwtTest", method = RequestMethod.POST)
//	@ResponseBody
	public String jwtTest(HttpServletRequest request,
		HttpServletResponse response, @RequestParam String idtoken) throws IOException, ParserConfigurationException {

		String[] jwtArray = decodeJWT(idtoken, "Google");
		if (jwtArray == null) {
			System.out.println("decodeJWT Error!--jwtTest");
		} else {
			System.out.println(Arrays.toString(jwtArray));
		}

		return Arrays.toString(jwtArray);
	}

	/**
	 * Json Web Token 轉譯
	 *
	 * @param idtoken 要轉譯的 JWT
	 * @param company 公司
	 * @return 傳回一陣列, JWT經轉譯後的內容, 格式為{header, body, signature}
	 */
	private String[] decodeJWT(String idtoken, String company) throws UnsupportedEncodingException {

		String jwtToken = idtoken;
		if (jwtToken == null) {
			return null;
		}
		System.out.println("------------ idtoken(" + company + ") ------------");
		System.out.println(jwtToken);

		System.out.println("------------ Decode JWT(" + company + ") ------------");
		String[] jwtTokenArray = jwtToken.split("\\.");
		String base64EncodedHeader = jwtTokenArray[0];
		String base64EncodedBody = jwtTokenArray[1];
		String base64EncodedSignature = jwtTokenArray[2];

		Base64 base64Url = new Base64(true);
		String header = new String(base64Url.decode(base64EncodedHeader), "UTF-8");
		System.out.println("JWT Header : " + header);

		String body = new String(base64Url.decode(base64EncodedBody), "UTF-8");
		System.out.println("JWT Body : " + body);
		System.out.println("JWT Body.sub : " + new JSONObject(body).get("sub").toString());

		String signature = new String(base64Url.decode(base64EncodedSignature), "UTF-8");
		System.out.println("JWT Signature : " + signature);

		String[] result = {header, body, signature};
		return result;
	}
}
