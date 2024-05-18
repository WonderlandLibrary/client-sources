package HORIZON-6-0-SKIDPROTECTION;

public abstract class BehaviorProjectileDispense extends BehaviorDefaultDispenseItem
{
    private static final String Â = "CL_00001394";
    
    public ItemStack Â(final IBlockSource source, final ItemStack stack) {
        final World var3 = source.HorizonCode_Horizon_È();
        final IPosition var4 = BlockDispenser.HorizonCode_Horizon_È(source);
        final EnumFacing var5 = BlockDispenser.Âµá€(source.à());
        final IProjectile var6 = this.HorizonCode_Horizon_È(var3, var4);
        var6.a_(var5.Ø(), var5.áŒŠÆ() + 0.1f, var5.áˆºÑ¢Õ(), this.Â(), this.HorizonCode_Horizon_È());
        var3.HorizonCode_Horizon_È((Entity)var6);
        stack.HorizonCode_Horizon_È(1);
        return stack;
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final IBlockSource source) {
        source.HorizonCode_Horizon_È().Â(1002, source.Âµá€(), 0);
    }
    
    protected abstract IProjectile HorizonCode_Horizon_È(final World p0, final IPosition p1);
    
    protected float HorizonCode_Horizon_È() {
        return 6.0f;
    }
    
    protected float Â() {
        return 1.1f;
    }
}
