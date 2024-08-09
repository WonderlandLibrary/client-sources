/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.security;

import io.jsonwebtoken.impl.security.JwkContext;
import io.jsonwebtoken.security.Jwk;
import java.security.Key;

public interface JwkFactory<K extends Key, J extends Jwk<K>> {
    public JwkContext<K> newContext(JwkContext<?> var1, K var2);

    public J createJwk(JwkContext<K> var1);
}

