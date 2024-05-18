package net.minecraft.src;

public class ItemReed extends Item
{
    private int spawnID;
    
    public ItemReed(final int par1, final Block par2Block) {
        super(par1);
        this.spawnID = par2Block.blockID;
    }
    
    @Override
    public boolean onItemUse(final ItemStack par1ItemStack, final EntityPlayer par2EntityPlayer, final World par3World, int par4, int par5, int par6, int par7, final float par8, final float par9, final float par10) {
        final int var11 = par3World.getBlockId(par4, par5, par6);
        if (var11 == Block.snow.blockID && (par3World.getBlockMetadata(par4, par5, par6) & 0x7) < 1) {
            par7 = 1;
        }
        else if (var11 != Block.vine.blockID && var11 != Block.tallGrass.blockID && var11 != Block.deadBush.blockID) {
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
        }
        if (!par2EntityPlayer.canPlayerEdit(par4, par5, par6, par7, par1ItemStack)) {
            return false;
        }
        if (par1ItemStack.stackSize == 0) {
            return false;
        }
        if (par3World.canPlaceEntityOnSide(this.spawnID, par4, par5, par6, false, par7, null, par1ItemStack)) {
            final Block var12 = Block.blocksList[this.spawnID];
            final int var13 = var12.onBlockPlaced(par3World, par4, par5, par6, par7, par8, par9, par10, 0);
            if (par3World.setBlock(par4, par5, par6, this.spawnID, var13, 3)) {
                if (par3World.getBlockId(par4, par5, par6) == this.spawnID) {
                    Block.blocksList[this.spawnID].onBlockPlacedBy(par3World, par4, par5, par6, par2EntityPlayer, par1ItemStack);
                    Block.blocksList[this.spawnID].onPostBlockPlaced(par3World, par4, par5, par6, var13);
                }
                par3World.playSoundEffect(par4 + 0.5f, par5 + 0.5f, par6 + 0.5f, var12.stepSound.getPlaceSound(), (var12.stepSound.getVolume() + 1.0f) / 2.0f, var12.stepSound.getPitch() * 0.8f);
                --par1ItemStack.stackSize;
            }
        }
        return true;
    }
}
