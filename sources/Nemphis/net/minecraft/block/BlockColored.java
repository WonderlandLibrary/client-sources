/*
 * Decompiled with CFR 0_118.
 */
package net.minecraft.block;

import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class BlockColored
extends Block {
    public static final PropertyEnum COLOR = PropertyEnum.create("color", EnumDyeColor.class);
    private static final String __OBFID = "CL_00000217";

    public BlockColored(Material p_i45398_1_) {
        super(p_i45398_1_);
        this.setDefaultState(this.blockState.getBaseState().withProperty(COLOR, (Comparable)((Object)EnumDyeColor.WHITE)));
        this.setCreativeTab(CreativeTabs.tabBlock);
    }

    @Override
    public int damageDropped(IBlockState state) {
        return ((EnumDyeColor)((Object)state.getValue(COLOR))).func_176765_a();
    }

    @Override
    public void getSubBlocks(Item itemIn, CreativeTabs tab, List list) {
        EnumDyeColor[] var4 = EnumDyeColor.values();
        int var5 = var4.length;
        int var6 = 0;
        while (var6 < var5) {
            EnumDyeColor var7 = var4[var6];
            list.add(new ItemStack(itemIn, 1, var7.func_176765_a()));
            ++var6;
        }
    }

    @Override
    public MapColor getMapColor(IBlockState state) {
        return ((EnumDyeColor)((Object)state.getValue(COLOR))).func_176768_e();
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(COLOR, (Comparable)((Object)EnumDyeColor.func_176764_b(meta)));
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return ((EnumDyeColor)((Object)state.getValue(COLOR))).func_176765_a();
    }

    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, COLOR);
    }
}

