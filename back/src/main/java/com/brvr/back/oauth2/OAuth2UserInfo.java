package com.brvr.back.oauth2;

import com.brvr.back.entity.User;
import com.brvr.back.enums.AuthProvider;
import com.brvr.back.enums.Role;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Map;

@Getter
@AllArgsConstructor
public abstract class OAuth2UserInfo {

    protected Map<String, Object> attributes;

    public abstract String getOAuth2Id();
    public abstract String getEmail();
    public abstract String getName();
}
