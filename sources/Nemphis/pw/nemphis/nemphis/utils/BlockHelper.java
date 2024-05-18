/*
 * Decompiled with CFR 0_118.
 */
package pw.vertexcode.nemphis.utils;

import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockIce;
import net.minecraft.block.BlockLadder;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockPackedIce;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3i;
import net.minecraft.world.World;

public class BlockHelper {
    private static Minecraft mc = Minecraft.getMinecraft();

    public static boolean canSeeBlock(int x, int y, int z) {
        if (BlockHelper.getFacing(new BlockPos(x, y, z)) != null) {
            return true;
        }
        return false;
    }

    public static Block getBlock(int x, int y, int z) {
        return BlockHelper.mc.theWorld.getBlockState(new BlockPos(x, y, z)).getBlock();
    }

    public static Block getBlock(double x, double y, double z) {
        return BlockHelper.mc.theWorld.getBlockState(new BlockPos(x, y, z)).getBlock();
    }

    public static float[] getBlockRotations(double x, double y, double z) {
        double var4 = x - BlockHelper.mc.thePlayer.posX + 0.5;
        double var5 = z - BlockHelper.mc.thePlayer.posZ + 0.5;
        double var6 = y - (BlockHelper.mc.thePlayer.posY + (double)BlockHelper.mc.thePlayer.getEyeHeight() - 1.0);
        double var7 = MathHelper.sqrt_double(var4 * var4 + var5 * var5);
        float var8 = (float)(Math.atan2(var5, var4) * 180.0 / 3.141592653589793) - 90.0f;
        return new float[]{var8, (float)(- Math.atan2(var6, var7) * 180.0 / 3.141592653589793)};
    }

    public static boolean isInLiquid() {
        boolean inLiquid = false;
        int y = (int)BlockHelper.mc.thePlayer.getEntityBoundingBox().minY;
        int x = MathHelper.floor_double(BlockHelper.mc.thePlayer.getEntityBoundingBox().minX);
        while (x < MathHelper.floor_double(BlockHelper.mc.thePlayer.getEntityBoundingBox().maxX) + 1) {
            int z = MathHelper.floor_double(BlockHelper.mc.thePlayer.getEntityBoundingBox().minZ);
            while (z < MathHelper.floor_double(BlockHelper.mc.thePlayer.getEntityBoundingBox().maxZ) + 1) {
                Block block = BlockHelper.getBlock(BlockHelper.mc.thePlayer.posX, BlockHelper.mc.thePlayer.posY + 1.0, BlockHelper.mc.thePlayer.posZ);
                if (block != null && !(block instanceof BlockAir)) {
                    if (!(block instanceof BlockLiquid)) {
                        return false;
                    }
                    inLiquid = true;
                }
                ++z;
            }
            ++x;
        }
        return inLiquid;
    }

    public static boolean isOnIce() {
        boolean onIce = false;
        int y = (int)BlockHelper.mc.thePlayer.getEntityBoundingBox().offset((double)0.0, (double)-0.1, (double)0.0).minY;
        int x = MathHelper.floor_double(BlockHelper.mc.thePlayer.getEntityBoundingBox().minX);
        while (x < MathHelper.floor_double(BlockHelper.mc.thePlayer.getEntityBoundingBox().maxX) + 1) {
            int z = MathHelper.floor_double(BlockHelper.mc.thePlayer.getEntityBoundingBox().minZ);
            while (z < MathHelper.floor_double(BlockHelper.mc.thePlayer.getEntityBoundingBox().maxZ) + 1) {
                Block block = BlockHelper.getBlock(x, y, z);
                if (block != null && !(block instanceof BlockAir) && (block instanceof BlockPackedIce || block instanceof BlockIce)) {
                    onIce = true;
                }
                ++z;
            }
            ++x;
        }
        return onIce;
    }

