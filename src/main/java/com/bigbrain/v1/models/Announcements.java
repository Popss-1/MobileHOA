package com.bigbrain.v1.models;

import java.util.Date;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;


public class Announcements {
	
	public enum Categories {
	       Urgent,
	       Social,
	       Informative
	    } 
	

	private int announcementPK;
	private int userIDFK;
	private String category;
	private Date announcementDate;
	private String title;
	private String description;
	private List<UsersAnnouncements> usersAnnouncements;

	public List<UsersAnnouncements> getUsersAnnouncements() {
		return usersAnnouncements;
	}

	public void addUsersAnnouncements(UsersAnnouncements usersAnnouncement) {
		usersAnnouncements.add(usersAnnouncement);
	}
	public int getAnnouncementPK() {
		return announcementPK;
	}

	public void setAnnouncementPK(int announcementPK) {
		this.announcementPK = announcementPK;
	}

	public int getUserIDFK() {
		return userIDFK;
	}

	public void setUserIDFK(int userIDFK) {
		this.userIDFK = userIDFK;
	}

	public String getCategory() {
		return category;
	}

	public void setSeverity(String category) {
		this.category = category;
	}

	public Date getAnnouncementDate() {
		return announcementDate;
	}

	public void setAnnouncementDate(Date announcementDate) {
		this.announcementDate = announcementDate;
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

	@Override
	public String toString() {
		return "Announcements [announcementPK=" + announcementPK + ", userIDFK=" + userIDFK + ", severity=" + category
				+ ", announcementDate=" + announcementDate + ", title=" + title + ", description=" + description + "]";
	}

}
