/*   1:    */ package net.minecraft.inventory;
/*   2:    */ 
/*   3:    */ import net.minecraft.entity.player.EntityPlayer;
/*   4:    */ import net.minecraft.item.ItemStack;
/*   5:    */ 
/*   6:    */ public class InventoryCrafting
/*   7:    */   implements IInventory
/*   8:    */ {
/*   9:    */   private ItemStack[] stackList;
/*  10:    */   private int inventoryWidth;
/*  11:    */   private Container eventHandler;
/*  12:    */   private static final String __OBFID = "CL_00001743";
/*  13:    */   
/*  14:    */   public InventoryCrafting(Container par1Container, int par2, int par3)
/*  15:    */   {
/*  16: 22 */     int var4 = par2 * par3;
/*  17: 23 */     this.stackList = new ItemStack[var4];
/*  18: 24 */     this.eventHandler = par1Container;
/*  19: 25 */     this.inventoryWidth = par2;
/*  20:    */   }
/*  21:    */   
/*  22:    */   public int getSizeInventory()
/*  23:    */   {
/*  24: 33 */     return this.stackList.length;
/*  25:    */   }
/*  26:    */   
/*  27:    */   public ItemStack getStackInSlot(int par1)
/*  28:    */   {
/*  29: 41 */     return par1 >= getSizeInventory() ? null : this.stackList[par1];
/*  30:    */   }
/*  31:    */   
/*  32:    */   public ItemStack getStackInRowAndColumn(int par1, int par2)
/*  33:    */   {
/*  34: 49 */     if ((par1 >= 0) && (par1 < this.inventoryWidth))
/*  35:    */     {
/*  36: 51 */       int var3 = par1 + par2 * this.inventoryWidth;
/*  37: 52 */       return getStackInSlot(var3);
/*  38:    */     }
/*  39: 56 */     return null;
/*  40:    */   }
/*  41:    */   
/*  42:    */   public String getInventoryName()
/*  43:    */   {
/*  44: 65 */     return "container.crafting";
/*  45:    */   }
/*  46:    */   
/*  47:    */   public boolean isInventoryNameLocalized()
/*  48:    */   {
/*  49: 73 */     return false;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public ItemStack getStackInSlotOnClosing(int par1)
/*  53:    */   {
/*  54: 82 */     if (this.stackList[par1] != null)
/*  55:    */     {
/*  56: 84 */       ItemStack var2 = this.stackList[par1];
/*  57: 85 */       this.stackList[par1] = null;
/*  58: 86 */       return var2;
/*  59:    */     }
/*  60: 90 */     return null;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public ItemStack decrStackSize(int par1, int par2)
/*  64:    */   {
/*  65:100 */     if (this.stackList[par1] != null)
/*  66:    */     {
/*  67:104 */       if (this.stackList[par1].stackSize <= par2)
/*  68:    */       {
/*  69:106 */         ItemStack var3 = this.stackList[par1];
/*  70:107 */         this.stackList[par1] = null;
/*  71:108 */         this.eventHandler.onCraftMatrixChanged(this);
/*  72:109 */         return var3;
/*  73:    */       }
/*  74:113 */       ItemStack var3 = this.stackList[par1].splitStack(par2);
/*  75:115 */       if (this.stackList[par1].stackSize == 0) {
/*  76:117 */         this.stackList[par1] = null;
/*  77:    */       }
/*  78:120 */       this.eventHandler.onCraftMatrixChanged(this);
/*  79:121 */       return var3;
/*  80:    */     }
/*  81:126 */     return null;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public void setInventorySlotContents(int par1, ItemStack par2ItemStack)
/*  85:    */   {
/*  86:135 */     this.stackList[par1] = par2ItemStack;
/*  87:136 */     this.eventHandler.onCraftMatrixChanged(this);
/*  88:    */   }
/*  89:    */   
/*  90:    */   public int getInventoryStackLimit()
/*  91:    */   {
/*  92:144 */     return 64;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public void onInventoryChanged() {}
/*  96:    */   
/*  97:    */   public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer)
/*  98:    */   {
/*  99:157 */     return true;
/* 100:    */   }
/* 101:    */   
/* 102:    */   public void openInventory() {}
/* 103:    */   
/* 104:    */   public void closeInventory() {}
/* 105:    */   
/* 106:    */   public boolean isItemValidForSlot(int par1, ItemStack par2ItemStack)
/* 107:    */   {
/* 108:169 */     return true;
/* 109:    */   }
/* 110:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.inventory.InventoryCrafting
 * JD-Core Version:    0.7.0.1
 */