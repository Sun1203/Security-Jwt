package com.example.loginlivesession2.security.user;

import com.example.loginlivesession2.account.entity.Member;
import com.example.loginlivesession2.account.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Member account = memberRepository.findByNickname(email).orElseThrow(
                () -> new RuntimeException("Not Found Member")
        );

        UserDetailsImpl userDetails = new UserDetailsImpl();
        userDetails.setMember(account);

        return userDetails;
    }
}
