/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.authlib;

import com.mojang.authlib.Agent;
import com.mojang.authlib.GameProfileRepository;
import com.mojang.authlib.UserAuthentication;
import com.mojang.authlib.minecraft.MinecraftSessionService;

public interface AuthenticationService {
    public UserAuthentication createUserAuthentication(Agent var1);

    public MinecraftSessionService createMinecraftSessionService();

    public GameProfileRepository createProfileRepository();
}

