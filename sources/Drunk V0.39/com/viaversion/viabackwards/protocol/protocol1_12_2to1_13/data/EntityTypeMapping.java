/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.data;

import com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap;
import com.viaversion.viaversion.libs.fastutil.ints.Int2IntOpenHashMap;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data.EntityTypeRewriter;
import java.lang.reflect.Field;
import java.util.Iterator;

public class EntityTypeMapping {
    private static final Int2IntMap TYPES = new Int2IntOpenHashMap();

    public static int getOldId(int type1_13) {
        return TYPES.get(type1_13);
    }

    static {
        TYPES.defaultReturnValue(-1);
        try {
            Field field = EntityTypeRewriter.class.getDeclaredField("ENTITY_TYPES");
            field.setAccessible(true);
            Int2IntMap entityTypes = (Int2IntMap)field.get(null);
            Iterator iterator = entityTypes.int2IntEntrySet().iterator();
            while (iterator.hasNext()) {
                Int2IntMap.Entry entry = (Int2IntMap.Entry)iterator.next();
                TYPES.put(entry.getIntValue(), entry.getIntKey());
            }
            return;
        }
        catch (IllegalAccessException | NoSuchFieldException ex) {
            ex.printStackTrace();
        }
    }
}

