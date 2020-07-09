package database;

import shared.MoveObjectInterface;

public class MoveObject implements MoveObjectInterface {
	
	private String idMove;
	private String idMatch;
	private String player;
	private int number;
	private int result;
	private int round;
	
	public MoveObject(String idMove, String idMatch,int round, String player, int number, int result) {
		super();
		this.idMove = idMove;
		this.idMatch = idMatch;
		this.player = player;
		this.number = number;
		this.result = result;
		this.round = round;
	}

	@Override
	public int getRound() {
		return round;
	}

	@Override
	public void setRound(int round) {
		this.round = round;
	}

	@Override
	public String getIdMove() {
		return idMove;
	}

	@Override
	public String getIdMatch() {
		return idMatch;
	}

	@Override
	public String getPlayer() {
		return player;
	}

	@Override
	public int getNumber() {
		return number;
	}

	@Override
	public int getResult() {
		return result;
	}
}
