/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trinity.oauth.repository;


import com.trinity.oauth.domain.UserAccount;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author oiprado
 */
@Repository
public interface UserAccountRepository extends PagingAndSortingRepository<UserAccount, Long>{
    
    UserAccount findOneByUserName(String userName);
    
}
