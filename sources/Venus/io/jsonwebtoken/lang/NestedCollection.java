/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.lang;

import io.jsonwebtoken.lang.CollectionMutator;
import io.jsonwebtoken.lang.Conjunctor;

public interface NestedCollection<E, P>
extends CollectionMutator<E, NestedCollection<E, P>>,
Conjunctor<P> {
}

