package net.minecraft.village;

import java.io.IOException;
import java.util.ArrayList;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.network.PacketBuffer;
import net.minecraft.village.MerchantRecipe;

public class MerchantRecipeList extends ArrayList {
   public MerchantRecipeList() {
   }

   public MerchantRecipeList(NBTTagCompound var1) {
      this.readRecipiesFromTags(var1);
   }

   public MerchantRecipe canRecipeBeUsed(ItemStack var1, ItemStack var2, int var3) {
      if(var3 >= this.size()) {
         for(int var6 = 0; var6 < this.size(); ++var6) {
            MerchantRecipe var5 = (MerchantRecipe)this.get(var6);
            if(this.func_181078_a(var1, var5.getItemToBuy()) && var1.stackSize >= var5.getItemToBuy().stackSize) {
               if(!var5.hasSecondItemToBuy()) {
                  ;
               }

               if(var5.hasSecondItemToBuy() && this.func_181078_a(var2, var5.getSecondItemToBuy()) && var2.stackSize >= var5.getSecondItemToBuy().stackSize) {
                  return var5;
               }
            }
         }

         return null;
      } else {
         MerchantRecipe var4 = (MerchantRecipe)this.get(var3);
         return this.func_181078_a(var1, var4.getItemToBuy()) && (!var4.hasSecondItemToBuy() || var4.hasSecondItemToBuy() && this.func_181078_a(var2, var4.getSecondItemToBuy())) && var1.stackSize >= var4.getItemToBuy().stackSize && (!var4.hasSecondItemToBuy() || var2.stackSize >= var4.getSecondItemToBuy().stackSize)?var4:null;
      }
   }

   private boolean func_181078_a(ItemStack var1, ItemStack var2) {
      return ItemStack.areItemsEqual(var1, var2) && (!var2.hasTagCompound() || var1.hasTagCompound() && NBTUtil.a(var2.getTagCompound(), var1.getTagCompound(), false));
   }

   public void writeToBuf(PacketBuffer var1) {
      var1.writeByte((byte)(this.size() & 255));

      for(MerchantRecipe var3 : this) {
         var1.a(var3.getItemToBuy());
         var1.a(var3.getItemToSell());
         ItemStack var5 = var3.getSecondItemToBuy();
         var1.writeBoolean(true);
         var1.a(var5);
         var1.writeBoolean(var3.isRecipeDisabled());
         var1.writeInt(var3.getToolUses());
         var1.writeInt(var3.getMaxTradeUses());
      }

   }

   public static MerchantRecipeList readFromBuf(PacketBuffer var0) throws IOException {
      MerchantRecipeList var1 = new MerchantRecipeList();
      int var2 = var0.readByte() & 255;

      for(int var3 = 0; var3 < var2; ++var3) {
         ItemStack var4 = var0.readItemStackFromBuffer();
         ItemStack var5 = var0.readItemStackFromBuffer();
         ItemStack var6 = null;
         if(var0.readBoolean()) {
            var6 = var0.readItemStackFromBuffer();
         }

         boolean var7 = var0.readBoolean();
         int var8 = var0.readInt();
         int var9 = var0.readInt();
         MerchantRecipe var10 = new MerchantRecipe(var4, var6, var5, var8, var9);
         var10.compensateToolUses();
         var1.add(var10);
      }

      return var1;
   }

   public void readRecipiesFromTags(NBTTagCompound var1) {
      NBTTagList var2 = var1.getTagList("Recipes", 10);

      for(int var3 = 0; var3 < var2.tagCount(); ++var3) {
         NBTTagCompound var4 = var2.getCompoundTagAt(var3);
         this.add(new MerchantRecipe(var4));
      }

   }

   public NBTTagCompound getRecipiesAsTags() {
      NBTTagCompound var1 = new NBTTagCompound();
      NBTTagList var2 = new NBTTagList();

      for(MerchantRecipe var4 : this) {
         var2.appendTag(var4.writeToTags());
      }

      var1.setTag("Recipes", var2);
      return var1;
   }

   private static IOException a(IOException var0) {
      return var0;
   }
}
