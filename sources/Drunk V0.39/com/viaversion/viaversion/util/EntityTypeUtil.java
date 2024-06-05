/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.util;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import java.util.ArrayList;
import java.util.Comparator;

public class EntityTypeUtil {
    public static EntityType[] toOrderedArray(EntityType[] values) {
        ArrayList<EntityType> types = new ArrayList<EntityType>();
        EntityType[] entityTypeArray = values;
        int n = entityTypeArray.length;
        int n2 = 0;
        while (true) {
            if (n2 >= n) {
                types.sort(Comparator.comparingInt(EntityType::getId));
                return types.toArray(new EntityType[0]);
            }
            EntityType type = entityTypeArray[n2];
            if (type.getId() != -1) {
                types.add(type);
            }
            ++n2;
        }
    }

    public static EntityType getTypeFromId(EntityType[] values, int typeId, EntityType fallback) {
        if (typeId >= 0 && typeId < values.length) {
            EntityType type = values[typeId];
            if (type != null) return type;
        }
        Via.getPlatform().getLogger().severe("Could not find " + fallback.getClass().getSimpleName() + " type id " + typeId);
        return fallback;
    }
}

