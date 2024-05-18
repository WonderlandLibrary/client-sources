package net.minecraft.enchantment;

import net.minecraft.util.*;

public class EnchantmentLootBonus extends Enchantment
{
    private static final String[] I;
    
    @Override
    public int getMinEnchantability(final int n) {
        return (0x7E ^ 0x71) + (n - " ".length()) * (0x1D ^ 0x14);
    }
    
    @Override
    public boolean canApplyTogether(final Enchantment enchantment) {
        if (super.canApplyTogether(enchantment) && enchantment.effectId != EnchantmentLootBonus.silkTouch.effectId) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    @Override
    public int getMaxLevel() {
        return "   ".length();
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
    
    protected EnchantmentLootBonus(final int n, final ResourceLocation resourceLocation, final int n2, final EnumEnchantmentType enumEnchantmentType) {
        super(n, resourceLocation, n2, enumEnchantmentType);
        if (enumEnchantmentType == EnumEnchantmentType.DIGGER) {
            this.setName(EnchantmentLootBonus.I["".length()]);
            "".length();
            if (0 < -1) {
                throw null;
            }
        }
        else if (enumEnchantmentType == EnumEnchantmentType.FISHING_ROD) {
            this.setName(EnchantmentLootBonus.I[" ".length()]);
            "".length();
            if (1 >= 2) {
                throw null;
            }
        }
        else {
            this.setName(EnchantmentLootBonus.I["  ".length()]);
        }
    }
    
    @Override
    public int getMaxEnchantability(final int n) {
        return super.getMinEnchantability(n) + (0xA2 ^ 0x90);
    }
    
    private static void I() {
        (I = new String["   ".length()])["".length()] = I("\u0019.)\u0004 \u001a/3\u0003&\u001c&!\u0015\u0010", "uAFpb");
        EnchantmentLootBonus.I[" ".length()] = I("\u0014\u001f\u001d5\t\u0017\u001e\u00072\r\u0011\u0003\u001a(%\u001f", "xprAK");
        EnchantmentLootBonus.I["  ".length()] = I(".\u001c#\u0019\u000e-\u001d9\u001e", "BsLmL");
    }
}
