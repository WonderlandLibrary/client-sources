package net;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;

public class abe {
   public static NBTTagCompound a(MerchantRecipeList var0) {
      return var0.getRecipiesAsTags();
   }

   public static MerchantRecipeList a(PacketBuffer var0) {
      return MerchantRecipeList.readFromBuf(var0);
   }

   public static void a(MerchantRecipeList var0, PacketBuffer var1) {
      var0.writeToBuf(var1);
   }

   public static MerchantRecipe a(MerchantRecipeList var0, ItemStack var1, ItemStack var2, int var3) {
      return var0.canRecipeBeUsed(var1, var2, var3);
   }
}
