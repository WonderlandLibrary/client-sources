/*   1:    */ package net.minecraft.inventory;
/*   2:    */ 
/*   3:    */ import java.util.List;
/*   4:    */ import net.minecraft.entity.IMerchant;
/*   5:    */ import net.minecraft.entity.player.EntityPlayer;
/*   6:    */ import net.minecraft.entity.player.InventoryPlayer;
/*   7:    */ import net.minecraft.item.ItemStack;
/*   8:    */ import net.minecraft.world.World;
/*   9:    */ 
/*  10:    */ public class ContainerMerchant
/*  11:    */   extends Container
/*  12:    */ {
/*  13:    */   private IMerchant theMerchant;
/*  14:    */   private InventoryMerchant merchantInventory;
/*  15:    */   private final World theWorld;
/*  16:    */   private static final String __OBFID = "CL_00001757";
/*  17:    */   
/*  18:    */   public ContainerMerchant(InventoryPlayer par1InventoryPlayer, IMerchant par2IMerchant, World par3World)
/*  19:    */   {
/*  20: 21 */     this.theMerchant = par2IMerchant;
/*  21: 22 */     this.theWorld = par3World;
/*  22: 23 */     this.merchantInventory = new InventoryMerchant(par1InventoryPlayer.player, par2IMerchant);
/*  23: 24 */     addSlotToContainer(new Slot(this.merchantInventory, 0, 36, 53));
/*  24: 25 */     addSlotToContainer(new Slot(this.merchantInventory, 1, 62, 53));
/*  25: 26 */     addSlotToContainer(new SlotMerchantResult(par1InventoryPlayer.player, par2IMerchant, this.merchantInventory, 2, 120, 53));
/*  26: 29 */     for (int var4 = 0; var4 < 3; var4++) {
/*  27: 31 */       for (int var5 = 0; var5 < 9; var5++) {
/*  28: 33 */         addSlotToContainer(new Slot(par1InventoryPlayer, var5 + var4 * 9 + 9, 8 + var5 * 18, 84 + var4 * 18));
/*  29:    */       }
/*  30:    */     }
/*  31: 37 */     for (var4 = 0; var4 < 9; var4++) {
/*  32: 39 */       addSlotToContainer(new Slot(par1InventoryPlayer, var4, 8 + var4 * 18, 142));
/*  33:    */     }
/*  34:    */   }
/*  35:    */   
/*  36:    */   public InventoryMerchant getMerchantInventory()
/*  37:    */   {
/*  38: 45 */     return this.merchantInventory;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public void addCraftingToCrafters(ICrafting par1ICrafting)
/*  42:    */   {
/*  43: 50 */     super.addCraftingToCrafters(par1ICrafting);
/*  44:    */   }
/*  45:    */   
/*  46:    */   public void detectAndSendChanges()
/*  47:    */   {
/*  48: 58 */     super.detectAndSendChanges();
/*  49:    */   }
/*  50:    */   
/*  51:    */   public void onCraftMatrixChanged(IInventory par1IInventory)
/*  52:    */   {
/*  53: 66 */     this.merchantInventory.resetRecipeAndSlots();
/*  54: 67 */     super.onCraftMatrixChanged(par1IInventory);
/*  55:    */   }
/*  56:    */   
/*  57:    */   public void setCurrentRecipeIndex(int par1)
/*  58:    */   {
/*  59: 72 */     this.merchantInventory.setCurrentRecipeIndex(par1);
/*  60:    */   }
/*  61:    */   
/*  62:    */   public void updateProgressBar(int par1, int par2) {}
/*  63:    */   
/*  64:    */   public boolean canInteractWith(EntityPlayer par1EntityPlayer)
/*  65:    */   {
/*  66: 79 */     return this.theMerchant.getCustomer() == par1EntityPlayer;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2)
/*  70:    */   {
/*  71: 87 */     ItemStack var3 = null;
/*  72: 88 */     Slot var4 = (Slot)this.inventorySlots.get(par2);
/*  73: 90 */     if ((var4 != null) && (var4.getHasStack()))
/*  74:    */     {
/*  75: 92 */       ItemStack var5 = var4.getStack();
/*  76: 93 */       var3 = var5.copy();
/*  77: 95 */       if (par2 == 2)
/*  78:    */       {
/*  79: 97 */         if (!mergeItemStack(var5, 3, 39, true)) {
/*  80: 99 */           return null;
/*  81:    */         }
/*  82:102 */         var4.onSlotChange(var5, var3);
/*  83:    */       }
/*  84:104 */       else if ((par2 != 0) && (par2 != 1))
/*  85:    */       {
/*  86:106 */         if ((par2 >= 3) && (par2 < 30))
/*  87:    */         {
/*  88:108 */           if (!mergeItemStack(var5, 30, 39, false)) {
/*  89:110 */             return null;
/*  90:    */           }
/*  91:    */         }
/*  92:113 */         else if ((par2 >= 30) && (par2 < 39) && (!mergeItemStack(var5, 3, 30, false))) {
/*  93:115 */           return null;
/*  94:    */         }
/*  95:    */       }
/*  96:118 */       else if (!mergeItemStack(var5, 3, 39, false))
/*  97:    */       {
/*  98:120 */         return null;
/*  99:    */       }
/* 100:123 */       if (var5.stackSize == 0) {
/* 101:125 */         var4.putStack(null);
/* 102:    */       } else {
/* 103:129 */         var4.onSlotChanged();
/* 104:    */       }
/* 105:132 */       if (var5.stackSize == var3.stackSize) {
/* 106:134 */         return null;
/* 107:    */       }
/* 108:137 */       var4.onPickupFromSlot(par1EntityPlayer, var5);
/* 109:    */     }
/* 110:140 */     return var3;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public void onContainerClosed(EntityPlayer par1EntityPlayer)
/* 114:    */   {
/* 115:148 */     super.onContainerClosed(par1EntityPlayer);
/* 116:149 */     this.theMerchant.setCustomer(null);
/* 117:150 */     super.onContainerClosed(par1EntityPlayer);
/* 118:152 */     if (!this.theWorld.isClient)
/* 119:    */     {
/* 120:154 */       ItemStack var2 = this.merchantInventory.getStackInSlotOnClosing(0);
/* 121:156 */       if (var2 != null) {
/* 122:158 */         par1EntityPlayer.dropPlayerItemWithRandomChoice(var2, false);
/* 123:    */       }
/* 124:161 */       var2 = this.merchantInventory.getStackInSlotOnClosing(1);
/* 125:163 */       if (var2 != null) {
/* 126:165 */         par1EntityPlayer.dropPlayerItemWithRandomChoice(var2, false);
/* 127:    */       }
/* 128:    */     }
/* 129:    */   }
/* 130:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.inventory.ContainerMerchant
 * JD-Core Version:    0.7.0.1
 */