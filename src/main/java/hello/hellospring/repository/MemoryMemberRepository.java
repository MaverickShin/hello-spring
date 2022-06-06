package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class MemoryMemberRepository implements MemberRepository {

    private static Map<Long, Member> store = new HashMap<>();
    private static long sequence = 0L;

    @Override
    public Member save(Member member) {
        member.setId(++sequence); // member를 저장할 때 시퀀스값을 증가 시킴, 증가된 시퀀스를 아이디로 사용
        store.put(member.getId(), member); // store에 member를 저장
        return member;
    }

    @Override
    public Optional<Member> findById(long id) {
        return Optional.ofNullable(store.get(id)); // null이 반환될 가능성이 있으면 optional 사용
    }

    @Override
    public Optional<Member> findByName(String name) {
        // stream api : 컬렉션, 배열안에 있는 요소들을 하나씩 참조하며 반복적인 처리를 할 수 있는것
        return store.values().stream() // map의 values를 하나씩 참조하며 반복
                // filter로 가공 member라는 객체에서 getName()이름을 가져온 후, name와 동일헌것을 탐색
                .filter(m -> m.getName().equals(name))
                .findAny(); // 어떤것이든 찾음 혹은 sorted() 정렬, count() 개수를 반환을 써서 원하는 결과를 가져온다.
                // 여기서는 위 equals()에서 찾은 값을 반환
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
        // store에 있는 모든 데이터를 반환
    }

    public void clearAll() {
        store.clear();
    }
}
