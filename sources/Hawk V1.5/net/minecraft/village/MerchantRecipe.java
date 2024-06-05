package net.minecraft.village;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class MerchantRecipe {
   private ItemStack itemToSell;
   private ItemStack secondItemToBuy;
   private boolean field_180323_f;
   private ItemStack itemToBuy;
   private int toolUses;
   private int maxTradeUses;
   private static final String __OBFID = "CL_00000126";

   public NBTTagCompound writeToTags() {
      NBTTagCompound var1 = new NBTTagCompound();
      var1.setTag("buy", this.itemToBuy.writeToNBT(new NBTTagCompound()));
      var1.setTag("sell", this.itemToSell.writeToNBT(new NBTTagCompound()));
      if (this.secondItemToBuy != null) {
         var1.setTag("buyB", this.secondItemToBuy.writeToNBT(new NBTTagCompound()));
      }

      var1.setInteger("uses", this.toolUses);
      var1.setInteger("maxUses", this.maxTradeUses);
      var1.setBoolean("rewardExp", this.field_180323_f);
      return var1;
   }

   public ItemStack getItemToBuy() {
      return this.itemToBuy;
   }

   public boolean func_180322_j() {
      return this.field_180323_f;
   }

   public ItemStack getItemToSell() {
      return this.itemToSell;
   }

   public ItemStack getSecondItemToBuy() {
      return this.secondItemToBuy;
   }

   public void readFromTags(NBTTagCompound var1) {
      NBTTagCompound var2 = var1.getCompoundTag("buy");
      this.itemToBuy = ItemStack.loadItemStackFromNBT(var2);
      NBTTagCompound var3 = var1.getCompoundTag("sell");
      this.itemToSell = ItemStack.loadItemStackFromNBT(var3);
      if (var1.hasKey("buyB", 10)) {
         this.secondItemToBuy = ItemStack.loadItemStackFromNBT(var1.getCompoundTag("buyB"));
      }

      if (var1.hasKey("uses", 99)) {
         this.toolUses = var1.getInteger("uses");
      }

      if (var1.hasKey("maxUses", 99)) {
         this.maxTradeUses = var1.getInteger("maxUses");
      } else {
         this.maxTradeUses = 7;
      }

      if (var1.hasKey("rewardExp", 1)) {
         this.field_180323_f = var1.getBoolean("rewardExp");
      } else {
         this.field_180323_f = true;
      }

   }

   public void incrementToolUses() {
      ++this.toolUses;
   }

   public void func_82785_h() {
      this.toolUses = this.maxTradeUses;
   }

   public MerchantRecipe(NBTTagCompound var1) {
      this.readFromTags(var1);
   }

   public void func_82783_a(int var1) {
      this.maxTradeUses += var1;
   }

   public MerchantRecipe(ItemStack var1, ItemStack var2, ItemStack var3, int var4, int var5) {
      this.itemToBuy = var1;
      this.secondItemToBuy = var2;
      this.itemToSell = var3;
      this.toolUses = var4;
      this.maxTradeUses = var5;
      this.field_180323_f = true;
   }

   public int func_180321_e() {
      return this.toolUses;
   }

   public MerchantRecipe(ItemStack var1, Item var2) {
      this(var1, new ItemStack(var2));
   }

   public boolean isRecipeDisabled() {
      return this.toolUses >= this.maxTradeUses;
   }

   public boolean hasSecondItemToBuy() {
      return this.secondItemToBuy != null;
   }

   public int func_180320_f() {
      return this.maxTradeUses;
   }

   public MerchantRecipe(ItemStack var1, ItemStack var2) {
      this(var1, (ItemStack)null, var2);
   }

   public MerchantRecipe(ItemStack var1, ItemStack var2, ItemStack var3) {
      this(var1, var2, var3, 0, 7);
   }
}
