package com.akash.demo.vo;

import java.util.Date;

public class JWTPayloadVO {
	String id;
	String subject;
	String issuer;
	Date expirationDate;

	public JWTPayloadVO(String id, String subject, String issuer, Date expirationDate) {
		super();
		this.id = id;
		this.subject = subject;
		this.issuer = issuer;
		this.expirationDate = expirationDate;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getIssuer() {
		return issuer;
	}

	public void setIssuer(String issuer) {
		this.issuer = issuer;
	}

	public Date getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

}
