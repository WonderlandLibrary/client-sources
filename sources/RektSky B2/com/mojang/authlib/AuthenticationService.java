package com.mojang.authlib;

import com.mojang.authlib.minecraft.*;

public interface AuthenticationService
{
    UserAuthentication createUserAuthentication(final Agent p0);
    
    MinecraftSessionService createMinecraftSessionService();
    
    GameProfileRepository createProfileRepository();
}
