package HORIZON-6-0-SKIDPROTECTION;

public class ItemCloth extends ItemBlock
{
    private static final String Ø = "CL_00000075";
    
    public ItemCloth(final Block p_i45358_1_) {
        super(p_i45358_1_);
        this.Ø­áŒŠá(0);
        this.HorizonCode_Horizon_È(true);
    }
    
    @Override
    public int Ý(final int damage) {
        return damage;
    }
    
    @Override
    public String Â(final ItemStack stack) {
        return String.valueOf(super.Ø()) + "." + EnumDyeColor.Â(stack.Ø()).Ø­áŒŠá();
    }
}
