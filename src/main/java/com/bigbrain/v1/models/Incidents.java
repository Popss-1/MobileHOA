package com.bigbrain.v1.models;

import java.sql.Date;


public class Incidents {

	public enum Statues {
       New,
       Expired, // after 6 months it expires
       Resolved
    } 
	

	private int incidentIDPK;

	private String incidentCategory;

	private String incidentStatus;
	private String description;

	private int userIDFK;

	private String reportedByPhoneNumber;

	private double longitude;

	private double latitude;

	private String title;

	private Date incidentDate;


	public Incidents(String incidentCategory, String incidentStatus, String description, int userIDFK, String reportedByPhoneNumber, double longitude, double latitude, String title, Date incidentDate) {
		this.incidentCategory = incidentCategory;
		this.incidentStatus = incidentStatus;
		this.description = description;
		this.userIDFK = userIDFK;
		this.reportedByPhoneNumber = reportedByPhoneNumber;
		this.longitude = longitude;
		this.latitude = latitude;
		this.title = title;
		this.incidentDate = incidentDate;
	}

	public Incidents(){

	}

	public int getIncidentIDPK() {
		return incidentIDPK;
	}

	public void setIncidentIDPK(int incidentIDPK) {
		this.incidentIDPK = incidentIDPK;
	}

	public String getIncidentCategory() {
		return incidentCategory;
	}

	public void setIncidentCategory(String incidentCategory) {
		this.incidentCategory = incidentCategory;
	}

	public String getIncidentStatus() {
		return incidentStatus;
	}

	public void setIncidentStatus(String incidentStatus) {
		this.incidentStatus = incidentStatus;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getUserIDFK() {
		return userIDFK;
	}

	public void setUserIDFK(int userIDFK) {
		this.userIDFK = userIDFK;
	}

	public String getReportedByPhoneNumber() {
		return reportedByPhoneNumber;
	}

	public void setReportedByPhoneNumber(String reportedByPhoneNumber) {
		this.reportedByPhoneNumber = reportedByPhoneNumber;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getIncidentDate() {
		return incidentDate;
	}

	public void setIncidentDate(Date incidentDate) {
		this.incidentDate = incidentDate;
	}

	@Override
	public String toString() {
		return "Incidents{" +
				"incidentIDPK=" + incidentIDPK +
				", incidentCategory='" + incidentCategory + '\'' +
				", incidentStatus='" + incidentStatus + '\'' +
				", description='" + description + '\'' +
				", userIDFK=" + userIDFK +
				", reportedByPhoneNumber='" + reportedByPhoneNumber + '\'' +
				", longitude=" + longitude +
				", latitude=" + latitude +
				", title='" + title + '\'' +
				", incidentDate=" + incidentDate +
				'}';
	}
}
