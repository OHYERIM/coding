package com.example.project_board.service.apiTest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.stereotype.Service;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

//@Service: 컨테이너에 bean을 등록하여 controller가 불러올 수 있도록 선언
@Service
public class PublicAPI {


    //메소드마다 기능은 꼭 하나씩만 하도록 규칙 SOLID
    public void testAPI(){
        String result = readAPI();

        //파싱받아서 가공하는 라이브러리 JAVA
        //1. SimpleJson: 대용량 데이터 처리 속도가 빠름
        //2. Jackson: 평균적으로 빠름
        //3. Gson: 간단한 데이터 처리 속도가 빠름

        //jacson: 빅데이터 등 큰 사이즈의 json 처리
        //gson: 분산 아키텍쳐 설정 등 작은용량의 json
        //simplejson: 골고루 빠름
        //json방식은 http 프로토콜을 통해서 데이터 전송 규약(클라이언트와 백엔드 통신)
        //백엔드와 백엔드 통신도 json방식 > grpc 통신의 등장으로 다수의 백엔드 통신은 grpc 변경.(앱통신도 grpc)

        //Gson tranfer (Gson 반환방식이므로 그대로 쓰면 된다.)
        Gson pretty = new GsonBuilder().setPrettyPrinting().create();
        String element = pretty.toJson(result);
        System.out.print(element);

        //String 문자열을 dto 객체로 변환
        //fromJson(문자열,DTO객체.class(런타임시점 객체))
        BusDTO busdto = pretty.fromJson(result, BusDTO.class);
        for(int i =0; i<busdto.getResponse().getBody().getNumOfRows(); i++) {
            System.out.println(busdto.getResponse().getBody().getItems().get(i).getCpname());
        }
    }

    public String readAPI() {
        //인증키
        String key = "%2Fk49W4UhNTuGlvyhZ6NCaHVhV1%2BBp0wbhWy0YjmvKgHQSFbVPwQqzw4ppSYg8O9ubHyLPYi8N%2F0e4yGvEQKGug%3D%3D";

        //데이터를 파싱받을 변수
        String brResult = "";
        //데이터를 받아와서 String 객체로 만들기 전에 한줄씩 더해야하므로 StringBuilder
        StringBuilder sb = new StringBuilder();

        //JSON API라는 것은 네트워크 통신을 통해 데이터를 다운받아 서비스 할 수 있도록 가공하기 위한 데이터
        //네트워크 통신이 끊기거나 예외적인 상황을 상정
        try {
            //http는 인증서가 필요하므로 http로 데이터 다운
            URL url = new URL("http://apis.data.go.kr/B551177/BusInformation/getBusInfo?serviceKey="
             + key + "&numOfRows=10&pageNo=1&area=1&type=json");
            //인증서가 필요한 객체
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));

            while ((brResult = br.readLine()) != null) {
                sb.append(brResult);
                System.out.println((brResult));}

            //container 있는 bean을 쓴 다음에 자원을 반환해줘야한다. con.disconnect();
            br.close();
            con.disconnect();

        }catch(Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}
