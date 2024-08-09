/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
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

    public static @Nullable RegistryType getByKey(String string) {
        return MAP.get(string);
    }

    private RegistryType(String string2) {
        this.resourceLocation = string2;
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
        for (RegistryType registryType : RegistryType.getValues()) {
            MAP.put(registryType.resourceLocation, registryType);
        }
    }
}

