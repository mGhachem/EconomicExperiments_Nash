package shared;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientListenerInterface extends Remote{

	//Notify player choice
	//Notify the Result to Player
	void notifyPlayerResult(int result) throws RemoteException;

	//Notify the Client when there's no two player
	void showWaiting()throws RemoteException;

	//Notify the Client to start the game
	void showGamePlatform()throws RemoteException;

	//Notify the Timer
	void notifyUpdateTimer(int sec)throws RemoteException;

	//Notify a New Round
	void notifyNewRound(int round)throws RemoteException;

	//Notify the Winner of the game
	void notifyWinner(int score)throws RemoteException;

	//Notify the Loser of the game
	void notifyLoser(int score)throws RemoteException;

	void notifyOpponentDetails(String nickname, double averageScore, int ranking)throws RemoteException;

	void setId(String id)throws RemoteException;

	void setEmail(String email)throws RemoteException;

	void setPassword(String password)throws RemoteException;

	void setNickname(String nickname)throws RemoteException;

	String getId()throws RemoteException;

	String getEmail()throws RemoteException;

	String getPassword()throws RemoteException;

	String getNickname()throws RemoteException;

	double getAverageScore()throws RemoteException;

	void setAverageScore(double averageScore)throws RemoteException;

	int getRanking()throws RemoteException;

	void setRanking(int ranking)throws RemoteException;

	void setNashScreen()throws RemoteException;

	void getNashScreen()throws RemoteException;

	void notifyRound(int round)throws RemoteException;

	void notifyPlayNumber(int num)throws RemoteException;

	void run()throws RemoteException;

	void setGame() throws RemoteException;
	
	boolean isAI() throws RemoteException;
	
	void setAI(boolean isAI) throws RemoteException;

	//void setGame(NashGameScreen nashGameScreen)throws RemoteException;


}