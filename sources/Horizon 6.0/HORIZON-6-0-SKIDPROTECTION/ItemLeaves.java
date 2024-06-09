package HORIZON-6-0-SKIDPROTECTION;

public class ItemLeaves extends ItemBlock
{
    private final BlockLeaves Ø;
    private static final String áŒŠÆ = "CL_00000046";
    
    public ItemLeaves(final BlockLeaves p_i45344_1_) {
        super(p_i45344_1_);
        this.Ø = p_i45344_1_;
        this.Ø­áŒŠá(0);
        this.HorizonCode_Horizon_È(true);
    }
    
    @Override
    public int Ý(final int damage) {
        return damage | 0x4;
    }
    
    @Override
    public int HorizonCode_Horizon_È(final ItemStack stack, final int renderPass) {
        return this.Ø.Âµá€(this.Ø.Ý(stack.Ø()));
    }
    
    @Override
    public String Â(final ItemStack stack) {
        return String.valueOf(super.Ø()) + "." + this.Ø.Âµá€(stack.Ø()).Ý();
    }
}
