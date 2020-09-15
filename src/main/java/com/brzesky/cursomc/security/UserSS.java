package com.brzesky.cursomc.security;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.brzesky.cursomc.domain.enums.Perfil;

public class UserSS implements UserDetails
{
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String email;
	private String senha;
	private Collection<? extends GrantedAuthority> authorities;
	
	public UserSS(){}
	
	public UserSS(Integer id, String email, String senha, Set<Perfil> perfis) {
		super();
		this.id = id;
		this.email = email;
		this.senha = senha;
		this.authorities = perfis.stream().map(x -> new SimpleGrantedAuthority(x.getDescricao())).collect(Collectors.toList());
	}

	public Integer getId()
	{
		return id;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() 
	{
		return authorities;
	}

	@Override
	public String getPassword() 
	{
		return senha;
	}

	@Override
	public String getUsername() 
	{
		return email;
	}

	@Override //Verifica se a conta não está espirada.
	public boolean isAccountNonExpired() 
	{
		return true;
	}

	@Override //Verifica se a conta não está bloqueada.
	public boolean isAccountNonLocked() 
	{
		return true;
	}

	@Override //Verifica se as credenciais não estão expiradas.
	public boolean isCredentialsNonExpired() 
	{
		return true;
	}

	@Override //Verifica se o usuário está ativo.
	public boolean isEnabled() 
	{
		return true;
	}

}
