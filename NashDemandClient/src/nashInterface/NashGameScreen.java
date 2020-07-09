package nashInterface;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import nashClient.ClientListener;
import shared.ClientListenerInterface;
import shared.Credential;
import shared.NashGameScreenInterface;
import shared.RemoteMatch;
import shared.ServerListenerInterface;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.awt.Font;
import java.awt.Color;
import javax.swing.border.EtchedBorder;
import java.awt.Toolkit;
import javax.swing.border.MatteBorder;
import org.cojen.dirmi.Environment;
import org.cojen.dirmi.Session;

import java.awt.Cursor;

public class NashGameScreen implements NashGameScreenInterface {

	private JFrame frame;
	private JTextField txtEmail;
	private JPasswordField txtPassword;
	private JTextField txtSNickname;
	private JTextField txtSEmail;
	private JPasswordField txtSPassword;
	private JTextField txtPlayer;
	private JLabel txtPlyr1Nickname;
	private JLabel txtPlyr2Nickname;
	private JLabel txtPlyr1Stat;
	private JLabel txtPlyr2Stat;
	private JLabel txtPlyr1Rank;
	private JLabel txtPlyr2Rank;
	private static ServerListenerInterface server;
	private JPanel signUpPanel;
	private JPanel signInPanel;
	private JPanel gPlatformPanel;
	private JPanel waitingPanel;
	private JPanel matchPanel;
	private JPanel winnerPanel;
	private ClientListenerInterface client;
	private ClientListener clientAI;
	private JLabel txtMyResult;
	private JLabel txtMyChoice;
	private JLabel lblTimer;
	private JLabel lblWaitingTimer;
	private JLabel lblRound;
	private JLabel endMatchMessage;
	private JLabel endMatchScore;
	private RemoteMatch match;
	private static Registry registry;
	private boolean done;
	public static NashGameScreenInterface thisScreen;
	private Thread waitingTimer;
	private JLabel medal_txt;
	private JLabel medal_txt_1;
	protected DecimalFormat df = new DecimalFormat("#.00");
	public static final Pattern VALID_EMAIL_ADDRESS_REGEX = 
			Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
	private static Environment env;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					NashGameScreen window = new NashGameScreen();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @throws RemoteException 
	 */
	public NashGameScreen() throws RemoteException {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws RemoteException 
	 */
	private void initialize() throws RemoteException {
		try {
//			System.setProperty("java.rmi.server.hostname","localhost");
//			registry = LocateRegistry.getRegistry("localhost", 1099);
//			server = (ServerListenerInterface) registry.lookup("SERVER");
			
			 env = new Environment();
	         String host = "localhost";
	         Session session = env.newSessionConnector(host, 2222).connect();
	         server = (ServerListenerInterface) session.receive();
	         
			System.out.println("Server: "+server);
			client = new ClientListener();
			System.out.println("client: "+client);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(frame, "CAN'T CONNECT TO SERVER!", null, JOptionPane.WARNING_MESSAGE);
			e1.printStackTrace();
		}

		ActionListener actionlistener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JButton btnpressed= (JButton) e.getSource();
				try {
					txtMyChoice.setText(btnpressed.getText());
					match.playNumber(client, Integer.parseInt(btnpressed.getText()));
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

		};

		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(NashGameScreen.class.getResource("/img/background.png")));
		frame.setBounds(100, 100, 510, 660);
		frame.getContentPane().setLayout(null);

		frame.addWindowListener((WindowListener) new WindowAdapter() {
		    public void windowClosing(WindowEvent e) {
		    	try {
					env.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		        frame.dispose();
		    }
		});
		matchPanel = new JPanel();
		matchPanel.setOpaque(false);
		matchPanel.setVisible(false);
		
				waitingPanel = new JPanel();
				waitingPanel.setOpaque(false);
				waitingPanel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				waitingPanel.setBounds(10, 10, 480, 600);
				waitingPanel.setLayout(null);
				
						JLabel lblWaiting = new JLabel("Waiting for human players...");
						lblWaiting.setForeground(new Color(255, 255, 255));
						lblWaiting.setFont(new Font("Poppins", Font.PLAIN, 18));
						lblWaiting.setHorizontalAlignment(SwingConstants.CENTER);
						lblWaiting.setBounds(10, 320, 460, 30);
						waitingPanel.add(lblWaiting);
						
								JLabel lblTimer_1_1 = new JLabel("\u25EF");
								lblTimer_1_1.setHorizontalTextPosition(SwingConstants.CENTER);
								lblTimer_1_1.setHorizontalAlignment(SwingConstants.CENTER);
								lblTimer_1_1.setForeground(Color.WHITE);
								lblTimer_1_1.setFont(new Font("Dialog", Font.PLAIN, 48));
								lblTimer_1_1.setBounds(10, 250, 460, 50);
								waitingPanel.add(lblTimer_1_1);
								
										lblWaitingTimer = new JLabel("30");
										lblWaitingTimer.setForeground(new Color(255, 255, 0));
										lblWaitingTimer.setFont(new Font("Poppins", Font.PLAIN, 22));
										lblWaitingTimer.setHorizontalAlignment(SwingConstants.CENTER);
										lblWaitingTimer.setBounds(10, 250, 460, 50);
										waitingPanel.add(lblWaitingTimer);
										frame.getContentPane().add(waitingPanel);
										
												JButton btnAI = new JButton("Play Against AI ");
												btnAI.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
												btnAI.setFocusPainted(false);
												btnAI.setBackground(new Color(60, 120, 170));
												btnAI.setBorderPainted(false);
												btnAI.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
												btnAI.setForeground(new Color(255, 255, 0));
												btnAI.setFont(new Font("Poppins", Font.PLAIN, 18));
												
														btnAI.addMouseListener(new MouseAdapter() {
															@Override
															public void mouseClicked(MouseEvent e) {
																try {
																	waitingTimer.interrupt();
																} catch (Exception e1) {
																	// TODO Auto-generated catch block
																	e1.printStackTrace();
																}
												
															}
														});
														btnAI.setBounds(148, 366, 200, 35);
														waitingPanel.add(btnAI);
		matchPanel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		matchPanel.setBounds(10, 10, 480, 600);
		frame.getContentPane().add(matchPanel);
		matchPanel.setLayout(null);

		txtPlyr1Nickname = new JLabel();
		txtPlyr1Nickname.setForeground(new Color(255, 255, 255));
		txtPlyr1Nickname.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(255, 255, 255)));
		txtPlyr1Nickname.setHorizontalAlignment(SwingConstants.CENTER);
		txtPlyr1Nickname.setText("PLAYER 1");
		txtPlyr1Nickname.setOpaque(false);
		txtPlyr1Nickname.setFont(new Font("Pasajero", Font.PLAIN, 17));
		txtPlyr1Nickname.setBounds(40, 247, 140, 25);
		matchPanel.add(txtPlyr1Nickname);

