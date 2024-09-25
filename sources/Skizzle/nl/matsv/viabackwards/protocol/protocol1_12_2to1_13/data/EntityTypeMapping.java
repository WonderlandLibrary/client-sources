/*
 * Decompiled with CFR 0.150.
 */
package nl.matsv.viabackwards.protocol.protocol1_12_2to1_13.data;

import java.lang.reflect.Field;
import us.myles.ViaVersion.protocols.protocol1_13to1_12_2.data.EntityTypeRewriter;
import us.myles.viaversion.libs.fastutil.ints.Int2IntMap;
import us.myles.viaversion.libs.fastutil.ints.Int2IntOpenHashMap;

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
            for (Int2IntMap.Entry entry : entityTypes.int2IntEntrySet()) {
                TYPES.put(entry.getIntValue(), entry.getIntKey());
            }
        }
        catch (IllegalAccessException | NoSuchFieldException ex) {
            ex.printStackTrace();
        }
    }
}

