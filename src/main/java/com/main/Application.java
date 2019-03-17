package com.main;

import static spark.Spark.before;
import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.post;

import java.sql.SQLException;

import com.dao.AccountDao;
import com.dao.TransferDao;
import com.db.DB;
import com.google.gson.Gson;

public class Application {

	private static AccountDao accountDao;
	private static TransferDao transferDao;

	public static void main(String[] args) throws SQLException {
		DB db = new DB();
		accountDao = new AccountDao(db);
		transferDao = new TransferDao(accountDao, db);

		port(8080);
		before((req, res) -> res.type("application/json"));
		setEndpoints();
	}

	private static void setEndpoints() {
		Gson gson = new Gson();
		post("/api/v1/accounts", "application/json", accountDao::addAccount, gson::toJson);
		post("/api/v1/accounts/", "application/json", accountDao::addAccount, gson::toJson);
		get("/api/v1/accounts/:accountNum", "application/json", accountDao::getAccount, gson::toJson);
		post("/api/v1/transfers", "application/json", transferDao::addTransfer, gson::toJson);
		get("/api/v1/transfers/:from", "application/json", transferDao::getTransfers, gson::toJson);
	}

}
