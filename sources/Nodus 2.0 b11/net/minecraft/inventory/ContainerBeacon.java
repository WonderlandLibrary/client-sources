/*   1:    */ package net.minecraft.inventory;
/*   2:    */ 
/*   3:    */ import java.util.List;
/*   4:    */ import net.minecraft.entity.player.EntityPlayer;
/*   5:    */ import net.minecraft.entity.player.InventoryPlayer;
/*   6:    */ import net.minecraft.item.ItemStack;
/*   7:    */ import net.minecraft.tileentity.TileEntityBeacon;
/*   8:    */ 
/*   9:    */ public class ContainerBeacon
/*  10:    */   extends Container
/*  11:    */ {
/*  12:    */   private TileEntityBeacon theBeacon;
/*  13:    */   private final BeaconSlot beaconSlot;
/*  14:    */   private int field_82865_g;
/*  15:    */   private int field_82867_h;
/*  16:    */   private int field_82868_i;
/*  17:    */   private static final String __OBFID = "CL_00001735";
/*  18:    */   
/*  19:    */   public ContainerBeacon(InventoryPlayer par1InventoryPlayer, TileEntityBeacon par2TileEntityBeacon)
/*  20:    */   {
/*  21: 24 */     this.theBeacon = par2TileEntityBeacon;
/*  22: 25 */     addSlotToContainer(this.beaconSlot = new BeaconSlot(par2TileEntityBeacon, 0, 136, 110));
/*  23: 26 */     byte var3 = 36;
/*  24: 27 */     short var4 = 137;
/*  25: 30 */     for (int var5 = 0; var5 < 3; var5++) {
/*  26: 32 */       for (int var6 = 0; var6 < 9; var6++) {
/*  27: 34 */         addSlotToContainer(new Slot(par1InventoryPlayer, var6 + var5 * 9 + 9, var3 + var6 * 18, var4 + var5 * 18));
/*  28:    */       }
/*  29:    */     }
/*  30: 38 */     for (var5 = 0; var5 < 9; var5++) {
/*  31: 40 */       addSlotToContainer(new Slot(par1InventoryPlayer, var5, var3 + var5 * 18, 58 + var4));
/*  32:    */     }
/*  33: 43 */     this.field_82865_g = par2TileEntityBeacon.func_145998_l();
/*  34: 44 */     this.field_82867_h = par2TileEntityBeacon.func_146007_j();
/*  35: 45 */     this.field_82868_i = par2TileEntityBeacon.func_146006_k();
/*  36:    */   }
/*  37:    */   
/*  38:    */   public void addCraftingToCrafters(ICrafting par1ICrafting)
/*  39:    */   {
/*  40: 50 */     super.addCraftingToCrafters(par1ICrafting);
/*  41: 51 */     par1ICrafting.sendProgressBarUpdate(this, 0, this.field_82865_g);
/*  42: 52 */     par1ICrafting.sendProgressBarUpdate(this, 1, this.field_82867_h);
/*  43: 53 */     par1ICrafting.sendProgressBarUpdate(this, 2, this.field_82868_i);
/*  44:    */   }
/*  45:    */   
/*  46:    */   public void updateProgressBar(int par1, int par2)
/*  47:    */   {
/*  48: 58 */     if (par1 == 0) {
/*  49: 60 */       this.theBeacon.func_146005_c(par2);
/*  50:    */     }
/*  51: 63 */     if (par1 == 1) {
/*  52: 65 */       this.theBeacon.func_146001_d(par2);
/*  53:    */     }
/*  54: 68 */     if (par1 == 2) {
/*  55: 70 */       this.theBeacon.func_146004_e(par2);
/*  56:    */     }
/*  57:    */   }
/*  58:    */   
/*  59:    */   public TileEntityBeacon func_148327_e()
/*  60:    */   {
/*  61: 76 */     return this.theBeacon;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public boolean canInteractWith(EntityPlayer par1EntityPlayer)
/*  65:    */   {
/*  66: 81 */     return this.theBeacon.isUseableByPlayer(par1EntityPlayer);
/*  67:    */   }
/*  68:    */   
/*  69:    */   public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2)
/*  70:    */   {
/*  71: 89 */     ItemStack var3 = null;
/*  72: 90 */     Slot var4 = (Slot)this.inventorySlots.get(par2);
/*  73: 92 */     if ((var4 != null) && (var4.getHasStack()))
/*  74:    */     {
/*  75: 94 */       ItemStack var5 = var4.getStack();
/*  76: 95 */       var3 = var5.copy();
/*  77: 97 */       if (par2 == 0)
/*  78:    */       {
/*  79: 99 */         if (!mergeItemStack(var5, 1, 37, true)) {
/*  80:101 */           return null;
/*  81:    */         }
/*  82:104 */         var4.onSlotChange(var5, var3);
/*  83:    */       }
/*  84:106 */       else if ((!this.beaconSlot.getHasStack()) && (this.beaconSlot.isItemValid(var5)) && (var5.stackSize == 1))
/*  85:    */       {
/*  86:108 */         if (!mergeItemStack(var5, 0, 1, false)) {
/*  87:110 */           return null;
/*  88:    */         }
/*  89:    */       }
/*  90:113 */       else if ((par2 >= 1) && (par2 < 28))
/*  91:    */       {
/*  92:115 */         if (!mergeItemStack(var5, 28, 37, false)) {
/*  93:117 */           return null;
/*  94:    */         }
/*  95:    */       }
/*  96:120 */       else if ((par2 >= 28) && (par2 < 37))
/*  97:    */       {
/*  98:122 */         if (!mergeItemStack(var5, 1, 28, false)) {
/*  99:124 */           return null;
/* 100:    */         }
/* 101:    */       }
/* 102:127 */       else if (!mergeItemStack(var5, 1, 37, false))
/* 103:    */       {
/* 104:129 */         return null;
/* 105:    */       }
/* 106:132 */       if (var5.stackSize == 0) {
/* 107:134 */         var4.putStack(null);
/* 108:    */       } else {
/* 109:138 */         var4.onSlotChanged();
/* 110:    */       }
/* 111:141 */       if (var5.stackSize == var3.stackSize) {
/* 112:143 */         return null;
/* 113:    */       }
/* 114:146 */       var4.onPickupFromSlot(par1EntityPlayer, var5);
/* 115:    */     }
/* 116:149 */     return var3;
/* 117:    */   }
/* 118:    */   
/* 119:    */   class BeaconSlot
/* 120:    */     extends Slot
/* 121:    */   {
/* 122:    */     private static final String __OBFID = "CL_00001736";
/* 123:    */     
/* 124:    */     public BeaconSlot(IInventory par2IInventory, int par3, int par4, int par5)
/* 125:    */     {
/* 126:158 */       super(par3, par4, par5);
/* 127:    */     }
/* 128:    */     
/* 129:    */     public boolean isItemValid(ItemStack par1ItemStack)
/* 130:    */     {
/* 131:163 */       return par1ItemStack != null;
/* 132:    */     }
/* 133:    */     
/* 134:    */     public int getSlotStackLimit()
/* 135:    */     {
/* 136:168 */       return 1;
/* 137:    */     }
/* 138:    */   }
/* 139:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.inventory.ContainerBeacon
 * JD-Core Version:    0.7.0.1
 */