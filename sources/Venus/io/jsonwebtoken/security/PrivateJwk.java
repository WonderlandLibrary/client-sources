/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.security;

import io.jsonwebtoken.security.AsymmetricJwk;
import io.jsonwebtoken.security.KeyPair;
import io.jsonwebtoken.security.PublicJwk;
import java.security.PrivateKey;
import java.security.PublicKey;

public interface PrivateJwk<K extends PrivateKey, L extends PublicKey, M extends PublicJwk<L>>
extends AsymmetricJwk<K> {
    public M toPublicJwk();

    public KeyPair<L, K> toKeyPair();
}

