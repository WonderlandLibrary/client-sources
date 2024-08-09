/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl;

import io.jsonwebtoken.ClaimsMutator;
import io.jsonwebtoken.lang.Assert;
import io.jsonwebtoken.lang.CollectionMutator;
import java.util.Collection;

public class DelegateAudienceCollection<P>
implements ClaimsMutator.AudienceCollection<P> {
    private final ClaimsMutator.AudienceCollection<?> delegate;
    private final P parent;

    public DelegateAudienceCollection(P p, ClaimsMutator.AudienceCollection<?> audienceCollection) {
        this.parent = Assert.notNull(p, "Parent cannot be null.");
        this.delegate = Assert.notNull(audienceCollection, "Delegate cannot be null.");
    }

    @Override
    public P single(String string) {
        this.delegate.single(string);
        return this.parent;
    }

    @Override
    public ClaimsMutator.AudienceCollection<P> add(String string) {
        this.delegate.add(string);
        return this;
    }

    @Override
    public ClaimsMutator.AudienceCollection<P> add(Collection<? extends String> collection) {
        this.delegate.add(collection);
        return this;
    }

    @Override
    public ClaimsMutator.AudienceCollection<P> clear() {
        this.delegate.clear();
        return this;
    }

    @Override
    public ClaimsMutator.AudienceCollection<P> remove(String string) {
        this.delegate.remove(string);
        return this;
    }

    @Override
    public P and() {
        this.delegate.and();
        return this.parent;
    }

    @Override
    public CollectionMutator remove(Object object) {
        return this.remove((String)object);
    }

    @Override
    public CollectionMutator clear() {
        return this.clear();
    }

    @Override
    public CollectionMutator add(Collection collection) {
        return this.add(collection);
    }

    @Override
    public CollectionMutator add(Object object) {
        return this.add((String)object);
    }
}

