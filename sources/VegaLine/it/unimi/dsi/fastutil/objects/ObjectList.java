/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.objects.ObjectCollection;
import it.unimi.dsi.fastutil.objects.ObjectListIterator;
import java.util.List;

public interface ObjectList<K>
extends List<K>,
Comparable<List<? extends K>>,
ObjectCollection<K> {
    @Override
    public ObjectListIterator<K> iterator();

    @Deprecated
    public ObjectListIterator<K> objectListIterator();

    @Deprecated
    public ObjectListIterator<K> objectListIterator(int var1);

    @Override
    public ObjectListIterator<K> listIterator();

    @Override
    public ObjectListIterator<K> listIterator(int var1);

    @Deprecated
    public ObjectList<K> objectSubList(int var1, int var2);

    @Override
    public ObjectList<K> subList(int var1, int var2);

    public void size(int var1);

    public void getElements(int var1, Object[] var2, int var3, int var4);

    public void removeElements(int var1, int var2);

    public void addElements(int var1, K[] var2);

    public void addElements(int var1, K[] var2, int var3, int var4);
}

