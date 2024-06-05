package net.minecraft.entity.passive;

import java.util.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import net.minecraft.village.*;

static class EmeraldForItems implements ITradeList
{
    public Item field_179405_a;
    public PriceInfo field_179404_b;
    
    public EmeraldForItems(final Item p_i45815_1_, final PriceInfo p_i45815_2_) {
        this.field_179405_a = p_i45815_1_;
        this.field_179404_b = p_i45815_2_;
    }
    
    @Override
    public void func_179401_a(final MerchantRecipeList p_179401_1_, final Random p_179401_2_) {
        int var3 = 1;
        if (this.field_179404_b != null) {
            var3 = this.field_179404_b.func_179412_a(p_179401_2_);
        }
        p_179401_1_.add(new MerchantRecipe(new ItemStack(this.field_179405_a, var3, 0), Items.emerald));
    }
}
