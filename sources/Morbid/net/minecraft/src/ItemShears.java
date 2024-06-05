package net.minecraft.src;

public class ItemShears extends Item
{
    public ItemShears(final int par1) {
        super(par1);
        this.setMaxStackSize(1);
        this.setMaxDamage(238);
        this.setCreativeTab(CreativeTabs.tabTools);
    }
    
    @Override
    public boolean onBlockDestroyed(final ItemStack par1ItemStack, final World par2World, final int par3, final int par4, final int par5, final int par6, final EntityLiving par7EntityLiving) {
        if (par3 != Block.leaves.blockID && par3 != Block.web.blockID && par3 != Block.tallGrass.blockID && par3 != Block.vine.blockID && par3 != Block.tripWire.blockID) {
            return super.onBlockDestroyed(par1ItemStack, par2World, par3, par4, par5, par6, par7EntityLiving);
        }
        par1ItemStack.damageItem(1, par7EntityLiving);
        return true;
    }
    
    @Override
    public boolean canHarvestBlock(final Block par1Block) {
        return par1Block.blockID == Block.web.blockID || par1Block.blockID == Block.redstoneWire.blockID || par1Block.blockID == Block.tripWire.blockID;
    }
    
    @Override
    public float getStrVsBlock(final ItemStack par1ItemStack, final Block par2Block) {
        return (par2Block.blockID != Block.web.blockID && par2Block.blockID != Block.leaves.blockID) ? ((par2Block.blockID == Block.cloth.blockID) ? 5.0f : super.getStrVsBlock(par1ItemStack, par2Block)) : 15.0f;
    }
}
