package com.kbkang.controller;


import com.kbkang.config.auth.PrincipalDetails;
import com.kbkang.config.jwt.provider.JwtProvider;
import com.kbkang.model.User;
import com.kbkang.repository.UserRepository;
import com.kbkang.response.TokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
// @CrossOrigin  // CORS 허용 
public class RestApiController {
	
	private final UserRepository userRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	private final JwtProvider jwtProvider;



	// 모든 사람이 접근 가능
	@GetMapping("home")
	public String home() {
		return "<h1>home</h1>";
	}
	
	// Tip : JWT를 사용하면 UserDetailsService를 호출하지 않기 때문에 @AuthenticationPrincipal 사용 불가능.
	// 왜냐하면 @AuthenticationPrincipal은 UserDetailsService에서 리턴될 때 만들어지기 때문이다.
	
	// 유저 혹은 매니저 혹은 어드민이 접근 가능
	@GetMapping("user")
	public String user(Authentication authentication) {
		PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
		System.out.println("principal : "+principal.getUser().getId());
		System.out.println("principal : "+principal.getUser().getUsername());
		System.out.println("principal : "+principal.getUser().getPassword());
		
		return "<h1>user</h1>";
	}
	
	// 매니저 혹은 어드민이 접근 가능
	@GetMapping("manager/reports")
	public String reports() {
		return "<h1>reports</h1>";
	}
	
	// 어드민이 접근 가능
	@GetMapping("admin/users")
	public List<User> users(){
		return userRepository.findAll();
	}
	
	@PostMapping("join")
	public String join(@RequestBody User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		user.setRoles("ROLE"+user.getRole());
		userRepository.save(user);
		return "회원가입완료";
	}

	@PostMapping(value = "/login", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ResponseEntity<TokenResponse> login(@RequestParam("username") String username,
											   @RequestParam("role") String role) {
//		String token = jwtProvider.createToken(user.getUsername());

		String token = jwtProvider.createToken(username+"||"+role);

		//db - insert (일단 maria , 나중에 redis)
		//{accessToken: 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0IiwiaWF0IjoxN…zODV9.nvOD2zrLKVUT13OkGjatCZXh3e_vRYKWg4MBTC8VvjU'
		return ResponseEntity.ok().body(new TokenResponse(token, "bearer"));
	}

	@PostMapping("/jwtAuth")
	public String jwtAuth(@RequestParam("") String token){
		//DB -> acess token 가져오기
//		eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0IiwiaWF0IjoxNzA0NjA1OTk3LCJleHAiOjE3MDU0Njk5OTd9.Yf-ZciPtEhbKpsX73vHCrSjbJRcjFynsDD9BlhuKr9w
		String sample_token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0IiwiaWF0IjoxNzA0NjA1OTk3LCJleHAiOjE3MDU0Njk5OTd9.Yf-ZciPtEhbKpsX73vHCrSjbJRcjFynsDD9BlhuKr9w";
		//token validation check
		String subject = jwtProvider.getSubject(token);

		boolean validResult = jwtProvider.validateToken(token);

		if(validResult || sample_token.equals(token)){
			return "sucess_인증";
		}else {
			return "fail_인증";
		}
	}

	@PostMapping("/profile")
	public String profile(@RequestParam("") String token){
		//DB -> acess token 가져오기
//		eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0IiwiaWF0IjoxNzA0NjA1OTk3LCJleHAiOjE3MDU0Njk5OTd9.Yf-ZciPtEhbKpsX73vHCrSjbJRcjFynsDD9BlhuKr9w
		String sample_token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0IiwiaWF0IjoxNzA0NjA1OTk3LCJleHAiOjE3MDU0Njk5OTd9.Yf-ZciPtEhbKpsX73vHCrSjbJRcjFynsDD9BlhuKr9w";
		//token validation check
		String subject = jwtProvider.getSubject(token);

		boolean validResult = jwtProvider.validateToken(token);

		if(validResult || sample_token.equals(token)){
			return "sucess_인증";
		}else {
			return "fail_인증";
		}
	}
}











