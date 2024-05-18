package HORIZON-6-0-SKIDPROTECTION;

public class ItemShears extends Item_1028566121
{
    private static final String à = "CL_00000062";
    
    public ItemShears() {
        this.Â(1);
        this.Ø­áŒŠá(238);
        this.HorizonCode_Horizon_È(CreativeTabs.áŒŠÆ);
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final ItemStack stack, final World worldIn, final Block blockIn, final BlockPos pos, final EntityLivingBase playerIn) {
        if (blockIn.Ó() != Material.áˆºÑ¢Õ && blockIn != Blocks.É && blockIn != Blocks.áƒ && blockIn != Blocks.ÇŽà && blockIn != Blocks.áŒŠÈ && blockIn != Blocks.ŠÂµà) {
            return super.HorizonCode_Horizon_È(stack, worldIn, blockIn, pos, playerIn);
        }
        stack.HorizonCode_Horizon_È(1, playerIn);
        return true;
    }
    
    @Override
    public boolean Â(final Block blockIn) {
        return blockIn == Blocks.É || blockIn == Blocks.Œ || blockIn == Blocks.áŒŠÈ;
    }
    
    @Override
    public float HorizonCode_Horizon_È(final ItemStack stack, final Block p_150893_2_) {
        return (p_150893_2_ != Blocks.É && p_150893_2_.Ó() != Material.áˆºÑ¢Õ) ? ((p_150893_2_ == Blocks.ŠÂµà) ? 5.0f : super.HorizonCode_Horizon_È(stack, p_150893_2_)) : 15.0f;
    }
}
