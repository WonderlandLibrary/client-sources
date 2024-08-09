/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.data;

import com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap;
import com.viaversion.viaversion.libs.fastutil.ints.Int2IntOpenHashMap;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data.EntityTypeRewriter;
import java.lang.reflect.Field;

public class EntityTypeMapping {
    private static final Int2IntMap TYPES = new Int2IntOpenHashMap();

    public static int getOldId(int n) {
        return TYPES.get(n);
    }

    static {
        TYPES.defaultReturnValue(-1);
        try {
            Field field = EntityTypeRewriter.class.getDeclaredField("ENTITY_TYPES");
            field.setAccessible(false);
            Int2IntMap int2IntMap = (Int2IntMap)field.get(null);
            for (Int2IntMap.Entry entry : int2IntMap.int2IntEntrySet()) {
                TYPES.put(entry.getIntValue(), entry.getIntKey());
            }
        } catch (IllegalAccessException | NoSuchFieldException reflectiveOperationException) {
            reflectiveOperationException.printStackTrace();
        }
    }
}

