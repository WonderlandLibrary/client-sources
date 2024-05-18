package HORIZON-6-0-SKIDPROTECTION;

import java.util.Random;
import java.util.List;

public class ItemEnchantedBook extends Item_1028566121
{
    private static final String à = "CL_00000025";
    
    @Override
    public boolean Ø(final ItemStack stack) {
        return true;
    }
    
    @Override
    public boolean áˆºÑ¢Õ(final ItemStack stack) {
        return false;
    }
    
    @Override
    public EnumRarity áŒŠÆ(final ItemStack stack) {
        return (this.ÂµÈ(stack).Âµá€() > 0) ? EnumRarity.Â : super.áŒŠÆ(stack);
    }
    
    public NBTTagList ÂµÈ(final ItemStack p_92110_1_) {
        final NBTTagCompound var2 = p_92110_1_.Å();
        return (NBTTagList)((var2 != null && var2.Â("StoredEnchantments", 9)) ? var2.HorizonCode_Horizon_È("StoredEnchantments") : new NBTTagList());
    }
    
    @Override
    public void HorizonCode_Horizon_È(final ItemStack stack, final EntityPlayer playerIn, final List tooltip, final boolean advanced) {
        super.HorizonCode_Horizon_È(stack, playerIn, tooltip, advanced);
        final NBTTagList var5 = this.ÂµÈ(stack);
        if (var5 != null) {
            for (int var6 = 0; var6 < var5.Âµá€(); ++var6) {
                final short var7 = var5.Â(var6).Âµá€("id");
                final short var8 = var5.Â(var6).Âµá€("lvl");
                if (Enchantment.HorizonCode_Horizon_È(var7) != null) {
                    tooltip.add(Enchantment.HorizonCode_Horizon_È(var7).Ø­áŒŠá(var8));
                }
            }
        }
    }
    
    public void HorizonCode_Horizon_È(final ItemStack p_92115_1_, final EnchantmentData p_92115_2_) {
        final NBTTagList var3 = this.ÂµÈ(p_92115_1_);
        boolean var4 = true;
        for (int var5 = 0; var5 < var3.Âµá€(); ++var5) {
            final NBTTagCompound var6 = var3.Â(var5);
            if (var6.Âµá€("id") == p_92115_2_.HorizonCode_Horizon_È.ŒÏ) {
                if (var6.Âµá€("lvl") < p_92115_2_.Â) {
                    var6.HorizonCode_Horizon_È("lvl", (short)p_92115_2_.Â);
                }
                var4 = false;
                break;
            }
        }
        if (var4) {
            final NBTTagCompound var7 = new NBTTagCompound();
            var7.HorizonCode_Horizon_È("id", (short)p_92115_2_.HorizonCode_Horizon_È.ŒÏ);
            var7.HorizonCode_Horizon_È("lvl", (short)p_92115_2_.Â);
            var3.HorizonCode_Horizon_È(var7);
        }
        if (!p_92115_1_.£á()) {
            p_92115_1_.Ø­áŒŠá(new NBTTagCompound());
        }
        p_92115_1_.Å().HorizonCode_Horizon_È("StoredEnchantments", var3);
    }
    
    public ItemStack HorizonCode_Horizon_È(final EnchantmentData p_92111_1_) {
        final ItemStack var2 = new ItemStack(this);
        this.HorizonCode_Horizon_È(var2, p_92111_1_);
        return var2;
    }
    
    public void HorizonCode_Horizon_È(final Enchantment p_92113_1_, final List p_92113_2_) {
        for (int var3 = p_92113_1_.Ý(); var3 <= p_92113_1_.Ø­áŒŠá(); ++var3) {
            p_92113_2_.add(this.HorizonCode_Horizon_È(new EnchantmentData(p_92113_1_, var3)));
        }
    }
    
    public WeightedRandomChestContent HorizonCode_Horizon_È(final Random p_92114_1_) {
        return this.HorizonCode_Horizon_È(p_92114_1_, 1, 1, 1);
    }
    
    public WeightedRandomChestContent HorizonCode_Horizon_È(final Random p_92112_1_, final int p_92112_2_, final int p_92112_3_, final int p_92112_4_) {
        final ItemStack var5 = new ItemStack(Items.Ñ¢Ç, 1, 0);
        EnchantmentHelper.HorizonCode_Horizon_È(p_92112_1_, var5, 30);
        return new WeightedRandomChestContent(var5, p_92112_2_, p_92112_3_, p_92112_4_);
    }
}
