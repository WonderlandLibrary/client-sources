/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.authlib.minecraft;

import com.mojang.authlib.AuthenticationService;
import com.mojang.authlib.HttpAuthenticationService;
import com.mojang.authlib.minecraft.BaseMinecraftSessionService;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public abstract class HttpMinecraftSessionService
extends BaseMinecraftSessionService {
    protected HttpMinecraftSessionService(HttpAuthenticationService httpAuthenticationService) {
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

