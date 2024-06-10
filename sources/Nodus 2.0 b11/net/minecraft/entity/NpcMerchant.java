/*  1:   */ package net.minecraft.entity;
/*  2:   */ 
/*  3:   */ import net.minecraft.entity.player.EntityPlayer;
/*  4:   */ import net.minecraft.inventory.InventoryMerchant;
/*  5:   */ import net.minecraft.item.ItemStack;
/*  6:   */ import net.minecraft.village.MerchantRecipe;
/*  7:   */ import net.minecraft.village.MerchantRecipeList;
/*  8:   */ 
/*  9:   */ public class NpcMerchant
/* 10:   */   implements IMerchant
/* 11:   */ {
/* 12:   */   private InventoryMerchant theMerchantInventory;
/* 13:   */   private EntityPlayer customer;
/* 14:   */   private MerchantRecipeList recipeList;
/* 15:   */   private static final String __OBFID = "CL_00001705";
/* 16:   */   
/* 17:   */   public NpcMerchant(EntityPlayer par1EntityPlayer)
/* 18:   */   {
/* 19:23 */     this.customer = par1EntityPlayer;
/* 20:24 */     this.theMerchantInventory = new InventoryMerchant(par1EntityPlayer, this);
/* 21:   */   }
/* 22:   */   
/* 23:   */   public EntityPlayer getCustomer()
/* 24:   */   {
/* 25:29 */     return this.customer;
/* 26:   */   }
/* 27:   */   
/* 28:   */   public void setCustomer(EntityPlayer par1EntityPlayer) {}
/* 29:   */   
/* 30:   */   public MerchantRecipeList getRecipes(EntityPlayer par1EntityPlayer)
/* 31:   */   {
/* 32:36 */     return this.recipeList;
/* 33:   */   }
/* 34:   */   
/* 35:   */   public void setRecipes(MerchantRecipeList par1MerchantRecipeList)
/* 36:   */   {
/* 37:41 */     this.recipeList = par1MerchantRecipeList;
/* 38:   */   }
/* 39:   */   
/* 40:   */   public void useRecipe(MerchantRecipe par1MerchantRecipe) {}
/* 41:   */   
/* 42:   */   public void func_110297_a_(ItemStack par1ItemStack) {}
/* 43:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.entity.NpcMerchant
 * JD-Core Version:    0.7.0.1
 */