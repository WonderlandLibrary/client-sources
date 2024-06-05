package net.minecraft.entity.passive;

import net.minecraft.item.*;
import java.util.*;
import net.minecraft.init.*;
import net.minecraft.village.*;

static class ListItemForEmeralds implements ITradeList
{
    public ItemStack field_179403_a;
    public PriceInfo field_179402_b;
    
    public ListItemForEmeralds(final Item p_i45811_1_, final PriceInfo p_i45811_2_) {
        this.field_179403_a = new ItemStack(p_i45811_1_);
        this.field_179402_b = p_i45811_2_;
    }
    
    public ListItemForEmeralds(final ItemStack p_i45812_1_, final PriceInfo p_i45812_2_) {
        this.field_179403_a = p_i45812_1_;
        this.field_179402_b = p_i45812_2_;
    }
    
    @Override
    public void func_179401_a(final MerchantRecipeList p_179401_1_, final Random p_179401_2_) {
        int var3 = 1;
        if (this.field_179402_b != null) {
            var3 = this.field_179402_b.func_179412_a(p_179401_2_);
        }
        ItemStack var4;
        ItemStack var5;
        if (var3 < 0) {
            var4 = new ItemStack(Items.emerald, 1, 0);
            var5 = new ItemStack(this.field_179403_a.getItem(), -var3, this.field_179403_a.getMetadata());
        }
        else {
            var4 = new ItemStack(Items.emerald, var3, 0);
            var5 = new ItemStack(this.field_179403_a.getItem(), 1, this.field_179403_a.getMetadata());
        }
        p_179401_1_.add(new MerchantRecipe(var4, var5));
    }
}
