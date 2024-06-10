/*  1:   */ package net.minecraft.item.crafting;
/*  2:   */ 
/*  3:   */ import net.minecraft.init.Items;
/*  4:   */ import net.minecraft.inventory.InventoryCrafting;
/*  5:   */ import net.minecraft.item.ItemMap;
/*  6:   */ import net.minecraft.item.ItemStack;
/*  7:   */ import net.minecraft.nbt.NBTTagCompound;
/*  8:   */ import net.minecraft.world.World;
/*  9:   */ import net.minecraft.world.storage.MapData;
/* 10:   */ 
/* 11:   */ public class RecipesMapExtending
/* 12:   */   extends ShapedRecipes
/* 13:   */ {
/* 14:   */   private static final String __OBFID = "CL_00000088";
/* 15:   */   
/* 16:   */   public RecipesMapExtending()
/* 17:   */   {
/* 18:16 */     super(3, 3, new ItemStack[] { new ItemStack(Items.paper), new ItemStack(Items.paper), new ItemStack(Items.paper), new ItemStack(Items.paper), new ItemStack(Items.filled_map, 0, 32767), new ItemStack(Items.paper), new ItemStack(Items.paper), new ItemStack(Items.paper), new ItemStack(Items.paper) }, new ItemStack(Items.map, 0, 0));
/* 19:   */   }
/* 20:   */   
/* 21:   */   public boolean matches(InventoryCrafting par1InventoryCrafting, World par2World)
/* 22:   */   {
/* 23:24 */     if (!super.matches(par1InventoryCrafting, par2World)) {
/* 24:26 */       return false;
/* 25:   */     }
/* 26:30 */     ItemStack var3 = null;
/* 27:32 */     for (int var4 = 0; (var4 < par1InventoryCrafting.getSizeInventory()) && (var3 == null); var4++)
/* 28:   */     {
/* 29:34 */       ItemStack var5 = par1InventoryCrafting.getStackInSlot(var4);
/* 30:36 */       if ((var5 != null) && (var5.getItem() == Items.filled_map)) {
/* 31:38 */         var3 = var5;
/* 32:   */       }
/* 33:   */     }
/* 34:42 */     if (var3 == null) {
/* 35:44 */       return false;
/* 36:   */     }
/* 37:48 */     MapData var6 = Items.filled_map.getMapData(var3, par2World);
/* 38:49 */     return var6 != null;
/* 39:   */   }
/* 40:   */   
/* 41:   */   public ItemStack getCraftingResult(InventoryCrafting par1InventoryCrafting)
/* 42:   */   {
/* 43:59 */     ItemStack var2 = null;
/* 44:61 */     for (int var3 = 0; (var3 < par1InventoryCrafting.getSizeInventory()) && (var2 == null); var3++)
/* 45:   */     {
/* 46:63 */       ItemStack var4 = par1InventoryCrafting.getStackInSlot(var3);
/* 47:65 */       if ((var4 != null) && (var4.getItem() == Items.filled_map)) {
/* 48:67 */         var2 = var4;
/* 49:   */       }
/* 50:   */     }
/* 51:71 */     var2 = var2.copy();
/* 52:72 */     var2.stackSize = 1;
/* 53:74 */     if (var2.getTagCompound() == null) {
/* 54:76 */       var2.setTagCompound(new NBTTagCompound());
/* 55:   */     }
/* 56:79 */     var2.getTagCompound().setBoolean("map_is_scaling", true);
/* 57:80 */     return var2;
/* 58:   */   }
/* 59:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.item.crafting.RecipesMapExtending
 * JD-Core Version:    0.7.0.1
 */