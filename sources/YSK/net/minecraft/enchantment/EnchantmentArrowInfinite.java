package net.minecraft.enchantment;

import net.minecraft.util.*;

public class EnchantmentArrowInfinite extends Enchantment
{
    private static final String[] I;
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("\u000b\u0000\u0001=\u0018#\u001c\u0015;\u0001\u0003\u0006\u0016", "jrsRo");
    }
    
    @Override
    public int getMinEnchantability(final int n) {
        return 0x79 ^ 0x6D;
    }
    
    public EnchantmentArrowInfinite(final int n, final ResourceLocation resourceLocation, final int n2) {
        super(n, resourceLocation, n2, EnumEnchantmentType.BOW);
        this.setName(EnchantmentArrowInfinite.I["".length()]);
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
            if (0 >= 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    static {
        I();
    }
    
    @Override
    public int getMaxEnchantability(final int n) {
        return 0x5C ^ 0x6E;
    }
    
    @Override
    public int getMaxLevel() {
        return " ".length();
    }
}
