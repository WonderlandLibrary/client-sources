/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.authlib.yggdrasil;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Iterables;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import com.mojang.authlib.AuthenticationService;
import com.mojang.authlib.Environment;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.HttpAuthenticationService;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.exceptions.AuthenticationUnavailableException;
import com.mojang.authlib.minecraft.HttpMinecraftSessionService;
import com.mojang.authlib.minecraft.InsecureTextureException;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.request.JoinMinecraftServerRequest;
import com.mojang.authlib.yggdrasil.response.HasJoinedMinecraftServerResponse;
import com.mojang.authlib.yggdrasil.response.MinecraftProfilePropertiesResponse;
import com.mojang.authlib.yggdrasil.response.MinecraftTexturesPayload;
import com.mojang.authlib.yggdrasil.response.Response;
import com.mojang.util.UUIDTypeAdapter;
import java.lang.reflect.Type;
import java.net.InetAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import org.apache.commons.codec.Charsets;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class YggdrasilMinecraftSessionService
extends HttpMinecraftSessionService {
    private static final String[] WHITELISTED_DOMAINS = new String[]{".minecraft.net", ".mojang.com"};
    private static final Logger LOGGER = LogManager.getLogger();
    private final String baseUrl;
    private final URL joinUrl;
    private final URL checkUrl;
    private final PublicKey publicKey;
    private final Gson gson = new GsonBuilder().registerTypeAdapter((Type)((Object)UUID.class), new UUIDTypeAdapter()).create();
    private final LoadingCache<GameProfile, GameProfile> insecureProfiles = CacheBuilder.newBuilder().expireAfterWrite(6L, TimeUnit.HOURS).build(new CacheLoader<GameProfile, GameProfile>(this){
        final YggdrasilMinecraftSessionService this$0;
        {
            this.this$0 = yggdrasilMinecraftSessionService;
        }

        @Override
        public GameProfile load(GameProfile gameProfile) throws Exception {
            return this.this$0.fillGameProfile(gameProfile, true);
        }

        @Override
        public Object load(Object object) throws Exception {
            return this.load((GameProfile)object);
        }
    });

    protected YggdrasilMinecraftSessionService(YggdrasilAuthenticationService yggdrasilAuthenticationService, Environment environment) {
        super(yggdrasilAuthenticationService);
        this.baseUrl = environment.getSessionHost() + "/session/minecraft/";
        this.joinUrl = HttpAuthenticationService.constantURL(this.baseUrl + "join");
        this.checkUrl = HttpAuthenticationService.constantURL(this.baseUrl + "hasJoined");
        try {
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(IOUtils.toByteArray(YggdrasilMinecraftSessionService.class.getResourceAsStream("/yggdrasil_session_pubkey.der")));
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            this.publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
        } catch (Exception exception) {
            throw new Error("Missing/invalid yggdrasil public key!");
        }
    }

    @Override
    public void joinServer(GameProfile gameProfile, String string, String string2) throws AuthenticationException {
        JoinMinecraftServerRequest joinMinecraftServerRequest = new JoinMinecraftServerRequest();
        joinMinecraftServerRequest.accessToken = string;
        joinMinecraftServerRequest.selectedProfile = gameProfile.getId();
        joinMinecraftServerRequest.serverId = string2;
        this.getAuthenticationService().makeRequest(this.joinUrl, joinMinecraftServerRequest, Response.class);
    }

    @Override
    public GameProfile hasJoinedServer(GameProfile gameProfile, String string, InetAddress inetAddress) throws AuthenticationUnavailableException {
        HashMap<String, Object> hashMap = new HashMap<String, Object>();
        hashMap.put("username", gameProfile.getName());
        hashMap.put("serverId", string);
        if (inetAddress != null) {
            hashMap.put("ip", inetAddress.getHostAddress());
        }
        URL uRL = HttpAuthenticationService.concatenateURL(this.checkUrl, HttpAuthenticationService.buildQuery(hashMap));
        try {
            HasJoinedMinecraftServerResponse hasJoinedMinecraftServerResponse = this.getAuthenticationService().makeRequest(uRL, null, HasJoinedMinecraftServerResponse.class);
            if (hasJoinedMinecraftServerResponse != null && hasJoinedMinecraftServerResponse.getId() != null) {
                GameProfile gameProfile2 = new GameProfile(hasJoinedMinecraftServerResponse.getId(), gameProfile.getName());
                if (hasJoinedMinecraftServerResponse.getProperties() != null) {
                    gameProfile2.getProperties().putAll(hasJoinedMinecraftServerResponse.getProperties());
                }
                return gameProfile2;
            }
            return null;
        } catch (AuthenticationUnavailableException authenticationUnavailableException) {
            throw authenticationUnavailableException;
        } catch (AuthenticationException authenticationException) {
            return null;
        }
    }

    @Override
    public Map<MinecraftProfileTexture.Type, MinecraftProfileTexture> getTextures(GameProfile gameProfile, boolean bl) {
        MinecraftTexturesPayload minecraftTexturesPayload;
        Property property = Iterables.getFirst(gameProfile.getProperties().get("textures"), null);
        if (property == null) {
            return new HashMap<MinecraftProfileTexture.Type, MinecraftProfileTexture>();
        }
        if (bl) {
            if (!property.hasSignature()) {
                LOGGER.error("Signature is missing from textures payload");
                throw new InsecureTextureException("Signature is missing from textures payload");
            }
            if (!property.isSignatureValid(this.publicKey)) {
                LOGGER.error("Textures payload has been tampered with (signature invalid)");
                throw new InsecureTextureException("Textures payload has been tampered with (signature invalid)");
            }
        }
        try {
            String string = new String(Base64.decodeBase64(property.getValue()), Charsets.UTF_8);
            minecraftTexturesPayload = this.gson.fromJson(string, MinecraftTexturesPayload.class);
        } catch (JsonParseException jsonParseException) {
            LOGGER.error("Could not decode textures payload", (Throwable)jsonParseException);
            return new HashMap<MinecraftProfileTexture.Type, MinecraftProfileTexture>();
        }
        if (minecraftTexturesPayload == null || minecraftTexturesPayload.getTextures() == null) {
            return new HashMap<MinecraftProfileTexture.Type, MinecraftProfileTexture>();
        }
        for (Map.Entry entry : minecraftTexturesPayload.getTextures().entrySet()) {
            if (YggdrasilMinecraftSessionService.isWhitelistedDomain(((MinecraftProfileTexture)entry.getValue()).getUrl())) continue;
            LOGGER.error("Textures payload has been tampered with (non-whitelisted domain)");
            return new HashMap<MinecraftProfileTexture.Type, MinecraftProfileTexture>();
        }
        return minecraftTexturesPayload.getTextures();
    }

    @Override
    public GameProfile fillProfileProperties(GameProfile gameProfile, boolean bl) {
        if (gameProfile.getId() == null) {
            return gameProfile;
        }
        if (!bl) {
            return this.insecureProfiles.getUnchecked(gameProfile);
        }
        return this.fillGameProfile(gameProfile, false);
    }

    protected GameProfile fillGameProfile(GameProfile gameProfile, boolean bl) {
        try {
            URL uRL = HttpAuthenticationService.constantURL(this.baseUrl + "profile/" + UUIDTypeAdapter.fromUUID(gameProfile.getId()));
            uRL = HttpAuthenticationService.concatenateURL(uRL, "unsigned=" + !bl);
            MinecraftProfilePropertiesResponse minecraftProfilePropertiesResponse = this.getAuthenticationService().makeRequest(uRL, null, MinecraftProfilePropertiesResponse.class);
            if (minecraftProfilePropertiesResponse == null) {
                LOGGER.debug("Couldn't fetch profile properties for " + gameProfile + " as the profile does not exist");
                return gameProfile;
            }
            GameProfile gameProfile2 = new GameProfile(minecraftProfilePropertiesResponse.getId(), minecraftProfilePropertiesResponse.getName());
            gameProfile2.getProperties().putAll(minecraftProfilePropertiesResponse.getProperties());
            gameProfile.getProperties().putAll(minecraftProfilePropertiesResponse.getProperties());
            LOGGER.debug("Successfully fetched profile properties for " + gameProfile);
            return gameProfile2;
        } catch (AuthenticationException authenticationException) {
            LOGGER.warn("Couldn't look up profile properties for " + gameProfile, (Throwable)authenticationException);
            return gameProfile;
        }
    }

    @Override
    public YggdrasilAuthenticationService getAuthenticationService() {
        return (YggdrasilAuthenticationService)super.getAuthenticationService();
    }

    private static boolean isWhitelistedDomain(String string) {
        URI uRI = null;
        try {
            uRI = new URI(string);
        } catch (URISyntaxException uRISyntaxException) {
            throw new IllegalArgumentException("Invalid URL '" + string + "'");
        }
        String string2 = uRI.getHost();
        for (int i = 0; i < WHITELISTED_DOMAINS.length; ++i) {
            if (!string2.endsWith(WHITELISTED_DOMAINS[i])) continue;
            return false;
        }
        return true;
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

