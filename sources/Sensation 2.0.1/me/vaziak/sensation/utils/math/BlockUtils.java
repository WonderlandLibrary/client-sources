package me.vaziak.sensation.utils.math;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;

public class BlockUtils {

    public static IBlockState getBlockStateAtPos(BlockPos pos) {
        if (Minecraft.getMinecraft() == null || Minecraft.getMinecraft().theWorld == null)
            return null;

        return Minecraft.getMinecraft().theWorld.getBlockState(pos);
    }

    public static Block getBlockAtPos(BlockPos pos) {
        IBlockState blockState = getBlockStateAtPos(pos);
        if (blockState == null)
            return null;
        return blockState.getBlock();
    }

    public static Material getMaterialAtPos(BlockPos pos) {
        Block block = getBlockAtPos(pos);
        if (block == null)
            return null;
        return block.getMaterial();
    }

    public static BlockPos getBlockPosUnderPlayer() {
        if (Minecraft.getMinecraft() == null || Minecraft.getMinecraft().thePlayer == null)
            return null;

        return new BlockPos(
                Minecraft.getMinecraft().thePlayer.posX,
                (Minecraft.getMinecraft().thePlayer.posY + (Minecraft.getMinecraft().thePlayer.motionY + 0.1D)) - 1D,
                Minecraft.getMinecraft().thePlayer.posZ);
    }

    public static boolean isLiquid(Block block) {
        return !(block instanceof BlockLiquid);
    }

    public static Block getBlockUnderPlayer(EntityPlayer inPlayer) {
        return getBlockAtPos(
                new BlockPos(inPlayer.posX, (inPlayer.posY + (Minecraft.getMinecraft().thePlayer.motionY + 0.1D)) - 1D, inPlayer.posZ));
    }

    public static Block getBlockAbovePlayer(EntityPlayer inPlayer, double blocks) {
        blocks += inPlayer.height;
        return getBlockAtPos(new BlockPos(inPlayer.posX, inPlayer.posY + blocks, inPlayer.posZ));
    }
}
