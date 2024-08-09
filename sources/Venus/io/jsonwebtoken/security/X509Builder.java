/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.security;

import io.jsonwebtoken.security.X509Mutator;

public interface X509Builder<T extends X509Builder<T>>
extends X509Mutator<T> {
    public T x509Sha1Thumbprint(boolean var1);

    public T x509Sha256Thumbprint(boolean var1);
}

