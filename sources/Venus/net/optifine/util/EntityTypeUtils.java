/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.util;

import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

public class EntityTypeUtils {
    public static EntityType getEntityType(ResourceLocation resourceLocation) {
        return !Registry.ENTITY_TYPE.containsKey(resourceLocation) ? null : Registry.ENTITY_TYPE.getOrDefault(resourceLocation);
    }
}

