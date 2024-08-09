/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.lang;

import java.util.Collection;

public interface CollectionMutator<E, M extends CollectionMutator<E, M>> {
    public M add(E var1);

    public M add(Collection<? extends E> var1);

    public M clear();

    public M remove(E var1);
}

