/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.lang;

import io.jsonwebtoken.impl.lang.DefaultCollectionMutator;
import io.jsonwebtoken.lang.Assert;
import io.jsonwebtoken.lang.NestedCollection;
import java.util.Collection;

public class DefaultNestedCollection<E, P>
extends DefaultCollectionMutator<E, NestedCollection<E, P>>
implements NestedCollection<E, P> {
    private final P parent;

    public DefaultNestedCollection(P p, Collection<? extends E> collection) {
        super(collection);
        this.parent = Assert.notNull(p, "Parent cannot be null.");
    }

    @Override
    public P and() {
        return this.parent;
    }
}

