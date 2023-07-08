package com.brvr.back.entity;

import com.brvr.back.enums.AuthProvider;
import com.brvr.back.enums.Role;
import com.brvr.back.oauth2.OAuth2UserInfo;

import lombok.*;

import javax.persistence.*;
import java.security.Provider;

@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class User extends BaseDateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String name;
    
    private String profile;
    
    private String oauth2Id;

    @Enumerated(EnumType.STRING)
    private AuthProvider authProvider;

    @Enumerated(EnumType.STRING)
    private Role role;

    public User update(OAuth2UserInfo oAuth2UserInfo) {
        this.email = oAuth2UserInfo.getEmail();
        this.oauth2Id = oAuth2UserInfo.getOAuth2Id();

        return this;
    }
}
