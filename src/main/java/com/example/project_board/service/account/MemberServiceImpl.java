package com.example.project_board.service.account;

import com.example.project_board.entity.account.Member;
import com.example.project_board.repository.account.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberServiceImpl implements MemberService{
    //서비스와 레파지토리와 연결됨
//    private final MemberRepository memberRepo;
    //이거랑
    @Autowired
    private MemberRepository memberRepo;
//    protected MemberServiceImpl(MemberRepository memberRepo) {
//        this.memberRepo = memberRepo;
//    }   //이거 언제한거????

    //22.08.16
    //public : 공개
    //LIST<Member> : 리턴 타입은 List 속성은 Member
    //return memberRepository 의 findMemberByEmailOrId 메서드를 실행한 리턴
    @Override
    public Member getMemberWhereIdOrEmail(String Email, String Id) {
        return memberRepo.findMembersByEmailOrId(Email, Id);
    }
    @Override
    public Member getMemberWhereIdAndROWNUL1(String id){
        return memberRepo.findFirstById(id);
    }
    //모든 회원의 정보를 가져다 오는 것
    //raturn List<Member> : 모든 회원의 정보를 List배열에 담아서 return
    //public : 모두 공개하는 메서드
    //List<Member> : 이 메서드가 실행되면 return되는 타입
    //(List<Member>) : 뒤에 있는 결과값을 형변환
    //memberRepo : @Autowired MemberRepository를 통해 기능 실행
    //findAll : memberRepo에 있는 모든 정보 가져오기 메서드 실행
    @Override
    public List<Member> getMemberList() {
        return (List<Member>) memberRepo.findAll();
    }   //List<Member>로 형변환

    //회원 1명의 정보를 Entity에 맞춰서 DB에 저장
    @Override
    public void insertMember(Member member) {
        memberRepo.save(member);
    }

    //2022.08.12
    @Override
    public Member getMember(Member member) {
//        return null;
        return memberRepo.findById(member.getSeq()).get();
        //멤버 seq를 찾고 큐플을 읽어온다.
    }

    @Override
    public void updateMember(Member member) {
        //1. seq를 통해서 튜플 정보를 모두 가져오기
        //2. 가져온 튜플 정보 중 수정할 내용 적용
        //3. DB에 저장(덮어쓰기)
        //findBYId(), get() : 고유키 기준으로 튜플 전체 데이터 가져오기
        Member findMember = memberRepo.findById(member.getSeq()).get();
        //튜플 전체 내용중에 ID/email주소 수정 (setter)
        findMember.setId(member.getId());
//        findMember.setPassword(member.getPassword());
        findMember.setEmail(member.getEmail());
        //crudRepo의 save 메서드를 통해 데이터 저장
        memberRepo.save(findMember);

        //고유키
        //1. 튜플을 식별할 수 있는 값 (데이터 한 줄)
        //2. 다른 테이블의 튜플과 연동하기 위한 값 (JOIN, 외래키)
        //3. 객체 지향 방법으로 DB를 저장
        //3-1. 영속성 : DB에 영구 저장
        //3-2. 고립성 : 다른 트랜젝션 작업에 연관되지 않도록 해주는 것
        //             관리자1은 seq 10의 회원 정보가 바꿨습니다. 이미 접속해 있던 관리자2가 seq 10 회원의 정보를 조회합니다.
        //             seq10의 회원정보를 바꾸는 작업이 한 개의 트랜잭션. 관리자2의 seq10회원의 정보를 조회하고 수정하는 작업도 한개의 트랜잭션.
        //             관리자1의 트랜잭션 작업이 완료될 때까지 관리자2의 seq10 회원정보는 옛날 정보를 조회하고 있고,
        //             관리자1의 트랜잭션 작업이 완료되는 순간까지 관리자2는 seq10 회원정보를 수정할 수 없다.
        //          ★ 다른 필드값은 수정이 가능해도, seq는 튜플의 식별자로써 수정이 불가해야, 관리자 1, 2의 트랜잭션 작업을 스프링부트에서 구분할 수 없다.
    }

    @Override
    public void deleteMember(Member member) {
        memberRepo.deleteById(member.getSeq());
    }
}
