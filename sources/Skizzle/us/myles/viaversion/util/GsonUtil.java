/*
 * Decompiled with CFR 0.150.
 */
package us.myles.ViaVersion.util;

import us.myles.viaversion.libs.gson.Gson;
import us.myles.viaversion.libs.gson.GsonBuilder;
import us.myles.viaversion.libs.gson.JsonParser;

public final class GsonUtil {
    private static final JsonParser JSON_PARSER = new JsonParser();
    private static final Gson GSON = GsonUtil.getGsonBuilder().create();

    public static Gson getGson() {
        return GSON;
    }

    public static GsonBuilder getGsonBuilder() {
        return new GsonBuilder();
    }

    public static JsonParser getJsonParser() {
        return JSON_PARSER;
    }
}

