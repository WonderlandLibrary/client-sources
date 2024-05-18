package net.minecraft.src;

public class ItemRedstone extends Item
{
    public ItemRedstone(final int par1) {
        super(par1);
        this.setCreativeTab(CreativeTabs.tabRedstone);
    }
    
    @Override
    public boolean onItemUse(final ItemStack par1ItemStack, final EntityPlayer par2EntityPlayer, final World par3World, int par4, int par5, int par6, final int par7, final float par8, final float par9, final float par10) {
        if (par3World.getBlockId(par4, par5, par6) != Block.snow.blockID) {
            if (par7 == 0) {
                --par5;
            }
            if (par7 == 1) {
                ++par5;
            }
            if (par7 == 2) {
                --par6;
            }
            if (par7 == 3) {
                ++par6;
            }
            if (par7 == 4) {
                --par4;
            }
            if (par7 == 5) {
                ++par4;
            }
            if (!par3World.isAirBlock(par4, par5, par6)) {
                return false;
            }
        }
        if (!par2EntityPlayer.canPlayerEdit(par4, par5, par6, par7, par1ItemStack)) {
            return false;
        }
        if (Block.redstoneWire.canPlaceBlockAt(par3World, par4, par5, par6)) {
            --par1ItemStack.stackSize;
            par3World.setBlock(par4, par5, par6, Block.redstoneWire.blockID);
        }
        return true;
    }
}
