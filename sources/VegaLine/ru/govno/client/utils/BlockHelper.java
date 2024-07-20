/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.govno.client.utils;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.BlockFarmland;
import net.minecraft.block.BlockGrass;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;

public class BlockHelper {
    Minecraft mc = Minecraft.getMinecraft();

    public static Block getBlock(BlockPos pos) {
        Minecraft mc = Minecraft.getMinecraft();
        return mc.world.getBlockState(pos).getBlock();
    }

    public static BlockPos getPlayerPos() {
        Minecraft mc = Minecraft.getMinecraft();
        return new BlockPos(Math.floor(Minecraft.player.posX), Math.floor(Minecraft.player.posY), Math.floor(Minecraft.player.posZ));
    }

    public static boolean IsValidBlockPos(BlockPos pos) {
        Minecraft mc = Minecraft.getMinecraft();
        IBlockState state = mc.world.getBlockState(pos);
        if (state.getBlock() instanceof BlockDirt || state.getBlock() instanceof BlockGrass && !(state.getBlock() instanceof BlockFarmland)) {
            return mc.world.getBlockState(pos.up()).getBlock() == Blocks.AIR;
        }
        return false;
    }

    public static List<BlockPos> getSphere(BlockPos blockPos, float offset, int range, boolean hollow, boolean sphere) {
        ArrayList<BlockPos> blockPosList = new ArrayList<BlockPos>();
        int blockPosX = blockPos.getX();
        int blockPosY = blockPos.getY();
        int blockPosZ = blockPos.getZ();
        for (float x = (float)blockPosX - offset; x <= (float)blockPosX + offset; x += 1.0f) {
            block1: for (float z = (float)blockPosZ - offset; z <= (float)blockPosZ + offset; z += 1.0f) {
                float y = sphere ? (float)blockPosY - offset : (float)blockPosY;
                while (true) {
                    float f;
                    float f2 = f = sphere ? (float)blockPosY + offset : (float)(blockPosY + range);
                    if (!(y < f)) continue block1;
                    float dist = ((float)blockPosX - x) * ((float)blockPosX - x) + ((float)blockPosZ - z) * ((float)blockPosZ - z) + (sphere ? ((float)blockPosY - y) * ((float)blockPosY - y) : 0.0f);
                    if (!(!(dist < offset * offset) || hollow && dist < offset - 1.0f * (offset - 1.0f))) {
                        BlockPos pos = new BlockPos(x, y, z);
                        blockPosList.add(pos);
                    }
                    y += 1.0f;
                }
            }
        }
        return blockPosList;
    }

    public static ArrayList<BlockPos> getBlocks(int x, int y, int z) {
        Minecraft mc = Minecraft.getMinecraft();
        BlockPos min = new BlockPos(Minecraft.player.posX - (double)x, Minecraft.player.posY - (double)y, Minecraft.player.posZ - (double)z);
        BlockPos max = new BlockPos(Minecraft.player.posX + (double)x, Minecraft.player.posY + (double)y, Minecraft.player.posZ + (double)z);
        return BlockHelper.getAllInBox(min, max);
    }

    public static ArrayList<BlockPos> getAllInBox(BlockPos from, BlockPos to) {
        ArrayList<BlockPos> blocks = new ArrayList<BlockPos>();
        BlockPos min = new BlockPos(Math.min(from.getX(), to.getX()), Math.min(from.getY(), to.getY()), Math.min(from.getZ(), to.getZ()));
        BlockPos max = new BlockPos(Math.max(from.getX(), to.getX()), Math.max(from.getY(), to.getY()), Math.max(from.getZ(), to.getZ()));
        for (int x = min.getX(); x <= max.getX(); ++x) {
            for (int y = min.getY(); y <= max.getY(); ++y) {
                for (int z = min.getZ(); z <= max.getZ(); ++z) {
                    blocks.add(new BlockPos(x, y, z));
                }
            }
        }
        return blocks;
    }
}

