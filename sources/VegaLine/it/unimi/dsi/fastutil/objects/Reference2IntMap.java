/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.ints.IntCollection;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import it.unimi.dsi.fastutil.objects.Reference2IntFunction;
import it.unimi.dsi.fastutil.objects.ReferenceSet;
import java.util.Map;

public interface Reference2IntMap<K>
extends Reference2IntFunction<K>,
Map<K, Integer> {
    @Override
    public ObjectSet<Map.Entry<K, Integer>> entrySet();

    public ObjectSet<Entry<K>> reference2IntEntrySet();

    @Override
    public ReferenceSet<K> keySet();

    public IntCollection values();

    public boolean containsValue(int var1);

    public static interface Entry<K>
    extends Map.Entry<K, Integer> {
        @Override
        @Deprecated
        public Integer getValue();

        @Override
        public int setValue(int var1);

        public int getIntValue();
    }

    public static interface FastEntrySet<K>
    extends ObjectSet<Entry<K>> {
        public ObjectIterator<Entry<K>> fastIterator();
    }
}

