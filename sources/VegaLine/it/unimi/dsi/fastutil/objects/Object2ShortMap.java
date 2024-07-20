/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.objects.Object2ShortFunction;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import it.unimi.dsi.fastutil.shorts.ShortCollection;
import java.util.Map;

public interface Object2ShortMap<K>
extends Object2ShortFunction<K>,
Map<K, Short> {
    @Override
    public ObjectSet<Map.Entry<K, Short>> entrySet();

    public ObjectSet<Entry<K>> object2ShortEntrySet();

    @Override
    public ObjectSet<K> keySet();

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

