package net.minecraft.entity.passive;

import net.minecraft.item.*;
import java.util.*;
import net.minecraft.init.*;
import net.minecraft.village.*;

static class ItemAndEmeraldToItem implements ITradeList
{
    public ItemStack field_179411_a;
    public PriceInfo field_179409_b;
    public ItemStack field_179410_c;
    public PriceInfo field_179408_d;
    
    public ItemAndEmeraldToItem(final Item p_i45813_1_, final PriceInfo p_i45813_2_, final Item p_i45813_3_, final PriceInfo p_i45813_4_) {
        this.field_179411_a = new ItemStack(p_i45813_1_);
        this.field_179409_b = p_i45813_2_;
        this.field_179410_c = new ItemStack(p_i45813_3_);
        this.field_179408_d = p_i45813_4_;
    }
    
    @Override
    public void func_179401_a(final MerchantRecipeList p_179401_1_, final Random p_179401_2_) {
        int var3 = 1;
        if (this.field_179409_b != null) {
            var3 = this.field_179409_b.func_179412_a(p_179401_2_);
        }
        int var4 = 1;
        if (this.field_179408_d != null) {
            var4 = this.field_179408_d.func_179412_a(p_179401_2_);
        }
        p_179401_1_.add(new MerchantRecipe(new ItemStack(this.field_179411_a.getItem(), var3, this.field_179411_a.getMetadata()), new ItemStack(Items.emerald), new ItemStack(this.field_179410_c.getItem(), var4, this.field_179410_c.getMetadata())));
    }
}
