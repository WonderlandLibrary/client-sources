package com.mojang.authlib.yggdrasil;

import java.lang.reflect.*;
import com.mojang.authlib.properties.*;
import java.util.*;
import com.mojang.util.*;
import com.mojang.authlib.minecraft.*;
import com.mojang.authlib.*;
import java.net.*;
import com.mojang.authlib.yggdrasil.response.*;
import org.apache.commons.lang3.*;
import com.mojang.authlib.exceptions.*;
import java.io.*;
import com.google.gson.*;

public class YggdrasilAuthenticationService extends HttpAuthenticationService
{
    private final String clientToken;
    private final Gson gson;
    
    public YggdrasilAuthenticationService(final Proxy proxy, final String clientToken) {
        super(proxy);
        this.clientToken = clientToken;
        final GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(GameProfile.class, new GameProfileSerializer());
        builder.registerTypeAdapter(PropertyMap.class, new PropertyMap.Serializer());
        builder.registerTypeAdapter(UUID.class, new UUIDTypeAdapter());
        builder.registerTypeAdapter(ProfileSearchResultsResponse.class, new ProfileSearchResultsResponse.Serializer());
        this.gson = builder.create();
    }
    
    @Override
    public UserAuthentication createUserAuthentication(final Agent agent) {
        return new YggdrasilUserAuthentication(this, agent);
    }
    
    @Override
    public MinecraftSessionService createMinecraftSessionService() {
        return new YggdrasilMinecraftSessionService(this);
    }
    
    @Override
    public GameProfileRepository createProfileRepository() {
        return new YggdrasilGameProfileRepository(this);
    }
    
    protected <T extends Response> T makeRequest(final URL url, final Object input, final Class<T> classOfT) throws AuthenticationException {
        try {
            final String jsonResult = (input == null) ? this.performGetRequest(url) : this.performPostRequest(url, this.gson.toJson(input), "application/json");
            final T result = this.gson.fromJson(jsonResult, classOfT);
            if (result == null) {
                return null;
            }
            if (!StringUtils.isNotBlank(result.getError())) {
                return result;
            }
            if ("UserMigratedException".equals(result.getCause())) {
                throw new UserMigratedException(result.getErrorMessage());
            }
            if (result.getError().equals("ForbiddenOperationException")) {
                throw new InvalidCredentialsException(result.getErrorMessage());
            }
            throw new AuthenticationException(result.getErrorMessage());
        }
        catch (IOException e) {
            throw new AuthenticationUnavailableException("Cannot contact authentication server", e);
        }
        catch (IllegalStateException e2) {
            throw new AuthenticationUnavailableException("Cannot contact authentication server", e2);
        }
        catch (JsonParseException e3) {
            throw new AuthenticationUnavailableException("Cannot contact authentication server", e3);
        }
    }
    
    public String getClientToken() {
        return this.clientToken;
    }
    
    private static class GameProfileSerializer implements JsonSerializer<GameProfile>, JsonDeserializer<GameProfile>
    {
        @Override
        public GameProfile deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context) throws JsonParseException {
            final JsonObject object = (JsonObject)json;
            final UUID id = object.has("id") ? context.deserialize(object.get("id"), UUID.class) : null;
            final String name = object.has("name") ? object.getAsJsonPrimitive("name").getAsString() : null;
            return new GameProfile(id, name);
        }
        
        @Override
        public JsonElement serialize(final GameProfile src, final Type typeOfSrc, final JsonSerializationContext context) {
            final JsonObject result = new JsonObject();
            if (src.getId() != null) {
                result.add("id", context.serialize(src.getId()));
            }
            if (src.getName() != null) {
                result.addProperty("name", src.getName());
            }
            return result;
        }
    }
}
