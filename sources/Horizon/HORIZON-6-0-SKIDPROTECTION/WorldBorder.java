package HORIZON-6-0-SKIDPROTECTION;

import java.util.Iterator;
import com.google.common.collect.Lists;
import java.util.List;

public class WorldBorder
{
    private final List HorizonCode_Horizon_È;
    private double Â;
    private double Ý;
    private double Ø­áŒŠá;
    private double Âµá€;
    private long Ó;
    private long à;
    private int Ø;
    private double áŒŠÆ;
    private double áˆºÑ¢Õ;
    private int ÂµÈ;
    private int á;
    private static final String ˆÏ­ = "CL_00002012";
    
    public WorldBorder() {
        this.HorizonCode_Horizon_È = Lists.newArrayList();
        this.Â = 0.0;
        this.Ý = 0.0;
        this.Ø­áŒŠá = 6.0E7;
        this.Âµá€ = this.Ø­áŒŠá;
        this.Ø = 29999984;
        this.áŒŠÆ = 0.2;
        this.áˆºÑ¢Õ = 5.0;
        this.ÂµÈ = 15;
        this.á = 5;
    }
    
    public boolean HorizonCode_Horizon_È(final BlockPos pos) {
        return pos.HorizonCode_Horizon_È() + 1 > this.Ø­áŒŠá() && pos.HorizonCode_Horizon_È() < this.Ó() && pos.Ý() + 1 > this.Âµá€() && pos.Ý() < this.à();
    }
    
    public boolean HorizonCode_Horizon_È(final ChunkCoordIntPair range) {
        return range.Âµá€() > this.Ø­áŒŠá() && range.Ý() < this.Ó() && range.Ó() > this.Âµá€() && range.Ø­áŒŠá() < this.à();
    }
    
    public boolean HorizonCode_Horizon_È(final AxisAlignedBB bb) {
        return bb.Ø­áŒŠá > this.Ø­áŒŠá() && bb.HorizonCode_Horizon_È < this.Ó() && bb.Ó > this.Âµá€() && bb.Ý < this.à();
    }
    
    public double HorizonCode_Horizon_È(final Entity p_177745_1_) {
        return this.HorizonCode_Horizon_È(p_177745_1_.ŒÏ, p_177745_1_.Ê);
    }
    
    public double HorizonCode_Horizon_È(final double x, final double z) {
        final double var5 = z - this.Âµá€();
        final double var6 = this.à() - z;
        final double var7 = x - this.Ø­áŒŠá();
        final double var8 = this.Ó() - x;
        double var9 = Math.min(var7, var8);
        var9 = Math.min(var9, var5);
        return Math.min(var9, var6);
    }
    
    public EnumBorderStatus Ý() {
        return (this.Âµá€ < this.Ø­áŒŠá) ? EnumBorderStatus.Â : ((this.Âµá€ > this.Ø­áŒŠá) ? EnumBorderStatus.HorizonCode_Horizon_È : EnumBorderStatus.Ý);
    }
    
    public double Ø­áŒŠá() {
        double var1 = this.HorizonCode_Horizon_È() - this.Ø() / 2.0;
        if (var1 < -this.Ø) {
            var1 = -this.Ø;
        }
        return var1;
    }
    
    public double Âµá€() {
        double var1 = this.Â() - this.Ø() / 2.0;
        if (var1 < -this.Ø) {
            var1 = -this.Ø;
        }
        return var1;
    }
    
    public double Ó() {
        double var1 = this.HorizonCode_Horizon_È() + this.Ø() / 2.0;
        if (var1 > this.Ø) {
            var1 = this.Ø;
        }
        return var1;
    }
    
    public double à() {
        double var1 = this.Â() + this.Ø() / 2.0;
        if (var1 > this.Ø) {
            var1 = this.Ø;
        }
        return var1;
    }
    
    public double HorizonCode_Horizon_È() {
        return this.Â;
    }
    
    public double Â() {
        return this.Ý;
    }
    
    public void Â(final double x, final double z) {
        this.Â = x;
        this.Ý = z;
        for (final IBorderListener var6 : this.ÂµÈ()) {
            var6.HorizonCode_Horizon_È(this, x, z);
        }
    }
    
    public double Ø() {
        if (this.Ý() != EnumBorderStatus.Ý) {
            final double var1 = (System.currentTimeMillis() - this.à) / (this.Ó - this.à);
            if (var1 < 1.0) {
                return this.Ø­áŒŠá + (this.Âµá€ - this.Ø­áŒŠá) * var1;
            }
            this.HorizonCode_Horizon_È(this.Âµá€);
        }
        return this.Ø­áŒŠá;
    }
    
    public long áŒŠÆ() {
        return (this.Ý() != EnumBorderStatus.Ý) ? (this.Ó - System.currentTimeMillis()) : 0L;
    }
    
    public double áˆºÑ¢Õ() {
        return this.Âµá€;
    }
    
    public void HorizonCode_Horizon_È(final double newSize) {
        this.Ø­áŒŠá = newSize;
        this.Âµá€ = newSize;
        this.Ó = System.currentTimeMillis();
        this.à = this.Ó;
        for (final IBorderListener var4 : this.ÂµÈ()) {
            var4.HorizonCode_Horizon_È(this, newSize);
        }
    }
    
    public void HorizonCode_Horizon_È(final double p_177738_1_, final double p_177738_3_, final long p_177738_5_) {
        this.Ø­áŒŠá = p_177738_1_;
        this.Âµá€ = p_177738_3_;
        this.à = System.currentTimeMillis();
        this.Ó = this.à + p_177738_5_;
        for (final IBorderListener var8 : this.ÂµÈ()) {
            var8.HorizonCode_Horizon_È(this, p_177738_1_, p_177738_3_, p_177738_5_);
        }
    }
    
    protected List ÂµÈ() {
        return Lists.newArrayList((Iterable)this.HorizonCode_Horizon_È);
    }
    
    public void HorizonCode_Horizon_È(final IBorderListener listener) {
        this.HorizonCode_Horizon_È.add(listener);
    }
    
    public void HorizonCode_Horizon_È(final int size) {
        this.Ø = size;
    }
    
    public int á() {
        return this.Ø;
    }
    
    public double ˆÏ­() {
        return this.áˆºÑ¢Õ;
    }
    
    public void Â(final double p_177724_1_) {
        this.áˆºÑ¢Õ = p_177724_1_;
        for (final IBorderListener var4 : this.ÂµÈ()) {
            var4.Ý(this, p_177724_1_);
        }
    }
    
    public double £á() {
        return this.áŒŠÆ;
    }
    
    public void Ý(final double p_177744_1_) {
        this.áŒŠÆ = p_177744_1_;
        for (final IBorderListener var4 : this.ÂµÈ()) {
            var4.Â(this, p_177744_1_);
        }
    }
    
    public double Å() {
        return (this.Ó == this.à) ? 0.0 : (Math.abs(this.Ø­áŒŠá - this.Âµá€) / (this.Ó - this.à));
    }
    
    public int £à() {
        return this.ÂµÈ;
    }
    
    public void Â(final int warningTime) {
        this.ÂµÈ = warningTime;
        for (final IBorderListener var3 : this.ÂµÈ()) {
            var3.HorizonCode_Horizon_È(this, warningTime);
        }
    }
    
    public int µà() {
        return this.á;
    }
    
    public void Ý(final int warningDistance) {
        this.á = warningDistance;
        for (final IBorderListener var3 : this.ÂµÈ()) {
            var3.Â(this, warningDistance);
        }
    }
}
