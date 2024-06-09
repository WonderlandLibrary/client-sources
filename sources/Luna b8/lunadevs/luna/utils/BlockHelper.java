package lunadevs.luna.utils;

import net.minecraft.client.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;

public class BlockHelper
{
    private static Minecraft mc;

    public static float[] getBlockRotations(final int x, final int y, final int z)
    {
        final Entity temp = new EntitySnowball(BlockHelper.mc.theWorld);
        temp.posX = x + 0.5;
        temp.posY = y + 0.5;
        temp.posZ = z + 0.5;
        return getAngles(temp);
    }

    public static float[] getFacingRotations(final int x, final int y, final int z, final EnumFacing facing)
    {
        final Entity temp = new EntitySnowball(BlockHelper.mc.theWorld);
        temp.posX = x + 0.5;
        temp.posY = y + 0.5;
        temp.posZ = z + 0.5;
        final Entity entity = temp;
        entity.posX += facing.getDirectionVec().getX() * 0.25;
        final Entity entity2 = temp;
        entity2.posY += facing.getDirectionVec().getY() * 0.25;
        final Entity entity3 = temp;
        entity3.posZ += facing.getDirectionVec().getZ() * 0.25;
        return getAngles(temp);
    }
    
    public static float getYawChangeToEntity(final Entity entity) {
        final double deltaX = entity.posX - mc.thePlayer.posX;
        final double deltaZ = entity.posZ - mc.thePlayer.posZ;
        double yawToEntity;
        if (deltaZ < 0.0 && deltaX < 0.0) {
            yawToEntity = 360.0 + Math.toDegrees(Math.atan(deltaZ / deltaX));
        }
        else if (deltaZ < 0.0 && deltaX > 0.0) {
            yawToEntity = -360.0 + Math.toDegrees(Math.atan(deltaZ / deltaX));
        }
        else {
            yawToEntity = Math.toDegrees(-Math.atan(deltaX / deltaZ));
        }
        return MathHelper.wrapAngleTo180_float(-(mc.thePlayer.rotationYaw - (float)yawToEntity));
    }
    
    public static float getPitchChangeToEntity(final Entity entity) {
        final double deltaX = entity.posX - mc.thePlayer.posX;
        final double deltaZ = entity.posZ - mc.thePlayer.posZ;
        final double deltaY = entity.posY - 1.6 + entity.getEyeHeight() - mc.thePlayer.posY;
        final double distanceXZ = MathHelper.sqrt_double(deltaX * deltaX + deltaZ * deltaZ);
        final double pitchToEntity = -Math.toDegrees(Math.atan(deltaY / distanceXZ));
        return -MathHelper.wrapAngleTo180_float(mc.thePlayer.rotationPitch - (float)pitchToEntity);
    }

    public static float[] getAngles(final Entity e) {
        return new float[] { getYawChangeToEntity(e) + mc.thePlayer.rotationYaw, getPitchChangeToEntity(e) + mc.thePlayer.rotationPitch };
    }
    
