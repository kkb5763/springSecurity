package com.kbkang.config;

import com.kbkang.config.jwt.JwtAuthenticationFilter;
import com.kbkang.config.jwt.JwtAuthorizationFilter;
import com.kbkang.config.oauth.PrincipalOauth2UserService;
import com.kbkang.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;

@Configuration // IoC 빈(bean)을 등록
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private PrincipalOauth2UserService principalOauth2UserService;
//	@Autowired
//	private PrincipalDetailsService principalDetailsService;
	@Autowired
	private UserRepository UserRepository;
	@Autowired
	private DataSource dataSource;
	@Bean
	public BCryptPasswordEncoder encodePwd() {
		return new BCryptPasswordEncoder();
	}
	@Autowired
	private CorsConfig corsConfig;


	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				.addFilter(corsConfig.corsFilter())
				.csrf().disable()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and().formLogin().disable().httpBasic().disable()
				.addFilter(new JwtAuthenticationFilter(authenticationManager()))
				.addFilter(new JwtAuthorizationFilter(authenticationManager(), UserRepository))
		;
		http.authorizeRequests()
//				.formLogin().disable()
//				.httpBasic().disable()
				.antMatchers("/api/v1/login").permitAll()
				.antMatchers("/api/v1/profile").authenticated()
				.antMatchers("/api/v1/match/**").authenticated()
				.antMatchers("/api/v1/admin/**").authenticated()

//				.antMatchers("/user/**").authenticated()
				// .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN') or
				// hasRole('ROLE_USER')")
				// .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN') and
				// hasRole('ROLE_USER')")
//				.antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
				.anyRequest().permitAll();
//				.and()
//				.formLogin().disable()
//				.loginPage("/login")
//				.loginProcessingUrl("/loginProc")
//				.defaultSuccessUrl("/")
//				.and()
//				.oauth2Login()
//				.loginPage("/login")
//				.userInfoEndpoint()
				//.userService(principalDetailsService);
//				.userService(principalOauth2UserService);

	}
}
