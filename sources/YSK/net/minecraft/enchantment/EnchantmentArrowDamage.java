package net.minecraft.enchantment;

import net.minecraft.util.*;

public class EnchantmentArrowDamage extends Enchantment
{
    private static final String[] I;
    
    public EnchantmentArrowDamage(final int n, final ResourceLocation resourceLocation, final int n2) {
        super(n, resourceLocation, n2, EnumEnchantmentType.BOW);
        this.setName(EnchantmentArrowDamage.I["".length()]);
    }
    
    @Override
    public int getMaxLevel() {
        return 0x1C ^ 0x19;
    }
    
    static {
        I();
    }
    
    @Override
    public int getMaxEnchantability(final int n) {
        return this.getMinEnchantability(n) + (0x77 ^ 0x78);
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("-6\u0019\"\u0013\b%\u0006,\u0003)", "LDkMd");
    }
    
    @Override
    public int getMinEnchantability(final int n) {
        return " ".length() + (n - " ".length()) * (0x85 ^ 0x8F);
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
}
