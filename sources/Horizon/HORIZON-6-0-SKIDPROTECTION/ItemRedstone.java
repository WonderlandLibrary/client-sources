package HORIZON-6-0-SKIDPROTECTION;

public class ItemRedstone extends Item_1028566121
{
    private static final String à = "CL_00000058";
    
    public ItemRedstone() {
        this.HorizonCode_Horizon_È(CreativeTabs.Ø­áŒŠá);
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final ItemStack stack, final EntityPlayer playerIn, final World worldIn, final BlockPos pos, final EnumFacing side, final float hitX, final float hitY, final float hitZ) {
        final boolean var9 = worldIn.Â(pos).Ý().HorizonCode_Horizon_È(worldIn, pos);
        final BlockPos var10 = var9 ? pos : pos.HorizonCode_Horizon_È(side);
        if (!playerIn.HorizonCode_Horizon_È(var10, side, stack)) {
            return false;
        }
        final Block var11 = worldIn.Â(var10).Ý();
        if (!worldIn.HorizonCode_Horizon_È(var11, var10, false, side, null, stack)) {
            return false;
        }
        if (Blocks.Œ.Ø­áŒŠá(worldIn, var10)) {
            --stack.Â;
            worldIn.Â(var10, Blocks.Œ.¥à());
            return true;
        }
        return false;
    }
}
