/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.bytes.Byte2CharFunction;
import it.unimi.dsi.fastutil.bytes.ByteSet;
import it.unimi.dsi.fastutil.chars.CharCollection;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import java.util.Map;

public interface Byte2CharMap
extends Byte2CharFunction,
Map<Byte, Character> {
    @Override
    public ObjectSet<Map.Entry<Byte, Character>> entrySet();

    public ObjectSet<Entry> byte2CharEntrySet();

    public ByteSet keySet();

    public CharCollection values();

    public boolean containsValue(char var1);

    public static interface Entry
    extends Map.Entry<Byte, Character> {
        @Override
        @Deprecated
        public Byte getKey();

        public byte getByteKey();

        @Override
        @Deprecated
        public Character getValue();

        @Override
        public char setValue(char var1);

        public char getCharValue();
    }

    public static interface FastEntrySet
    extends ObjectSet<Entry> {
        public ObjectIterator<Entry> fastIterator();
    }
}

