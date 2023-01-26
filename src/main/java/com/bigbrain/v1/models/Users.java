package com.bigbrain.v1.models;

import javax.persistence.*;

import jakarta.persistence.Entity;
import jakarta.persistence.GenerationType;
@Entity
public class Users {

	@Id
	@GeneratedValues(Strategy = GenerationType.IDENTITY)
	private int UserIdPK;
	private String emailAddress;
	private String firstName;
	private String lastName;
	private String password;
	private String roleCodeIDFK;
	private String subscriptionStatusCodeID;
	
	public int getUserIdPK() {
		return UserIdPK;
	}
	public void setUserIdPK(int userIdPK) {
		UserIdPK = userIdPK;
	}
	public String getEmailAddress() {
		return emailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRoleCodeIDFK() {
		return roleCodeIDFK;
	}
	public void setRoleCodeIDFK(String roleCodeIDFK) {
		this.roleCodeIDFK = roleCodeIDFK;
	}
	public String getSubscriptionStatusCodeID() {
		return subscriptionStatusCodeID;
	}
	public void setSubscriptionStatusCodeID(String subscriptionStatusCodeID) {
		this.subscriptionStatusCodeID = subscriptionStatusCodeID;
	}
	
	@Override
	public String toString() {
		return "Users [UserIdPK=" + UserIdPK + ", emailAddress=" + emailAddress + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", password=" + password + ", roleCodeIDFK=" + roleCodeIDFK
				+ ", subscriptionStatusCodeID=" + subscriptionStatusCodeID + "]";
	}
	
}
