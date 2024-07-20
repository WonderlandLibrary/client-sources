/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.authlib;

import com.mojang.authlib.BaseUserAuthentication;
import com.mojang.authlib.HttpAuthenticationService;

public abstract class HttpUserAuthentication
extends BaseUserAuthentication {
    protected HttpUserAuthentication(HttpAuthenticationService authenticationService) {
        super(authenticationService);
    }

    @Override
    public HttpAuthenticationService getAuthenticationService() {
        return (HttpAuthenticationService)super.getAuthenticationService();
    }
}

