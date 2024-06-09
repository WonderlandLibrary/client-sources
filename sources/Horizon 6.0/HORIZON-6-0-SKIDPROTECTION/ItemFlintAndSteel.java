package HORIZON-6-0-SKIDPROTECTION;

public class ItemFlintAndSteel extends Item_1028566121
{
    private static final String à = "CL_00000035";
    
    public ItemFlintAndSteel() {
        this.Ø­áŒŠá = 1;
        this.Ø­áŒŠá(64);
        this.HorizonCode_Horizon_È(CreativeTabs.áŒŠÆ);
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final ItemStack stack, final EntityPlayer playerIn, final World worldIn, BlockPos pos, final EnumFacing side, final float hitX, final float hitY, final float hitZ) {
        pos = pos.HorizonCode_Horizon_È(side);
        if (!playerIn.HorizonCode_Horizon_È(pos, side, stack)) {
            return false;
        }
        if (worldIn.Â(pos).Ý().Ó() == Material.HorizonCode_Horizon_È) {
            worldIn.HorizonCode_Horizon_È(pos.HorizonCode_Horizon_È() + 0.5, pos.Â() + 0.5, pos.Ý() + 0.5, "fire.ignite", 1.0f, ItemFlintAndSteel.Ý.nextFloat() * 0.4f + 0.8f);
            worldIn.Â(pos, Blocks.Ô.¥à());
        }
        stack.HorizonCode_Horizon_È(1, playerIn);
        return true;
    }
}
