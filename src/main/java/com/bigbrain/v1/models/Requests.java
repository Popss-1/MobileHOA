package com.bigbrain.v1.models;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.validation.constraints.NotNull;


public class Requests {

	public enum Priorities {
        low,
        Medium,
        High,
        Servere
    }
	
	public enum Statuses {
        Received,
        Assigned,
        In_progress,
        Completed,
        Delayed
    }
	

	private int requestIDPK;

	private int requestUserIDFK;

	private String title;

	private String description;

	private short statusCodeIDFK;

	private short prioritycodeIDFK;

	private Date requestDate;

	private int addressIDFK;

	private int assignedUserIDMTMFK;

	private String priority;

	private String status;

	private Address address;

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}
	
	
	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getRequestIDPK() {
		return requestIDPK;
	}

	public void setRequestIDPK(int requestIDPK) {
		this.requestIDPK = requestIDPK;
	}

	public int getRequestUserIDFK() {
		return requestUserIDFK;
	}

	public void setRequestUserIDFK(int requestUserIDFK) {
		this.requestUserIDFK = requestUserIDFK;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public short getStatusCodeIDFK() {
		return statusCodeIDFK;
	}

	public void setStatusCodeIDFK(short statusCodeIDFK) {
		this.statusCodeIDFK = statusCodeIDFK;
	}

	public short getPrioritycodeIDFK() {
		return prioritycodeIDFK;
	}

	public void setPrioritycodeIDFK(short prioritycodeIDFK) {
		this.prioritycodeIDFK = prioritycodeIDFK;
	}

	public Date getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}

	public int getAddressIDFK() {
		return addressIDFK;
	}

	public void setAddressIDFK(int addressIDFK) {
		this.addressIDFK = addressIDFK;
	}

	public int getAssignedUserIDMTMFK() {
		return assignedUserIDMTMFK;
	}

	public void setAssignedUserIDMTMFK(int assignedUserIDMTMFK) {
		this.assignedUserIDMTMFK = assignedUserIDMTMFK;
	}

	@Override
	public String toString() {
		return "Requests [requestIDPK=" + requestIDPK + ", requestUserIDFK=" + requestUserIDFK + ", title=" + title
				+ ", description=" + description + ", statusCodeIDFK=" + statusCodeIDFK + ", prioritycodeIDFK="
				+ prioritycodeIDFK + ", requestDate=" + requestDate + ", addressIDFK=" + addressIDFK
				+ ", assignedUserIDMTMFK=" + assignedUserIDMTMFK + ", priority=" + priority + ", status=" + status
				+ ", address=" + address + "]";
	}

	

}
