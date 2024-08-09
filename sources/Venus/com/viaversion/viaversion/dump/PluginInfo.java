/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.dump;

import java.util.List;

public class PluginInfo {
    private final boolean enabled;
    private final String name;
    private final String version;
    private final String main;
    private final List<String> authors;

    public PluginInfo(boolean bl, String string, String string2, String string3, List<String> list) {
        this.enabled = bl;
        this.name = string;
        this.version = string2;
        this.main = string3;
        this.authors = list;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public String getName() {
        return this.name;
    }

    public String getVersion() {
        return this.version;
    }

    public String getMain() {
        return this.main;
    }

    public List<String> getAuthors() {
        return this.authors;
    }
}

