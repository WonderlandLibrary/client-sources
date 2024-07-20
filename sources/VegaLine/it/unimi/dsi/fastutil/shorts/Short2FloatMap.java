/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.floats.FloatCollection;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import it.unimi.dsi.fastutil.shorts.Short2FloatFunction;
import it.unimi.dsi.fastutil.shorts.ShortSet;
import java.util.Map;

public interface Short2FloatMap
extends Short2FloatFunction,
Map<Short, Float> {
    @Override
    public ObjectSet<Map.Entry<Short, Float>> entrySet();

    public ObjectSet<Entry> short2FloatEntrySet();

    public ShortSet keySet();

    public FloatCollection values();

    public boolean containsValue(float var1);

    public static interface Entry
    extends Map.Entry<Short, Float> {
        @Override
        @Deprecated
        public Short getKey();

        public short getShortKey();

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

