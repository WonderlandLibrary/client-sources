/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen;

import com.mojang.datafixers.kinds.App;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.registry.Registry;

public class FlatLayerInfo {
    public static final Codec<FlatLayerInfo> field_236929_a_ = RecordCodecBuilder.create(FlatLayerInfo::lambda$static$1);
    private final BlockState layerMaterial;
    private final int layerCount;
    private int layerMinimumY;

    public FlatLayerInfo(int n, Block block) {
        this.layerCount = n;
        this.layerMaterial = block.getDefaultState();
    }

    public int getLayerCount() {
        return this.layerCount;
    }

    public BlockState getLayerMaterial() {
        return this.layerMaterial;
    }

    public int getMinY() {
        return this.layerMinimumY;
    }

    public void setMinY(int n) {
        this.layerMinimumY = n;
    }

    public String toString() {
        return (String)(this.layerCount != 1 ? this.layerCount + "*" : "") + Registry.BLOCK.getKey(this.layerMaterial.getBlock());
    }

    private static App lambda$static$1(RecordCodecBuilder.Instance instance) {
        return instance.group(((MapCodec)Codec.intRange(0, 256).fieldOf("height")).forGetter(FlatLayerInfo::getLayerCount), ((MapCodec)Registry.BLOCK.fieldOf("block")).orElse(Blocks.AIR).forGetter(FlatLayerInfo::lambda$static$0)).apply(instance, FlatLayerInfo::new);
    }

    private static Block lambda$static$0(FlatLayerInfo flatLayerInfo) {
        return flatLayerInfo.getLayerMaterial().getBlock();
    }
}

