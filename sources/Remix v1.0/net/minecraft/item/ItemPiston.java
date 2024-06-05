package net.minecraft.item;

import net.minecraft.block.*;

public class ItemPiston extends ItemBlock
{
    public ItemPiston(final Block p_i45348_1_) {
        super(p_i45348_1_);
    }
    
    @Override
    public int getMetadata(final int damage) {
        return 7;
    }
}
