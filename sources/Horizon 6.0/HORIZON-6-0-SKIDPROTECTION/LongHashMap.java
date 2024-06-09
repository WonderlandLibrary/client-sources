package HORIZON-6-0-SKIDPROTECTION;

public class LongHashMap
{
    private transient HorizonCode_Horizon_È[] HorizonCode_Horizon_È;
    private transient int Â;
    private int Ý;
    private int Ø­áŒŠá;
    private final float Âµá€ = 0.75f;
    private transient volatile int Ó;
    private static final String à = "CL_00001492";
    
    public LongHashMap() {
        this.HorizonCode_Horizon_È = new HorizonCode_Horizon_È[4096];
        this.Ø­áŒŠá = 3072;
        this.Ý = this.HorizonCode_Horizon_È.length - 1;
    }
    
    private static int à(final long originalKey) {
        return (int)(originalKey ^ originalKey >>> 27);
    }
    
    private static int HorizonCode_Horizon_È(int integer) {
        integer ^= (integer >>> 20 ^ integer >>> 12);
        return integer ^ integer >>> 7 ^ integer >>> 4;
    }
    
    private static int HorizonCode_Horizon_È(final int p_76158_0_, final int p_76158_1_) {
        return p_76158_0_ & p_76158_1_;
    }
    
    public int HorizonCode_Horizon_È() {
        return this.Â;
    }
    
    public Object HorizonCode_Horizon_È(final long p_76164_1_) {
        final int var3 = à(p_76164_1_);
        for (HorizonCode_Horizon_È var4 = this.HorizonCode_Horizon_È[HorizonCode_Horizon_È(var3, this.Ý)]; var4 != null; var4 = var4.Ý) {
            if (var4.HorizonCode_Horizon_È == p_76164_1_) {
                return var4.Â;
            }
        }
        return null;
    }
    
    public boolean Â(final long p_76161_1_) {
        return this.Ý(p_76161_1_) != null;
    }
    
    final HorizonCode_Horizon_È Ý(final long p_76160_1_) {
        final int var3 = à(p_76160_1_);
        for (HorizonCode_Horizon_È var4 = this.HorizonCode_Horizon_È[HorizonCode_Horizon_È(var3, this.Ý)]; var4 != null; var4 = var4.Ý) {
            if (var4.HorizonCode_Horizon_È == p_76160_1_) {
                return var4;
            }
        }
        return null;
    }
    
    public void HorizonCode_Horizon_È(final long p_76163_1_, final Object p_76163_3_) {
        final int var4 = à(p_76163_1_);
        final int var5 = HorizonCode_Horizon_È(var4, this.Ý);
        for (HorizonCode_Horizon_È var6 = this.HorizonCode_Horizon_È[var5]; var6 != null; var6 = var6.Ý) {
            if (var6.HorizonCode_Horizon_È == p_76163_1_) {
                var6.Â = p_76163_3_;
                return;
            }
        }
        ++this.Ó;
        this.HorizonCode_Horizon_È(var4, p_76163_1_, p_76163_3_, var5);
    }
    
    private void Â(final int p_76153_1_) {
        final HorizonCode_Horizon_È[] var2 = this.HorizonCode_Horizon_È;
        final int var3 = var2.length;
        if (var3 == 1073741824) {
            this.Ø­áŒŠá = Integer.MAX_VALUE;
        }
        else {
            final HorizonCode_Horizon_È[] var4 = new HorizonCode_Horizon_È[p_76153_1_];
            this.HorizonCode_Horizon_È(var4);
            this.HorizonCode_Horizon_È = var4;
            this.Ý = this.HorizonCode_Horizon_È.length - 1;
            final float var5 = p_76153_1_;
            this.getClass();
            this.Ø­áŒŠá = (int)(var5 * 0.75f);
        }
    }
    
    private void HorizonCode_Horizon_È(final HorizonCode_Horizon_È[] p_76154_1_) {
        final HorizonCode_Horizon_È[] var2 = this.HorizonCode_Horizon_È;
        final int var3 = p_76154_1_.length;
        for (int var4 = 0; var4 < var2.length; ++var4) {
            HorizonCode_Horizon_È var5 = var2[var4];
            if (var5 != null) {
                var2[var4] = null;
                HorizonCode_Horizon_È var6;
                do {
                    var6 = var5.Ý;
                    final int var7 = HorizonCode_Horizon_È(var5.Ø­áŒŠá, var3 - 1);
                    var5.Ý = p_76154_1_[var7];
                    p_76154_1_[var7] = var5;
                } while ((var5 = var6) != null);
            }
        }
    }
    
    public Object Ø­áŒŠá(final long p_76159_1_) {
        final HorizonCode_Horizon_È var3 = this.Âµá€(p_76159_1_);
        return (var3 == null) ? null : var3.Â;
    }
    
    final HorizonCode_Horizon_È Âµá€(final long p_76152_1_) {
        final int var3 = à(p_76152_1_);
        final int var4 = HorizonCode_Horizon_È(var3, this.Ý);
        HorizonCode_Horizon_È var6;
        HorizonCode_Horizon_È var7;
        for (HorizonCode_Horizon_È var5 = var6 = this.HorizonCode_Horizon_È[var4]; var6 != null; var6 = var7) {
            var7 = var6.Ý;
            if (var6.HorizonCode_Horizon_È == p_76152_1_) {
                ++this.Ó;
                --this.Â;
                if (var5 == var6) {
                    this.HorizonCode_Horizon_È[var4] = var7;
                }
                else {
                    var5.Ý = var7;
                }
                return var6;
            }
            var5 = var6;
        }
        return var6;
    }
    
    private void HorizonCode_Horizon_È(final int p_76156_1_, final long p_76156_2_, final Object p_76156_4_, final int p_76156_5_) {
        final HorizonCode_Horizon_È var6 = this.HorizonCode_Horizon_È[p_76156_5_];
        this.HorizonCode_Horizon_È[p_76156_5_] = new HorizonCode_Horizon_È(p_76156_1_, p_76156_2_, p_76156_4_, var6);
        if (this.Â++ >= this.Ø­áŒŠá) {
            this.Â(2 * this.HorizonCode_Horizon_È.length);
        }
    }
    
    public double Â() {
        int countValid = 0;
        for (int i = 0; i < this.HorizonCode_Horizon_È.length; ++i) {
            if (this.HorizonCode_Horizon_È[i] != null) {
                ++countValid;
            }
        }
        return 1.0 * countValid / this.Â;
    }
    
    static class HorizonCode_Horizon_È
    {
        final long HorizonCode_Horizon_È;
        Object Â;
        HorizonCode_Horizon_È Ý;
        final int Ø­áŒŠá;
        private static final String Âµá€ = "CL_00001493";
        
        HorizonCode_Horizon_È(final int p_i1553_1_, final long p_i1553_2_, final Object p_i1553_4_, final HorizonCode_Horizon_È p_i1553_5_) {
            this.Â = p_i1553_4_;
            this.Ý = p_i1553_5_;
            this.HorizonCode_Horizon_È = p_i1553_2_;
            this.Ø­áŒŠá = p_i1553_1_;
        }
        
        public final long HorizonCode_Horizon_È() {
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
            final Long var3 = this.HorizonCode_Horizon_È();
            final Long var4 = var2.HorizonCode_Horizon_È();
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
