package com.example.demo.configure;

import com.example.demo.entity.Member;
import com.example.demo.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException{
        Member user = Member.builder()
                .email(email)
                .build();
        try {
            user = memberRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("가입된 사용자가 아닙니다."));
        } catch (UsernameNotFoundException e){
            throw  new UsernameNotFoundException(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        Set<GrantedAuthority> grantedAuthoritySet = new HashSet<>();

        return new org
                .springframework
                .security
                .core
                .userdetails
                .User(user.getEmail(), "", grantedAuthoritySet);
    }
}
