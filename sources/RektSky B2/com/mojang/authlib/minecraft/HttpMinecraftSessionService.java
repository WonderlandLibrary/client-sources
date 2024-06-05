package com.mojang.authlib.minecraft;

import com.mojang.authlib.*;

public abstract class HttpMinecraftSessionService extends BaseMinecraftSessionService
{
    protected HttpMinecraftSessionService(final HttpAuthenticationService authenticationService) {
        super(authenticationService);
    }
    
    @Override
    public HttpAuthenticationService getAuthenticationService() {
        return (HttpAuthenticationService)super.getAuthenticationService();
    }
}
