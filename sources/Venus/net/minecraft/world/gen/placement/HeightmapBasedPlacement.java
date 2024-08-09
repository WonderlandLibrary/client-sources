/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.placement;

import com.mojang.serialization.Codec;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.placement.IPlacementConfig;
import net.minecraft.world.gen.placement.Placement;

public abstract class HeightmapBasedPlacement<DC extends IPlacementConfig>
extends Placement<DC> {
    public HeightmapBasedPlacement(Codec<DC> codec) {
        super(codec);
    }

    protected abstract Heightmap.Type func_241858_a(DC var1);
}

