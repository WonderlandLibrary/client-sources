/*
 * Decompiled with CFR 0.143.
 */
package net.minecraft.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;

public class BlockHardenedClay
extends Block {
    private static final String __OBFID = "CL_00000255";

    public BlockHardenedClay() {
        super(Material.rock);
        this.setCreativeTab(CreativeTabs.tabBlock);
    }

    @Override
    public MapColor getMapColor(IBlockState state) {
        return MapColor.adobeColor;
    }
}

