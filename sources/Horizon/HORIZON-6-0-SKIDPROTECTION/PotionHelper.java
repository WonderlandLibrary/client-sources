package HORIZON-6-0-SKIDPROTECTION;

import java.util.ArrayList;
import com.google.common.collect.Lists;
import java.util.List;
import java.util.Iterator;
import java.util.Collection;
import com.google.common.collect.Maps;
import java.util.Map;

public class PotionHelper
{
    public static final String HorizonCode_Horizon_È;
    public static final String Â;
    public static final String Ý = "+0-1-2-3&4-4+13";
    public static final String Ø­áŒŠá;
    public static final String Âµá€;
    public static final String Ó;
    public static final String à;
    public static final String Ø;
    public static final String áŒŠÆ;
    public static final String áˆºÑ¢Õ;
    public static final String ÂµÈ;
    public static final String á;
    public static final String ˆÏ­;
    public static final String £á;
    private static final Map Å;
    private static final Map £à;
    private static final Map µà;
    private static final String[] ˆà;
    private static final String ¥Æ = "CL_00000078";
    
    static {
        HorizonCode_Horizon_È = null;
        Å = Maps.newHashMap();
        £à = Maps.newHashMap();
        PotionHelper.Å.put(Potion.á.Â(), "0 & !1 & !2 & !3 & 0+6");
        Â = "-0+1-2-3&4-4+13";
        PotionHelper.Å.put(Potion.Ý.Â(), "!0 & 1 & !2 & !3 & 1+6");
        Ø = "+0+1-2-3&4-4+13";
        PotionHelper.Å.put(Potion.£á.Â(), "0 & 1 & !2 & !3 & 0+6");
        Ó = "+0-1+2-3&4-4+13";
        PotionHelper.Å.put(Potion.Ø.Â(), "0 & !1 & 2 & !3");
        Ø­áŒŠá = "-0-1+2-3&4-4+13";
        PotionHelper.Å.put(Potion.µÕ.Â(), "!0 & !1 & 2 & !3 & 2+6");
        Âµá€ = "-0+3-4+13";
        PotionHelper.Å.put(Potion.Ø­à.Â(), "!0 & !1 & !2 & 3 & 3+6");
        PotionHelper.Å.put(Potion.áŒŠÆ.Â(), "!0 & !1 & 2 & 3");
        PotionHelper.Å.put(Potion.Ø­áŒŠá.Â(), "!0 & 1 & !2 & 3 & 3+6");
        à = "+0-1-2+3&4-4+13";
        PotionHelper.Å.put(Potion.à.Â(), "0 & !1 & !2 & 3 & 3+6");
        á = "-0+1+2-3+13&4-4";
        PotionHelper.Å.put(Potion.ˆà.Â(), "!0 & 1 & 2 & !3 & 2+6");
        PotionHelper.Å.put(Potion.£à.Â(), "!0 & 1 & 2 & 3 & 2+6");
        ˆÏ­ = "+0-1+2+3+13&4-4";
        PotionHelper.Å.put(Potion.Å.Â(), "0 & !1 & 2 & 3 & 2+6");
        £á = "+0+1-2+3&4-4+13";
        PotionHelper.Å.put(Potion.áˆºÑ¢Õ.Â(), "0 & 1 & !2 & 3");
        áˆºÑ¢Õ = "+5-6-7";
        PotionHelper.£à.put(Potion.Ý.Â(), "5");
        PotionHelper.£à.put(Potion.Âµá€.Â(), "5");
        PotionHelper.£à.put(Potion.à.Â(), "5");
        PotionHelper.£à.put(Potion.á.Â(), "5");
        PotionHelper.£à.put(Potion.áŒŠÆ.Â(), "5");
        PotionHelper.£à.put(Potion.Ø.Â(), "5");
        PotionHelper.£à.put(Potion.ˆÏ­.Â(), "5");
        PotionHelper.£à.put(Potion.µÕ.Â(), "5");
        PotionHelper.£à.put(Potion.áˆºÑ¢Õ.Â(), "5");
        áŒŠÆ = "-5+6-7";
        ÂµÈ = "+14&13-13";
        µà = Maps.newHashMap();
        ˆà = new String[] { "potion.prefix.mundane", "potion.prefix.uninteresting", "potion.prefix.bland", "potion.prefix.clear", "potion.prefix.milky", "potion.prefix.diffuse", "potion.prefix.artless", "potion.prefix.thin", "potion.prefix.awkward", "potion.prefix.flat", "potion.prefix.bulky", "potion.prefix.bungling", "potion.prefix.buttered", "potion.prefix.smooth", "potion.prefix.suave", "potion.prefix.debonair", "potion.prefix.thick", "potion.prefix.elegant", "potion.prefix.fancy", "potion.prefix.charming", "potion.prefix.dashing", "potion.prefix.refined", "potion.prefix.cordial", "potion.prefix.sparkling", "potion.prefix.potent", "potion.prefix.foul", "potion.prefix.odorless", "potion.prefix.rank", "potion.prefix.harsh", "potion.prefix.acrid", "potion.prefix.gross", "potion.prefix.stinky" };
    }
    
