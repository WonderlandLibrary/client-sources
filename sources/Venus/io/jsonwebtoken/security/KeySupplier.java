/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.security;

import java.security.Key;

public interface KeySupplier<K extends Key> {
    public K getKey();
}

