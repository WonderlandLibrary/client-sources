/*
 * Decompiled with CFR 0.150.
 */
package us.myles.ViaVersion.dump;

import java.util.Map;
import us.myles.ViaVersion.dump.VersionInfo;
import us.myles.viaversion.libs.gson.JsonObject;

public class DumpTemplate {
    private final VersionInfo versionInfo;
    private final Map<String, Object> configuration;
    private final JsonObject platformDump;
    private final JsonObject injectionDump;

    public DumpTemplate(VersionInfo versionInfo, Map<String, Object> configuration, JsonObject platformDump, JsonObject injectionDump) {
        this.versionInfo = versionInfo;
        this.configuration = configuration;
        this.platformDump = platformDump;
        this.injectionDump = injectionDump;
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
}

