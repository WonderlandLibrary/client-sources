/*
 * Decompiled with CFR 0.150.
 */
package us.myles.viaversion.libs.fastutil;

import java.util.Collection;
import us.myles.viaversion.libs.fastutil.BigListIterator;
import us.myles.viaversion.libs.fastutil.Size64;

public interface BigList<K>
extends Collection<K>,
Size64 {
    public K get(long var1);

    public K remove(long var1);

    public K set(long var1, K var3);

    public void add(long var1, K var3);

    public void size(long var1);

    public boolean addAll(long var1, Collection<? extends K> var3);

    public long indexOf(Object var1);

    public long lastIndexOf(Object var1);

    public BigListIterator<K> listIterator();

    public BigListIterator<K> listIterator(long var1);

    public BigList<K> subList(long var1, long var3);

    @Override
    @Deprecated
    default public int size() {
        return Size64.super.size();
    }
}

