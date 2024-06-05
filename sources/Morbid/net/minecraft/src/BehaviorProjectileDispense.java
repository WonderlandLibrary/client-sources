package net.minecraft.src;

public abstract class BehaviorProjectileDispense extends BehaviorDefaultDispenseItem
{
    public ItemStack dispenseStack(final IBlockSource par1IBlockSource, final ItemStack par2ItemStack) {
        final World var3 = par1IBlockSource.getWorld();
        final IPosition var4 = BlockDispenser.getIPositionFromBlockSource(par1IBlockSource);
        final EnumFacing var5 = BlockDispenser.getFacing(par1IBlockSource.getBlockMetadata());
        final IProjectile var6 = this.getProjectileEntity(var3, var4);
        var6.setThrowableHeading(var5.getFrontOffsetX(), var5.getFrontOffsetY() + 0.1f, var5.getFrontOffsetZ(), this.func_82500_b(), this.func_82498_a());
        var3.spawnEntityInWorld((Entity)var6);
        par2ItemStack.splitStack(1);
        return par2ItemStack;
    }
    
    @Override
    protected void playDispenseSound(final IBlockSource par1IBlockSource) {
        par1IBlockSource.getWorld().playAuxSFX(1002, par1IBlockSource.getXInt(), par1IBlockSource.getYInt(), par1IBlockSource.getZInt(), 0);
    }
    
    protected abstract IProjectile getProjectileEntity(final World p0, final IPosition p1);
    
    protected float func_82498_a() {
        return 6.0f;
    }
    
    protected float func_82500_b() {
        return 1.1f;
    }
}
