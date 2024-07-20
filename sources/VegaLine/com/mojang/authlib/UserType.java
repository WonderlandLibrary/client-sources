/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.authlib;

import java.util.HashMap;
import java.util.Map;

public enum UserType {
    LEGACY("legacy"),
    MOJANG("mojang");

    private static final Map<String, UserType> BY_NAME;
    private final String name;

    private UserType(String name) {
        this.name = name;
    }

    public static UserType byName(String name) {
        return BY_NAME.get(name.toLowerCase());
    }

    public String getName() {
        return this.name;
    }

    static {
        BY_NAME = new HashMap<String, UserType>();
        for (UserType type2 : UserType.values()) {
            BY_NAME.put(type2.name, type2);
        }
    }
}

