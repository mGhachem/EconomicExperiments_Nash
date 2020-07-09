package nashClient;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import nashInterface.NashGameScreen;
import shared.ClientListenerInterface;
import shared.NashGameScreenInterface;

public class ClientListener implements ClientListenerInterface, Runnable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String email;
	private String password;
	private String nickname;
	private NashGameScreenInterface nashScreen;
	private double averageScore;
	private int ranking;
	private boolean isAI=false;
	
	//Constructor of the Client  
	public ClientListener() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	   * notifies the player result.
	   * @param An integer 
	   * of the result.
	   * @return No return value.
	   */ 
	@Override
	public void notifyPlayerResult(int result)throws RemoteException{
		if(!isAI)
		nashScreen.notifyPlayerResult(result);
	};
	
	/**
	   * notifies the waiting Panel when there's no 
	   * player available to start the game.
	   * @param No parameter. 
	   * of the result.
	   * @return No return value.
	   */ 
	@Override
	public void showWaiting() throws RemoteException{
		if(!isAI)
		nashScreen.notifyWaiting();
	};
	
	/**
	   * shows the Game Platform on the UI to start the game.
	   * @param No parameters. 
	   * @return No return value.
	   */ 
	@Override
	public void showGamePlatform()throws RemoteException {
		if(!isAI)
		nashScreen.showGamePlatform();
	};
	
	/**
	   * Updates the timer on the UI.
	   * @param An integer that represents the seconds. 
	   * @return No return value.
	   */ 
	@Override
	public void notifyUpdateTimer(int sec) throws RemoteException{
		if(!isAI)
		nashScreen.notifyUpdateTimer(sec);
	};
	

	/**
	   * Notifies the beginning of a new round.
	   * @param An integer that represents the round number. 
	   * @return No return value.
	   */ 
	@Override
	public void notifyNewRound(int round)throws RemoteException {
		if(!isAI)
		nashScreen.notifyNewRound(round);
	};

	/**
	   * Notifies the winner of the game on the UI.
	   * @param An integer that represents the winner score. 
	   * @return No return value.
	   */ 
	@Override
	public void notifyWinner(int score) throws RemoteException{
		if(!isAI)
		nashScreen.notifyWinner(score);
	};
	
	/**
	   * Notifies the loser of the game on the UI.
	   * @param An integer that represents the loser score. 
	   * @return No return value.
	   */ 
	@Override
	public void notifyLoser(int score) throws RemoteException{
		if(!isAI)
		nashScreen.notifyLoser(score);
	};
	
	/**
	   * Notifies the opponent's details.
	   * @param a string as a nickname, a double the average score,
	   * an integer as a ranking. 
	   * @return No return value.
	   */ 
	@Override
	public void notifyOpponentDetails(String nickname, double averageScore, int ranking)throws RemoteException {
		if(!isAI)
		// TODO Auto-generated method stub
		nashScreen.notifyOpponentDetails(nickname, averageScore, ranking);
	}
	
	//Getter and Setter
	@Override
	public void setId(String id)throws RemoteException {
		this.id = id;
	}

	@Override
	public void setEmail(String email) throws RemoteException{
		this.email = email;
	}

	@Override
	public void setPassword(String password)throws RemoteException {
		this.password = password;
	}

	@Override
	public void setNickname(String nickname) throws RemoteException{
		this.nickname = nickname;
	}

	@Override
	public String getId() throws RemoteException{
		return id;
	}
	
	/**
	   * Gets the user email. 
	   * @return the user email.
	   */ 
	@Override
	public String getEmail() throws RemoteException{
		return email;
	}

	/**
	   * Gets the user password. 
	   * @return the user password.
	   */ 
	@Override
	public String getPassword() throws RemoteException{
		return password;
	}
	
	/**
	   * Gets the user nickname. 
	   * @return the user nickname.
	   */ 
	@Override
	public String getNickname()throws RemoteException {
		return nickname;
	}
	
	/**
	   * Gets the user average score. 
	   * @return the user average score.
	   */ 
	@Override
	public double getAverageScore() throws RemoteException{
		return averageScore;
	}

	/**
	   * Sets the user average score. 
	   * @param a double average score.
	   */ 
	@Override
	public void setAverageScore(double averageScore)throws RemoteException {
		this.averageScore = averageScore;
	}

	/**
	   * Gets the user ranking. 
	   * @return the user ranking.
	   */ 
	@Override
	public int getRanking()throws RemoteException {
		return ranking;
	}

	/**
	   * Sets the user ranking. 
	   * @param an integer as a ranking.
	   */ 
	@Override
	public void setRanking(int ranking)throws RemoteException {
		this.ranking = ranking;
	}

	@Override
	public void setNashScreen() throws RemoteException{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void getNashScreen()throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void notifyRound(int round)throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void notifyPlayNumber(int num)throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
			System.out.println("ERRORE Connessione");
	}


	@Override
	public void setGame() {
		// TODO Auto-generated method stub
		nashScreen = NashGameScreen.thisScreen;
		
	}

	/**
	   * Sets the user as an AI player. 
	   * @param boolean true whether the client is an AI, otherwise false.
	   */ 
	public void setAI(boolean isAI) throws RemoteException{
		this.isAI = isAI;
	}

	/**
	   * Gets whether the client user is an AI. 
	   * @return true whether the user is an AI, otherwise false.
	   */ 
	@Override
	public boolean isAI() throws RemoteException {
		// TODO Auto-generated method stub
		return this.isAI;
	}
}
