/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.security;

import io.jsonwebtoken.lang.MapMutator;
import io.jsonwebtoken.lang.NestedCollection;
import io.jsonwebtoken.security.HashAlgorithm;
import io.jsonwebtoken.security.Jwk;
import io.jsonwebtoken.security.KeyOperation;
import io.jsonwebtoken.security.KeyOperationPolicied;
import io.jsonwebtoken.security.SecurityBuilder;
import java.security.Key;

public interface JwkBuilder<K extends Key, J extends Jwk<K>, T extends JwkBuilder<K, J, T>>
extends MapMutator<String, Object, T>,
SecurityBuilder<J, T>,
KeyOperationPolicied<T> {
    public T algorithm(String var1) throws IllegalArgumentException;

    public T id(String var1) throws IllegalArgumentException;

    public T idFromThumbprint();

    public T idFromThumbprint(HashAlgorithm var1);

    public NestedCollection<KeyOperation, T> operations();
}

