package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    /**
     * 회원 가입
     * @param member
     * @return memberId
     */
    @Transactional
    public Long join(Member member) {
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    /**
     * 중복 회원 체크
     * @param member
     */
    private void validateDuplicateMember(Member member) {

        List<Member> findMembers = memberRepository.findByName(member.getName()); // 데이터베이스에 name을 유니크 제약조건을 설정 권장. 동시에 저장될 수 있는 가능성이 있음
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    /**
     * 전체 회원 조회
     * @return members
     */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    /**
     * 회원 조회
     * @param memberId
     * @return member
     */
    public Member findOne(Long memberId) {
        return memberRepository.findById(memberId).get();
    }

    /**
     * 회원 정보 변경
     */
    @Transactional
    public void update(Long id, String name) {
        Member member = memberRepository.findById(id).get();
        member.setName(name);
    }
}
