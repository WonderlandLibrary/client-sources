package net.minecraft.enchantment;

import net.minecraft.util.*;

public class EnchantmentWaterWalker extends Enchantment
{
    private static final String[] I;
    
    @Override
    public int getMaxLevel() {
        return "   ".length();
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("/99.\u0001\u000f9! \u0016*", "XXMKs");
    }
    
    @Override
    public int getMinEnchantability(final int n) {
        return n * (0xA8 ^ 0xA2);
    }
    
    public EnchantmentWaterWalker(final int n, final ResourceLocation resourceLocation, final int n2) {
        super(n, resourceLocation, n2, EnumEnchantmentType.ARMOR_FEET);
        this.setName(EnchantmentWaterWalker.I["".length()]);
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
            if (3 == 2) {
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
        return this.getMinEnchantability(n) + (0x7F ^ 0x70);
    }
}
