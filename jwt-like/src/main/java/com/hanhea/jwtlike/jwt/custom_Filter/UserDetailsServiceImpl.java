package com.hanhea.jwtlike.jwt.custom_Filter;

import com.hanhea.jwtlike.account.entity.Account;
import com.hanhea.jwtlike.account.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        Account account = accountRepository.findByNickname(username).orElseThrow(
                () -> new RuntimeException("유저정보가 없습니다."));
        UserDetailsImpl userDetails = new UserDetailsImpl();
        userDetails.setAccount(account);
        return userDetails;
    }
}
