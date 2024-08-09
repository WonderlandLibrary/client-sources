/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.security;

import io.jsonwebtoken.security.AsymmetricJwkBuilder;
import io.jsonwebtoken.security.PrivateJwk;
import io.jsonwebtoken.security.PublicJwk;
import java.security.PrivateKey;
import java.security.PublicKey;

public interface PrivateJwkBuilder<K extends PrivateKey, L extends PublicKey, J extends PublicJwk<L>, M extends PrivateJwk<K, L, J>, T extends PrivateJwkBuilder<K, L, J, M, T>>
extends AsymmetricJwkBuilder<K, M, T> {
    public T publicKey(L var1);
}

