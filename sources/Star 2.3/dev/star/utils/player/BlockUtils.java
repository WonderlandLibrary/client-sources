package dev.star.utils.player;

import dev.star.utils.Utils;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.util.*;

public class BlockUtils implements Utils {

    public static boolean isValidBlock(BlockPos pos) {
        return isValidBlock(mc.theWorld.getBlockState(pos).getBlock(), false);
    }

    public static Block getBlockAtPos(BlockPos pos) {
        IBlockState blockState = mc.theWorld.getBlockState(pos);
        return blockState.getBlock();
    }

    public static boolean nullCheck() {
        return mc.thePlayer != null && mc.theWorld != null;
    }

    public static boolean isFullBlock(BlockPos blockPos) {
        AxisAlignedBB axisAlignedBB = getBlock(blockPos) != null ? getBlock(blockPos).getCollisionBoundingBox(mc.theWorld, blockPos, getBlockState(blockPos)) : null;
        if (axisAlignedBB == null) {
            return false;
        } else {
            return axisAlignedBB.maxX - axisAlignedBB.minX == 1.0 && axisAlignedBB.maxY - axisAlignedBB.minY == 1.0 && axisAlignedBB.maxZ - axisAlignedBB.minZ == 1.0;
        }
    }

    public static boolean isSamePos(BlockPos blockPos, BlockPos blockPos2) {
        return blockPos == blockPos2 || (blockPos.getX() == blockPos2.getX() && blockPos.getY() == blockPos2.getY() && blockPos.getZ() == blockPos2.getZ());
    }


    public static EnumFacing getHorizontalFacingEnum(BlockPos blockPos) {
        double dx = mc.thePlayer.posX - ((double)blockPos.getX() + 0.5);
        double dz = mc.thePlayer.posZ - ((double)blockPos.getZ() + 0.5);
        if (dx > 0.0) {
            if (dz > dx) {
                return EnumFacing.SOUTH;
            } else {
                return -dz > dx ? EnumFacing.NORTH : EnumFacing.EAST;
            }
        } else if (dz > -dx) {
            return EnumFacing.SOUTH;
        } else {
            return dz < dx ? EnumFacing.NORTH : EnumFacing.WEST;
        }
    }


    public static float getBlockHardness(final Block block, final ItemStack itemStack, boolean ignoreSlow, boolean ignoreGround) {
        final float getBlockHardness = block.getBlockHardness();
        if (getBlockHardness < 0.0f) {
            return 0.0f;
        }
        return (block.getMaterial().isToolNotRequired() || (itemStack != null && itemStack.canHarvestBlock(block))) ? (getToolDigEfficiency(itemStack, block, ignoreSlow, ignoreGround) / getBlockHardness / 30.0f) : (getToolDigEfficiency(itemStack, block, ignoreSlow, ignoreGround) / getBlockHardness / 100.0f);
    }

    public static float[] getFaceRotation(EnumFacing face, BlockPos blockPos) {
        Vec3i faceVec = face.getDirectionVec();
        Vec3 blockFaceVec = new Vec3((double)faceVec.getX() * 0.5, (double)faceVec.getY() * 0.5, (double)faceVec.getZ() * 0.5);
            blockFaceVec.add(blockPos.toVec3());
        blockFaceVec.addVector(0.5, 0.5, 0.5);
        return RotationUtils.getRotations(blockFaceVec);
    }

