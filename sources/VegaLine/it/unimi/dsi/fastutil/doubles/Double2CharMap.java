/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.chars.CharCollection;
import it.unimi.dsi.fastutil.doubles.Double2CharFunction;
import it.unimi.dsi.fastutil.doubles.DoubleSet;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import java.util.Map;

public interface Double2CharMap
extends Double2CharFunction,
Map<Double, Character> {
    @Override
    public ObjectSet<Map.Entry<Double, Character>> entrySet();

    public ObjectSet<Entry> double2CharEntrySet();

    public DoubleSet keySet();

    public CharCollection values();

    public boolean containsValue(char var1);

    public static interface Entry
    extends Map.Entry<Double, Character> {
        @Override
        @Deprecated
        public Double getKey();

        public double getDoubleKey();

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

