package net.minecraft.item;

import net.minecraft.world.*;
import net.minecraft.block.material.*;
import net.minecraft.entity.player.*;
import net.minecraft.creativetab.*;
import net.minecraft.util.*;
import net.minecraft.block.*;
import net.minecraft.block.properties.*;
import net.minecraft.stats.*;
import net.minecraft.init.*;
import net.minecraft.block.state.*;

public class ItemBucket extends Item
{
    private static final String[] I;
    private Block isFull;
    
    public boolean tryPlaceContainedLiquid(final World world, final BlockPos blockPos) {
        if (this.isFull == Blocks.air) {
            return "".length() != 0;
        }
        final Material material = world.getBlockState(blockPos).getBlock().getMaterial();
        int n;
        if (material.isSolid()) {
            n = "".length();
            "".length();
            if (0 <= -1) {
                throw null;
            }
        }
        else {
            n = " ".length();
        }
        final int n2 = n;
        if (!world.isAirBlock(blockPos) && n2 == 0) {
            return "".length() != 0;
        }
        if (world.provider.doesWaterVaporize() && this.isFull == Blocks.flowing_water) {
            final int x = blockPos.getX();
            final int y = blockPos.getY();
            final int z = blockPos.getZ();
            world.playSoundEffect(x + 0.5f, y + 0.5f, z + 0.5f, ItemBucket.I["".length()], 0.5f, 2.6f + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.8f);
            int i = "".length();
            "".length();
            if (-1 >= 4) {
                throw null;
            }
            while (i < (0xB2 ^ 0xBA)) {
                world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, x + Math.random(), y + Math.random(), z + Math.random(), 0.0, 0.0, 0.0, new int["".length()]);
                ++i;
            }
            "".length();
            if (3 != 3) {
                throw null;
            }
        }
        else {
            if (!world.isRemote && n2 != 0 && !material.isLiquid()) {
                world.destroyBlock(blockPos, " ".length() != 0);
            }
            world.setBlockState(blockPos, this.isFull.getDefaultState(), "   ".length());
        }
        return " ".length() != 0;
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("\u000b4\u0002!\t\u0014{\n,\u001c\u0003", "yUlEf");
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (3 < -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    private ItemStack fillBucket(final ItemStack itemStack, final EntityPlayer entityPlayer, final Item item) {
        if (entityPlayer.capabilities.isCreativeMode) {
            return itemStack;
        }
        if ((itemStack.stackSize -= " ".length()) <= 0) {
            return new ItemStack(item);
        }
        if (!entityPlayer.inventory.addItemStackToInventory(new ItemStack(item))) {
            entityPlayer.dropPlayerItemWithRandomChoice(new ItemStack(item, " ".length(), "".length()), "".length() != 0);
        }
        return itemStack;
    }
    
    static {
        I();
    }
    
    public ItemBucket(final Block isFull) {
        this.maxStackSize = " ".length();
        this.isFull = isFull;
        this.setCreativeTab(CreativeTabs.tabMisc);
    }
    
    @Override
    public ItemStack onItemRightClick(final ItemStack itemStack, final World world, final EntityPlayer entityPlayer) {
        int n;
        if (this.isFull == Blocks.air) {
            n = " ".length();
            "".length();
            if (0 < -1) {
                throw null;
            }
        }
        else {
            n = "".length();
        }
        final int n2 = n;
        final MovingObjectPosition movingObjectPositionFromPlayer = this.getMovingObjectPositionFromPlayer(world, entityPlayer, n2 != 0);
        if (movingObjectPositionFromPlayer == null) {
            return itemStack;
        }
        if (movingObjectPositionFromPlayer.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
            final BlockPos blockPos = movingObjectPositionFromPlayer.getBlockPos();
            if (!world.isBlockModifiable(entityPlayer, blockPos)) {
                return itemStack;
            }
            if (n2 != 0) {
                if (!entityPlayer.canPlayerEdit(blockPos.offset(movingObjectPositionFromPlayer.sideHit), movingObjectPositionFromPlayer.sideHit, itemStack)) {
                    return itemStack;
                }
                final IBlockState blockState = world.getBlockState(blockPos);
                final Material material = blockState.getBlock().getMaterial();
                if (material == Material.water && blockState.getValue((IProperty<Integer>)BlockLiquid.LEVEL) == 0) {
                    world.setBlockToAir(blockPos);
                    entityPlayer.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
                    return this.fillBucket(itemStack, entityPlayer, Items.water_bucket);
                }
                if (material == Material.lava && blockState.getValue((IProperty<Integer>)BlockLiquid.LEVEL) == 0) {
                    world.setBlockToAir(blockPos);
                    entityPlayer.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
                    return this.fillBucket(itemStack, entityPlayer, Items.lava_bucket);
                }
            }
            else {
                if (this.isFull == Blocks.air) {
                    return new ItemStack(Items.bucket);
                }
                final BlockPos offset = blockPos.offset(movingObjectPositionFromPlayer.sideHit);
                if (!entityPlayer.canPlayerEdit(offset, movingObjectPositionFromPlayer.sideHit, itemStack)) {
                    return itemStack;
                }
                if (this.tryPlaceContainedLiquid(world, offset) && !entityPlayer.capabilities.isCreativeMode) {
                    entityPlayer.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
                    return new ItemStack(Items.bucket);
                }
            }
        }
        return itemStack;
    }
}
