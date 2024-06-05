package xyz.vergoclient.util.main;

import com.sun.javafx.geom.Vec3d;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.*;

import java.util.Objects;

public class BlockUtils {

    private static final Minecraft mc = Minecraft.getMinecraft();

    public static boolean canBeClicked(BlockPos pos) {
        return getBlock(pos).canCollideCheck(getState(pos), false);
    }


    public static Block getBlock(BlockPos pos) {
        return getState(pos).getBlock();
    }

    public static Block getBlock(double x, double y, double z) {
        return mc.theWorld.getBlockState(new BlockPos(x, y, z)).getBlock();
    }

    public static Block getBlockAbovePlayer(EntityPlayer inPlayer, double blocks) {
        blocks += inPlayer.height;
        return getBlockAtPos(new BlockPos(inPlayer.posX, inPlayer.posY + blocks, inPlayer.posZ));
    }

    public static Block getBlockAtPos(BlockPos inBlockPos) {
        IBlockState s = mc.theWorld.getBlockState(inBlockPos);
        return s.getBlock();
    }

    public static Block getBlockAtPosC(EntityPlayer inPlayer, double x, double y, double z) {
        return getBlockAtPos(new BlockPos(inPlayer.posX - x, inPlayer.posY - y, inPlayer.posZ - z));
    }

    public static float getBlockDistance(float xDiff, float yDiff, float zDiff) {
        return MathHelper.sqrt_float(((xDiff - 0.5F) * (xDiff - 0.5F)) + ((yDiff - 0.5F) * (yDiff - 0.5F))
                + ((zDiff - 0.5F) * (zDiff - 0.5F)));
    }

    public static BlockPos getBlockPos(BlockPos inBlockPos) {
        return inBlockPos;
    }

    public static BlockPos getBlockPos(double x, double y, double z) {
        return getBlockPos(new BlockPos(x, y, z));
    }

    public static BlockPos getBlockPosUnderPlayer(EntityPlayer inPlayer) {
        return new BlockPos(inPlayer.posX, (inPlayer.posY + (mc.thePlayer.motionY + 0.1D)) - 1D, inPlayer.posZ);
    }

    public static Block getBlockUnderPlayer(EntityPlayer inPlayer) {
        return getBlockAtPos(
                new BlockPos(inPlayer.posX, (inPlayer.posY + (mc.thePlayer.motionY + 0.1D)) - 1D, inPlayer.posZ));
    }
    public static float getHorizontalPlayerBlockDistance(BlockPos blockPos) {
        float xDiff = (float) (mc.thePlayer.posX - blockPos.getX());
        float zDiff = (float) (mc.thePlayer.posZ - blockPos.getZ());
        return MathHelper.sqrt_float(((xDiff - 0.5F) * (xDiff - 0.5F)) + ((zDiff - 0.5F) * (zDiff - 0.5F)));
    }

    public static Material getMaterial(BlockPos pos) {
        return getState(pos).getBlock().getMaterial();
    }

    public static MovingObjectPosition getMouseOver() {
        return mc.objectMouseOver;
    }

    public static EntityPlayerSP getPlayer() {
        return mc.thePlayer;
    }

    public static float getPlayerBlockDistance(BlockPos blockPos) {
        return getPlayerBlockDistance(blockPos.getX(), blockPos.getY(), blockPos.getZ());
    }

    public static float getPlayerBlockDistance(double posX, double posY, double posZ) {
        float xDiff = (float) (mc.thePlayer.posX - posX);
        float yDiff = (float) (mc.thePlayer.posY - posY);
        float zDiff = (float) (mc.thePlayer.posZ - posZ);
        return getBlockDistance(xDiff, yDiff, zDiff);
    }

    public static IBlockState getState(BlockPos pos) {
        return mc.theWorld.getBlockState(pos);
    }

    public static boolean isInLiquid(Entity entity) {
        if (entity == null)
            return false;
        boolean inLiquid = false;
        final int y = (int) entity.getEntityBoundingBox().minY;
        for (int x = MathHelper.floor_double(entity.getEntityBoundingBox().minX); x < MathHelper.floor_double(mc.thePlayer.getEntityBoundingBox().maxX) + 1; x++) {
            for (int z = MathHelper.floor_double(entity.getEntityBoundingBox().minZ); z < MathHelper.floor_double(entity.getEntityBoundingBox().maxZ) + 1; z++) {
                final Block block = mc.theWorld.getBlockState(new BlockPos(x, y, z)).getBlock();
                if (block != null && !(block instanceof BlockAir)) {
                    if (!(block instanceof BlockLiquid))
                        return false;
                    inLiquid = true;
                }
            }
        }
        return inLiquid || mc.thePlayer.isInWater();
    }

