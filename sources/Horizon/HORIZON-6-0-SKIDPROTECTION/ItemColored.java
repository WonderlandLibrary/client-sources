package HORIZON-6-0-SKIDPROTECTION;

public class ItemColored extends ItemBlock
{
    private final Block Ø;
    private String[] áŒŠÆ;
    private static final String áˆºÑ¢Õ = "CL_00000003";
    
    public ItemColored(final Block p_i45332_1_, final boolean p_i45332_2_) {
        super(p_i45332_1_);
        this.Ø = p_i45332_1_;
        if (p_i45332_2_) {
            this.Ø­áŒŠá(0);
            this.HorizonCode_Horizon_È(true);
        }
    }
    
    @Override
    public int HorizonCode_Horizon_È(final ItemStack stack, final int renderPass) {
        return this.Ø.Âµá€(this.Ø.Ý(stack.Ø()));
    }
    
    @Override
    public int Ý(final int damage) {
        return damage;
    }
    
    public ItemColored HorizonCode_Horizon_È(final String[] p_150943_1_) {
        this.áŒŠÆ = p_150943_1_;
        return this;
    }
    
    @Override
    public String Â(final ItemStack stack) {
        if (this.áŒŠÆ == null) {
            return super.Â(stack);
        }
        final int var2 = stack.Ø();
        return (var2 >= 0 && var2 < this.áŒŠÆ.length) ? (String.valueOf(super.Â(stack)) + "." + this.áŒŠÆ[var2]) : super.Â(stack);
    }
}
