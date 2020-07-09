package shared;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;

public interface RemoteMatch extends Remote {

	String getIdMatch() throws RemoteException;

	boolean addPlayer(ClientListenerInterface client) throws RemoteException;

	void playNumber(ClientListenerInterface client, int num) throws SQLException, RemoteException;

	void showResults(int rslt1, int rslt2) throws RemoteException;

	ArrayList<ClientListenerInterface> getPlayers() throws RemoteException;

	int[] getDemands() throws RemoteException;

	int[] gettotScore() throws RemoteException;

	void startGame() throws RemoteException;

	void timeout() throws SQLException, RemoteException;


}