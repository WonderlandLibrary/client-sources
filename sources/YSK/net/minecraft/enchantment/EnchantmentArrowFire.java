package net.minecraft.enchantment;

import net.minecraft.util.*;

public class EnchantmentArrowFire extends Enchantment
{
    private static final String[] I;
    
    @Override
    public int getMaxLevel() {
        return " ".length();
    }
    
    @Override
    public int getMaxEnchantability(final int n) {
        return 0x6A ^ 0x58;
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("\u0013\u001e=\u0004\u00014\u0005=\u000e", "rlOkv");
    }
    
    @Override
    public int getMinEnchantability(final int n) {
        return 0x90 ^ 0x84;
    }
    
    public EnchantmentArrowFire(final int n, final ResourceLocation resourceLocation, final int n2) {
        super(n, resourceLocation, n2, EnumEnchantmentType.BOW);
        this.setName(EnchantmentArrowFire.I["".length()]);
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
            if (1 < -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    static {
        I();
    }
}