    public static boolean HorizonCode_Horizon_È(final int p_77914_0_, final int p_77914_1_) {
        return (p_77914_0_ & 1 << p_77914_1_) != 0x0;
    }
    
    private static int Â(final int p_77910_0_, final int p_77910_1_) {
        return HorizonCode_Horizon_È(p_77910_0_, p_77910_1_) ? 1 : 0;
    }
    
    private static int Ý(final int p_77916_0_, final int p_77916_1_) {
        return HorizonCode_Horizon_È(p_77916_0_, p_77916_1_) ? 0 : 1;
    }
    
    public static int HorizonCode_Horizon_È(final int p_77909_0_) {
        return HorizonCode_Horizon_È(p_77909_0_, 5, 4, 3, 2, 1);
    }
    
    public static int HorizonCode_Horizon_È(final Collection p_77911_0_) {
        final int var1 = 3694022;
        if (p_77911_0_ == null || p_77911_0_.isEmpty()) {
            return var1;
        }
        float var2 = 0.0f;
        float var3 = 0.0f;
        float var4 = 0.0f;
        float var5 = 0.0f;
        for (final PotionEffect var7 : p_77911_0_) {
            if (var7.Âµá€()) {
                final int var8 = Potion.HorizonCode_Horizon_È[var7.HorizonCode_Horizon_È()].áˆºÑ¢Õ();
                for (int var9 = 0; var9 <= var7.Ý(); ++var9) {
                    var2 += (var8 >> 16 & 0xFF) / 255.0f;
                    var3 += (var8 >> 8 & 0xFF) / 255.0f;
                    var4 += (var8 >> 0 & 0xFF) / 255.0f;
                    ++var5;
                }
            }
        }
        if (var5 == 0.0f) {
            return 0;
        }
        var2 = var2 / var5 * 255.0f;
        var3 = var3 / var5 * 255.0f;
        var4 = var4 / var5 * 255.0f;
        return (int)var2 << 16 | (int)var3 << 8 | (int)var4;
    }
    
    public static boolean Â(final Collection p_82817_0_) {
        for (final PotionEffect var2 : p_82817_0_) {
            if (!var2.Ø­áŒŠá()) {
                return false;
            }
        }
        return true;
    }
    
    public static int HorizonCode_Horizon_È(final int p_77915_0_, final boolean p_77915_1_) {
        if (p_77915_1_) {
            return HorizonCode_Horizon_È(Â(p_77915_0_, true));
        }
        if (PotionHelper.µà.containsKey(p_77915_0_)) {
            return PotionHelper.µà.get(p_77915_0_);
        }
        final int var2 = HorizonCode_Horizon_È(Â(p_77915_0_, false));
        PotionHelper.µà.put(p_77915_0_, var2);
        return var2;
    }
    
    public static String Â(final int p_77905_0_) {
        final int var1 = HorizonCode_Horizon_È(p_77905_0_);
        return PotionHelper.ˆà[var1];
    }
    
    private static int HorizonCode_Horizon_È(final boolean p_77904_0_, final boolean p_77904_1_, final boolean p_77904_2_, final int p_77904_3_, final int p_77904_4_, final int p_77904_5_, final int p_77904_6_) {
        int var7 = 0;
        if (p_77904_0_) {
            var7 = Ý(p_77904_6_, p_77904_4_);
        }
        else if (p_77904_3_ != -1) {
            if (p_77904_3_ == 0 && Ý(p_77904_6_) == p_77904_4_) {
                var7 = 1;
            }
            else if (p_77904_3_ == 1 && Ý(p_77904_6_) > p_77904_4_) {
                var7 = 1;
            }
            else if (p_77904_3_ == 2 && Ý(p_77904_6_) < p_77904_4_) {
                var7 = 1;
            }
        }
        else {
            var7 = Â(p_77904_6_, p_77904_4_);
        }
        if (p_77904_1_) {
            var7 *= p_77904_5_;
        }
        if (p_77904_2_) {
            var7 *= -1;
        }
        return var7;
    }
    
    private static int Ý(int p_77907_0_) {
        int var1;
        for (var1 = 0; p_77907_0_ > 0; p_77907_0_ &= p_77907_0_ - 1, ++var1) {}
        return var1;
    }
    
