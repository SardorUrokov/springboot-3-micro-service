package com.mkb.school.config;

import com.mkb.school.entity.User;
import com.mkb.school.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository repository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> credential = repository.findByEmail(username);
        return credential.map(CustomUserDetails::new)
                .orElseThrow(() ->
                        new UsernameNotFoundException("user not found with email :" + username)
                );
    }
}