/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.floats.FloatCollection;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import it.unimi.dsi.fastutil.objects.Reference2FloatFunction;
import it.unimi.dsi.fastutil.objects.ReferenceSet;
import java.util.Map;

public interface Reference2FloatMap<K>
extends Reference2FloatFunction<K>,
Map<K, Float> {
    @Override
    public ObjectSet<Map.Entry<K, Float>> entrySet();

    public ObjectSet<Entry<K>> reference2FloatEntrySet();

    @Override
    public ReferenceSet<K> keySet();

    public FloatCollection values();

    public boolean containsValue(float var1);

    public static interface Entry<K>
    extends Map.Entry<K, Float> {
        @Override
        @Deprecated
        public Float getValue();

        @Override
        public float setValue(float var1);

        public float getFloatValue();
    }

    public static interface FastEntrySet<K>
    extends ObjectSet<Entry<K>> {
        public ObjectIterator<Entry<K>> fastIterator();
    }
}

