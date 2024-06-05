package net.minecraft.entity.passive;

import java.util.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.enchantment.*;
import net.minecraft.item.*;
import net.minecraft.village.*;

static class ListEnchantedBookForEmeralds implements ITradeList
{
    @Override
    public void func_179401_a(final MerchantRecipeList p_179401_1_, final Random p_179401_2_) {
        final Enchantment var3 = Enchantment.enchantmentsList[p_179401_2_.nextInt(Enchantment.enchantmentsList.length)];
        final int var4 = MathHelper.getRandomIntegerInRange(p_179401_2_, var3.getMinLevel(), var3.getMaxLevel());
        final ItemStack var5 = Items.enchanted_book.getEnchantedItemStack(new EnchantmentData(var3, var4));
        int var6 = 2 + p_179401_2_.nextInt(5 + var4 * 10) + 3 * var4;
        if (var6 > 64) {
            var6 = 64;
        }
        p_179401_1_.add(new MerchantRecipe(new ItemStack(Items.book), new ItemStack(Items.emerald, var6), var5));
    }
}
