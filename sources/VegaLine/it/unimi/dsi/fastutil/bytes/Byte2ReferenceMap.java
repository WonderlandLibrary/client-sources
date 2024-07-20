/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.bytes.Byte2ReferenceFunction;
import it.unimi.dsi.fastutil.bytes.ByteSet;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import it.unimi.dsi.fastutil.objects.ReferenceCollection;
import java.util.Map;

public interface Byte2ReferenceMap<V>
extends Byte2ReferenceFunction<V>,
Map<Byte, V> {
    @Override
    public ObjectSet<Map.Entry<Byte, V>> entrySet();

    public ObjectSet<Entry<V>> byte2ReferenceEntrySet();

    public ByteSet keySet();

    @Override
    public ReferenceCollection<V> values();

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

