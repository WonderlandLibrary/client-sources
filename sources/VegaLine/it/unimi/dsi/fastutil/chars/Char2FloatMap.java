/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.chars.Char2FloatFunction;
import it.unimi.dsi.fastutil.chars.CharSet;
import it.unimi.dsi.fastutil.floats.FloatCollection;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import java.util.Map;

public interface Char2FloatMap
extends Char2FloatFunction,
Map<Character, Float> {
    @Override
    public ObjectSet<Map.Entry<Character, Float>> entrySet();

    public ObjectSet<Entry> char2FloatEntrySet();

    public CharSet keySet();

    public FloatCollection values();

    public boolean containsValue(float var1);

    public static interface Entry
    extends Map.Entry<Character, Float> {
        @Override
        @Deprecated
        public Character getKey();

        public char getCharKey();

        @Override
        @Deprecated
        public Float getValue();

        @Override
        public float setValue(float var1);

        public float getFloatValue();
    }

    public static interface FastEntrySet
    extends ObjectSet<Entry> {
        public ObjectIterator<Entry> fastIterator();
    }
}

