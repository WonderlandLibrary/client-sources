package net.minecraft.enchantment;

import net.minecraft.util.*;

public class EnchantmentFireAspect extends Enchantment
{
    private static final String[] I;
    
    protected EnchantmentFireAspect(final int n, final ResourceLocation resourceLocation, final int n2) {
        super(n, resourceLocation, n2, EnumEnchantmentType.WEAPON);
        this.setName(EnchantmentFireAspect.I["".length()]);
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("%\u001a\u0018'", "CsjBj");
    }
    
    @Override
    public int getMaxEnchantability(final int n) {
        return super.getMinEnchantability(n) + (0x3A ^ 0x8);
    }
    
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
            if (0 == 3) {
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
        return (0xBA ^ 0xB0) + (0x0 ^ 0x14) * (n - " ".length());
    }
}
