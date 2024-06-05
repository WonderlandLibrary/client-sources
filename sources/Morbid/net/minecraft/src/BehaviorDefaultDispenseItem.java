package net.minecraft.src;

public class BehaviorDefaultDispenseItem implements IBehaviorDispenseItem
{
    @Override
    public final ItemStack dispense(final IBlockSource par1IBlockSource, final ItemStack par2ItemStack) {
        final ItemStack var3 = this.dispenseStack(par1IBlockSource, par2ItemStack);
        this.playDispenseSound(par1IBlockSource);
        this.spawnDispenseParticles(par1IBlockSource, BlockDispenser.getFacing(par1IBlockSource.getBlockMetadata()));
        return var3;
    }
    
    protected ItemStack dispenseStack(final IBlockSource par1IBlockSource, final ItemStack par2ItemStack) {
        final EnumFacing var3 = BlockDispenser.getFacing(par1IBlockSource.getBlockMetadata());
        final IPosition var4 = BlockDispenser.getIPositionFromBlockSource(par1IBlockSource);
        final ItemStack var5 = par2ItemStack.splitStack(1);
        doDispense(par1IBlockSource.getWorld(), var5, 6, var3, var4);
        return par2ItemStack;
    }
    
    public static void doDispense(final World par0World, final ItemStack par1ItemStack, final int par2, final EnumFacing par3EnumFacing, final IPosition par4IPosition) {
        final double var5 = par4IPosition.getX();
        final double var6 = par4IPosition.getY();
        final double var7 = par4IPosition.getZ();
        final EntityItem var8 = new EntityItem(par0World, var5, var6 - 0.3, var7, par1ItemStack);
        final double var9 = par0World.rand.nextDouble() * 0.1 + 0.2;
        var8.motionX = par3EnumFacing.getFrontOffsetX() * var9;
        var8.motionY = 0.20000000298023224;
        var8.motionZ = par3EnumFacing.getFrontOffsetZ() * var9;
        final EntityItem entityItem = var8;
        entityItem.motionX += par0World.rand.nextGaussian() * 0.007499999832361937 * par2;
        final EntityItem entityItem2 = var8;
        entityItem2.motionY += par0World.rand.nextGaussian() * 0.007499999832361937 * par2;
        final EntityItem entityItem3 = var8;
        entityItem3.motionZ += par0World.rand.nextGaussian() * 0.007499999832361937 * par2;
        par0World.spawnEntityInWorld(var8);
    }
    
    protected void playDispenseSound(final IBlockSource par1IBlockSource) {
        par1IBlockSource.getWorld().playAuxSFX(1000, par1IBlockSource.getXInt(), par1IBlockSource.getYInt(), par1IBlockSource.getZInt(), 0);
    }
    
    protected void spawnDispenseParticles(final IBlockSource par1IBlockSource, final EnumFacing par2EnumFacing) {
        par1IBlockSource.getWorld().playAuxSFX(2000, par1IBlockSource.getXInt(), par1IBlockSource.getYInt(), par1IBlockSource.getZInt(), this.func_82488_a(par2EnumFacing));
    }
    
    private int func_82488_a(final EnumFacing par1EnumFacing) {
        return par1EnumFacing.getFrontOffsetX() + 1 + (par1EnumFacing.getFrontOffsetZ() + 1) * 3;
    }
}
