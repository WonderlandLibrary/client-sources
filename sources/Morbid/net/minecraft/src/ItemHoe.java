package net.minecraft.src;

public class ItemHoe extends Item
{
    protected EnumToolMaterial theToolMaterial;
    
    public ItemHoe(final int par1, final EnumToolMaterial par2EnumToolMaterial) {
        super(par1);
        this.theToolMaterial = par2EnumToolMaterial;
        this.maxStackSize = 1;
        this.setMaxDamage(par2EnumToolMaterial.getMaxUses());
        this.setCreativeTab(CreativeTabs.tabTools);
    }
    
    @Override
    public boolean onItemUse(final ItemStack par1ItemStack, final EntityPlayer par2EntityPlayer, final World par3World, final int par4, final int par5, final int par6, final int par7, final float par8, final float par9, final float par10) {
        if (!par2EntityPlayer.canPlayerEdit(par4, par5, par6, par7, par1ItemStack)) {
            return false;
        }
        final int var11 = par3World.getBlockId(par4, par5, par6);
        final int var12 = par3World.getBlockId(par4, par5 + 1, par6);
        if ((par7 == 0 || var12 != 0 || var11 != Block.grass.blockID) && var11 != Block.dirt.blockID) {
            return false;
        }
        final Block var13 = Block.tilledField;
        par3World.playSoundEffect(par4 + 0.5f, par5 + 0.5f, par6 + 0.5f, var13.stepSound.getStepSound(), (var13.stepSound.getVolume() + 1.0f) / 2.0f, var13.stepSound.getPitch() * 0.8f);
        if (par3World.isRemote) {
            return true;
        }
        par3World.setBlock(par4, par5, par6, var13.blockID);
        par1ItemStack.damageItem(1, par2EntityPlayer);
        return true;
    }
    
    @Override
    public boolean isFull3D() {
        return true;
    }
    
    public String getMaterialName() {
        return this.theToolMaterial.toString();
    }
}
