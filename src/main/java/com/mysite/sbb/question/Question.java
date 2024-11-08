package com.mysite.sbb.question;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import com.mysite.sbb.answer.Answer;

// 일반적으로 Entity는 Setter Method를 사용하지 않는게 좋음 
// 엔티티는 데이터베이스와 바로 연결되므로 데이터를 자유롭게 변경하는 것이 안전하지 않다고 판단하기 때문
// 값은 엔티티의 생성자에 의해서만 저장할 수 있게 하고, 변경을 위한 메서드를 추가 작성하는 것이 바람직하다
// 여기서는 복잡도를 낮추고 원활한 설명을 위해 Setter Method를 사용하였음
@Getter
@Setter
@Entity
public class Question {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(length = 200)
	private String subject; // 질문 제목 

	@Column(columnDefinition = "TEXT")
	private String content; // 질문 내용
	
	private LocalDateTime createDate;

	@OneToMany(mappedBy = "question", cascade=CascadeType.REMOVE)
	private List<Answer> answerList;
}
