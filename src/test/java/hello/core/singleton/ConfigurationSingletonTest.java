package hello.core.singleton;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.MemberRepository;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class ConfigurationSingletonTest {

    @Test
    void configurationTest(){
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfigTest.class);
        MemberServiceImpl memberService = ac.getBean("memberService", MemberServiceImpl.class);
        OrderServiceImpl orderService = ac.getBean("orderService", OrderServiceImpl.class);
        MemberRepository memberRepository = ac.getBean("memberRepository", MemberRepository.class);
    }

    @Configuration
    static class AppConfigTest {
        @Bean
        public MemberRepository memberRepository(){
            System.out.println("call AppConfigTest.memberRepository");
            return new MemoryMemberRepository();
        }
        @Bean
        public MemberService memberService(){
            System.out.println("call AppConfigTest.memberService");
            return new MemberServiceImpl(memberRepository());
        }
        @Bean
        public DiscountPolicy discountPolicy(){
            return new RateDiscountPolicy();
        }
        @Bean
        public OrderService orderService(){
            System.out.println("call AppConfigTest.orderService");
            return new OrderServiceImpl(memberRepository(), discountPolicy());
        }
    }

}
