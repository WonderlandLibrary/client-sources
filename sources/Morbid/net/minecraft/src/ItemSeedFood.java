package net.minecraft.src;

public class ItemSeedFood extends ItemFood
{
    private int cropId;
    private int soilId;
    
    public ItemSeedFood(final int par1, final int par2, final float par3, final int par4, final int par5) {
        super(par1, par2, par3, false);
        this.cropId = par4;
        this.soilId = par5;
    }
    
    @Override
    public boolean onItemUse(final ItemStack par1ItemStack, final EntityPlayer par2EntityPlayer, final World par3World, final int par4, final int par5, final int par6, final int par7, final float par8, final float par9, final float par10) {
        if (par7 != 1) {
            return false;
        }
        if (!par2EntityPlayer.canPlayerEdit(par4, par5, par6, par7, par1ItemStack) || !par2EntityPlayer.canPlayerEdit(par4, par5 + 1, par6, par7, par1ItemStack)) {
            return false;
        }
        final int var11 = par3World.getBlockId(par4, par5, par6);
        if (var11 == this.soilId && par3World.isAirBlock(par4, par5 + 1, par6)) {
            par3World.setBlock(par4, par5 + 1, par6, this.cropId);
            --par1ItemStack.stackSize;
            return true;
        }
        return false;
    }
}
