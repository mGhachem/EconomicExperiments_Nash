package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import shared.Credential;
import shared.DataBaseManagerInterface;
import shared.MatchObjectInterface;
import shared.MoveObjectInterface;
import shared.UserObjectInterface;

public class DataBaseManager implements DataBaseManagerInterface {
	private static DataBaseManager dbManager;
	private UsersQueries userquery;
	private MovesQueries movequery;
	private MatchesQueries matchquery;
	private Connection connection;

	/**
	   * A DataBaseManager constructor,
	   * Constructs the DataBaseManager Object. 
	   * @param url as String, userID as String and password as String.
	   * @exception SQLException. 
	   */
	private DataBaseManager(String url, String userID, String password) throws SQLException {
		connection = DriverManager.getConnection("jdbc:postgresql://" +url+ "/NDG", userID, password);
	}

	/**
	   * A method called to construct the DataBaseManager Object
	   *  setting its url, userID and password. 
	   * @param url as String, userID as String and password as String.
	   * @exception SQLException.
	   * @return returns a DataBaseManager object.
	   */
	public static DataBaseManager createDBManager(String url, String userID, String password) throws SQLException {
		if (dbManager == null) {
			dbManager = new DataBaseManager(url, userID, password);
			return dbManager;
		} else
			return dbManager;
	}
	
	/**
	   * Allows to check the user registration by its Email.
	   * @param email as String.
	   * @exception SQLException.
	   * @return returns a UserObjectInterface object.
	   */
	@Override
	public UserObjectInterface checkByEmail(String email) throws SQLException {
		// TODO Auto-generated method stub
		if(userquery== null)
			userquery = new UsersQueries(connection);
		return userquery.checkByEmail(email);
	}
	
	/**
	   * Allows to add player into database.
	   * @param user as UserObjectInterface.
	   * @exception SQLException.
	   * @return returns integer >0 whether the user is registered, 0 otherwise.
	   */
	@Override
	public int addUser(UserObjectInterface user) throws SQLException {
		if(userquery== null)
			userquery = new UsersQueries(connection);
		// TODO Auto-generated method stub
		return userquery.addUser(user);
	}
	
	/**
	   * Gets the player's average score.
	   * @param idUser as String.
	   * @exception SQLException.
	   * @return returns double as an average score.
	   */
	@Override
	public double getAverageScore(String idUser) throws SQLException {
		if(movequery== null)
			movequery = new MovesQueries(connection);
		// TODO Auto-generated method stub
		return movequery.getAverageScore(idUser);
	}

	/**
	   * Gets the player's ranking.
	   * @param idUser as String.
	   * @exception SQLException.
	   * @return returns integer as a ranking.
	   */
	@Override
	public int getRanking(String idUser) throws SQLException {
		if(movequery== null)
			movequery = new MovesQueries(connection);
		// TODO Auto-generated method stub
		return movequery.getRanking(idUser);
	}

	/**
	   * Allows to add player's choice into database.
	   * @param mv as MoveObjectInterface.
	   * @exception SQLException.
	   * @return returns integer >0 whether the move is registered, 0 otherwise.
	   */
	public int addMove(MoveObjectInterface mv) throws SQLException {
		if(movequery== null)
			movequery = new MovesQueries(connection);
		// TODO Auto-generated method stub
		return movequery.addMove(mv);
	}

	/**
	   * Allows to add a match into database.
	   * @param mch as MatchObjectInterface.
	   * @exception SQLException.
	   * @return returns integer >0 whether the match is registered, 0 otherwise.
	   */
	@Override
	public int addMatch(MatchObjectInterface mch) throws SQLException {
		if(matchquery== null)
			matchquery = new MatchesQueries(connection);
		// TODO Auto-generated method stub
		return matchquery.addMatch(mch);
	}

	/**
	   * Allows to check whether the player is registered or no.
	   * @param login as Credential.
	   * @exception SQLException.
	   * @return returns true whether the player is registered, otherwise false.
	   */
	@Override
	public boolean checkUser(Credential login) throws SQLException {
		// TODO Auto-generated method stub
		if(userquery== null)
			userquery = new UsersQueries(connection);
		return userquery.checkUser(login);
	}

	/**
	   * Allows to get the best move for an AI player
	   * relatively to human previous choice.
	   * @param integer as choice.
	   * @exception SQLException.
	   * @return returns integer as a best move.
	   */
	@Override
	public int getBestMove(int choice) throws SQLException {
		// TODO Auto-generated method stub
		if(movequery== null)
			movequery = new MovesQueries(connection);
		// TODO Auto-generated method stub
		return movequery.getBestMove(choice);
	}

}
