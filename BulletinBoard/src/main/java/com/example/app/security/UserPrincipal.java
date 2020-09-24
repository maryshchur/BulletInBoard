package com.example.app.security;

import com.example.app.entities.User;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Data
public class UserPrincipal implements UserDetails {
    private User user;
    //List<GrantedAuthority> authorities;
    public UserPrincipal(User user) {
        this.user = user;
//        authorities = Collections.
//                singletonList(new SimpleGrantedAuthority("ROLE_USER"));
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
                //authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
    public static UserPrincipal create(User user) {
        return new UserPrincipal(user);
    }
}
