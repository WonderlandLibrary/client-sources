/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.util;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.optifine.Config;

public class EntityUtils {
    private static final Map<EntityType, Integer> mapIdByType = new HashMap<EntityType, Integer>();
    private static final Map<String, Integer> mapIdByLocation = new HashMap<String, Integer>();
    private static final Map<String, Integer> mapIdByName = new HashMap<String, Integer>();

    public static int getEntityIdByClass(Entity entity2) {
        return entity2 == null ? -1 : EntityUtils.getEntityIdByType(entity2.getType());
    }

    public static int getEntityIdByType(EntityType entityType) {
        Integer n = mapIdByType.get(entityType);
        return n == null ? -1 : n;
    }

    public static int getEntityIdByLocation(String string) {
        Integer n = mapIdByLocation.get(string);
        return n == null ? -1 : n;
    }

    public static int getEntityIdByName(String string) {
        Integer n = mapIdByName.get(string = string.toLowerCase(Locale.ROOT));
        return n == null ? -1 : n;
    }

    static {
        for (EntityType entityType : Registry.ENTITY_TYPE) {
            int n = Registry.ENTITY_TYPE.getId(entityType);
            ResourceLocation resourceLocation = Registry.ENTITY_TYPE.getKey(entityType);
            String string = resourceLocation.toString();
            String string2 = resourceLocation.getPath();
            if (mapIdByType.containsKey(entityType)) {
                Config.warn("Duplicate entity type: " + entityType + ", id1: " + mapIdByType.get(entityType) + ", id2: " + n);
            }
            if (mapIdByLocation.containsKey(string)) {
                Config.warn("Duplicate entity location: " + string + ", id1: " + mapIdByLocation.get(string) + ", id2: " + n);
            }
            if (mapIdByName.containsKey(string)) {
                Config.warn("Duplicate entity name: " + string2 + ", id1: " + mapIdByName.get(string2) + ", id2: " + n);
            }
            mapIdByType.put(entityType, n);
            mapIdByLocation.put(string, n);
            mapIdByName.put(string2, n);
        }
    }
}

