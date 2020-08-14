package com.LocationLocator.service;

import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.LocationLocator.DAO.MongoDAO;
import com.LocationLocator.model.UserLog;

@Service
public class MongoDbService {

	@Autowired
	MongoDAO mongoDAO;

	public List<UserLog> findAllUsers() {
		System.out.println("Fetching....");
		//List<UserLog> usersList = 
		return mongoDAO.findAll();
	}

	public UserLog saveUserLog(String username) {
		UserLog log = new UserLog(username);
		mongoDAO.save(log);
		System.out.println("Data Saved");
		return log;
	}
	
	
}
