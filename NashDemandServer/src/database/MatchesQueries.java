package database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import shared.MatchObjectInterface;

public class MatchesQueries {
	private String matchTable = "matches";
	private String matchID = "idmatch";
	private String matchTime = "time";
	private String matchDate ="date";
	private String matchPlayer1 = "player1";
	private String matchPlayer2 = "player2";
	private Connection con;
	
	/**
	   * A MatchesQueries constructor,
	   * Constructs the MatchesQueries Object setting its connection. 
	   * @param connection, references the Connection.
	   */
	MatchesQueries(Connection connection){
		this.con = connection;
	}

	/**
	   * Allows to add a match into database.
	   * @param mch as MatchObjectInterface.
	   * @exception SQLException.
	   * @return returns integer >0 whether the match is registered, 0 otherwise.
	   */
	public int addMatch(MatchObjectInterface mch) throws SQLException {
		// TODO Auto-generated method stub
		String addMatchQuery = "INSERT INTO "+ matchTable +"(" + matchID + "," + matchTime + "," + matchDate + "," + matchPlayer1 + "," + matchPlayer2 + ")"
				+ " Values('" + mch.getIdMatch() + "','" + mch.getTime() + "','" + mch.getDate() + "','" + mch.getPlayer1() + "','" + mch.getPlayer2() + "')";
		System.out.println(addMatchQuery);
		Statement smt = con.createStatement();
		return smt.executeUpdate(addMatchQuery);
	}

}
