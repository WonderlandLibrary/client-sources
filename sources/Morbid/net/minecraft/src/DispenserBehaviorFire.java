package net.minecraft.src;

final class DispenserBehaviorFire extends BehaviorDefaultDispenseItem
{
    private boolean field_96466_b;
    
    DispenserBehaviorFire() {
        this.field_96466_b = true;
    }
    
    @Override
    protected ItemStack dispenseStack(final IBlockSource par1IBlockSource, final ItemStack par2ItemStack) {
        final EnumFacing var3 = BlockDispenser.getFacing(par1IBlockSource.getBlockMetadata());
        final World var4 = par1IBlockSource.getWorld();
        final int var5 = par1IBlockSource.getXInt() + var3.getFrontOffsetX();
        final int var6 = par1IBlockSource.getYInt() + var3.getFrontOffsetY();
        final int var7 = par1IBlockSource.getZInt() + var3.getFrontOffsetZ();
        if (var4.isAirBlock(var5, var6, var7)) {
            var4.setBlock(var5, var6, var7, Block.fire.blockID);
            if (par2ItemStack.attemptDamageItem(1, var4.rand)) {
                par2ItemStack.stackSize = 0;
            }
        }
        else if (var4.getBlockId(var5, var6, var7) == Block.tnt.blockID) {
            Block.tnt.onBlockDestroyedByPlayer(var4, var5, var6, var7, 1);
            var4.setBlockToAir(var5, var6, var7);
        }
        else {
            this.field_96466_b = false;
        }
        return par2ItemStack;
    }
    
    @Override
    protected void playDispenseSound(final IBlockSource par1IBlockSource) {
        if (this.field_96466_b) {
            par1IBlockSource.getWorld().playAuxSFX(1000, par1IBlockSource.getXInt(), par1IBlockSource.getYInt(), par1IBlockSource.getZInt(), 0);
        }
        else {
            par1IBlockSource.getWorld().playAuxSFX(1001, par1IBlockSource.getXInt(), par1IBlockSource.getYInt(), par1IBlockSource.getZInt(), 0);
        }
    }
}
