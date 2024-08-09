/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.color;

import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockDisplayReader;

public interface IBlockColor {
    public int getColor(BlockState var1, @Nullable IBlockDisplayReader var2, @Nullable BlockPos var3, int var4);
}

