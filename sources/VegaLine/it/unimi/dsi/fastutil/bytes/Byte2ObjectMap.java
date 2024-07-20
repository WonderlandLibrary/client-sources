/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.bytes.Byte2ObjectFunction;
import it.unimi.dsi.fastutil.bytes.ByteSet;
import it.unimi.dsi.fastutil.objects.ObjectCollection;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import java.util.Map;

public interface Byte2ObjectMap<V>
extends Byte2ObjectFunction<V>,
Map<Byte, V> {
    @Override
    public ObjectSet<Map.Entry<Byte, V>> entrySet();

    public ObjectSet<Entry<V>> byte2ObjectEntrySet();

    public ByteSet keySet();

    @Override
    public ObjectCollection<V> values();

    public static interface Entry<V>
    extends Map.Entry<Byte, V> {
        @Override
        @Deprecated
        public Byte getKey();

        public byte getByteKey();
    }

    public static interface FastEntrySet<V>
    extends ObjectSet<Entry<V>> {
        public ObjectIterator<Entry<V>> fastIterator();
    }
}

