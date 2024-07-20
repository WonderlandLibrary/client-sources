/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.booleans.BooleanCollection;
import it.unimi.dsi.fastutil.bytes.Byte2BooleanFunction;
import it.unimi.dsi.fastutil.bytes.ByteSet;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import java.util.Map;

public interface Byte2BooleanMap
extends Byte2BooleanFunction,
Map<Byte, Boolean> {
    @Override
    public ObjectSet<Map.Entry<Byte, Boolean>> entrySet();

    public ObjectSet<Entry> byte2BooleanEntrySet();

    public ByteSet keySet();

    public BooleanCollection values();

    public boolean containsValue(boolean var1);

    public static interface Entry
    extends Map.Entry<Byte, Boolean> {
        @Override
        @Deprecated
        public Byte getKey();

        public byte getByteKey();

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

