/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.security;

import io.jsonwebtoken.security.KeyOperation;
import java.util.Collection;

public interface KeyOperationPolicy {
    public Collection<KeyOperation> getOperations();

    public void validate(Collection<? extends KeyOperation> var1) throws IllegalArgumentException;
}

