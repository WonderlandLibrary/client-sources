package com.mojang.authlib.yggdrasil.request;

import com.mojang.authlib.yggdrasil.*;

public class ValidateRequest
{
    private String clientToken;
    private String accessToken;
    
    public ValidateRequest(final YggdrasilUserAuthentication authenticationService) {
        this.clientToken = authenticationService.getAuthenticationService().getClientToken();
        this.accessToken = authenticationService.getAuthenticatedToken();
    }
}
