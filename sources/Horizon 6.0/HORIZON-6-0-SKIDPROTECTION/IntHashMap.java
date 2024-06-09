package HORIZON-6-0-SKIDPROTECTION;

public class IntHashMap
{
    private transient HorizonCode_Horizon_È[] HorizonCode_Horizon_È;
    private transient int Â;
    private int Ý;
    private final float Ø­áŒŠá = 0.75f;
    private static final String Âµá€ = "CL_00001490";
    
    public IntHashMap() {
        this.HorizonCode_Horizon_È = new HorizonCode_Horizon_È[16];
        this.Ý = 12;
    }
    
    private static int à(int p_76044_0_) {
        p_76044_0_ ^= (p_76044_0_ >>> 20 ^ p_76044_0_ >>> 12);
        return p_76044_0_ ^ p_76044_0_ >>> 7 ^ p_76044_0_ >>> 4;
    }
    
    private static int HorizonCode_Horizon_È(final int p_76043_0_, final int p_76043_1_) {
        return p_76043_0_ & p_76043_1_ - 1;
    }
    
    public Object HorizonCode_Horizon_È(final int p_76041_1_) {
        final int var2 = à(p_76041_1_);
        for (HorizonCode_Horizon_È var3 = this.HorizonCode_Horizon_È[HorizonCode_Horizon_È(var2, this.HorizonCode_Horizon_È.length)]; var3 != null; var3 = var3.Ý) {
            if (var3.HorizonCode_Horizon_È == p_76041_1_) {
                return var3.Â;
            }
        }
        return null;
    }
    
    public boolean Â(final int p_76037_1_) {
        return this.Ý(p_76037_1_) != null;
    }
    
    final HorizonCode_Horizon_È Ý(final int p_76045_1_) {
        final int var2 = à(p_76045_1_);
        for (HorizonCode_Horizon_È var3 = this.HorizonCode_Horizon_È[HorizonCode_Horizon_È(var2, this.HorizonCode_Horizon_È.length)]; var3 != null; var3 = var3.Ý) {
            if (var3.HorizonCode_Horizon_È == p_76045_1_) {
                return var3;
            }
        }
        return null;
    }
    
    public void HorizonCode_Horizon_È(final int p_76038_1_, final Object p_76038_2_) {
        final int var3 = à(p_76038_1_);
        final int var4 = HorizonCode_Horizon_È(var3, this.HorizonCode_Horizon_È.length);
        for (HorizonCode_Horizon_È var5 = this.HorizonCode_Horizon_È[var4]; var5 != null; var5 = var5.Ý) {
            if (var5.HorizonCode_Horizon_È == p_76038_1_) {
                var5.Â = p_76038_2_;
                return;
            }
        }
        this.HorizonCode_Horizon_È(var3, p_76038_1_, p_76038_2_, var4);
    }
    
    private void Ø(final int p_76047_1_) {
        final HorizonCode_Horizon_È[] var2 = this.HorizonCode_Horizon_È;
        final int var3 = var2.length;
        if (var3 == 1073741824) {
            this.Ý = Integer.MAX_VALUE;
        }
        else {
            final HorizonCode_Horizon_È[] var4 = new HorizonCode_Horizon_È[p_76047_1_];
            this.HorizonCode_Horizon_È(var4);
            this.HorizonCode_Horizon_È = var4;
            this.Ý = (int)(p_76047_1_ * 0.75f);
        }
    }
    
    private void HorizonCode_Horizon_È(final HorizonCode_Horizon_È[] p_76048_1_) {
        final HorizonCode_Horizon_È[] var2 = this.HorizonCode_Horizon_È;
        final int var3 = p_76048_1_.length;
        for (int var4 = 0; var4 < var2.length; ++var4) {
            HorizonCode_Horizon_È var5 = var2[var4];
            if (var5 != null) {
                var2[var4] = null;
                HorizonCode_Horizon_È var6;
                do {
                    var6 = var5.Ý;
                    final int var7 = HorizonCode_Horizon_È(var5.Ø­áŒŠá, var3);
                    var5.Ý = p_76048_1_[var7];
                    p_76048_1_[var7] = var5;
                } while ((var5 = var6) != null);
            }
        }
    }
    
