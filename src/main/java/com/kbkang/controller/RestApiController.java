package com.kbkang.controller;


import com.kbkang.config.auth.PrincipalDetails;
import com.kbkang.config.jwt.provider.JwtProvider;
import com.kbkang.model.User;
import com.kbkang.repository.UserRepository;
import com.kbkang.response.TokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
// @CrossOrigin  // CORS 허용 
public class RestApiController {
	
	private final UserRepository UserRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	private final JwtProvider jwtProvider;



	// 모든 사람이 접근 가능
	@GetMapping("home")
	public List<User> home() {
		return UserRepository.findAll();
	}

	// 유저 혹은 매니저 혹은 어드민이 접근 가능
	@GetMapping("user")
	public List<User> user() {
		return UserRepository.findAll();
	}

	// 매니저 혹은 어드민이 접근 가능
	@GetMapping("manager/test")
	public String reports() {
		return "<h1>reports</h1>";
	}

	// 어드민이 접근 가능
	@GetMapping("admin/users")
	public List<User> users(){
		return UserRepository.findAll();
	}
	
	@PostMapping("join")
	public String join(@RequestBody User User) {
		User.setPassword(bCryptPasswordEncoder.encode(User.getPassword()));
		User.setRoles("ROLE_"+ User.getRole());
		User.setRole("ROLE_"+ User.getRole());
		UserRepository.save(User);
		return "회원가입완료";
	}

	@PostMapping(value = "/login", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ResponseEntity<TokenResponse> login(@RequestParam("username") String username,
											   @RequestParam("role") String role) {
//		String token = jwtProvider.createToken(user.getUsername());

		String token = jwtProvider.createToken(username);

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
	@Secured({"ROLE_UaaSER", "UaSER"})
	public String profile(@RequestParam("") String token){
		//DB -> acess token 가져오기
//		eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0IiwiaWF0IjoxNzA0NjA1OTk3LCJleHAiOjE3MDU0Njk5OTd9.Yf-ZciPtEhbKpsX73vHCrSjbJRcjFynsDD9BlhuKr9w
		return "profile 접근 성공";
	}

	@PostMapping("/matching")
	@Secured({"ROLE_USER", "USER"})
	public String matching(@RequestParam("") String token){
		return "matching 접근 성공";
	}
}











