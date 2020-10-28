/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trinity.oauth.service.impl;

import com.trinity.oauth.domain.UserAccount;
import com.trinity.oauth.repository.UserAccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;

@Service
@Slf4j
public class UserDetailServiceImpl implements UserDetailsService, Serializable {

    private UserAccountRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String userName) {
        
        UserAccount user = userRepository.findOneByUserName(userName);
        
        if (user == null) {
            throw new UsernameNotFoundException(String.format("User %s does not exist!", userName));
        }
        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(
                "ROLE_ADMIN"
        );

        return new UserRepositoryUserDetails(user, Arrays.asList(simpleGrantedAuthority));
    }

    private  class UserRepositoryUserDetails implements UserDetails, Serializable {

        private static final long serialVersionUID = 1L;
        
        private Collection<SimpleGrantedAuthority> authorities;
        private UserAccount user;
        
        private UserRepositoryUserDetails(UserAccount user, Collection<SimpleGrantedAuthority> roles) {
            this.authorities = roles;
            this.user = user;
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
            return !user.getAccountExpired();
        }

        @Override
        public boolean isAccountNonLocked() {
            return !user.getLocked();
        }


        @Override
        public boolean isCredentialsNonExpired() {
            return !user.getCredentialsExpired();
        }


        @Override
        public boolean isEnabled() {
            return user.getEnabled();
        }

        @Override
        public String getPassword() {
            return user.getPassword();
        }

        @Override
        public String toString() {
            return String.format("[userName] = %s", getUsername());
        }

    }

    @Autowired
    public void setUserRepository(UserAccountRepository userRepository) {
        this.userRepository = userRepository;
    }
}
