/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.security;

import io.jsonwebtoken.security.Jwk;
import io.jsonwebtoken.security.X509Accessor;
import java.security.Key;

public interface AsymmetricJwk<K extends Key>
extends Jwk<K>,
X509Accessor {
    public String getPublicKeyUse();
}

