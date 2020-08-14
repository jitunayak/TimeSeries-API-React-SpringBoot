package com.LocationLocator.service;

import java.time.Instant;
import java.util.List;

import org.springframework.stereotype.Service;

import com.influxdb.annotations.Column;
import com.influxdb.annotations.Measurement;
import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import com.influxdb.client.WriteApi;
import com.influxdb.client.domain.WritePrecision;
import com.influxdb.client.write.Point;
import com.influxdb.query.FluxTable;


@Service
public class InFluxDbService  {
  public String WriteData(String used_percent) {
	  
    String token = "hu4FHmK_w8Gwxct70w0ehgi0R7wFB2zGBZ2dYkLvaAbiCEVIwYpjygffqLZTHgLelXYYo0ZOilGcIGF6UDQQtw==";
    String bucket = "bosch";
    String org = "bosch";

    InfluxDBClient client = InfluxDBClientFactory.create("http://localhost:9999", token.toCharArray());
    String data = "mem,host=host1 used_percent="+used_percent;
    try (WriteApi writeApi = client.getWriteApi()) {
      writeApi.writeRecord(bucket, org, WritePrecision.NS, data);
    }
	return data;
  }
  
  
}