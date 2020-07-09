package server;

import java.sql.SQLException;
import java.util.UUID;

import database.UserObject;
import shared.UserObjectInterface;
import shared.Credential;
import shared.DataBaseManagerInterface;

public class RegistrationManager {
	private DataBaseManagerInterface dbManager;
	private static RegistrationManager registrationManager;
	
	/**
	   * A RegistrationManager constructor,
	   * Constructs the RegistrationManager Object. 
	   * @param dbManager as DataBaseManagerInterface.
	   */
	private RegistrationManager(DataBaseManagerInterface dbManager) {
		this.dbManager = dbManager;
	}

	/**
	   * A method called to construct the RegistrationManager Object
	   *  setting its dbManager. 
	   * @param dbManager as DataBaseManagerInterface.
	   * @return returns a RegistrationManager object.
	   */
	public static RegistrationManager createRegistrationManager(DataBaseManagerInterface dbManager) 
	{
		if(registrationManager==null)
			registrationManager = new RegistrationManager(dbManager);	
		return registrationManager;		
	}

	/**
	   * returns whether the registration of a new user has been done correctly.
	   * @param an object login that contains(a nickname and email), string password.
	   * @exception SQLException, RemoteException. 
	   * @return true if the user has been registered correctly otherwise false.
	   */
	public boolean signUp(Credential login, String password) throws SQLException {
		// TODO Auto-generated method stub
		boolean done = dbManager.checkUser(login);
		if(done == false)
			return false;
		else {
			String idUser = UUID.randomUUID().toString();
			UserObjectInterface user = new UserObject(idUser, login.getNickname(),login.getEmail(), password);
			int result = dbManager.addUser(user);
			if (result>0)
				return true;
			else
				return false;
		}
	}
}