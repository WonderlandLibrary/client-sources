/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.data;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

public class ModelsResourceUtil {
    @Deprecated
    public static ResourceLocation func_240223_a_(String string) {
        return new ResourceLocation("minecraft", "block/" + string);
    }

    public static ResourceLocation func_240224_b_(String string) {
        return new ResourceLocation("minecraft", "item/" + string);
    }

    public static ResourceLocation func_240222_a_(Block block, String string) {
        ResourceLocation resourceLocation = Registry.BLOCK.getKey(block);
        return new ResourceLocation(resourceLocation.getNamespace(), "block/" + resourceLocation.getPath() + string);
    }

    public static ResourceLocation func_240221_a_(Block block) {
        ResourceLocation resourceLocation = Registry.BLOCK.getKey(block);
        return new ResourceLocation(resourceLocation.getNamespace(), "block/" + resourceLocation.getPath());
    }

    public static ResourceLocation func_240219_a_(Item item) {
        ResourceLocation resourceLocation = Registry.ITEM.getKey(item);
        return new ResourceLocation(resourceLocation.getNamespace(), "item/" + resourceLocation.getPath());
    }

    public static ResourceLocation func_240220_a_(Item item, String string) {
        ResourceLocation resourceLocation = Registry.ITEM.getKey(item);
        return new ResourceLocation(resourceLocation.getNamespace(), "item/" + resourceLocation.getPath() + string);
    }
}

