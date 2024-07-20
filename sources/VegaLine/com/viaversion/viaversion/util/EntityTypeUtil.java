/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.util;

import com.google.common.base.Preconditions;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.protocol.Protocol;
import java.util.ArrayList;
import java.util.Comparator;

public final class EntityTypeUtil {
    private static final EntityType[] EMPTY_ARRAY = new EntityType[0];

    public static EntityType[] toOrderedArray(EntityType[] values) {
        ArrayList<EntityType> types = new ArrayList<EntityType>();
        for (EntityType type2 : values) {
            if (type2.getId() == -1) continue;
            types.add(type2);
        }
        types.sort(Comparator.comparingInt(EntityType::getId));
        return types.toArray(EMPTY_ARRAY);
    }

    public static <T extends EntityType> void initialize(T[] values, EntityType[] typesToFill, Protocol<?, ?, ?, ?> protocol, EntityIdSetter<T> idSetter) {
        for (T type2 : values) {
            if (type2.isAbstractType()) continue;
            int id = protocol.getMappingData().getEntityMappings().mappedId(type2.identifier());
            Preconditions.checkArgument(id != -1, "Entity type %s has no id", new Object[]{type2.identifier()});
            idSetter.setId(type2, id);
            typesToFill[id] = type2;
        }
    }

    public static EntityType[] createSizedArray(EntityType[] values) {
        int count = 0;
        for (EntityType type2 : values) {
            if (type2.isAbstractType()) continue;
            ++count;
        }
        return new EntityType[count];
    }

    public static EntityType getTypeFromId(EntityType[] values, int typeId, EntityType fallback) {
        EntityType type2;
        if (typeId < 0 || typeId >= values.length || (type2 = values[typeId]) == null) {
            Via.getPlatform().getLogger().severe("Could not find " + fallback.getClass().getSimpleName() + " type id " + typeId);
            return fallback;
        }
        return type2;
    }

    @FunctionalInterface
    public static interface EntityIdSetter<T extends EntityType> {
        public void setId(T var1, int var2);
    }
}

