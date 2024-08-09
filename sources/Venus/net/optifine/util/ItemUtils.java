/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.util;

import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

public class ItemUtils {
    public static Item getItem(ResourceLocation resourceLocation) {
        return !Registry.ITEM.containsKey(resourceLocation) ? null : Registry.ITEM.getOrDefault(resourceLocation);
    }

    public static int getId(Item item) {
        return Registry.ITEM.getId(item);
    }
}

