/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.govno.client.cfg;

import com.google.gson.JsonObject;

public interface ConfigUpdater {
    public JsonObject save();

    public void load(JsonObject var1);
}

