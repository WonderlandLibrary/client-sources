package HORIZON-6-0-SKIDPROTECTION;

public class ItemHangingEntity extends Item_1028566121
{
    private final Class à;
    private static final String Ø = "CL_00000038";
    
    public ItemHangingEntity(final Class p_i45342_1_) {
        this.à = p_i45342_1_;
        this.HorizonCode_Horizon_È(CreativeTabs.Ý);
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final ItemStack stack, final EntityPlayer playerIn, final World worldIn, final BlockPos pos, final EnumFacing side, final float hitX, final float hitY, final float hitZ) {
        if (side == EnumFacing.HorizonCode_Horizon_È) {
            return false;
        }
        if (side == EnumFacing.Â) {
            return false;
        }
        final BlockPos var9 = pos.HorizonCode_Horizon_È(side);
        if (!playerIn.HorizonCode_Horizon_È(var9, side, stack)) {
            return false;
        }
        final EntityHanging var10 = this.HorizonCode_Horizon_È(worldIn, var9, side);
        if (var10 != null && var10.à()) {
            if (!worldIn.ŠÄ) {
                worldIn.HorizonCode_Horizon_È(var10);
            }
            --stack.Â;
        }
        return true;
    }
    
    private EntityHanging HorizonCode_Horizon_È(final World worldIn, final BlockPos p_179233_2_, final EnumFacing p_179233_3_) {
        return (this.à == EntityPainting.class) ? new EntityPainting(worldIn, p_179233_2_, p_179233_3_) : ((this.à == EntityItemFrame.class) ? new EntityItemFrame(worldIn, p_179233_2_, p_179233_3_) : null);
    }
}
