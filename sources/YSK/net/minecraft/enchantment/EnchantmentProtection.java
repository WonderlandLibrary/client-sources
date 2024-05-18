package net.minecraft.enchantment;

import net.minecraft.entity.*;
import net.minecraft.util.*;

public class EnchantmentProtection extends Enchantment
{
    private static final int[] baseEnchantability;
    private static final String[] protectionName;
    public final int protectionType;
    private static final int[] levelEnchantability;
    private static final String[] I;
    private static final int[] thresholdEnchantability;
    
    static {
        I();
        final String[] protectionName2 = new String[0x7 ^ 0x2];
        protectionName2["".length()] = EnchantmentProtection.I["".length()];
        protectionName2[" ".length()] = EnchantmentProtection.I[" ".length()];
        protectionName2["  ".length()] = EnchantmentProtection.I["  ".length()];
        protectionName2["   ".length()] = EnchantmentProtection.I["   ".length()];
        protectionName2[0x1D ^ 0x19] = EnchantmentProtection.I[0xAC ^ 0xA8];
        protectionName = protectionName2;
        final int[] baseEnchantability2 = new int[0x24 ^ 0x21];
        baseEnchantability2["".length()] = " ".length();
        baseEnchantability2[" ".length()] = (0x2 ^ 0x8);
        baseEnchantability2["  ".length()] = (0x4E ^ 0x4B);
        baseEnchantability2["   ".length()] = (0x59 ^ 0x5C);
        baseEnchantability2[0xE ^ 0xA] = "   ".length();
        baseEnchantability = baseEnchantability2;
        final int[] levelEnchantability2 = new int[0x9A ^ 0x9F];
        levelEnchantability2["".length()] = (0x50 ^ 0x5B);
        levelEnchantability2[" ".length()] = (0x14 ^ 0x1C);
        levelEnchantability2["  ".length()] = (0x9A ^ 0x9C);
        levelEnchantability2["   ".length()] = (0x4B ^ 0x43);
        levelEnchantability2[0xA5 ^ 0xA1] = (0xB6 ^ 0xB0);
        levelEnchantability = levelEnchantability2;
        final int[] thresholdEnchantability2 = new int[0x45 ^ 0x40];
        thresholdEnchantability2["".length()] = (0x8C ^ 0x98);
        thresholdEnchantability2[" ".length()] = (0x93 ^ 0x9F);
        thresholdEnchantability2["  ".length()] = (0x1A ^ 0x10);
        thresholdEnchantability2["   ".length()] = (0x7 ^ 0xB);
        thresholdEnchantability2[0x9 ^ 0xD] = (0x9A ^ 0x95);
        thresholdEnchantability = thresholdEnchantability2;
    }
    
    @Override
    public int getMinEnchantability(final int n) {
        return EnchantmentProtection.baseEnchantability[this.protectionType] + (n - " ".length()) * EnchantmentProtection.levelEnchantability[this.protectionType];
    }
    
    public EnchantmentProtection(final int n, final ResourceLocation resourceLocation, final int n2, final int protectionType) {
        super(n, resourceLocation, n2, EnumEnchantmentType.ARMOR);
        this.protectionType = protectionType;
        if (protectionType == "  ".length()) {
            this.type = EnumEnchantmentType.ARMOR_FEET;
        }
    }
    
    public static double func_92092_a(final Entity entity, double n) {
        final int maxEnchantmentLevel = EnchantmentHelper.getMaxEnchantmentLevel(Enchantment.blastProtection.effectId, entity.getInventory());
        if (maxEnchantmentLevel > 0) {
            n -= MathHelper.floor_double(n * (maxEnchantmentLevel * 0.15f));
        }
        return n;
    }
    
    @Override
    public boolean canApplyTogether(final Enchantment enchantment) {
        if (enchantment instanceof EnchantmentProtection) {
            final EnchantmentProtection enchantmentProtection = (EnchantmentProtection)enchantment;
            int n;
            if (enchantmentProtection.protectionType == this.protectionType) {
                n = "".length();
                "".length();
                if (4 < 4) {
                    throw null;
                }
            }
            else if (this.protectionType != "  ".length() && enchantmentProtection.protectionType != "  ".length()) {
                n = "".length();
                "".length();
                if (1 >= 4) {
                    throw null;
                }
            }
            else {
                n = " ".length();
            }
            return n != 0;
        }
        return super.canApplyTogether(enchantment);
    }
    
    private static void I() {
        (I = new String[0x99 ^ 0x9F])["".length()] = I("\u0003'(", "bKDAh");
        EnchantmentProtection.I[" ".length()] = I("\t\u00131\u001f", "ozCzy");
        EnchantmentProtection.I["  ".length()] = I("1-=%", "WLQIu");
        EnchantmentProtection.I["   ".length()] = I("\u000b,\u001c) \u001d=\u0003+", "nTlEO");
        EnchantmentProtection.I[0x85 ^ 0x81] = I("!\u0017\u0002=/2\u0011\u0004;/", "QemWJ");
        EnchantmentProtection.I[0x24 ^ 0x21] = I("<\"\u0007 \u001b78\t-\u0014-b\u0014:\u0015-)\u0007<T", "YLdHz");
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (3 <= 1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public int getMaxEnchantability(final int n) {
        return this.getMinEnchantability(n) + EnchantmentProtection.thresholdEnchantability[this.protectionType];
    }
    
    public static int getFireTimeForEntity(final Entity entity, int n) {
        final int maxEnchantmentLevel = EnchantmentHelper.getMaxEnchantmentLevel(Enchantment.fireProtection.effectId, entity.getInventory());
        if (maxEnchantmentLevel > 0) {
            n -= MathHelper.floor_float(n * maxEnchantmentLevel * 0.15f);
        }
        return n;
    }
    
    @Override
    public int getMaxLevel() {
        return 0x79 ^ 0x7D;
    }
    
    @Override
    public String getName() {
        return EnchantmentProtection.I[0x7F ^ 0x7A] + EnchantmentProtection.protectionName[this.protectionType];
    }
    
    @Override
    public int calcModifierDamage(final int n, final DamageSource damageSource) {
        if (damageSource.canHarmInCreative()) {
            return "".length();
        }
        final float n2 = ((0x86 ^ 0x80) + n * n) / 3.0f;
        int n3;
        if (this.protectionType == 0) {
            n3 = MathHelper.floor_float(n2 * 0.75f);
            "".length();
            if (3 < -1) {
                throw null;
            }
        }
        else if (this.protectionType == " ".length() && damageSource.isFireDamage()) {
            n3 = MathHelper.floor_float(n2 * 1.25f);
            "".length();
            if (-1 == 2) {
                throw null;
            }
        }
        else if (this.protectionType == "  ".length() && damageSource == DamageSource.fall) {
            n3 = MathHelper.floor_float(n2 * 2.5f);
            "".length();
            if (4 != 4) {
                throw null;
            }
        }
        else if (this.protectionType == "   ".length() && damageSource.isExplosion()) {
            n3 = MathHelper.floor_float(n2 * 1.5f);
            "".length();
            if (3 < -1) {
                throw null;
            }
        }
        else if (this.protectionType == (0xAF ^ 0xAB) && damageSource.isProjectile()) {
            n3 = MathHelper.floor_float(n2 * 1.5f);
            "".length();
            if (3 != 3) {
                throw null;
            }
        }
        else {
            n3 = "".length();
        }
        return n3;
    }
}
