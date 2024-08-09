/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.security;

import io.jsonwebtoken.security.HashAlgorithm;
import java.net.URI;

public interface JwkThumbprint {
    public HashAlgorithm getHashAlgorithm();

    public byte[] toByteArray();

    public URI toURI();

    public String toString();
}

