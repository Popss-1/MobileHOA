package com.bigbrain.v1.models;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.validation.constraints.NotNull;

// spring batch generate every month
public class Bills {
	

	private int billIDPK;

	private int userIDFK;

	private Date billDate;

	private BigInteger amountDue;
	private List<Payments> payments;

	public List<Payments> getPayments() {
		return payments;
	}

	public void addPayments(Payments payment) {
		payments.add(payment);
	}

	public String toString() {
		return "Bills [billIDPK=" + billIDPK + ", userIDFK=" + userIDFK + ", billDate=" + billDate + ", amountDue="
				+ amountDue + "]";
	}
	public int getBillIDPK() {
		return billIDPK;
	}
	public void setBillIDPK(int billIDPK) {
		this.billIDPK = billIDPK;
	}
	public int getUserIDFK() {
		return userIDFK;
	}
	public void setUserIDFK(int userIDFK) {
		this.userIDFK = userIDFK;
	}
	public Date getBillDate() {
		return billDate;
	}
	public void setBillDate(Date billDate) {
		this.billDate = billDate;
	}
	public BigInteger getAmountDue() {
		return amountDue;
	}
	public void setAmountDue(BigInteger amountDue) {
		this.amountDue = amountDue;
	}
	
}
