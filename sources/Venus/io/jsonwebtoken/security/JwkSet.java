/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.security;

import io.jsonwebtoken.security.Jwk;
import java.util.Map;
import java.util.Set;

public interface JwkSet
extends Map<String, Object>,
Iterable<Jwk<?>> {
    public Set<Jwk<?>> getKeys();
}

