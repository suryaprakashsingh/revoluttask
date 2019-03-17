package com.dao;

import java.util.List;

import com.db.DB;
import com.google.gson.Gson;
import com.model.Account;
import com.model.Error;
import com.model.Transfer;

import spark.Request;
import spark.Response;

public class TransferDao {

	private final AccountDao accountDao;
	private final DB db;

	public TransferDao(AccountDao accountDao, DB db) {
		this.accountDao = accountDao;
		this.db = db;
	}

	public Object addTransfer(final Request request, final Response response) {
		final Transfer data = new Gson().fromJson(request.body(), Transfer.class);
		if (data == null) {
			return Error.from(400);
		}
		final Account from = accountDao.getAccount(data.getFrom());
		final Account to = accountDao.getAccount(data.getTo());
		if (from == null || to == null) {
			return Error.from(404);
		}
		// original balance
		final double fromBalance = from.getBalance();
		final double toBalance = to.getBalance();
		// Update balance
		from.setBalance(fromBalance - data.getAmount());
		to.setBalance(toBalance + data.getAmount());

		accountDao.update(from);
		accountDao.update(to);
		db.addTransfer(data);
		return data;

	}

	public List<Transfer> getTransfers(final Request request, final Response response) {
		final String accountNum = request.params("from");
		return db.getTransfers(accountNum);
	}

}