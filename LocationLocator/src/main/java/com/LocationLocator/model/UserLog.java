package com.LocationLocator.model;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.TimeZone;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document
public class UserLog {

	@Id
	String inserteDateTime;
	String userName;

	public UserLog(String userName) {
		this.inserteDateTime = OffsetDateTime.now(ZoneOffset.UTC).toString();
		this.userName = userName;
	}

	public UserLog() {

	}

	public String getInserteDateTime() {
		return inserteDateTime;
	}

	public void setInserteDateTime(String inserteDateTime) {
		this.inserteDateTime = inserteDateTime;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Override
	public String toString() {
		return "UserLog [inserteDateTime= " + inserteDateTime + ", for userName= " + userName + "]";
	}

}
