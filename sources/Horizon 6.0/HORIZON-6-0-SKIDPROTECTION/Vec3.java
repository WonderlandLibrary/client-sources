package HORIZON-6-0-SKIDPROTECTION;

public class Vec3
{
    public final double HorizonCode_Horizon_È;
    public final double Â;
    public final double Ý;
    private static final String Ø­áŒŠá = "CL_00000612";
    
    public Vec3(double x, double y, double z) {
        if (x == -0.0) {
            x = 0.0;
        }
        if (y == -0.0) {
            y = 0.0;
        }
        if (z == -0.0) {
            z = 0.0;
        }
        this.HorizonCode_Horizon_È = x;
        this.Â = y;
        this.Ý = z;
    }
    
    public Vec3 HorizonCode_Horizon_È(final Vec3 vec) {
        return new Vec3(vec.HorizonCode_Horizon_È - this.HorizonCode_Horizon_È, vec.Â - this.Â, vec.Ý - this.Ý);
    }
    
    public Vec3 HorizonCode_Horizon_È() {
        final double var1 = MathHelper.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È * this.HorizonCode_Horizon_È + this.Â * this.Â + this.Ý * this.Ý);
        return (var1 < 1.0E-4) ? new Vec3(0.0, 0.0, 0.0) : new Vec3(this.HorizonCode_Horizon_È / var1, this.Â / var1, this.Ý / var1);
    }
    
    public double Â(final Vec3 vec) {
        return this.HorizonCode_Horizon_È * vec.HorizonCode_Horizon_È + this.Â * vec.Â + this.Ý * vec.Ý;
    }
    
    public Vec3 Ý(final Vec3 vec) {
        return new Vec3(this.Â * vec.Ý - this.Ý * vec.Â, this.Ý * vec.HorizonCode_Horizon_È - this.HorizonCode_Horizon_È * vec.Ý, this.HorizonCode_Horizon_È * vec.Â - this.Â * vec.HorizonCode_Horizon_È);
    }
    
    public Vec3 Ø­áŒŠá(final Vec3 p_178788_1_) {
        return this.HorizonCode_Horizon_È(p_178788_1_.HorizonCode_Horizon_È, p_178788_1_.Â, p_178788_1_.Ý);
    }
    
    public Vec3 HorizonCode_Horizon_È(final double p_178786_1_, final double p_178786_3_, final double p_178786_5_) {
        return this.Â(-p_178786_1_, -p_178786_3_, -p_178786_5_);
    }
    
    public Vec3 Âµá€(final Vec3 p_178787_1_) {
        return this.Â(p_178787_1_.HorizonCode_Horizon_È, p_178787_1_.Â, p_178787_1_.Ý);
    }
    
    public Vec3 Â(final double x, final double y, final double z) {
        return new Vec3(this.HorizonCode_Horizon_È + x, this.Â + y, this.Ý + z);
    }
    
    public double Ó(final Vec3 vec) {
        final double var2 = vec.HorizonCode_Horizon_È - this.HorizonCode_Horizon_È;
        final double var3 = vec.Â - this.Â;
        final double var4 = vec.Ý - this.Ý;
        return MathHelper.HorizonCode_Horizon_È(var2 * var2 + var3 * var3 + var4 * var4);
    }
    
    public double à(final Vec3 vec) {
        final double var2 = vec.HorizonCode_Horizon_È - this.HorizonCode_Horizon_È;
        final double var3 = vec.Â - this.Â;
        final double var4 = vec.Ý - this.Ý;
        return var2 * var2 + var3 * var3 + var4 * var4;
    }
    
    public double Â() {
        return MathHelper.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È * this.HorizonCode_Horizon_È + this.Â * this.Â + this.Ý * this.Ý);
    }
    
    public Vec3 HorizonCode_Horizon_È(final Vec3 vec, final double x) {
        final double var4 = vec.HorizonCode_Horizon_È - this.HorizonCode_Horizon_È;
        final double var5 = vec.Â - this.Â;
        final double var6 = vec.Ý - this.Ý;
        if (var4 * var4 < 1.0000000116860974E-7) {
            return null;
        }
        final double var7 = (x - this.HorizonCode_Horizon_È) / var4;
        return (var7 >= 0.0 && var7 <= 1.0) ? new Vec3(this.HorizonCode_Horizon_È + var4 * var7, this.Â + var5 * var7, this.Ý + var6 * var7) : null;
    }
    
    public Vec3 Â(final Vec3 vec, final double y) {
        final double var4 = vec.HorizonCode_Horizon_È - this.HorizonCode_Horizon_È;
        final double var5 = vec.Â - this.Â;
        final double var6 = vec.Ý - this.Ý;
        if (var5 * var5 < 1.0000000116860974E-7) {
            return null;
        }
        final double var7 = (y - this.Â) / var5;
        return (var7 >= 0.0 && var7 <= 1.0) ? new Vec3(this.HorizonCode_Horizon_È + var4 * var7, this.Â + var5 * var7, this.Ý + var6 * var7) : null;
    }
    
    public Vec3 Ý(final Vec3 vec, final double z) {
        final double var4 = vec.HorizonCode_Horizon_È - this.HorizonCode_Horizon_È;
        final double var5 = vec.Â - this.Â;
        final double var6 = vec.Ý - this.Ý;
        if (var6 * var6 < 1.0000000116860974E-7) {
            return null;
        }
        final double var7 = (z - this.Ý) / var6;
        return (var7 >= 0.0 && var7 <= 1.0) ? new Vec3(this.HorizonCode_Horizon_È + var4 * var7, this.Â + var5 * var7, this.Ý + var6 * var7) : null;
    }
    
    @Override
    public String toString() {
        return "(" + this.HorizonCode_Horizon_È + ", " + this.Â + ", " + this.Ý + ")";
    }
    
    public Vec3 HorizonCode_Horizon_È(final float p_178789_1_) {
        final float var2 = MathHelper.Â(p_178789_1_);
        final float var3 = MathHelper.HorizonCode_Horizon_È(p_178789_1_);
        final double var4 = this.HorizonCode_Horizon_È;
        final double var5 = this.Â * var2 + this.Ý * var3;
        final double var6 = this.Ý * var2 - this.Â * var3;
        return new Vec3(var4, var5, var6);
    }
    
    public Vec3 Â(final float p_178785_1_) {
        final float var2 = MathHelper.Â(p_178785_1_);
        final float var3 = MathHelper.HorizonCode_Horizon_È(p_178785_1_);
        final double var4 = this.HorizonCode_Horizon_È * var2 + this.Ý * var3;
        final double var5 = this.Â;
        final double var6 = this.Ý * var2 - this.HorizonCode_Horizon_È * var3;
        return new Vec3(var4, var5, var6);
    }
}
