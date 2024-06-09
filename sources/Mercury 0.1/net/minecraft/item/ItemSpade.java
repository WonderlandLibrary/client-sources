/*
 * Decompiled with CFR 0.145.
 */
package net.minecraft.item;

import com.google.common.collect.Sets;
import java.util.Set;
import net.minecraft.block.Block;
import net.minecraft.block.BlockGrass;
import net.minecraft.block.BlockMycelium;
import net.minecraft.block.BlockSand;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemTool;

public class ItemSpade
extends ItemTool {
    private static final Set field_150916_c = Sets.newHashSet(Blocks.clay, Blocks.dirt, Blocks.farmland, Blocks.grass, Blocks.gravel, Blocks.mycelium, Blocks.sand, Blocks.snow, Blocks.snow_layer, Blocks.soul_sand);
    private static final String __OBFID = "CL_00000063";

    public ItemSpade(Item.ToolMaterial p_i45353_1_) {
        super(1.0f, p_i45353_1_, field_150916_c);
    }

    @Override
    public boolean canHarvestBlock(Block blockIn) {
        return blockIn == Blocks.snow_layer ? true : blockIn == Blocks.snow;
    }
}

