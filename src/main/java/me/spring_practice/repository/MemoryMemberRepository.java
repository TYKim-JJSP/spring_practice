package me.spring_practice.repository;

import me.spring_practice.domain.Member;

import java.util.*;

public class MemoryMemberRepository implements MemberRepository{

    private static Map<Long, Member> store = new HashMap<>();
    private static long sequence = 0L;

    @Override
    public Member save(Member member) {
        member.setId(++sequence);
        store.put(member.getId(), member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(store.get(id));  //null 이 반환될 가능성이 있는 값은 Optional 로 감싸서 반환한다.
    }

    @Override
    public Optional<Member> findByName(String name) {
        // steam 을 사용한 filter 메소드에 람다식을 사용하여 name 문자열과 일치하는 이름을 가진 회원객체를 반환한다.
        return store.values().stream()
                .filter(member -> member.getName().equals(name))
                .findAny();
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }

    public void clearStore() {
        store.clear();
    }
}
