/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.dump;

import com.viaversion.viaversion.dump.VersionInfo;
import com.viaversion.viaversion.libs.gson.JsonObject;
import java.util.Map;

public class DumpTemplate {
    private final VersionInfo versionInfo;
    private final Map<String, Object> configuration;
    private final JsonObject platformDump;
    private final JsonObject injectionDump;
    private final JsonObject playerSample;

    public DumpTemplate(VersionInfo versionInfo, Map<String, Object> map, JsonObject jsonObject, JsonObject jsonObject2, JsonObject jsonObject3) {
        this.versionInfo = versionInfo;
        this.configuration = map;
        this.platformDump = jsonObject;
        this.injectionDump = jsonObject2;
        this.playerSample = jsonObject3;
    }

    public VersionInfo getVersionInfo() {
        return this.versionInfo;
    }

    public Map<String, Object> getConfiguration() {
        return this.configuration;
    }

    public JsonObject getPlatformDump() {
        return this.platformDump;
    }

    public JsonObject getInjectionDump() {
        return this.injectionDump;
    }

    public JsonObject getPlayerSample() {
        return this.playerSample;
    }
}

