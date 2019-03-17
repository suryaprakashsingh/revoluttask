package com.model;

import java.util.UUID;

public class Transfer {

	private final String txid;
	private final String from;
	private final String to;
	private final double amount;

	public Transfer(final String from, final String to, final double amount) {
		this.txid = UUID.randomUUID().toString();
		this.from = from;
		this.to = to;
		this.amount = amount;
	}

	public Transfer(final String txid, final String from, final String to, final double amount) {
		this.txid = txid;
		this.from = from;
		this.to = to;
		this.amount = amount;
	}

	public String getTxid() {
		return txid;
	}

	public String getFrom() {
		return from;
	}

	public String getTo() {
		return to;
	}

	public double getAmount() {
		return amount;
	}

	public static Transfer from(final Transfer data) {
		return new Transfer(data.getFrom(), data.getTo(), data.getAmount());
	}

	@Override
	public String toString() {
		return "Transfer{" + "txid=" + txid + ", from=" + from + ", to=" + to + ", amount=" + amount + '}';
	}
}
