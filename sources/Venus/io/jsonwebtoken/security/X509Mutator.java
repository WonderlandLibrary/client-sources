/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.security;

import java.net.URI;
import java.security.cert.X509Certificate;
import java.util.List;

public interface X509Mutator<T extends X509Mutator<T>> {
    public T x509Url(URI var1);

    public T x509Chain(List<X509Certificate> var1);

    public T x509Sha1Thumbprint(byte[] var1);

    public T x509Sha256Thumbprint(byte[] var1);
}

