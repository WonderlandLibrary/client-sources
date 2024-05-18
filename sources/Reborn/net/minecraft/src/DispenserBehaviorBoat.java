package net.minecraft.src;

final class DispenserBehaviorBoat extends BehaviorDefaultDispenseItem
{
    private final BehaviorDefaultDispenseItem defaultDispenserItemBehavior;
    
    DispenserBehaviorBoat() {
        this.defaultDispenserItemBehavior = new BehaviorDefaultDispenseItem();
    }
    
    public ItemStack dispenseStack(final IBlockSource par1IBlockSource, final ItemStack par2ItemStack) {
        final EnumFacing var3 = BlockDispenser.getFacing(par1IBlockSource.getBlockMetadata());
        final World var4 = par1IBlockSource.getWorld();
        final double var5 = par1IBlockSource.getX() + var3.getFrontOffsetX() * 1.125f;
        final double var6 = par1IBlockSource.getY() + var3.getFrontOffsetY() * 1.125f;
        final double var7 = par1IBlockSource.getZ() + var3.getFrontOffsetZ() * 1.125f;
        final int var8 = par1IBlockSource.getXInt() + var3.getFrontOffsetX();
        final int var9 = par1IBlockSource.getYInt() + var3.getFrontOffsetY();
        final int var10 = par1IBlockSource.getZInt() + var3.getFrontOffsetZ();
        final Material var11 = var4.getBlockMaterial(var8, var9, var10);
        double var12;
        if (Material.water.equals(var11)) {
            var12 = 1.0;
        }
        else {
            if (!Material.air.equals(var11) || !Material.water.equals(var4.getBlockMaterial(var8, var9 - 1, var10))) {
                return this.defaultDispenserItemBehavior.dispense(par1IBlockSource, par2ItemStack);
            }
            var12 = 0.0;
        }
        final EntityBoat var13 = new EntityBoat(var4, var5, var6 + var12, var7);
        var4.spawnEntityInWorld(var13);
        par2ItemStack.splitStack(1);
        return par2ItemStack;
    }
    
    @Override
    protected void playDispenseSound(final IBlockSource par1IBlockSource) {
        par1IBlockSource.getWorld().playAuxSFX(1000, par1IBlockSource.getXInt(), par1IBlockSource.getYInt(), par1IBlockSource.getZInt(), 0);
    }
}
