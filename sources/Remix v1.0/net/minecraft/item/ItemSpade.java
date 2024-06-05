package net.minecraft.item;

import java.util.*;
import net.minecraft.block.*;
import net.minecraft.init.*;
import com.google.common.collect.*;

public class ItemSpade extends ItemTool
{
    private static final Set field_150916_c;
    
    public ItemSpade(final ToolMaterial p_i45353_1_) {
        super(1.0f, p_i45353_1_, ItemSpade.field_150916_c);
    }
    
    @Override
    public boolean canHarvestBlock(final Block blockIn) {
        return blockIn == Blocks.snow_layer || blockIn == Blocks.snow;
    }
    
    static {
        field_150916_c = Sets.newHashSet((Object[])new Block[] { Blocks.clay, Blocks.dirt, Blocks.farmland, Blocks.grass, Blocks.gravel, Blocks.mycelium, Blocks.sand, Blocks.snow, Blocks.snow_layer, Blocks.soul_sand });
    }
}
