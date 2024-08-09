/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.authlib.legacy;

import com.mojang.authlib.Agent;
import com.mojang.authlib.GameProfileRepository;
import com.mojang.authlib.HttpAuthenticationService;
import com.mojang.authlib.UserAuthentication;
import com.mojang.authlib.legacy.LegacyMinecraftSessionService;
import com.mojang.authlib.legacy.LegacyUserAuthentication;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import java.net.Proxy;
import org.apache.commons.lang3.Validate;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
@Deprecated
public class LegacyAuthenticationService
extends HttpAuthenticationService {
    protected LegacyAuthenticationService(Proxy proxy) {
        super(proxy);
    }

    @Override
    public LegacyUserAuthentication createUserAuthentication(Agent agent) {
        Validate.notNull(agent);
        if (agent != Agent.MINECRAFT) {
            throw new IllegalArgumentException("Legacy authentication cannot handle anything but Minecraft");
        }
        return new LegacyUserAuthentication(this);
    }

    @Override
    public LegacyMinecraftSessionService createMinecraftSessionService() {
        return new LegacyMinecraftSessionService(this);
    }

    @Override
    public GameProfileRepository createProfileRepository() {
        throw new UnsupportedOperationException("Legacy authentication service has no profile repository");
    }

    @Override
    public MinecraftSessionService createMinecraftSessionService() {
        return this.createMinecraftSessionService();
    }

    @Override
    public UserAuthentication createUserAuthentication(Agent agent) {
        return this.createUserAuthentication(agent);
    }
}

