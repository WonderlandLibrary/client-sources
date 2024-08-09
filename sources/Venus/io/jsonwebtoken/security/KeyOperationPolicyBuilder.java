/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.security;

import io.jsonwebtoken.lang.Builder;
import io.jsonwebtoken.lang.CollectionMutator;
import io.jsonwebtoken.security.KeyOperation;
import io.jsonwebtoken.security.KeyOperationPolicy;
import java.util.Collection;

public interface KeyOperationPolicyBuilder
extends CollectionMutator<KeyOperation, KeyOperationPolicyBuilder>,
Builder<KeyOperationPolicy> {
    public KeyOperationPolicyBuilder unrelated();

    @Override
    public KeyOperationPolicyBuilder add(KeyOperation var1);

    @Override
    public KeyOperationPolicyBuilder add(Collection<? extends KeyOperation> var1);
}

