package me.spring_practice.service;

import me.spring_practice.domain.Member;
import me.spring_practice.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {
    // 테스트 클래스의 경우 직관적인 이해를 위해 메서드 이름을 한글로 적어도 상관없다.
    // 빌드될 때 테스트 코드는 실제 코드에 포함되지 않기 때문.

//    MemberService memberService = new MemberService();
//    MemoryMemberRepository memberRepository = new MemoryMemberRepository();
    // 위 코드는 MemberServiceTest 에서 테스트하는 repository 인스턴스와 MemberService 에서 사용하는 repository 인스턴스가 다르다는 문제점이 있다. (서로 다른 인스턴스)
    // 따라서 실제 사용하는 repository 와 테스트하는 repository 를 일치시키기 위해 DI (Dependency Injection)을 사용한다.
    // 즉, MemberService 클래스 내부에서 private final 으로 선언하여 사용하던 repository 를 DI를 통해 클래스간의 강한 연결을 분리하면서,
    // MemberService 클래스에서 repository 를 포함하지 않은, 독립적인 상태로 테스트를 할 수 있다.
    // **MemberService 에서 repository 에 접근하는 방법을 바꿔서, 실제 사용하는 외부 repository 를 주입할 수 있게 만든 것이다.**

    MemberService memberService;
    MemoryMemberRepository memberRepository;

    @BeforeEach
    public void beforeEach(){
        memberRepository = new MemoryMemberRepository();
        // 이 예제의 경우 단순히 새로운 인스턴스를 생성하는 방식으로 repository 의존성을 주입했지만,
        // 상용 프로젝트의 경우 실제 사용하는 repository 를 주입하면 테스트하는 repository 와 실제 사용하는 repository 를 일치시킬 수 있다.
        memberService = new MemberService(memberRepository);
    }

    @AfterEach
    public void afterEach(){
        memberRepository.clearStore();
    }
    @Test
    void 회원가입() {   // join 메서드의 정상 케이스를 테스트한다.
        // 테스트 작성 방법 ~ given, when, then 패턴
        // given : 주어지는 데이터
        // when : 검증되는 로직
        // then : 검증되는 결과값
        // 주석을 통해 코드를 구분한다. 대부분의 경우 유용하다. 상황에 따라 변형할 수 있다.


        //given
        Member member = new Member();
        member.setName("Walter");

        //when
        Long saveId = memberService.join(member);

        //then
        Member foundMember = memberService.findOne(saveId).get();
        assertThat(foundMember.getName()).isEqualTo(member.getName());


    }

    @Test
    public void 중복_회원_예외(){ // join 메서드의 예외 케이스를 테스트한다.
        //given
        Member member1 = new Member();
        member1.setName("Walter");

        Member member2 = new Member();
        member2.setName("Walter");

        //when
        memberService.join(member1);

//        // try-catch 문 :
//        // 예외(Exception)가 발생할 수 있는 코드를 안전하게 실행하고,
//        // 예외가 발생했을 때 적절히 처리할 수 있도록 돕는 문법.
//        // 프로그램이 예외 상황에서도 중단되지 않고 안정적으로 실행되도록 도와준다.
//        try {
//            memberService.join(member1);
//            fail();
//        } catch (IllegalStateException e) {
//            assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
//        }


        // assertThrows : try-catch 문을 대신하여 예외 케이스를 편리하게 테스트할 수 있는 메서드 (junit)
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));

        //then
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
    }

    @Test
    void findAllMember() {
    }

    @Test
    void findOne() {
    }
}