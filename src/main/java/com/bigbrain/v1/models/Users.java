package com.bigbrain.v1.models;

import java.util.Date;
import java.util.List;

// datatype in sql server vs in model
public class Users {

    private int UserIdPK;
    private String emailAddress;
    private String firstName;
    private String lastName;
    private String password;
    private String role;
    private String subscriptionStatus;
    private Date subscriptionExpirationDate;
    private List<Bills> bills;
    private Address address;
	private List<Incidents> incidents;
	private List<Requests> requests;
	private List<UsersAnnouncements> usersAnnouncements;

    public Users(int userIdPK, String emailAddress, String firstName, String lastName, String password, String role, String subscriptionStatus, Address address) {
        UserIdPK = userIdPK;
        this.emailAddress = emailAddress;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.role = role;
        this.subscriptionStatus = subscriptionStatus;
        this.address = address;
    }

    public List<Requests> getRequests() {
		return requests;
	}

	public void addRequest(Requests request) {
		requests.add(request);
	}

	public List<UsersAnnouncements> getUsersAnnouncements() {
		return usersAnnouncements;
	}

	public void addUsersAnnouncements(UsersAnnouncements usersAnnouncement) {
		usersAnnouncements.add(usersAnnouncement);
	}

	public void setSubscriptionExpirationDate(Date subscriptionExpirationDate) {
		this.subscriptionExpirationDate = subscriptionExpirationDate;
	}

	public List<Incidents> getIncidents() {
		return incidents;
	}

	public void addIncidents(Incidents incident) {
		incidents.add(incident);
	}

	public Date getSubscriptionExpirationDate() {
        return subscriptionExpirationDate;
    }

    public List<Bills> getBills() {
        return bills;
    }

    public void addBills(Bills bill) {

        this.bills.add(bill);
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getSubscriptionStatus() {
        return subscriptionStatus;
    }

    public void setSubscriptionStatus(String subscriptionStatus) {
        this.subscriptionStatus = subscriptionStatus;
    }

    public Date getSubscriptionExpiration() {
        return subscriptionExpirationDate;
    }

    public void setSubscriptionExpiration(Date subscriptionExpiration) {
        this.subscriptionExpirationDate = subscriptionExpiration;
    }

    public enum roles {
        Admin,
        Homeowner,
        Maintainence,
        Manager
    }

    public enum SubscriptionStatues {
        Valid,
        Expired,
        Not_applicable
    }

}
