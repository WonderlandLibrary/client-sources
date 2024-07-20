/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.objects.ObjectCollection;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import it.unimi.dsi.fastutil.shorts.Short2ObjectFunction;
import it.unimi.dsi.fastutil.shorts.ShortSet;
import java.util.Map;

public interface Short2ObjectMap<V>
extends Short2ObjectFunction<V>,
Map<Short, V> {
    @Override
    public ObjectSet<Map.Entry<Short, V>> entrySet();

    public ObjectSet<Entry<V>> short2ObjectEntrySet();

    public ShortSet keySet();

    @Override
    public ObjectCollection<V> values();

    public static interface Entry<V>
    extends Map.Entry<Short, V> {
        @Override
        @Deprecated
        public Short getKey();

        public short getShortKey();
    }

    public static interface FastEntrySet<V>
    extends ObjectSet<Entry<V>> {
        public ObjectIterator<Entry<V>> fastIterator();
    }
}

