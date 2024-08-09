/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.authlib.yggdrasil;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.mojang.authlib.Agent;
import com.mojang.authlib.Environment;
import com.mojang.authlib.EnvironmentParser;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.GameProfileRepository;
import com.mojang.authlib.HttpAuthenticationService;
import com.mojang.authlib.UserAuthentication;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.exceptions.AuthenticationUnavailableException;
import com.mojang.authlib.exceptions.InsufficientPrivilegesException;
import com.mojang.authlib.exceptions.InvalidCredentialsException;
import com.mojang.authlib.exceptions.UserMigratedException;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.authlib.properties.PropertyMap;
import com.mojang.authlib.yggdrasil.YggdrasilEnvironment;
import com.mojang.authlib.yggdrasil.YggdrasilGameProfileRepository;
import com.mojang.authlib.yggdrasil.YggdrasilMinecraftSessionService;
import com.mojang.authlib.yggdrasil.YggdrasilSocialInteractionsService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;
import com.mojang.authlib.yggdrasil.response.ProfileSearchResultsResponse;
import com.mojang.authlib.yggdrasil.response.Response;
import com.mojang.util.UUIDTypeAdapter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.Proxy;
import java.net.URL;
import java.util.UUID;
import javax.annotation.Nullable;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class YggdrasilAuthenticationService
extends HttpAuthenticationService {
    private static final Logger LOGGER = LogManager.getLogger();
    @Nullable
    private final String clientToken;
    private final Gson gson;
    private final Environment environment;

    public YggdrasilAuthenticationService(Proxy proxy) {
        this(proxy, YggdrasilAuthenticationService.determineEnvironment());
    }

    public YggdrasilAuthenticationService(Proxy proxy, Environment environment) {
        this(proxy, null, environment);
    }

    public YggdrasilAuthenticationService(Proxy proxy, @Nullable String string) {
        this(proxy, string, YggdrasilAuthenticationService.determineEnvironment());
    }

    public YggdrasilAuthenticationService(Proxy proxy, @Nullable String string, Environment environment) {
        super(proxy);
        this.clientToken = string;
        this.environment = environment;
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter((Type)((Object)GameProfile.class), new GameProfileSerializer(null));
        gsonBuilder.registerTypeAdapter((Type)((Object)PropertyMap.class), new PropertyMap.Serializer());
        gsonBuilder.registerTypeAdapter((Type)((Object)UUID.class), new UUIDTypeAdapter());
        gsonBuilder.registerTypeAdapter((Type)((Object)ProfileSearchResultsResponse.class), new ProfileSearchResultsResponse.Serializer());
        this.gson = gsonBuilder.create();
        LOGGER.info("Environment: " + environment.asString());
    }

    private static Environment determineEnvironment() {
        return EnvironmentParser.getEnvironmentFromProperties().orElse(YggdrasilEnvironment.PROD);
    }

    @Override
    public UserAuthentication createUserAuthentication(Agent agent) {
        if (this.clientToken == null) {
            throw new IllegalStateException("Missing client token");
        }
        return new YggdrasilUserAuthentication(this, this.clientToken, agent, this.environment);
    }

    @Override
    public MinecraftSessionService createMinecraftSessionService() {
        return new YggdrasilMinecraftSessionService(this, this.environment);
    }

    @Override
    public GameProfileRepository createProfileRepository() {
        return new YggdrasilGameProfileRepository(this, this.environment);
    }

    protected <T extends Response> T makeRequest(URL uRL, Object object, Class<T> clazz) throws AuthenticationException {
        return this.makeRequest(uRL, object, clazz, null);
    }

    protected <T extends Response> T makeRequest(URL uRL, Object object, Class<T> clazz, @Nullable String string) throws AuthenticationException {
        try {
            String string2 = object == null ? this.performGetRequest(uRL, string) : this.performPostRequest(uRL, this.gson.toJson(object), "application/json");
            Response response = (Response)this.gson.fromJson(string2, clazz);
            if (response == null) {
                return null;
            }
            if (StringUtils.isNotBlank(response.getError())) {
                if ("UserMigratedException".equals(response.getCause())) {
                    throw new UserMigratedException(response.getErrorMessage());
                }
                if ("ForbiddenOperationException".equals(response.getError())) {
                    throw new InvalidCredentialsException(response.getErrorMessage());
                }
                if ("InsufficientPrivilegesException".equals(response.getError())) {
                    throw new InsufficientPrivilegesException(response.getErrorMessage());
                }
                throw new AuthenticationException(response.getErrorMessage());
            }
            return (T)response;
        } catch (JsonParseException | IOException | IllegalStateException exception) {
            throw new AuthenticationUnavailableException("Cannot contact authentication server", exception);
        }
    }

    public YggdrasilSocialInteractionsService createSocialInteractionsService(String string) throws AuthenticationException {
        return new YggdrasilSocialInteractionsService(this, string, this.environment);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private static class GameProfileSerializer
    implements JsonSerializer<GameProfile>,
    JsonDeserializer<GameProfile> {
        private GameProfileSerializer() {
        }

        @Override
        public GameProfile deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            JsonObject jsonObject = (JsonObject)jsonElement;
            UUID uUID = jsonObject.has("id") ? (UUID)jsonDeserializationContext.deserialize(jsonObject.get("id"), (Type)((Object)UUID.class)) : null;
            String string = jsonObject.has("name") ? jsonObject.getAsJsonPrimitive("name").getAsString() : null;
            return new GameProfile(uUID, string);
        }

        @Override
        public JsonElement serialize(GameProfile gameProfile, Type type, JsonSerializationContext jsonSerializationContext) {
            JsonObject jsonObject = new JsonObject();
            if (gameProfile.getId() != null) {
                jsonObject.add("id", jsonSerializationContext.serialize(gameProfile.getId()));
            }
            if (gameProfile.getName() != null) {
                jsonObject.addProperty("name", gameProfile.getName());
            }
            return jsonObject;
        }

        @Override
        public JsonElement serialize(Object object, Type type, JsonSerializationContext jsonSerializationContext) {
            return this.serialize((GameProfile)object, type, jsonSerializationContext);
        }

        @Override
        public Object deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            return this.deserialize(jsonElement, type, jsonDeserializationContext);
        }

        GameProfileSerializer(1 var1_1) {
            this();
        }
    }
}