		txtPlyr2Nickname = new JLabel();
		txtPlyr2Nickname.setForeground(new Color(255, 255, 255));
		txtPlyr2Nickname.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(255, 255, 255)));
		txtPlyr2Nickname.setHorizontalAlignment(SwingConstants.CENTER);
		txtPlyr2Nickname.setText("PLAYER 2");
		txtPlyr2Nickname.setOpaque(false);
		txtPlyr2Nickname.setFont(new Font("Poppins", Font.PLAIN, 17));
		txtPlyr2Nickname.setBounds(300, 247, 140, 25);
		matchPanel.add(txtPlyr2Nickname);

		txtPlyr1Stat = new JLabel(String.valueOf(df.format(client.getAverageScore())));
		txtPlyr1Stat.setForeground(new Color(255, 255, 255));
		txtPlyr1Stat.setBorder(null);
		txtPlyr1Stat.setOpaque(false);
		txtPlyr1Stat.setHorizontalAlignment(SwingConstants.CENTER);
		txtPlyr1Stat.setFont(new Font("Pasajero", Font.PLAIN, 15));
		txtPlyr1Stat.setBounds(40, 272, 40, 25);
		matchPanel.add(txtPlyr1Stat);

		txtPlyr2Stat = new JLabel();
		txtPlyr2Stat.setText("0.0");
		txtPlyr2Stat.setForeground(new Color(255, 255, 255));
		txtPlyr2Stat.setBorder(null);
		txtPlyr2Stat.setOpaque(false);
		txtPlyr2Stat.setHorizontalAlignment(SwingConstants.CENTER);
		txtPlyr2Stat.setFont(new Font("Poppins", Font.PLAIN, 15));
		txtPlyr2Stat.setBounds(300, 272, 40, 25);
		matchPanel.add(txtPlyr2Stat);
		txtPlyr1Rank = new JLabel(String.valueOf(client.getRanking()));
		txtPlyr1Rank.setForeground(new Color(255, 255, 255));
		txtPlyr1Rank.setBorder(null);
		txtPlyr1Rank.setOpaque(false);
		txtPlyr1Rank.setHorizontalAlignment(SwingConstants.CENTER);
		txtPlyr1Rank.setFont(new Font("Pasajero", Font.PLAIN, 15));
		txtPlyr1Rank.setBounds(140, 272, 40, 25);
		matchPanel.add(txtPlyr1Rank);

		medal_txt = new JLabel("\uD83C\uDF96");
		medal_txt.setOpaque(false);
		medal_txt.setHorizontalAlignment(SwingConstants.CENTER);
		medal_txt.setForeground(new Color(255, 255, 0));
		medal_txt.setFont(new Font("Dialog", Font.PLAIN, 24));
		medal_txt.setBorder(null);
		medal_txt.setBounds(375, 268, 40, 29);
		matchPanel.add(medal_txt);

		txtPlyr2Rank = new JLabel();
		txtPlyr2Rank.setText("0");
		txtPlyr2Rank.setForeground(new Color(255, 255, 255));
		txtPlyr2Rank.setBorder(null);
		txtPlyr2Rank.setOpaque(false);
		txtPlyr2Rank.setHorizontalAlignment(SwingConstants.CENTER);
		txtPlyr2Rank.setFont(new Font("Poppins", Font.PLAIN, 15));
		txtPlyr2Rank.setBounds(400, 272, 40, 25);
		matchPanel.add(txtPlyr2Rank);

		JLabel lblMyChoice = new JLabel("YOUR DEMAND:");
		lblMyChoice.setForeground(new Color(255, 255, 255));
		lblMyChoice.setFont(new Font("Poppins", Font.PLAIN, 17));
		lblMyChoice.setBounds(140, 470, 130, 25);
		matchPanel.add(lblMyChoice);

		JLabel lblMyResult = new JLabel("YOUR RESULT:");
		lblMyResult.setHorizontalAlignment(SwingConstants.LEFT);
		lblMyResult.setForeground(new Color(255, 255, 0));
		lblMyResult.setFont(new Font("Poppins", Font.PLAIN, 17));
		lblMyResult.setBounds(140, 500, 130, 25);
		matchPanel.add(lblMyResult);

		txtMyResult = new JLabel();
		txtMyResult.setHorizontalAlignment(SwingConstants.CENTER);
		txtMyResult.setText("0");
		txtMyResult.setBorder(null);
		txtMyResult.setForeground(new Color(255, 255, 0));
		txtMyResult.setOpaque(false);
		txtMyResult.setFont(new Font("Poppins", Font.PLAIN, 17));
		txtMyResult.setBounds(270, 500, 40, 25);
		matchPanel.add(txtMyResult);

		txtMyChoice = new JLabel("0");
		txtMyChoice.setHorizontalAlignment(SwingConstants.CENTER);
		txtMyChoice.setForeground(new Color(255, 255, 255));
		txtMyChoice.setBorder(null);
		txtMyChoice.setOpaque(false);
		txtMyChoice.setFont(new Font("Poppins", Font.PLAIN, 17));
		txtMyChoice.setBounds(270, 470, 40, 25);
		matchPanel.add(txtMyChoice);

		lblTimer = new JLabel("0");
		lblTimer.setForeground(new Color(255, 255, 0));
		lblTimer.setFont(new Font("Poppins", Font.PLAIN, 26));
		lblTimer.setHorizontalAlignment(SwingConstants.CENTER);
		lblTimer.setBounds(190, 302, 100, 50);
		matchPanel.add(lblTimer);

		lblRound = new JLabel("ROUND 1");
		lblRound.setForeground(new Color(255, 255, 255));
		lblRound.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(255, 255, 255)));
		lblRound.setHorizontalAlignment(SwingConstants.CENTER);
		lblRound.setFont(new Font("Pasajero", Font.PLAIN, 26));
		lblRound.setBounds(10, 177, 460, 40);
		matchPanel.add(lblRound);

		JButton btn4 = new JButton("4");
		btn4.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btn4.setFocusPainted(false);
		btn4.setForeground(new Color(255, 255, 255));
		btn4.setBackground(new Color(50, 110, 160));
		btn4.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		btn4.setFont(new Font("Poppins", Font.PLAIN, 15));
		btn4.setBorderPainted(false);
		btn4.setBounds(295, 380, 50, 29);
		btn4.addActionListener(actionlistener);
		matchPanel.add(btn4);

		JButton btn3 = new JButton("3");
		btn3.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btn3.setFocusPainted(false);
		btn3.setForeground(new Color(255, 255, 255));
		btn3.setBackground(new Color(50, 110, 160));
		btn3.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		btn3.setFont(new Font("Poppins", Font.PLAIN, 15));
		btn3.setBorderPainted(false);
		btn3.setBounds(240, 380, 50, 29);
		btn3.addActionListener(actionlistener);
		matchPanel.add(btn3);

		JButton btn2 = new JButton("2");
		btn2.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btn2.setFocusPainted(false);
		btn2.setForeground(new Color(255, 255, 255));
		btn2.setBackground(new Color(50, 110, 160));
		btn2.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		btn2.setFont(new Font("Poppins", Font.PLAIN, 15));
		btn2.setBorderPainted(false);
		btn2.setBounds(185, 380, 50, 29);
		btn2.addActionListener(actionlistener);
		matchPanel.add(btn2);

		JButton btn1 = new JButton("1");
		btn1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btn1.setFocusPainted(false);
		btn1.setForeground(new Color(255, 255, 255));
		btn1.setBackground(new Color(50, 110, 160));
		btn1.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		btn1.setBorderPainted(false);
		btn1.setFont(new Font("Poppins", Font.PLAIN, 15));
		btn1.setBounds(130, 380, 50, 29);
		btn1.addActionListener(actionlistener);
		matchPanel.add(btn1);

		JButton btn9 = new JButton("9");
		btn9.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btn9.setFocusPainted(false);
		btn9.setForeground(new Color(255, 255, 255));
		btn9.setBackground(new Color(50, 110, 160));
		btn9.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		btn9.setBorderPainted(false);
		btn9.setFont(new Font("Poppins", Font.PLAIN, 15));
		btn9.setBounds(325, 417, 50, 29);
		btn9.addActionListener(actionlistener);
		matchPanel.add(btn9);

		JButton btn8 = new JButton("8");
		btn8.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btn8.setFocusPainted(false);
		btn8.setForeground(new Color(255, 255, 255));
		btn8.setBackground(new Color(50, 110, 160));
		btn8.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		btn8.setFont(new Font("Poppins", Font.PLAIN, 15));
		btn8.setBorderPainted(false);
		btn8.setBounds(270, 417, 50, 29);
		btn8.addActionListener(actionlistener);
		matchPanel.add(btn8);

		JButton btn7 = new JButton("7");
		btn7.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btn7.setFocusPainted(false);
		btn7.setForeground(new Color(255, 255, 255));
		btn7.setBackground(new Color(50, 110, 160));
		btn7.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		btn7.setFont(new Font("Poppins", Font.PLAIN, 15));
		btn7.setBorderPainted(false);
		btn7.setBounds(215, 417, 50, 29);
		btn7.addActionListener(actionlistener);
		matchPanel.add(btn7);

		JButton btn6 = new JButton("6");
		btn6.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btn6.setFocusPainted(false);
		btn6.setForeground(new Color(255, 255, 255));
		btn6.setBackground(new Color(50, 110, 160));
		btn6.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		btn6.setFont(new Font("Poppins", Font.PLAIN, 15));
		btn6.setBorderPainted(false);
		btn6.setBounds(160, 417, 50, 29);
		btn6.addActionListener(actionlistener);
		matchPanel.add(btn6);

		JButton btn5 = new JButton("5");
		btn5.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btn5.setFocusPainted(false);
		btn5.setForeground(new Color(255, 255, 255));
		btn5.setBackground(new Color(50, 110, 160));
		btn5.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		btn5.setFont(new Font("Poppins", Font.PLAIN, 15));
		btn5.setBounds(105, 417, 50, 29);
		btn5.setBorderPainted(false);
		btn5.addActionListener(actionlistener);
		matchPanel.add(btn5);

		JLabel lblbckgrd2 = new JLabel("");
		lblbckgrd2.setOpaque(true);
		lblbckgrd2.setBackground(new Color(50, 110, 160));
		lblbckgrd2.setBounds(290, 247, 160, 50);
		matchPanel.add(lblbckgrd2);

		medal_txt_1 = new JLabel("\uD83C\uDF96");
		medal_txt_1.setOpaque(false);
		medal_txt_1.setHorizontalAlignment(SwingConstants.CENTER);
		medal_txt_1.setForeground(new Color(255, 255, 0));
		medal_txt_1.setFont(new Font("Dialog", Font.PLAIN, 24));
		medal_txt_1.setBorder(null);
		medal_txt_1.setBounds(115, 268, 40, 29);
		matchPanel.add(medal_txt_1);

		JLabel lblbckgrd1 = new JLabel("");
		lblbckgrd1.setOpaque(true);
		lblbckgrd1.setBackground(new Color(50, 110, 160));
		lblbckgrd1.setBounds(30, 247, 160, 50);
		matchPanel.add(lblbckgrd1);

		JLabel lblTimer_1 = new JLabel("\u25EF");
		lblTimer_1.setHorizontalTextPosition(SwingConstants.CENTER);
		lblTimer_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblTimer_1.setForeground(new Color(255, 255, 255));
		lblTimer_1.setFont(new Font("Dialog", Font.PLAIN, 48));
		lblTimer_1.setBounds(190, 300, 100, 50);
		matchPanel.add(lblTimer_1);

		JLabel lblRound_1 = new JLabel("");
		lblRound_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblRound_1.setForeground(Color.WHITE);
		lblRound_1.setFont(new Font("Poppins", Font.PLAIN, 26));
		lblRound_1.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(255, 255, 255)));
		lblRound_1.setBounds(10, 495, 460, 40);
		matchPanel.add(lblRound_1);



		signInPanel = new JPanel();
		signInPanel.setOpaque(false);
		signInPanel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		signInPanel.setBackground(new Color(70, 130, 180));
		signInPanel.setBounds(10, 10, 480, 600);
		frame.getContentPane().add(signInPanel);
		signInPanel.setLayout(null);

		txtEmail = new JTextField();
		txtEmail.setBorder(null);
		txtEmail.setFont(new Font("Poppins", Font.PLAIN, 16));
		txtEmail.setBounds(100, 275, 250, 25);
		signInPanel.add(txtEmail);
		txtEmail.setColumns(10);

		txtPassword = new JPasswordField();
		txtPassword.setBorder(null);
		txtPassword.setFont(new Font("Poppins", Font.PLAIN, 16));
		txtPassword.setColumns(10);
		txtPassword.setBounds(100, 335, 250, 26);
		signInPanel.add(txtPassword);

		JLabel lblNewLabel = new JLabel("Email:");
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setFont(new Font("Poppins", Font.PLAIN, 16));
		lblNewLabel.setBounds(100, 250, 96, 25);
		signInPanel.add(lblNewLabel);

		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setForeground(new Color(255, 255, 255));
		lblPassword.setFont(new Font("Poppins", Font.PLAIN, 16));
		lblPassword.setBounds(100, 310, 96, 25);
		signInPanel.add(lblPassword);

		JButton btnLogin = new JButton("Login");
		btnLogin.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnLogin.setForeground(new Color(255, 255, 0));
		btnLogin.setBackground(new Color(60, 120, 170));
		btnLogin.setBorderPainted(false);
		btnLogin.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		btnLogin.setFont(new Font("Poppins", Font.PLAIN, 15));
		btnLogin.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String email = txtEmail.getText();
				String password = new String(txtPassword.getPassword());
				try {
					if(!validate(email))
						JOptionPane.showMessageDialog(frame, "PLEASE! CHECK YOUR EMAIL",null, JOptionPane.WARNING_MESSAGE);
					else {
						done = server.login(email, password, client);
						if(done) {
							txtPlayer.setText("WELCOME " + client.getNickname().toUpperCase());
							server.updateStatistics(client);
							signInPanel.setVisible(false);
							gPlatformPanel.setVisible(true);
							}
						else
							JOptionPane.showMessageDialog(frame, "PLEASE! CHECK YOUR CREDENTIALS", null, JOptionPane.WARNING_MESSAGE);
					}

				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnLogin.setBounds(230, 380, 120, 30);
		signInPanel.add(btnLogin);

		JButton btnExit = new JButton("X");
		btnExit.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnExit.setFont(new Font("Dialog", Font.PLAIN, 12));
		btnExit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				frame.dispose();
			}
		});
		btnExit.setBounds(431, 518, 39, 30);
		signInPanel.add(btnExit);

		JButton btnSignUp = new JButton("New Player");
		btnSignUp.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnSignUp.setBackground(new Color(40, 110, 160));
		btnSignUp.setBorderPainted(false);
		btnSignUp.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		btnSignUp.setForeground(new Color(255, 255, 0));
		btnSignUp.setFont(new Font("Poppins", Font.PLAIN, 15));
		btnSignUp.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				signInPanel.setVisible(false);
				signUpPanel.setVisible(true);	
			}
		});
		btnSignUp.setBounds(100, 380, 120, 30);
		signInPanel.add(btnSignUp);

		signUpPanel = new JPanel();
		signUpPanel.setOpaque(false);
		signUpPanel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		signUpPanel.setBounds(10, 10, 480, 600);
		frame.getContentPane().add(signUpPanel);
		signUpPanel.setLayout(null);

		JLabel lblNickname = new JLabel("Nickname:");
		lblNickname.setForeground(new Color(255, 255, 255));
		lblNickname.setFont(new Font("Poppins", Font.PLAIN, 15));
		lblNickname.setBounds(100, 250, 96, 25);
		signUpPanel.add(lblNickname);

		txtSNickname = new JTextField();
		txtSNickname.setFont(new Font("Poppins", Font.PLAIN, 15));
		txtSNickname.setColumns(10);
		txtSNickname.setBounds(100, 275, 250, 25);
		signUpPanel.add(txtSNickname);

		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setForeground(new Color(255, 255, 255));
		lblEmail.setFont(new Font("Poppins", Font.PLAIN, 15));
		lblEmail.setBounds(100, 310, 96, 25);
		signUpPanel.add(lblEmail);

		txtSEmail = new JTextField();
		txtSEmail.setFont(new Font("Poppins", Font.PLAIN, 15));
		txtSEmail.setColumns(10);
		txtSEmail.setBounds(101, 335, 250, 26);
		signUpPanel.add(txtSEmail);

		JLabel lblSpassword = new JLabel("Password");
		lblSpassword.setForeground(new Color(255, 255, 255));
		lblSpassword.setFont(new Font("Poppins", Font.PLAIN, 15));
		lblSpassword.setBounds(101, 375, 96, 25);
		signUpPanel.add(lblSpassword);

		txtSPassword = new JPasswordField();
		txtSPassword.setFont(new Font("Poppins", Font.PLAIN, 15));
		txtSPassword.setColumns(10);
		txtSPassword.setBounds(100, 400, 250, 26);
		signUpPanel.add(txtSPassword);

		JButton btnNewButton = new JButton("Login");
		btnNewButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnNewButton.setFont(new Font("Poppins", Font.PLAIN, 15));
		btnNewButton.setForeground(new Color(255, 255, 0));
		btnNewButton.setBackground(new Color(40, 100, 150));
		btnNewButton.setBorderPainted(false);
		btnNewButton.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));


		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				signUpPanel.setVisible(false);
				signInPanel.setVisible(true);
			}
		});
		btnNewButton.setBounds(100, 440, 120, 30);
		signUpPanel.add(btnNewButton);

		JButton btnsignUp = new JButton("Create");
		btnsignUp.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnsignUp.setFont(new Font("Poppins", Font.PLAIN, 15));
		btnsignUp.setFont(new Font("Poppins", Font.PLAIN, 15));
		btnsignUp.setForeground(new Color(255, 255, 0));
		btnsignUp.setBackground(new Color(60, 120, 170));
		btnsignUp.setBorderPainted(false);
		btnsignUp.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		btnsignUp.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String nickname = txtSNickname.getText();
				String email = txtSEmail.getText();
				String password = txtSPassword.getText();
				try {
					if(nickname.equals("")||email.equals("")||password.equals("")){
						JOptionPane.showMessageDialog(frame, "ALL FIELDS ARE REQUESTED", null, JOptionPane.WARNING_MESSAGE);
					}else {
						if(!validate(email))
							JOptionPane.showMessageDialog(frame, "PLEASE! CHECK YOUR EMAIL", null, JOptionPane.WARNING_MESSAGE);
						else {
							Credential login = new Credential(email, nickname);
							done  = server.signUp(login, password);
						}
						if(done == false)
							JOptionPane.showMessageDialog(frame, "WRONG CREDENTIALS", null, JOptionPane.WARNING_MESSAGE);
						else {
							signUpPanel.setVisible(false);
							signInPanel.setVisible(true);
						}

					}
				} catch (Exception exp) {
					// TODO Auto-generated catch block
					exp.printStackTrace();
				} 
			}
		});
		btnsignUp.setBounds(230, 440, 120, 30);
		signUpPanel.add(btnsignUp);

		gPlatformPanel = new JPanel();
		gPlatformPanel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		gPlatformPanel.setOpaque(false);
		gPlatformPanel.setBounds(10, 10, 480, 600);
		frame.getContentPane().add(gPlatformPanel);
		gPlatformPanel.setLayout(null);

		txtPlayer = new JTextField();
		txtPlayer.setText("WELCOME PLAYER 1");
		txtPlayer.setForeground(new Color(255, 255, 255));
		txtPlayer.setFont(new Font("Poppins", Font.PLAIN, 20));
		txtPlayer.setBorder(null);
		txtPlayer.setOpaque(false);
		txtPlayer.setHorizontalAlignment(SwingConstants.CENTER);
		txtPlayer.setColumns(10);
		txtPlayer.setBounds(10, 250, 460, 40);
		gPlatformPanel.add(txtPlayer);

		JButton btnPlayGame = new JButton("Play Game!");
		btnPlayGame.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnPlayGame.setFont(new Font("Poppins", Font.PLAIN, 18));
		btnPlayGame.setForeground(new Color(255, 255, 0));
		btnPlayGame.setBackground(new Color(60, 120, 170));
		btnPlayGame.setBorderPainted(false);
		btnPlayGame.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));

		btnPlayGame.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					match = server.playGame(client);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} 
			}
		});
		btnPlayGame.setBounds(145, 313, 200, 35);
		gPlatformPanel.add(btnPlayGame);
		winnerPanel = new JPanel();
		winnerPanel.setVisible(false);
		winnerPanel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		winnerPanel.setOpaque(false);
		winnerPanel.setBounds(10, 10, 480, 600);
		frame.getContentPane().add(winnerPanel);
		winnerPanel.setLayout(null);

		endMatchMessage = new JLabel("BravoYouWonorOopsYouLost");
		endMatchMessage.setForeground(new Color(255, 255, 0));
		endMatchMessage.setHorizontalAlignment(SwingConstants.CENTER);
		endMatchMessage.setFont(new Font("Poppins", Font.PLAIN, 22));
		endMatchMessage.setBounds(10, 250, 460, 50);
		winnerPanel.add(endMatchMessage);

		JLabel lblScore = new JLabel("Your Score");
		lblScore.setForeground(new Color(255, 255, 255));
		lblScore.setFont(new Font("Poppins", Font.PLAIN, 18));
		lblScore.setHorizontalAlignment(SwingConstants.CENTER);
		lblScore.setBounds(10, 320, 460, 30);
		winnerPanel.add(lblScore);

		endMatchScore = new JLabel("20");
		endMatchScore.setForeground(new Color(255, 255, 0));
		endMatchScore.setFont(new Font("Poppins", Font.BOLD, 24));
		endMatchScore.setHorizontalAlignment(SwingConstants.CENTER);
		endMatchScore.setBounds(10, 350, 460, 50);
		winnerPanel.add(endMatchScore);

		JButton btn_play_again = new JButton("Play Again");
		btn_play_again.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btn_play_again.setFocusPainted(false);
		btn_play_again.setFont(new Font("Poppins", Font.PLAIN, 18));
		btn_play_again.setBounds(169, 410, 150, 35);
		btn_play_again.setForeground(new Color(255, 255, 0));
		btn_play_again.setBackground(new Color(60, 120, 170));
		btn_play_again.setBorderPainted(false);
		btn_play_again.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));

		btn_play_again.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					winnerPanel.setVisible(false);
					match = server.playGame(client);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} 
			}
		});
		winnerPanel.add(btn_play_again);

		JLabel logo = new JLabel("Nash Demand Game");
		logo.setHorizontalAlignment(SwingConstants.CENTER);
		logo.setFont(new Font("Pasajero", Font.PLAIN, 24));
		logo.setForeground(new Color(240, 255, 255));
		logo.setIcon(null);
		logo.setBounds(10, 90, 480, 30);
		frame.getContentPane().add(logo);

		JLabel logo_1 = new JLabel(	"ECEX");
		logo_1.setHorizontalAlignment(SwingConstants.CENTER);
		logo_1.setForeground(new Color(173, 216, 230));
		logo_1.setFont(new Font("Pasajero", Font.PLAIN, 24));
		logo_1.setBounds(110, 50, 266, 42);
		frame.getContentPane().add(logo_1);

		JLabel copyright = new JLabel("NashDemandGame by Economic Experiments \u00A9 2020");
		copyright.setFont(new Font("Pasajero", Font.PLAIN, 15));
		copyright.setHorizontalAlignment(SwingConstants.CENTER);
		copyright.setForeground(new Color(240, 255, 255));
		copyright.setBounds(0, 570, 500, 25);
		frame.getContentPane().add(copyright);

		JLabel lbl_background = new JLabel("");
		lbl_background.setOpaque(true);
		lbl_background.setBackground(new Color(70, 130, 180));
		lbl_background.setBounds(0, 151, 500, 409);
		frame.getContentPane().add(lbl_background);

		JLabel lbl_background_1 = new JLabel("");
		lbl_background_1.setOpaque(true);
		lbl_background_1.setBackground(new Color(60, 120, 170));
		lbl_background_1.setBounds(0, 0, 500, 620);
		frame.getContentPane().add(lbl_background_1);

		this.thisScreen = this;
		client.setGame();
		hideAll();

	}

	/**
	 * notifies the player result.
	 * @param An integer 
	 * of the result.
	 * @return No return value.
	 */ 
	@Override
	public void notifyPlayerResult(int result) {
		// TODO Auto-generated method stub
		Thread t = new Thread() {
			public void run(){
				txtMyResult.setText(String.valueOf(result));
			}
		};
		t.start();

	}

	/**
	 * notifies the waiting Panel when there's no 
	 * two player available to start the game.
	 * @param No parameter. 
	 * of the result.
	 * @return No return value.
	 */ 
	@Override
	public void notifyWaiting() {
		// TODO Auto-generated method stub
		Thread t = new Thread() {
			public void run(){
				waitingPanel.setVisible(true);
				gPlatformPanel.setVisible(false);
				startTimer(30);
			}
		};
		t.start();
	}

	/**
	 * Starts the timer.
	 * @param An integer that represents the time in seconds. 
	 * @return No return value.
	 */ 
	protected void startTimer(int seconds) {
		// TODO Auto-generated method stub
		waitingTimer = new Thread() {
			public void run() {
				for(int i=seconds; i>=1; i--) {
					try {
						lblWaitingTimer.setText(String.valueOf(i));
						sleep(1000);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						break;
					}
				}
				try {
					startGameWithAI();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		waitingTimer.start();
	}

	/**
	 * Updates the timer on the UI.
	 * @param An integer that represents the seconds. 
	 * @return No return value.
	 */ 
	@Override
	public void notifyUpdateTimer(int sec) {
		// TODO Auto-generated method stub
		Thread t = new Thread() {
			public void run(){
				lblTimer.setText(String.valueOf(sec));
			}
		};
		t.start();
	}

	/**
	 * Notifies the beginning of a new round.
	 * @param An integer that represents the round number. 
	 * @return No return value.
	 */ 
	@Override
	public void notifyNewRound(int round) {
		// TODO Auto-generated method stub
		Thread t = new Thread() {
			public void run(){
				lblRound.setText("ROUND " + String.valueOf(round));
				if (round==1) {
					txtMyChoice.setText("");
					txtMyResult.setText("");
				}
			}
		};
		t.start();
	}

	/**
	 * Notifies the winner of the game on the UI.
	 * @param An integer that represents the winner score. 
	 * @return No return value.
	 */ 
	@Override
	public void notifyWinner(int score) {
		// TODO Auto-generated method stub
		Thread t = new Thread() {
			public void run(){
				try {
					matchPanel.setVisible(false);
					winnerPanel.setVisible(true);
					endMatchMessage.setText("Bravo "+client.getNickname()+" You Won!");
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				endMatchScore.setText(String.valueOf(score));
			}
		};
		t.start();
	}

	/**
	 * Notifies the loser of the game on the UI.
	 * @param An integer that represents the loser score. 
	 * @return No return value.
	 */ 
	@Override
	public void notifyLoser(int score) {
		// TODO Auto-generated method stub
		Thread t = new Thread() {
			public void run(){
				try {
					matchPanel.setVisible(false);
					winnerPanel.setVisible(true);
					endMatchMessage.setText("Oops "+client.getNickname()+" You Lost!");
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				endMatchScore.setText(String.valueOf(score));
			}
		};
		t.start();
	}

	/**
	 * Notifies the opponents details.
	 * @param a string as a nickname, a double the average score,
	 * an integer as a ranking. 
	 * @return No return value.
	 */ 
	@Override
	public void notifyOpponentDetails(String nickname, double averageScore, int ranking) {
		// TODO Auto-generated method stub
		Thread t = new Thread() {
			public void run(){
				txtPlyr2Nickname.setText(nickname.toUpperCase());
				txtPlyr2Stat.setText(String.valueOf(df.format(averageScore)));
				txtPlyr2Rank.setText(String.valueOf(String.valueOf(ranking)));
			}
		};
		t.start();

	}

	/**
	 * shows the Game Platform on the UI to start the game.
	 * @param No parameters. 
	 * @return No return value.
	 */ 
	@Override
	public void showGamePlatform() {
		// TODO Auto-generated method stub
		Thread t = new Thread() {
			public void run(){
				try {
					txtPlyr1Nickname.setText(client.getNickname().toUpperCase());
					txtPlyr1Stat.setText(String.valueOf(df.format(client.getAverageScore())));
					txtPlyr1Rank.setText(String.valueOf(client.getRanking()));
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				matchPanel.setVisible(true);
				gPlatformPanel.setVisible(false);
				waitingPanel.setVisible(false);
			}
		};
		t.start();

	}

	/**
	 * Starts the game against an AI.
	 * @param No parameters.
	 * @exception Any exception. 
	 * @return No return value.
	 */ 
	public void startGameWithAI() throws Exception {
		if (waitingTimer != null ) { 
			waitingTimer.interrupt();
			waitingTimer = null;
		}
		clientAI = new ClientListener();
		server.playAgainstAI(clientAI);
	}

	/**
	 * hides all the panel on the UI.
	 * @param No parameters.
	 * @exception Any exception. 
	 * @return No return value.
	 */ 
	public void hideAll() {
		signUpPanel.setVisible(false);
		gPlatformPanel.setVisible(false);
		waitingPanel.setVisible(false);
	}

	public static boolean validate(String emailStr) {
		Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
		return matcher.find();
	}
}
