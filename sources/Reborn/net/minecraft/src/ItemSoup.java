package net.minecraft.src;

public class ItemSoup extends ItemFood
{
    public ItemSoup(final int par1, final int par2) {
        super(par1, par2, false);
        this.setMaxStackSize(1);
    }
    
    @Override
    public ItemStack onEaten(final ItemStack par1ItemStack, final World par2World, final EntityPlayer par3EntityPlayer) {
        super.onEaten(par1ItemStack, par2World, par3EntityPlayer);
        return new ItemStack(Item.bowlEmpty);
    }
}
