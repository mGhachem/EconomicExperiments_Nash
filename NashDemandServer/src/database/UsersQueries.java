package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import shared.Credential;
import shared.UserObjectInterface;

public class UsersQueries {
	private String userTable = "users";
	private String userId = "iduser";
	private String userNickname ="nickname";
	private String userEmail = "email";
	private String userPassword = "password";
	private Connection connection;

	   /**
	   * A UsersQueries constructor,
	   * Constructs the UsersQueries Object setting its connection. 
	   * @param connection, references the Connection.
	   */
	public UsersQueries(Connection con) {
		connection = con;
	}
	
	/**
	   * Allows to check the user registration by its Email.
	   * @param email as String.
	   * @exception SQLException.
	   * @return returns a UserObjectInterface object.
	   */
	public UserObjectInterface checkByEmail(String email) throws SQLException {
		// TODO Auto-generated method stub
		String queryCheck = "SELECT * FROM "+userTable+" WHERE "+userEmail+"='"+email+"'";
		System.out.println(queryCheck);
		Statement smt = connection.createStatement();
		ResultSet rs = smt.executeQuery(queryCheck);
		if (rs == null)
			return null;
		if(rs.next())
			return new UserObject(rs.getString(userId), rs.getString(userNickname), rs.getString(userEmail), rs.getString(userPassword));
		else
			return null;
	}
	
	/**
	   * Allows to add player into database.
	   * @param user as UserObjectInterface.
	   * @exception SQLException.
	   * @return returns integer >0 whether the user is registered, 0 otherwise.
	   */
	public int addUser(UserObjectInterface user) throws SQLException{
		// TODO Auto-generated method stub
		String addUserQuery = "INSERT INTO "+userTable+"("+userId+","+userNickname+","+userEmail+","+userPassword+")"
				+"Values('"+user.getIdUser()+"','"+user.getNickname()+"','"+user.getEmail()+"','"+user.getPassword()+"')";
		Statement smt = connection.createStatement();
		return smt.executeUpdate(addUserQuery);
	}
	
	/**
	   * Allows to check whether the player is registered or no.
	   * @param login as Credential.
	   * @exception SQLException.
	   * @return returns true whether the player is registered, otherwise false.
	   */
	public boolean checkUser(Credential login) throws SQLException{
		// TODO Auto-generated method stub
		String checkQuery = "SELECT * FROM "+userTable+ " WHERE "+userNickname+" = '"+login.getNickname()+"'"
				+" OR "+userEmail+" = '"+login.getEmail()+"'";
		 PreparedStatement smt = connection.prepareStatement(checkQuery);
		 ResultSet rs = smt.executeQuery();
		 return !rs.next();
	}

}
