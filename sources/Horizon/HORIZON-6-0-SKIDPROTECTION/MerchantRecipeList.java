package HORIZON-6-0-SKIDPROTECTION;

import java.io.IOException;
import java.util.ArrayList;

public class MerchantRecipeList extends ArrayList
{
    private static final String HorizonCode_Horizon_È = "CL_00000127";
    
    public MerchantRecipeList() {
    }
    
    public MerchantRecipeList(final NBTTagCompound p_i1944_1_) {
        this.HorizonCode_Horizon_È(p_i1944_1_);
    }
    
    public MerchantRecipe HorizonCode_Horizon_È(final ItemStack p_77203_1_, final ItemStack p_77203_2_, final int p_77203_3_) {
        if (p_77203_3_ > 0 && p_77203_3_ < this.size()) {
            final MerchantRecipe var6 = this.get(p_77203_3_);
            return (ItemStack.Ý(p_77203_1_, var6.HorizonCode_Horizon_È()) && ((p_77203_2_ == null && !var6.Ý()) || (var6.Ý() && ItemStack.Ý(p_77203_2_, var6.Â()))) && p_77203_1_.Â >= var6.HorizonCode_Horizon_È().Â && (!var6.Ý() || p_77203_2_.Â >= var6.Â().Â)) ? var6 : null;
        }
        for (int var7 = 0; var7 < this.size(); ++var7) {
            final MerchantRecipe var8 = this.get(var7);
            if (ItemStack.Ý(p_77203_1_, var8.HorizonCode_Horizon_È()) && p_77203_1_.Â >= var8.HorizonCode_Horizon_È().Â && ((!var8.Ý() && p_77203_2_ == null) || (var8.Ý() && ItemStack.Ý(p_77203_2_, var8.Â()) && p_77203_2_.Â >= var8.Â().Â))) {
                return var8;
            }
        }
        return null;
    }
    
    public void HorizonCode_Horizon_È(final PacketBuffer p_151391_1_) {
        p_151391_1_.writeByte((byte)(this.size() & 0xFF));
        for (int var2 = 0; var2 < this.size(); ++var2) {
            final MerchantRecipe var3 = this.get(var2);
            p_151391_1_.HorizonCode_Horizon_È(var3.HorizonCode_Horizon_È());
            p_151391_1_.HorizonCode_Horizon_È(var3.Ø­áŒŠá());
            final ItemStack var4 = var3.Â();
            p_151391_1_.writeBoolean(var4 != null);
            if (var4 != null) {
                p_151391_1_.HorizonCode_Horizon_È(var4);
            }
            p_151391_1_.writeBoolean(var3.Ø());
            p_151391_1_.writeInt(var3.Âµá€());
            p_151391_1_.writeInt(var3.Ó());
        }
    }
    
    public static MerchantRecipeList Â(final PacketBuffer p_151390_0_) throws IOException {
        final MerchantRecipeList var1 = new MerchantRecipeList();
        for (int var2 = p_151390_0_.readByte() & 0xFF, var3 = 0; var3 < var2; ++var3) {
            final ItemStack var4 = p_151390_0_.Ø();
            final ItemStack var5 = p_151390_0_.Ø();
            ItemStack var6 = null;
            if (p_151390_0_.readBoolean()) {
                var6 = p_151390_0_.Ø();
            }
            final boolean var7 = p_151390_0_.readBoolean();
            final int var8 = p_151390_0_.readInt();
            final int var9 = p_151390_0_.readInt();
            final MerchantRecipe var10 = new MerchantRecipe(var4, var6, var5, var8, var9);
            if (var7) {
                var10.áŒŠÆ();
            }
            var1.add(var10);
        }
        return var1;
    }
    
    public void HorizonCode_Horizon_È(final NBTTagCompound p_77201_1_) {
        final NBTTagList var2 = p_77201_1_.Ý("Recipes", 10);
        for (int var3 = 0; var3 < var2.Âµá€(); ++var3) {
            final NBTTagCompound var4 = var2.Â(var3);
            this.add(new MerchantRecipe(var4));
        }
    }
    
    public NBTTagCompound HorizonCode_Horizon_È() {
        final NBTTagCompound var1 = new NBTTagCompound();
        final NBTTagList var2 = new NBTTagList();
        for (int var3 = 0; var3 < this.size(); ++var3) {
            final MerchantRecipe var4 = this.get(var3);
            var2.HorizonCode_Horizon_È(var4.ÂµÈ());
        }
        var1.HorizonCode_Horizon_È("Recipes", var2);
        return var1;
    }
}
