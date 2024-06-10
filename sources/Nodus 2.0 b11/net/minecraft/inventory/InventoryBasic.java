/*   1:    */ package net.minecraft.inventory;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.List;
/*   5:    */ import net.minecraft.entity.player.EntityPlayer;
/*   6:    */ import net.minecraft.item.ItemStack;
/*   7:    */ 
/*   8:    */ public class InventoryBasic
/*   9:    */   implements IInventory
/*  10:    */ {
/*  11:    */   private String inventoryTitle;
/*  12:    */   private int slotsCount;
/*  13:    */   private ItemStack[] inventoryContents;
/*  14:    */   private List field_70480_d;
/*  15:    */   private boolean field_94051_e;
/*  16:    */   private static final String __OBFID = "CL_00001514";
/*  17:    */   
/*  18:    */   public InventoryBasic(String par1Str, boolean par2, int par3)
/*  19:    */   {
/*  20: 19 */     this.inventoryTitle = par1Str;
/*  21: 20 */     this.field_94051_e = par2;
/*  22: 21 */     this.slotsCount = par3;
/*  23: 22 */     this.inventoryContents = new ItemStack[par3];
/*  24:    */   }
/*  25:    */   
/*  26:    */   public void func_110134_a(IInvBasic par1IInvBasic)
/*  27:    */   {
/*  28: 27 */     if (this.field_70480_d == null) {
/*  29: 29 */       this.field_70480_d = new ArrayList();
/*  30:    */     }
/*  31: 32 */     this.field_70480_d.add(par1IInvBasic);
/*  32:    */   }
/*  33:    */   
/*  34:    */   public void func_110132_b(IInvBasic par1IInvBasic)
/*  35:    */   {
/*  36: 37 */     this.field_70480_d.remove(par1IInvBasic);
/*  37:    */   }
/*  38:    */   
/*  39:    */   public ItemStack getStackInSlot(int par1)
/*  40:    */   {
/*  41: 45 */     return this.inventoryContents[par1];
/*  42:    */   }
/*  43:    */   
/*  44:    */   public ItemStack decrStackSize(int par1, int par2)
/*  45:    */   {
/*  46: 54 */     if (this.inventoryContents[par1] != null)
/*  47:    */     {
/*  48: 58 */       if (this.inventoryContents[par1].stackSize <= par2)
/*  49:    */       {
/*  50: 60 */         ItemStack var3 = this.inventoryContents[par1];
/*  51: 61 */         this.inventoryContents[par1] = null;
/*  52: 62 */         onInventoryChanged();
/*  53: 63 */         return var3;
/*  54:    */       }
/*  55: 67 */       ItemStack var3 = this.inventoryContents[par1].splitStack(par2);
/*  56: 69 */       if (this.inventoryContents[par1].stackSize == 0) {
/*  57: 71 */         this.inventoryContents[par1] = null;
/*  58:    */       }
/*  59: 74 */       onInventoryChanged();
/*  60: 75 */       return var3;
/*  61:    */     }
/*  62: 80 */     return null;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public ItemStack getStackInSlotOnClosing(int par1)
/*  66:    */   {
/*  67: 90 */     if (this.inventoryContents[par1] != null)
/*  68:    */     {
/*  69: 92 */       ItemStack var2 = this.inventoryContents[par1];
/*  70: 93 */       this.inventoryContents[par1] = null;
/*  71: 94 */       return var2;
/*  72:    */     }
/*  73: 98 */     return null;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public void setInventorySlotContents(int par1, ItemStack par2ItemStack)
/*  77:    */   {
/*  78:107 */     this.inventoryContents[par1] = par2ItemStack;
/*  79:109 */     if ((par2ItemStack != null) && (par2ItemStack.stackSize > getInventoryStackLimit())) {
/*  80:111 */       par2ItemStack.stackSize = getInventoryStackLimit();
/*  81:    */     }
/*  82:114 */     onInventoryChanged();
/*  83:    */   }
/*  84:    */   
/*  85:    */   public int getSizeInventory()
/*  86:    */   {
/*  87:122 */     return this.slotsCount;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public String getInventoryName()
/*  91:    */   {
/*  92:130 */     return this.inventoryTitle;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public boolean isInventoryNameLocalized()
/*  96:    */   {
/*  97:138 */     return this.field_94051_e;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public void func_110133_a(String par1Str)
/* 101:    */   {
/* 102:143 */     this.field_94051_e = true;
/* 103:144 */     this.inventoryTitle = par1Str;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public int getInventoryStackLimit()
/* 107:    */   {
/* 108:152 */     return 64;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public void onInventoryChanged()
/* 112:    */   {
/* 113:160 */     if (this.field_70480_d != null) {
/* 114:162 */       for (int var1 = 0; var1 < this.field_70480_d.size(); var1++) {
/* 115:164 */         ((IInvBasic)this.field_70480_d.get(var1)).onInventoryChanged(this);
/* 116:    */       }
/* 117:    */     }
/* 118:    */   }
/* 119:    */   
/* 120:    */   public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer)
/* 121:    */   {
/* 122:174 */     return true;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public void openInventory() {}
/* 126:    */   
/* 127:    */   public void closeInventory() {}
/* 128:    */   
/* 129:    */   public boolean isItemValidForSlot(int par1, ItemStack par2ItemStack)
/* 130:    */   {
/* 131:186 */     return true;
/* 132:    */   }
/* 133:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.inventory.InventoryBasic
 * JD-Core Version:    0.7.0.1
 */