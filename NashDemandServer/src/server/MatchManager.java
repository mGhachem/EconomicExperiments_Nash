package server;

import shared.ClientListenerInterface;
import shared.DataBaseManagerInterface;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import database.MatchObject;
import shared.MatchObjectInterface;
import matches.Match;
import shared.RemoteMatch;
import shared.ServerListenerInterface;

public class MatchManager {
	private DataBaseManagerInterface dbManager;
	private static MatchManager matchManager;
	private ServerListenerInterface server;
	private HashMap<String, RemoteMatch>matches = new HashMap<>();;

	/**
	   * A private MatchManager constructor,
	   * Constructs the MatchManager Object setting its databaseManager and serverListerner. 
	   * @param databManager, references the dataBaseManager
	   * and a ServerListenerInterface references the server object.
	   */
	private MatchManager(DataBaseManagerInterface dbManager, ServerListenerInterface server) {
		this.dbManager = dbManager;
		this.server = server;
	}
	
	/**
	   * A method called to construct the MatchManager Object
	   *  setting its databaseManager and serverListerner. 
	   * @param databManager, references the dataBaseManager
	   * and a ServerListenerInterface references the server object.
	   * @return returns a MatchManager object.
	   */
	public static MatchManager createMatchManager(DataBaseManagerInterface dbManager, ServerListener server) 
	{
		if(matchManager==null)
			matchManager = new MatchManager(dbManager, server);	
		return matchManager;		
	}

	/**
	   * allows the user to play a game.
	   * @param an object of ClientListenerInterface client.
	   * @exception SQLException, RemoteException. 
	   * @return the RemoteMatch in which the user participates.
	   */
	public RemoteMatch playGame(ClientListenerInterface client) throws SQLException, RemoteException {
		RemoteMatch match = null;
		MatchObjectInterface mch;
		LocalDateTime currentTime = LocalDateTime.now();
		// TODO Auto-generated method stub
		if(matches.isEmpty()) {
				match = new Match(dbManager);
				matches.put(match.getIdMatch(), match);
		} else {
			// Get the collection of matches and choose the first one on the collection
			Collection<RemoteMatch> allMatches = matches.values();
			match = allMatches.iterator().next();
			matches.remove(match.getIdMatch(), match);
		}
		boolean full = match.addPlayer(client);
		if(full == false) {
			client.showWaiting();
		} 
		else {
			mch = new MatchObject(match.getIdMatch(), currentTime, match.getPlayers().get(0).getId(), match.getPlayers().get(1).getId());
			dbManager.addMatch(mch);
			ArrayList<ClientListenerInterface>players = match.getPlayers();
			for(ClientListenerInterface p : players){
				if(!p.isAI())
					p.showGamePlatform();
				if(p.equals(client))
					p.notifyOpponentDetails(players.get(0).getNickname(), players.get(0).getAverageScore(), players.get(0).getRanking());
				else 
					p.notifyOpponentDetails(players.get(1).getNickname(), players.get(1).getAverageScore(), players.get(1).getRanking());
			}
			match.startGame();
		}

		return match;
	}
	
	/**
	   * allows the user to play a game against an AI player.
	   * @param an object of ClientListenerInterface.
	   * @exception SQLException, RemoteException. 
	   * @return No return value.
	   */
	public void playAgainstAI(ClientListenerInterface client) {
		try {
			client.setAI(true);
			server.login("ai@email.com", "testAI", client);
			server.updateStatistics(client);
			playGame(client);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
}