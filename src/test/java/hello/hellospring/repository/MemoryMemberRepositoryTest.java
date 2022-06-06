package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class MemoryMemberRepositoryTest {

    MemoryMemberRepository repository = new MemoryMemberRepository();

    @AfterEach // 메소드가 끝날때마다 실행하는 콜백 메소드이다.
    public void afterEach(){
        // findByName, findAll 메소드 실행 시 각 메소드에서 데이터를 저장했기 때문에
        // 메소드 실행되고 실행 후에 데이터(메모리)를 비워주어야 한다.
        repository.clearAll();
    }

    @Test
    public void save(){
        Member member = new Member();
        member.setName("spring");

        repository.save(member);

        // Optional에서 값을 꺼낼 땐 get으로 꺼내야 한다.
        Member result = repository.findById(member.getId()).get();

        // Spring Assert

        // Assertions.assertEquals(member, result); // 둘이 똑같은지 확인해 볼 수 있다.

        // Assertions를 static import하여 assertThat()을 바로 사용할 수 있다.
        assertThat(member).isEqualTo(result); // 마찬가지로 둘이 똑같은지 확인해 볼 수 있다.
    }

    @Test
    public void findByName(){
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        // Member result = repository.findByName("spring1").get();
        Member result = repository.findByName("spring2").get();

        // assertThat(result).isEqualTo(member1);
        assertThat(result).isEqualTo(member2);
    }

    @Test
    public void findAll() {
        // Shift + F6 을 누르면 변수명이 동시에 바뀜
        
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        List<Member> result = repository.findAll();

        assertThat(result.size()).isEqualTo(2);

         /*
             여기까지 테스트 쭉 돌렸는데, 갑자기 findByName이 에러가 떴다. 왜일까?
             순서대로 실행되는 것이 아니라, Method 단위로만 동작하기 때문에 순서는 상관이 없다
             고로, 순서에 의존적으로 설계하면 절대 안 된다.

             빌드 history(?)를 보면, findAll이 먼저 실행이 되어버렸다.
             그래서 이미 setName spring1, spring2가 저장이 되어버렸다.
             그러므로, test가 하나 끝나고나면 데이터를 Clear 해주어야 한다. => afterEach() & clearStore()

             1. MemberRepository 개발이 끝난 후 > 테스트 작성
             2. 이것을 뒤집어서 테스트 > 구현 클래스 개발 순서로 진행해도 된다. (테스트 주도 개발 : TDD )

             테스트가 엄청 많을 경우, 폴더의 우클릭 > Run 'Tests in '(foldername)''으로 자동으로 돌린다.

             테스트코드가 없이 개발하는 것은, 몇 만라인 이상일 경우는 절대 불가능하다. 협업할 때에는 특히 더 필요하다.
         */
    }

}
