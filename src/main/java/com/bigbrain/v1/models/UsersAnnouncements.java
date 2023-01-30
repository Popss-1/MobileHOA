package com.bigbrain.v1.models;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;


public class UsersAnnouncements {


	private int UsersAnnouncementsPK;
	private int userIDFK;
	private int announcementIDFK;
	private Date createDate;
	private Date editDate;

	public int getUsersAnnouncementsPK() {
		return UsersAnnouncementsPK;
	}

	public void setUsersAnnouncementsPK(int usersAnnouncementsPK) {
		UsersAnnouncementsPK = usersAnnouncementsPK;
	}

	public int getUserIDFK() {
		return userIDFK;
	}

	public void setUserIDFK(int userIDFK) {
		this.userIDFK = userIDFK;
	}

	public int getAnnouncementIDFK() {
		return announcementIDFK;
	}

	public void setAnnouncementIDFK(int announcementIDFK) {
		this.announcementIDFK = announcementIDFK;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getEditDate() {
		return editDate;
	}

	public void setEditDate(Date editDate) {
		this.editDate = editDate;
	}

	@Override
	public String toString() {
		return "UsersAnnouncements [UsersAnnouncementsPK=" + UsersAnnouncementsPK + ", userIDFK=" + userIDFK
				+ ", announcementIDFK=" + announcementIDFK + ", createDate=" + createDate + ", editDate=" + editDate
				+ "]";
	}

}
