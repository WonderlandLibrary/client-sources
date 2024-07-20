/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.objects.ObjectListIterator;
import it.unimi.dsi.fastutil.objects.ReferenceCollection;
import java.util.List;

public interface ReferenceList<K>
extends List<K>,
ReferenceCollection<K> {
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
    public ReferenceList<K> referenceSubList(int var1, int var2);

    @Override
    public ReferenceList<K> subList(int var1, int var2);

    public void size(int var1);

    public void getElements(int var1, Object[] var2, int var3, int var4);

    public void removeElements(int var1, int var2);

    public void addElements(int var1, K[] var2);

    public void addElements(int var1, K[] var2, int var3, int var4);
}

