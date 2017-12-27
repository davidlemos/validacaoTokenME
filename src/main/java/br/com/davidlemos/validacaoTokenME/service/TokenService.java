package br.com.davidlemos.validacaoTokenME.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;


public class TokenService {
		
	private static final String SECRETKEY = "M0nk3y3xch4ng3";
    	
	public String validarToken(String authToken) {
		String email ="";
		try {
			 Claims claims = Jwts.parser()
				        .setSigningKey(SECRETKEY)
				        .parseClaimsJws(authToken)
				        .getBody();
			 email = claims.get("sub").toString();
			 
			 System.out.println( claims.get("sub").toString());
			 return email;
			
		} catch (Exception e) {
			return email;
		}		
	
	}

}
