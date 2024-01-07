package com.kbkang.config.auth;

import com.kbkang.model.User;
import com.kbkang.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class PrincipalDetailsService implements UserDetailsService{

	@Autowired
	private UserRepository UserRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User User = UserRepository.findByUsername(username);
		if(User == null) {
			return null;
		}else {
			return new PrincipalDetails(User);
		}
		
	}

}
