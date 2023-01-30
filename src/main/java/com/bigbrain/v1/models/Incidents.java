package com.bigbrain.v1.models;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.persistence.SequenceGenerator;


public class Incidents {

	public enum Statues {
       New,
       Expired,
       Resolved
    } 
	

	private int incidentIDPK;

	private String incidentCategory;

	private String incidentStatus;

	private int userIDFK;

	private String reportedByPhoneNumber;

	private double longtitude;

	private double latitude;

	private String title;

	private Date incidentDate;

	public Date getIncidentDate() {
		return incidentDate;
	}

	public void setIncidentDate(Date incidentDate) {
		this.incidentDate = incidentDate;
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

	public double getLongtitude() {
		return longtitude;
	}

	public void setLongtitude(double longtitude) {
		this.longtitude = longtitude;
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

	@Override
	public String toString() {
		return "Incidents [incidentIDPK=" + incidentIDPK + ", incidentCategory=" + incidentCategory
				+ ", incidentStatus=" + incidentStatus + ", userIDFK=" + userIDFK + ", reportedByPhoneNumber="
				+ reportedByPhoneNumber + ", longtitude=" + longtitude + ", latitude=" + latitude + ", title=" + title
				+ ", incidentDate=" + incidentDate + "]";
	}

}
