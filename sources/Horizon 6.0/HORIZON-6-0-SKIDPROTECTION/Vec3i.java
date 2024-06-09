package HORIZON-6-0-SKIDPROTECTION;

import com.google.common.base.Objects;

public class Vec3i implements Comparable
{
    public static final Vec3i Âµá€;
    private final int HorizonCode_Horizon_È;
    private final int Â;
    private final int Ý;
    private static final String Ø­áŒŠá = "CL_00002315";
    
    static {
        Âµá€ = new Vec3i(0, 0, 0);
    }
    
    public Vec3i(final int p_i46007_1_, final int p_i46007_2_, final int p_i46007_3_) {
        this.HorizonCode_Horizon_È = p_i46007_1_;
        this.Â = p_i46007_2_;
        this.Ý = p_i46007_3_;
    }
    
    public Vec3i(final double p_i46008_1_, final double p_i46008_3_, final double p_i46008_5_) {
        this(MathHelper.Ý(p_i46008_1_), MathHelper.Ý(p_i46008_3_), MathHelper.Ý(p_i46008_5_));
    }
    
    @Override
    public boolean equals(final Object p_equals_1_) {
        if (this == p_equals_1_) {
            return true;
        }
        if (!(p_equals_1_ instanceof Vec3i)) {
            return false;
        }
        final Vec3i var2 = (Vec3i)p_equals_1_;
        return this.HorizonCode_Horizon_È() == var2.HorizonCode_Horizon_È() && this.Â() == var2.Â() && this.Ý() == var2.Ý();
    }
    
    @Override
    public int hashCode() {
        return (this.Â() + this.Ý() * 31) * 31 + this.HorizonCode_Horizon_È();
    }
    
    public int Âµá€(final Vec3i p_177953_1_) {
        return (this.Â() == p_177953_1_.Â()) ? ((this.Ý() == p_177953_1_.Ý()) ? (this.HorizonCode_Horizon_È() - p_177953_1_.HorizonCode_Horizon_È()) : (this.Ý() - p_177953_1_.Ý())) : (this.Â() - p_177953_1_.Â());
    }
    
    public int HorizonCode_Horizon_È() {
        return this.HorizonCode_Horizon_È;
    }
    
    public int Â() {
        return this.Â;
    }
    
    public int Ý() {
        return this.Ý;
    }
    
    public Vec3i Ø­áŒŠá(final Vec3i vec) {
        return new Vec3i(this.Â() * vec.Ý() - this.Ý() * vec.Â(), this.Ý() * vec.HorizonCode_Horizon_È() - this.HorizonCode_Horizon_È() * vec.Ý(), this.HorizonCode_Horizon_È() * vec.Â() - this.Â() * vec.HorizonCode_Horizon_È());
    }
    
    public double Ý(final double toX, final double toY, final double toZ) {
        final double var7 = this.HorizonCode_Horizon_È() - toX;
        final double var8 = this.Â() - toY;
        final double var9 = this.Ý() - toZ;
        return var7 * var7 + var8 * var8 + var9 * var9;
    }
    
    public double Ø­áŒŠá(final double p_177957_1_, final double p_177957_3_, final double p_177957_5_) {
        final double var7 = this.HorizonCode_Horizon_È() + 0.5 - p_177957_1_;
        final double var8 = this.Â() + 0.5 - p_177957_3_;
        final double var9 = this.Ý() + 0.5 - p_177957_5_;
        return var7 * var7 + var8 * var8 + var9 * var9;
    }
    
    public double Ó(final Vec3i to) {
        return this.Ý(to.HorizonCode_Horizon_È(), to.Â(), to.Ý());
    }
    
    @Override
    public String toString() {
        return Objects.toStringHelper((Object)this).add("x", this.HorizonCode_Horizon_È()).add("y", this.Â()).add("z", this.Ý()).toString();
    }
    
    @Override
    public int compareTo(final Object p_compareTo_1_) {
        return this.Âµá€((Vec3i)p_compareTo_1_);
    }
}
