package net.minecraft.src;

public class ItemSign extends Item
{
    public ItemSign(final int par1) {
        super(par1);
        this.maxStackSize = 16;
        this.setCreativeTab(CreativeTabs.tabDecorations);
    }
    
    @Override
    public boolean onItemUse(final ItemStack par1ItemStack, final EntityPlayer par2EntityPlayer, final World par3World, int par4, int par5, int par6, final int par7, final float par8, final float par9, final float par10) {
        if (par7 == 0) {
            return false;
        }
        if (!par3World.getBlockMaterial(par4, par5, par6).isSolid()) {
            return false;
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
        if (!par2EntityPlayer.canPlayerEdit(par4, par5, par6, par7, par1ItemStack)) {
            return false;
        }
        if (!Block.signPost.canPlaceBlockAt(par3World, par4, par5, par6)) {
            return false;
        }
        if (par7 == 1) {
            final int var11 = MathHelper.floor_double((par2EntityPlayer.rotationYaw + 180.0f) * 16.0f / 360.0f + 0.5) & 0xF;
            par3World.setBlock(par4, par5, par6, Block.signPost.blockID, var11, 2);
        }
        else {
            par3World.setBlock(par4, par5, par6, Block.signWall.blockID, par7, 2);
        }
        --par1ItemStack.stackSize;
        final TileEntitySign var12 = (TileEntitySign)par3World.getBlockTileEntity(par4, par5, par6);
        if (var12 != null) {
            par2EntityPlayer.displayGUIEditSign(var12);
        }
        return true;
    }
}
