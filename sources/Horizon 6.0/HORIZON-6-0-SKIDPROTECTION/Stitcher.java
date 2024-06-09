package HORIZON-6-0-SKIDPROTECTION;

import java.util.Iterator;
import java.util.ArrayList;
import java.util.Arrays;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.util.List;
import java.util.Set;

public class Stitcher
{
    private final int HorizonCode_Horizon_È;
    private final Set Â;
    private final List Ý;
    private int Ø­áŒŠá;
    private int Âµá€;
    private final int Ó;
    private final int à;
    private final boolean Ø;
    private final int áŒŠÆ;
    private static final String áˆºÑ¢Õ = "CL_00001054";
    
    public Stitcher(final int p_i45095_1_, final int p_i45095_2_, final boolean p_i45095_3_, final int p_i45095_4_, final int p_i45095_5_) {
        this.Â = Sets.newHashSetWithExpectedSize(256);
        this.Ý = Lists.newArrayListWithCapacity(256);
        this.HorizonCode_Horizon_È = p_i45095_5_;
        this.Ó = p_i45095_1_;
        this.à = p_i45095_2_;
        this.Ø = p_i45095_3_;
        this.áŒŠÆ = p_i45095_4_;
    }
    
    public int HorizonCode_Horizon_È() {
        return this.Ø­áŒŠá;
    }
    
    public int Â() {
        return this.Âµá€;
    }
    
    public void HorizonCode_Horizon_È(final TextureAtlasSprite p_110934_1_) {
        final HorizonCode_Horizon_È var2 = new HorizonCode_Horizon_È(p_110934_1_, this.HorizonCode_Horizon_È);
        if (this.áŒŠÆ > 0) {
            var2.HorizonCode_Horizon_È(this.áŒŠÆ);
        }
        this.Â.add(var2);
    }
    
    public void Ý() {
        final HorizonCode_Horizon_È[] var1 = this.Â.toArray(new HorizonCode_Horizon_È[this.Â.size()]);
        Arrays.sort(var1);
        final HorizonCode_Horizon_È[] var2 = var1;
        for (int var3 = var1.length, var4 = 0; var4 < var3; ++var4) {
            final HorizonCode_Horizon_È var5 = var2[var4];
            if (!this.HorizonCode_Horizon_È(var5)) {
                final String var6 = String.format("Unable to fit: %s, size: %dx%d, atlas: %dx%d, atlasMax: %dx%d - Maybe try a lower resolution resourcepack?", var5.HorizonCode_Horizon_È().áŒŠÆ(), var5.HorizonCode_Horizon_È().Ý(), var5.HorizonCode_Horizon_È().Ø­áŒŠá(), this.Ø­áŒŠá, this.Âµá€, this.Ó, this.à);
                throw new StitcherException(var5, var6);
            }
        }
        if (this.Ø) {
            this.Ø­áŒŠá = MathHelper.Â(this.Ø­áŒŠá);
            this.Âµá€ = MathHelper.Â(this.Âµá€);
        }
    }
    
    public List Ø­áŒŠá() {
        final ArrayList var1 = Lists.newArrayList();
        for (final Â var3 : this.Ý) {
            var3.HorizonCode_Horizon_È(var1);
        }
        final ArrayList var4 = Lists.newArrayList();
        for (final Â var6 : var1) {
            final HorizonCode_Horizon_È var7 = var6.HorizonCode_Horizon_È();
            final TextureAtlasSprite var8 = var7.HorizonCode_Horizon_È();
            var8.HorizonCode_Horizon_È(this.Ø­áŒŠá, this.Âµá€, var6.Â(), var6.Ý(), var7.Âµá€());
            var4.add(var8);
        }
        return var4;
    }
    
    private static int Â(final int p_147969_0_, final int p_147969_1_) {
        return (p_147969_0_ >> p_147969_1_) + (((p_147969_0_ & (1 << p_147969_1_) - 1) != 0x0) ? 1 : 0) << p_147969_1_;
    }
    
    private boolean HorizonCode_Horizon_È(final HorizonCode_Horizon_È p_94310_1_) {
        for (int var2 = 0; var2 < this.Ý.size(); ++var2) {
            if (this.Ý.get(var2).HorizonCode_Horizon_È(p_94310_1_)) {
                return true;
            }
            p_94310_1_.Ø­áŒŠá();
            if (this.Ý.get(var2).HorizonCode_Horizon_È(p_94310_1_)) {
                return true;
            }
            p_94310_1_.Ø­áŒŠá();
        }
        return this.Â(p_94310_1_);
    }
    
