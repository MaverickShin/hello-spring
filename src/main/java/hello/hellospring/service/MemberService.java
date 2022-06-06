package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// @Service
// Test class 빠르게 만들기 Ctrl + Shift + T
public class MemberService {

    // private final MemberRepository memberRepository = new memberRepository();
    private final MemberRepository memberRepository;

    // 인스턴스를 외부에서 생성하도록 변경
    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }



    /**
     * 회원가입
    */
    public Long join(Member member) {

        /*
            같은 이름이 있는 중복 회원x
            Ctrl + Alt + v : return을 자동으로 해준다. (아래처럼 Optional<Member> 자동생성)
        Optional<Member> result = memberRepository.findByName(member.getName());

         if문과 같다, Optional를 사용하면 아래 함수를 사용할 수 있다.
         ifPresent() : 값이 있을때 실행됨
        result.ifPresent(m -> { // m ->
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        });
        */

        /*
          위 코드를 간결하게
          어차피 memberRepository.findByName()의 return 값은 Optional<Member>이기 때문에
          바로 ifPresent()를 사용할 수 있다.
        memberRepository.findByName(member.getName())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });

          중복확인 메소드가 꾀나 긴 편이라 따로 메소드로 빼주려면
          코드를 드래그 하여 Ctrl + Alt + Shift + T 를 눌러 리펙토링 할 수 있고,
          바로 메소드로 추출하고 싶으면 Alt + Shift + M 을 누르면 된다.
        */
        
        // 중복확인 메소드
        validateDuplicateMember(member);
        
        memberRepository.save(member);
        return member.getId();
    }

    // 중복확인 메소드
    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName())
                        .ifPresent(m -> {
                            throw new IllegalStateException("이미 존재하는 회원입니다.");
                        });
    }

    /**
     * 전체회원 조회
     */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    /**
     * 아이디 찾기
     */
    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }
}
