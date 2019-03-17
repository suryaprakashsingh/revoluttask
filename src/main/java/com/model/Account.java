package com.model;

public class Account {

	private String accountNum;
	private double balance;

	public Account(final String accountNum, final double balance) {
		this.accountNum = accountNum;
		this.balance = balance;
	}

	public String getAccountNum() {
		return accountNum;
	}

	public void setAccountNum(final String accountNum) {
		this.accountNum = accountNum;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(final double balance) {
		this.balance = balance;
	}

	@Override
	public String toString() {
		return "Account{" + " accountNum='" + accountNum + '\'' + ", balance=" + balance + '}';
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Account that = (Account) obj;

		if (!accountNum.equalsIgnoreCase(that.accountNum)) {
			return false;
		}
		return true;
	}
}
