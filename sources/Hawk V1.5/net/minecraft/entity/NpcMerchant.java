package net.minecraft.entity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryMerchant;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;

public class NpcMerchant implements IMerchant {
   private static final String __OBFID = "CL_00001705";
   private IChatComponent field_175548_d;
   private EntityPlayer customer;
   private MerchantRecipeList recipeList;
   private InventoryMerchant theMerchantInventory;

   public IChatComponent getDisplayName() {
      return (IChatComponent)(this.field_175548_d != null ? this.field_175548_d : new ChatComponentTranslation("entity.Villager.name", new Object[0]));
   }

   public MerchantRecipeList getRecipes(EntityPlayer var1) {
      return this.recipeList;
   }

   public void setRecipes(MerchantRecipeList var1) {
      this.recipeList = var1;
   }

   public void useRecipe(MerchantRecipe var1) {
      var1.incrementToolUses();
   }

   public void setCustomer(EntityPlayer var1) {
   }

   public NpcMerchant(EntityPlayer var1, IChatComponent var2) {
      this.customer = var1;
      this.field_175548_d = var2;
      this.theMerchantInventory = new InventoryMerchant(var1, this);
   }

   public EntityPlayer getCustomer() {
      return this.customer;
   }

   public void verifySellingItem(ItemStack var1) {
   }
}
