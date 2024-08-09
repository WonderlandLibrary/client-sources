/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.block_entity_handlers;

import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.providers.BackwardsBlockEntityProvider;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectOpenHashMap;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.IntTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
import com.viaversion.viaversion.util.Pair;

public class FlowerPotHandler
implements BackwardsBlockEntityProvider.BackwardsBlockEntityHandler {
    private static final Int2ObjectMap<Pair<String, Byte>> FLOWERS = new Int2ObjectOpenHashMap<Pair<String, Byte>>(22, 0.99f);
    private static final Pair<String, Byte> AIR = new Pair<String, Byte>("minecraft:air", (byte)0);

    private static void register(int n, String string, byte by) {
        FLOWERS.put(n, new Pair<String, Byte>(string, by));
    }

    public static boolean isFlowah(int n) {
        return n >= 5265 && n <= 5286;
    }

    public Pair<String, Byte> getOrDefault(int n) {
        Pair<String, Byte> pair = (Pair<String, Byte>)FLOWERS.get(n);
        return pair != null ? pair : AIR;
    }

    @Override
    public CompoundTag transform(UserConnection userConnection, int n, CompoundTag compoundTag) {
        Pair<String, Byte> pair = this.getOrDefault(n);
        compoundTag.put("Item", new StringTag(pair.key()));
        compoundTag.put("Data", new IntTag(pair.value().byteValue()));
        return compoundTag;
    }

    static {
        FLOWERS.put(5265, AIR);
        FlowerPotHandler.register(5266, "minecraft:sapling", (byte)0);
        FlowerPotHandler.register(5267, "minecraft:sapling", (byte)1);
        FlowerPotHandler.register(5268, "minecraft:sapling", (byte)2);
        FlowerPotHandler.register(5269, "minecraft:sapling", (byte)3);
        FlowerPotHandler.register(5270, "minecraft:sapling", (byte)4);
        FlowerPotHandler.register(5271, "minecraft:sapling", (byte)5);
        FlowerPotHandler.register(5272, "minecraft:tallgrass", (byte)2);
        FlowerPotHandler.register(5273, "minecraft:yellow_flower", (byte)0);
        FlowerPotHandler.register(5274, "minecraft:red_flower", (byte)0);
        FlowerPotHandler.register(5275, "minecraft:red_flower", (byte)1);
        FlowerPotHandler.register(5276, "minecraft:red_flower", (byte)2);
        FlowerPotHandler.register(5277, "minecraft:red_flower", (byte)3);
        FlowerPotHandler.register(5278, "minecraft:red_flower", (byte)4);
        FlowerPotHandler.register(5279, "minecraft:red_flower", (byte)5);
        FlowerPotHandler.register(5280, "minecraft:red_flower", (byte)6);
        FlowerPotHandler.register(5281, "minecraft:red_flower", (byte)7);
        FlowerPotHandler.register(5282, "minecraft:red_flower", (byte)8);
        FlowerPotHandler.register(5283, "minecraft:red_mushroom", (byte)0);
        FlowerPotHandler.register(5284, "minecraft:brown_mushroom", (byte)0);
        FlowerPotHandler.register(5285, "minecraft:deadbush", (byte)0);
        FlowerPotHandler.register(5286, "minecraft:cactus", (byte)0);
    }
}

