/*
 * Decompiled with CFR 0.143.
 */
package net.minecraft.item;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRedstoneWire;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class ItemShears
extends Item {
    private static final String __OBFID = "CL_00000062";

    public ItemShears() {
        this.setMaxStackSize(1);
        this.setMaxDamage(238);
        this.setCreativeTab(CreativeTabs.tabTools);
    }

    @Override
    public boolean onBlockDestroyed(ItemStack stack, World worldIn, Block blockIn, BlockPos pos, EntityLivingBase playerIn) {
        if (blockIn.getMaterial() != Material.leaves && blockIn != Blocks.web && blockIn != Blocks.tallgrass && blockIn != Blocks.vine && blockIn != Blocks.tripwire && blockIn != Blocks.wool) {
            return super.onBlockDestroyed(stack, worldIn, blockIn, pos, playerIn);
        }
        stack.damageItem(1, playerIn);
        return true;
    }

    @Override
    public boolean canHarvestBlock(Block blockIn) {
        return blockIn == Blocks.web || blockIn == Blocks.redstone_wire || blockIn == Blocks.tripwire;
    }

    @Override
    public float getStrVsBlock(ItemStack stack, Block p_150893_2_) {
        return p_150893_2_ != Blocks.web && p_150893_2_.getMaterial() != Material.leaves ? (p_150893_2_ == Blocks.wool ? 5.0f : super.getStrVsBlock(stack, p_150893_2_)) : 15.0f;
    }
}

