package com.akash.demo.interceptor;

import java.util.Base64;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.akash.demo.constants.CommonConstants;
import com.akash.demo.jwt.JWTAuth;
import com.akash.demo.service.HospitalOpsService;
import com.akash.demo.vo.JWTPayloadVO;

public class RequestInterceptor extends HandlerInterceptorAdapter {
	@Autowired
	private HospitalOpsService hospitalOpsService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if (request.getRequestURI().contains("/admin")) {
			System.out.println("Admin URL");
			String auth = request.getHeader(CommonConstants.AUTHROIZATION);
			if (auth == null || auth.isEmpty() || !auth.contains(CommonConstants.BEARER)) {
				response.setStatus(401);
				return false;
			}
			String splittedForBearer[] = auth.split(" ");
			if (splittedForBearer.length != 2 || splittedForBearer[1].isEmpty()) {
				return false;
			}
			JWTPayloadVO jwtPayloadVO = JWTAuth.getJWTPayload(splittedForBearer[1]);
			if (!JWTAuth.validateJWT(jwtPayloadVO, request.getParameter("username"))) {
				response.setStatus(403);
				return false;
			}
		} else if (request.getRequestURI().contains("/doctor")) {
			System.out.println("Doctor URL");
			String auth = request.getHeader(CommonConstants.AUTHROIZATION);
			if (auth == null || auth.isEmpty() || auth.contains(CommonConstants.BEARER)) {
				response.setStatus(401);
				return false;
			}
			String splitted[] = auth.split(" ");
			if (splitted[0].isEmpty() || !splitted[0].toLowerCase().equals("basic") || !(splitted.length == 2)) {
				return false;
			}
			if (auth != null && !auth.isEmpty()) {
				System.out.println("Auth header is present allowing request");
				if (auth.toLowerCase().contains("basic")) {
					System.out.println("basic is present");
					String tokens[] = auth.split(" ");
					byte[] decodedToken = Base64.getDecoder().decode(tokens[1]);
					String userNamePass = new String(decodedToken);
					String splitUserName[] = userNamePass.split(":");
					if (splitUserName.length != 2) {
						return false;
					}
					if (!hospitalOpsService.validateUser(splitUserName[0], splitUserName[1])) {
						return false;
					}
				}
			}
		}
		return true;
	}
}