    private static int HorizonCode_Horizon_È(final String p_77912_0_, final int p_77912_1_, final int p_77912_2_, final int p_77912_3_) {
        if (p_77912_1_ >= p_77912_0_.length() || p_77912_2_ < 0 || p_77912_1_ >= p_77912_2_) {
            return 0;
        }
        final int var4 = p_77912_0_.indexOf(124, p_77912_1_);
        if (var4 >= 0 && var4 < p_77912_2_) {
            final int var5 = HorizonCode_Horizon_È(p_77912_0_, p_77912_1_, var4 - 1, p_77912_3_);
            if (var5 > 0) {
                return var5;
            }
            final int var6 = HorizonCode_Horizon_È(p_77912_0_, var4 + 1, p_77912_2_, p_77912_3_);
            return (var6 > 0) ? var6 : 0;
        }
        else {
            final int var5 = p_77912_0_.indexOf(38, p_77912_1_);
            if (var5 < 0 || var5 >= p_77912_2_) {
                boolean var7 = false;
                boolean var8 = false;
                boolean var9 = false;
                boolean var10 = false;
                boolean var11 = false;
                byte var12 = -1;
                int var13 = 0;
                int var14 = 0;
                int var15 = 0;
                for (int var16 = p_77912_1_; var16 < p_77912_2_; ++var16) {
                    final char var17 = p_77912_0_.charAt(var16);
                    if (var17 >= '0' && var17 <= '9') {
                        if (var7) {
                            var14 = var17 - '0';
                            var8 = true;
                        }
                        else {
                            var13 *= 10;
                            var13 += var17 - '0';
                            var9 = true;
                        }
                    }
                    else if (var17 == '*') {
                        var7 = true;
                    }
                    else if (var17 == '!') {
                        if (var9) {
                            var15 += HorizonCode_Horizon_È(var10, var8, var11, var12, var13, var14, p_77912_3_);
                            var10 = false;
                            var11 = false;
                            var7 = false;
                            var8 = false;
                            var9 = false;
                            var14 = 0;
                            var13 = 0;
                            var12 = -1;
                        }
                        var10 = true;
                    }
                    else if (var17 == '-') {
                        if (var9) {
                            var15 += HorizonCode_Horizon_È(var10, var8, var11, var12, var13, var14, p_77912_3_);
                            var10 = false;
                            var11 = false;
                            var7 = false;
                            var8 = false;
                            var9 = false;
                            var14 = 0;
                            var13 = 0;
                            var12 = -1;
                        }
                        var11 = true;
                    }
                    else if (var17 != '=' && var17 != '<' && var17 != '>') {
                        if (var17 == '+' && var9) {
                            var15 += HorizonCode_Horizon_È(var10, var8, var11, var12, var13, var14, p_77912_3_);
                            var10 = false;
                            var11 = false;
                            var7 = false;
                            var8 = false;
                            var9 = false;
                            var14 = 0;
                            var13 = 0;
                            var12 = -1;
                        }
                    }
                    else {
                        if (var9) {
                            var15 += HorizonCode_Horizon_È(var10, var8, var11, var12, var13, var14, p_77912_3_);
                            var10 = false;
                            var11 = false;
                            var7 = false;
                            var8 = false;
                            var9 = false;
                            var14 = 0;
                            var13 = 0;
                            var12 = -1;
                        }
                        if (var17 == '=') {
                            var12 = 0;
                        }
                        else if (var17 == '<') {
                            var12 = 2;
                        }
                        else if (var17 == '>') {
                            var12 = 1;
                        }
                    }
                }
                if (var9) {
                    var15 += HorizonCode_Horizon_È(var10, var8, var11, var12, var13, var14, p_77912_3_);
                }
                return var15;
            }
            final int var6 = HorizonCode_Horizon_È(p_77912_0_, p_77912_1_, var5 - 1, p_77912_3_);
            if (var6 <= 0) {
                return 0;
            }
            final int var18 = HorizonCode_Horizon_È(p_77912_0_, var5 + 1, p_77912_2_, p_77912_3_);
            return (var18 <= 0) ? 0 : ((var6 > var18) ? var6 : var18);
        }
    }
    
