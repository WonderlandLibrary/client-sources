package net.minecraft.enchantment;

import net.minecraft.util.*;

public class EnchantmentArrowKnockback extends Enchantment
{
    private static final String[] I;
    
    static {
        I();
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
            if (4 != 4) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public int getMaxLevel() {
        return "  ".length();
    }
    
    @Override
    public int getMinEnchantability(final int n) {
        return (0x18 ^ 0x14) + (n - " ".length()) * (0x49 ^ 0x5D);
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("\u0014\u0002\u0018\u0015\u001f>\u001e\u0005\u0019\u0003\u0017\u0011\t\u0011", "upjzh");
    }
    
    public EnchantmentArrowKnockback(final int n, final ResourceLocation resourceLocation, final int n2) {
        super(n, resourceLocation, n2, EnumEnchantmentType.BOW);
        this.setName(EnchantmentArrowKnockback.I["".length()]);
    }
    
    @Override
    public int getMaxEnchantability(final int n) {
        return this.getMinEnchantability(n) + (0x7E ^ 0x67);
    }
}
