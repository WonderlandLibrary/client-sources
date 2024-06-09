/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.utils;

import java.util.List;
import me.thekirkayt.utils.Helper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockIce;
import net.minecraft.block.BlockLadder;
import net.minecraft.block.BlockPackedIce;
import net.minecraft.block.BlockVine;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.RegistryNamespacedDefaultedByKey;
import net.minecraft.world.World;

public class BlockUtils {
    public static boolean isOnLiquid() {
        boolean onLiquid = false;
        if (BlockUtils.getBlockAtPosC(Helper.player(), 0.30000001192092896, 0.10000000149011612, 0.30000001192092896).getMaterial().isLiquid() && BlockUtils.getBlockAtPosC(Helper.player(), -0.30000001192092896, 0.10000000149011612, -0.30000001192092896).getMaterial().isLiquid()) {
            onLiquid = true;
        }
        return onLiquid;
    }

    public static boolean isOnLadder() {
        if (Helper.player() == null) {
            return false;
        }
        boolean onLadder = false;
        int y = (int)Helper.player().getEntityBoundingBox().offset((double)0.0, (double)1.0, (double)0.0).minY;
        for (int x = MathHelper.floor_double((double)Helper.player().getEntityBoundingBox().minX); x < MathHelper.floor_double(Helper.player().getEntityBoundingBox().maxX) + 1; ++x) {
            for (int z = MathHelper.floor_double((double)Helper.player().getEntityBoundingBox().minZ); z < MathHelper.floor_double(Helper.player().getEntityBoundingBox().maxZ) + 1; ++z) {
                Block block = BlockUtils.getBlock(x, y, z);
                if (block == null || block instanceof BlockAir) continue;
                if (!(block instanceof BlockLadder) && !(block instanceof BlockVine)) {
                    return false;
                }
                onLadder = true;
            }
        }
        return onLadder || Helper.player().isOnLadder();
    }

