package net.minecraft.src;

public class ItemSnow extends ItemBlockWithMetadata
{
    public ItemSnow(final int par1, final Block par2Block) {
        super(par1, par2Block);
    }
    
    @Override
    public boolean onItemUse(final ItemStack par1ItemStack, final EntityPlayer par2EntityPlayer, final World par3World, final int par4, final int par5, final int par6, final int par7, final float par8, final float par9, final float par10) {
        if (par1ItemStack.stackSize == 0) {
            return false;
        }
        if (!par2EntityPlayer.canPlayerEdit(par4, par5, par6, par7, par1ItemStack)) {
            return false;
        }
        final int var11 = par3World.getBlockId(par4, par5, par6);
        if (var11 == Block.snow.blockID) {
            final Block var12 = Block.blocksList[this.getBlockID()];
            final int var13 = par3World.getBlockMetadata(par4, par5, par6);
            final int var14 = var13 & 0x7;
            if (var14 <= 6 && par3World.checkNoEntityCollision(var12.getCollisionBoundingBoxFromPool(par3World, par4, par5, par6)) && par3World.setBlockMetadataWithNotify(par4, par5, par6, var14 + 1 | (var13 & 0xFFFFFFF8), 2)) {
                par3World.playSoundEffect(par4 + 0.5f, par5 + 0.5f, par6 + 0.5f, var12.stepSound.getPlaceSound(), (var12.stepSound.getVolume() + 1.0f) / 2.0f, var12.stepSound.getPitch() * 0.8f);
                --par1ItemStack.stackSize;
                return true;
            }
        }
        return super.onItemUse(par1ItemStack, par2EntityPlayer, par3World, par4, par5, par6, par7, par8, par9, par10);
    }
}
