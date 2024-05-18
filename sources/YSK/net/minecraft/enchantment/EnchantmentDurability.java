package net.minecraft.enchantment;

import java.util.*;
import net.minecraft.item.*;
import net.minecraft.util.*;

public class EnchantmentDurability extends Enchantment
{
    private static final String[] I;
    
    static {
        I();
    }
    
    @Override
    public boolean canApply(final ItemStack itemStack) {
        int n;
        if (itemStack.isItemStackDamageable()) {
            n = " ".length();
            "".length();
            if (3 < 0) {
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
            if (true != true) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public int getMaxEnchantability(final int n) {
        return super.getMinEnchantability(n) + (0x72 ^ 0x40);
    }
    
    public static boolean negateDamage(final ItemStack itemStack, final int n, final Random random) {
        int n2;
        if (itemStack.getItem() instanceof ItemArmor && random.nextFloat() < 0.6f) {
            n2 = "".length();
            "".length();
            if (-1 == 2) {
                throw null;
            }
        }
        else if (random.nextInt(n + " ".length()) > 0) {
            n2 = " ".length();
            "".length();
            if (4 <= -1) {
                throw null;
            }
        }
        else {
            n2 = "".length();
        }
        return n2 != 0;
    }
    
    @Override
    public int getMaxLevel() {
        return "   ".length();
    }
    
    protected EnchantmentDurability(final int n, final ResourceLocation resourceLocation, final int n2) {
        super(n, resourceLocation, n2, EnumEnchantmentType.BREAKABLE);
        this.setName(EnchantmentDurability.I["".length()]);
    }
    
    @Override
    public int getMinEnchantability(final int n) {
        return (0x3C ^ 0x39) + (n - " ".length()) * (0x31 ^ 0x39);
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("\u0006\u001b\u000b\u001b;\u000b\u0002\u0010\u000e ", "bnyzY");
    }
}
