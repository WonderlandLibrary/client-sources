/*
 * Decompiled with CFR 0_118.
 */
package net.minecraft.item;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEndPortalFrame;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderEye;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatBase;
import net.minecraft.stats.StatList;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class ItemEnderEye
extends Item {
    private static final String __OBFID = "CL_00000026";

    public ItemEnderEye() {
        this.setCreativeTab(CreativeTabs.tabMisc);
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {
        IBlockState var9 = worldIn.getBlockState(pos);
        if (playerIn.func_175151_a(pos.offset(side), side, stack) && var9.getBlock() == Blocks.end_portal_frame && !((Boolean)var9.getValue(BlockEndPortalFrame.field_176507_b)).booleanValue()) {
            if (worldIn.isRemote) {
                return true;
            }
            worldIn.setBlockState(pos, var9.withProperty(BlockEndPortalFrame.field_176507_b, Boolean.valueOf(true)), 2);
            worldIn.updateComparatorOutputLevel(pos, Blocks.end_portal_frame);
            --stack.stackSize;
            int var10 = 0;
            while (var10 < 16) {
                double var11 = (float)pos.getX() + (5.0f + itemRand.nextFloat() * 6.0f) / 16.0f;
                double var13 = (float)pos.getY() + 0.8125f;
                double var15 = (float)pos.getZ() + (5.0f + itemRand.nextFloat() * 6.0f) / 16.0f;
                double var17 = 0.0;
                double var19 = 0.0;
                double var21 = 0.0;
                worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, var11, var13, var15, var17, var19, var21, new int[0]);
                ++var10;
            }
            EnumFacing var23 = (EnumFacing)((Object)var9.getValue(BlockEndPortalFrame.field_176508_a));
            int var24 = 0;
            int var12 = 0;
            boolean var25 = false;
            boolean var14 = true;
            EnumFacing var26 = var23.rotateY();
            int var16 = -2;
            while (var16 <= 2) {
                BlockPos var28 = pos.offset(var26, var16);
                IBlockState var18 = worldIn.getBlockState(var28);
                if (var18.getBlock() == Blocks.end_portal_frame) {
                    if (!((Boolean)var18.getValue(BlockEndPortalFrame.field_176507_b)).booleanValue()) {
                        var14 = false;
                        break;
                    }
                    var12 = var16;
                    if (!var25) {
                        var24 = var16;
                        var25 = true;
                    }
                }
                ++var16;
            }
            if (var14 && var12 == var24 + 2) {
                BlockPos var33;
                BlockPos var27 = pos.offset(var23, 4);
                int var29 = var24;
                while (var29 <= var12) {
                    BlockPos var30 = var27.offset(var26, var29);
                    IBlockState var32 = worldIn.getBlockState(var30);
                    if (var32.getBlock() != Blocks.end_portal_frame || !((Boolean)var32.getValue(BlockEndPortalFrame.field_176507_b)).booleanValue()) {
                        var14 = false;
                        break;
                    }
                    ++var29;
                }
                var29 = var24 - 1;
                while (var29 <= var12 + 1) {
                    var27 = pos.offset(var26, var29);
                    int var31 = 1;
                    while (var31 <= 3) {
                        var33 = var27.offset(var23, var31);
                        IBlockState var20 = worldIn.getBlockState(var33);
                        if (var20.getBlock() != Blocks.end_portal_frame || !((Boolean)var20.getValue(BlockEndPortalFrame.field_176507_b)).booleanValue()) {
                            var14 = false;
                            break;
                        }
                        ++var31;
                    }
                    var29 += 4;
                }
                if (var14) {
                    var29 = var24;
                    while (var29 <= var12) {
                        var27 = pos.offset(var26, var29);
                        int var31 = 1;
                        while (var31 <= 3) {
                            var33 = var27.offset(var23, var31);
                            worldIn.setBlockState(var33, Blocks.end_portal.getDefaultState(), 2);
                            ++var31;
                        }
                        ++var29;
                    }
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn) {
        BlockPos var5;
        MovingObjectPosition var4 = this.getMovingObjectPositionFromPlayer(worldIn, playerIn, false);
        if (var4 != null && var4.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK && worldIn.getBlockState(var4.func_178782_a()).getBlock() == Blocks.end_portal_frame) {
            return itemStackIn;
        }
        if (!worldIn.isRemote && (var5 = worldIn.func_180499_a("Stronghold", new BlockPos(playerIn))) != null) {
            EntityEnderEye var6 = new EntityEnderEye(worldIn, playerIn.posX, playerIn.posY, playerIn.posZ);
            var6.func_180465_a(var5);
            worldIn.spawnEntityInWorld(var6);
            worldIn.playSoundAtEntity(playerIn, "random.bow", 0.5f, 0.4f / (itemRand.nextFloat() * 0.4f + 0.8f));
            worldIn.playAuxSFXAtEntity(null, 1002, new BlockPos(playerIn), 0);
            if (!playerIn.capabilities.isCreativeMode) {
                --itemStackIn.stackSize;
            }
            playerIn.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
        }
        return itemStackIn;
    }
}

