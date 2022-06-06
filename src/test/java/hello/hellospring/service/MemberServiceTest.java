package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {

    // MemberService memberService = new MemberService();
    MemberService memberService;
    // MemoryMemberRepository memoryMemberRepository = new MemoryMemberRepository(); // 메모리를 비워주기위해 인스턴스 생성
    MemoryMemberRepository memoryMemberRepository;
    // store(MemoryMemberRepository의 데이터가 담긴 Map)가 static이라 인스턴스가 달라도 되지만
    // static이 아니라면 인스턴스가 다르기 때문에 전혀 다른 데이터가 나온다.
    // 그렇기 때문에 MemberService에서 MemoryMemberRepository의 인스턴스를 직접 생성하지 말고
    // 외부에서 인스턴스를 받도록 변경하고, Test 클래스에서는 BeforeEach를 통해 메소드 실행전 인스턴스를 생성하도록 한다.
    // 이렇게 하면 모든 MemoryMemberRepository의 인스턴스는 같은 인스턴스를 사용하게 된다.

    @BeforeEach
    public void beforeEach(){
        memoryMemberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memoryMemberRepository);
    }

    @AfterEach
    public void afterEach() {
        memoryMemberRepository.clearAll();
    }


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

    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}