package shared;

import java.sql.SQLException;

public interface DataBaseManagerInterface {

	UserObjectInterface checkByEmail(String email)throws SQLException;

	double getAverageScore(String string) throws SQLException;

	int getRanking(String nickname) throws SQLException;

	int addMatch(MatchObjectInterface mch) throws SQLException;

	int addMove(MoveObjectInterface mv) throws SQLException;

	int addUser(UserObjectInterface user)throws SQLException;

}