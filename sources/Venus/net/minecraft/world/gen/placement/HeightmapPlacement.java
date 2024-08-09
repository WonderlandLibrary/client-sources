/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.placement;

import com.mojang.serialization.Codec;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.placement.IPlacementConfig;
import net.minecraft.world.gen.placement.SimpleHeightmapBasedPlacement;

public class HeightmapPlacement<DC extends IPlacementConfig>
extends SimpleHeightmapBasedPlacement<DC> {
    public HeightmapPlacement(Codec<DC> codec) {
        super(codec);
    }

    @Override
    protected Heightmap.Type func_241858_a(DC DC) {
        return Heightmap.Type.MOTION_BLOCKING;
    }
}

