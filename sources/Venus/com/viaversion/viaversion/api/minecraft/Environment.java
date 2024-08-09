/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.api.minecraft;

public enum Environment {
    NORMAL(0),
    NETHER(-1),
    END(1);

    private final int id;

    private Environment(int n2) {
        this.id = n2;
    }

    public int id() {
        return this.id;
    }

    @Deprecated
    public int getId() {
        return this.id;
    }

    public static Environment getEnvironmentById(int n) {
        switch (n) {
            default: {
                return NETHER;
            }
            case 0: {
                return NORMAL;
            }
            case 1: 
        }
        return END;
    }
}

