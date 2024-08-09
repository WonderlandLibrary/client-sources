/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.security;

import io.jsonwebtoken.impl.lang.DefaultCollectionMutator;
import io.jsonwebtoken.impl.security.DefaultKeyOperationPolicy;
import io.jsonwebtoken.lang.Collections;
import io.jsonwebtoken.security.Jwks;
import io.jsonwebtoken.security.KeyOperation;
import io.jsonwebtoken.security.KeyOperationPolicy;
import io.jsonwebtoken.security.KeyOperationPolicyBuilder;
import java.util.Collection;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class DefaultKeyOperationPolicyBuilder
extends DefaultCollectionMutator<KeyOperation, KeyOperationPolicyBuilder>
implements KeyOperationPolicyBuilder {
    private boolean unrelated = false;

    public DefaultKeyOperationPolicyBuilder() {
        super(Jwks.OP.get().values());
    }

    @Override
    public KeyOperationPolicyBuilder unrelated() {
        this.unrelated = true;
        return this;
    }

    @Override
    public KeyOperationPolicy build() {
        return new DefaultKeyOperationPolicy(Collections.immutable(this.getCollection()), this.unrelated);
    }

    @Override
    public KeyOperationPolicyBuilder add(Collection collection) {
        return (KeyOperationPolicyBuilder)super.add(collection);
    }

    @Override
    public KeyOperationPolicyBuilder add(KeyOperation keyOperation) {
        return (KeyOperationPolicyBuilder)super.add(keyOperation);
    }

    @Override
    public Object build() {
        return this.build();
    }
}

