package HORIZON-6-0-SKIDPROTECTION;

public class SlotFurnaceFuel extends Slot
{
    private static final String HorizonCode_Horizon_È = "CL_00002184";
    
    public SlotFurnaceFuel(final IInventory p_i45795_1_, final int p_i45795_2_, final int p_i45795_3_, final int p_i45795_4_) {
        super(p_i45795_1_, p_i45795_2_, p_i45795_3_, p_i45795_4_);
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final ItemStack stack) {
        return TileEntityFurnace.Ý(stack) || Ø­áŒŠá(stack);
    }
    
    @Override
    public int Ý(final ItemStack p_178170_1_) {
        return Ø­áŒŠá(p_178170_1_) ? 1 : super.Ý(p_178170_1_);
    }
    
    public static boolean Ø­áŒŠá(final ItemStack p_178173_0_) {
        return p_178173_0_ != null && p_178173_0_.HorizonCode_Horizon_È() != null && p_178173_0_.HorizonCode_Horizon_È() == Items.áŒŠáŠ;
    }
}
