package com.kbkang.config.jwt;


import com.kbkang.config.auth.PrincipalDetails;
import com.kbkang.model.User;
import com.kbkang.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// 인가
public class JwtAuthorizationFilter extends BasicAuthenticationFilter{
	
	private UserRepository UserRepository;
	
	public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UserRepository UserRepository) {
		super(authenticationManager);
		this.UserRepository = UserRepository;
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		String header = request.getHeader(JwtProperties.HEADER_STRING);
		if(header == null || !header.startsWith(JwtProperties.TOKEN_PREFIX)) {
			chain.doFilter(request, response);
                        return;
		}
		System.out.println("header : "+header);
		String token = request.getHeader(JwtProperties.HEADER_STRING)
				.replace(JwtProperties.TOKEN_PREFIX, "");
		String secretKey = JwtProperties.SECRET;

//		String username = JWT.require(Algorithm.HMAC512(JwtProperties.SECRET)).build().verify(token)
//				.getClaim("username").asString();
		String username = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
//		String username = "test";
		if(username != null) {	
			User User = UserRepository.findByUsername(username);

			PrincipalDetails principalDetails = new PrincipalDetails(User);
			Authentication authentication =
					new UsernamePasswordAuthenticationToken(
							principalDetails,
							null, // 패스워드는 모르니까 null 처리,
							principalDetails.getAuthorities());
			
			// 강제로 시큐리티의 세션에 접근하여 값 저장
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}
	
		chain.doFilter(request, response);
	}
	
}
