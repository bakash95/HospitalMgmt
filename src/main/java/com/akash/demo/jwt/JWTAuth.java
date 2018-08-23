package com.akash.demo.jwt;

import java.security.Key;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import com.akash.demo.constants.CommonConstants;
import com.akash.demo.vo.JWTPayloadVO;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JWTAuth {

	private final static String key = "eaWWE21892$23";

	public static String createJWT(String id, String issuer, String subject, long lifeTimeOfToken) {
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

		long timeInMills = System.currentTimeMillis();
		Date now = new Date(timeInMills);

		byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(key);

		Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
		JwtBuilder builder = Jwts.builder().setId(id).setIssuedAt(now).setSubject(subject).setIssuer(issuer)
				.signWith(signatureAlgorithm, signingKey);

		if (lifeTimeOfToken >= 0) {
			long expMillis = timeInMills + lifeTimeOfToken;
			Date exp = new Date(expMillis);
			builder.setExpiration(exp);
		}

		return builder.compact();
	}

	public static JWTPayloadVO getJWTPayload(String jwt) throws Exception {
		Claims claims = null;
		try {
			claims = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(key)).parseClaimsJws(jwt)
					.getBody();

		} catch (Exception e) {
			throw new Exception("Sorry the token seems to be invalid!!");
		}
		return new JWTPayloadVO(claims.getId(), claims.getSubject(), claims.getIssuer(), claims.getExpiration());
	}

	public static boolean validateJWT(JWTPayloadVO jwtPayloadVO, String username) {
		if (jwtPayloadVO.getExpirationDate().before(new Date())) {
			return false;
		} else if (!jwtPayloadVO.getId().equals(username)) {
			return false;
		} else if (!jwtPayloadVO.getIssuer().equals(CommonConstants.ISSUER)) {
			return false;
		} else if (!jwtPayloadVO.getSubject().equals(CommonConstants.SUBJECT)) {
			return false;
		}
		return true;
	}
}
