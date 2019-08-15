package com.chandra.retail.message.response;

/**
 * @author Chandramani Mishra
 *
 */
public class JwtResponse {

	private String token;
    private String type = "Bearer";
    private String loginuser;
    private String role;

	public JwtResponse(String token, String loginuser, String role) {
		this.token = token;
		this.loginuser = loginuser;
		this.role = role;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getLoginuser() {
		return loginuser;
	}

	public void setLoginuser(String loginuser) {
		this.loginuser = loginuser;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
	
    
}
