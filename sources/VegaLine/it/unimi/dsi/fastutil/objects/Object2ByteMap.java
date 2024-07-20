/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.bytes.ByteCollection;
import it.unimi.dsi.fastutil.objects.Object2ByteFunction;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import java.util.Map;

public interface Object2ByteMap<K>
extends Object2ByteFunction<K>,
Map<K, Byte> {
    @Override
    public ObjectSet<Map.Entry<K, Byte>> entrySet();

    public ObjectSet<Entry<K>> object2ByteEntrySet();

    @Override
    public ObjectSet<K> keySet();

    public ByteCollection values();

    public boolean containsValue(byte var1);

    public static interface Entry<K>
    extends Map.Entry<K, Byte> {
        @Override
        @Deprecated
        public Byte getValue();

        @Override
        public byte setValue(byte var1);

        public byte getByteValue();
    }

    public static interface FastEntrySet<K>
    extends ObjectSet<Entry<K>> {
        public ObjectIterator<Entry<K>> fastIterator();
    }
}

