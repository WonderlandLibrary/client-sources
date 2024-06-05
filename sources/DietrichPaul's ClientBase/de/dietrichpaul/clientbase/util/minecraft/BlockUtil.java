package de.dietrichpaul.clientbase.util.minecraft;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.BlockPos;

public class BlockUtil {

    private static final MinecraftClient CLIENT = MinecraftClient.getInstance();

    public static ClientWorld getWorld() {
        return CLIENT.world;
    }

    public static BlockState getBlockState(BlockPos pos) {
        return getWorld().getBlockState(pos);
    }

    public static Block getBlock(BlockPos pos) {
        return getBlock(pos).getDefaultState().getBlock();
    }

}
