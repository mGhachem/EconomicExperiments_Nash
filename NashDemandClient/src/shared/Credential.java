package shared;

import java.io.Serializable;

public class Credential implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String email;
	private String nickname;
	
	
	public Credential(String email, String nickname) {
		super();
		this.email = email;
		this.nickname = nickname;
	}

	public String getNickname() {
		return nickname;
	}

	public String getEmail() {
		// TODO Auto-generated method stub
		return email;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public void setEmail(String email) {
		this.email = email;
	}	
}
