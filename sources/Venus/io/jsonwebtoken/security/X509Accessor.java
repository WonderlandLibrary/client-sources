/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.security;

import java.net.URI;
import java.security.cert.X509Certificate;
import java.util.List;

public interface X509Accessor {
    public URI getX509Url();

    public List<X509Certificate> getX509Chain();

    public byte[] getX509Sha1Thumbprint();

    public byte[] getX509Sha256Thumbprint();
}

