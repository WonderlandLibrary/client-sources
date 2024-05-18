package HORIZON-6-0-SKIDPROTECTION;

import java.util.Iterator;

public class PathNavigateGround extends PathNavigate
{
    protected WalkNodeProcessor Âµá€;
    private boolean Ó;
    private static final String à = "CL_00002246";
    
    public PathNavigateGround(final EntityLiving p_i45875_1_, final World worldIn) {
        super(p_i45875_1_, worldIn);
    }
    
    @Override
    protected PathFinder_1163157884 HorizonCode_Horizon_È() {
        (this.Âµá€ = new WalkNodeProcessor()).HorizonCode_Horizon_È(true);
        return new PathFinder_1163157884(this.Âµá€);
    }
    
    @Override
    protected boolean áŒŠÆ() {
        return this.HorizonCode_Horizon_È.ŠÂµà || (this.£á() && this.áˆºÑ¢Õ()) || (this.HorizonCode_Horizon_È.áˆºÇŽØ() && this.HorizonCode_Horizon_È instanceof EntityZombie && this.HorizonCode_Horizon_È.Æ instanceof EntityChicken);
    }
    
    @Override
    protected Vec3 Ø() {
        return new Vec3(this.HorizonCode_Horizon_È.ŒÏ, this.Å(), this.HorizonCode_Horizon_È.Ê);
    }
    
    private int Å() {
        if (this.HorizonCode_Horizon_È.£ÂµÄ() && this.£á()) {
            int var1 = (int)this.HorizonCode_Horizon_È.£É().Â;
            Block var2 = this.Â.Â(new BlockPos(MathHelper.Ý(this.HorizonCode_Horizon_È.ŒÏ), var1, MathHelper.Ý(this.HorizonCode_Horizon_È.Ê))).Ý();
            int var3 = 0;
            while (var2 == Blocks.áˆºÑ¢Õ || var2 == Blocks.ÂµÈ) {
                ++var1;
                var2 = this.Â.Â(new BlockPos(MathHelper.Ý(this.HorizonCode_Horizon_È.ŒÏ), var1, MathHelper.Ý(this.HorizonCode_Horizon_È.Ê))).Ý();
                if (++var3 > 16) {
                    return (int)this.HorizonCode_Horizon_È.£É().Â;
                }
            }
            return var1;
        }
        return (int)(this.HorizonCode_Horizon_È.£É().Â + 0.5);
    }
    
    @Override
    protected void ÂµÈ() {
        super.ÂµÈ();
        if (this.Ó) {
            if (this.Â.áˆºÑ¢Õ(new BlockPos(MathHelper.Ý(this.HorizonCode_Horizon_È.ŒÏ), (int)(this.HorizonCode_Horizon_È.£É().Â + 0.5), MathHelper.Ý(this.HorizonCode_Horizon_È.Ê)))) {
                return;
            }
            for (int var1 = 0; var1 < this.Ý.Ø­áŒŠá(); ++var1) {
                final PathPoint var2 = this.Ý.HorizonCode_Horizon_È(var1);
                if (this.Â.áˆºÑ¢Õ(new BlockPos(var2.HorizonCode_Horizon_È, var2.Â, var2.Ý))) {
                    this.Ý.Â(var1 - 1);
                    return;
                }
            }
        }
    }
    
    @Override
    protected boolean HorizonCode_Horizon_È(final Vec3 p_75493_1_, final Vec3 p_75493_2_, int p_75493_3_, final int p_75493_4_, int p_75493_5_) {
        int var6 = MathHelper.Ý(p_75493_1_.HorizonCode_Horizon_È);
        int var7 = MathHelper.Ý(p_75493_1_.Ý);
        double var8 = p_75493_2_.HorizonCode_Horizon_È - p_75493_1_.HorizonCode_Horizon_È;
        double var9 = p_75493_2_.Ý - p_75493_1_.Ý;
        final double var10 = var8 * var8 + var9 * var9;
        if (var10 < 1.0E-8) {
            return false;
        }
        final double var11 = 1.0 / Math.sqrt(var10);
        var8 *= var11;
        var9 *= var11;
        p_75493_3_ += 2;
        p_75493_5_ += 2;
        if (!this.HorizonCode_Horizon_È(var6, (int)p_75493_1_.Â, var7, p_75493_3_, p_75493_4_, p_75493_5_, p_75493_1_, var8, var9)) {
            return false;
        }
        p_75493_3_ -= 2;
        p_75493_5_ -= 2;
        final double var12 = 1.0 / Math.abs(var8);
        final double var13 = 1.0 / Math.abs(var9);
        double var14 = var6 * 1 - p_75493_1_.HorizonCode_Horizon_È;
        double var15 = var7 * 1 - p_75493_1_.Ý;
        if (var8 >= 0.0) {
            ++var14;
        }
        if (var9 >= 0.0) {
            ++var15;
        }
        var14 /= var8;
        var15 /= var9;
        final int var16 = (var8 < 0.0) ? -1 : 1;
        final int var17 = (var9 < 0.0) ? -1 : 1;
        final int var18 = MathHelper.Ý(p_75493_2_.HorizonCode_Horizon_È);
        final int var19 = MathHelper.Ý(p_75493_2_.Ý);
        int var20 = var18 - var6;
        int var21 = var19 - var7;
        while (var20 * var16 > 0 || var21 * var17 > 0) {
            if (var14 < var15) {
                var14 += var12;
                var6 += var16;
                var20 = var18 - var6;
            }
            else {
                var15 += var13;
                var7 += var17;
                var21 = var19 - var7;
            }
            if (!this.HorizonCode_Horizon_È(var6, (int)p_75493_1_.Â, var7, p_75493_3_, p_75493_4_, p_75493_5_, p_75493_1_, var8, var9)) {
                return false;
            }
        }
        return true;
    }
    
