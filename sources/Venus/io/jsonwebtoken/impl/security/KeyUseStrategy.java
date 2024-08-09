/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.security;

import io.jsonwebtoken.impl.security.KeyUsage;

public interface KeyUseStrategy {
    public String toJwkValue(KeyUsage var1);
}

