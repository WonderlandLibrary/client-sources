package net.minecraft.src;

import java.util.*;

public class WeightedRandom
{
    public static int getTotalWeight(final Collection par0Collection) {
        int var1 = 0;
        for (final WeightedRandomItem var3 : par0Collection) {
            var1 += var3.itemWeight;
        }
        return var1;
    }
    
    public static WeightedRandomItem getRandomItem(final Random par0Random, final Collection par1Collection, final int par2) {
        if (par2 <= 0) {
            throw new IllegalArgumentException();
        }
        int var3 = par0Random.nextInt(par2);
        for (final WeightedRandomItem var5 : par1Collection) {
            var3 -= var5.itemWeight;
            if (var3 < 0) {
                return var5;
            }
        }
        return null;
    }
    
    public static WeightedRandomItem getRandomItem(final Random par0Random, final Collection par1Collection) {
        return getRandomItem(par0Random, par1Collection, getTotalWeight(par1Collection));
    }
    
    public static int getTotalWeight(final WeightedRandomItem[] par0ArrayOfWeightedRandomItem) {
        int var1 = 0;
        for (final WeightedRandomItem var4 : par0ArrayOfWeightedRandomItem) {
            var1 += var4.itemWeight;
        }
        return var1;
    }
    
    public static WeightedRandomItem getRandomItem(final Random par0Random, final WeightedRandomItem[] par1ArrayOfWeightedRandomItem, final int par2) {
        if (par2 <= 0) {
            throw new IllegalArgumentException();
        }
        int var3 = par0Random.nextInt(par2);
        for (final WeightedRandomItem var6 : par1ArrayOfWeightedRandomItem) {
            var3 -= var6.itemWeight;
            if (var3 < 0) {
                return var6;
            }
        }
        return null;
    }
    
    public static WeightedRandomItem getRandomItem(final Random par0Random, final WeightedRandomItem[] par1ArrayOfWeightedRandomItem) {
        return getRandomItem(par0Random, par1ArrayOfWeightedRandomItem, getTotalWeight(par1ArrayOfWeightedRandomItem));
    }
}
