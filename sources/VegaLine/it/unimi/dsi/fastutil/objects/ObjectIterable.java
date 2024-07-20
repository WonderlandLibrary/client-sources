/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.objects.ObjectIterator;

public interface ObjectIterable<K>
extends Iterable<K> {
    @Override
    public ObjectIterator<K> iterator();
}

