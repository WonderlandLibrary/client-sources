/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.authlib;

import com.mojang.authlib.properties.PropertyMap;
import java.util.UUID;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class GameProfile {
    private final UUID id;
    private final String name;
    private final PropertyMap properties = new PropertyMap();
    private boolean legacy;

    public GameProfile(UUID uUID, String string) {
        if (uUID == null && StringUtils.isBlank(string)) {
            throw new IllegalArgumentException("Name and ID cannot both be blank");
        }
        this.id = uUID;
        this.name = string;
    }

    public UUID getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public PropertyMap getProperties() {
        return this.properties;
    }

    public boolean isComplete() {
        return this.id != null && StringUtils.isNotBlank(this.getName());
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (object == null || this.getClass() != object.getClass()) {
            return true;
        }
        GameProfile gameProfile = (GameProfile)object;
        if (this.id != null ? !this.id.equals(gameProfile.id) : gameProfile.id != null) {
            return true;
        }
        return this.name != null ? !this.name.equals(gameProfile.name) : gameProfile.name != null;
    }

    public int hashCode() {
        int n = this.id != null ? this.id.hashCode() : 0;
        n = 31 * n + (this.name != null ? this.name.hashCode() : 0);
        return n;
    }

    public String toString() {
        return new ToStringBuilder(this).append("id", this.id).append("name", this.name).append("properties", this.properties).append("legacy", this.legacy).toString();
    }

    public boolean isLegacy() {
        return this.legacy;
    }
}