    public static boolean isOnLiquid(Entity entity) {
        if (entity == null)
            return false;
        boolean onLiquid = false;
        final int y = (int) entity.getEntityBoundingBox().offset(0.0D, -0.01D, 0.0D).minY;
        for (int x = MathHelper.floor_double(entity.getEntityBoundingBox().minX); x < MathHelper.floor_double(entity.getEntityBoundingBox().maxX) + 1; x++) {
            for (int z = MathHelper.floor_double(entity.getEntityBoundingBox().minZ); z < MathHelper.floor_double(entity.getEntityBoundingBox().maxZ) + 1; z++) {
                final Block block = mc.theWorld.getBlockState(new BlockPos(x, y, z)).getBlock();
                if (block != null && !(block instanceof BlockAir)) {
                    if (!(block instanceof BlockLiquid))
                        return false;
                    onLiquid = true;
                }
            }
        }
        return onLiquid;
    }

    public static boolean isOnIce(Entity entity) {
        if (entity == null)
            return false;
        boolean onIce = false;
        final int y = (int) entity.getEntityBoundingBox().offset(0.0D, -0.01D, 0.0D).minY;
        for (int x = MathHelper.floor_double(entity.getEntityBoundingBox().minX); x < MathHelper.floor_double(entity.getEntityBoundingBox().maxX) + 1; x++) {
            for (int z = MathHelper.floor_double(entity.getEntityBoundingBox().minZ); z < MathHelper.floor_double(entity.getEntityBoundingBox().maxZ) + 1; z++) {
                final Block block = mc.theWorld.getBlockState(new BlockPos(x, y, z)).getBlock();
                if (block != null && !(block instanceof BlockAir)) {
                    if (!(block instanceof BlockIce) && !(block instanceof BlockPackedIce))
                        return false;
                    onIce = true;
                }
            }
        }
        return onIce;
    }

    public static boolean isOnLadder(Entity entity) {
        if (entity == null)
            return false;
        boolean onLadder = false;
        final int y = (int) entity.getEntityBoundingBox().offset(0.0D, 1.0D, 0.0D).minY;
        for (int x = MathHelper.floor_double(entity.getEntityBoundingBox().minX); x < MathHelper.floor_double(entity.getEntityBoundingBox().maxX) + 1; x++) {
            for (int z = MathHelper.floor_double(entity.getEntityBoundingBox().minZ); z < MathHelper.floor_double(entity.getEntityBoundingBox().maxZ) + 1; z++) {
                final Block block = mc.theWorld.getBlockState(new BlockPos(x, y, z)).getBlock();
                if (Objects.nonNull(block) && !(block instanceof BlockAir)) {
                    if (!(block instanceof BlockLadder || block instanceof BlockVine))
                        return false;
                    onLadder = true;
                }
            }
        }
        return onLadder || mc.thePlayer.isOnLadder();
    }

    public static boolean isInsideBlock(Entity entity) {
        for (int x = MathHelper.floor_double(entity.getEntityBoundingBox().minX); x < MathHelper.floor_double(entity.getEntityBoundingBox().maxX) + 1; x++) {
            for (int y = MathHelper.floor_double(entity.getEntityBoundingBox().minY); y < MathHelper.floor_double(entity.getEntityBoundingBox().maxY) + 1; y++) {
                for (int z = MathHelper.floor_double(entity.getEntityBoundingBox().minZ); z < MathHelper.floor_double(entity.getEntityBoundingBox().maxZ) + 1; z++) {
                    Block block = mc.theWorld.getBlockState(new BlockPos(x, y, z)).getBlock();
                    if (block != null) {
                        AxisAlignedBB boundingBox = block.getCollisionBoundingBox(mc.theWorld, new BlockPos(x, y, z), mc.theWorld.getBlockState(new BlockPos(x, y, z)));
                        if (block instanceof BlockHopper) {
                            boundingBox = new AxisAlignedBB(x, y, z, x + 1, y + 1, z + 1);
                        }

                        if (boundingBox != null && entity.getEntityBoundingBox().intersectsWith(boundingBox)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}
