/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.security;

import io.jsonwebtoken.security.KeyBuilder;
import java.security.PrivateKey;
import java.security.PublicKey;

public interface PrivateKeyBuilder
extends KeyBuilder<PrivateKey, PrivateKeyBuilder> {
    public PrivateKeyBuilder publicKey(PublicKey var1);
}

