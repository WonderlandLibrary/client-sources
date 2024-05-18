package net.minecraft.src;

public class ItemSeeds extends Item
{
    private int blockType;
    private int soilBlockID;
    
    public ItemSeeds(final int par1, final int par2, final int par3) {
        super(par1);
        this.blockType = par2;
        this.soilBlockID = par3;
        this.setCreativeTab(CreativeTabs.tabMaterials);
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
        if (var11 == this.soilBlockID && par3World.isAirBlock(par4, par5 + 1, par6)) {
            par3World.setBlock(par4, par5 + 1, par6, this.blockType);
            --par1ItemStack.stackSize;
            return true;
        }
        return false;
    }
}
