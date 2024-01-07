package com.kbkang.repository;

import com.kbkang.model.Member;
import com.kbkang.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * 
 * @author
 * JPA는 기본 CRUD를 JpaRepository가 상속하는 CrudRepository가 가지고 있음.
 * JPA는 method names 전략을 가짐. README.md 사진 참고
 */

// JpaRepository 를 상속하면 자동 컴포넌트 스캔됨.
public interface MemberRepository extends JpaRepository<Member, Integer>{
	//
	Member findByUsername(String username);

}

