package com.brzesky.cursomc.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JWTUtil 
{
	@Value("${jwt.secret}")
	private String secret;
	
	@Value("${jwt.expiration}")
	private Long expiration;
	
	//Metodo que gera o Token
	public String generateToken(String username)
	{
		return Jwts.builder().setSubject(username)
							 .setExpiration(new Date(System.currentTimeMillis() + expiration))
							 .signWith(SignatureAlgorithm.HS512, secret.getBytes())
							 .compact();
	}
	
}


//jwt.secret = SequenciaDeCaracteresParaAssinarToken
//jwt.expiration = 60000