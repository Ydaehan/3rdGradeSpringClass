package com.mysite.sbb;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.transaction.annotation.Transactional;

import com.mysite.sbb.answer.Answer;
import com.mysite.sbb.answer.AnswerRepository;
import com.mysite.sbb.question.Question;
import com.mysite.sbb.question.QuestionRepository;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
class SbbApplicationTests {

  @Autowired
  private QuestionRepository questionRepository;

	@Autowired
	private AnswerRepository answerRepository;

   @Test
   void contextLoads() {

   }

  @Test
  void testJpa() {
    Question q1 = new Question();
    q1.setSubject("sbb가 무엇인가요?");
    q1.setContent("sbb에 대해서 알고 싶습니다.");
    q1.setCreateDate(LocalDateTime.now());
    this.questionRepository.save(q1);
  
    Question q2 = new Question();
    q2.setSubject("스프링 부트 모델 질문입니다.");
    q2.setContent("id는 자동으로 생성되나요?");
    q2.setCreateDate(LocalDateTime.now());
    this.questionRepository.save(q2);

  }

  @Test
  void testJpa2() {
    List<Question> all = this.questionRepository.findAll();
    
    Question q = all.get(0);
    assertEquals("sbb가 무엇인가요?", q.getSubject());
  }
  
  @Test
  void testJpa3() {
    Optional<Question> oq = this.questionRepository.findById(1);
    if(oq.isPresent()){
      Question q = oq.get();
      assertEquals("sbb가 무엇인가요?", q.getSubject());
    }
  }

	@Test
	void testJpa04() {
		// select * from question where subject = ?
		// => QuestionRepository
		Question q = this.questionRepository.findBySubject("sbb가 무엇인가요?");
		assertEquals(1,q.getId()); 
	}

	@Test
	void testJpa05() {
		// select * from question where subject = ? and content = ?
		// QuestionRepository의 findBySubjectAndContent 호출

		Question q = this.questionRepository.findBySubjectAndContent("sbb가 무엇인가요?", "sbb에 대해서 알고 싶습니다.");

		// 검색된 레코드는 첫번째로 입력된 레코드이고 따라서 id는 1번이어야 한다.
		assertEquals(1, q.getId());

		// select * from question where subject = ? and content = ?
		// => findBySubjectAndContent 메서드 선언

		// select * from question where createDate between ? and ?
		// => findByCreateDateBetween 메서드 선언

		// select * from question where id < ? 
		// => findByIdLessThan 메서드 선언

		// select * from question where id >= ?
		// => findByIdGreaterThanEqual 메서드 선언

		// select * from question where subject like ?
		// => findBySubjectLike 메서드 선언 

		// select * from question where subject in (?, ?, ?, ...)
		// => findBySubjectIn(String[] subjects) 메서드 선언

		// select * from question where subject = ? order by createDate asc
		// => findBySubjectOrderByCreateDateAsc 메서드 선언

	}
	@Test
	void testJpa06() {
			// select * from question where subject like ? -> 패턴검색. pattern search
			// => QuestionRepository에 findBySubjectLike 메서드 추가 필요

			// 질문의 제목이 sbb로 시작하는 것을 찾고자 한다.
			// select * from question where subject like 'sbb%'
			List<Question> qList = this.questionRepository.findBySubjectLike("sbb%");
			Question q = qList.get(0);
			assertEquals("sbb가 무엇인가요?", q.getSubject());

			// 질문의 제목이 sbb로 끝나는 것을 찾을 때는
			// select * from question where subject like '%sbb'
			
			// 질문의 제목에 sbb를 포함하는 것을 찾을 때는
			// select * from question where subject like '%sbb%'

			// 다음 시간에 질문 데이터 수정하기. PPT 111 page 부터.
			
	}

	@Test
	void testJpa07() {
		// 1. Question table에 레코드가 2개 있음을 확인
		assertEquals(2, this.questionRepository.count());
		// 2. Question table에서 id가 2인 레코드를 삭제
		// 그렇게 하려면 일단 id가 1인 레코드를 인출해야 한다.
		Optional<Question> oq = this.questionRepository.findById(1);
		assertTrue(oq.isPresent());
		Question q = oq.get();

		// 3. QuestionRepository의 delete 메서드를 호출하면서 삭제할
		// question 객체를 인자로 전달해야한다.
		this.questionRepository.delete(q);
		assertEquals(1, this.questionRepository.count());
	}

	@Test
	void testJpa08() {

	}

	// @Test
	void testJpa09() {
		Optional<Answer> oa = this.answerRepository.findById(1);
		assertTrue(oa.isPresent());
		Answer answer = oa.get();

		// 답변 데이터를 통해 질문 데이터를 가져왔음
		assertEquals(2, answer.getQuestion().getId());
	}

	@Transactional
	@Test 
	void testJpa10() {
		// 질문 데이터를 통해 답변 데이터 가져오기
		// 1. 질문 데이터 가져오기
		Optional<Question> oq = this.questionRepository.findById(2);
		assertTrue(oq.isPresent());
		Question q = oq.get();

		// 2. 질문에 대한 답변 가져오기
		List<Answer> answerList = q.getAnswerList();
		assertEquals(1, answerList.size());
		assertEquals("네 자동으로 생성됩니다.", answerList.get(0).getContent());
	}
}
