package com.db;

import com.model.Account;
import com.model.Transfer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DB {

	private static final String DB_DRIVER = "org.h2.Driver";
	private static final String DB_CONNECTION = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1";
	private static final String DB_USER = "";
	private static final String DB_PASSWORD = "";

	public DB() throws SQLException {
		createTables();
	}


	public void createTables() throws SQLException {
		Connection connection = getDBConnection();
		PreparedStatement createPreparedStatement = null;

		String CreateAccountTableQuery = "CREATE TABLE ACCOUNT(accountno varchar(20) primary key, balance double(12))";
		String CreateTransactionTableQuery = "CREATE TABLE TRANSACTIONS(transactionno varchar(40) primary key, amount double(12), accountfrom varchar(20), accountto varchar(20) not null)";
		String AlterTableQuery = "ALTER TABLE TRANSACTIONS add foreign key (accountto) references ACCOUNT(accountno)";

		try {
			connection.setAutoCommit(false);

			createPreparedStatement = connection.prepareStatement(CreateAccountTableQuery);
			createPreparedStatement.executeUpdate();
			createPreparedStatement.close();

			createPreparedStatement = connection.prepareStatement(CreateTransactionTableQuery);
			createPreparedStatement.executeUpdate();
			createPreparedStatement.close();

			createPreparedStatement = connection.prepareStatement(AlterTableQuery);
			createPreparedStatement.executeUpdate();
			createPreparedStatement.close();

			connection.commit();
		} catch (SQLException e) {
			System.out.println("Exception Message " + e.getLocalizedMessage());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			connection.close();
		}
	}

	public void addAccount(Account data) {
		Connection connection = getDBConnection();
		String InsertQuery = "INSERT INTO ACCOUNT" + "(accountno, balance) values" + "(?,?)";

		try {
			connection.setAutoCommit(false);

			PreparedStatement insertPreparedStatement = connection.prepareStatement(InsertQuery);
			insertPreparedStatement.setString(1, data.getAccountNum());
			insertPreparedStatement.setDouble(2, data.getBalance());
			insertPreparedStatement.executeUpdate();
			insertPreparedStatement.close();

			connection.commit();
		} catch (SQLException e) {
			System.out.println("Exception Message " + e.getLocalizedMessage());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException ex) {
				System.out.println("Exception Message " + ex.getLocalizedMessage());
			}
		}
	}

	public Account getAccount(String accountNum) {
		Connection connection = getDBConnection();
		PreparedStatement selectPreparedStatement = null;
		Account data = null;
		String SelectQuery = "select * from ACCOUNT where accountno = ?";

		try {
			selectPreparedStatement = connection.prepareStatement(SelectQuery);
			selectPreparedStatement.setString(1, accountNum);
			ResultSet rs = selectPreparedStatement.executeQuery();

			while (rs.next()) {
				data = new Account(rs.getString("accountno"), rs.getDouble("balance"));
			}
			selectPreparedStatement.close();

		} catch (SQLException e) {
			System.out.println("Exception Message " + e.getLocalizedMessage());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException ex) {
				System.out.println("Exception Message " + ex.getLocalizedMessage());
			}
		}
		return data;
	}

	private Connection getDBConnection() {
		Connection dbConnection = null;
		try {
			Class.forName(DB_DRIVER);
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
		}
		try {
			dbConnection = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
			return dbConnection;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return dbConnection;
	}

	public void updateBalance(Account account) {
		Connection connection = getDBConnection();
		String InsertQuery = "UPDATE ACCOUNT SET balance = ? WHERE accountno = ?";

		try {
			connection.setAutoCommit(false);

			PreparedStatement insertPreparedStatement = connection.prepareStatement(InsertQuery);
			insertPreparedStatement.setDouble(1, account.getBalance());
			insertPreparedStatement.setString(2, account.getAccountNum());
			insertPreparedStatement.executeUpdate();
			insertPreparedStatement.close();

			connection.commit();
		} catch (SQLException e) {
			System.out.println("Exception Message " + e.getLocalizedMessage());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException ex) {
				System.out.println("Exception Message " + ex.getLocalizedMessage());
			}
		}
	}

	public void addTransfer(Transfer data) {
		Connection connection = getDBConnection();
		String InsertQuery = "INSERT INTO TRANSACTIONS" + "(transactionno, amount, accountfrom, accountto) values"
				+ "(?,?,?,?)";

		try {
			connection.setAutoCommit(false);

			PreparedStatement insertPreparedStatement = connection.prepareStatement(InsertQuery);
			insertPreparedStatement.setString(1, UUID.randomUUID().toString());
			insertPreparedStatement.setDouble(2, data.getAmount());
			insertPreparedStatement.setString(3, data.getFrom());
			insertPreparedStatement.setString(4, data.getTo());
			insertPreparedStatement.executeUpdate();
			insertPreparedStatement.close();

			connection.commit();
		} catch (SQLException e) {
			System.out.println("Exception Message " + e.getLocalizedMessage());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException ex) {
				System.out.println("Exception Message " + ex.getLocalizedMessage());
			}
		}
	}

	public List<Transfer> getTransfers(String accountNum) {
		Connection connection = getDBConnection();
		PreparedStatement selectPreparedStatement = null;
		List<Transfer> list = new ArrayList<Transfer>();
		String SelectQuery = "select * from TRANSACTIONS where accountfrom = ?";

		try {
			selectPreparedStatement = connection.prepareStatement(SelectQuery);
			selectPreparedStatement.setString(1, accountNum);
			ResultSet rs = selectPreparedStatement.executeQuery();

			while (rs.next()) {
				list.add(new Transfer(rs.getString("transactionno"), rs.getString("accountfrom"),
						rs.getString("accountto"), rs.getDouble("amount")));
			}
			selectPreparedStatement.close();

		} catch (SQLException e) {
			System.out.println("Exception Message " + e.getLocalizedMessage());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException ex) {
				System.out.println("Exception Message " + ex.getLocalizedMessage());
			}
		}
		return list;
	}
}