    public static boolean isOnIce() {
        if (Helper.player() == null) {
            return false;
        }
        boolean onIce = false;
        int y = (int)Helper.player().getEntityBoundingBox().offset((double)0.0, (double)-0.01, (double)0.0).minY;
        for (int x = MathHelper.floor_double((double)Helper.player().getEntityBoundingBox().minX); x < MathHelper.floor_double(Helper.player().getEntityBoundingBox().maxX) + 1; ++x) {
            for (int z = MathHelper.floor_double((double)Helper.player().getEntityBoundingBox().minZ); z < MathHelper.floor_double(Helper.player().getEntityBoundingBox().maxZ) + 1; ++z) {
                Block block = BlockUtils.getBlock(x, y, z);
                if (block == null || block instanceof BlockAir) continue;
                if (!(block instanceof BlockIce) && !(block instanceof BlockPackedIce)) {
                    return false;
                }
                onIce = true;
            }
        }
        return onIce;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public boolean isInsideBlock() {
        Helper.mc();
        x = MathHelper.floor_double(Minecraft.thePlayer.boundingBox.minX);
        ** GOTO lbl36
lbl-1000: // 1 sources:
        {
            Helper.mc();
            y = MathHelper.floor_double(Minecraft.thePlayer.boundingBox.minY);
            ** GOTO lbl32
lbl-1000: // 1 sources:
            {
                Helper.mc();
                z = MathHelper.floor_double(Minecraft.thePlayer.boundingBox.minZ);
                ** GOTO lbl28
lbl-1000: // 1 sources:
                {
                    Helper.mc();
                    block = Minecraft.theWorld.getBlockState(new BlockPos(x, y, z)).getBlock();
                    if (block != null && !(block instanceof BlockAir)) {
                        Helper.mc();
                        Helper.mc();
                        boundingBox = block.getCollisionBoundingBox(Minecraft.theWorld, new BlockPos(x, y, z), Minecraft.theWorld.getBlockState(new BlockPos(x, y, z)));
                        if (boundingBox != null) {
                            Helper.mc();
                            if (Minecraft.thePlayer.boundingBox.intersectsWith(boundingBox)) {
                                return true;
                            }
                        }
                    }
                    ++z;
lbl28: // 2 sources:
                    Helper.mc();
                    ** while (z < MathHelper.floor_double((double)Minecraft.thePlayer.boundingBox.maxZ) + 1)
                }
lbl31: // 1 sources:
                ++y;
lbl32: // 2 sources:
                Helper.mc();
                ** while (y < MathHelper.floor_double((double)Minecraft.thePlayer.boundingBox.maxY) + 1)
            }
lbl35: // 1 sources:
            ++x;
lbl36: // 2 sources:
            Helper.mc();
            ** while (x < MathHelper.floor_double((double)Minecraft.thePlayer.boundingBox.maxX) + 1)
        }
lbl39: // 1 sources:
        return false;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public Block getBlockByIDorName(String message) {
        tBlock = null;
        try {
            return Block.getBlockById(Integer.parseInt(message));
        }
        catch (NumberFormatException e) {
            block = null;
            ** for (object : Block.blockRegistry)
        }
lbl-1000: // 1 sources:
        {
            block = (Block)object;
            label = block.getLocalizedName().replace(" ", "");
            if (label.toLowerCase().startsWith(message) || label.toLowerCase().contains(message)) break;
            continue;
        }
lbl11: // 2 sources:
        if (block == null) return tBlock;
        return block;
    }

    public static boolean isBlockUnderPlayer(Material material, float height) {
        return BlockUtils.getBlockAtPosC(Helper.player(), 0.3100000023841858, height, 0.3100000023841858).getMaterial() == material && BlockUtils.getBlockAtPosC(Helper.player(), -0.3100000023841858, height, -0.3100000023841858).getMaterial() == material && BlockUtils.getBlockAtPosC(Helper.player(), -0.3100000023841858, height, 0.3100000023841858).getMaterial() == material && BlockUtils.getBlockAtPosC(Helper.player(), 0.3100000023841858, height, -0.3100000023841858).getMaterial() == material;
    }

    public static BlockData getBlockData(BlockPos pos, List list) {
        BlockData blockData;
        Helper.mc();
        if (list.contains(Minecraft.theWorld.getBlockState(pos.add(0, -1, 0)).getBlock())) {
            Helper.mc();
            if (list.contains(Minecraft.theWorld.getBlockState(pos.add(-1, 0, 0)).getBlock())) {
                Helper.mc();
                if (list.contains(Minecraft.theWorld.getBlockState(pos.add(1, 0, 0)).getBlock())) {
                    Helper.mc();
                    if (list.contains(Minecraft.theWorld.getBlockState(pos.add(0, 0, -1)).getBlock())) {
                        Helper.mc();
                        blockData = list.contains(Minecraft.theWorld.getBlockState(pos.add(0, 0, 1)).getBlock()) ? null : new BlockData(pos.add(0, 0, 1), EnumFacing.NORTH);
                    } else {
                        blockData = new BlockData(pos.add(0, 0, -1), EnumFacing.SOUTH);
                    }
                } else {
                    blockData = new BlockData(pos.add(1, 0, 0), EnumFacing.WEST);
                }
            } else {
                blockData = new BlockData(pos.add(-1, 0, 0), EnumFacing.EAST);
            }
        } else {
            blockData = new BlockData(pos.add(0, -1, 0), EnumFacing.UP);
        }
        return blockData;
    }

    public static Block getBlockAtPosC(EntityPlayer inPlayer, double x, double y, double z) {
        return BlockUtils.getBlock(new BlockPos(inPlayer.posX - x, inPlayer.posY - y, inPlayer.posZ - z));
    }

    public static Block getBlockUnderPlayer(EntityPlayer inPlayer, double height) {
        return BlockUtils.getBlock(new BlockPos(inPlayer.posX, inPlayer.posY - height, inPlayer.posZ));
    }

    public static Block getBlockAbovePlayer(EntityPlayer inPlayer, double height) {
        return BlockUtils.getBlock(new BlockPos(inPlayer.posX, inPlayer.posY + (double)inPlayer.height + height, inPlayer.posZ));
    }

    public static Block getBlock(int x, int y, int z) {
        return Helper.world().getBlockState(new BlockPos(x, y, z)).getBlock();
    }

    public static Block getBlock(BlockPos pos) {
        return Helper.world().getBlockState(pos).getBlock();
    }

    public static class BlockData {
        public BlockPos position;
        public EnumFacing face;

        public BlockData(BlockPos position, EnumFacing face) {
            this.position = position;
            this.face = face;
        }
    }

}

