package com.brzesky.cursomc.config;


import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.brzesky.cursomc.security.JWTAuthenticationFilter;
import com.brzesky.cursomc.security.JWTUtil;



@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter
{
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private Environment env;
	
	@Autowired
	private JWTUtil jwtUtil;
	
	private static final String[] PUBLIC_MATCHERS =
	{
		"/h2-console/**",	
	};
	
	//Metdo que permit apenas recuperar os dados.
	private static final String[] PUBLIC_MATCHERS_GET =
	{
		"/produtos/**",
		"/categorias/**",
		"/clientes/**"
	};
	
	@Override
	protected void configure(HttpSecurity http) throws Exception
	{	
		//Metodo que libera o profile test para ser executar o H2.
		if(Arrays.asList(env.getActiveProfiles()).contains("test"))
		{
			http.headers().frameOptions().disable();
		}
		
//		http.cors();
		http.cors().and().csrf().disable();//Disabilita o sistema de segurança para armazenamento de sessão CSRF.
		http.authorizeRequests().antMatchers(PUBLIC_MATCHERS).permitAll()
								.antMatchers(HttpMethod.GET,PUBLIC_MATCHERS_GET).permitAll()
								.anyRequest().authenticated();
		http.addFilter(new JWTAuthenticationFilter(authenticationManager(), jwtUtil));
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);//Para garantir que nosso BackEnd não vai criar sessão de usuário.
	}
	
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception
	{
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
	}
	
	@Bean
	CorsConfigurationSource corsConfigurationSource()
	{
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
		return (CorsConfigurationSource) source;
	}
	
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder()
	{
		return new BCryptPasswordEncoder();
	}
}


























