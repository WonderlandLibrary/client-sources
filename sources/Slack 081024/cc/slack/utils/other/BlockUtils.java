package cc.slack.utils.other;

import cc.slack.utils.client.IMinecraft;
import cc.slack.utils.rotations.RotationUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBed;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.util.*;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;


public class BlockUtils implements IMinecraft {
    public Block getBlock(Vec3 vec3) {
        return getBlock(new BlockPos(vec3));
    }
    public static Block getBlock(BlockPos blockPos) {
        if (mc.theWorld != null && blockPos != null) {
            return mc.theWorld.getBlockState(blockPos).getBlock();
        }
        return null;
    }

    public static Material getMaterial(BlockPos blockPos) {
        Block block = getBlock(blockPos);
        if (block != null) {
            return block.getMaterial();
        }
        return null;
    }

    public static float getHardness(BlockPos blockPos) {
        return getBlock(blockPos).getPlayerRelativeBlockHardness(mc.thePlayer, mc.theWorld, blockPos);
    }

    public static boolean isReplaceable(BlockPos blockPos) {
        Material material = getMaterial(blockPos);
        return material != null && material.isReplaceable();
    }

    public static boolean isReplaceableNotBed(BlockPos blockPos) {
        Material material = getMaterial(blockPos);
        return material != null && material.isReplaceable() && !(getBlock(blockPos) instanceof BlockBed) ;
    }

    public static boolean isAir(BlockPos blockPos) {
        Material material = getMaterial(blockPos);
        return  material == Material.air;
    }

    public static IBlockState getState(BlockPos blockPos) {
        World mc = Minecraft.getMinecraft().theWorld;
        return mc.getBlockState(blockPos);
    }

    public static boolean canBeClicked(BlockPos blockPos) {
        return (getBlock(blockPos) != null && Objects.requireNonNull(getBlock(blockPos)).canCollideCheck(getState(blockPos), false)) &&
                mc.theWorld.getWorldBorder().contains(blockPos);
    }

    public static String getBlockName(int id) {
        return Block.getBlockById(id).getLocalizedName();
    }

    public static boolean isFullBlock(BlockPos blockPos) {
        AxisAlignedBB axisAlignedBB = getBlock(blockPos) != null ? getBlock(blockPos).getCollisionBoundingBox(mc.theWorld, blockPos, getState(blockPos)) : null;
        if (axisAlignedBB == null) return false;
        return axisAlignedBB.maxX - axisAlignedBB.minX == 1.0 && axisAlignedBB.maxY - axisAlignedBB.minY == 1.0 && axisAlignedBB.maxZ - axisAlignedBB.minZ == 1.0;
    }

    public static double getCenterDistance(BlockPos blockPos) {
        return mc.thePlayer.getDistance(blockPos.getX() + 0.5, blockPos.getY() + 0.5, blockPos.getZ() + 0.5);
    }

    public static float[] getCenterRotation(BlockPos blockPos) {
        return RotationUtil.getRotations(blockPos.getX() + 0.5, blockPos.getY() + 0.5, blockPos.getZ() + 0.5);
    }

    public static float[] getFaceRotation(EnumFacing face, BlockPos blockPos) {
        Vec3i faceVec = face.getDirectionVec();
        Vec3 blockFaceVec = new Vec3(faceVec.getX() * 0.5, faceVec.getY() * 0.5, faceVec.getZ() * 0.5);
        blockFaceVec = blockFaceVec.add(blockPos.toVec3());
        blockFaceVec = blockFaceVec.addVector(0.5, 0.5, 0.5);
        return RotationUtil.getRotations(blockFaceVec);

    }

    public static int getAbsoluteValue(BlockPos blockPos) {
        return blockPos.getX() + blockPos.getY() * 1000 + blockPos.getZ() * 200000;
    }

    public static EnumFacing getHorizontalFacingEnum(BlockPos blockPos) {
        return getHorizontalFacingEnum(blockPos, mc.thePlayer.posX, mc.thePlayer.posZ);
    }

    public static double getScaffoldPriority(BlockPos blockPos) {
        return getCenterDistance(blockPos) + Math.abs(MathHelper.wrapAngleTo180_double(getCenterRotation(blockPos)[0] - (mc.thePlayer.rotationYaw + 180)))/130;
    }

    public static EnumFacing getHorizontalFacingEnum(BlockPos blockPos, double x, double z) {
        double dx = x - (blockPos.getX() + 0.5);
        double dz = z - (blockPos.getZ() + 0.5);

        if (dx > 0) {
            if (dz > dx) {
                return EnumFacing.SOUTH;
            } else if (-dz > dx) {
                return EnumFacing.NORTH;
            } else {
                return EnumFacing.EAST;
            }
        } else {
            if (dz > -dx) {
                return EnumFacing.SOUTH;
            } else if (dz < dx) {
                return EnumFacing.NORTH;
            } else {
                return EnumFacing.WEST;
            }
        }
    }

    public static Map<BlockPos, Block> searchBlocks(int radius) {
        Map<BlockPos, Block> blocks = new HashMap<>();

        for (int x = radius; x >= -radius + 1; x--) {
            for (int y = radius; y >= -radius + 1; y--) {
                for (int z = radius; z >= -radius + 1; z--) {
                    BlockPos blockPos = new BlockPos(mc.thePlayer.posX + x, mc.thePlayer.posY + y, mc.thePlayer.posZ + z);
                    Block block = getBlock(blockPos);
                    if (block != null) {
                        blocks.put(blockPos, block);
                    }
                }
            }
        }

        return blocks;
    }

    public static boolean collideBlock(AxisAlignedBB axisAlignedBB, Function<Block, Boolean> collide) {
        for (int x = MathHelper.floor_double(mc.thePlayer.getEntityBoundingBox().minX); x <
                MathHelper.floor_double(mc.thePlayer.getEntityBoundingBox().maxX) + 1; x++) {
            for (int z = MathHelper.floor_double(mc.thePlayer.getEntityBoundingBox().minZ); z <
                    MathHelper.floor_double(mc.thePlayer.getEntityBoundingBox().maxZ) + 1; z++) {
                Block block = getBlock(new BlockPos(x, axisAlignedBB.minY, z));

                if (!collide.apply(block)) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean collideBlockIntersects(AxisAlignedBB axisAlignedBB, Predicate<Block> collide) {
        for (int x = MathHelper.floor_double(mc.thePlayer.getEntityBoundingBox().minX); x <
                MathHelper.floor_double(mc.thePlayer.getEntityBoundingBox().maxX) + 1; x++) {
            for (int z = MathHelper.floor_double(mc.thePlayer.getEntityBoundingBox().minZ); z <
                    MathHelper.floor_double(mc.thePlayer.getEntityBoundingBox().maxZ) + 1; z++) {
                BlockPos blockPos = new BlockPos(x, axisAlignedBB.minY, z);
                Block block = getBlock(blockPos);

                if (collide.test(block)) {
                    AxisAlignedBB boundingBox = block != null ? block.getCollisionBoundingBox(mc.theWorld, blockPos, getState(blockPos)) : null;
                    if (boundingBox == null) continue;

                    if (mc.thePlayer.getEntityBoundingBox().intersectsWith(boundingBox)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    public static Vec3 floorVec3(Vec3 vec3) {
        return new Vec3(Math.floor(vec3.xCoord), Math.floor(vec3.yCoord), Math.floor(vec3.zCoord));
    }
}
