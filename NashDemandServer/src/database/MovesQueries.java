package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import shared.MoveObjectInterface;

public class MovesQueries {
	private String movesTable = "moves";
	private String moveID = "idmove";
	private String moveMatchId = "idmatch";
	private String moveRound ="round";
	private String movePlayer = "player";
	private String moveNumber = "number";
	private String moveResult = "result";
	private String moveRankingView = "ranking";
	private String moveRankingViewScore ="score";
	private String moveRankingViewPlayer ="player";
	private Connection con;
	

	/**
	   * A MovesQueries constructor,
	   * Constructs the MovesQueries Object setting its connection. 
	   * @param connection, references the Connection.
	   */
	MovesQueries(Connection connection){
		this.con = connection;
	}
	
	/**
	   * Gets the player's average score.
	   * @param idUser as String.
	   * @exception SQLException.
	   * @return returns double as an average score.
	   */
	public double getAverageScore(String idUser) throws SQLException{
		double result = 0.001;
		// TODO Auto-generated method stub
		String avgScore = "SELECT AVG("+moveResult+") AS avg FROM "+movesTable+" WHERE "+movePlayer+" ='"+idUser+"'";
		Statement smt = con.createStatement();
		ResultSet rs = smt.executeQuery(avgScore);
		if (rs.next())
			result = (rs.getDouble("avg"));
		return result;
	}
	
	/**
	   * Gets the player's ranking.
	   * @param idUser as String.
	   * @exception SQLException.
	   * @return returns integer as a ranking.
	   */
	public int getRanking(String idUser) throws SQLException {
		// TODO Auto-generated method stub
		int result = 0;
		// TODO Auto-generated method stub
		String ranking = "SELECT COUNT(*) AS rank FROM "+moveRankingView+" WHERE "+moveRankingViewScore+" >= "
				+ "( SELECT "+moveRankingViewScore+" FROM "+moveRankingView+" WHERE "+moveRankingViewPlayer+" = '"+idUser+"' )";
		Statement smt = con.createStatement();
		ResultSet rs = smt.executeQuery(ranking);
		if (rs.next())
			result = (rs.getInt("rank"));
		return result;
	}

	/**
	   * Allows to add player's choice into database.
	   * @param mv as MoveObjectInterface.
	   * @exception SQLException.
	   * @return returns integer >0 whether the move is registered, 0 otherwise.
	   */
	public int addMove(MoveObjectInterface mv) throws SQLException {
		// TODO Auto-generated method stub
				String addMovehQuery = "INSERT INTO "+movesTable+"("+moveID+","+moveMatchId+","+moveRound+","+movePlayer+","+moveNumber+","+moveResult+")"
						+"Values('"+mv.getIdMove()+"','"+mv.getIdMatch()+"','"+mv.getRound()+"','"+mv.getPlayer()+"','"+mv.getNumber()+"','"+mv.getResult()+"')";
				Statement smt = con.createStatement();
				return smt.executeUpdate(addMovehQuery);
		
	}

	/**
	   * Allows to get the best move for an AI player
	   * relatively to human previous choice.
	   * @param integer as choice.
	   * @exception SQLException.
	   * @return returns integer as a best move.
	   */
	public int getBestMove(int choice) throws SQLException{
		// TODO Auto-generated method stub
		int result = 0;
		
		String bestMoveQuery = "SELECT Mo."+moveNumber+",  AVG(Mo."+moveResult+") FROM "+movesTable+" Mo JOIN "+movesTable+" M ON (Mo."+moveMatchId+
		" = M."+moveMatchId+") WHERE M."+moveNumber+" = '"+choice+"' AND M."+movePlayer+" <> Mo."+movePlayer+" AND Mo."+moveRound+" = M."+
				moveRound+" + 1 GROUP BY Mo."+moveNumber+" order by AVG(Mo."+moveResult+") DESC";
		Statement smt = con.createStatement();
		ResultSet rs = smt.executeQuery(bestMoveQuery);
		if(rs.next())
			result = rs.getInt(1);
		return result;
	}

}
