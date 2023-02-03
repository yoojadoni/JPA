package com.example.demo.service.member;

import com.example.demo.common.StatusCodeEnum;
import com.example.demo.configure.exception.CustomException;
import com.example.demo.entity.Member;
import com.example.demo.entity.owner.Owner;
import com.example.demo.repository.MemberRepository;
import com.example.demo.repository.owner.OwnerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
public class MemberService {

    @Autowired
    MemberRepository memberRepository;

    public Optional<Member> findById(Long id){
        return memberRepository.findById(id);
    }

    public Optional<Member> findByEmail(String email){
        return memberRepository.findByEmail(email);
    }

    public Member saveMember(Member member){
        return memberRepository.save(member);
    }

    @Transactional
    public Member updateMemberById(Member member) throws Exception{
        try{
            Member findMemeber = memberRepository.findById(member.getId()).orElseThrow(() -> new CustomException(StatusCodeEnum.NO_DATA));
            findMemeber.changeMember(member);
        }catch (Exception e){
            throw new Exception();
        }
        return member;
    }

}
