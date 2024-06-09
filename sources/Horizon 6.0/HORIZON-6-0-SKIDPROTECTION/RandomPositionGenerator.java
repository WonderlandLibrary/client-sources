package HORIZON-6-0-SKIDPROTECTION;

import java.util.Random;

public class RandomPositionGenerator
{
    private static Vec3 HorizonCode_Horizon_È;
    private static final String Â = "CL_00001629";
    
    static {
        RandomPositionGenerator.HorizonCode_Horizon_È = new Vec3(0.0, 0.0, 0.0);
    }
    
    public static Vec3 HorizonCode_Horizon_È(final EntityCreature p_75463_0_, final int p_75463_1_, final int p_75463_2_) {
        return Ý(p_75463_0_, p_75463_1_, p_75463_2_, null);
    }
    
    public static Vec3 HorizonCode_Horizon_È(final EntityCreature p_75464_0_, final int p_75464_1_, final int p_75464_2_, final Vec3 p_75464_3_) {
        RandomPositionGenerator.HorizonCode_Horizon_È = p_75464_3_.HorizonCode_Horizon_È(p_75464_0_.ŒÏ, p_75464_0_.Çªà¢, p_75464_0_.Ê);
        return Ý(p_75464_0_, p_75464_1_, p_75464_2_, RandomPositionGenerator.HorizonCode_Horizon_È);
    }
    
    public static Vec3 Â(final EntityCreature p_75461_0_, final int p_75461_1_, final int p_75461_2_, final Vec3 p_75461_3_) {
        RandomPositionGenerator.HorizonCode_Horizon_È = new Vec3(p_75461_0_.ŒÏ, p_75461_0_.Çªà¢, p_75461_0_.Ê).Ø­áŒŠá(p_75461_3_);
        return Ý(p_75461_0_, p_75461_1_, p_75461_2_, RandomPositionGenerator.HorizonCode_Horizon_È);
    }
    
    private static Vec3 Ý(final EntityCreature p_75462_0_, final int p_75462_1_, final int p_75462_2_, final Vec3 p_75462_3_) {
        final Random var4 = p_75462_0_.ˆÐƒØ();
        boolean var5 = false;
        int var6 = 0;
        int var7 = 0;
        int var8 = 0;
        float var9 = -99999.0f;
        boolean var12;
        if (p_75462_0_.Šáƒ()) {
            final double var10 = p_75462_0_.Ø­à().Ý(MathHelper.Ý(p_75462_0_.ŒÏ), MathHelper.Ý(p_75462_0_.Çªà¢), MathHelper.Ý(p_75462_0_.Ê)) + 4.0;
            final double var11 = p_75462_0_.µÕ() + p_75462_1_;
            var12 = (var10 < var11 * var11);
        }
        else {
            var12 = false;
        }
        for (int var13 = 0; var13 < 10; ++var13) {
            int var14 = var4.nextInt(2 * p_75462_1_ + 1) - p_75462_1_;
            int var15 = var4.nextInt(2 * p_75462_2_ + 1) - p_75462_2_;
            int var16 = var4.nextInt(2 * p_75462_1_ + 1) - p_75462_1_;
            if (p_75462_3_ == null || var14 * p_75462_3_.HorizonCode_Horizon_È + var16 * p_75462_3_.Ý >= 0.0) {
                if (p_75462_0_.Šáƒ() && p_75462_1_ > 1) {
                    final BlockPos var17 = p_75462_0_.Ø­à();
                    if (p_75462_0_.ŒÏ > var17.HorizonCode_Horizon_È()) {
                        var14 -= var4.nextInt(p_75462_1_ / 2);
                    }
                    else {
                        var14 += var4.nextInt(p_75462_1_ / 2);
                    }
                    if (p_75462_0_.Ê > var17.Ý()) {
                        var16 -= var4.nextInt(p_75462_1_ / 2);
                    }
                    else {
                        var16 += var4.nextInt(p_75462_1_ / 2);
                    }
                }
                var14 += MathHelper.Ý(p_75462_0_.ŒÏ);
                var15 += MathHelper.Ý(p_75462_0_.Çªà¢);
                var16 += MathHelper.Ý(p_75462_0_.Ê);
                final BlockPos var17 = new BlockPos(var14, var15, var16);
                if (!var12 || p_75462_0_.Ø­áŒŠá(var17)) {
                    final float var18 = p_75462_0_.HorizonCode_Horizon_È(var17);
                    if (var18 > var9) {
                        var9 = var18;
                        var6 = var14;
                        var7 = var15;
                        var8 = var16;
                        var5 = true;
                    }
                }
            }
        }
        if (var5) {
            return new Vec3(var6, var7, var8);
        }
        return null;
    }
}
