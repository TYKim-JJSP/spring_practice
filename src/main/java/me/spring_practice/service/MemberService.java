package me.spring_practice.service;

import me.spring_practice.domain.Member;
import me.spring_practice.repository.MemberRepository;
import me.spring_practice.repository.MemoryMemberRepository;

import java.util.List;
import java.util.Optional;

public class MemberService {

    // private final MemberRepository memberRepository = new MemoryMemberRepository();

    // 의존성 주입 Dependency Injection
    private final MemberRepository memberRepository;
    public MemberService(MemberRepository memberRepository){
        this.memberRepository = memberRepository;
    }
    // 회원 가입
    public Long join (Member member){
        // 같은 이름을 가진 중복회원(동명이인)은 가입 불가 (임의의 로직임)
//        Optional<Member> result = memberRepository.findByName(member.getName());
//        result.ifPresent(m -> {
//            throw new IllegalStateException("이미 존재하는 회원입니다.");
//        });

        // 위의 코드를 깔끔하게 리팩터링한 코드
        // memberRepository.findByName() 의 반환값을 변수에 저장하지 않고 그대로 사용.
        // 메서드로 추출함.
        validateDuplicateMember(member);    // 이름이 중복된 회원 검증

        memberRepository.save(member);
        return member.getId();
    }
    // 이름이 중복된 회원 검증 메서드
    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName())
                        .ifPresent( m -> {
                            throw new IllegalStateException("이미 존재하는 회원입니다.");
                        });
    }

    // 전체 회원 조회 메서드
    public List<Member> findAllMember(){
        return memberRepository.findAll();
    }

    // id를 통한 회원 조회 메서드
    public Optional<Member> findOne(Long memberId){
        return memberRepository.findById(memberId);
    }

}
