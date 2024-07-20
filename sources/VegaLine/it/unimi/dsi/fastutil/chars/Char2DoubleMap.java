/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.chars.Char2DoubleFunction;
import it.unimi.dsi.fastutil.chars.CharSet;
import it.unimi.dsi.fastutil.doubles.DoubleCollection;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import java.util.Map;

public interface Char2DoubleMap
extends Char2DoubleFunction,
Map<Character, Double> {
    @Override
    public ObjectSet<Map.Entry<Character, Double>> entrySet();

    public ObjectSet<Entry> char2DoubleEntrySet();

    public CharSet keySet();

    public DoubleCollection values();

    public boolean containsValue(double var1);

    public static interface Entry
    extends Map.Entry<Character, Double> {
        @Override
        @Deprecated
        public Character getKey();

        public char getCharKey();

        @Override
        @Deprecated
        public Double getValue();

        @Override
        public double setValue(double var1);

        public double getDoubleValue();
    }

    public static interface FastEntrySet
    extends ObjectSet<Entry> {
        public ObjectIterator<Entry> fastIterator();
    }
}

