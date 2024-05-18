package HORIZON-6-0-SKIDPROTECTION;

public class ItemSeedFood extends ItemFood
{
    private Block Ø;
    private Block áŒŠÆ;
    private static final String áˆºÑ¢Õ = "CL_00000060";
    
    public ItemSeedFood(final int p_i45351_1_, final float p_i45351_2_, final Block p_i45351_3_, final Block p_i45351_4_) {
        super(p_i45351_1_, p_i45351_2_, false);
        this.Ø = p_i45351_3_;
        this.áŒŠÆ = p_i45351_4_;
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final ItemStack stack, final EntityPlayer playerIn, final World worldIn, final BlockPos pos, final EnumFacing side, final float hitX, final float hitY, final float hitZ) {
        if (side != EnumFacing.Â) {
            return false;
        }
        if (!playerIn.HorizonCode_Horizon_È(pos.HorizonCode_Horizon_È(side), side, stack)) {
            return false;
        }
        if (worldIn.Â(pos).Ý() == this.áŒŠÆ && worldIn.Ø­áŒŠá(pos.Ø­áŒŠá())) {
            worldIn.Â(pos.Ø­áŒŠá(), this.Ø.¥à());
            --stack.Â;
            return true;
        }
        return false;
    }
}
