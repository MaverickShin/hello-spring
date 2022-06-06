package hello.hellospring;

import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import hello.hellospring.service.MemberService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// 자바코드로 Bean 등록 하기
@Configuration
public class springConfig {

    // DI에는필드주입, setter 주입, 생성자주입이렇게 3가지방법이있다. 
    // 의존관계가실행중에 동적으로변하는경우는거의없으므로 생성자 주입을권장한다.

    /**
     * 생성자 주입을 권장하는 이유
     *
     * 1. 수정자 주입 vs 생성자 주입
     *  수정자 주입은 프로퍼티마다 번거롭게 수정자 메소드를 추가해줘야 한다는 점과
     *  수정자 주입을 이용하면 필수적으로 DI 되어야 할 항목을 빼먹을 수 있다는 단점이 있다.
     *
     * 2. 생성자 주입 vs 필드 주입
     *  - DI 컨테이너와 결합이 매우 강하게 되어 외부에서 사용하기 어려워 진다 :
     *      필드 주입과 수정자 주입은 해당 필드를 final로 선언할 수 없다.
     *      수정자 주입이나 일반 메소드 주입을 이용하게 된다면 불필요하게 수정의 가능성을 열어두게 되는 것으로
     *      차후 변경이 되었을 때 발생하는 Side-Effect를 사전에 감지할 수 없다
     *  - 의존 관계가 보이지 않는다 :
     *      순환 참조하는 구조가 되었을 때 필드 주입으로 구현했다면,
     *      해당 참조가 실행돼 StackOverflowError가 발생하기 전까지는 알 수 없다.
     *      하지만 생성자 주입으로 구현되어있다면 어플리케이션이 구동될 때 에러가 발생하게 된다.
     *  - 테스트 코드 작성 :
     *     DI의 핵심은 관리되는 클래스가 DI 컨테이너에 의존성이 없어야 한다는 것이다.
     *     즉, 독립적으로 인스턴스화가 가능한 POJO(Plain Old Java Ojbect) 여야 한다는 것이다.
     *     DI 컨테이너를 사용하지 않고서도 단위 테스트에서 인스턴스화할 수 있어야 한다.
     *     생성자 주입을 하게 되면, Test Case 생성 시, 원하는 구현체를 넘겨주면 되고,
     *     구현체를 넘겨주지 않은 경우에는 객체생성 자체가 불가능하기 때문에 테스트하기도 편하다.
     */

    // MemberService 클래스에서 @Service가 없어도 됨
    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepository());
    }

    // MemberRepository 클래스에서 @Repository가 없어도 됨
    @Bean
    public MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }
}
