/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.lang;

import java.util.Map;

public interface Registry<K, V>
extends Map<K, V> {
    public V forKey(K var1) throws IllegalArgumentException;
}

