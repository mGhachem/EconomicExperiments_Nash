package server;

import java.rmi.RemoteException;
import java.sql.SQLException;

import shared.ClientListenerInterface;
import shared.DataBaseManagerInterface;

public class StatisticsManager {
	private DataBaseManagerInterface dbManager;
	private static StatisticsManager statisticsManager;
	
	
	/**
	   * A StatisticsManager constructor,
	   * Constructs the StatisticsManager Object. 
	   * @param dbManager as DataBaseManagerInterface.
	   */
	private StatisticsManager(DataBaseManagerInterface dbManager) {
		this.dbManager = dbManager;
	}

	/**
	   * A method called to construct the StatisticsManager Object
	   *  setting its dbManager. 
	   * @param dbManager as DataBaseManagerInterface.
	   * @return returns a StatisticsManager object.
	   */
	public static StatisticsManager createStatisticsManager(DataBaseManagerInterface dbManager) 
	{
		if(statisticsManager==null)
			statisticsManager = new StatisticsManager(dbManager);	
		return statisticsManager;		
	}

	/**
	   * updates the user statistics received as an argument.
	   * @param an object of ClientListenerInterface.
	   * @exception SQLException, RemoteException. 
	   * @return No return value.
	   */
	public void updateStatistics(ClientListenerInterface client) throws SQLException, RemoteException{
		// TODO Auto-generated method stub
		double avs = dbManager.getAverageScore(client.getId());
		int ranking = dbManager.getRanking(client.getId());
		client.setAverageScore(avs);
		client.setRanking(ranking);
	}
}
