/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.authlib;

import com.mojang.authlib.AuthenticationService;
import com.mojang.authlib.BaseUserAuthentication;
import com.mojang.authlib.HttpAuthenticationService;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public abstract class HttpUserAuthentication
extends BaseUserAuthentication {
    protected HttpUserAuthentication(HttpAuthenticationService httpAuthenticationService) {
        super(httpAuthenticationService);
    }

    @Override
    public HttpAuthenticationService getAuthenticationService() {
        return (HttpAuthenticationService)super.getAuthenticationService();
    }

    @Override
    public AuthenticationService getAuthenticationService() {
        return this.getAuthenticationService();
    }
}

