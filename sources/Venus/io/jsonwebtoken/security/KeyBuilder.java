/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.security;

import io.jsonwebtoken.security.SecurityBuilder;
import java.security.Key;

public interface KeyBuilder<K extends Key, B extends KeyBuilder<K, B>>
extends SecurityBuilder<K, B> {
}

