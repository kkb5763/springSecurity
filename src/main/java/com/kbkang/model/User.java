package com.kbkang.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// ORM - Object Relation Mapping

@Builder
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class User {
	@Id // primary key
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int userid;
	private String username;
	private String password;
	private String email;
	private String role; //ROLE_USER, ADMIN, MANAGER ... 총 8개
	// OAuth를 위해 구성한 추가 필드 2개
	private String provider;
	private String providerId;

	private String roles;

	// ENUM으로 안하고 ,로 해서 구분해서 ROLE을 입력 -> 그걸 파싱!!
	public List<String> getRoleList(){
		if(this.roles.length() > 0){
			return Arrays.asList(this.roles.split(","));
		}
		return new ArrayList<>();
	}
//	@CreationTimestamp
//	private Timestamp createDate;
}
