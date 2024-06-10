/*   1:    */ package net.minecraft.inventory;
/*   2:    */ 
/*   3:    */ import net.minecraft.entity.player.EntityPlayer;
/*   4:    */ import net.minecraft.item.ItemStack;
/*   5:    */ 
/*   6:    */ public class InventoryCraftResult
/*   7:    */   implements IInventory
/*   8:    */ {
/*   9:  9 */   private ItemStack[] stackResult = new ItemStack[1];
/*  10:    */   private static final String __OBFID = "CL_00001760";
/*  11:    */   
/*  12:    */   public int getSizeInventory()
/*  13:    */   {
/*  14: 17 */     return 1;
/*  15:    */   }
/*  16:    */   
/*  17:    */   public ItemStack getStackInSlot(int par1)
/*  18:    */   {
/*  19: 25 */     return this.stackResult[0];
/*  20:    */   }
/*  21:    */   
/*  22:    */   public String getInventoryName()
/*  23:    */   {
/*  24: 33 */     return "Result";
/*  25:    */   }
/*  26:    */   
/*  27:    */   public boolean isInventoryNameLocalized()
/*  28:    */   {
/*  29: 41 */     return false;
/*  30:    */   }
/*  31:    */   
/*  32:    */   public ItemStack decrStackSize(int par1, int par2)
/*  33:    */   {
/*  34: 50 */     if (this.stackResult[0] != null)
/*  35:    */     {
/*  36: 52 */       ItemStack var3 = this.stackResult[0];
/*  37: 53 */       this.stackResult[0] = null;
/*  38: 54 */       return var3;
/*  39:    */     }
/*  40: 58 */     return null;
/*  41:    */   }
/*  42:    */   
/*  43:    */   public ItemStack getStackInSlotOnClosing(int par1)
/*  44:    */   {
/*  45: 68 */     if (this.stackResult[0] != null)
/*  46:    */     {
/*  47: 70 */       ItemStack var2 = this.stackResult[0];
/*  48: 71 */       this.stackResult[0] = null;
/*  49: 72 */       return var2;
/*  50:    */     }
/*  51: 76 */     return null;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public void setInventorySlotContents(int par1, ItemStack par2ItemStack)
/*  55:    */   {
/*  56: 85 */     this.stackResult[0] = par2ItemStack;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public int getInventoryStackLimit()
/*  60:    */   {
/*  61: 93 */     return 64;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public void onInventoryChanged() {}
/*  65:    */   
/*  66:    */   public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer)
/*  67:    */   {
/*  68:106 */     return true;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public void openInventory() {}
/*  72:    */   
/*  73:    */   public void closeInventory() {}
/*  74:    */   
/*  75:    */   public boolean isItemValidForSlot(int par1, ItemStack par2ItemStack)
/*  76:    */   {
/*  77:118 */     return true;
/*  78:    */   }
/*  79:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.inventory.InventoryCraftResult
 * JD-Core Version:    0.7.0.1
 */