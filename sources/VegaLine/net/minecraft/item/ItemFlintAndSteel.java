/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.item;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemFlintAndSteel
extends Item {
    public ItemFlintAndSteel() {
        this.maxStackSize = 1;
        this.setMaxDamage(64);
        this.setCreativeTab(CreativeTabs.TOOLS);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer stack, World playerIn, BlockPos worldIn, EnumHand pos, EnumFacing hand, float facing, float hitX, float hitY) {
        ItemStack itemstack;
        if (!stack.canPlayerEdit(worldIn = worldIn.offset(hand), hand, itemstack = stack.getHeldItem(pos))) {
            return EnumActionResult.FAIL;
        }
        if (playerIn.getBlockState(worldIn).getMaterial() == Material.AIR) {
            playerIn.playSound(stack, worldIn, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1.0f, itemRand.nextFloat() * 0.4f + 0.8f);
            playerIn.setBlockState(worldIn, Blocks.FIRE.getDefaultState(), 11);
        }
        if (stack instanceof EntityPlayerMP) {
            CriteriaTriggers.field_193137_x.func_193173_a((EntityPlayerMP)stack, worldIn, itemstack);
        }
        itemstack.damageItem(1, stack);
        return EnumActionResult.SUCCESS;
    }
}

