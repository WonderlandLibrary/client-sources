/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.util;

import com.viaversion.viaversion.libs.gson.Gson;
import com.viaversion.viaversion.libs.gson.GsonBuilder;

public final class GsonUtil {
    private static final Gson GSON = new GsonBuilder().create();

    public static Gson getGson() {
        return GSON;
    }
}

