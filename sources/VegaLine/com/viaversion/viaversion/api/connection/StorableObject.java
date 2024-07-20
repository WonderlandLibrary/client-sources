/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.api.connection;

public interface StorableObject {
    default public boolean clearOnServerSwitch() {
        return true;
    }

    default public void onRemove() {
    }
}

