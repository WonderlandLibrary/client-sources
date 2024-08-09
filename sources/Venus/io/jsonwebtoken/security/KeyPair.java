/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.security;

import java.security.PrivateKey;
import java.security.PublicKey;

public interface KeyPair<A extends PublicKey, B extends PrivateKey> {
    public A getPublic();

    public B getPrivate();

    public java.security.KeyPair toJavaKeyPair();
}

