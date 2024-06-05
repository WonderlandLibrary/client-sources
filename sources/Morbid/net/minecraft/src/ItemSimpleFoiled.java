package net.minecraft.src;

public class ItemSimpleFoiled extends Item
{
    public ItemSimpleFoiled(final int par1) {
        super(par1);
    }
    
    @Override
    public boolean hasEffect(final ItemStack par1ItemStack) {
        return true;
    }
}