    public static boolean isOnFloor(double yOffset) {
        boolean onIce = false;
        int y = (int)BlockHelper.mc.thePlayer.getEntityBoundingBox().offset((double)0.0, (double)yOffset, (double)0.0).minY;
        int x = MathHelper.floor_double(BlockHelper.mc.thePlayer.getEntityBoundingBox().minX);
        while (x < MathHelper.floor_double(BlockHelper.mc.thePlayer.getEntityBoundingBox().maxX) + 1) {
            int z = MathHelper.floor_double(BlockHelper.mc.thePlayer.getEntityBoundingBox().minZ);
            while (z < MathHelper.floor_double(BlockHelper.mc.thePlayer.getEntityBoundingBox().maxZ) + 1) {
                Block block = BlockHelper.getBlock(x, y, z);
                if (block != null && !(block instanceof BlockAir) && block.isCollidable()) {
                    onIce = true;
                }
                ++z;
            }
            ++x;
        }
        return onIce;
    }

    public static boolean isOnLadder() {
        boolean onLadder = false;
        int y = (int)BlockHelper.mc.thePlayer.getEntityBoundingBox().offset((double)0.0, (double)1.0, (double)0.0).minY;
        int x = MathHelper.floor_double(BlockHelper.mc.thePlayer.getEntityBoundingBox().minX);
        while (x < MathHelper.floor_double(BlockHelper.mc.thePlayer.getEntityBoundingBox().maxX) + 1) {
            int z = MathHelper.floor_double(BlockHelper.mc.thePlayer.getEntityBoundingBox().minZ);
            while (z < MathHelper.floor_double(BlockHelper.mc.thePlayer.getEntityBoundingBox().maxZ) + 1) {
                Block block = BlockHelper.getBlock(x, y, z);
                if (block != null && !(block instanceof BlockAir)) {
                    if (!(block instanceof BlockLadder)) {
                        return false;
                    }
                    onLadder = true;
                }
                ++z;
            }
            ++x;
        }
        if (!onLadder && !BlockHelper.mc.thePlayer.isOnLadder()) {
            return false;
        }
        return true;
    }

    public static boolean isOnLiquid() {
        boolean onLiquid = false;
        int y = (int)BlockHelper.mc.thePlayer.getEntityBoundingBox().offset((double)0.0, (double)-0.01, (double)0.0).minY;
        int x = MathHelper.floor_double(BlockHelper.mc.thePlayer.getEntityBoundingBox().minX);
        while (x < MathHelper.floor_double(BlockHelper.mc.thePlayer.getEntityBoundingBox().maxX) + 1) {
            int z = MathHelper.floor_double(BlockHelper.mc.thePlayer.getEntityBoundingBox().minZ);
            while (z < MathHelper.floor_double(BlockHelper.mc.thePlayer.getEntityBoundingBox().maxZ) + 1) {
                Block block = BlockHelper.getBlock(x, y, z);
                if (block != null && !(block instanceof BlockAir)) {
                    if (!(block instanceof BlockLiquid)) {
                        return false;
                    }
                    onLiquid = true;
                }
                ++z;
            }
            ++x;
        }
        return onLiquid;
    }

    public static EnumFacing getFacing(BlockPos pos) {
        EnumFacing[] orderedValues;
        EnumFacing[] array = orderedValues = new EnumFacing[]{EnumFacing.UP, EnumFacing.NORTH, EnumFacing.EAST, EnumFacing.SOUTH, EnumFacing.WEST, EnumFacing.DOWN};
        int length = array.length;
        int i = 0;
        while (i < length) {
            EnumFacing facing = array[i];
            EntitySnowball temp = new EntitySnowball(BlockHelper.mc.theWorld);
            temp.posX = (double)pos.getX() + 0.5;
            temp.posY = (double)pos.getY() + 0.5;
            temp.posZ = (double)pos.getZ() + 0.5;
            EntitySnowball entity = temp;
            entity.posX += (double)facing.getDirectionVec().getX() * 0.5;
            EntitySnowball entity2 = temp;
            entity2.posY += (double)facing.getDirectionVec().getY() * 0.5;
            EntitySnowball entity3 = temp;
            entity3.posZ += (double)facing.getDirectionVec().getZ() * 0.5;
            if (BlockHelper.mc.thePlayer.canEntityBeSeen(temp)) {
                return facing;
            }
            ++i;
        }
        return null;
    }
}

