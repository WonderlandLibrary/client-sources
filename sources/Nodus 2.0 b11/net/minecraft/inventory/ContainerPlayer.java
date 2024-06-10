/*   1:    */ package net.minecraft.inventory;
/*   2:    */ 
/*   3:    */ import java.util.List;
/*   4:    */ import net.minecraft.entity.player.EntityPlayer;
/*   5:    */ import net.minecraft.entity.player.InventoryPlayer;
/*   6:    */ import net.minecraft.item.ItemArmor;
/*   7:    */ import net.minecraft.item.ItemStack;
/*   8:    */ import net.minecraft.item.crafting.CraftingManager;
/*   9:    */ import net.minecraft.util.IIcon;
/*  10:    */ 
/*  11:    */ public class ContainerPlayer
/*  12:    */   extends Container
/*  13:    */ {
/*  14: 16 */   public InventoryCrafting craftMatrix = new InventoryCrafting(this, 2, 2);
/*  15: 17 */   public IInventory craftResult = new InventoryCraftResult();
/*  16:    */   public boolean isLocalWorld;
/*  17:    */   private final EntityPlayer thePlayer;
/*  18:    */   private static final String __OBFID = "CL_00001754";
/*  19:    */   
/*  20:    */   public ContainerPlayer(InventoryPlayer par1InventoryPlayer, boolean par2, EntityPlayer par3EntityPlayer)
/*  21:    */   {
/*  22: 26 */     this.isLocalWorld = par2;
/*  23: 27 */     this.thePlayer = par3EntityPlayer;
/*  24: 28 */     addSlotToContainer(new SlotCrafting(par1InventoryPlayer.player, this.craftMatrix, this.craftResult, 0, 144, 36));
/*  25: 32 */     for (int var4 = 0; var4 < 2; var4++) {
/*  26: 34 */       for (int var5 = 0; var5 < 2; var5++) {
/*  27: 36 */         addSlotToContainer(new Slot(this.craftMatrix, var5 + var4 * 2, 88 + var5 * 18, 26 + var4 * 18));
/*  28:    */       }
/*  29:    */     }
/*  30: 40 */     for (var4 = 0; var4 < 4; var4++)
/*  31:    */     {
/*  32: 42 */       final int var44 = var4;
/*  33: 43 */       addSlotToContainer(new Slot(par1InventoryPlayer, par1InventoryPlayer.getSizeInventory() - 1 - var4, 8, 8 + var4 * 18)
/*  34:    */       {
/*  35:    */         private static final String __OBFID = "CL_00001755";
/*  36:    */         
/*  37:    */         public int getSlotStackLimit()
/*  38:    */         {
/*  39: 48 */           return 1;
/*  40:    */         }
/*  41:    */         
/*  42:    */         public boolean isItemValid(ItemStack par1ItemStack)
/*  43:    */         {
/*  44: 52 */           return par1ItemStack != null;
/*  45:    */         }
/*  46:    */         
/*  47:    */         public IIcon getBackgroundIconIndex()
/*  48:    */         {
/*  49: 56 */           return ItemArmor.func_94602_b(var44);
/*  50:    */         }
/*  51:    */       });
/*  52:    */     }
/*  53: 61 */     for (var4 = 0; var4 < 3; var4++) {
/*  54: 63 */       for (int var5 = 0; var5 < 9; var5++) {
/*  55: 65 */         addSlotToContainer(new Slot(par1InventoryPlayer, var5 + (var4 + 1) * 9, 8 + var5 * 18, 84 + var4 * 18));
/*  56:    */       }
/*  57:    */     }
/*  58: 69 */     for (var4 = 0; var4 < 9; var4++) {
/*  59: 71 */       addSlotToContainer(new Slot(par1InventoryPlayer, var4, 8 + var4 * 18, 142));
/*  60:    */     }
/*  61: 74 */     onCraftMatrixChanged(this.craftMatrix);
/*  62:    */   }
/*  63:    */   
/*  64:    */   public void onCraftMatrixChanged(IInventory par1IInventory)
/*  65:    */   {
/*  66: 82 */     this.craftResult.setInventorySlotContents(0, CraftingManager.getInstance().findMatchingRecipe(this.craftMatrix, this.thePlayer.worldObj));
/*  67:    */   }
/*  68:    */   
/*  69:    */   public void onContainerClosed(EntityPlayer par1EntityPlayer)
/*  70:    */   {
/*  71: 90 */     super.onContainerClosed(par1EntityPlayer);
/*  72: 92 */     for (int var2 = 0; var2 < 4; var2++)
/*  73:    */     {
/*  74: 94 */       ItemStack var3 = this.craftMatrix.getStackInSlotOnClosing(var2);
/*  75: 96 */       if (var3 != null) {
/*  76: 98 */         par1EntityPlayer.dropPlayerItemWithRandomChoice(var3, false);
/*  77:    */       }
/*  78:    */     }
/*  79:102 */     this.craftResult.setInventorySlotContents(0, null);
/*  80:    */   }
/*  81:    */   
/*  82:    */   public boolean canInteractWith(EntityPlayer par1EntityPlayer)
/*  83:    */   {
/*  84:107 */     return true;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2)
/*  88:    */   {
/*  89:115 */     ItemStack var3 = null;
/*  90:116 */     Slot var4 = (Slot)this.inventorySlots.get(par2);
/*  91:118 */     if ((var4 != null) && (var4.getHasStack()))
/*  92:    */     {
/*  93:120 */       ItemStack var5 = var4.getStack();
/*  94:121 */       var3 = var5.copy();
/*  95:123 */       if (par2 == 0)
/*  96:    */       {
/*  97:125 */         if (!mergeItemStack(var5, 9, 45, true)) {
/*  98:127 */           return null;
/*  99:    */         }
/* 100:130 */         var4.onSlotChange(var5, var3);
/* 101:    */       }
/* 102:132 */       else if ((par2 >= 1) && (par2 < 5))
/* 103:    */       {
/* 104:134 */         if (!mergeItemStack(var5, 9, 45, false)) {
/* 105:136 */           return null;
/* 106:    */         }
/* 107:    */       }
/* 108:139 */       else if ((par2 >= 5) && (par2 < 9))
/* 109:    */       {
/* 110:141 */         if (!mergeItemStack(var5, 9, 45, false)) {
/* 111:143 */           return null;
/* 112:    */         }
/* 113:    */       }
/* 114:146 */       else if (((var3.getItem() instanceof ItemArmor)) && (!((Slot)this.inventorySlots.get(5 + ((ItemArmor)var3.getItem()).armorType)).getHasStack()))
/* 115:    */       {
/* 116:148 */         int var6 = 5 + ((ItemArmor)var3.getItem()).armorType;
/* 117:150 */         if (!mergeItemStack(var5, var6, var6 + 1, false)) {
/* 118:152 */           return null;
/* 119:    */         }
/* 120:    */       }
/* 121:155 */       else if ((par2 >= 9) && (par2 < 36))
/* 122:    */       {
/* 123:157 */         if (!mergeItemStack(var5, 36, 45, false)) {
/* 124:159 */           return null;
/* 125:    */         }
/* 126:    */       }
/* 127:162 */       else if ((par2 >= 36) && (par2 < 45))
/* 128:    */       {
/* 129:164 */         if (!mergeItemStack(var5, 9, 36, false)) {
/* 130:166 */           return null;
/* 131:    */         }
/* 132:    */       }
/* 133:169 */       else if (!mergeItemStack(var5, 9, 45, false))
/* 134:    */       {
/* 135:171 */         return null;
/* 136:    */       }
/* 137:174 */       if (var5.stackSize == 0) {
/* 138:176 */         var4.putStack(null);
/* 139:    */       } else {
/* 140:180 */         var4.onSlotChanged();
/* 141:    */       }
/* 142:183 */       if (var5.stackSize == var3.stackSize) {
/* 143:185 */         return null;
/* 144:    */       }
/* 145:188 */       var4.onPickupFromSlot(par1EntityPlayer, var5);
/* 146:    */     }
/* 147:191 */     return var3;
/* 148:    */   }
/* 149:    */   
/* 150:    */   public boolean func_94530_a(ItemStack par1ItemStack, Slot par2Slot)
/* 151:    */   {
/* 152:196 */     return (par2Slot.inventory != this.craftResult) && (super.func_94530_a(par1ItemStack, par2Slot));
/* 153:    */   }
/* 154:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.inventory.ContainerPlayer
 * JD-Core Version:    0.7.0.1
 */