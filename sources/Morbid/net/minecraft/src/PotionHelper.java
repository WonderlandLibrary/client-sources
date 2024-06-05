package net.minecraft.src;

import java.util.*;

public class PotionHelper
{
    public static final String field_77924_a;
    public static final String sugarEffect;
    public static final String ghastTearEffect = "+0-1-2-3&4-4+13";
    public static final String spiderEyeEffect;
    public static final String fermentedSpiderEyeEffect;
    public static final String speckledMelonEffect;
    public static final String blazePowderEffect;
    public static final String magmaCreamEffect;
    public static final String redstoneEffect;
    public static final String glowstoneEffect;
    public static final String gunpowderEffect;
    public static final String goldenCarrotEffect;
    private static final HashMap potionRequirements;
    private static final HashMap potionAmplifiers;
    private static final HashMap field_77925_n;
    private static final String[] potionPrefixes;
    
    static {
        field_77924_a = null;
        potionRequirements = new HashMap();
        potionAmplifiers = new HashMap();
        PotionHelper.potionRequirements.put(Potion.regeneration.getId(), "0 & !1 & !2 & !3 & 0+6");
        sugarEffect = "-0+1-2-3&4-4+13";
        PotionHelper.potionRequirements.put(Potion.moveSpeed.getId(), "!0 & 1 & !2 & !3 & 1+6");
        magmaCreamEffect = "+0+1-2-3&4-4+13";
        PotionHelper.potionRequirements.put(Potion.fireResistance.getId(), "0 & 1 & !2 & !3 & 0+6");
        speckledMelonEffect = "+0-1+2-3&4-4+13";
        PotionHelper.potionRequirements.put(Potion.heal.getId(), "0 & !1 & 2 & !3");
        spiderEyeEffect = "-0-1+2-3&4-4+13";
        PotionHelper.potionRequirements.put(Potion.poison.getId(), "!0 & !1 & 2 & !3 & 2+6");
        fermentedSpiderEyeEffect = "-0+3-4+13";
        PotionHelper.potionRequirements.put(Potion.weakness.getId(), "!0 & !1 & !2 & 3 & 3+6");
        PotionHelper.potionRequirements.put(Potion.harm.getId(), "!0 & !1 & 2 & 3");
        PotionHelper.potionRequirements.put(Potion.moveSlowdown.getId(), "!0 & 1 & !2 & 3 & 3+6");
        blazePowderEffect = "+0-1-2+3&4-4+13";
        PotionHelper.potionRequirements.put(Potion.damageBoost.getId(), "0 & !1 & !2 & 3 & 3+6");
        goldenCarrotEffect = "-0+1+2-3+13&4-4";
        PotionHelper.potionRequirements.put(Potion.nightVision.getId(), "!0 & 1 & 2 & !3 & 2+6");
        PotionHelper.potionRequirements.put(Potion.invisibility.getId(), "!0 & 1 & 2 & 3 & 2+6");
        glowstoneEffect = "+5-6-7";
        PotionHelper.potionAmplifiers.put(Potion.moveSpeed.getId(), "5");
        PotionHelper.potionAmplifiers.put(Potion.digSpeed.getId(), "5");
        PotionHelper.potionAmplifiers.put(Potion.damageBoost.getId(), "5");
        PotionHelper.potionAmplifiers.put(Potion.regeneration.getId(), "5");
        PotionHelper.potionAmplifiers.put(Potion.harm.getId(), "5");
        PotionHelper.potionAmplifiers.put(Potion.heal.getId(), "5");
        PotionHelper.potionAmplifiers.put(Potion.resistance.getId(), "5");
        PotionHelper.potionAmplifiers.put(Potion.poison.getId(), "5");
        redstoneEffect = "-5+6-7";
        gunpowderEffect = "+14&13-13";
        field_77925_n = new HashMap();
        potionPrefixes = new String[] { "potion.prefix.mundane", "potion.prefix.uninteresting", "potion.prefix.bland", "potion.prefix.clear", "potion.prefix.milky", "potion.prefix.diffuse", "potion.prefix.artless", "potion.prefix.thin", "potion.prefix.awkward", "potion.prefix.flat", "potion.prefix.bulky", "potion.prefix.bungling", "potion.prefix.buttered", "potion.prefix.smooth", "potion.prefix.suave", "potion.prefix.debonair", "potion.prefix.thick", "potion.prefix.elegant", "potion.prefix.fancy", "potion.prefix.charming", "potion.prefix.dashing", "potion.prefix.refined", "potion.prefix.cordial", "potion.prefix.sparkling", "potion.prefix.potent", "potion.prefix.foul", "potion.prefix.odorless", "potion.prefix.rank", "potion.prefix.harsh", "potion.prefix.acrid", "potion.prefix.gross", "potion.prefix.stinky" };
    }
    
