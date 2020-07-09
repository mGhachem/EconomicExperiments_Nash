package server;

/**
 * 
 * Online Economic Experiments - Nash Demand Game
 * 
 * The project has been done with cooperation with
 * Timotheous Mavroupolos (Stockholm School of Economics).
 * 
 * @author Mohamed Ghachem.
 * @version 1.0.
 * @since June 2020.
 * 
 */

import java.sql.SQLException;
import org.cojen.dirmi.Environment;
import database.DataBaseManager;
import shared.ServerListenerInterface;


public class StartServer {
	
	private static ServerListenerInterface server;

	@SuppressWarnings("resource")
	public static void main(String[] args) throws Exception {
		String user = "postgres";
		String password = "postgres";
		String port = "5432";
		String hostname = "localhost";
		String url = hostname + ":"+ port;
//		Registry register;

		try {
			DataBaseManager dbManager = DataBaseManager.createDBManager(url, user, password);
			
			server = new ServerListener(dbManager);
			
			Environment env = new Environment();
			env.newSessionAcceptor(2222).acceptAll(server);

			System.out.println("Server Started: ");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
