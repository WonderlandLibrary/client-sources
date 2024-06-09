package HORIZON-6-0-SKIDPROTECTION;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GenLayerHills extends GenLayer
{
    private static final Logger Ý;
    private GenLayer Ø­áŒŠá;
    private static final String Âµá€ = "CL_00000563";
    
    static {
        Ý = LogManager.getLogger();
    }
    
    public GenLayerHills(final long p_i45479_1_, final GenLayer p_i45479_3_, final GenLayer p_i45479_4_) {
        super(p_i45479_1_);
        this.HorizonCode_Horizon_È = p_i45479_3_;
        this.Ø­áŒŠá = p_i45479_4_;
    }
    
    @Override
    public int[] HorizonCode_Horizon_È(final int areaX, final int areaY, final int areaWidth, final int areaHeight) {
        final int[] var5 = this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(areaX - 1, areaY - 1, areaWidth + 2, areaHeight + 2);
        final int[] var6 = this.Ø­áŒŠá.HorizonCode_Horizon_È(areaX - 1, areaY - 1, areaWidth + 2, areaHeight + 2);
        final int[] var7 = IntCache.HorizonCode_Horizon_È(areaWidth * areaHeight);
        for (int var8 = 0; var8 < areaHeight; ++var8) {
            for (int var9 = 0; var9 < areaWidth; ++var9) {
                this.HorizonCode_Horizon_È(var9 + areaX, (long)(var8 + areaY));
                final int var10 = var5[var9 + 1 + (var8 + 1) * (areaWidth + 2)];
                final int var11 = var6[var9 + 1 + (var8 + 1) * (areaWidth + 2)];
                final boolean var12 = (var11 - 2) % 29 == 0;
                if (var10 > 255) {
                    GenLayerHills.Ý.debug("old! " + var10);
                }
                if (var10 != 0 && var11 >= 2 && (var11 - 2) % 29 == 1 && var10 < 128) {
                    if (BiomeGenBase.Âµá€(var10 + 128) != null) {
                        var7[var9 + var8 * areaWidth] = var10 + 128;
                    }
                    else {
                        var7[var9 + var8 * areaWidth] = var10;
                    }
                }
                else if (this.HorizonCode_Horizon_È(3) != 0 && !var12) {
                    var7[var9 + var8 * areaWidth] = var10;
                }
                else {
                    int var13;
                    if ((var13 = var10) == BiomeGenBase.ˆà.ÇªÔ) {
                        var13 = BiomeGenBase.ÇŽÕ.ÇªÔ;
                    }
                    else if (var10 == BiomeGenBase.Ø­à.ÇªÔ) {
                        var13 = BiomeGenBase.É.ÇªÔ;
                    }
                    else if (var10 == BiomeGenBase.È.ÇªÔ) {
                        var13 = BiomeGenBase.áŠ.ÇªÔ;
                    }
                    else if (var10 == BiomeGenBase.ˆáŠ.ÇªÔ) {
                        var13 = BiomeGenBase.µà.ÇªÔ;
                    }
                    else if (var10 == BiomeGenBase.µÕ.ÇªÔ) {
                        var13 = BiomeGenBase.áƒ.ÇªÔ;
                    }
                    else if (var10 == BiomeGenBase.Ø­Âµ.ÇªÔ) {
                        var13 = BiomeGenBase.Ä.ÇªÔ;
                    }
                    else if (var10 == BiomeGenBase.áŒŠ.ÇªÔ) {
                        var13 = BiomeGenBase.£ÂµÄ.ÇªÔ;
                    }
                    else if (var10 == BiomeGenBase.µà.ÇªÔ) {
                        if (this.HorizonCode_Horizon_È(3) == 0) {
                            var13 = BiomeGenBase.É.ÇªÔ;
                        }
                        else {
                            var13 = BiomeGenBase.Ø­à.ÇªÔ;
                        }
                    }
                    else if (var10 == BiomeGenBase.ŒÏ.ÇªÔ) {
                        var13 = BiomeGenBase.Çªà¢.ÇªÔ;
                    }
                    else if (var10 == BiomeGenBase.Õ.ÇªÔ) {
                        var13 = BiomeGenBase.à¢.ÇªÔ;
                    }
                    else if (var10 == BiomeGenBase.£à.ÇªÔ) {
                        var13 = BiomeGenBase.¥à.ÇªÔ;
                    }
                    else if (var10 == BiomeGenBase.¥Æ.ÇªÔ) {
                        var13 = BiomeGenBase.Ñ¢Â.ÇªÔ;
                    }
                    else if (var10 == BiomeGenBase.Ï­à.ÇªÔ) {
                        var13 = BiomeGenBase.áˆºáˆºÈ.ÇªÔ;
                    }
                    else if (GenLayer.HorizonCode_Horizon_È(var10, BiomeGenBase.Ï.ÇªÔ)) {
                        var13 = BiomeGenBase.ÇŽá€.ÇªÔ;
                    }
                    else if (var10 == BiomeGenBase.¥à.ÇªÔ && this.HorizonCode_Horizon_È(3) == 0) {
                        final int var14 = this.HorizonCode_Horizon_È(2);
                        if (var14 == 0) {
                            var13 = BiomeGenBase.µà.ÇªÔ;
                        }
                        else {
                            var13 = BiomeGenBase.Ø­à.ÇªÔ;
                        }
                    }
                    if (var12 && var13 != var10) {
                        if (BiomeGenBase.Âµá€(var13 + 128) != null) {
                            var13 += 128;
                        }
                        else {
                            var13 = var10;
                        }
                    }
                    if (var13 == var10) {
                        var7[var9 + var8 * areaWidth] = var10;
                    }
                    else {
                        final int var14 = var5[var9 + 1 + (var8 + 1 - 1) * (areaWidth + 2)];
                        final int var15 = var5[var9 + 1 + 1 + (var8 + 1) * (areaWidth + 2)];
                        final int var16 = var5[var9 + 1 - 1 + (var8 + 1) * (areaWidth + 2)];
                        final int var17 = var5[var9 + 1 + (var8 + 1 + 1) * (areaWidth + 2)];
                        int var18 = 0;
                        if (GenLayer.HorizonCode_Horizon_È(var14, var10)) {
                            ++var18;
                        }
                        if (GenLayer.HorizonCode_Horizon_È(var15, var10)) {
                            ++var18;
                        }
                        if (GenLayer.HorizonCode_Horizon_È(var16, var10)) {
                            ++var18;
                        }
                        if (GenLayer.HorizonCode_Horizon_È(var17, var10)) {
                            ++var18;
                        }
                        if (var18 >= 3) {
                            var7[var9 + var8 * areaWidth] = var13;
                        }
                        else {
                            var7[var9 + var8 * areaWidth] = var10;
                        }
                    }
                }
            }
        }
        return var7;
    }
}
