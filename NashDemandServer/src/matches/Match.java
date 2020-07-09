package matches;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;
import database.MoveObject;
import shared.MoveObjectInterface;
import shared.ClientListenerInterface;
import shared.DataBaseManagerInterface;
import shared.RemoteMatch;

public class Match implements RemoteMatch {
	/**
	 * 
	 */
	private ArrayList<ClientListenerInterface> players = new ArrayList<>();
	private int[] demands = new int[2];
	private int[] totScore = new int[2];
	private MoveTimer timer;
	private int round;
	private String idMatch;
	private DataBaseManagerInterface dbManager;
	private int totalRounds = 5;
	
	/**
	   * A Match constructor,
	   * Constructs a Remote Match Object setting its databaseManager 
	   * and its id as String. 
	   * @param databManager, references the dataBaseManager
	   */
	public Match(DataBaseManagerInterface dbManager2) throws RemoteException {
		String id = UUID.randomUUID().toString();
		this.idMatch=id;
		this.dbManager = dbManager2;
	};
	

	/**
	   * Gets the match id. 
	   * @return the match id.
	   */ 
	@Override
	public String getIdMatch() {
		return idMatch;
	}
	
	/**
	 * adds a player to the current match available.
	 * @param a ClientListenerInterface as a client.
	 * @return true if the players number is two, otherwise false;
	 */
	@Override
	public boolean addPlayer(ClientListenerInterface client) throws RemoteException{
		// TODO Auto-generated method stub
		players.add(client);
		if(players.size()==2)
			return true;
		else 
			return false;
	}
	
	/**
	 * allows a player to choose a number during a game.
	 * @param a ClientListenerInterface as a client, and the player's demand as an integer.
	 * @return No return value.
	 */
	@Override
	public synchronized void playNumber(ClientListenerInterface client, int num) throws SQLException, RemoteException {	

		if(players.get(0).equals(client)) {
			demands[0] = num;
		}
		else {
			demands[1] = num;
		}
		if(demands[0]!=0 && demands[1]!=0) {
			timer.interrupt();
			computeResults(demands[0], demands[1]);

			if(round< totalRounds )
				try {
					newRound();
				} catch (RemoteException | InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			else {
				endMatch();
			}
		}

	};

	/**
	 * notifies the end of the match, showing the winner and the loser.
	 * @param No parameters.
	 * @return No return value.
	 */
	private void endMatch() throws RemoteException {
		// TODO Auto-generated method stub
		if(totScore[0]==totScore[1]) {
			players.get(0).notifyWinner(totScore[0]);
			players.get(1).notifyWinner(totScore[1]);
		}
		else if(totScore[0]>totScore[1]) {
			players.get(0).notifyWinner(totScore[0]);
			players.get(1).notifyLoser(totScore[1]);
		}else {
			players.get(1).notifyWinner(totScore[1]);
			players.get(0).notifyLoser(totScore[0]);
		}
	}
	
	/**
	 * starts a new round within the match, starts a timer and notify the players.
	 * @param No parameters.
	 * @Exception RemoteException, InterruptedException.
	 * @return No return value.
	 */
	private synchronized void newRound() throws RemoteException, InterruptedException {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		startTimer(10, this);
		++round;
		int latestMove = demands[0];
		demands[0] = demands[1] = 0;
		
		for(ClientListenerInterface p : players) {
			if(!p.isAI())
				p.notifyNewRound(round);
			else {
				try {
					playNumber(p, getBestMove(latestMove));
				} catch (RemoteException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}
	
	/**
	 * computes the players' demands and saves it into the database.
	 * @param No parameters.
	 * @Exception RemoteException, InterruptedException.
	 * @return No return value.
	 */
	private synchronized void computeResults(int num1, int num2) throws SQLException, RemoteException {
		// TODO Auto-generated method stub
		MoveObjectInterface mv;
		String moveId1 = UUID.randomUUID().toString();
		String moveId2 = UUID.randomUUID().toString();
		int sum = num1 + num2;
		if(sum > 10) {
			showResults(0,0);
			mv = new MoveObject(moveId1, idMatch, round, players.get(0).getId(), demands[0], 0 );
			dbManager.addMove(mv);
			mv = new MoveObject(moveId2, idMatch, round, players.get(1).getId(), demands[1], 0 );
			dbManager.addMove(mv);
		}else {
			showResults(num1, num2);
			mv = new MoveObject(moveId1, idMatch, round, players.get(0).getId(), num1, num1 );
			dbManager.addMove(mv);
			mv = new MoveObject(moveId2, idMatch, round, players.get(1).getId(), num2, num2 );
			dbManager.addMove(mv);
			totScore[0] +=num1;
			totScore[1] +=num2;
		}
	}
	
	/**
	 * notifies the players' results.
	 * @param rslt1, rslt2 as intger and players' result.
	 * @Exception RemoteException.
	 * @return No return value.
	 */
	@Override
	public void showResults(int rslt1, int rslt2) throws RemoteException {
		players.get(0).notifyPlayerResult(rslt1);
		if(!players.get(1).isAI())
			players.get(1).notifyPlayerResult(rslt2);			
	}
	
	/**
	 * Gets the players' list .
	 * @param No parameters.
	 * @return an ArrayList of the players.
	 */
	@Override
	public ArrayList<ClientListenerInterface> getPlayers() {
		return players;
	}
	
	/**
	 * Gets the players' demands .
	 * @param No parameters.
	 * @return an Array of the players' demands.
	 */
	@Override
	public int[] getDemands() {
		return demands;
	}
	
	/**
	 * Gets the players' total score .
	 * @param No parameters.
	 * @return an Array of the players' total score.
	 */
	@Override
	public int[] gettotScore() {
		return totScore;
	}
	
	/**
	 * Allows to start a new game.
	 * @param No parameters.
	 * @exception RemoteException.
	 * @return No return value.
	 */
	@Override
	public synchronized void startGame() throws RemoteException {
		// TODO Auto-generated method stub
		round=0;
		try {
			newRound();
		} catch (RemoteException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
	
	/**
	 * Allows to set a player's demand to one, when the time is out.
	 * @param No parameters.
	 * @exception SQLException, RemoteException.
	 * @return No return value.
	 */
	@Override
	public synchronized void timeout() throws SQLException, RemoteException {
		// TODO Auto-generated method stub
		if(demands[0]==0)
			demands[0]=1;
		if(demands[1]==0)
			demands[1]=1;
		computeResults(demands[0], demands[1]);
		try {
			if(round<5)
				newRound();
		} catch (RemoteException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Allows to start a timer.
	 * @param time as integer, match as a RemoteMatch.
	 * @return No return value.
	 */
	private synchronized void startTimer(int time, RemoteMatch match) {
		timer = new MoveTimer(time, match);
		timer.start();
	}
	
	/**
	 * Allows to get the best move for an AI player 
	 * relatively to human previous demand.
	 * @param demand as integer.
	 * @return No return value.
	 */
	private int getBestMove(int demand) {
		int result =0;
		if(round==1)
			result = (int)(Math.random() * 9 + 1);
		else {
			try {
				result =  dbManager.getBestMove(demand);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("Result "+result);
		return result;
	}
}
