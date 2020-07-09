package server;

import java.rmi.RemoteException;
import java.sql.SQLException;
import database.DataBaseManager;
import shared.ClientListenerInterface;
import shared.Credential;
import shared.RemoteMatch;
import shared.ServerListenerInterface;

public class ServerListener implements ServerListenerInterface {
	
	private DataBaseManager dbaseManager;
	private LoginManager loginManager;
	private RegistrationManager registrationManager;
	private MatchManager matchManager;
	private StatisticsManager statisticsManager;
	
	/**
	   * A ServerListener constructor,
	   * Constructs the Remote ServerListener Object with a LoginManager, 
	   * RegistrationManager,MatchManager and StatisticsManager
	   * @param databManager, references the dataBaseManager.
	   * @exception RemoteException. 
	   */
	public ServerListener(DataBaseManager databManager) throws RemoteException {
		dbaseManager = databManager;
		loginManager = LoginManager.createLoginManager(databManager);
		registrationManager = RegistrationManager.createRegistrationManager(databManager);
		matchManager = MatchManager.createMatchManager(databManager, this);
		statisticsManager = StatisticsManager.createStatisticsManager(databManager);
	}
	
	/**
	   * returns whether the user is already registered.
	   * @param a string email, string password and a ClientListenerInterface client.
	   * @exception SQLException, RemoteException. 
	   * @return true if the user is already registered otherwise false.
	   */
	@Override
	public boolean login(String email, String password, ClientListenerInterface client) throws SQLException, RemoteException
	{
		return loginManager.login(email, password, client);
	}
	
	/**
	   * returns whether the registration of a new user has been done correctly.
	   * @param an object login that contains(a nickname and email), string password.
	   * @exception SQLException, RemoteException. 
	   * @return true if the user has been registered correctly otherwise false.
	   */
	@Override
	public boolean signUp(Credential login, String password) throws SQLException, RemoteException 
	{
		return registrationManager.signUp(login, password);
	}
	
	/**
	   * allows the user to play a game.
	   * @param an object of ClientListenerInterface.
	   * @exception SQLException, RemoteException. 
	   * @return the RemoteMatch in which the user participates.
	   */
	@Override
	public RemoteMatch playGame(ClientListenerInterface client) throws SQLException, RemoteException {
		return matchManager.playGame(client);		
	}
	
	/**
	   * updates the user statistics received as an argument.
	   * @param an object of ClientListenerInterface.
	   * @exception SQLException, RemoteException. 
	   * @return No return value.
	   */
	@Override
	public void updateStatistics(ClientListenerInterface client) throws SQLException, RemoteException {
		// TODO Auto-generated method stub
		statisticsManager.updateStatistics(client);
		
	}
	
	/**
	   * allows the user to play a game against an AI player.
	   * @param an object of ClientListenerInterface.
	   * @exception SQLException, RemoteException. 
	   * @return No return value.
	   */
	public void playAgainstAI(ClientListenerInterface client) {
		matchManager.playAgainstAI(client);
	}

	public void setDbaseManager(DataBaseManager dbaseManager) {
		this.dbaseManager = dbaseManager;
	}
	
}
