/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.utils;

import me.thekirkayt.utils.EntityHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockIce;
import net.minecraft.block.BlockLadder;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockPackedIce;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemShears;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3i;
import net.minecraft.world.World;

public class BlockHelper {
    private static Minecraft mc;

    public static boolean isInsideBlock(EntityPlayer player) {
        for (int x = MathHelper.floor_double((double)player.boundingBox.minX); x < MathHelper.floor_double(player.boundingBox.maxX) + 1; ++x) {
            for (int y = MathHelper.floor_double((double)player.boundingBox.minY); y < MathHelper.floor_double(player.boundingBox.maxY) + 1; ++y) {
                for (int z = MathHelper.floor_double((double)player.boundingBox.minZ); z < MathHelper.floor_double(player.boundingBox.maxZ) + 1; ++z) {
                    AxisAlignedBB boundingBox;
                    Minecraft.getMinecraft();
                    Block block = Minecraft.theWorld.getBlockState(new BlockPos(x, y, z)).getBlock();
                    if (block == null || block instanceof BlockAir || (boundingBox = block.getCollisionBoundingBox(Minecraft.theWorld, new BlockPos(x, y, z), Minecraft.theWorld.getBlockState(new BlockPos(x, y, z)))) == null || !player.boundingBox.intersectsWith(boundingBox)) continue;
                    return true;
                }
            }
        }
        return false;
    }

    public static float[] getFacingRotations(int x, int y, int z, EnumFacing facing) {
        EntitySnowball temp = new EntitySnowball(Minecraft.theWorld);
        temp.posX = (double)x + 0.5;
        temp.posY = (double)y + 0.5;
        temp.posZ = (double)z + 0.5;
        temp.posX += (double)facing.getDirectionVec().getX() * 0.25;
        temp.posY += (double)facing.getDirectionVec().getY() * 0.25;
        temp.posZ += (double)facing.getDirectionVec().getZ() * 0.25;
        return EntityHelper.getAngles(temp);
    }

    public static boolean canSeeBlock(int x, int y, int z) {
        return BlockHelper.getFacing(new BlockPos(x, y, z)) != null;
    }

    public static Block getBlock(int x, int y, int z) {
        return Minecraft.theWorld.getBlockState(new BlockPos(x, y, z)).getBlock();
    }

    public static Block getBlock(double x, double y, double z) {
        return Minecraft.theWorld.getBlockState(new BlockPos(x, y, z)).getBlock();
    }

    public static boolean isInLiquid() {
        boolean inLiquid = false;
        int y = (int)Minecraft.thePlayer.getEntityBoundingBox().minY;
        for (int x = MathHelper.floor_double((double)Minecraft.thePlayer.getEntityBoundingBox().minX); x < MathHelper.floor_double(Minecraft.thePlayer.getEntityBoundingBox().maxX) + 1; ++x) {
            for (int z = MathHelper.floor_double((double)Minecraft.thePlayer.getEntityBoundingBox().minZ); z < MathHelper.floor_double(Minecraft.thePlayer.getEntityBoundingBox().maxZ) + 1; ++z) {
                Block block = BlockHelper.getBlock(x, y, z);
                if (block == null || block instanceof BlockAir) continue;
                if (!(block instanceof BlockLiquid)) {
                    return false;
                }
                inLiquid = true;
            }
        }
        return inLiquid;
    }

    public static boolean isOnIce() {
        boolean onIce = false;
        int y = (int)Minecraft.thePlayer.getEntityBoundingBox().offset((double)0.0, (double)-0.1, (double)0.0).minY;
        for (int x = MathHelper.floor_double((double)Minecraft.thePlayer.getEntityBoundingBox().minX); x < MathHelper.floor_double(Minecraft.thePlayer.getEntityBoundingBox().maxX) + 1; ++x) {
            for (int z = MathHelper.floor_double((double)Minecraft.thePlayer.getEntityBoundingBox().minZ); z < MathHelper.floor_double(Minecraft.thePlayer.getEntityBoundingBox().maxZ) + 1; ++z) {
                Block block = BlockHelper.getBlock(x, y, z);
                if (block == null || block instanceof BlockAir || !(block instanceof BlockPackedIce) && !(block instanceof BlockIce)) continue;
                onIce = true;
            }
        }
        return onIce;
    }

    public static boolean isOnFloor(double yOffset) {
        boolean onIce = false;
        int y = (int)Minecraft.thePlayer.getEntityBoundingBox().offset((double)0.0, (double)yOffset, (double)0.0).minY;
        for (int x = MathHelper.floor_double((double)Minecraft.thePlayer.getEntityBoundingBox().minX); x < MathHelper.floor_double(Minecraft.thePlayer.getEntityBoundingBox().maxX) + 1; ++x) {
            for (int z = MathHelper.floor_double((double)Minecraft.thePlayer.getEntityBoundingBox().minZ); z < MathHelper.floor_double(Minecraft.thePlayer.getEntityBoundingBox().maxZ) + 1; ++z) {
                Block block = BlockHelper.getBlock(x, y, z);
                if (block == null || block instanceof BlockAir || !block.isCollidable()) continue;
                onIce = true;
            }
        }
        return onIce;
    }

