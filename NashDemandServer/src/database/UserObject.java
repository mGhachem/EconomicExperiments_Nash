package database;

import shared.UserObjectInterface;

public class UserObject implements UserObjectInterface {
	
	private String idUser;
	private String nickname;
	private String email;
	private String password;
	
	public UserObject(String idUser, String nickname, String email, String password) {
		this.idUser = idUser;
		this.nickname = nickname;
		this.email = email;
		this.password = password;;
	}
	
	@Override
	public String getIdUser() {
		return idUser;
	}

	@Override
	public String getNickname() {
		return nickname;
	}

	@Override
	public String getEmail() {
		return email;
	}
	
	@Override
	public String getPassword() {
		return password;
	}
}
