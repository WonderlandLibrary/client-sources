/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.security;

import io.jsonwebtoken.security.KeyOperationPolicy;

public interface KeyOperationPolicied<T extends KeyOperationPolicied<T>> {
    public T operationPolicy(KeyOperationPolicy var1) throws IllegalArgumentException;
}