    public static boolean checkFlag(final int par0, final int par1) {
        return (par0 & 1 << par1) != 0x0;
    }
    
    private static int isFlagSet(final int par0, final int par1) {
        return checkFlag(par0, par1) ? 1 : 0;
    }
    
    private static int isFlagUnset(final int par0, final int par1) {
        return checkFlag(par0, par1) ? 0 : 1;
    }
    
    public static int func_77909_a(final int par0) {
        return func_77908_a(par0, 5, 4, 3, 2, 1);
    }
    
    public static int calcPotionLiquidColor(final Collection par0Collection) {
        final int var1 = 3694022;
        if (par0Collection != null && !par0Collection.isEmpty()) {
            float var2 = 0.0f;
            float var3 = 0.0f;
            float var4 = 0.0f;
            float var5 = 0.0f;
            for (final PotionEffect var7 : par0Collection) {
                final int var8 = Potion.potionTypes[var7.getPotionID()].getLiquidColor();
                for (int var9 = 0; var9 <= var7.getAmplifier(); ++var9) {
                    var2 += (var8 >> 16 & 0xFF) / 255.0f;
                    var3 += (var8 >> 8 & 0xFF) / 255.0f;
                    var4 += (var8 >> 0 & 0xFF) / 255.0f;
                    ++var5;
                }
            }
            var2 = var2 / var5 * 255.0f;
            var3 = var3 / var5 * 255.0f;
            var4 = var4 / var5 * 255.0f;
            return (int)var2 << 16 | (int)var3 << 8 | (int)var4;
        }
        return var1;
    }
    
    public static boolean func_82817_b(final Collection par0Collection) {
        for (final PotionEffect var2 : par0Collection) {
            if (!var2.getIsAmbient()) {
                return false;
            }
        }
        return true;
    }
    
    public static int func_77915_a(final int par0, final boolean par1) {
        if (par1) {
            return calcPotionLiquidColor(getPotionEffects(par0, par1));
        }
        if (PotionHelper.field_77925_n.containsKey(par0)) {
            return PotionHelper.field_77925_n.get(par0);
        }
        final int var2 = calcPotionLiquidColor(getPotionEffects(par0, false));
        PotionHelper.field_77925_n.put(par0, var2);
        return var2;
    }
    
    public static String func_77905_c(final int par0) {
        final int var1 = func_77909_a(par0);
        return PotionHelper.potionPrefixes[var1];
    }
    
    private static int func_77904_a(final boolean par0, final boolean par1, final boolean par2, final int par3, final int par4, final int par5, final int par6) {
        int var7 = 0;
        if (par0) {
            var7 = isFlagUnset(par6, par4);
        }
        else if (par3 != -1) {
            if (par3 == 0 && countSetFlags(par6) == par4) {
                var7 = 1;
            }
            else if (par3 == 1 && countSetFlags(par6) > par4) {
                var7 = 1;
            }
            else if (par3 == 2 && countSetFlags(par6) < par4) {
                var7 = 1;
            }
        }
        else {
            var7 = isFlagSet(par6, par4);
        }
        if (par1) {
            var7 *= par5;
        }
        if (par2) {
            var7 *= -1;
        }
        return var7;
    }
    
    private static int countSetFlags(int par0) {
        int var1;
        for (var1 = 0; par0 > 0; par0 &= par0 - 1, ++var1) {}
        return var1;
    }
    
    private static int parsePotionEffects(final String par0Str, final int par1, final int par2, final int par3) {
        if (par1 >= par0Str.length() || par2 < 0 || par1 >= par2) {
            return 0;
        }
        final int var4 = par0Str.indexOf(124, par1);
        if (var4 >= 0 && var4 < par2) {
            final int var5 = parsePotionEffects(par0Str, par1, var4 - 1, par3);
            if (var5 > 0) {
                return var5;
            }
            final int var6 = parsePotionEffects(par0Str, var4 + 1, par2, par3);
            return (var6 > 0) ? var6 : 0;
        }
        else {
            final int var5 = par0Str.indexOf(38, par1);
            if (var5 < 0 || var5 >= par2) {
                boolean var7 = false;
                boolean var8 = false;
                boolean var9 = false;
                boolean var10 = false;
                boolean var11 = false;
                byte var12 = -1;
                int var13 = 0;
                int var14 = 0;
                int var15 = 0;
                for (int var16 = par1; var16 < par2; ++var16) {
                    final char var17 = par0Str.charAt(var16);
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
                            var15 += func_77904_a(var10, var8, var11, var12, var13, var14, par3);
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
                            var15 += func_77904_a(var10, var8, var11, var12, var13, var14, par3);
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
                            var15 += func_77904_a(var10, var8, var11, var12, var13, var14, par3);
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
                            var15 += func_77904_a(var10, var8, var11, var12, var13, var14, par3);
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
                    var15 += func_77904_a(var10, var8, var11, var12, var13, var14, par3);
                }
                return var15;
            }
            final int var6 = parsePotionEffects(par0Str, par1, var5 - 1, par3);
            if (var6 <= 0) {
                return 0;
            }
            final int var18 = parsePotionEffects(par0Str, var5 + 1, par2, par3);
            return (var18 <= 0) ? 0 : ((var6 > var18) ? var6 : var18);
        }
    }
    
