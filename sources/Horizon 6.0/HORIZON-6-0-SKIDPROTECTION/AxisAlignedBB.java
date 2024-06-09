package HORIZON-6-0-SKIDPROTECTION;

public class AxisAlignedBB
{
    public final double HorizonCode_Horizon_È;
    public final double Â;
    public final double Ý;
    public final double Ø­áŒŠá;
    public final double Âµá€;
    public final double Ó;
    private static final String à = "CL_00000607";
    
    public AxisAlignedBB(final double x1, final double y1, final double z1, final double x2, final double y2, final double z2) {
        this.HorizonCode_Horizon_È = Math.min(x1, x2);
        this.Â = Math.min(y1, y2);
        this.Ý = Math.min(z1, z2);
        this.Ø­áŒŠá = Math.max(x1, x2);
        this.Âµá€ = Math.max(y1, y2);
        this.Ó = Math.max(z1, z2);
    }
    
    public AxisAlignedBB(final BlockPos p_i45554_1_, final BlockPos p_i45554_2_) {
        this.HorizonCode_Horizon_È = p_i45554_1_.HorizonCode_Horizon_È();
        this.Â = p_i45554_1_.Â();
        this.Ý = p_i45554_1_.Ý();
        this.Ø­áŒŠá = p_i45554_2_.HorizonCode_Horizon_È();
        this.Âµá€ = p_i45554_2_.Â();
        this.Ó = p_i45554_2_.Ý();
    }
    
    public AxisAlignedBB HorizonCode_Horizon_È(final double x, final double y, final double z) {
        double var7 = this.HorizonCode_Horizon_È;
        double var8 = this.Â;
        double var9 = this.Ý;
        double var10 = this.Ø­áŒŠá;
        double var11 = this.Âµá€;
        double var12 = this.Ó;
        if (x < 0.0) {
            var7 += x;
        }
        else if (x > 0.0) {
            var10 += x;
        }
        if (y < 0.0) {
            var8 += y;
        }
        else if (y > 0.0) {
            var11 += y;
        }
        if (z < 0.0) {
            var9 += z;
        }
        else if (z > 0.0) {
            var12 += z;
        }
        return new AxisAlignedBB(var7, var8, var9, var10, var11, var12);
    }
    
    public AxisAlignedBB Â(final double x, final double y, final double z) {
        final double var7 = this.HorizonCode_Horizon_È - x;
        final double var8 = this.Â - y;
        final double var9 = this.Ý - z;
        final double var10 = this.Ø­áŒŠá + x;
        final double var11 = this.Âµá€ + y;
        final double var12 = this.Ó + z;
        return new AxisAlignedBB(var7, var8, var9, var10, var11, var12);
    }
    
    public AxisAlignedBB HorizonCode_Horizon_È(final AxisAlignedBB other) {
        final double var2 = Math.min(this.HorizonCode_Horizon_È, other.HorizonCode_Horizon_È);
        final double var3 = Math.min(this.Â, other.Â);
        final double var4 = Math.min(this.Ý, other.Ý);
        final double var5 = Math.max(this.Ø­áŒŠá, other.Ø­áŒŠá);
        final double var6 = Math.max(this.Âµá€, other.Âµá€);
        final double var7 = Math.max(this.Ó, other.Ó);
        return new AxisAlignedBB(var2, var3, var4, var5, var6, var7);
    }
    
    public static AxisAlignedBB HorizonCode_Horizon_È(final double p_178781_0_, final double p_178781_2_, final double p_178781_4_, final double p_178781_6_, final double p_178781_8_, final double p_178781_10_) {
        final double var12 = Math.min(p_178781_0_, p_178781_6_);
        final double var13 = Math.min(p_178781_2_, p_178781_8_);
        final double var14 = Math.min(p_178781_4_, p_178781_10_);
        final double var15 = Math.max(p_178781_0_, p_178781_6_);
        final double var16 = Math.max(p_178781_2_, p_178781_8_);
        final double var17 = Math.max(p_178781_4_, p_178781_10_);
        return new AxisAlignedBB(var12, var13, var14, var15, var16, var17);
    }
    
