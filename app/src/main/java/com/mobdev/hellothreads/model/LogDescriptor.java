package com.mobdev.hellothreads.model;

/**
 * Created by Marco Picone (picone.m@gmail.com) 20/03/2020
 * Generic Data Structure
 */
public class LogDescriptor {

	private int id;

	private long timestamp = 0;

	private double latitude = 0.0;

	private double longitude = 0.0;

	private String type = null;

	private String data = null;
	
	public LogDescriptor() {
	}

	public LogDescriptor(double latitude, double longitude, String type, String data) {
		super();
		this.timestamp = System.currentTimeMillis();
		this.latitude = latitude;
		this.longitude = longitude;
		this.type = type;
		this.data = data;
	}

 	public LogDescriptor(long timestamp, double latitude, double longitude,
                         String type, String data) {
		super();
		this.timestamp = timestamp;
		this.latitude = latitude;
		this.longitude = longitude;
		this.type = type;
		this.data = data;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
	
	
	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Timestamp: "+timestamp+" Lat: "+ latitude+" Lon:"+longitude+" Type:"+type+" Data:"+data;
	}

	@Override
	public boolean equals(Object o) {
		LogDescriptor logObj = (LogDescriptor)o;
		if(logObj.timestamp == this.timestamp)
			return true;
		else
			return false;
	}
}

