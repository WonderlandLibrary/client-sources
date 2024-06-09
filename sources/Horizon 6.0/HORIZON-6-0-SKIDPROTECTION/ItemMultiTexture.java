package HORIZON-6-0-SKIDPROTECTION;

import com.google.common.base.Function;

public class ItemMultiTexture extends ItemBlock
{
    protected final Block Ø;
    protected final Function áŒŠÆ;
    private static final String áˆºÑ¢Õ = "CL_00000051";
    
    public ItemMultiTexture(final Block p_i45784_1_, final Block p_i45784_2_, final Function p_i45784_3_) {
        super(p_i45784_1_);
        this.Ø = p_i45784_2_;
        this.áŒŠÆ = p_i45784_3_;
        this.Ø­áŒŠá(0);
        this.HorizonCode_Horizon_È(true);
    }
    
    public ItemMultiTexture(final Block p_i45346_1_, final Block p_i45346_2_, final String[] p_i45346_3_) {
        this(p_i45346_1_, p_i45346_2_, (Function)new Function() {
            private static final String HorizonCode_Horizon_È = "CL_00002161";
            
            public String HorizonCode_Horizon_È(final ItemStack p_179541_1_) {
                int var2 = p_179541_1_.Ø();
                if (var2 < 0 || var2 >= p_i45346_3_.length) {
                    var2 = 0;
                }
                return p_i45346_3_[var2];
            }
            
            public Object apply(final Object p_apply_1_) {
                return this.HorizonCode_Horizon_È((ItemStack)p_apply_1_);
            }
        });
    }
    
    @Override
    public int Ý(final int damage) {
        return damage;
    }
    
    @Override
    public String Â(final ItemStack stack) {
        return String.valueOf(super.Ø()) + "." + (String)this.áŒŠÆ.apply((Object)stack);
    }
}
