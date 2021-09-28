package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest // 스프링 컨테이너 안에서 테스트를 수행한다. 없으면 @Autowired  실패한다.
@Transactional // 트랜잭션 수행 후 롤백함
class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;
    @Autowired EntityManager em;

    @Test
//    @Rollback(false)
    public void join() throws Exception {
        // given
        Member member = new Member();
        member.setName("kim");
        // when
        Long savedId = memberService.join(member);

        // then
//        em.flush();
        assertThat(member).isEqualTo(memberRepository.findById(savedId).get());
    }

    @Test
    public void duplicateMemberJoin() throws Exception {
        // given
        Member member1 = new Member();
        member1.setName("kim");

        Member member2 = new Member();
        member2.setName("kim");

        // when
        memberService.join(member1);
        try {
            memberService.join(member2);
        } catch (IllegalStateException e) {
            return;
        }

        // then
        fail("예외가 발생해야 한다.");

        /*
        assertThrows(IllegalStateException.class, () -> {
            memberService.join(member1);
            memberService.join(member2);
        });
        */
    }
}