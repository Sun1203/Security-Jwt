package com.hanhea.jwtlike.account.repository;

import com.hanhea.jwtlike.account.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findAccountByNickname(String nickname);
}
