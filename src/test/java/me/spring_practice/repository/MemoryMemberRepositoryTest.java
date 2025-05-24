package me.spring_practice.repository;

import me.spring_practice.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;


class MemoryMemberRepositoryTest {
    MemoryMemberRepository repository = new MemoryMemberRepository();

    //JUnit 기초
    //테스트를 설계할 때는 메소드의 실행 순서와 상관없이 모든 메소드가 따로 동작하도록 설계해야 한다.
    //따라서 테스트가 하나 끝나면 공용 데이터를 클리어해줘야 한다.

    //AfterEach 어노테이션 : 메소드 테스트가 끝날때마다 실행하는 동작을 정의한다. 일종의 콜백메소드.
    @AfterEach
    public void afterEach() {
        repository.clearStore();
    }

    //Test 어노테이션 : 테스트할 메소드를 junit 으로 실행할 수 있게 해준다.
    @Test
    public void save() {
        Member member = new Member();
        member.setName("spring");

        repository.save(member);
        // get : Optional 에서 값을 꺼내는 가장 빠른 방법. 빠른 테스트 진행을 위해 사용함. 하지만 실무에선 다른 방법을 주로 쓰는 듯.
        Member result = repository.findById(member.getId()).get();

        //Assertions 클래스(junit 패키지)의 assertEquals 정적메소드 : 기대값과 실제값을 비교하여 검증한다.
        //Assertions.assertEquals(member, result);

        //Assertions 클래스(assertj 패키지)의 assertThat 정적메소드를 사용하는 방법
        //Assertions.assertThat(member).isEqualTo(result);

        //Assertions 를 static import 하면 assetThat 만 써서 사용할 수 있어 편리하다. (실제 자주 사용)
        assertThat(member).isEqualTo(result);

        //실무에서는 위 기능을 빌드툴에 엮어서 빌드할 때 테스트케이스를 통과하지 못하면 다음 단계로 못 넘어가게 막는다.

    }

    @Test
    public void findByName(){
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        Member result = repository.findByName("spring1").get();

        assertThat(result).isEqualTo(member1);

    }

    @Test
    public void findAll(){
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        List<Member> result = repository.findAll();

        assertThat(result.size()).isEqualTo(2);
    }
}
