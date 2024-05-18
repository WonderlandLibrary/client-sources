package HORIZON-6-0-SKIDPROTECTION;

public class ItemFireball extends Item_1028566121
{
    private static final String à = "CL_00000029";
    
    public ItemFireball() {
        this.HorizonCode_Horizon_È(CreativeTabs.Ó);
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final ItemStack stack, final EntityPlayer playerIn, final World worldIn, BlockPos pos, final EnumFacing side, final float hitX, final float hitY, final float hitZ) {
        if (worldIn.ŠÄ) {
            return true;
        }
        pos = pos.HorizonCode_Horizon_È(side);
        if (!playerIn.HorizonCode_Horizon_È(pos, side, stack)) {
            return false;
        }
        if (worldIn.Â(pos).Ý().Ó() == Material.HorizonCode_Horizon_È) {
            worldIn.HorizonCode_Horizon_È(pos.HorizonCode_Horizon_È() + 0.5, pos.Â() + 0.5, pos.Ý() + 0.5, "item.fireCharge.use", 1.0f, (ItemFireball.Ý.nextFloat() - ItemFireball.Ý.nextFloat()) * 0.2f + 1.0f);
            worldIn.Â(pos, Blocks.Ô.¥à());
        }
        if (!playerIn.áˆºáˆºáŠ.Ø­áŒŠá) {
            --stack.Â;
        }
        return true;
    }
}
