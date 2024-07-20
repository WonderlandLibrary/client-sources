/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.authlib;

import com.mojang.authlib.Agent;
import com.mojang.authlib.ProfileLookupCallback;

public interface GameProfileRepository {
    public void findProfilesByNames(String[] var1, Agent var2, ProfileLookupCallback var3);
}

