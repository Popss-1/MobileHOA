package com.bigbrain.v1.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.validation.constraints.NotNull;


public class Payments {

	private int paymentIDPK;

	private float creditCardNumber;

	private int CVV;

	private int expirationMonth;

	private int expirationYear;
	
	public int getPaymentIDPK() {
		return paymentIDPK;
	}
	public void setPaymentIDPK(int paymentIDPK) {
		this.paymentIDPK = paymentIDPK;
	}
	public float getCreditCardNumber() {
		return creditCardNumber;
	}
	public void setCreditCardNumber(float creditCardNumber) {
		this.creditCardNumber = creditCardNumber;
	}
	public int getCVV() {
		return CVV;
	}
	public void setCVV(int cVV) {
		CVV = cVV;
	}
	public int getExpirationMonth() {
		return expirationMonth;
	}
	public void setExpirationMonth(int expirationMonth) {
		this.expirationMonth = expirationMonth;
	}
	public int getExpirationYear() {
		return expirationYear;
	}
	public void setExpirationYear(int expirationYear) {
		this.expirationYear = expirationYear;
	}
	
	@Override
	public String toString() {
		return "Payments [paymentIDPK=" + paymentIDPK + ", creditCardNumber=" + creditCardNumber + ", CVV=" + CVV
				+ ", expirationMonth=" + expirationMonth + ", expirationYear=" + expirationYear + "]";
	}
	
	
}
