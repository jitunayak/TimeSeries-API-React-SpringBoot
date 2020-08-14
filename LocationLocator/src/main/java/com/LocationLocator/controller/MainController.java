package com.LocationLocator.controller;

import java.time.Clock;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.LocationLocator.model.UserLog;
import com.LocationLocator.service.InFluxDbService;
import com.LocationLocator.service.MongoDbService;

@RestController
public class MainController {

	@Autowired
	InFluxDbService service;

	@GetMapping("/listHeaders")
	public ResponseEntity<String> listAllHeaders(@RequestHeader Map<String, String> headers) {

		headers.forEach((key, value) -> {
			System.out.println((String.format("Header '%s' = %s", key, value)));
		});

		return new ResponseEntity<String>(String.format("Listed %d headers", headers.size()), HttpStatus.OK);
	}

	@GetMapping("/date/")
	public String date() {
		// System.out.println(Instant.now().toString());
		OffsetDateTime now = OffsetDateTime.now(ZoneOffset.UTC);
		System.out.println("Server timeStamp in UTC " + now);
		// System.out.println(ZonedDateTime.now().getZone());
		// System.out.println(ZonedDateTime.now().getOffset().getTotalSeconds());
		int now1 = ZonedDateTime.now().getOffset().getTotalSeconds();
		OffsetDateTime off = OffsetDateTime.now(ZoneOffset.UTC).minusSeconds(now1);
		return "Client's Time = " + off.getHour() + ":" + off.getMinute();

	}

	@Autowired
	MongoDbService mongoService;

	@GetMapping("/date/{offset}/{username}")
	@CrossOrigin(origins = "http://localhost:3000")

	public String getMyDate(@PathVariable("offset") String offset, @PathVariable("username") String username) {
		// System.out.println("Received : "+Integer.valueOf(offset));

		UserLog userLog = mongoService.saveUserLog(username);
		OffsetDateTime off = OffsetDateTime.now(ZoneOffset.UTC).minusMinutes(Integer.valueOf(offset));
		// String offsetQuery = "var offset = new Date().getTimezoneOffset();";
		return "\n\nClient's Time = " + off.getHour() + ":" + off.getMinute() + " ";

	}

	@CrossOrigin(origins = "http://localhost:3000")
	@GetMapping("/all/{offset}")

	public ResponseEntity<List<UserLog>> writeData(@PathVariable("offset") String offset) {
		List<UserLog> allusers = mongoService.findAllUsers();

		List<UserLog> userInOffSetList = new ArrayList<>();
		
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
		for (UserLog userLog : allusers) {
			userLog.setInserteDateTime(OffsetDateTime.parse(userLog.getInserteDateTime())
					.minusMinutes(Integer.valueOf(offset)).toString());
			userInOffSetList.add(userLog);
			//System.out.println(userLog);
		}
		
		//userInOffSetList.parallelStream().map( )
		
		ResponseEntity<List<UserLog>> responseEntity = new ResponseEntity<>(userInOffSetList, HttpStatus.OK);
		return responseEntity;

	}

}
