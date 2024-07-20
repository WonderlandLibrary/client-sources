/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.objects.ObjectIterable;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import java.util.Collection;

public interface ObjectCollection<K>
extends Collection<K>,
ObjectIterable<K> {
    @Override
    public ObjectIterator<K> iterator();

    @Deprecated
    public ObjectIterator<K> objectIterator();

    @Override
    public <T> T[] toArray(T[] var1);
}

