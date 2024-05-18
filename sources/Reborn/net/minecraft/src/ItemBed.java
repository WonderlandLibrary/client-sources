package net.minecraft.src;

public class ItemBed extends Item
{
    public ItemBed(final int par1) {
        super(par1);
        this.setCreativeTab(CreativeTabs.tabDecorations);
    }
    
    @Override
    public boolean onItemUse(final ItemStack par1ItemStack, final EntityPlayer par2EntityPlayer, final World par3World, final int par4, int par5, final int par6, final int par7, final float par8, final float par9, final float par10) {
        if (par3World.isRemote) {
            return true;
        }
        if (par7 != 1) {
            return false;
        }
        ++par5;
        final BlockBed var11 = (BlockBed)Block.bed;
        final int var12 = MathHelper.floor_double(par2EntityPlayer.rotationYaw * 4.0f / 360.0f + 0.5) & 0x3;
        byte var13 = 0;
        byte var14 = 0;
        if (var12 == 0) {
            var14 = 1;
        }
        if (var12 == 1) {
            var13 = -1;
        }
        if (var12 == 2) {
            var14 = -1;
        }
        if (var12 == 3) {
            var13 = 1;
        }
        if (!par2EntityPlayer.canPlayerEdit(par4, par5, par6, par7, par1ItemStack) || !par2EntityPlayer.canPlayerEdit(par4 + var13, par5, par6 + var14, par7, par1ItemStack)) {
            return false;
        }
        if (par3World.isAirBlock(par4, par5, par6) && par3World.isAirBlock(par4 + var13, par5, par6 + var14) && par3World.doesBlockHaveSolidTopSurface(par4, par5 - 1, par6) && par3World.doesBlockHaveSolidTopSurface(par4 + var13, par5 - 1, par6 + var14)) {
            par3World.setBlock(par4, par5, par6, var11.blockID, var12, 3);
            if (par3World.getBlockId(par4, par5, par6) == var11.blockID) {
                par3World.setBlock(par4 + var13, par5, par6 + var14, var11.blockID, var12 + 8, 3);
            }
            --par1ItemStack.stackSize;
            return true;
        }
        return false;
    }
}
