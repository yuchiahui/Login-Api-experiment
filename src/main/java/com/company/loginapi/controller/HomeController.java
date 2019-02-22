package com.company.loginapi.controller;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;
import org.apache.tomcat.util.codec.binary.Base64;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

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

		ModelAndView model = new ModelAndView("line");
		model.addObject("xmlSource", source);
		return model;
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
