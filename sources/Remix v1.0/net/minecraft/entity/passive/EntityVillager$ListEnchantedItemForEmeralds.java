package net.minecraft.entity.passive;

import net.minecraft.item.*;
import java.util.*;
import net.minecraft.init.*;
import net.minecraft.enchantment.*;
import net.minecraft.village.*;

static class ListEnchantedItemForEmeralds implements ITradeList
{
    public ItemStack field_179407_a;
    public PriceInfo field_179406_b;
    
    public ListEnchantedItemForEmeralds(final Item p_i45814_1_, final PriceInfo p_i45814_2_) {
        this.field_179407_a = new ItemStack(p_i45814_1_);
        this.field_179406_b = p_i45814_2_;
    }
    
    @Override
    public void func_179401_a(final MerchantRecipeList p_179401_1_, final Random p_179401_2_) {
        int var3 = 1;
        if (this.field_179406_b != null) {
            var3 = this.field_179406_b.func_179412_a(p_179401_2_);
        }
        final ItemStack var4 = new ItemStack(Items.emerald, var3, 0);
        ItemStack var5 = new ItemStack(this.field_179407_a.getItem(), 1, this.field_179407_a.getMetadata());
        var5 = EnchantmentHelper.addRandomEnchantment(p_179401_2_, var5, 5 + p_179401_2_.nextInt(15));
        p_179401_1_.add(new MerchantRecipe(var4, var5));
    }
}
