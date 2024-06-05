package net.minecraft.item;

import net.minecraft.block.*;

public class ItemAnvilBlock extends ItemMultiTexture
{
    public ItemAnvilBlock(final Block p_i1826_1_) {
        super(p_i1826_1_, p_i1826_1_, new String[] { "intact", "slightlyDamaged", "veryDamaged" });
    }
    
    @Override
    public int getMetadata(final int damage) {
        return damage << 2;
    }
}
