package club.mega.util;

import club.mega.interfaces.MinecraftInterface;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;

public final class WorldUtil implements MinecraftInterface {

    public static IBlockState getBlockState(final BlockPos pos) {
        return MC.theWorld.getBlockState(pos);
    }

    public static Block getBlock(final BlockPos pos) {
        return getBlockState(pos).getBlock();
    }

}
