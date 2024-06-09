package HORIZON-6-0-SKIDPROTECTION;

public abstract class PathNavigate
{
    protected EntityLiving HorizonCode_Horizon_È;
    protected World Â;
    protected PathEntity Ý;
    protected double Ø­áŒŠá;
    private final IAttributeInstance Âµá€;
    private int Ó;
    private int à;
    private Vec3 Ø;
    private float áŒŠÆ;
    private final PathFinder_1163157884 áˆºÑ¢Õ;
    private static final String ÂµÈ = "CL_00001627";
    
    public PathNavigate(final EntityLiving p_i1671_1_, final World worldIn) {
        this.Ø = new Vec3(0.0, 0.0, 0.0);
        this.áŒŠÆ = 1.0f;
        this.HorizonCode_Horizon_È = p_i1671_1_;
        this.Â = worldIn;
        this.Âµá€ = p_i1671_1_.HorizonCode_Horizon_È(SharedMonsterAttributes.Â);
        this.áˆºÑ¢Õ = this.HorizonCode_Horizon_È();
    }
    
    protected abstract PathFinder_1163157884 HorizonCode_Horizon_È();
    
    public void HorizonCode_Horizon_È(final double p_75489_1_) {
        this.Ø­áŒŠá = p_75489_1_;
    }
    
    public float Â() {
        return (float)this.Âµá€.Âµá€();
    }
    
    public final PathEntity HorizonCode_Horizon_È(final double p_75488_1_, final double p_75488_3_, final double p_75488_5_) {
        return this.HorizonCode_Horizon_È(new BlockPos(MathHelper.Ý(p_75488_1_), (int)p_75488_3_, MathHelper.Ý(p_75488_5_)));
    }
    
    public PathEntity HorizonCode_Horizon_È(final BlockPos p_179680_1_) {
        if (!this.áŒŠÆ()) {
            return null;
        }
        final float var2 = this.Â();
        this.Â.Ï­Ðƒà.HorizonCode_Horizon_È("pathfind");
        final BlockPos var3 = new BlockPos(this.HorizonCode_Horizon_È);
        final int var4 = (int)(var2 + 8.0f);
        final ChunkCache var5 = new ChunkCache(this.Â, var3.Â(-var4, -var4, -var4), var3.Â(var4, var4, var4), 0);
        final PathEntity var6 = this.áˆºÑ¢Õ.HorizonCode_Horizon_È(var5, this.HorizonCode_Horizon_È, p_179680_1_, var2);
        this.Â.Ï­Ðƒà.Â();
        return var6;
    }
    
    public boolean HorizonCode_Horizon_È(final double p_75492_1_, final double p_75492_3_, final double p_75492_5_, final double p_75492_7_) {
        final PathEntity var9 = this.HorizonCode_Horizon_È(MathHelper.Ý(p_75492_1_), (int)p_75492_3_, MathHelper.Ý(p_75492_5_));
        return this.HorizonCode_Horizon_È(var9, p_75492_7_);
    }
    
    public void HorizonCode_Horizon_È(final float p_179678_1_) {
        this.áŒŠÆ = p_179678_1_;
    }
    
    public PathEntity HorizonCode_Horizon_È(final Entity p_75494_1_) {
        if (!this.áŒŠÆ()) {
            return null;
        }
        final float var2 = this.Â();
        this.Â.Ï­Ðƒà.HorizonCode_Horizon_È("pathfind");
        final BlockPos var3 = new BlockPos(this.HorizonCode_Horizon_È).Ø­áŒŠá();
        final int var4 = (int)(var2 + 16.0f);
        final ChunkCache var5 = new ChunkCache(this.Â, var3.Â(-var4, -var4, -var4), var3.Â(var4, var4, var4), 0);
        final PathEntity var6 = this.áˆºÑ¢Õ.HorizonCode_Horizon_È(var5, this.HorizonCode_Horizon_È, p_75494_1_, var2);
        this.Â.Ï­Ðƒà.Â();
        return var6;
    }
    
    public boolean HorizonCode_Horizon_È(final Entity p_75497_1_, final double p_75497_2_) {
        final PathEntity var4 = this.HorizonCode_Horizon_È(p_75497_1_);
        return var4 != null && this.HorizonCode_Horizon_È(var4, p_75497_2_);
    }
    
