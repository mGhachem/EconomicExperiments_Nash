package shared;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;

public interface ServerListenerInterface  extends Remote{

	/* returns whether the user is already registered. */
	boolean login(String email, String password, ClientListenerInterface client) throws SQLException, RemoteException;

	/* returns whether the registration of a new user has been done correctly. */
	boolean signUp(Credential login, String password)throws SQLException, RemoteException;

	/* allows the user to play a game. */
	RemoteMatch playGame(ClientListenerInterface client) throws SQLException, RemoteException;
	
	/* updates the user statistics received as an argument. */
	void updateStatistics(ClientListenerInterface client) throws SQLException, RemoteException;
	
	/* allows the user to play a game against an AI player. */
	void playAgainstAI(ClientListenerInterface clientAI) throws SQLException, RemoteException;

}