    public AxisAlignedBB Ý(final double x, final double y, final double z) {
        return new AxisAlignedBB(this.HorizonCode_Horizon_È + x, this.Â + y, this.Ý + z, this.Ø­áŒŠá + x, this.Âµá€ + y, this.Ó + z);
    }
    
    public double HorizonCode_Horizon_È(final AxisAlignedBB other, double p_72316_2_) {
        if (other.Âµá€ > this.Â && other.Â < this.Âµá€ && other.Ó > this.Ý && other.Ý < this.Ó) {
            if (p_72316_2_ > 0.0 && other.Ø­áŒŠá <= this.HorizonCode_Horizon_È) {
                final double var4 = this.HorizonCode_Horizon_È - other.Ø­áŒŠá;
                if (var4 < p_72316_2_) {
                    p_72316_2_ = var4;
                }
            }
            else if (p_72316_2_ < 0.0 && other.HorizonCode_Horizon_È >= this.Ø­áŒŠá) {
                final double var4 = this.Ø­áŒŠá - other.HorizonCode_Horizon_È;
                if (var4 > p_72316_2_) {
                    p_72316_2_ = var4;
                }
            }
            return p_72316_2_;
        }
        return p_72316_2_;
    }
    
    public double Â(final AxisAlignedBB other, double p_72323_2_) {
        if (other.Ø­áŒŠá > this.HorizonCode_Horizon_È && other.HorizonCode_Horizon_È < this.Ø­áŒŠá && other.Ó > this.Ý && other.Ý < this.Ó) {
            if (p_72323_2_ > 0.0 && other.Âµá€ <= this.Â) {
                final double var4 = this.Â - other.Âµá€;
                if (var4 < p_72323_2_) {
                    p_72323_2_ = var4;
                }
            }
            else if (p_72323_2_ < 0.0 && other.Â >= this.Âµá€) {
                final double var4 = this.Âµá€ - other.Â;
                if (var4 > p_72323_2_) {
                    p_72323_2_ = var4;
                }
            }
            return p_72323_2_;
        }
        return p_72323_2_;
    }
    
    public double Ý(final AxisAlignedBB other, double p_72322_2_) {
        if (other.Ø­áŒŠá > this.HorizonCode_Horizon_È && other.HorizonCode_Horizon_È < this.Ø­áŒŠá && other.Âµá€ > this.Â && other.Â < this.Âµá€) {
            if (p_72322_2_ > 0.0 && other.Ó <= this.Ý) {
                final double var4 = this.Ý - other.Ó;
                if (var4 < p_72322_2_) {
                    p_72322_2_ = var4;
                }
            }
            else if (p_72322_2_ < 0.0 && other.Ý >= this.Ó) {
                final double var4 = this.Ó - other.Ý;
                if (var4 > p_72322_2_) {
                    p_72322_2_ = var4;
                }
            }
            return p_72322_2_;
        }
        return p_72322_2_;
    }
    
    public boolean Â(final AxisAlignedBB other) {
        return other.Ø­áŒŠá > this.HorizonCode_Horizon_È && other.HorizonCode_Horizon_È < this.Ø­áŒŠá && (other.Âµá€ > this.Â && other.Â < this.Âµá€) && (other.Ó > this.Ý && other.Ý < this.Ó);
    }
    
    public boolean HorizonCode_Horizon_È(final Vec3 vec) {
        return vec.HorizonCode_Horizon_È > this.HorizonCode_Horizon_È && vec.HorizonCode_Horizon_È < this.Ø­áŒŠá && (vec.Â > this.Â && vec.Â < this.Âµá€) && (vec.Ý > this.Ý && vec.Ý < this.Ó);
    }
    
    public double HorizonCode_Horizon_È() {
        final double var1 = this.Ø­áŒŠá - this.HorizonCode_Horizon_È;
        final double var2 = this.Âµá€ - this.Â;
        final double var3 = this.Ó - this.Ý;
        return (var1 + var2 + var3) / 3.0;
    }
    
