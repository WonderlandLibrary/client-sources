/*   1:    */ package net.minecraft.inventory;
/*   2:    */ 
/*   3:    */ import net.minecraft.entity.player.EntityPlayer;
/*   4:    */ import net.minecraft.item.ItemStack;
/*   5:    */ 
/*   6:    */ public class InventoryLargeChest
/*   7:    */   implements IInventory
/*   8:    */ {
/*   9:    */   private String name;
/*  10:    */   private IInventory upperChest;
/*  11:    */   private IInventory lowerChest;
/*  12:    */   private static final String __OBFID = "CL_00001507";
/*  13:    */   
/*  14:    */   public InventoryLargeChest(String par1Str, IInventory par2IInventory, IInventory par3IInventory)
/*  15:    */   {
/*  16: 20 */     this.name = par1Str;
/*  17: 22 */     if (par2IInventory == null) {
/*  18: 24 */       par2IInventory = par3IInventory;
/*  19:    */     }
/*  20: 27 */     if (par3IInventory == null) {
/*  21: 29 */       par3IInventory = par2IInventory;
/*  22:    */     }
/*  23: 32 */     this.upperChest = par2IInventory;
/*  24: 33 */     this.lowerChest = par3IInventory;
/*  25:    */   }
/*  26:    */   
/*  27:    */   public int getSizeInventory()
/*  28:    */   {
/*  29: 41 */     return this.upperChest.getSizeInventory() + this.lowerChest.getSizeInventory();
/*  30:    */   }
/*  31:    */   
/*  32:    */   public boolean isPartOfLargeChest(IInventory par1IInventory)
/*  33:    */   {
/*  34: 49 */     return (this.upperChest == par1IInventory) || (this.lowerChest == par1IInventory);
/*  35:    */   }
/*  36:    */   
/*  37:    */   public String getInventoryName()
/*  38:    */   {
/*  39: 57 */     return this.lowerChest.isInventoryNameLocalized() ? this.lowerChest.getInventoryName() : this.upperChest.isInventoryNameLocalized() ? this.upperChest.getInventoryName() : this.name;
/*  40:    */   }
/*  41:    */   
/*  42:    */   public boolean isInventoryNameLocalized()
/*  43:    */   {
/*  44: 65 */     return (this.upperChest.isInventoryNameLocalized()) || (this.lowerChest.isInventoryNameLocalized());
/*  45:    */   }
/*  46:    */   
/*  47:    */   public ItemStack getStackInSlot(int par1)
/*  48:    */   {
/*  49: 73 */     return par1 >= this.upperChest.getSizeInventory() ? this.lowerChest.getStackInSlot(par1 - this.upperChest.getSizeInventory()) : this.upperChest.getStackInSlot(par1);
/*  50:    */   }
/*  51:    */   
/*  52:    */   public ItemStack decrStackSize(int par1, int par2)
/*  53:    */   {
/*  54: 82 */     return par1 >= this.upperChest.getSizeInventory() ? this.lowerChest.decrStackSize(par1 - this.upperChest.getSizeInventory(), par2) : this.upperChest.decrStackSize(par1, par2);
/*  55:    */   }
/*  56:    */   
/*  57:    */   public ItemStack getStackInSlotOnClosing(int par1)
/*  58:    */   {
/*  59: 91 */     return par1 >= this.upperChest.getSizeInventory() ? this.lowerChest.getStackInSlotOnClosing(par1 - this.upperChest.getSizeInventory()) : this.upperChest.getStackInSlotOnClosing(par1);
/*  60:    */   }
/*  61:    */   
/*  62:    */   public void setInventorySlotContents(int par1, ItemStack par2ItemStack)
/*  63:    */   {
/*  64: 99 */     if (par1 >= this.upperChest.getSizeInventory()) {
/*  65:101 */       this.lowerChest.setInventorySlotContents(par1 - this.upperChest.getSizeInventory(), par2ItemStack);
/*  66:    */     } else {
/*  67:105 */       this.upperChest.setInventorySlotContents(par1, par2ItemStack);
/*  68:    */     }
/*  69:    */   }
/*  70:    */   
/*  71:    */   public int getInventoryStackLimit()
/*  72:    */   {
/*  73:114 */     return this.upperChest.getInventoryStackLimit();
/*  74:    */   }
/*  75:    */   
/*  76:    */   public void onInventoryChanged()
/*  77:    */   {
/*  78:122 */     this.upperChest.onInventoryChanged();
/*  79:123 */     this.lowerChest.onInventoryChanged();
/*  80:    */   }
/*  81:    */   
/*  82:    */   public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer)
/*  83:    */   {
/*  84:131 */     return (this.upperChest.isUseableByPlayer(par1EntityPlayer)) && (this.lowerChest.isUseableByPlayer(par1EntityPlayer));
/*  85:    */   }
/*  86:    */   
/*  87:    */   public void openInventory()
/*  88:    */   {
/*  89:136 */     this.upperChest.openInventory();
/*  90:137 */     this.lowerChest.openInventory();
/*  91:    */   }
/*  92:    */   
/*  93:    */   public void closeInventory()
/*  94:    */   {
/*  95:142 */     this.upperChest.closeInventory();
/*  96:143 */     this.lowerChest.closeInventory();
/*  97:    */   }
/*  98:    */   
/*  99:    */   public boolean isItemValidForSlot(int par1, ItemStack par2ItemStack)
/* 100:    */   {
/* 101:151 */     return true;
/* 102:    */   }
/* 103:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.inventory.InventoryLargeChest
 * JD-Core Version:    0.7.0.1
 */