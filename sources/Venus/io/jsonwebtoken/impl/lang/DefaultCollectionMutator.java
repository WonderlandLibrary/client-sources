/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.lang;

import io.jsonwebtoken.Identifiable;
import io.jsonwebtoken.lang.CollectionMutator;
import io.jsonwebtoken.lang.Collections;
import io.jsonwebtoken.lang.Objects;
import io.jsonwebtoken.lang.Strings;
import java.util.Collection;
import java.util.LinkedHashSet;

public class DefaultCollectionMutator<E, M extends CollectionMutator<E, M>>
implements CollectionMutator<E, M> {
    private final Collection<E> collection;

    public DefaultCollectionMutator(Collection<? extends E> collection) {
        this.collection = new LinkedHashSet<E>(Collections.nullSafe(collection));
    }

    protected final M self() {
        return (M)this;
    }

    @Override
    public M add(E e) {
        if (Objects.isEmpty(e)) {
            return this.self();
        }
        if (e instanceof Identifiable && !Strings.hasText(((Identifiable)e).getId())) {
            String string = e.getClass() + " getId() value cannot be null or empty.";
            throw new IllegalArgumentException(string);
        }
        this.collection.remove(e);
        this.collection.add(e);
        return this.self();
    }

    @Override
    public M remove(E e) {
        this.collection.remove(e);
        return this.self();
    }

    @Override
    public M add(Collection<? extends E> collection) {
        for (E e : Collections.nullSafe(collection)) {
            this.add(e);
        }
        return this.self();
    }

    @Override
    public M clear() {
        this.collection.clear();
        return this.self();
    }

    protected Collection<E> getCollection() {
        return Collections.immutable(this.collection);
    }
}

