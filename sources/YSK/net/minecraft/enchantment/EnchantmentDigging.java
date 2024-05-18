package net.minecraft.enchantment;

import net.minecraft.util.*;
import net.minecraft.item.*;
import net.minecraft.init.*;

public class EnchantmentDigging extends Enchantment
{
    private static final String[] I;
    
    protected EnchantmentDigging(final int n, final ResourceLocation resourceLocation, final int n2) {
        super(n, resourceLocation, n2, EnumEnchantmentType.DIGGER);
        this.setName(EnchantmentDigging.I["".length()]);
    }
    
    @Override
    public boolean canApply(final ItemStack itemStack) {
        int n;
        if (itemStack.getItem() == Items.shears) {
            n = " ".length();
            "".length();
            if (2 != 2) {
                throw null;
            }
        }
        else {
            n = (super.canApply(itemStack) ? 1 : 0);
        }
        return n != 0;
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
            if (2 != 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public int getMaxEnchantability(final int n) {
        return super.getMinEnchantability(n) + (0x9E ^ 0xAC);
    }
    
    static {
        I();
    }
    
    @Override
    public int getMinEnchantability(final int n) {
        return " ".length() + (0x7F ^ 0x75) * (n - " ".length());
    }
    
    @Override
    public int getMaxLevel() {
        return 0x49 ^ 0x4C;
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("\f\u001a\u0002,\u0019\u0006\u0014", "hseKp");
    }
}
