package net.minecraft.enchantment;

import net.minecraft.util.*;

public class EnchantmentKnockback extends Enchantment
{
    private static final String[] I;
    
    @Override
    public int getMaxEnchantability(final int n) {
        return super.getMinEnchantability(n) + (0xF ^ 0x3D);
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("9%\u0005\u0000'0*\t\b", "RKjcL");
    }
    
    @Override
    public int getMinEnchantability(final int n) {
        return (0xBA ^ 0xBF) + (0x60 ^ 0x74) * (n - " ".length());
    }
    
    protected EnchantmentKnockback(final int n, final ResourceLocation resourceLocation, final int n2) {
        super(n, resourceLocation, n2, EnumEnchantmentType.WEAPON);
        this.setName(EnchantmentKnockback.I["".length()]);
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
            if (4 <= 0) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    static {
        I();
    }
    
    @Override
    public int getMaxLevel() {
        return "  ".length();
    }
}
