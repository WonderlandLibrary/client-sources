package HORIZON-6-0-SKIDPROTECTION;

public class BlockDropper extends BlockDispenser
{
    private final IBehaviorDispenseItem Âµà;
    private static final String Ç = "CL_00000233";
    
    public BlockDropper() {
        this.Âµà = new BehaviorDefaultDispenseItem();
    }
    
    @Override
    protected IBehaviorDispenseItem HorizonCode_Horizon_È(final ItemStack p_149940_1_) {
        return this.Âµà;
    }
    
    @Override
    public TileEntity HorizonCode_Horizon_È(final World worldIn, final int meta) {
        return new TileEntityDropper();
    }
    
    @Override
    protected void áŒŠÆ(final World worldIn, final BlockPos p_176439_2_) {
        final BlockSourceImpl var3 = new BlockSourceImpl(worldIn, p_176439_2_);
        final TileEntityDispenser var4 = (TileEntityDispenser)var3.Ø();
        if (var4 != null) {
            final int var5 = var4.Ø­à();
            if (var5 < 0) {
                worldIn.Â(1001, p_176439_2_, 0);
            }
            else {
                final ItemStack var6 = var4.á(var5);
                if (var6 != null) {
                    final EnumFacing var7 = (EnumFacing)worldIn.Â(p_176439_2_).HorizonCode_Horizon_È(BlockDropper.Õ);
                    final BlockPos var8 = p_176439_2_.HorizonCode_Horizon_È(var7);
                    final IInventory var9 = TileEntityHopper.Â(worldIn, var8.HorizonCode_Horizon_È(), var8.Â(), var8.Ý());
                    ItemStack var10;
                    if (var9 == null) {
                        var10 = this.Âµà.HorizonCode_Horizon_È(var3, var6);
                        if (var10 != null && var10.Â == 0) {
                            var10 = null;
                        }
                    }
                    else {
                        var10 = TileEntityHopper.HorizonCode_Horizon_È(var9, var6.áˆºÑ¢Õ().HorizonCode_Horizon_È(1), var7.Âµá€());
                        if (var10 == null) {
                            final ItemStack áˆºÑ¢Õ;
                            var10 = (áˆºÑ¢Õ = var6.áˆºÑ¢Õ());
                            if (--áˆºÑ¢Õ.Â == 0) {
                                var10 = null;
                            }
                        }
                        else {
                            var10 = var6.áˆºÑ¢Õ();
                        }
                    }
                    var4.Ý(var5, var10);
                }
            }
        }
    }
}
