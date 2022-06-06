package hello.hellospring.controller;

import hello.hellospring.domain.Member;
import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

// Spring Bean 등록 시 유의사항 : 현재 프로젝트 기준 hellospring 디렉토리의 하위 디렉토리만 가능하다.
// 자바코드로 직접 Bean 등록하는 방법 : springConfig 클래스 확인
@Controller
public class MemberController {

    // private final MemberService memberService = new MemberService();

    // MemberService, MemoryMemberRepository와 같이 인스턴스를 재생성하지 않고 하나의 인스턴스를 사용하려면
    // 먼저, Spring Bean에 등록해주어야 한다 (@Controller, @Service, @Repository)
    // 이렇게 등록된 Bean은 @Autowired를 통해 찾을 수 있고,
    // 그렇게 찾은 Bean을 주입하는 과정을 DI(Dependency Injection) 의존관계 주입이라고 한다.
    // 그리고 위 과정을 보면 개발자가 직접 new 생성자를 통해 인스턴스를 생성하거나, Bean을 컨트롤 하지 않고
    // Spring이 이 모든과정을 대신해준다. 이를 IOC(Inversion of Control) 제어의 역전이라고 말한다.

    // 스프링 빈(Spring Bean)이란? Spring IoC 컨테이너가 관리하는 자바 객체를 빈(Bean)이라 부른다.

    /**
     *     스프링에서는 다음과 같은 순서로 객체가 만들어지고 실행된다.
     *     1.객체 생성
     *
     *     2. 의존성 객체 주입
     *     스스로가 만드는것이 아니라 제어권을 스프링에게 위임하여 스프링이 만들어놓은 객체를 주입한다.
     *
     *     3.의존성 객체 메소드 호출
     *
     *     스프링이 모든 의존성 객체를 스프링이 실행될때 다 만들어주고 필요한곳에 주입시켜줌으로써 Bean들은 싱글턴 패턴의 특징을 가지며,
     *
     *     제어의 흐름을 사용자가 컨트롤 하는 것이 아니라 스프링에게 맡겨 작업을 처리하게 된다.
     */
    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/members/new")
    public String createForm() {
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String create(MemberForm form) {
        Member member = new Member();
        member.setName(form.getName());

        memberService.join(member);

        return "redirect:/";
    }

    @GetMapping("/members")
    public String list(Model model) {
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/memberList";
    }
}
