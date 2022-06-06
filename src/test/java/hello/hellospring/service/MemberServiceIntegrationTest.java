package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

// 통합테스트
@SpringBootTest // 스프링 컨테이너와 테스트를 함께 실행한다
@Transactional // Test Case를 위해 씀, DB에서 테스트에 사용했던 데이터들을 Rollback 해줌
        // 테스트 케이스에 이 애노테이션이 있으면, 테스트 시작 전에 트랜잭션을 시작하고 테스트 완료 후에 항상 롤백한다.
        // 이렇게 하면 DB에 데이터가 남지 않으므로 다음 테스트에 영향을 주지 않는다.
class MemberServiceIntegrationTest {

    // 테스트할때는 필드 주입해도 됨
    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

    // 테스트는 이름을 과감하게 한글로 해도됨
    @Test
    void 회원가입() {
        // given
        Member member = new Member();
        member.setName("hello");

        // when
        Long saveId = memberService.join(member);

        // then
        Member findMember = memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());
    }

    @Test
    public void 중복_회원_예외() {
        // given
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");

        // when
        memberService.join(member1);
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");

        /*
        // 일반적인 예외 처리
        try {
            memberService.join(member2);
            fail();

            // fail() : 단정 메소드
            // fail 메소드가 필요한 상황 : 예외 처리 시험
        } catch (IllegalStateException e) { // IllegalStateException 예외 : 메소드가 요구된 처리를 하기에 적합한 상태에 있지 않을때
            assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
        }
        */

        // then
    }
}