    public static float getToolDigEfficiency(ItemStack itemStack, Block block, boolean ignoreSlow, boolean ignoreGround) {
        float n = (itemStack == null) ? 1.0f : itemStack.getItem().getStrVsBlock(itemStack, block);
        if (n > 1.0f) {
            final int getEnchantmentLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.efficiency.effectId, itemStack);
            if (getEnchantmentLevel > 0 && itemStack != null) {
                n += getEnchantmentLevel * getEnchantmentLevel + 1;
            }
        }
        if (mc.thePlayer.isPotionActive(Potion.digSpeed)) {
            n *= 1.0f + (mc.thePlayer.getActivePotionEffect(Potion.digSpeed).getAmplifier() + 1) * 0.2f;
        }
        if (!ignoreSlow) {
            if (mc.thePlayer.isPotionActive(Potion.digSlowdown)) {
                float n2;
                switch (mc.thePlayer.getActivePotionEffect(Potion.digSlowdown).getAmplifier()) {
                    case 0: {
                        n2 = 0.3f;
                        break;
                    }
                    case 1: {
                        n2 = 0.09f;
                        break;
                    }
                    case 2: {
                        n2 = 0.0027f;
                        break;
                    }
                    default: {
                        n2 = 8.1E-4f;
                        break;
                    }
                }
                n *= n2;
            }
            if (mc.thePlayer.isInsideOfMaterial(Material.water) && !EnchantmentHelper.getAquaAffinityModifier(mc.thePlayer)) {
                n /= 5.0f;
            }
            if (!mc.thePlayer.onGround && !ignoreGround) {
                n /= 5.0f;
            }
        }
        return n;
    }

    public static Block getBlock(BlockPos blockPos) {
        return getBlockState(blockPos).getBlock();
    }



    public static boolean notFull(Block block) {
        return block instanceof BlockFenceGate || block instanceof BlockLadder || block instanceof BlockFlowerPot || block instanceof BlockBasePressurePlate || isFluid(block) || block instanceof BlockFence || block instanceof BlockAnvil || block instanceof BlockEnchantmentTable || block instanceof BlockChest;
    }


    public static IBlockState getBlockState(BlockPos blockPos) {
        return mc.theWorld.getBlockState(blockPos);
    }


    public static boolean isFluid(Block block) {
        return block.getMaterial() == Material.lava || block.getMaterial() == Material.water;
    }

    public static boolean replaceable(BlockPos blockPos) {
        if (!nullCheck()) {
            return true;
        }
        return getBlockAtPos(blockPos).isReplaceable(mc.theWorld, blockPos);
    }

    public static boolean isValidBlock(Block block, boolean placing) {
        if (block instanceof BlockCarpet
                || block instanceof BlockSnow
                || block instanceof BlockContainer
                || block instanceof BlockBasePressurePlate
                || block.getMaterial().isLiquid()) {
            return false;
        }
        if (placing && (block instanceof BlockSlab
                || block instanceof BlockStairs
                || block instanceof BlockLadder
                || block instanceof BlockStainedGlassPane
                || block instanceof BlockWall
                || block instanceof BlockWeb
                || block instanceof BlockCactus
                || block instanceof BlockFalling
                || block == Blocks.glass_pane
                || block == Blocks.iron_bars)) {
            return false;
        }
        return (block.getMaterial().isSolid() || !block.isTranslucent() || block.isFullBlock());
    }

    public static boolean isInLiquid() {
        if (mc.thePlayer == null) return false;
        for (int x = MathHelper.floor_double(mc.thePlayer.getEntityBoundingBox().minX); x < MathHelper.floor_double(mc.thePlayer.getEntityBoundingBox().maxX) + 1; x++) {
            for (int z = MathHelper.floor_double(mc.thePlayer.getEntityBoundingBox().minZ); z < MathHelper.floor_double(mc.thePlayer.getEntityBoundingBox().maxZ) + 1; z++) {
                BlockPos pos = new BlockPos(x, (int) mc.thePlayer.getEntityBoundingBox().minY, z);
                Block block = mc.theWorld.getBlockState(pos).getBlock();
                if (block != null && !(block instanceof BlockAir)) {
                    return block instanceof BlockLiquid;
                }
            }
        }
        return false;
    }

    public static boolean isOnLiquid() {
        if (mc.thePlayer == null) return false;
        AxisAlignedBB boundingBox = mc.thePlayer.getEntityBoundingBox();
        if (boundingBox != null) {
            boundingBox = boundingBox.contract(0.01D, 0.0D, 0.01D).offset(0.0D, -0.01D, 0.0D);
            boolean onLiquid = false;
            int y = (int) boundingBox.minY;

            for (int x = MathHelper.floor_double(boundingBox.minX); x < MathHelper.floor_double(boundingBox.maxX + 1.0D); ++x) {
                for (int z = MathHelper.floor_double(boundingBox.minZ); z < MathHelper.floor_double(boundingBox.maxZ + 1.0D); ++z) {
                    Block block = mc.theWorld.getBlockState(new BlockPos(x, y, z)).getBlock();
                    if (block != Blocks.air) {
                        if (!(block instanceof BlockLiquid)) return false;
                        onLiquid = true;
                    }
                }
            }

            return onLiquid;
        }
        return false;
    }

}
