package com.epam.hotelmanagement.dao;

//THIS CLASS IMPLEMENTS ALL THE CRUDS OPERATIONS (create, read, update, delete)

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.epam.hotelmanagement.bean.Resident;

public class ResidentDao {
	
	private String jdbcURL = "jdbc:mysql://localhost:3306/resdb?useSSL=false";
	private String jdbcUsername = "root";
	private String jdbcPassword = "krivoguza153";
	private String jdbcDriver = "com.mysql.jdbc.Driver"; //Assigning driver name
	
	//SQL Queries to store the variables and store the resident to the database
	//according to id and for removing him
	
	private static final String INSERT_RES_SQL = "INSERT INTO residents" + " (name, email, country, room) VALUES"
	+ "(?, ?, ?, ?);";
	
	private static final String SELECT_RES_BY_ID = "select id, name, email, country, room from residents where id = ?";
	private static final String SELECT_ALL_RES = "select * from residents";
	private static final String DELETE_RES_SQL = "delete from residents where id = ?;";
	private static final String UPDATE_RES_SQL = "update residents set name = ?, email = ?, country = ? room = ? where id = ?;";
	
	public ResidentDao() {
	}
	
	//Separate method for getting connection of the JDBC
	//the driver is loaded inside this method and then method returns connection
	
	protected Connection getConnection() {
		Connection connection = null;
		try {
			Class.forName(jdbcDriver); //loading the jdbc by the name of the driver
			connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword); //take the connection by using getconnection method
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return connection;
		
	}
	
	
	
	// insert resident
	public void insertResident( Resident resident) throws SQLException{
		System.out.println(INSERT_RES_SQL);  //output in console
		try (Connection connection = getConnection(); //get connection of jdbc
				PreparedStatement preparedStatement = connection.prepareStatement(INSERT_RES_SQL)){
			preparedStatement.setString(1, resident.getName());
			preparedStatement.setString(2, resident.getEmail());
			preparedStatement.setString(3, resident.getCountry());
			preparedStatement.setString(4, resident.getRoom());
			System.out.println(preparedStatement);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			printSQLException(e); //handle all exceptions which we will get
		}
	}


	
	// select resident by id
	public Resident selectResident(int id) {
		Resident resident = null;
		// Step 1: Establishing a Connection
		try (Connection connection = getConnection();
				// Step 2:Create a statement using connection object
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_RES_BY_ID);) {
			preparedStatement.setInt(1, id);
			System.out.println(preparedStatement);
			// Step 3: Execute the query or update query
			ResultSet rs = preparedStatement.executeQuery();

			// Step 4: Process the ResultSet object.
			while (rs.next()) {
				String name = rs.getString("name");
				String email = rs.getString("email");
				String country = rs.getString("country");
				String room = rs.getString("room");
				resident = new Resident(id, name, email, country, room);
			}
		} catch (SQLException e) {
			printSQLException(e);
		}
		return resident;
	}
	
	// select all residents (the method is list, because we take all the residents
	public List<Resident> selectAllResidents(){
		// using try-with-resources to avoid closing resources (boiler plate code)
		List<Resident> residents = new ArrayList<>();
		// Step 1: Establishing a Connection
		try (Connection connection = getConnection();

				// Step 2:Create a statement using connection object
			PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_RES);) {
			System.out.println(preparedStatement);
			// Step 3: Execute the query or update query
			ResultSet rs = preparedStatement.executeQuery();

			// Step 4: Process the ResultSet object.
			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				String email = rs.getString("email");
				String country = rs.getString("country");
				String room = rs.getString("room");
				residents.add(new Resident(id, name, email, country, room));
			}
		} catch (SQLException e) {
			printSQLException(e);
		}
		return residents;
	}
	
	// update resident
	public boolean updateResident(Resident resident) throws SQLException {
		boolean rowUpdated;
		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(UPDATE_RES_SQL);) {
			System.out.println("updated Resident:"+statement);
			statement.setString(1, resident.getName());
			statement.setString(2, resident.getEmail());
			statement.setString(3, resident.getCountry());
			statement.setString(4, resident.getRoom());
			statement.setInt(5, resident.getId());

			rowUpdated = statement.executeUpdate() > 0;
		}
		return rowUpdated;
	}
	
	// delete resident
	
	public boolean deleteResident(int id) throws SQLException {
		boolean rowDeleted;
		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(DELETE_RES_SQL);) {
			statement.setInt(1, id);
			rowDeleted = statement.executeUpdate() > 0;
		}
		return rowDeleted;
	}
	
	
	private void printSQLException(SQLException ex) {
		for (Throwable e : ex) {
			if (e instanceof SQLException) {
				e.printStackTrace(System.err);
				System.err.println("SQLState: " + ((SQLException) e).getSQLState());
				System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
				System.err.println("Message: " + e.getMessage());
				Throwable t = ex.getCause();
				while (t != null) {
					System.out.println("Cause: " + t);
					t = t.getCause();
				}
				
			}
		}
		
	}
	
	//Method for hashing password of database
	public static String doHashing(String password) {
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			
			messageDigest.update(password.getBytes()); 
			
			messageDigest.digest();
			byte[] resultByteArray = messageDigest.digest();
			
			StringBuilder sb = new StringBuilder();
			
			for (byte b : resultByteArray) {
				sb.append(String.format("%02x", b));
			}
			
			return sb.toString();
			
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		return "";
	}
	
}
