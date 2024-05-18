package HORIZON-6-0-SKIDPROTECTION;

public class ItemMinecart extends Item_1028566121
{
    private static final IBehaviorDispenseItem à;
    private final EntityMinecart.HorizonCode_Horizon_È Ø;
    private static final String áŒŠÆ = "CL_00000049";
    
    static {
        à = new BehaviorDefaultDispenseItem() {
            private final BehaviorDefaultDispenseItem Â = new BehaviorDefaultDispenseItem();
            private static final String Ý = "CL_00000050";
            
            public ItemStack Â(final IBlockSource source, final ItemStack stack) {
                final EnumFacing var3 = BlockDispenser.Âµá€(source.à());
                final World var4 = source.HorizonCode_Horizon_È();
                final double var5 = source.Â() + var3.Ø() * 1.125;
                final double var6 = Math.floor(source.Ý()) + var3.áŒŠÆ();
                final double var7 = source.Ø­áŒŠá() + var3.áˆºÑ¢Õ() * 1.125;
                final BlockPos var8 = source.Âµá€().HorizonCode_Horizon_È(var3);
                final IBlockState var9 = var4.Â(var8);
                final BlockRailBase.HorizonCode_Horizon_È var10 = (BlockRailBase.HorizonCode_Horizon_È)((var9.Ý() instanceof BlockRailBase) ? var9.HorizonCode_Horizon_È(((BlockRailBase)var9.Ý()).È()) : BlockRailBase.HorizonCode_Horizon_È.HorizonCode_Horizon_È);
                double var11;
                if (BlockRailBase.áŒŠÆ(var9)) {
                    if (var10.Ý()) {
                        var11 = 0.6;
                    }
                    else {
                        var11 = 0.1;
                    }
                }
                else {
                    if (var9.Ý().Ó() != Material.HorizonCode_Horizon_È || !BlockRailBase.áŒŠÆ(var4.Â(var8.Âµá€()))) {
                        return this.Â.HorizonCode_Horizon_È(source, stack);
                    }
                    final IBlockState var12 = var4.Â(var8.Âµá€());
                    final BlockRailBase.HorizonCode_Horizon_È var13 = (BlockRailBase.HorizonCode_Horizon_È)((var12.Ý() instanceof BlockRailBase) ? var12.HorizonCode_Horizon_È(((BlockRailBase)var12.Ý()).È()) : BlockRailBase.HorizonCode_Horizon_È.HorizonCode_Horizon_È);
                    if (var3 != EnumFacing.HorizonCode_Horizon_È && var13.Ý()) {
                        var11 = -0.4;
                    }
                    else {
                        var11 = -0.9;
                    }
                }
                final EntityMinecart var14 = EntityMinecart.HorizonCode_Horizon_È(var4, var5, var6 + var11, var7, ((ItemMinecart)stack.HorizonCode_Horizon_È()).Ø);
                if (stack.¥Æ()) {
                    var14.à(stack.µà());
                }
                var4.HorizonCode_Horizon_È(var14);
                stack.HorizonCode_Horizon_È(1);
                return stack;
            }
            
            @Override
            protected void HorizonCode_Horizon_È(final IBlockSource source) {
                source.HorizonCode_Horizon_È().Â(1000, source.Âµá€(), 0);
            }
        };
    }
    
    public ItemMinecart(final EntityMinecart.HorizonCode_Horizon_È p_i45785_1_) {
        this.Ø­áŒŠá = 1;
        this.Ø = p_i45785_1_;
        this.HorizonCode_Horizon_È(CreativeTabs.Âµá€);
        BlockDispenser.ŠÂµà.HorizonCode_Horizon_È(this, ItemMinecart.à);
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final ItemStack stack, final EntityPlayer playerIn, final World worldIn, final BlockPos pos, final EnumFacing side, final float hitX, final float hitY, final float hitZ) {
        final IBlockState var9 = worldIn.Â(pos);
        if (BlockRailBase.áŒŠÆ(var9)) {
            if (!worldIn.ŠÄ) {
                final BlockRailBase.HorizonCode_Horizon_È var10 = (BlockRailBase.HorizonCode_Horizon_È)((var9.Ý() instanceof BlockRailBase) ? var9.HorizonCode_Horizon_È(((BlockRailBase)var9.Ý()).È()) : BlockRailBase.HorizonCode_Horizon_È.HorizonCode_Horizon_È);
                double var11 = 0.0;
                if (var10.Ý()) {
                    var11 = 0.5;
                }
                final EntityMinecart var12 = EntityMinecart.HorizonCode_Horizon_È(worldIn, pos.HorizonCode_Horizon_È() + 0.5, pos.Â() + 0.0625 + var11, pos.Ý() + 0.5, this.Ø);
                if (stack.¥Æ()) {
                    var12.à(stack.µà());
                }
                worldIn.HorizonCode_Horizon_È(var12);
            }
            --stack.Â;
            return true;
        }
        return false;
    }
}
