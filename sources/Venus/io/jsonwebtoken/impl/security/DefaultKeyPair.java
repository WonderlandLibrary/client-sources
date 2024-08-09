/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.security;

import io.jsonwebtoken.lang.Assert;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

public class DefaultKeyPair<A extends PublicKey, B extends PrivateKey>
implements io.jsonwebtoken.security.KeyPair<A, B> {
    private final A publicKey;
    private final B privateKey;
    private final KeyPair jdkPair;

    public DefaultKeyPair(A a, B b) {
        this.publicKey = (PublicKey)Assert.notNull(a, "PublicKey argument cannot be null.");
        this.privateKey = (PrivateKey)Assert.notNull(b, "PrivateKey argument cannot be null.");
        this.jdkPair = new KeyPair((PublicKey)this.publicKey, (PrivateKey)this.privateKey);
    }

    @Override
    public A getPublic() {
        return this.publicKey;
    }

    @Override
    public B getPrivate() {
        return this.privateKey;
    }

    @Override
    public KeyPair toJavaKeyPair() {
        return this.jdkPair;
    }
}