    public static boolean isOnLadder() {
        boolean onLadder = false;
        int y = (int)Minecraft.thePlayer.getEntityBoundingBox().offset((double)0.0, (double)1.0, (double)0.0).minY;
        for (int x = MathHelper.floor_double((double)Minecraft.thePlayer.getEntityBoundingBox().minX); x < MathHelper.floor_double(Minecraft.thePlayer.getEntityBoundingBox().maxX) + 1; ++x) {
            for (int z = MathHelper.floor_double((double)Minecraft.thePlayer.getEntityBoundingBox().minZ); z < MathHelper.floor_double(Minecraft.thePlayer.getEntityBoundingBox().maxZ) + 1; ++z) {
                Block block = BlockHelper.getBlock(x, y, z);
                if (block == null || block instanceof BlockAir) continue;
                if (!(block instanceof BlockLadder)) {
                    return false;
                }
                onLadder = true;
            }
        }
        return onLadder || Minecraft.thePlayer.isOnLadder();
    }

    public static boolean isOnLiquid() {
        boolean onLiquid = false;
        int y = (int)Minecraft.thePlayer.getEntityBoundingBox().offset((double)0.0, (double)-0.01, (double)0.0).minY;
        for (int x = MathHelper.floor_double((double)Minecraft.thePlayer.getEntityBoundingBox().minX); x < MathHelper.floor_double(Minecraft.thePlayer.getEntityBoundingBox().maxX) + 1; ++x) {
            for (int z = MathHelper.floor_double((double)Minecraft.thePlayer.getEntityBoundingBox().minZ); z < MathHelper.floor_double(Minecraft.thePlayer.getEntityBoundingBox().maxZ) + 1; ++z) {
                Block block = BlockHelper.getBlock(x, y, z);
                if (block == null || block instanceof BlockAir) continue;
                if (!(block instanceof BlockLiquid)) {
                    return false;
                }
                onLiquid = true;
            }
        }
        return onLiquid;
    }

    public static EnumFacing getFacing(BlockPos pos) {
        EnumFacing[] orderedValues;
        for (EnumFacing facing : orderedValues = new EnumFacing[]{EnumFacing.UP, EnumFacing.NORTH, EnumFacing.EAST, EnumFacing.SOUTH, EnumFacing.WEST, EnumFacing.DOWN}) {
            EntitySnowball temp = new EntitySnowball(Minecraft.theWorld);
            temp.posX = (double)pos.getX() + 0.5;
            temp.posY = (double)pos.getY() + 0.5;
            temp.posZ = (double)pos.getZ() + 0.5;
            temp.posX += (double)facing.getDirectionVec().getX() * 0.5;
            temp.posY += (double)facing.getDirectionVec().getY() * 0.5;
            temp.posZ += (double)facing.getDirectionVec().getZ() * 0.5;
            if (!Minecraft.thePlayer.canEntityBeSeen(temp)) continue;
            return facing;
        }
        return null;
    }

    public static Block getBlockAtPos(BlockPos inBlockPos) {
        BlockPos currentPos = inBlockPos;
        Minecraft.getMinecraft();
        Block s = Minecraft.theWorld.getBlockState(currentPos).getBlock();
        return s;
    }

    public static void bestTool(int x, int y, int z) {
        Minecraft.getMinecraft();
        int blockId = Block.getIdFromBlock(Minecraft.theWorld.getBlockState(new BlockPos(x, y, z)).getBlock());
        int bestSlot = 0;
        float f = -1.0f;
        for (int i1 = 36; i1 < 45; ++i1) {
            try {
                Minecraft.getMinecraft();
                ItemStack curSlot = Minecraft.thePlayer.inventoryContainer.getSlot(i1).getStack();
                if (!(curSlot.getItem() instanceof ItemTool) && !(curSlot.getItem() instanceof ItemSword) && !(curSlot.getItem() instanceof ItemShears) || !(curSlot.getStrVsBlock(Block.getBlockById(blockId)) > f)) continue;
                bestSlot = i1 - 36;
                f = curSlot.getStrVsBlock(Block.getBlockById(blockId));
                continue;
            }
            catch (Exception curSlot) {
                // empty catch block
            }
        }
        if (f != -1.0f) {
            Minecraft.getMinecraft();
            Minecraft.thePlayer.inventory.currentItem = bestSlot;
            Minecraft.playerController.updateController();
        }
    }

    public static float[] getBlockRotations(double x, double y, double z) {
        double var4 = x - Minecraft.thePlayer.posX + 0.5;
        double var6 = z - Minecraft.thePlayer.posZ + 0.5;
        double var8 = y - (Minecraft.thePlayer.posY + (double)Minecraft.thePlayer.getEyeHeight() - 1.0);
        double var14 = MathHelper.sqrt_double(var4 * var4 + var6 * var6);
        float var12 = (float)(Math.atan2(var6, var4) * 180.0 / 3.141592653589793) - 90.0f;
        return new float[]{var12, (float)(-(Math.atan2(var8, var14) * 180.0 / 3.141592653589793))};
    }
}

