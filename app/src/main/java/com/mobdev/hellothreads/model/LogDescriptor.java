package com.mobdev.hellothreads.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;


@Entity(tableName = "logs")
public class LogDescriptor {

	@PrimaryKey(autoGenerate = true)
	private int id;

	@ColumnInfo(name = "timestamp")
	private long timestamp = 0;

	@ColumnInfo(name = "latitude")
	private double latitude = 0.0;

	@ColumnInfo(name = "longitude")
	private double longitude = 0.0;

	@ColumnInfo(name = "type")
	private String type = null;

	@ColumnInfo(name = "data")
	private String data = null;

	public LogDescriptor() {
	}

	@Ignore
	public LogDescriptor(double latitude, double longitude, String type, String data) {
		super();
		this.timestamp = System.currentTimeMillis();
		this.latitude = latitude;
		this.longitude = longitude;
		this.type = type;
		this.data = data;
	}

	@Ignore
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