    public boolean HorizonCode_Horizon_È(final PathEntity p_75484_1_, final double p_75484_2_) {
        if (p_75484_1_ == null) {
            this.Ý = null;
            return false;
        }
        if (!p_75484_1_.HorizonCode_Horizon_È(this.Ý)) {
            this.Ý = p_75484_1_;
        }
        this.ÂµÈ();
        if (this.Ý.Ø­áŒŠá() == 0) {
            return false;
        }
        this.Ø­áŒŠá = p_75484_2_;
        final Vec3 var4 = this.Ø();
        this.à = this.Ó;
        this.Ø = var4;
        return true;
    }
    
    public PathEntity Ý() {
        return this.Ý;
    }
    
    public void Ø­áŒŠá() {
        ++this.Ó;
        if (!this.Ó()) {
            if (this.áŒŠÆ()) {
                this.Âµá€();
            }
            else if (this.Ý != null && this.Ý.Âµá€() < this.Ý.Ø­áŒŠá()) {
                final Vec3 var1 = this.Ø();
                final Vec3 var2 = this.Ý.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È, this.Ý.Âµá€());
                if (var1.Â > var2.Â && !this.HorizonCode_Horizon_È.ŠÂµà && MathHelper.Ý(var1.HorizonCode_Horizon_È) == MathHelper.Ý(var2.HorizonCode_Horizon_È) && MathHelper.Ý(var1.Ý) == MathHelper.Ý(var2.Ý)) {
                    this.Ý.Ý(this.Ý.Âµá€() + 1);
                }
            }
            if (!this.Ó()) {
                final Vec3 var1 = this.Ý.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È);
                if (var1 != null) {
                    this.HorizonCode_Horizon_È.ŒÏ().HorizonCode_Horizon_È(var1.HorizonCode_Horizon_È, var1.Â, var1.Ý, this.Ø­áŒŠá);
                }
            }
        }
    }
    
    protected void Âµá€() {
        final Vec3 var1 = this.Ø();
        int var2 = this.Ý.Ø­áŒŠá();
        for (int var3 = this.Ý.Âµá€(); var3 < this.Ý.Ø­áŒŠá(); ++var3) {
            if (this.Ý.HorizonCode_Horizon_È(var3).Â != (int)var1.Â) {
                var2 = var3;
                break;
            }
        }
        final float var4 = this.HorizonCode_Horizon_È.áŒŠ * this.HorizonCode_Horizon_È.áŒŠ * this.áŒŠÆ;
        for (int var5 = this.Ý.Âµá€(); var5 < var2; ++var5) {
            final Vec3 var6 = this.Ý.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È, var5);
            if (var1.à(var6) < var4) {
                this.Ý.Ý(var5 + 1);
            }
        }
        int var5 = MathHelper.Ó(this.HorizonCode_Horizon_È.áŒŠ);
        final int var7 = (int)this.HorizonCode_Horizon_È.£ÂµÄ + 1;
        final int var8 = var5;
        for (int var9 = var2 - 1; var9 >= this.Ý.Âµá€(); --var9) {
            if (this.HorizonCode_Horizon_È(var1, this.Ý.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È, var9), var5, var7, var8)) {
                this.Ý.Ý(var9);
                break;
            }
        }
        this.HorizonCode_Horizon_È(var1);
    }
    
    protected void HorizonCode_Horizon_È(final Vec3 p_179677_1_) {
        if (this.Ó - this.à > 100) {
            if (p_179677_1_.à(this.Ø) < 2.25) {
                this.à();
            }
            this.à = this.Ó;
            this.Ø = p_179677_1_;
        }
    }
    
    public boolean Ó() {
        return this.Ý == null || this.Ý.Â();
    }
    
    public void à() {
        this.Ý = null;
    }
    
    protected abstract Vec3 Ø();
    
    protected abstract boolean áŒŠÆ();
    
    protected boolean áˆºÑ¢Õ() {
        return this.HorizonCode_Horizon_È.£ÂµÄ() || this.HorizonCode_Horizon_È.ÇŽá€();
    }
    
    protected void ÂµÈ() {
    }
    
    protected abstract boolean HorizonCode_Horizon_È(final Vec3 p0, final Vec3 p1, final int p2, final int p3, final int p4);
}
