package com.mojang.authlib.yggdrasil.request;

import com.mojang.authlib.yggdrasil.*;

public class InvalidateRequest
{
    private String accessToken;
    private String clientToken;
    
    public InvalidateRequest(final YggdrasilUserAuthentication authenticationService) {
        this.accessToken = authenticationService.getAuthenticatedToken();
        this.clientToken = authenticationService.getAuthenticationService().getClientToken();
    }
}
