package com.example.project_board;

import com.example.project_board.entity.account.Member;
import com.example.project_board.repository.account.MemberRepository;
import com.example.project_board.service.apiTest.PublicAPI;
import com.example.project_board.service.textTransfer.SeleniumExample;
import com.example.project_board.service.textTransfer.TextTransfer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ProjectBoardApplicationTests {

	@Autowired
	SeleniumExample seleniumExample;

	@Test
	void ScrapingTest() {
		seleniumExample.scraping();
	}

	@Autowired
	TextTransfer textTransfer;

	@Autowired
	PublicAPI publicAPI;

	@Test
	void apiTest() {
		publicAPI.testAPI();
	}

	@Autowired
	MemberRepository memberRepository;

	@Test
	@DisplayName("저장, 데이터가 잘 들어갔는지 확인")
	void contextSave(){
		//Setter로 엔티티를 생성하고 repository가 정상 작동하는지 확인
		Member member = new Member();
		//클라이언트에서 controller에 데이터를 전달하는 내용을 setter를 통해 대체
		member.setId("humanClass4");
		member.setPassword("12341234");
		member.setEmail("class4@gmail.com");
		//memberRepository의 save메소드가 정상 동작하는지 확인
		memberRepository.save(member);
	}

	@Test
	void textTest() throws Exception{
		textTransfer.transferText3Word("abcdefg@gmail.com");
	}
}
