/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.api.minecraft;

import java.util.HashMap;
import java.util.Map;
import org.checkerframework.checker.nullness.qual.Nullable;

public enum RegistryType {
    BLOCK("block"),
    ITEM("item"),
    FLUID("fluid"),
    ENTITY("entity_type"),
    GAME_EVENT("game_event");

    private static final Map<String, RegistryType> MAP;
    private static final RegistryType[] VALUES;
    private final String resourceLocation;

    public static RegistryType[] getValues() {
        return VALUES;
    }

    public static @Nullable RegistryType getByKey(String resourceKey) {
        return MAP.get(resourceKey);
    }

    private RegistryType(String resourceLocation) {
        this.resourceLocation = resourceLocation;
    }

    @Deprecated
    public String getResourceLocation() {
        return this.resourceLocation;
    }

    public String resourceLocation() {
        return this.resourceLocation;
    }

    static {
        MAP = new HashMap<String, RegistryType>();
        VALUES = RegistryType.values();
        RegistryType[] registryTypeArray = RegistryType.getValues();
        int n = registryTypeArray.length;
        int n2 = 0;
        while (n2 < n) {
            RegistryType type = registryTypeArray[n2];
            MAP.put(type.resourceLocation, type);
            ++n2;
        }
    }
}

