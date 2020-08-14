package com.LocationLocator.DAO;

import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.LocationLocator.model.UserLog;

public interface MongoDAO extends MongoRepository<UserLog, OffsetDateTime> {

	public List<UserLog> findByuserName(String name);
}
