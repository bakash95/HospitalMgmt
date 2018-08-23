package com.akash.demo.interceptor;

import java.util.Base64;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.akash.demo.constants.CommonConstants;
import com.akash.demo.jwt.JWTAuth;
import com.akash.demo.service.HospitalOpsService;
import com.akash.demo.vo.JWTPayloadVO;

public class RequestInterceptor extends HandlerInterceptorAdapter {

	@Resource(name = "urlAuthProps")
	private Map<String, String> urlAuthProps;

	@Autowired
	private HospitalOpsService hospitalOpsService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String url = request.getRequestURI().replaceAll("/", "");
		String mapEntry = urlAuthProps.get(url);
		if (url == null || mapEntry == null) {
			return false;
		}
		if (mapEntry.equals(CommonConstants.ADMIN)) {
			if (!hospitalOpsService.checkRole(request.getParameter(CommonConstants.USERNAME), CommonConstants.ADMIN)) {
				response.sendError(401, "Sorry you are not a admin!!!");
				return false;
			}
			String auth = request.getHeader(CommonConstants.AUTHROIZATION);
			if (auth == null || auth.isEmpty() || !auth.contains(CommonConstants.BEARER)) {
				response.sendError(401, "Sorry you there is no bearer token or the word bearer is missing");
				return false;
			}
			String splittedForBearer[] = auth.split(" ");
			if (splittedForBearer.length != 2 || splittedForBearer[1].isEmpty()) {
				response.sendError(401, "Sorry you there is no bearer token!!");
				return false;
			}
			try {
				JWTPayloadVO jwtPayloadVO = JWTAuth.getJWTPayload(splittedForBearer[1]);
				if (!JWTAuth.validateJWT(jwtPayloadVO, request.getParameter(CommonConstants.USERNAME))) {
					response.sendError(403, "Invalid Access Token please Regenerate");
				}
			} catch (Exception e) {
				response.sendError(403, "Invalid Access Token please Regenerate");
				return false;
			}
		} else if (mapEntry.equals(CommonConstants.DOCTOR)) {
			String auth = request.getHeader(CommonConstants.AUTHROIZATION);
			if (auth == null || auth.isEmpty() || auth.contains(CommonConstants.BEARER)) {
				response.sendError(401, "Sorry you there is no basic token or the word basic is missing");
				return false;
			}
			String splitted[] = auth.split(" ");
			if (splitted[0].isEmpty() || !splitted[0].toLowerCase().equals(CommonConstants.BASIC)
					|| !(splitted.length == 2)) {
				response.sendError(401, "Sorry you there is no basic token!");
				return false;
			}
			if (auth != null && !auth.isEmpty()) {
				System.out.println("Auth header is present allowing request");
				if (auth.toLowerCase().contains(CommonConstants.BASIC)) {
					System.out.println("basic is present");
					String tokens[] = auth.split(" ");
					byte[] decodedToken = Base64.getDecoder().decode(tokens[1]);
					String userNamePass = new String(decodedToken);
					String splitUserName[] = userNamePass.split(":");
					if (splitUserName.length != 2) {
						response.setStatus(401);
						return false;
					}
					System.out.println("user name is " + splitUserName[0]);
					if (!hospitalOpsService.validateUser(splitUserName[0], splitUserName[1])) {
						response.sendError(401, "Sorry you are not a valid user");
						return false;
					}
					if (!hospitalOpsService.checkRole(splitUserName[0], CommonConstants.DOCTOR)) {
						response.sendError(401, "Sorry you dont have sufficient privileages");
						return false;
					}
				}
			}
		}
		return true;
	}
}
