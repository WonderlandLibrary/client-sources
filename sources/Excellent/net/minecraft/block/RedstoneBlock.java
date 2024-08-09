package net.minecraft.block;

import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

public class RedstoneBlock extends Block {
    public RedstoneBlock(AbstractBlock.Properties properties) {
        super(properties);
    }

    /**
     * Can this block provide power. Only wire currently seems to have this change based on its state.
     * call via {@link IBlockState#canProvidePower()} whenever possible. Implementing/overriding is fine.
     */
    public boolean canProvidePower(BlockState state) {
        return true;
    }

    /**
     * call via {@link IBlockState#getWeakPower(IBlockAccess, BlockPos, EnumFacing)} whenever possible.
     * Implementing/overriding is fine.
     */
    public int getWeakPower(BlockState blockState, IBlockReader blockAccess, BlockPos pos, Direction side) {
        return 15;
    }
}
