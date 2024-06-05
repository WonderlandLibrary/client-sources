package net.minecraft.item;

import com.google.common.base.*;
import net.minecraft.block.*;
import net.minecraft.world.*;

public class ItemDoublePlant extends ItemMultiTexture
{
    public ItemDoublePlant(final Block p_i45787_1_, final Block p_i45787_2_, final Function p_i45787_3_) {
        super(p_i45787_1_, p_i45787_2_, p_i45787_3_);
    }
    
    @Override
    public int getColorFromItemStack(final ItemStack stack, final int renderPass) {
        final BlockDoublePlant.EnumPlantType var3 = BlockDoublePlant.EnumPlantType.func_176938_a(stack.getMetadata());
        return (var3 != BlockDoublePlant.EnumPlantType.GRASS && var3 != BlockDoublePlant.EnumPlantType.FERN) ? super.getColorFromItemStack(stack, renderPass) : ColorizerGrass.getGrassColor(0.5, 1.0);
    }
}
