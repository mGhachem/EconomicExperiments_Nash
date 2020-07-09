package server;

import shared.ClientListenerInterface;
import shared.DataBaseManagerInterface;
import java.rmi.RemoteException;
import java.sql.SQLException;
import shared.UserObjectInterface;

public class LoginManager {
	private DataBaseManagerInterface dbManager;
	private static LoginManager loginManager;
	
	/**
	   * A LoginManager constructor,
	   * Constructs the LoginManager Object. 
	   * @param dbManager as DataBaseManagerInterface.
	   */
	private LoginManager(DataBaseManagerInterface dbManager) {
		this.dbManager = dbManager;
	}

	/**
	   * A method called to construct the LoginManager Object
	   *  setting its dbManager. 
	   * @param dbManager as DataBaseManagerInterface.
	   * @return returns a LoginManager object.
	   */
	public static LoginManager createLoginManager(DataBaseManagerInterface dbManager) 
	{
		if(loginManager==null)
			loginManager = new LoginManager(dbManager);
		return loginManager;		
	}
	
	/**
	   * returns whether the user is already registered.
	   * @param a string email, string password and a ClientListenerInterface client.
	   * @exception SQLException, RemoteException. 
	   * @return true if the user is already registered otherwise false.
	   */
	public boolean login(String email, String password, ClientListenerInterface client) throws SQLException, RemoteException 
	{
		UserObjectInterface user = dbManager.checkByEmail(email);
		if(user!=null && user.getPassword().equals(password)) {
			client.setId(user.getIdUser());
			client.setEmail(user.getEmail());
			client.setNickname(user.getNickname());
			client.setPassword(user.getPassword());
		return true;
		}
		return false;
	}
}