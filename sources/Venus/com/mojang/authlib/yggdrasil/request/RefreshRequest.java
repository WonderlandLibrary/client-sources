/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.authlib.yggdrasil.request;

import com.mojang.authlib.GameProfile;

public class RefreshRequest {
    private String clientToken;
    private String accessToken;
    private GameProfile selectedProfile;
    private boolean requestUser = true;

    public RefreshRequest(String string, String string2) {
        this(string, string2, null);
    }

    public RefreshRequest(String string, String string2, GameProfile gameProfile) {
        this.clientToken = string2;
        this.accessToken = string;
        this.selectedProfile = gameProfile;
    }
}

