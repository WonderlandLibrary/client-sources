/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.placement;

import com.mojang.serialization.Codec;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.placement.IPlacementConfig;
import net.minecraft.world.gen.placement.NoPlacementConfig;
import net.minecraft.world.gen.placement.SimpleHeightmapBasedPlacement;

public class TopSolidOnce
extends SimpleHeightmapBasedPlacement<NoPlacementConfig> {
    public TopSolidOnce(Codec<NoPlacementConfig> codec) {
        super(codec);
    }

    @Override
    protected Heightmap.Type func_241858_a(NoPlacementConfig noPlacementConfig) {
        return Heightmap.Type.OCEAN_FLOOR_WG;
    }

    @Override
    protected Heightmap.Type func_241858_a(IPlacementConfig iPlacementConfig) {
        return this.func_241858_a((NoPlacementConfig)iPlacementConfig);
    }
}

