package dev.luvbeeq.baritone.api.cache;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;

/**
 * @author Brady
 * @since 8/4/2018
 */
public interface IBlockTypeAccess {

    BlockState getBlock(int x, int y, int z);

    default BlockState getBlock(BlockPos pos) {
        return getBlock(pos.getX(), pos.getY(), pos.getZ());
    }
}
