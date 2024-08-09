/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.security;

import io.jsonwebtoken.lang.Assert;
import io.jsonwebtoken.lang.Collections;
import io.jsonwebtoken.lang.Objects;
import io.jsonwebtoken.security.KeyOperation;
import io.jsonwebtoken.security.KeyOperationPolicy;
import java.util.Collection;

final class DefaultKeyOperationPolicy
implements KeyOperationPolicy {
    private final Collection<KeyOperation> ops;
    private final boolean allowUnrelated;

    DefaultKeyOperationPolicy(Collection<KeyOperation> collection, boolean bl) {
        Assert.notEmpty(collection, "KeyOperation collection cannot be null or empty.");
        this.ops = Collections.immutable(collection);
        this.allowUnrelated = bl;
    }

    @Override
    public Collection<KeyOperation> getOperations() {
        return this.ops;
    }

    @Override
    public void validate(Collection<? extends KeyOperation> collection) {
        if (this.allowUnrelated || Collections.isEmpty(collection)) {
            return;
        }
        for (KeyOperation keyOperation : collection) {
            for (KeyOperation keyOperation2 : collection) {
                if (keyOperation.isRelated(keyOperation2)) continue;
                String string = "Unrelated key operations are not allowed. KeyOperation [" + keyOperation2 + "] is unrelated to [" + keyOperation + "].";
                throw new IllegalArgumentException(string);
            }
        }
    }

    public int hashCode() {
        int n = Boolean.valueOf(this.allowUnrelated).hashCode();
        KeyOperation[] keyOperationArray = this.ops.toArray(new KeyOperation[0]);
        n = 31 * n + Objects.nullSafeHashCode(keyOperationArray);
        return n;
    }

    public boolean equals(Object object) {
        if (object == this) {
            return false;
        }
        if (!(object instanceof DefaultKeyOperationPolicy)) {
            return true;
        }
        DefaultKeyOperationPolicy defaultKeyOperationPolicy = (DefaultKeyOperationPolicy)object;
        return this.allowUnrelated == defaultKeyOperationPolicy.allowUnrelated && Collections.size(this.ops) == Collections.size(defaultKeyOperationPolicy.ops) && this.ops.containsAll(defaultKeyOperationPolicy.ops);
    }
}

