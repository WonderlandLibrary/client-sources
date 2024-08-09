/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.dump;

import java.util.Set;

public class VersionInfo {
    private final String javaVersion;
    private final String operatingSystem;
    private final int serverProtocol;
    private final Set<Integer> enabledProtocols;
    private final String platformName;
    private final String platformVersion;
    private final String pluginVersion;
    private final String implementationVersion;
    private final Set<String> subPlatforms;

    public VersionInfo(String string, String string2, int n, Set<Integer> set, String string3, String string4, String string5, String string6, Set<String> set2) {
        this.javaVersion = string;
        this.operatingSystem = string2;
        this.serverProtocol = n;
        this.enabledProtocols = set;
        this.platformName = string3;
        this.platformVersion = string4;
        this.pluginVersion = string5;
        this.implementationVersion = string6;
        this.subPlatforms = set2;
    }

    public String getJavaVersion() {
        return this.javaVersion;
    }

    public String getOperatingSystem() {
        return this.operatingSystem;
    }

    public int getServerProtocol() {
        return this.serverProtocol;
    }

    public Set<Integer> getEnabledProtocols() {
        return this.enabledProtocols;
    }

    public String getPlatformName() {
        return this.platformName;
    }

    public String getPlatformVersion() {
        return this.platformVersion;
    }

    public String getPluginVersion() {
        return this.pluginVersion;
    }

    public String getImplementationVersion() {
        return this.implementationVersion;
    }

    public Set<String> getSubPlatforms() {
        return this.subPlatforms;
    }
}

