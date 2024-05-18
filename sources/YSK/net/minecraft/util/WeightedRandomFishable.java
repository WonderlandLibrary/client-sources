package net.minecraft.util;

import net.minecraft.item.*;
import java.util.*;
import net.minecraft.enchantment.*;

public class WeightedRandomFishable extends WeightedRandom.Item
{
    private final ItemStack returnStack;
    private boolean enchantable;
    private float maxDamagePercent;
    
    public WeightedRandomFishable(final ItemStack returnStack, final int n) {
        super(n);
        this.returnStack = returnStack;
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
            if (false) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public WeightedRandomFishable setMaxDamagePercent(final float maxDamagePercent) {
        this.maxDamagePercent = maxDamagePercent;
        return this;
    }
    
    public WeightedRandomFishable setEnchantable() {
        this.enchantable = (" ".length() != 0);
        return this;
    }
    
    public ItemStack getItemStack(final Random random) {
        final ItemStack copy = this.returnStack.copy();
        if (this.maxDamagePercent > 0.0f) {
            final int n = (int)(this.maxDamagePercent * this.returnStack.getMaxDamage());
            int length = copy.getMaxDamage() - random.nextInt(random.nextInt(n) + " ".length());
            if (length > n) {
                length = n;
            }
            if (length < " ".length()) {
                length = " ".length();
            }
            copy.setItemDamage(length);
        }
        if (this.enchantable) {
            EnchantmentHelper.addRandomEnchantment(random, copy, 0xA1 ^ 0xBF);
        }
        return copy;
    }
}