    public static boolean isOnEdgeOfBlock() {
    	return mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer, mc.thePlayer.getEntityBoundingBox().offset(0.5, -0.5, 0.5).expand(-0.000, 0.0, -0.000)).isEmpty();
        }
    
    public static boolean canSeeBlock(final int x, final int y, final int z)
    {
        return getFacing(new BlockPos(x, y, z)) != null;
    }

    public static Block getBlock(final int x, final int y, final int z)
    {
        return BlockHelper.mc.theWorld.getBlockState(new BlockPos(x, y, z)).getBlock();
    }

    public static Block getBlock(final double x, final double y, final double z)
    {
        return BlockHelper.mc.theWorld.getBlockState(new BlockPos(x, y, z)).getBlock();
    }

    public static boolean isInLiquid()
    {
        boolean inLiquid = false;
        final int y = (int)BlockHelper.mc.thePlayer.getEntityBoundingBox().minY;

        for (int x = MathHelper.floor_double(BlockHelper.mc.thePlayer.getEntityBoundingBox().minX); x < MathHelper.floor_double(BlockHelper.mc.thePlayer.getEntityBoundingBox().maxX) + 1; ++x)
        {
            for (int z = MathHelper.floor_double(BlockHelper.mc.thePlayer.getEntityBoundingBox().minZ); z < MathHelper.floor_double(BlockHelper.mc.thePlayer.getEntityBoundingBox().maxZ) + 1; ++z)
            {
                final Block block = getBlock(x, y, z);

                if (block != null && !(block instanceof BlockAir))
                {
                    if (!(block instanceof BlockLiquid))
                    {
                        return false;
                    }

                    inLiquid = true;
                }
            }
        }

        return inLiquid;
    }

    public static boolean isOnIce()
    {
        boolean onIce = false;
        final int y = (int)BlockHelper.mc.thePlayer.getEntityBoundingBox().offset(0.0, -0.1, 0.0).minY;

        for (int x = MathHelper.floor_double(BlockHelper.mc.thePlayer.getEntityBoundingBox().minX); x < MathHelper.floor_double(BlockHelper.mc.thePlayer.getEntityBoundingBox().maxX) + 1; ++x)
        {
            for (int z = MathHelper.floor_double(BlockHelper.mc.thePlayer.getEntityBoundingBox().minZ); z < MathHelper.floor_double(BlockHelper.mc.thePlayer.getEntityBoundingBox().maxZ) + 1; ++z)
            {
                final Block block = getBlock(x, y, z);

                if (block != null && !(block instanceof BlockAir) && (block instanceof BlockPackedIce || block instanceof BlockIce))
                {
                    onIce = true;
                }
            }
        }

        return onIce;
    }

    public static boolean isOnFloor(final double yOffset)
    {
        boolean onIce = false;
        final int y = (int)BlockHelper.mc.thePlayer.getEntityBoundingBox().offset(0.0, yOffset, 0.0).minY;

        for (int x = MathHelper.floor_double(BlockHelper.mc.thePlayer.getEntityBoundingBox().minX); x < MathHelper.floor_double(BlockHelper.mc.thePlayer.getEntityBoundingBox().maxX) + 1; ++x)
        {
            for (int z = MathHelper.floor_double(BlockHelper.mc.thePlayer.getEntityBoundingBox().minZ); z < MathHelper.floor_double(BlockHelper.mc.thePlayer.getEntityBoundingBox().maxZ) + 1; ++z)
            {
                final Block block = getBlock(x, y, z);

                if (block != null && !(block instanceof BlockAir) && block.isCollidable())
                {
                    onIce = true;
                }
            }
        }

        return onIce;
    }

    public static boolean isOnLadder()
    {
        boolean onLadder = false;
        final int y = (int)BlockHelper.mc.thePlayer.getEntityBoundingBox().offset(0.0, 1.0, 0.0).minY;

        for (int x = MathHelper.floor_double(BlockHelper.mc.thePlayer.getEntityBoundingBox().minX); x < MathHelper.floor_double(BlockHelper.mc.thePlayer.getEntityBoundingBox().maxX) + 1; ++x)
        {
            for (int z = MathHelper.floor_double(BlockHelper.mc.thePlayer.getEntityBoundingBox().minZ); z < MathHelper.floor_double(BlockHelper.mc.thePlayer.getEntityBoundingBox().maxZ) + 1; ++z)
            {
                final Block block = getBlock(x, y, z);

                if (block != null && !(block instanceof BlockAir))
                {
                    if (!(block instanceof BlockLadder))
                    {
                        return false;
                    }

                    onLadder = true;
                }
            }
        }

        return onLadder || BlockHelper.mc.thePlayer.isOnLadder();
    }

    public static boolean isOnLiquid()
    {
        boolean onLiquid = false;
        final int y = (int)BlockHelper.mc.thePlayer.getEntityBoundingBox().offset(0.0, -0.01, 0.0).minY;

        for (int x = MathHelper.floor_double(BlockHelper.mc.thePlayer.getEntityBoundingBox().minX); x < MathHelper.floor_double(BlockHelper.mc.thePlayer.getEntityBoundingBox().maxX) + 1; ++x)
        {
            for (int z = MathHelper.floor_double(BlockHelper.mc.thePlayer.getEntityBoundingBox().minZ); z < MathHelper.floor_double(BlockHelper.mc.thePlayer.getEntityBoundingBox().maxZ) + 1; ++z)
            {
                final Block block = getBlock(x, y, z);

                if (block != null && !(block instanceof BlockAir))
                {
                    if (!(block instanceof BlockLiquid))
                    {
                        return false;
                    }

                    onLiquid = true;
                }
            }
        }

        return onLiquid;
    }

    public static EnumFacing getFacing(final BlockPos pos)
    {
        final EnumFacing[] array;
        final EnumFacing[] orderedValues = array = new EnumFacing[] { EnumFacing.UP, EnumFacing.NORTH, EnumFacing.EAST, EnumFacing.SOUTH, EnumFacing.WEST, EnumFacing.DOWN };

        for (final EnumFacing facing : array)
        {
            final Entity temp = new EntitySnowball(BlockHelper.mc.theWorld);
            temp.posX = pos.getX() + 0.5;
            temp.posY = pos.getY() + 0.5;
            temp.posZ = pos.getZ() + 0.5;
            final Entity entity = temp;
            entity.posX += facing.getDirectionVec().getX() * 0.5;
            final Entity entity2 = temp;
            entity2.posY += facing.getDirectionVec().getY() * 0.5;
            final Entity entity3 = temp;
            entity3.posZ += facing.getDirectionVec().getZ() * 0.5;

            if (BlockHelper.mc.thePlayer.canEntityBeSeen(temp))
            {
                return facing;
            }
        }

        return null;
    }

    static
    {
        BlockHelper.mc = Minecraft.getMinecraft();
    }

    public static Block getBlockAtPos(final BlockPos inBlockPos)
    {
        final IBlockState s = BlockHelper.mc.theWorld.getBlockState(inBlockPos);
        return s.getBlock();
    }

    public static Block getBlockAbove(float height) {
        return getBlock(Minecraft.getMinecraft().thePlayer.posX, Minecraft.getMinecraft().thePlayer.posY + height, Minecraft.getMinecraft().thePlayer.posZ);
    }

    public static Block getBlockUnder(final float height) {
        return getBlock(Minecraft.getMinecraft().thePlayer.posX, Minecraft.getMinecraft().thePlayer.posY - height, Minecraft.getMinecraft().thePlayer.posZ);
    }

    public static boolean isBlockUnderPlayer(final Material material, final float height, final BlockPos pos) {
        return getBlockAtPosC(Minecraft.getMinecraft().thePlayer, 0.3100000023841858, height, 0.3100000023841858, pos).getMaterial() == material && getBlockAtPosC(Minecraft.getMinecraft().thePlayer, -0.3100000023841858, height, -0.3100000023841858, pos).getMaterial() == material && getBlockAtPosC(Minecraft.getMinecraft().thePlayer, -0.3100000023841858, height, 0.3100000023841858, pos).getMaterial() == material && getBlockAtPosC(Minecraft.getMinecraft().thePlayer, 0.3100000023841858, height, -0.3100000023841858, pos).getMaterial() == material;
    }
    
    public static Block getBlockAtPosC(final EntityPlayer inPlayer, final double x, final double y, final double z, final BlockPos pos) {
        return Minecraft.getMinecraft().theWorld.getBlockState(pos).getBlock();
    }

    public static Block getBlockUnderPlayer(final EntityPlayer inPlayer, final double height) {
        return getBlock(new BlockPos(inPlayer.posX, inPlayer.posY - height, inPlayer.posZ));
    }
    
    public static Block getBlock(final BlockPos pos) {
        return Minecraft.getMinecraft().theWorld.getBlockState(pos).getBlock();
    }

    public static Block getBlock(Entity entity, double offset) {
        if (entity == null)
            return null;

        final int y = (int) entity.getEntityBoundingBox().offset(0.0D, offset, 0.0D).minY;
        for (int x = MathHelper.floor_double(entity.getEntityBoundingBox().minX); x < MathHelper.floor_double(entity.getEntityBoundingBox().maxX) + 1; x++) {
            for (int z = MathHelper.floor_double(entity.getEntityBoundingBox().minZ); z < MathHelper.floor_double(entity.getEntityBoundingBox().maxZ) + 1; z++) {
                return mc.theWorld.getBlockState(new BlockPos(x, y, z)).getBlock();
            }
        }
        return null;
    }

    public static boolean isInsideBlock() {
        for (int x = MathHelper.floor_double(mc.thePlayer.boundingBox.minX); x < MathHelper.floor_double(mc.thePlayer.boundingBox.maxX) + 1; ++x) {
            for (int y = MathHelper.floor_double(mc.thePlayer.boundingBox.minY); y < MathHelper.floor_double(mc.thePlayer.boundingBox.maxY) + 1; ++y) {
                for (int z = MathHelper.floor_double(mc.thePlayer.boundingBox.minZ); z < MathHelper.floor_double(mc.thePlayer.boundingBox.maxZ) + 1; ++z) {
                    final Block block = mc.theWorld.getBlockState(new BlockPos(x, y, z)).getBlock();
                    if (block != null && !(block instanceof BlockAir)) {
                        AxisAlignedBB boundingBox = block.getCollisionBoundingBox(mc.theWorld, new BlockPos(x, y, z), mc.theWorld.getBlockState(new BlockPos(x, y, z)));
                        if (block instanceof BlockHopper) {
                            boundingBox = new AxisAlignedBB(x, y, z, x + 1, y + 1, z + 1);
                        }
                        if (boundingBox != null && mc.thePlayer.boundingBox.intersectsWith(boundingBox)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
    
    public static float getDistanceToGround(final Entity e) {
        if (mc.thePlayer.isCollidedVertically) {
            return 0.0f;
        }
        float a = (float)e.posY;
        while (a > 0.0f) {
            final int[] stairs = { 53, 67, 108, 109, 114, 128, 134, 135, 136, 156, 163, 164, 180 };
            final int[] exemptIds = { 6, 27, 28, 30, 31, 32, 37, 38, 39, 40, 50, 51, 55, 59, 63, 65, 66, 68, 69, 70, 72, 75, 76, 77, 83, 92, 93, 94, 104, 105, 106, 115, 119, 131, 132, 143, 147, 148, 149, 150, 157, 171, 175, 176, 177 };
            final Block block = BlockHelper.getBlockAtPos(new BlockPos(e.posX, a - 1.0f, e.posZ));
            if (!(block instanceof BlockAir)) {
                if (Block.getIdFromBlock(block) == 44 || Block.getIdFromBlock(block) == 126) {
                    return ((float)(e.posY - a - 0.5) < 0.0f) ? 0.0f : ((float)(e.posY - a - 0.5));
                }
                int[] array;
                for (int length = (array = stairs).length, i = 0; i < length; ++i) {
                    final int id = array[i];
                    if (Block.getIdFromBlock(block) == id) {
                        return ((float)(e.posY - a - 1.0) < 0.0f) ? 0.0f : ((float)(e.posY - a - 1.0));
                    }
                }
                int[] array2;
                for (int length2 = (array2 = exemptIds).length, j = 0; j < length2; ++j) {
                    final int id = array2[j];
                    if (Block.getIdFromBlock(block) == id) {
                        return ((float)(e.posY - a) < 0.0f) ? 0.0f : ((float)(e.posY - a));
                    }
                }
                return (float)(e.posY - a + block.getBlockBoundsMaxY() - 1.0);
            }
            else {
                --a;
            }
        }
        return 0.0f;
    }

    public static Block getBlockAbovePlayer(final EntityPlayer inPlayer, final double height) {
        return getBlock(new BlockPos(inPlayer.posX, inPlayer.posY + inPlayer.height + height, inPlayer.posZ));
    }

    public static Block getBlockAtPosC(final EntityPlayer inPlayer, final double x, final double y, final double z) {
        return getBlock(new BlockPos(inPlayer.posX - x, inPlayer.posY - y, inPlayer.posZ - z));
    }
    
    public static boolean isBlockUnderPlayer(final Material material, final float height) {
        return getBlockAtPosC(mc.thePlayer, 0.3100000023841858, height, 0.3100000023841858).getMaterial() == material && getBlockAtPosC(mc.thePlayer, -0.3100000023841858, height, -0.3100000023841858).getMaterial() == material && getBlockAtPosC(mc.thePlayer, -0.3100000023841858, height, 0.3100000023841858).getMaterial() == material && getBlockAtPosC(mc.thePlayer, 0.3100000023841858, height, -0.3100000023841858).getMaterial() == material;
    }

    public static Block getBlock(final AxisAlignedBB bb) {
        final int y = (int)bb.minY;
        for (int x = MathHelper.floor_double(bb.minX); x < MathHelper.floor_double(bb.maxX) + 1; ++x) {
            for (int z = MathHelper.floor_double(bb.minZ); z < MathHelper.floor_double(bb.maxZ) + 1; ++z) {
                final Block block = mc.theWorld.getBlockState(new BlockPos(x, y, z)).getBlock();
                if (block != null) {
                    return block;
                }
            }
        }
        return null;
    }
    
    public static Block getBlock(final double offset) {
        return getBlock(mc.thePlayer.getEntityBoundingBox().offset(0.0, offset, 0.0));
    }
}