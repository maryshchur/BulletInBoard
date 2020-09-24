package com.example.app.security;

import com.example.app.entities.User;
import com.example.app.exceptions.NotFoundException;
import com.example.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserPrincipalDetailService  implements UserDetailsService {
    private UserRepository userRepository;

    @Autowired
    public UserPrincipalDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByEmail(username).orElseThrow(
                () -> new NotFoundException("User does not exist"));
        return UserPrincipal.create(user);
    }
}
