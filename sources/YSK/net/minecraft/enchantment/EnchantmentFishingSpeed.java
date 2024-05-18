package net.minecraft.enchantment;

import net.minecraft.util.*;

public class EnchantmentFishingSpeed extends Enchantment
{
    private static final String[] I;
    
    static {
        I();
    }
    
    protected EnchantmentFishingSpeed(final int n, final ResourceLocation resourceLocation, final int n2, final EnumEnchantmentType enumEnchantmentType) {
        super(n, resourceLocation, n2, enumEnchantmentType);
        this.setName(EnchantmentFishingSpeed.I["".length()]);
    }
    
    @Override
    public int getMaxEnchantability(final int n) {
        return super.getMinEnchantability(n) + (0x74 ^ 0x46);
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("\t8+>/\u00016\u000b&#\n5", "oQXVF");
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
            if (-1 == 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public int getMaxLevel() {
        return "   ".length();
    }
    
    @Override
    public int getMinEnchantability(final int n) {
        return (0x87 ^ 0x88) + (n - " ".length()) * (0x5D ^ 0x54);
    }
}
