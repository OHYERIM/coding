package com.example.project_board;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@EnableJpaAuditing //@EnableJpaAuditing은 Setter를 사용하지 않아도 Entity의 @CreateDate, @LastModifiedDate를 자동으로 Date값을 주입해준다.
				   //왜 사용하는건지? Date를 주입하거나 설정하는 부분이 총 3가지가 있다.
				   //1. 클라이언트: 사용자가 임의로 날짜를 수정할 수 있는 위험
				   //   이미 클라이언트에서 DATE정보를 전달받으면 쉽게 Entity에 데이터를 입력이 가능하기 때문에
				   //2. 서버: 클라이언트가 접속하는 서버의 날짜 기준으로 일관성이 있다는 장점이 있음
				   //   단점으로는 서버에서 날짜 내장메소드를 실행하는 리소스 문제가 있음
				   //3. DB: DB는 모든 정보를 총괄하는 1개뿐인 서버(날짜를 완전히 일관성있게 만들 수 있음)
				   //   하지만, 모든 백엔드가 접속하기 때문에 리소스 문제를 야기할 가능성이 높다는 단점이 있음
@SpringBootApplication
public class ProjectBoardApplication {
	public static void main(String[] args) {

		SpringApplication.run(ProjectBoardApplication.class, args);

	}

}
