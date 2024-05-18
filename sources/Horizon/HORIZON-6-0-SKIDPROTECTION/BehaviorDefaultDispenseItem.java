package HORIZON-6-0-SKIDPROTECTION;

public class BehaviorDefaultDispenseItem implements IBehaviorDispenseItem
{
    private static final String Â = "CL_00001195";
    
    @Override
    public final ItemStack HorizonCode_Horizon_È(final IBlockSource source, final ItemStack stack) {
        final ItemStack var3 = this.Â(source, stack);
        this.HorizonCode_Horizon_È(source);
        this.HorizonCode_Horizon_È(source, BlockDispenser.Âµá€(source.à()));
        return var3;
    }
    
    protected ItemStack Â(final IBlockSource source, final ItemStack stack) {
        final EnumFacing var3 = BlockDispenser.Âµá€(source.à());
        final IPosition var4 = BlockDispenser.HorizonCode_Horizon_È(source);
        final ItemStack var5 = stack.HorizonCode_Horizon_È(1);
        HorizonCode_Horizon_È(source.HorizonCode_Horizon_È(), var5, 6, var3, var4);
        return stack;
    }
    
    public static void HorizonCode_Horizon_È(final World worldIn, final ItemStack stack, final int speed, final EnumFacing p_82486_3_, final IPosition position) {
        final double var5 = position.Â();
        double var6 = position.Ý();
        final double var7 = position.Ø­áŒŠá();
        if (p_82486_3_.á() == EnumFacing.HorizonCode_Horizon_È.Â) {
            var6 -= 0.125;
        }
        else {
            var6 -= 0.15625;
        }
        final EntityItem var8 = new EntityItem(worldIn, var5, var6, var7, stack);
        final double var9 = worldIn.Å.nextDouble() * 0.1 + 0.2;
        var8.ÇŽÉ = p_82486_3_.Ø() * var9;
        var8.ˆá = 0.20000000298023224;
        var8.ÇŽÕ = p_82486_3_.áˆºÑ¢Õ() * var9;
        final EntityItem entityItem = var8;
        entityItem.ÇŽÉ += worldIn.Å.nextGaussian() * 0.007499999832361937 * speed;
        final EntityItem entityItem2 = var8;
        entityItem2.ˆá += worldIn.Å.nextGaussian() * 0.007499999832361937 * speed;
        final EntityItem entityItem3 = var8;
        entityItem3.ÇŽÕ += worldIn.Å.nextGaussian() * 0.007499999832361937 * speed;
        worldIn.HorizonCode_Horizon_È(var8);
    }
    
    protected void HorizonCode_Horizon_È(final IBlockSource source) {
        source.HorizonCode_Horizon_È().Â(1000, source.Âµá€(), 0);
    }
    
    protected void HorizonCode_Horizon_È(final IBlockSource source, final EnumFacing facingIn) {
        source.HorizonCode_Horizon_È().Â(2000, source.Âµá€(), this.HorizonCode_Horizon_È(facingIn));
    }
    
    private int HorizonCode_Horizon_È(final EnumFacing facingIn) {
        return facingIn.Ø() + 1 + (facingIn.áˆºÑ¢Õ() + 1) * 3;
    }
}
