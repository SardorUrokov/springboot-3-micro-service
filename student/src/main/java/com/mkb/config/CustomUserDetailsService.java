package com.mkb.config;

import lombok.RequiredArgsConstructor;
import java.util.Optional;
import com.mkb.entity.User;
import com.mkb.repository.UserRepository;
import org.springframework.stereotype.Component;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Component
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> credential = repository.findByEmail(username);
        return credential.map(CustomUserDetails::new)
                .orElseThrow(
                        () -> new UsernameNotFoundException("user not found with name :" + username)
                );
    }
}