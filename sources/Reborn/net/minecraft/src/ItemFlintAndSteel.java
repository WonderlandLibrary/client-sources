package net.minecraft.src;

public class ItemFlintAndSteel extends Item
{
    public ItemFlintAndSteel(final int par1) {
        super(par1);
        this.maxStackSize = 1;
        this.setMaxDamage(64);
        this.setCreativeTab(CreativeTabs.tabTools);
    }
    
    @Override
    public boolean onItemUse(final ItemStack par1ItemStack, final EntityPlayer par2EntityPlayer, final World par3World, int par4, int par5, int par6, final int par7, final float par8, final float par9, final float par10) {
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
        if (!par2EntityPlayer.canPlayerEdit(par4, par5, par6, par7, par1ItemStack)) {
            return false;
        }
        final int var11 = par3World.getBlockId(par4, par5, par6);
        if (var11 == 0) {
            par3World.playSoundEffect(par4 + 0.5, par5 + 0.5, par6 + 0.5, "fire.ignite", 1.0f, ItemFlintAndSteel.itemRand.nextFloat() * 0.4f + 0.8f);
            par3World.setBlock(par4, par5, par6, Block.fire.blockID);
        }
        par1ItemStack.damageItem(1, par2EntityPlayer);
        return true;
    }
}
