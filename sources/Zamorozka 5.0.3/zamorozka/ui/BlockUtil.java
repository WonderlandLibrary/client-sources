package zamorozka.ui;

import net.minecraft.block.*;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;
import java.util.Objects;

public class BlockUtil implements MCUtil {
    public static boolean collideBlock(AxisAlignedBB axisAlignedBB, Collidable collide) {
        for (int x = MathHelper.floor(Minecraft.player.getEntityBoundingBox().minX); x < MathHelper
                .floor(Minecraft.player.getEntityBoundingBox().maxX) + 1; x++) {
            for (int z = MathHelper.floor(Minecraft.player.getEntityBoundingBox().minZ); z < MathHelper
                    .floor(Minecraft.player.getEntityBoundingBox().maxZ) + 1; z++) {
                Block block = getBlock(new BlockPos(x, axisAlignedBB.minY, z));

                if (!collide.collideBlock(block))
                    return false;
            }
        }
        return true;
    }
    
	public static float getHorizontalPlayerBlockDistance(BlockPos blockPos) {
		float xDiff = (float) (mc.player.posX - blockPos.getX());
		float zDiff = (float) (mc.player.posZ - blockPos.getZ());
		return MathHelper.sqrt(((xDiff - 0.5F) * (xDiff - 0.5F)) + ((zDiff - 0.5F) * (zDiff - 0.5F)));
	}

    public static boolean isOnIce(Entity entity) {
        if (entity == null)
            return false;
        boolean onIce = false;
        final int y = (int) entity.getEntityBoundingBox().offset(0.0D, -0.01D, 0.0D).minY;
        for (int x = MathHelper.floor(entity.getEntityBoundingBox().minX); x < MathHelper
                .floor(entity.getEntityBoundingBox().maxX) + 1; x++) {
            for (int z = MathHelper.floor(entity.getEntityBoundingBox().minZ); z < MathHelper
                    .floor(entity.getEntityBoundingBox().maxZ) + 1; z++) {
                final Block block = mc.world.getBlockState(new BlockPos(x, y, z)).getBlock();
                if (block != null && !(block instanceof BlockAir)) {
                    if (!(block instanceof BlockIce) && !(block instanceof BlockPackedIce))
                        return false;
                    onIce = true;
                }
            }
        }
        return onIce;
    }
    
	public static EnumFacing getFacingDirection(BlockPos pos) {
        EnumFacing direction = null;
        if (!mc.world.getBlockState(pos.add(0, 1, 0)).getBlock().isBlockSolid(mc.world, pos, direction)) {
            direction = EnumFacing.UP;
        }
        RayTraceResult rayResult = mc.world.rayTraceBlocks(new net.minecraft.util.math.Vec3d(mc.player.posX, mc.player.posY + mc.player.getEyeHeight(), mc.player.posZ), new Vec3d(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5));
        if (rayResult != null) {
            return rayResult.sideHit;
        }
        return direction;
    }

    public static boolean isOnLadder(Entity entity) {
        if (entity == null)
            return false;
        boolean onLadder = false;
        final int y = (int) entity.getEntityBoundingBox().offset(0.0D, 1.0D, 0.0D).minY;
        for (int x = MathHelper.floor(entity.getEntityBoundingBox().minX); x < MathHelper
                .floor(entity.getEntityBoundingBox().maxX) + 1; x++) {
            for (int z = MathHelper.floor(entity.getEntityBoundingBox().minZ); z < MathHelper
                    .floor(entity.getEntityBoundingBox().maxZ) + 1; z++) {
                final Block block = mc.world.getBlockState(new BlockPos(x, y, z)).getBlock();
                if (Objects.nonNull(block) && !(block instanceof BlockAir)) {
                    if (!(block instanceof BlockLadder || block instanceof BlockVine))
                        return false;
                    onLadder = true;
                }
            }
        }
        return onLadder || Minecraft.player.isOnLadder();
    }

    public static boolean isOnLiquid(double profondeur) {
        return mc.world.getBlockState(new BlockPos(Minecraft.player.posX, Minecraft.player.posY - profondeur, Minecraft.player.posZ))
                .getBlock().getMaterial(null).isLiquid();
    }

    public static boolean isTotalOnLiquid(double profondeur) {
        for (double x = Minecraft.player.boundingBox.minX; x < Minecraft.player.boundingBox.maxX; x += 0.01f) {

            for (double z = Minecraft.player.boundingBox.minZ; z < Minecraft.player.boundingBox.maxZ; z += 0.01f) {
                Block block = mc.world.getBlockState(new BlockPos(x, Minecraft.player.posY - profondeur, z)).getBlock();
                if (!(block instanceof BlockLiquid) && !(block instanceof BlockAir)) {
                    return false;
                }
            }
        }
        return true;
    }

    public static Block getBlock(BlockPos pos) {
        return getState(pos).getBlock();
    }

    public static IBlockState getState(BlockPos pos) {
        return mc.world.getBlockState(pos);
    }

    public static ArrayList<BlockPos> getAllInBox(BlockPos from, BlockPos to) {
        ArrayList<BlockPos> blocks = new ArrayList<>();

        BlockPos min = new BlockPos(Math.min(from.getX(), to.getX()), Math.min(from.getY(), to.getY()),
                Math.min(from.getZ(), to.getZ()));
        BlockPos max = new BlockPos(Math.max(from.getX(), to.getX()), Math.max(from.getY(), to.getY()),
                Math.max(from.getZ(), to.getZ()));

        for (int x = min.getX(); x <= max.getX(); x++)
            for (int y = min.getY(); y <= max.getY(); y++)
                for (int z = min.getZ(); z <= max.getZ(); z++)
                    blocks.add(new BlockPos(x, y, z));

        return blocks;
    }

    public static Block getBlock(final int x, final int y, final int z) {
        return mc.world.getBlockState(new BlockPos(x, y, z)).getBlock();
    }

    public static boolean canSeeBlock(float x, float y, float z) {
        return getFacing(new BlockPos(x, y, z)) != null;
    }

    public static EnumFacing getFacing(BlockPos pos) {
        EnumFacing[] orderedValues = new EnumFacing[]{EnumFacing.UP, EnumFacing.NORTH, EnumFacing.EAST,
                EnumFacing.SOUTH, EnumFacing.WEST, EnumFacing.DOWN};
        EnumFacing[] var2 = orderedValues;
        int var3 = orderedValues.length;
        for (int var4 = 0; var4 < var3; ++var4) {
            EnumFacing facing = var2[var4];
            EntitySnowball temp = new EntitySnowball(mc.world);
            temp.posX = (double) pos.getX() + 0.5D;
            temp.posY = (double) pos.getY() + 0.5D;
            temp.posZ = (double) pos.getZ() + 0.5D;
            temp.posX += (double) facing.getDirectionVec().getX() * 0.5D;
            temp.posY += (double) facing.getDirectionVec().getY() * 0.5D;
            temp.posZ += (double) facing.getDirectionVec().getZ() * 0.5D;
            if (Minecraft.player.canEntityBeSeen(temp)) {
                return facing;
            }
        }

        return null;
    }

    public interface Collidable {
        boolean collideBlock(Block block);
    }

}
