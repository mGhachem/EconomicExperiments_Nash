package database;

import java.time.LocalDateTime;
import java.time.LocalTime;

import shared.MatchObjectInterface;

public class MatchObject implements MatchObjectInterface {
	
	private String idMatch;
	private String time;
	private String date;
	private String player1;
	private String player2;
	
	
	
	public MatchObject(String idMatch, LocalDateTime currentTime, String player1, String player2) {
		super();
		this.idMatch = idMatch;
		this.time = currentTime.toLocalTime().toString();
		this.date = currentTime.toLocalDate().toString();
		this.player1 = player1;
		this.player2 = player2;
	}
	
	public MatchObject(String idMatch2, LocalDateTime currentTime) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getIdMatch() {
		return idMatch;
	}
	@Override
	public String getTime() {
		return time;
	}
	@Override
	public String getDate() {
		return date;
	}
	@Override
	public String getPlayer1() {
		return player1;
	}
	@Override
	public String getPlayer2() {
		return player2;
	}
}