    private boolean HorizonCode_Horizon_È(final int p_179683_1_, final int p_179683_2_, final int p_179683_3_, final int p_179683_4_, final int p_179683_5_, final int p_179683_6_, final Vec3 p_179683_7_, final double p_179683_8_, final double p_179683_10_) {
        final int var12 = p_179683_1_ - p_179683_4_ / 2;
        final int var13 = p_179683_3_ - p_179683_6_ / 2;
        if (!this.Â(var12, p_179683_2_, var13, p_179683_4_, p_179683_5_, p_179683_6_, p_179683_7_, p_179683_8_, p_179683_10_)) {
            return false;
        }
        for (int var14 = var12; var14 < var12 + p_179683_4_; ++var14) {
            for (int var15 = var13; var15 < var13 + p_179683_6_; ++var15) {
                final double var16 = var14 + 0.5 - p_179683_7_.HorizonCode_Horizon_È;
                final double var17 = var15 + 0.5 - p_179683_7_.Ý;
                if (var16 * p_179683_8_ + var17 * p_179683_10_ >= 0.0) {
                    final Block var18 = this.Â.Â(new BlockPos(var14, p_179683_2_ - 1, var15)).Ý();
                    final Material var19 = var18.Ó();
                    if (var19 == Material.HorizonCode_Horizon_È) {
                        return false;
                    }
                    if (var19 == Material.Ø && !this.HorizonCode_Horizon_È.£ÂµÄ()) {
                        return false;
                    }
                    if (var19 == Material.áŒŠÆ) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
    
    private boolean Â(final int p_179692_1_, final int p_179692_2_, final int p_179692_3_, final int p_179692_4_, final int p_179692_5_, final int p_179692_6_, final Vec3 p_179692_7_, final double p_179692_8_, final double p_179692_10_) {
        for (final BlockPos var13 : BlockPos.Â(new BlockPos(p_179692_1_, p_179692_2_, p_179692_3_), new BlockPos(p_179692_1_ + p_179692_4_ - 1, p_179692_2_ + p_179692_5_ - 1, p_179692_3_ + p_179692_6_ - 1))) {
            final double var14 = var13.HorizonCode_Horizon_È() + 0.5 - p_179692_7_.HorizonCode_Horizon_È;
            final double var15 = var13.Ý() + 0.5 - p_179692_7_.Ý;
            if (var14 * p_179692_8_ + var15 * p_179692_10_ >= 0.0) {
                final Block var16 = this.Â.Â(var13).Ý();
                if (!var16.HorizonCode_Horizon_È((IBlockAccess)this.Â, var13)) {
                    return false;
                }
                continue;
            }
        }
        return true;
    }
    
    public void HorizonCode_Horizon_È(final boolean p_179690_1_) {
        this.Âµá€.Ý(p_179690_1_);
    }
    
    public boolean á() {
        return this.Âµá€.Ø­áŒŠá();
    }
    
    public void Â(final boolean p_179688_1_) {
        this.Âµá€.Â(p_179688_1_);
    }
    
    public void Ý(final boolean p_179691_1_) {
        this.Âµá€.HorizonCode_Horizon_È(p_179691_1_);
    }
    
    public boolean ˆÏ­() {
        return this.Âµá€.Â();
    }
    
    public void Ø­áŒŠá(final boolean p_179693_1_) {
        this.Âµá€.Ø­áŒŠá(p_179693_1_);
    }
    
    public boolean £á() {
        return this.Âµá€.Ý();
    }
    
    public void Âµá€(final boolean p_179685_1_) {
        this.Ó = p_179685_1_;
    }
}
