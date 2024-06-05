package net.minecraft.item;

import net.minecraft.block.*;

public class ItemCloth extends ItemBlock
{
    public ItemCloth(final Block p_i45358_1_) {
        super(p_i45358_1_);
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
    }
    
    @Override
    public int getMetadata(final int damage) {
        return damage;
    }
    
    @Override
    public String getUnlocalizedName(final ItemStack stack) {
        return super.getUnlocalizedName() + "." + EnumDyeColor.func_176764_b(stack.getMetadata()).func_176762_d();
    }
}
