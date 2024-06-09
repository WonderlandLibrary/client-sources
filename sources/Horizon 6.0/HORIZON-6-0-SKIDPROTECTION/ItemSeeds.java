package HORIZON-6-0-SKIDPROTECTION;

public class ItemSeeds extends Item_1028566121
{
    private Block à;
    private Block Ø;
    private static final String áŒŠÆ = "CL_00000061";
    
    public ItemSeeds(final Block p_i45352_1_, final Block p_i45352_2_) {
        this.à = p_i45352_1_;
        this.Ø = p_i45352_2_;
        this.HorizonCode_Horizon_È(CreativeTabs.á);
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final ItemStack stack, final EntityPlayer playerIn, final World worldIn, final BlockPos pos, final EnumFacing side, final float hitX, final float hitY, final float hitZ) {
        if (side != EnumFacing.Â) {
            return false;
        }
        if (!playerIn.HorizonCode_Horizon_È(pos.HorizonCode_Horizon_È(side), side, stack)) {
            return false;
        }
        if (worldIn.Â(pos).Ý() == this.Ø && worldIn.Ø­áŒŠá(pos.Ø­áŒŠá())) {
            worldIn.Â(pos.Ø­áŒŠá(), this.à.¥à());
            --stack.Â;
            return true;
        }
        return false;
    }
}
