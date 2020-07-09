package shared;

import java.sql.SQLException;

public interface DataBaseManagerInterface {

	/* allows to check the user by its email */
	UserObjectInterface checkByEmail(String email)throws SQLException;
	
	/* allows to get the user's average score */
	double getAverageScore(String string) throws SQLException;
	
	/* allows to get the user's ranking */
	int getRanking(String nickname) throws SQLException;

	/* allows to save a match into the database */
	int addMatch(MatchObjectInterface mch) throws SQLException;

	/* allows to save a player's choice into the database */
	int addMove(MoveObjectInterface mv) throws SQLException;

	/* allows to save an user into the database */
	int addUser(UserObjectInterface user)throws SQLException;

	/* allows to check user registration into the database */
	boolean checkUser(Credential login) throws SQLException;

	/* allows to get the best move for an AI player */
	int getBestMove(int choice) throws SQLException;

}