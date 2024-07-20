/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import it.unimi.dsi.fastutil.objects.Reference2ShortFunction;
import it.unimi.dsi.fastutil.objects.ReferenceSet;
import it.unimi.dsi.fastutil.shorts.ShortCollection;
import java.util.Map;

public interface Reference2ShortMap<K>
extends Reference2ShortFunction<K>,
Map<K, Short> {
    @Override
    public ObjectSet<Map.Entry<K, Short>> entrySet();

    public ObjectSet<Entry<K>> reference2ShortEntrySet();

    @Override
    public ReferenceSet<K> keySet();

    public ShortCollection values();

    public boolean containsValue(short var1);

    public static interface Entry<K>
    extends Map.Entry<K, Short> {
        @Override
        @Deprecated
        public Short getValue();

        @Override
        public short setValue(short var1);

        public short getShortValue();
    }

    public static interface FastEntrySet<K>
    extends ObjectSet<Entry<K>> {
        public ObjectIterator<Entry<K>> fastIterator();
    }
}

