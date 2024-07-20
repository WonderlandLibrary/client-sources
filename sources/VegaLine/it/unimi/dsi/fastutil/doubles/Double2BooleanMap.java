/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.booleans.BooleanCollection;
import it.unimi.dsi.fastutil.doubles.Double2BooleanFunction;
import it.unimi.dsi.fastutil.doubles.DoubleSet;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import java.util.Map;

public interface Double2BooleanMap
extends Double2BooleanFunction,
Map<Double, Boolean> {
    @Override
    public ObjectSet<Map.Entry<Double, Boolean>> entrySet();

    public ObjectSet<Entry> double2BooleanEntrySet();

    public DoubleSet keySet();

    public BooleanCollection values();

    public boolean containsValue(boolean var1);

    public static interface Entry
    extends Map.Entry<Double, Boolean> {
        @Override
        @Deprecated
        public Double getKey();

        public double getDoubleKey();

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

