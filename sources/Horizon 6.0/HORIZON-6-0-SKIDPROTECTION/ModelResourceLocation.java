package HORIZON-6-0-SKIDPROTECTION;

import org.apache.commons.lang3.StringUtils;

public class ModelResourceLocation extends ResourceLocation_1975012498
{
    private final String Ý;
    private static final String Ø­áŒŠá = "CL_00002387";
    
    protected ModelResourceLocation(final int p_i46078_1_, final String... p_i46078_2_) {
        super(0, new String[] { p_i46078_2_[0], p_i46078_2_[1] });
        this.Ý = (StringUtils.isEmpty((CharSequence)p_i46078_2_[2]) ? "normal" : p_i46078_2_[2].toLowerCase());
    }
    
    public ModelResourceLocation(final String p_i46079_1_) {
        this(0, HorizonCode_Horizon_È(p_i46079_1_));
    }
    
    public ModelResourceLocation(final ResourceLocation_1975012498 p_i46080_1_, final String p_i46080_2_) {
        this(p_i46080_1_.toString(), p_i46080_2_);
    }
    
    public ModelResourceLocation(final String p_i46081_1_, final String p_i46081_2_) {
        this(0, HorizonCode_Horizon_È(String.valueOf(p_i46081_1_) + '#' + ((p_i46081_2_ == null) ? "normal" : p_i46081_2_)));
    }
    
    protected static String[] HorizonCode_Horizon_È(final String p_177517_0_) {
        final String[] var1 = { null, p_177517_0_, null };
        final int var2 = p_177517_0_.indexOf(35);
        String var3 = p_177517_0_;
        if (var2 >= 0) {
            var1[2] = p_177517_0_.substring(var2 + 1, p_177517_0_.length());
            if (var2 > 1) {
                var3 = p_177517_0_.substring(0, var2);
            }
        }
        System.arraycopy(ResourceLocation_1975012498.Â(var3), 0, var1, 0, 2);
        return var1;
    }
    
    public String HorizonCode_Horizon_È() {
        return this.Ý;
    }
    
    @Override
    public boolean equals(final Object p_equals_1_) {
        if (this == p_equals_1_) {
            return true;
        }
        if (p_equals_1_ instanceof ModelResourceLocation && super.equals(p_equals_1_)) {
            final ModelResourceLocation var2 = (ModelResourceLocation)p_equals_1_;
            return this.Ý.equals(var2.Ý);
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        return 31 * super.hashCode() + this.Ý.hashCode();
    }
    
    @Override
    public String toString() {
        return String.valueOf(super.toString()) + '#' + this.Ý;
    }
}
