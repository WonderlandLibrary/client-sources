/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.security;

import io.jsonwebtoken.security.AsymmetricJwk;
import io.jsonwebtoken.security.JwkBuilder;
import io.jsonwebtoken.security.X509Builder;
import java.security.Key;

public interface AsymmetricJwkBuilder<K extends Key, J extends AsymmetricJwk<K>, T extends AsymmetricJwkBuilder<K, J, T>>
extends JwkBuilder<K, J, T>,
X509Builder<T> {
    public T publicKeyUse(String var1) throws IllegalArgumentException;
}

