package HORIZON-6-0-SKIDPROTECTION;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.StringUtils;

public class ResourceLocation_1975012498
{
    protected final String HorizonCode_Horizon_È;
    protected final String Â;
    private static final String Ý = "CL_00001082";
    
    protected ResourceLocation_1975012498(final int p_i45928_1_, final String... p_i45928_2_) {
        this.HorizonCode_Horizon_È = (StringUtils.isEmpty((CharSequence)p_i45928_2_[0]) ? "minecraft" : p_i45928_2_[0].toLowerCase());
        Validate.notNull((Object)(this.Â = p_i45928_2_[1]));
    }
    
    public ResourceLocation_1975012498(final String p_i1293_1_) {
        this(0, Â(p_i1293_1_));
    }
    
    public ResourceLocation_1975012498(final String p_i1292_1_, final String p_i1292_2_) {
        this(0, new String[] { p_i1292_1_, p_i1292_2_ });
    }
    
    protected static String[] Â(final String p_177516_0_) {
        final String[] var1 = { null, p_177516_0_ };
        final int var2 = p_177516_0_.indexOf(58);
        if (var2 >= 0) {
            var1[1] = p_177516_0_.substring(var2 + 1, p_177516_0_.length());
            if (var2 > 1) {
                var1[0] = p_177516_0_.substring(0, var2);
            }
        }
        return var1;
    }
    
    public String Â() {
        return this.Â;
    }
    
    public String Ý() {
        return this.HorizonCode_Horizon_È;
    }
    
    @Override
    public String toString() {
        return String.valueOf(this.HorizonCode_Horizon_È) + ':' + this.Â;
    }
    
    @Override
    public boolean equals(final Object p_equals_1_) {
        if (this == p_equals_1_) {
            return true;
        }
        if (!(p_equals_1_ instanceof ResourceLocation_1975012498)) {
            return false;
        }
        final ResourceLocation_1975012498 var2 = (ResourceLocation_1975012498)p_equals_1_;
        return this.HorizonCode_Horizon_È.equals(var2.HorizonCode_Horizon_È) && this.Â.equals(var2.Â);
    }
    
    @Override
    public int hashCode() {
        return 31 * this.HorizonCode_Horizon_È.hashCode() + this.Â.hashCode();
    }
}
