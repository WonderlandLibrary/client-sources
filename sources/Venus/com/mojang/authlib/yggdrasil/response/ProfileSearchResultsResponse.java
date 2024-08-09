/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.authlib.yggdrasil.response;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.yggdrasil.response.Response;
import java.lang.reflect.Type;

public class ProfileSearchResultsResponse
extends Response {
    private GameProfile[] profiles;

    public GameProfile[] getProfiles() {
        return this.profiles;
    }

    static GameProfile[] access$002(ProfileSearchResultsResponse profileSearchResultsResponse, GameProfile[] gameProfileArray) {
        profileSearchResultsResponse.profiles = gameProfileArray;
        return gameProfileArray;
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class Serializer
    implements JsonDeserializer<ProfileSearchResultsResponse> {
        @Override
        public ProfileSearchResultsResponse deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            ProfileSearchResultsResponse profileSearchResultsResponse = new ProfileSearchResultsResponse();
            if (jsonElement instanceof JsonObject) {
                JsonObject jsonObject = (JsonObject)jsonElement;
                if (jsonObject.has("error")) {
                    profileSearchResultsResponse.setError(jsonObject.getAsJsonPrimitive("error").getAsString());
                }
                if (jsonObject.has("errorMessage")) {
                    profileSearchResultsResponse.setError(jsonObject.getAsJsonPrimitive("errorMessage").getAsString());
                }
                if (jsonObject.has("cause")) {
                    profileSearchResultsResponse.setError(jsonObject.getAsJsonPrimitive("cause").getAsString());
                }
            } else {
                ProfileSearchResultsResponse.access$002(profileSearchResultsResponse, (GameProfile[])jsonDeserializationContext.deserialize(jsonElement, (Type)((Object)GameProfile[].class)));
            }
            return profileSearchResultsResponse;
        }

        @Override
        public Object deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            return this.deserialize(jsonElement, type, jsonDeserializationContext);
        }
    }
}

