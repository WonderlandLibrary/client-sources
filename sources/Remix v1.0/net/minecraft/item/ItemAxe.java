package net.minecraft.item;

import java.util.*;
import net.minecraft.block.*;
import net.minecraft.block.material.*;
import net.minecraft.init.*;
import com.google.common.collect.*;

public class ItemAxe extends ItemTool
{
    private static final Set field_150917_c;
    
    protected ItemAxe(final ToolMaterial p_i45327_1_) {
        super(3.0f, p_i45327_1_, ItemAxe.field_150917_c);
    }
    
    @Override
    public float getStrVsBlock(final ItemStack stack, final Block p_150893_2_) {
        return (p_150893_2_.getMaterial() != Material.wood && p_150893_2_.getMaterial() != Material.plants && p_150893_2_.getMaterial() != Material.vine) ? super.getStrVsBlock(stack, p_150893_2_) : this.efficiencyOnProperMaterial;
    }
    
    static {
        field_150917_c = Sets.newHashSet((Object[])new Block[] { Blocks.planks, Blocks.bookshelf, Blocks.log, Blocks.log2, Blocks.chest, Blocks.pumpkin, Blocks.lit_pumpkin, Blocks.melon_block, Blocks.ladder });
    }
}