    public static List getPotionEffects(final int par0, final boolean par1) {
        ArrayList var2 = null;
        for (final Potion var6 : Potion.potionTypes) {
            if (var6 != null && (!var6.isUsable() || par1)) {
                final String var7 = PotionHelper.potionRequirements.get(var6.getId());
                if (var7 != null) {
                    int var8 = parsePotionEffects(var7, 0, var7.length(), par0);
                    if (var8 > 0) {
                        int var9 = 0;
                        final String var10 = PotionHelper.potionAmplifiers.get(var6.getId());
                        if (var10 != null) {
                            var9 = parsePotionEffects(var10, 0, var10.length(), par0);
                            if (var9 < 0) {
                                var9 = 0;
                            }
                        }
                        if (var6.isInstant()) {
                            var8 = 1;
                        }
                        else {
                            var8 = 1200 * (var8 * 3 + (var8 - 1) * 2);
                            var8 >>= var9;
                            var8 = (int)Math.round(var8 * var6.getEffectiveness());
                            if ((par0 & 0x4000) != 0x0) {
                                var8 = (int)Math.round(var8 * 0.75 + 0.5);
                            }
                        }
                        if (var2 == null) {
                            var2 = new ArrayList();
                        }
                        final PotionEffect var11 = new PotionEffect(var6.getId(), var8, var9);
                        if ((par0 & 0x4000) != 0x0) {
                            var11.setSplashPotion(true);
                        }
                        var2.add(var11);
                    }
                }
            }
        }
        return var2;
    }
    
    private static int brewBitOperations(int par0, final int par1, final boolean par2, final boolean par3, final boolean par4) {
        if (par4) {
            if (!checkFlag(par0, par1)) {
                return 0;
            }
        }
        else if (par2) {
            par0 &= ~(1 << par1);
        }
        else if (par3) {
            if ((par0 & 1 << par1) == 0x0) {
                par0 |= 1 << par1;
            }
            else {
                par0 &= ~(1 << par1);
            }
        }
        else {
            par0 |= 1 << par1;
        }
        return par0;
    }
    
    public static int applyIngredient(int par0, final String par1Str) {
        final byte var2 = 0;
        final int var3 = par1Str.length();
        boolean var4 = false;
        boolean var5 = false;
        boolean var6 = false;
        boolean var7 = false;
        int var8 = 0;
        for (int var9 = var2; var9 < var3; ++var9) {
            final char var10 = par1Str.charAt(var9);
            if (var10 >= '0' && var10 <= '9') {
                var8 *= 10;
                var8 += var10 - '0';
                var4 = true;
            }
            else if (var10 == '!') {
                if (var4) {
                    par0 = brewBitOperations(par0, var8, var6, var5, var7);
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
                    par0 = brewBitOperations(par0, var8, var6, var5, var7);
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
                    par0 = brewBitOperations(par0, var8, var6, var5, var7);
                    var7 = false;
                    var5 = false;
                    var6 = false;
                    var4 = false;
                    var8 = 0;
                }
            }
            else if (var10 == '&') {
                if (var4) {
                    par0 = brewBitOperations(par0, var8, var6, var5, var7);
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
            par0 = brewBitOperations(par0, var8, var6, var5, var7);
        }
        return par0 & 0x7FFF;
    }
    
    public static int func_77908_a(final int par0, final int par1, final int par2, final int par3, final int par4, final int par5) {
        return (checkFlag(par0, par1) ? 16 : 0) | (checkFlag(par0, par2) ? 8 : 0) | (checkFlag(par0, par3) ? 4 : 0) | (checkFlag(par0, par4) ? 2 : 0) | (checkFlag(par0, par5) ? 1 : 0);
    }
}
