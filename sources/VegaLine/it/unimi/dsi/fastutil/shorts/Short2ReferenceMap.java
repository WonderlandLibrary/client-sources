/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import it.unimi.dsi.fastutil.objects.ReferenceCollection;
import it.unimi.dsi.fastutil.shorts.Short2ReferenceFunction;
import it.unimi.dsi.fastutil.shorts.ShortSet;
import java.util.Map;

public interface Short2ReferenceMap<V>
extends Short2ReferenceFunction<V>,
Map<Short, V> {
    @Override
    public ObjectSet<Map.Entry<Short, V>> entrySet();

    public ObjectSet<Entry<V>> short2ReferenceEntrySet();

    public ShortSet keySet();

    @Override
    public ReferenceCollection<V> values();

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

