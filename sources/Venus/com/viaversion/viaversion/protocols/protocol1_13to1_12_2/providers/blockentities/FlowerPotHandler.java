/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.providers.blockentities;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.providers.BlockEntityProvider;
import com.viaversion.viaversion.util.Pair;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class FlowerPotHandler
implements BlockEntityProvider.BlockEntityHandler {
    private static final Map<Pair<?, Byte>, Integer> flowers = new ConcurrentHashMap();

    public static void register(String string, byte by, byte by2, int n) {
        flowers.put(new Pair<String, Byte>(string, by2), n);
        flowers.put(new Pair<Byte, Byte>(by, by2), n);
    }

    @Override
    public int transform(UserConnection userConnection, CompoundTag compoundTag) {
        Object object;
        Object object2 = compoundTag.contains("Item") ? ((Tag)compoundTag.get("Item")).getValue() : null;
        Object object3 = object = compoundTag.contains("Data") ? ((Tag)compoundTag.get("Data")).getValue() : null;
        Integer n = flowers.get(new Pair<Object, Byte>(object2 = object2 instanceof String ? ((String)object2).replace("minecraft:", "") : (object2 instanceof Number ? Byte.valueOf(((Number)object2).byteValue()) : Byte.valueOf((byte)0)), (Byte)(object = object instanceof Number ? Byte.valueOf(((Number)object).byteValue()) : Byte.valueOf((byte)0))));
        if (n != null) {
            return n;
        }
        n = flowers.get(new Pair<Object, Byte>(object2, (byte)0));
        if (n != null) {
            return n;
        }
        return 0;
    }

    static {
        FlowerPotHandler.register("air", (byte)0, (byte)0, 5265);
        FlowerPotHandler.register("sapling", (byte)6, (byte)0, 5266);
        FlowerPotHandler.register("sapling", (byte)6, (byte)1, 5267);
        FlowerPotHandler.register("sapling", (byte)6, (byte)2, 5268);
        FlowerPotHandler.register("sapling", (byte)6, (byte)3, 5269);
        FlowerPotHandler.register("sapling", (byte)6, (byte)4, 5270);
        FlowerPotHandler.register("sapling", (byte)6, (byte)5, 5271);
        FlowerPotHandler.register("tallgrass", (byte)31, (byte)2, 5272);
        FlowerPotHandler.register("yellow_flower", (byte)37, (byte)0, 5273);
        FlowerPotHandler.register("red_flower", (byte)38, (byte)0, 5274);
        FlowerPotHandler.register("red_flower", (byte)38, (byte)1, 5275);
        FlowerPotHandler.register("red_flower", (byte)38, (byte)2, 5276);
        FlowerPotHandler.register("red_flower", (byte)38, (byte)3, 5277);
        FlowerPotHandler.register("red_flower", (byte)38, (byte)4, 5278);
        FlowerPotHandler.register("red_flower", (byte)38, (byte)5, 5279);
        FlowerPotHandler.register("red_flower", (byte)38, (byte)6, 5280);
        FlowerPotHandler.register("red_flower", (byte)38, (byte)7, 5281);
        FlowerPotHandler.register("red_flower", (byte)38, (byte)8, 5282);
        FlowerPotHandler.register("red_mushroom", (byte)40, (byte)0, 5283);
        FlowerPotHandler.register("brown_mushroom", (byte)39, (byte)0, 5284);
        FlowerPotHandler.register("deadbush", (byte)32, (byte)0, 5285);
        FlowerPotHandler.register("cactus", (byte)81, (byte)0, 5286);
    }
}

