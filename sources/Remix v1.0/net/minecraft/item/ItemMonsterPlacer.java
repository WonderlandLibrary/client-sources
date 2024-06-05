package net.minecraft.item;

import net.minecraft.creativetab.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.init.*;
import net.minecraft.block.state.*;
import net.minecraft.tileentity.*;
import net.minecraft.util.*;
import net.minecraft.block.*;
import net.minecraft.stats.*;
import java.util.*;

public class ItemMonsterPlacer extends Item
{
    public ItemMonsterPlacer() {
        this.setHasSubtypes(true);
        this.setCreativeTab(CreativeTabs.tabMisc);
    }
    
    public static Entity spawnCreature(final World worldIn, final int p_77840_1_, final double p_77840_2_, final double p_77840_4_, final double p_77840_6_) {
        if (!EntityList.entityEggs.containsKey(p_77840_1_)) {
            return null;
        }
        Entity var8 = null;
        for (int var9 = 0; var9 < 1; ++var9) {
            var8 = EntityList.createEntityByID(p_77840_1_, worldIn);
            if (var8 instanceof EntityLivingBase) {
                final EntityLiving var10 = (EntityLiving)var8;
                var8.setLocationAndAngles(p_77840_2_, p_77840_4_, p_77840_6_, MathHelper.wrapAngleTo180_float(worldIn.rand.nextFloat() * 360.0f), 0.0f);
                var10.rotationYawHead = var10.rotationYaw;
                var10.renderYawOffset = var10.rotationYaw;
                var10.func_180482_a(worldIn.getDifficultyForLocation(new BlockPos(var10)), null);
                worldIn.spawnEntityInWorld(var8);
                var10.playLivingSound();
            }
        }
        return var8;
    }
    
    @Override
    public String getItemStackDisplayName(final ItemStack stack) {
        String var2 = ("" + StatCollector.translateToLocal(this.getUnlocalizedName() + ".name")).trim();
        final String var3 = EntityList.getStringFromID(stack.getMetadata());
        if (var3 != null) {
            var2 = var2 + " " + StatCollector.translateToLocal("entity." + var3 + ".name");
        }
        return var2;
    }
    
    @Override
    public int getColorFromItemStack(final ItemStack stack, final int renderPass) {
        final EntityList.EntityEggInfo var3 = EntityList.entityEggs.get(stack.getMetadata());
        return (var3 != null) ? ((renderPass == 0) ? var3.primaryColor : var3.secondaryColor) : 16777215;
    }
    
    @Override
    public boolean onItemUse(final ItemStack stack, final EntityPlayer playerIn, final World worldIn, BlockPos pos, final EnumFacing side, final float hitX, final float hitY, final float hitZ) {
        if (worldIn.isRemote) {
            return true;
        }
        if (!playerIn.func_175151_a(pos.offset(side), side, stack)) {
            return false;
        }
        final IBlockState var9 = worldIn.getBlockState(pos);
        if (var9.getBlock() == Blocks.mob_spawner) {
            final TileEntity var10 = worldIn.getTileEntity(pos);
            if (var10 instanceof TileEntityMobSpawner) {
                final MobSpawnerBaseLogic var11 = ((TileEntityMobSpawner)var10).getSpawnerBaseLogic();
                var11.setEntityName(EntityList.getStringFromID(stack.getMetadata()));
                var10.markDirty();
                worldIn.markBlockForUpdate(pos);
                if (!playerIn.capabilities.isCreativeMode) {
                    --stack.stackSize;
                }
                return true;
            }
        }
        pos = pos.offset(side);
        double var12 = 0.0;
        if (side == EnumFacing.UP && var9 instanceof BlockFence) {
            var12 = 0.5;
        }
        final Entity var13 = spawnCreature(worldIn, stack.getMetadata(), pos.getX() + 0.5, pos.getY() + var12, pos.getZ() + 0.5);
        if (var13 != null) {
            if (var13 instanceof EntityLivingBase && stack.hasDisplayName()) {
                var13.setCustomNameTag(stack.getDisplayName());
            }
            if (!playerIn.capabilities.isCreativeMode) {
                --stack.stackSize;
            }
        }
        return true;
    }
    
    @Override
    public ItemStack onItemRightClick(final ItemStack itemStackIn, final World worldIn, final EntityPlayer playerIn) {
        if (worldIn.isRemote) {
            return itemStackIn;
        }
        final MovingObjectPosition var4 = this.getMovingObjectPositionFromPlayer(worldIn, playerIn, true);
        if (var4 == null) {
            return itemStackIn;
        }
        if (var4.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
            final BlockPos var5 = var4.getBlockPos();
            if (!worldIn.isBlockModifiable(playerIn, var5)) {
                return itemStackIn;
            }
            if (!playerIn.func_175151_a(var5, var4.sideHit, itemStackIn)) {
                return itemStackIn;
            }
            if (worldIn.getBlockState(var5).getBlock() instanceof BlockLiquid) {
                final Entity var6 = spawnCreature(worldIn, itemStackIn.getMetadata(), var5.getX() + 0.5, var5.getY() + 0.5, var5.getZ() + 0.5);
                if (var6 != null) {
                    if (var6 instanceof EntityLivingBase && itemStackIn.hasDisplayName()) {
                        ((EntityLiving)var6).setCustomNameTag(itemStackIn.getDisplayName());
                    }
                    if (!playerIn.capabilities.isCreativeMode) {
                        --itemStackIn.stackSize;
                    }
                    playerIn.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
                }
            }
        }
        return itemStackIn;
    }
    
    @Override
    public void getSubItems(final Item itemIn, final CreativeTabs tab, final List subItems) {
        for (final EntityList.EntityEggInfo var5 : EntityList.entityEggs.values()) {
            subItems.add(new ItemStack(itemIn, 1, var5.spawnedID));
        }
    }
}
