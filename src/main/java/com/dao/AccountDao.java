package com.dao;

import com.db.DB;
import com.google.gson.Gson;
import com.model.Error;
import spark.Request;
import spark.Response;
import com.model.Account;

public class AccountDao {

	private final DB db;

	public AccountDao(DB db) {
		this.db = db;
	}

	public Object addAccount(final Request request, final Response response) {
		final Account data = new Gson().fromJson(request.body(), Account.class);
		if (data == null) {
			return Error.from(400);
		}
		// Add to memory db
		db.addAccount(data);
		return data;
	}

	public Object getAccount(final Request request, final Response response) {
		// Get parameter from user request
		final String accountNum = request.params("accountNum");
		// Return specific account
		final Account account = db.getAccount(accountNum);
		if (account == null) {
			return Error.from(404);
		}
		return account;
	}

	public Account getAccount(final String accountNum) {
		return db.getAccount(accountNum);
	}

	public void update(final Account account) {
		db.updateBalance(account);
	}
}
