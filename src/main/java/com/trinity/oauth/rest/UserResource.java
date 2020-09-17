package com.trinity.oauth.rest;

import com.trinity.oauth.domain.UserAccount;
import com.trinity.oauth.repository.UserAccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
@Slf4j
public class UserResource {

    private final UserAccountRepository userRepository;
    
    @Value("${spring.profiles.active}")
    private String activeProfile;

    public UserResource(UserAccountRepository userRepository) {
        this.userRepository = userRepository;
    }

    @RequestMapping(value = "/info", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> user(Principal principal) {
        log.info(String.format("Active profile: %s", activeProfile));
        Map<String, Object> context = new LinkedHashMap<>();
        UserAccount user = userRepository.findOneByUserName(principal.getName());
        
        user.setPassword(null);
        context.put("profileName", String.format("%s", user.getUserName()));
        context.put("environment", activeProfile);
        context.put("roles", AuthorityUtils.authorityListToSet(((Authentication) principal).getAuthorities()));
        context.put("user", user);
        
        return context;
    }

}
