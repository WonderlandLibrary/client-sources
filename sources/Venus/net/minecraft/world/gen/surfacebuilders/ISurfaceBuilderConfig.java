/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.surfacebuilders;

import net.minecraft.block.BlockState;

public interface ISurfaceBuilderConfig {
    public BlockState getTop();

    public BlockState getUnder();
}

