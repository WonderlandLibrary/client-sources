/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.security;

import io.jsonwebtoken.Identifiable;
import io.jsonwebtoken.security.HashAlgorithm;
import io.jsonwebtoken.security.JwkThumbprint;
import io.jsonwebtoken.security.KeyOperation;
import java.security.Key;
import java.util.Map;
import java.util.Set;

public interface Jwk<K extends Key>
extends Identifiable,
Map<String, Object> {
    public String getAlgorithm();

    public Set<KeyOperation> getOperations();

    public String getType();

    public JwkThumbprint thumbprint();

    public JwkThumbprint thumbprint(HashAlgorithm var1);

    public K toKey();
}

