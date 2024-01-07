package com.kbkang.config.auth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import com.kbkang.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

// Authentication 객체에 저장할 수 있는 유일한 타입
public class PrincipalDetails implements UserDetails, OAuth2User{

	private static final long serialVersionUID = 1L;
	private User User;
	private Map<String, Object> attributes;

	// 일반 시큐리티 로그인시 사용
	public PrincipalDetails(User User) {
		this.User = User;
	}
	
	// OAuth2.0 로그인시 사용
	public PrincipalDetails(User User, Map<String, Object> attributes) {
		this.User = User;
		this.attributes = attributes;
	}
	
	public User getUser() {
		return User;
	}

	@Override
	public String getPassword() {
		return User.getPassword();
	}

	@Override
	public String getUsername() {
		return User.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(()->{ return getUser().getRole();});
		return authorities;
	}

	// 리소스 서버로 부터 받는 회원정보
	@Override
	public Map<String, Object> getAttributes() {
		return attributes;
	}

	// User의 PrimaryKey
	@Override
	public String getName() {
		return User.getUserid()+"";
	}
	
}