    public AxisAlignedBB Ø­áŒŠá(final double x, final double y, final double z) {
        final double var7 = this.HorizonCode_Horizon_È + x;
        final double var8 = this.Â + y;
        final double var9 = this.Ý + z;
        final double var10 = this.Ø­áŒŠá - x;
        final double var11 = this.Âµá€ - y;
        final double var12 = this.Ó - z;
        return new AxisAlignedBB(var7, var8, var9, var10, var11, var12);
    }
    
    public MovingObjectPosition HorizonCode_Horizon_È(final Vec3 p_72327_1_, final Vec3 p_72327_2_) {
        Vec3 var3 = p_72327_1_.HorizonCode_Horizon_È(p_72327_2_, this.HorizonCode_Horizon_È);
        Vec3 var4 = p_72327_1_.HorizonCode_Horizon_È(p_72327_2_, this.Ø­áŒŠá);
        Vec3 var5 = p_72327_1_.Â(p_72327_2_, this.Â);
        Vec3 var6 = p_72327_1_.Â(p_72327_2_, this.Âµá€);
        Vec3 var7 = p_72327_1_.Ý(p_72327_2_, this.Ý);
        Vec3 var8 = p_72327_1_.Ý(p_72327_2_, this.Ó);
        if (!this.Â(var3)) {
            var3 = null;
        }
        if (!this.Â(var4)) {
            var4 = null;
        }
        if (!this.Ý(var5)) {
            var5 = null;
        }
        if (!this.Ý(var6)) {
            var6 = null;
        }
        if (!this.Ø­áŒŠá(var7)) {
            var7 = null;
        }
        if (!this.Ø­áŒŠá(var8)) {
            var8 = null;
        }
        Vec3 var9 = null;
        if (var3 != null) {
            var9 = var3;
        }
        if (var4 != null && (var9 == null || p_72327_1_.à(var4) < p_72327_1_.à(var9))) {
            var9 = var4;
        }
        if (var5 != null && (var9 == null || p_72327_1_.à(var5) < p_72327_1_.à(var9))) {
            var9 = var5;
        }
        if (var6 != null && (var9 == null || p_72327_1_.à(var6) < p_72327_1_.à(var9))) {
            var9 = var6;
        }
        if (var7 != null && (var9 == null || p_72327_1_.à(var7) < p_72327_1_.à(var9))) {
            var9 = var7;
        }
        if (var8 != null && (var9 == null || p_72327_1_.à(var8) < p_72327_1_.à(var9))) {
            var9 = var8;
        }
        if (var9 == null) {
            return null;
        }
        EnumFacing var10 = null;
        if (var9 == var3) {
            var10 = EnumFacing.Âµá€;
        }
        else if (var9 == var4) {
            var10 = EnumFacing.Ó;
        }
        else if (var9 == var5) {
            var10 = EnumFacing.HorizonCode_Horizon_È;
        }
        else if (var9 == var6) {
            var10 = EnumFacing.Â;
        }
        else if (var9 == var7) {
            var10 = EnumFacing.Ý;
        }
        else {
            var10 = EnumFacing.Ø­áŒŠá;
        }
        return new MovingObjectPosition(var9, var10);
    }
    
    private boolean Â(final Vec3 vec) {
        return vec != null && (vec.Â >= this.Â && vec.Â <= this.Âµá€ && vec.Ý >= this.Ý && vec.Ý <= this.Ó);
    }
    
    private boolean Ý(final Vec3 vec) {
        return vec != null && (vec.HorizonCode_Horizon_È >= this.HorizonCode_Horizon_È && vec.HorizonCode_Horizon_È <= this.Ø­áŒŠá && vec.Ý >= this.Ý && vec.Ý <= this.Ó);
    }
    
    private boolean Ø­áŒŠá(final Vec3 vec) {
        return vec != null && (vec.HorizonCode_Horizon_È >= this.HorizonCode_Horizon_È && vec.HorizonCode_Horizon_È <= this.Ø­áŒŠá && vec.Â >= this.Â && vec.Â <= this.Âµá€);
    }
    
    @Override
    public String toString() {
        return "box[" + this.HorizonCode_Horizon_È + ", " + this.Â + ", " + this.Ý + " -> " + this.Ø­áŒŠá + ", " + this.Âµá€ + ", " + this.Ó + "]";
    }
}
