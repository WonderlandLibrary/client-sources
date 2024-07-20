/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.booleans;

import it.unimi.dsi.fastutil.booleans.BooleanIterable;
import it.unimi.dsi.fastutil.booleans.BooleanIterator;
import java.util.Collection;

public interface BooleanCollection
extends Collection<Boolean>,
BooleanIterable {
    @Override
    public BooleanIterator iterator();

    @Deprecated
    public BooleanIterator booleanIterator();

    @Override
    public <T> T[] toArray(T[] var1);

    public boolean contains(boolean var1);

    public boolean[] toBooleanArray();

    public boolean[] toBooleanArray(boolean[] var1);

    public boolean[] toArray(boolean[] var1);

    @Override
    public boolean add(boolean var1);

    public boolean rem(boolean var1);

    public boolean addAll(BooleanCollection var1);

    public boolean containsAll(BooleanCollection var1);

    public boolean removeAll(BooleanCollection var1);

    public boolean retainAll(BooleanCollection var1);
}