    public Object Ø­áŒŠá(final int p_76049_1_) {
        final HorizonCode_Horizon_È var2 = this.Âµá€(p_76049_1_);
        return (var2 == null) ? null : var2.Â;
    }
    
    final HorizonCode_Horizon_È Âµá€(final int p_76036_1_) {
        final int var2 = à(p_76036_1_);
        final int var3 = HorizonCode_Horizon_È(var2, this.HorizonCode_Horizon_È.length);
        HorizonCode_Horizon_È var5;
        HorizonCode_Horizon_È var6;
        for (HorizonCode_Horizon_È var4 = var5 = this.HorizonCode_Horizon_È[var3]; var5 != null; var5 = var6) {
            var6 = var5.Ý;
            if (var5.HorizonCode_Horizon_È == p_76036_1_) {
                --this.Â;
                if (var4 == var5) {
                    this.HorizonCode_Horizon_È[var3] = var6;
                }
                else {
                    var4.Ý = var6;
                }
                return var5;
            }
            var4 = var5;
        }
        return var5;
    }
    
    public void HorizonCode_Horizon_È() {
        final HorizonCode_Horizon_È[] var1 = this.HorizonCode_Horizon_È;
        for (int var2 = 0; var2 < var1.length; ++var2) {
            var1[var2] = null;
        }
        this.Â = 0;
    }
    
    private void HorizonCode_Horizon_È(final int p_76040_1_, final int p_76040_2_, final Object p_76040_3_, final int p_76040_4_) {
        final HorizonCode_Horizon_È var5 = this.HorizonCode_Horizon_È[p_76040_4_];
        this.HorizonCode_Horizon_È[p_76040_4_] = new HorizonCode_Horizon_È(p_76040_1_, p_76040_2_, p_76040_3_, var5);
        if (this.Â++ >= this.Ý) {
            this.Ø(2 * this.HorizonCode_Horizon_È.length);
        }
    }
    
    static class HorizonCode_Horizon_È
    {
        final int HorizonCode_Horizon_È;
        Object Â;
        HorizonCode_Horizon_È Ý;
        final int Ø­áŒŠá;
        private static final String Âµá€ = "CL_00001491";
        
        HorizonCode_Horizon_È(final int p_i1552_1_, final int p_i1552_2_, final Object p_i1552_3_, final HorizonCode_Horizon_È p_i1552_4_) {
            this.Â = p_i1552_3_;
            this.Ý = p_i1552_4_;
            this.HorizonCode_Horizon_È = p_i1552_2_;
            this.Ø­áŒŠá = p_i1552_1_;
        }
        
        public final int HorizonCode_Horizon_È() {
            return this.HorizonCode_Horizon_È;
        }
        
        public final Object Â() {
            return this.Â;
        }
        
        @Override
        public final boolean equals(final Object p_equals_1_) {
            if (!(p_equals_1_ instanceof HorizonCode_Horizon_È)) {
                return false;
            }
            final HorizonCode_Horizon_È var2 = (HorizonCode_Horizon_È)p_equals_1_;
            final Integer var3 = this.HorizonCode_Horizon_È();
            final Integer var4 = var2.HorizonCode_Horizon_È();
            if (var3 == var4 || (var3 != null && var3.equals(var4))) {
                final Object var5 = this.Â();
                final Object var6 = var2.Â();
                if (var5 == var6 || (var5 != null && var5.equals(var6))) {
                    return true;
                }
            }
            return false;
        }
        
        @Override
        public final int hashCode() {
            return à(this.HorizonCode_Horizon_È);
        }
        
        @Override
        public final String toString() {
            return String.valueOf(this.HorizonCode_Horizon_È()) + "=" + this.Â();
        }
    }
}
