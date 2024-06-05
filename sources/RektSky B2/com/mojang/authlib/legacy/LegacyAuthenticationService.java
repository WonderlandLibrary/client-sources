package com.mojang.authlib.legacy;

import java.net.*;
import org.apache.commons.lang3.*;
import com.mojang.authlib.minecraft.*;
import com.mojang.authlib.*;

public class LegacyAuthenticationService extends HttpAuthenticationService
{
    protected LegacyAuthenticationService(final Proxy proxy) {
        super(proxy);
    }
    
    @Override
    public LegacyUserAuthentication createUserAuthentication(final Agent agent) {
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
}
