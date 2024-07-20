/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.chars.CharCollection;
import it.unimi.dsi.fastutil.floats.Float2CharFunction;
import it.unimi.dsi.fastutil.floats.FloatSet;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import java.util.Map;

public interface Float2CharMap
extends Float2CharFunction,
Map<Float, Character> {
    @Override
    public ObjectSet<Map.Entry<Float, Character>> entrySet();

    public ObjectSet<Entry> float2CharEntrySet();

    public FloatSet keySet();

    public CharCollection values();

    public boolean containsValue(char var1);

    public static interface Entry
    extends Map.Entry<Float, Character> {
        @Override
        @Deprecated
        public Float getKey();

        public float getFloatKey();

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

