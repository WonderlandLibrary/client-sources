/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.data;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import net.minecraft.data.BlockModeInfo;
import net.minecraft.util.ResourceLocation;

public class BlockModelFields {
    public static final BlockModeInfo<Rotation> field_240200_a_ = new BlockModeInfo<Rotation>("x", BlockModelFields::lambda$static$0);
    public static final BlockModeInfo<Rotation> field_240201_b_ = new BlockModeInfo<Rotation>("y", BlockModelFields::lambda$static$1);
    public static final BlockModeInfo<ResourceLocation> field_240202_c_ = new BlockModeInfo<ResourceLocation>("model", BlockModelFields::lambda$static$2);
    public static final BlockModeInfo<Boolean> field_240203_d_ = new BlockModeInfo<Boolean>("uvlock", JsonPrimitive::new);
    public static final BlockModeInfo<Integer> field_240204_e_ = new BlockModeInfo<Integer>("weight", JsonPrimitive::new);

    private static JsonElement lambda$static$2(ResourceLocation resourceLocation) {
        return new JsonPrimitive(resourceLocation.toString());
    }

    private static JsonElement lambda$static$1(Rotation rotation) {
        return new JsonPrimitive(rotation.field_240208_e_);
    }

    private static JsonElement lambda$static$0(Rotation rotation) {
        return new JsonPrimitive(rotation.field_240208_e_);
    }

    public static enum Rotation {
        R0(0),
        R90(90),
        R180(180),
        R270(270);

        private final int field_240208_e_;

        private Rotation(int n2) {
            this.field_240208_e_ = n2;
        }
    }
}

