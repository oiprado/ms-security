/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trinity.oauth.service.impl;


//import com.it270.oauth.domain.Authority;
import com.trinity.commons.security.payload.Profile;
import com.trinity.oauth.domain.UserAccount;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.trinity.oauth.repository.UserAccountRepository;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
    
    @Autowired
    private UserAccountRepository userRepository;
    @Value("${spring.profiles.active}")
    private String environment;

    @Override
    public UserDetails loadUserByUsername(String userName) {
        
        UserAccount user = userRepository.findOneByUserName(userName);
        
        if (user == null) {
            throw new UsernameNotFoundException(String.format("User %s does not exist!", userName));
        }
        
        return new UserRepositoryUserDetails(user, null, environment);
    }

    private final static class UserRepositoryUserDetails implements UserDetails {

        private static final long serialVersionUID = 1L;
        
        protected Collection<SimpleGrantedAuthority> authorities;
        protected String environment;
        protected UserAccount user; 
        
        private UserRepositoryUserDetails(UserAccount user, Collection<SimpleGrantedAuthority> roles, String environment) {
            this.authorities = roles;
            this.user = user;
            this.environment = environment;
            
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {            
            return authorities;
        }

        @Override
        public String getUsername() {
            return this.user.getUserName();
        }

        @Override
        public boolean isAccountNonExpired() {
            return user.getAccountExpired();
        }

        @Override
        public boolean isAccountNonLocked() {
            return user.getLocked();
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return user.getCredentialsExpired();
        }

        @Override
        public boolean isEnabled() {
            return user.getEnabled();
        }

        @Override
        public String getPassword() {
            return null;
        }

        @Override
        public String toString() {
            return String.format("[userName] = %s", getUsername());
        }

    }

}
