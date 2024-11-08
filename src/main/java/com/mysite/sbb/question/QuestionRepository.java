package com.mysite.sbb.question;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Integer> {
	// List<Question> findBySubject(String subject);
	// 위와 같이 List로 결과를 반환하는 것이 맞지만,
	// JUnit 테스트용으로 아래와 같이 하나의 객체만 결과로 반환하는 것으로 선언
	
	Question findBySubject(String subject);
	// List<Question> findBySubjectAndContent(String subject, String content);
	Question findBySubjectAndContent(String subject, String content);

	List<Question> findBySubjectLike(String subject);
}

