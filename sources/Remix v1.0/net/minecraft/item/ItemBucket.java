package net.minecraft.item;

import net.minecraft.creativetab.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.block.material.*;
import net.minecraft.block.*;
import net.minecraft.block.properties.*;
import net.minecraft.stats.*;
import net.minecraft.init.*;
import net.minecraft.block.state.*;
import net.minecraft.util.*;

public class ItemBucket extends Item
{
    private Block isFull;
    
    public ItemBucket(final Block p_i45331_1_) {
        this.maxStackSize = 1;
        this.isFull = p_i45331_1_;
        this.setCreativeTab(CreativeTabs.tabMisc);
    }
    
    @Override
    public ItemStack onItemRightClick(final ItemStack itemStackIn, final World worldIn, final EntityPlayer playerIn) {
        final boolean var4 = this.isFull == Blocks.air;
        final MovingObjectPosition var5 = this.getMovingObjectPositionFromPlayer(worldIn, playerIn, var4);
        if (var5 == null) {
            return itemStackIn;
        }
        if (var5.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
            final BlockPos var6 = var5.getBlockPos();
            if (!worldIn.isBlockModifiable(playerIn, var6)) {
                return itemStackIn;
            }
            if (var4) {
                if (!playerIn.func_175151_a(var6.offset(var5.sideHit), var5.sideHit, itemStackIn)) {
                    return itemStackIn;
                }
                final IBlockState var7 = worldIn.getBlockState(var6);
                final Material var8 = var7.getBlock().getMaterial();
                if (var8 == Material.water && (int)var7.getValue(BlockLiquid.LEVEL) == 0) {
                    worldIn.setBlockToAir(var6);
                    playerIn.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
                    return this.func_150910_a(itemStackIn, playerIn, Items.water_bucket);
                }
                if (var8 == Material.lava && (int)var7.getValue(BlockLiquid.LEVEL) == 0) {
                    worldIn.setBlockToAir(var6);
                    playerIn.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
                    return this.func_150910_a(itemStackIn, playerIn, Items.lava_bucket);
                }
            }
            else {
                if (this.isFull == Blocks.air) {
                    return new ItemStack(Items.bucket);
                }
                final BlockPos var9 = var6.offset(var5.sideHit);
                if (!playerIn.func_175151_a(var9, var5.sideHit, itemStackIn)) {
                    return itemStackIn;
                }
                if (this.func_180616_a(worldIn, var9) && !playerIn.capabilities.isCreativeMode) {
                    playerIn.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
                    return new ItemStack(Items.bucket);
                }
            }
        }
        return itemStackIn;
    }
    
    private ItemStack func_150910_a(final ItemStack p_150910_1_, final EntityPlayer p_150910_2_, final Item p_150910_3_) {
        if (p_150910_2_.capabilities.isCreativeMode) {
            return p_150910_1_;
        }
        if (--p_150910_1_.stackSize <= 0) {
            return new ItemStack(p_150910_3_);
        }
        if (!p_150910_2_.inventory.addItemStackToInventory(new ItemStack(p_150910_3_))) {
            p_150910_2_.dropPlayerItemWithRandomChoice(new ItemStack(p_150910_3_, 1, 0), false);
        }
        return p_150910_1_;
    }
    
    public boolean func_180616_a(final World worldIn, final BlockPos p_180616_2_) {
        if (this.isFull == Blocks.air) {
            return false;
        }
        final Material var3 = worldIn.getBlockState(p_180616_2_).getBlock().getMaterial();
        final boolean var4 = !var3.isSolid();
        if (!worldIn.isAirBlock(p_180616_2_) && !var4) {
            return false;
        }
        if (worldIn.provider.func_177500_n() && this.isFull == Blocks.flowing_water) {
            final int var5 = p_180616_2_.getX();
            final int var6 = p_180616_2_.getY();
            final int var7 = p_180616_2_.getZ();
            worldIn.playSoundEffect(var5 + 0.5f, var6 + 0.5f, var7 + 0.5f, "random.fizz", 0.5f, 2.6f + (worldIn.rand.nextFloat() - worldIn.rand.nextFloat()) * 0.8f);
            for (int var8 = 0; var8 < 8; ++var8) {
                worldIn.spawnParticle(EnumParticleTypes.SMOKE_LARGE, var5 + Math.random(), var6 + Math.random(), var7 + Math.random(), 0.0, 0.0, 0.0, new int[0]);
            }
        }
        else {
            if (!worldIn.isRemote && var4 && !var3.isLiquid()) {
                worldIn.destroyBlock(p_180616_2_, true);
            }
            worldIn.setBlockState(p_180616_2_, this.isFull.getDefaultState(), 3);
        }
        return true;
    }
}
