/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.authlib;

import com.mojang.authlib.GameProfile;

public interface ProfileLookupCallback {
    public void onProfileLookupSucceeded(GameProfile var1);

    public void onProfileLookupFailed(GameProfile var1, Exception var2);
}

