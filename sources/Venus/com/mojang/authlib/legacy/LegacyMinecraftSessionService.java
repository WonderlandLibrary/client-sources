/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.authlib.legacy;

import com.mojang.authlib.AuthenticationService;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.HttpAuthenticationService;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.exceptions.AuthenticationUnavailableException;
import com.mojang.authlib.legacy.LegacyAuthenticationService;
import com.mojang.authlib.minecraft.HttpMinecraftSessionService;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
@Deprecated
public class LegacyMinecraftSessionService
extends HttpMinecraftSessionService {
    private static final String BASE_URL = "http://session.minecraft.net/game/";
    private static final URL JOIN_URL = HttpAuthenticationService.constantURL("http://session.minecraft.net/game/joinserver.jsp");
    private static final URL CHECK_URL = HttpAuthenticationService.constantURL("http://session.minecraft.net/game/checkserver.jsp");

    protected LegacyMinecraftSessionService(LegacyAuthenticationService legacyAuthenticationService) {
        super(legacyAuthenticationService);
    }

    @Override
    public void joinServer(GameProfile gameProfile, String string, String string2) throws AuthenticationException {
        HashMap<String, Object> hashMap = new HashMap<String, Object>();
        hashMap.put("user", gameProfile.getName());
        hashMap.put("sessionId", string);
        hashMap.put("serverId", string2);
        URL uRL = HttpAuthenticationService.concatenateURL(JOIN_URL, HttpAuthenticationService.buildQuery(hashMap));
        try {
            String string3 = this.getAuthenticationService().performGetRequest(uRL);
            if (!"OK".equals(string3)) {
                throw new AuthenticationException(string3);
            }
        } catch (IOException iOException) {
            throw new AuthenticationUnavailableException(iOException);
        }
    }

    @Override
    public GameProfile hasJoinedServer(GameProfile gameProfile, String string, InetAddress inetAddress) throws AuthenticationUnavailableException {
        HashMap<String, Object> hashMap = new HashMap<String, Object>();
        hashMap.put("user", gameProfile.getName());
        hashMap.put("serverId", string);
        URL uRL = HttpAuthenticationService.concatenateURL(CHECK_URL, HttpAuthenticationService.buildQuery(hashMap));
        try {
            String string2 = this.getAuthenticationService().performGetRequest(uRL);
            return "YES".equals(string2) ? gameProfile : null;
        } catch (IOException iOException) {
            throw new AuthenticationUnavailableException(iOException);
        }
    }

    @Override
    public Map<MinecraftProfileTexture.Type, MinecraftProfileTexture> getTextures(GameProfile gameProfile, boolean bl) {
        return new HashMap<MinecraftProfileTexture.Type, MinecraftProfileTexture>();
    }

    @Override
    public GameProfile fillProfileProperties(GameProfile gameProfile, boolean bl) {
        return gameProfile;
    }

    @Override
    public LegacyAuthenticationService getAuthenticationService() {
        return (LegacyAuthenticationService)super.getAuthenticationService();
    }

    @Override
    public HttpAuthenticationService getAuthenticationService() {
        return this.getAuthenticationService();
    }

    @Override
    public AuthenticationService getAuthenticationService() {
        return this.getAuthenticationService();
    }
}

