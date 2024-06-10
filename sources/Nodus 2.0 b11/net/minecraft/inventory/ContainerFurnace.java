/*   1:    */ package net.minecraft.inventory;
/*   2:    */ 
/*   3:    */ import java.util.List;
/*   4:    */ import net.minecraft.entity.player.EntityPlayer;
/*   5:    */ import net.minecraft.entity.player.InventoryPlayer;
/*   6:    */ import net.minecraft.item.ItemStack;
/*   7:    */ import net.minecraft.item.crafting.FurnaceRecipes;
/*   8:    */ import net.minecraft.tileentity.TileEntityFurnace;
/*   9:    */ 
/*  10:    */ public class ContainerFurnace
/*  11:    */   extends Container
/*  12:    */ {
/*  13:    */   private TileEntityFurnace furnace;
/*  14:    */   private int lastCookTime;
/*  15:    */   private int lastBurnTime;
/*  16:    */   private int lastItemBurnTime;
/*  17:    */   private static final String __OBFID = "CL_00001748";
/*  18:    */   
/*  19:    */   public ContainerFurnace(InventoryPlayer par1InventoryPlayer, TileEntityFurnace par2TileEntityFurnace)
/*  20:    */   {
/*  21: 19 */     this.furnace = par2TileEntityFurnace;
/*  22: 20 */     addSlotToContainer(new Slot(par2TileEntityFurnace, 0, 56, 17));
/*  23: 21 */     addSlotToContainer(new Slot(par2TileEntityFurnace, 1, 56, 53));
/*  24: 22 */     addSlotToContainer(new SlotFurnace(par1InventoryPlayer.player, par2TileEntityFurnace, 2, 116, 35));
/*  25: 25 */     for (int var3 = 0; var3 < 3; var3++) {
/*  26: 27 */       for (int var4 = 0; var4 < 9; var4++) {
/*  27: 29 */         addSlotToContainer(new Slot(par1InventoryPlayer, var4 + var3 * 9 + 9, 8 + var4 * 18, 84 + var3 * 18));
/*  28:    */       }
/*  29:    */     }
/*  30: 33 */     for (var3 = 0; var3 < 9; var3++) {
/*  31: 35 */       addSlotToContainer(new Slot(par1InventoryPlayer, var3, 8 + var3 * 18, 142));
/*  32:    */     }
/*  33:    */   }
/*  34:    */   
/*  35:    */   public void addCraftingToCrafters(ICrafting par1ICrafting)
/*  36:    */   {
/*  37: 41 */     super.addCraftingToCrafters(par1ICrafting);
/*  38: 42 */     par1ICrafting.sendProgressBarUpdate(this, 0, this.furnace.field_145961_j);
/*  39: 43 */     par1ICrafting.sendProgressBarUpdate(this, 1, this.furnace.field_145956_a);
/*  40: 44 */     par1ICrafting.sendProgressBarUpdate(this, 2, this.furnace.field_145963_i);
/*  41:    */   }
/*  42:    */   
/*  43:    */   public void detectAndSendChanges()
/*  44:    */   {
/*  45: 52 */     super.detectAndSendChanges();
/*  46: 54 */     for (int var1 = 0; var1 < this.crafters.size(); var1++)
/*  47:    */     {
/*  48: 56 */       ICrafting var2 = (ICrafting)this.crafters.get(var1);
/*  49: 58 */       if (this.lastCookTime != this.furnace.field_145961_j) {
/*  50: 60 */         var2.sendProgressBarUpdate(this, 0, this.furnace.field_145961_j);
/*  51:    */       }
/*  52: 63 */       if (this.lastBurnTime != this.furnace.field_145956_a) {
/*  53: 65 */         var2.sendProgressBarUpdate(this, 1, this.furnace.field_145956_a);
/*  54:    */       }
/*  55: 68 */       if (this.lastItemBurnTime != this.furnace.field_145963_i) {
/*  56: 70 */         var2.sendProgressBarUpdate(this, 2, this.furnace.field_145963_i);
/*  57:    */       }
/*  58:    */     }
/*  59: 74 */     this.lastCookTime = this.furnace.field_145961_j;
/*  60: 75 */     this.lastBurnTime = this.furnace.field_145956_a;
/*  61: 76 */     this.lastItemBurnTime = this.furnace.field_145963_i;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public void updateProgressBar(int par1, int par2)
/*  65:    */   {
/*  66: 81 */     if (par1 == 0) {
/*  67: 83 */       this.furnace.field_145961_j = par2;
/*  68:    */     }
/*  69: 86 */     if (par1 == 1) {
/*  70: 88 */       this.furnace.field_145956_a = par2;
/*  71:    */     }
/*  72: 91 */     if (par1 == 2) {
/*  73: 93 */       this.furnace.field_145963_i = par2;
/*  74:    */     }
/*  75:    */   }
/*  76:    */   
/*  77:    */   public boolean canInteractWith(EntityPlayer par1EntityPlayer)
/*  78:    */   {
/*  79: 99 */     return this.furnace.isUseableByPlayer(par1EntityPlayer);
/*  80:    */   }
/*  81:    */   
/*  82:    */   public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2)
/*  83:    */   {
/*  84:107 */     ItemStack var3 = null;
/*  85:108 */     Slot var4 = (Slot)this.inventorySlots.get(par2);
/*  86:110 */     if ((var4 != null) && (var4.getHasStack()))
/*  87:    */     {
/*  88:112 */       ItemStack var5 = var4.getStack();
/*  89:113 */       var3 = var5.copy();
/*  90:115 */       if (par2 == 2)
/*  91:    */       {
/*  92:117 */         if (!mergeItemStack(var5, 3, 39, true)) {
/*  93:119 */           return null;
/*  94:    */         }
/*  95:122 */         var4.onSlotChange(var5, var3);
/*  96:    */       }
/*  97:124 */       else if ((par2 != 1) && (par2 != 0))
/*  98:    */       {
/*  99:126 */         if (FurnaceRecipes.smelting().func_151395_a(var5) != null)
/* 100:    */         {
/* 101:128 */           if (!mergeItemStack(var5, 0, 1, false)) {
/* 102:130 */             return null;
/* 103:    */           }
/* 104:    */         }
/* 105:133 */         else if (TileEntityFurnace.func_145954_b(var5))
/* 106:    */         {
/* 107:135 */           if (!mergeItemStack(var5, 1, 2, false)) {
/* 108:137 */             return null;
/* 109:    */           }
/* 110:    */         }
/* 111:140 */         else if ((par2 >= 3) && (par2 < 30))
/* 112:    */         {
/* 113:142 */           if (!mergeItemStack(var5, 30, 39, false)) {
/* 114:144 */             return null;
/* 115:    */           }
/* 116:    */         }
/* 117:147 */         else if ((par2 >= 30) && (par2 < 39) && (!mergeItemStack(var5, 3, 30, false))) {
/* 118:149 */           return null;
/* 119:    */         }
/* 120:    */       }
/* 121:152 */       else if (!mergeItemStack(var5, 3, 39, false))
/* 122:    */       {
/* 123:154 */         return null;
/* 124:    */       }
/* 125:157 */       if (var5.stackSize == 0) {
/* 126:159 */         var4.putStack(null);
/* 127:    */       } else {
/* 128:163 */         var4.onSlotChanged();
/* 129:    */       }
/* 130:166 */       if (var5.stackSize == var3.stackSize) {
/* 131:168 */         return null;
/* 132:    */       }
/* 133:171 */       var4.onPickupFromSlot(par1EntityPlayer, var5);
/* 134:    */     }
/* 135:174 */     return var3;
/* 136:    */   }
/* 137:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.inventory.ContainerFurnace
 * JD-Core Version:    0.7.0.1
 */