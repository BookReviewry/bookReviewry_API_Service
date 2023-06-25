package com.brvr.back.oauth2;

import java.util.Map;

import com.brvr.back.enums.AuthProvider;

public class OAuth2UserInfoFactory {

    public static OAuth2UserInfo getOAuth2UserInfo(AuthProvider authProvider, Map<String, Object> attributes) {
        switch (authProvider) {
            case GOOGLE: return new GoogleOAuth2User(attributes);
            default: throw new IllegalArgumentException("Invalid Provider Type.");
        }
    }
}