    private boolean Â(final HorizonCode_Horizon_È p_94311_1_) {
        final int var2 = Math.min(p_94311_1_.Â(), p_94311_1_.Ý());
        final boolean var3 = this.Ø­áŒŠá == 0 && this.Âµá€ == 0;
        boolean var12;
        if (this.Ø) {
            final int var4 = MathHelper.Â(this.Ø­áŒŠá);
            final int var5 = MathHelper.Â(this.Âµá€);
            final int var6 = MathHelper.Â(this.Ø­áŒŠá + var2);
            final int var7 = MathHelper.Â(this.Âµá€ + var2);
            final boolean var8 = var6 <= this.Ó;
            final boolean var9 = var7 <= this.à;
            if (!var8 && !var9) {
                return false;
            }
            final boolean var10 = var4 != var6;
            final boolean var11 = var5 != var7;
            if (var10 ^ var11) {
                var12 = !var10;
            }
            else {
                var12 = (var8 && var4 <= var5);
            }
        }
        else {
            final boolean var13 = this.Ø­áŒŠá + var2 <= this.Ó;
            final boolean var14 = this.Âµá€ + var2 <= this.à;
            if (!var13 && !var14) {
                return false;
            }
            var12 = (var13 && (var3 || this.Ø­áŒŠá <= this.Âµá€));
        }
        final int var4 = Math.max(p_94311_1_.Â(), p_94311_1_.Ý());
        if (MathHelper.Â((var12 ? this.Ø­áŒŠá : this.Âµá€) + var4) > (var12 ? this.Ó : this.à)) {
            return false;
        }
        Â var15;
        if (var12) {
            if (p_94311_1_.Â() > p_94311_1_.Ý()) {
                p_94311_1_.Ø­áŒŠá();
            }
            if (this.Âµá€ == 0) {
                this.Âµá€ = p_94311_1_.Ý();
            }
            var15 = new Â(this.Ø­áŒŠá, 0, p_94311_1_.Â(), this.Âµá€);
            this.Ø­áŒŠá += p_94311_1_.Â();
        }
        else {
            var15 = new Â(0, this.Âµá€, this.Ø­áŒŠá, p_94311_1_.Ý());
            this.Âµá€ += p_94311_1_.Ý();
        }
        var15.HorizonCode_Horizon_È(p_94311_1_);
        this.Ý.add(var15);
        return true;
    }
    
    public static class HorizonCode_Horizon_È implements Comparable
    {
        private final TextureAtlasSprite HorizonCode_Horizon_È;
        private final int Â;
        private final int Ý;
        private final int Ø­áŒŠá;
        private boolean Âµá€;
        private float Ó;
        private static final String à = "CL_00001055";
        
        public HorizonCode_Horizon_È(final TextureAtlasSprite p_i45094_1_, final int p_i45094_2_) {
            this.Ó = 1.0f;
            this.HorizonCode_Horizon_È = p_i45094_1_;
            this.Â = p_i45094_1_.Ý();
            this.Ý = p_i45094_1_.Ø­áŒŠá();
            this.Ø­áŒŠá = p_i45094_2_;
            this.Âµá€ = (Â(this.Ý, p_i45094_2_) > Â(this.Â, p_i45094_2_));
        }
        
        public TextureAtlasSprite HorizonCode_Horizon_È() {
            return this.HorizonCode_Horizon_È;
        }
        
        public int Â() {
            return this.Âµá€ ? Â((int)(this.Ý * this.Ó), this.Ø­áŒŠá) : Â((int)(this.Â * this.Ó), this.Ø­áŒŠá);
        }
        
        public int Ý() {
            return this.Âµá€ ? Â((int)(this.Â * this.Ó), this.Ø­áŒŠá) : Â((int)(this.Ý * this.Ó), this.Ø­áŒŠá);
        }
        
        public void Ø­áŒŠá() {
            this.Âµá€ = !this.Âµá€;
        }
        
        public boolean Âµá€() {
            return this.Âµá€;
        }
        
        public void HorizonCode_Horizon_È(final int p_94196_1_) {
            if (this.Â > p_94196_1_ && this.Ý > p_94196_1_) {
                this.Ó = p_94196_1_ / Math.min(this.Â, this.Ý);
            }
        }
        
        @Override
        public String toString() {
            return "Holder{width=" + this.Â + ", height=" + this.Ý + '}';
        }
        
