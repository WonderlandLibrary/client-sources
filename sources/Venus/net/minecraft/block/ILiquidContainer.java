/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.block;

import net.minecraft.block.BlockState;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;

public interface ILiquidContainer {
    public boolean canContainFluid(IBlockReader var1, BlockPos var2, BlockState var3, Fluid var4);

    public boolean receiveFluid(IWorld var1, BlockPos var2, BlockState var3, FluidState var4);
}