    public static List Â(final int p_77917_0_, final boolean p_77917_1_) {
        ArrayList var2 = null;
        for (final Potion var6 : Potion.HorizonCode_Horizon_È) {
            if (var6 != null && (!var6.áŒŠÆ() || p_77917_1_)) {
                final String var7 = PotionHelper.Å.get(var6.Â());
                if (var7 != null) {
                    int var8 = HorizonCode_Horizon_È(var7, 0, var7.length(), p_77917_0_);
                    if (var8 > 0) {
                        int var9 = 0;
                        final String var10 = PotionHelper.£à.get(var6.Â());
                        if (var10 != null) {
                            var9 = HorizonCode_Horizon_È(var10, 0, var10.length(), p_77917_0_);
                            if (var9 < 0) {
                                var9 = 0;
                            }
                        }
                        if (var6.Ý()) {
                            var8 = 1;
                        }
                        else {
                            var8 = 1200 * (var8 * 3 + (var8 - 1) * 2);
                            var8 >>= var9;
                            var8 = (int)Math.round(var8 * var6.Ø());
                            if ((p_77917_0_ & 0x4000) != 0x0) {
                                var8 = (int)Math.round(var8 * 0.75 + 0.5);
                            }
                        }
                        if (var2 == null) {
                            var2 = Lists.newArrayList();
                        }
                        final PotionEffect var11 = new PotionEffect(var6.Â(), var8, var9);
                        if ((p_77917_0_ & 0x4000) != 0x0) {
                            var11.HorizonCode_Horizon_È(true);
                        }
                        var2.add(var11);
                    }
                }
            }
        }
        return var2;
    }
    
    private static int HorizonCode_Horizon_È(int p_77906_0_, final int p_77906_1_, final boolean p_77906_2_, final boolean p_77906_3_, final boolean p_77906_4_) {
        if (p_77906_4_) {
            if (!HorizonCode_Horizon_È(p_77906_0_, p_77906_1_)) {
                return 0;
            }
        }
        else if (p_77906_2_) {
            p_77906_0_ &= ~(1 << p_77906_1_);
        }
        else if (p_77906_3_) {
            if ((p_77906_0_ & 1 << p_77906_1_) == 0x0) {
                p_77906_0_ |= 1 << p_77906_1_;
            }
            else {
                p_77906_0_ &= ~(1 << p_77906_1_);
            }
        }
        else {
            p_77906_0_ |= 1 << p_77906_1_;
        }
        return p_77906_0_;
    }
    
    public static int HorizonCode_Horizon_È(int p_77913_0_, final String p_77913_1_) {
        final byte var2 = 0;
        final int var3 = p_77913_1_.length();
        boolean var4 = false;
        boolean var5 = false;
        boolean var6 = false;
        boolean var7 = false;
        int var8 = 0;
        for (int var9 = var2; var9 < var3; ++var9) {
            final char var10 = p_77913_1_.charAt(var9);
            if (var10 >= '0' && var10 <= '9') {
                var8 *= 10;
                var8 += var10 - '0';
                var4 = true;
            }
            else if (var10 == '!') {
                if (var4) {
                    p_77913_0_ = HorizonCode_Horizon_È(p_77913_0_, var8, var6, var5, var7);
                    var7 = false;
                    var5 = false;
                    var6 = false;
                    var4 = false;
                    var8 = 0;
                }
                var5 = true;
            }
            else if (var10 == '-') {
                if (var4) {
                    p_77913_0_ = HorizonCode_Horizon_È(p_77913_0_, var8, var6, var5, var7);
                    var7 = false;
                    var5 = false;
                    var6 = false;
                    var4 = false;
                    var8 = 0;
                }
                var6 = true;
            }
            else if (var10 == '+') {
                if (var4) {
                    p_77913_0_ = HorizonCode_Horizon_È(p_77913_0_, var8, var6, var5, var7);
                    var7 = false;
                    var5 = false;
                    var6 = false;
                    var4 = false;
                    var8 = 0;
                }
            }
            else if (var10 == '&') {
                if (var4) {
                    p_77913_0_ = HorizonCode_Horizon_È(p_77913_0_, var8, var6, var5, var7);
                    var7 = false;
                    var5 = false;
                    var6 = false;
                    var4 = false;
                    var8 = 0;
                }
                var7 = true;
            }
        }
        if (var4) {
            p_77913_0_ = HorizonCode_Horizon_È(p_77913_0_, var8, var6, var5, var7);
        }
        return p_77913_0_ & 0x7FFF;
    }
    
    public static int HorizonCode_Horizon_È(final int p_77908_0_, final int p_77908_1_, final int p_77908_2_, final int p_77908_3_, final int p_77908_4_, final int p_77908_5_) {
        return (HorizonCode_Horizon_È(p_77908_0_, p_77908_1_) ? 16 : 0) | (HorizonCode_Horizon_È(p_77908_0_, p_77908_2_) ? 8 : 0) | (HorizonCode_Horizon_È(p_77908_0_, p_77908_3_) ? 4 : 0) | (HorizonCode_Horizon_È(p_77908_0_, p_77908_4_) ? 2 : 0) | (HorizonCode_Horizon_È(p_77908_0_, p_77908_5_) ? 1 : 0);
    }
}
