/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.authlib.minecraft;

import com.mojang.authlib.HttpAuthenticationService;
import com.mojang.authlib.minecraft.BaseMinecraftSessionService;

public abstract class HttpMinecraftSessionService
extends BaseMinecraftSessionService {
    protected HttpMinecraftSessionService(HttpAuthenticationService authenticationService) {
        super(authenticationService);
    }

    @Override
    public HttpAuthenticationService getAuthenticationService() {
        return (HttpAuthenticationService)super.getAuthenticationService();
    }
}

