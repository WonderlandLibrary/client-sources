package net.minecraft.enchantment;

import net.minecraft.util.*;
import net.minecraft.item.*;
import net.minecraft.init.*;

public class EnchantmentUntouching extends Enchantment
{
    private static final String[] I;
    
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
            if (3 <= -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("'\u0001\u0002)'1\u0007\u001f(5", "RovFR");
    }
    
    protected EnchantmentUntouching(final int n, final ResourceLocation resourceLocation, final int n2) {
        super(n, resourceLocation, n2, EnumEnchantmentType.DIGGER);
        this.setName(EnchantmentUntouching.I["".length()]);
    }
    
    @Override
    public int getMaxEnchantability(final int n) {
        return super.getMinEnchantability(n) + (0x27 ^ 0x15);
    }
    
    static {
        I();
    }
    
    @Override
    public boolean canApplyTogether(final Enchantment enchantment) {
        if (super.canApplyTogether(enchantment) && enchantment.effectId != EnchantmentUntouching.fortune.effectId) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    @Override
    public boolean canApply(final ItemStack itemStack) {
        int n;
        if (itemStack.getItem() == Items.shears) {
            n = " ".length();
            "".length();
            if (0 <= -1) {
                throw null;
            }
        }
        else {
            n = (super.canApply(itemStack) ? 1 : 0);
        }
        return n != 0;
    }
    
    @Override
    public int getMaxLevel() {
        return " ".length();
    }
    
    @Override
    public int getMinEnchantability(final int n) {
        return 0xB4 ^ 0xBB;
    }
}
