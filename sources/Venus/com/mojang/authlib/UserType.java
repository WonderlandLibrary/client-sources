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

    private UserType(String string2) {
        this.name = string2;
    }

    public static UserType byName(String string) {
        return BY_NAME.get(string.toLowerCase());
    }

    public String getName() {
        return this.name;
    }

    static {
        BY_NAME = new HashMap<String, UserType>();
        for (UserType userType : UserType.values()) {
            BY_NAME.put(userType.name, userType);
        }
    }
}

