package net.minecraft.src;

final class DispenserBehaviorDye extends BehaviorDefaultDispenseItem
{
    private boolean field_96461_b;
    
    DispenserBehaviorDye() {
        this.field_96461_b = true;
    }
    
    @Override
    protected ItemStack dispenseStack(final IBlockSource par1IBlockSource, final ItemStack par2ItemStack) {
        if (par2ItemStack.getItemDamage() == 15) {
            final EnumFacing var3 = BlockDispenser.getFacing(par1IBlockSource.getBlockMetadata());
            final World var4 = par1IBlockSource.getWorld();
            final int var5 = par1IBlockSource.getXInt() + var3.getFrontOffsetX();
            final int var6 = par1IBlockSource.getYInt() + var3.getFrontOffsetY();
            final int var7 = par1IBlockSource.getZInt() + var3.getFrontOffsetZ();
            if (ItemDye.func_96604_a(par2ItemStack, var4, var5, var6, var7)) {
                if (!var4.isRemote) {
                    var4.playAuxSFX(2005, var5, var6, var7, 0);
                }
            }
            else {
                this.field_96461_b = false;
            }
            return par2ItemStack;
        }
        return super.dispenseStack(par1IBlockSource, par2ItemStack);
    }
    
    @Override
    protected void playDispenseSound(final IBlockSource par1IBlockSource) {
        if (this.field_96461_b) {
            par1IBlockSource.getWorld().playAuxSFX(1000, par1IBlockSource.getXInt(), par1IBlockSource.getYInt(), par1IBlockSource.getZInt(), 0);
        }
        else {
            par1IBlockSource.getWorld().playAuxSFX(1001, par1IBlockSource.getXInt(), par1IBlockSource.getYInt(), par1IBlockSource.getZInt(), 0);
        }
    }
}
