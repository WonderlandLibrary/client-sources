/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.booleans.BooleanCollection;
import it.unimi.dsi.fastutil.ints.Int2BooleanFunction;
import it.unimi.dsi.fastutil.ints.IntSet;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import java.util.Map;

public interface Int2BooleanMap
extends Int2BooleanFunction,
Map<Integer, Boolean> {
    @Override
    public ObjectSet<Map.Entry<Integer, Boolean>> entrySet();

    public ObjectSet<Entry> int2BooleanEntrySet();

    public IntSet keySet();

    public BooleanCollection values();

    public boolean containsValue(boolean var1);

    public static interface Entry
    extends Map.Entry<Integer, Boolean> {
        @Override
        @Deprecated
        public Integer getKey();

        public int getIntKey();

        @Override
        @Deprecated
        public Boolean getValue();

        @Override
        public boolean setValue(boolean var1);

        public boolean getBooleanValue();
    }

    public static interface FastEntrySet
    extends ObjectSet<Entry> {
        public ObjectIterator<Entry> fastIterator();
    }
}

