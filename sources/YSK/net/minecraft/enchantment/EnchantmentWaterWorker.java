package net.minecraft.enchantment;

import net.minecraft.util.*;

public class EnchantmentWaterWorker extends Enchantment
{
    private static final String[] I;
    
    @Override
    public int getMinEnchantability(final int n) {
        return " ".length();
    }
    
    @Override
    public int getMaxLevel() {
        return " ".length();
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
            if (4 < 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I(" 6\u0016-8\u00008\u0010#/%", "WWbHJ");
    }
    
    public EnchantmentWaterWorker(final int n, final ResourceLocation resourceLocation, final int n2) {
        super(n, resourceLocation, n2, EnumEnchantmentType.ARMOR_HEAD);
        this.setName(EnchantmentWaterWorker.I["".length()]);
    }
    
    static {
        I();
    }
    
    @Override
    public int getMaxEnchantability(final int n) {
        return this.getMinEnchantability(n) + (0xA9 ^ 0x81);
    }
}