        public int HorizonCode_Horizon_È(final HorizonCode_Horizon_È p_compareTo_1_) {
            int var2;
            if (this.Ý() == p_compareTo_1_.Ý()) {
                if (this.Â() == p_compareTo_1_.Â()) {
                    if (this.HorizonCode_Horizon_È.áŒŠÆ() == null) {
                        return (p_compareTo_1_.HorizonCode_Horizon_È.áŒŠÆ() == null) ? 0 : -1;
                    }
                    return this.HorizonCode_Horizon_È.áŒŠÆ().compareTo(p_compareTo_1_.HorizonCode_Horizon_È.áŒŠÆ());
                }
                else {
                    var2 = ((this.Â() < p_compareTo_1_.Â()) ? 1 : -1);
                }
            }
            else {
                var2 = ((this.Ý() < p_compareTo_1_.Ý()) ? 1 : -1);
            }
            return var2;
        }
        
        @Override
        public int compareTo(final Object p_compareTo_1_) {
            return this.HorizonCode_Horizon_È((HorizonCode_Horizon_È)p_compareTo_1_);
        }
    }
    
    public static class Â
    {
        private final int HorizonCode_Horizon_È;
        private final int Â;
        private final int Ý;
        private final int Ø­áŒŠá;
        private List Âµá€;
        private HorizonCode_Horizon_È Ó;
        private static final String à = "CL_00001056";
        
        public Â(final int p_i1277_1_, final int p_i1277_2_, final int p_i1277_3_, final int p_i1277_4_) {
            this.HorizonCode_Horizon_È = p_i1277_1_;
            this.Â = p_i1277_2_;
            this.Ý = p_i1277_3_;
            this.Ø­áŒŠá = p_i1277_4_;
        }
        
        public HorizonCode_Horizon_È HorizonCode_Horizon_È() {
            return this.Ó;
        }
        
        public int Â() {
            return this.HorizonCode_Horizon_È;
        }
        
        public int Ý() {
            return this.Â;
        }
        
        public boolean HorizonCode_Horizon_È(final HorizonCode_Horizon_È p_94182_1_) {
            if (this.Ó != null) {
                return false;
            }
            final int var2 = p_94182_1_.Â();
            final int var3 = p_94182_1_.Ý();
            if (var2 > this.Ý || var3 > this.Ø­áŒŠá) {
                return false;
            }
            if (var2 == this.Ý && var3 == this.Ø­áŒŠá) {
                this.Ó = p_94182_1_;
                return true;
            }
            if (this.Âµá€ == null) {
                (this.Âµá€ = Lists.newArrayListWithCapacity(1)).add(new Â(this.HorizonCode_Horizon_È, this.Â, var2, var3));
                final int var4 = this.Ý - var2;
                final int var5 = this.Ø­áŒŠá - var3;
                if (var5 > 0 && var4 > 0) {
                    final int var6 = Math.max(this.Ø­áŒŠá, var4);
                    final int var7 = Math.max(this.Ý, var5);
                    if (var6 >= var7) {
                        this.Âµá€.add(new Â(this.HorizonCode_Horizon_È, this.Â + var3, var2, var5));
                        this.Âµá€.add(new Â(this.HorizonCode_Horizon_È + var2, this.Â, var4, this.Ø­áŒŠá));
                    }
                    else {
                        this.Âµá€.add(new Â(this.HorizonCode_Horizon_È + var2, this.Â, var4, var3));
                        this.Âµá€.add(new Â(this.HorizonCode_Horizon_È, this.Â + var3, this.Ý, var5));
                    }
                }
                else if (var4 == 0) {
                    this.Âµá€.add(new Â(this.HorizonCode_Horizon_È, this.Â + var3, var2, var5));
                }
                else if (var5 == 0) {
                    this.Âµá€.add(new Â(this.HorizonCode_Horizon_È + var2, this.Â, var4, var3));
                }
            }
            for (final Â var9 : this.Âµá€) {
                if (var9.HorizonCode_Horizon_È(p_94182_1_)) {
                    return true;
                }
            }
            return false;
        }
        
        public void HorizonCode_Horizon_È(final List p_94184_1_) {
            if (this.Ó != null) {
                p_94184_1_.add(this);
            }
            else if (this.Âµá€ != null) {
                for (final Â var3 : this.Âµá€) {
                    var3.HorizonCode_Horizon_È(p_94184_1_);
                }
            }
        }
        
        @Override
        public String toString() {
            return "Slot{originX=" + this.HorizonCode_Horizon_È + ", originY=" + this.Â + ", width=" + this.Ý + ", height=" + this.Ø­áŒŠá + ", texture=" + this.Ó + ", subSlots=" + this.Âµá€ + '}';
        }
    }
}
