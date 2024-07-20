/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.booleans.BooleanCollection;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import it.unimi.dsi.fastutil.shorts.Short2BooleanFunction;
import it.unimi.dsi.fastutil.shorts.ShortSet;
import java.util.Map;

public interface Short2BooleanMap
extends Short2BooleanFunction,
Map<Short, Boolean> {
    @Override
    public ObjectSet<Map.Entry<Short, Boolean>> entrySet();

    public ObjectSet<Entry> short2BooleanEntrySet();

    public ShortSet keySet();

    public BooleanCollection values();

    public boolean containsValue(boolean var1);

    public static interface Entry
    extends Map.Entry<Short, Boolean> {
        @Override
        @Deprecated
        public Short getKey();

        public short getShortKey();

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

