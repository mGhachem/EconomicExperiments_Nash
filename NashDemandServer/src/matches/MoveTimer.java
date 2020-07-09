package matches;

import java.rmi.RemoteException;
import java.sql.SQLException;

import shared.ClientListenerInterface;
import shared.RemoteMatch;

public class MoveTimer extends Thread{

	private int seconds;
	private RemoteMatch match;

	/**
	   * A MoveTimer constructor,
	   * Constructs the MoveTimer Object setting its seconds and the RemoteMatch. 
	   * @param match, references the dataBaseManager, seconds as integer.
	   */
	MoveTimer(int seconds, RemoteMatch match) {
		this.seconds = seconds;
		this.match = match;
	}


	/**
	   * Launches the timer 
	   * @param No parameter.
	   */
	public void run() {
		boolean interrupted = false;
		for(int i=seconds; i>=1; i--) {
			try {
				for (ClientListenerInterface p : match.getPlayers()) 
				{
					if(!p.isAI())
						p.notifyUpdateTimer(i);
					sleep(1000);
				}
			} catch (RemoteException | InterruptedException e1) {
				// TODO Auto-generated catch block
				// e1.printStackTrace();
				interrupted = true;
				break;
			}
		}
		try {
			if (!interrupted)
				match.timeout();
		} catch (RemoteException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
