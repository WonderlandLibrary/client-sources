/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.authlib.minecraft;

import java.util.Map;
import javax.annotation.Nullable;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class MinecraftProfileTexture {
    public static final int PROFILE_TEXTURE_COUNT = Type.values().length;
    private final String url;
    private final Map<String, String> metadata;

    public MinecraftProfileTexture(String string, Map<String, String> map) {
        this.url = string;
        this.metadata = map;
    }

    public String getUrl() {
        return this.url;
    }

    @Nullable
    public String getMetadata(String string) {
        if (this.metadata == null) {
            return null;
        }
        return this.metadata.get(string);
    }

    public String getHash() {
        return FilenameUtils.getBaseName(this.url);
    }

    public String toString() {
        return new ToStringBuilder(this).append("url", this.url).append("hash", this.getHash()).toString();
    }

    public static enum Type {
        SKIN,
        CAPE,
        ELYTRA;

    }
}

