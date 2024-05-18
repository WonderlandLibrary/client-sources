package net.minecraft.enchantment;

import net.minecraft.util.*;

public class EnchantmentOxygen extends Enchantment
{
    private static final String[] I;
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("\u0018\u000e\r\u001f5\u0019", "wvtxP");
    }
    
    public EnchantmentOxygen(final int n, final ResourceLocation resourceLocation, final int n2) {
        super(n, resourceLocation, n2, EnumEnchantmentType.ARMOR_HEAD);
        this.setName(EnchantmentOxygen.I["".length()]);
    }
    
    @Override
    public int getMaxEnchantability(final int n) {
        return this.getMinEnchantability(n) + (0x88 ^ 0x96);
    }
    
    @Override
    public int getMaxLevel() {
        return "   ".length();
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
            if (4 == 0) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public int getMinEnchantability(final int n) {
        return (0x38 ^ 0x32) * n;
    }
}
