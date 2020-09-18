/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trinity.oauth.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author oiprado
 */
@Entity
@Table(name = "user_account")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserAccount implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @Column(name = "user_name")
    private String userName;
    @Basic(optional = false)
    @Column(name = "password")
    private String password;
    @Column(name = "create_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;
    @Column(name = "account_expired")
    private Boolean accountExpired;
    @Column(name = "locked")
    private Boolean locked;
    @Column(name = "credentials_expired")
    private Boolean credentialsExpired;
    @Column(name = "credentials_expired_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date credentialsExpiredDate;
    @Column(name = "enabled")
    private Boolean enabled;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "userAccountId")
    private UserInfo userInfo;
}
