/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trinity.oauth.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

/**
 *
 * @author oiprado
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfigurer extends ResourceServerConfigurerAdapter {

    private static final String RESOURCE_ID = "resource-server-rest-api";
    private static final String SECURED_PATTERN = "/api/**";
    public static final String SECURED_READ_SCOPE = "#oauth2.hasScope('read')";
    public static final String SECURED_WRITE_SCOPE = "#oauth2.hasScope('write')";

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.resourceId(RESOURCE_ID);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
            .logout().clearAuthentication(true)
            .and()
                .csrf().disable()
            .authorizeRequests()
            .antMatchers("/oauth/token").permitAll()
            .and()
                .authorizeRequests().antMatchers(SECURED_PATTERN).authenticated();
    }
}
