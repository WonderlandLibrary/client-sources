package net.minecraft.block.state;

import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IBlockBehaviors
{
    boolean func_189547_a(World p_189547_1_, BlockPos p_189547_2_, int p_189547_3_, int p_189547_4_);

    void func_189546_a(World p_189546_1_, BlockPos p_189546_2_, Block p_189546_3_);
}
