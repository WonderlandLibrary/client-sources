/*   1:    */ package net.minecraft.inventory;
/*   2:    */ 
/*   3:    */ import java.util.List;
/*   4:    */ import net.minecraft.entity.passive.EntityHorse;
/*   5:    */ import net.minecraft.entity.player.EntityPlayer;
/*   6:    */ import net.minecraft.init.Items;
/*   7:    */ import net.minecraft.item.ItemStack;
/*   8:    */ 
/*   9:    */ public class ContainerHorseInventory
/*  10:    */   extends Container
/*  11:    */ {
/*  12:    */   private IInventory field_111243_a;
/*  13:    */   private EntityHorse theHorse;
/*  14:    */   private static final String __OBFID = "CL_00001751";
/*  15:    */   
/*  16:    */   public ContainerHorseInventory(IInventory par1IInventory, IInventory par2IInventory, final EntityHorse par3EntityHorse)
/*  17:    */   {
/*  18: 16 */     this.field_111243_a = par2IInventory;
/*  19: 17 */     this.theHorse = par3EntityHorse;
/*  20: 18 */     byte var4 = 3;
/*  21: 19 */     par2IInventory.openInventory();
/*  22: 20 */     int var5 = (var4 - 4) * 18;
/*  23: 21 */     addSlotToContainer(new Slot(par2IInventory, 0, 8, 18)
/*  24:    */     {
/*  25:    */       private static final String __OBFID = "CL_00001752";
/*  26:    */       
/*  27:    */       public boolean isItemValid(ItemStack par1ItemStack)
/*  28:    */       {
/*  29: 26 */         return (super.isItemValid(par1ItemStack)) && (par1ItemStack.getItem() == Items.saddle) && (!getHasStack());
/*  30:    */       }
/*  31: 28 */     });
/*  32: 29 */     addSlotToContainer(new Slot(par2IInventory, 1, 8, 36)
/*  33:    */     {
/*  34:    */       private static final String __OBFID = "CL_00001753";
/*  35:    */       
/*  36:    */       public boolean isItemValid(ItemStack par1ItemStack)
/*  37:    */       {
/*  38: 34 */         return (super.isItemValid(par1ItemStack)) && (par3EntityHorse.func_110259_cr()) && (EntityHorse.func_146085_a(par1ItemStack.getItem()));
/*  39:    */       }
/*  40:    */       
/*  41:    */       public boolean func_111238_b()
/*  42:    */       {
/*  43: 38 */         return par3EntityHorse.func_110259_cr();
/*  44:    */       }
/*  45:    */     });
/*  46: 44 */     if (par3EntityHorse.isChested()) {
/*  47: 46 */       for (int var6 = 0; var6 < var4; var6++) {
/*  48: 48 */         for (int var7 = 0; var7 < 5; var7++) {
/*  49: 50 */           addSlotToContainer(new Slot(par2IInventory, 2 + var7 + var6 * 5, 80 + var7 * 18, 18 + var6 * 18));
/*  50:    */         }
/*  51:    */       }
/*  52:    */     }
/*  53: 55 */     for (int var6 = 0; var6 < 3; var6++) {
/*  54: 57 */       for (int var7 = 0; var7 < 9; var7++) {
/*  55: 59 */         addSlotToContainer(new Slot(par1IInventory, var7 + var6 * 9 + 9, 8 + var7 * 18, 102 + var6 * 18 + var5));
/*  56:    */       }
/*  57:    */     }
/*  58: 63 */     for (var6 = 0; var6 < 9; var6++) {
/*  59: 65 */       addSlotToContainer(new Slot(par1IInventory, var6, 8 + var6 * 18, 160 + var5));
/*  60:    */     }
/*  61:    */   }
/*  62:    */   
/*  63:    */   public boolean canInteractWith(EntityPlayer par1EntityPlayer)
/*  64:    */   {
/*  65: 71 */     return (this.field_111243_a.isUseableByPlayer(par1EntityPlayer)) && (this.theHorse.isEntityAlive()) && (this.theHorse.getDistanceToEntity(par1EntityPlayer) < 8.0F);
/*  66:    */   }
/*  67:    */   
/*  68:    */   public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2)
/*  69:    */   {
/*  70: 79 */     ItemStack var3 = null;
/*  71: 80 */     Slot var4 = (Slot)this.inventorySlots.get(par2);
/*  72: 82 */     if ((var4 != null) && (var4.getHasStack()))
/*  73:    */     {
/*  74: 84 */       ItemStack var5 = var4.getStack();
/*  75: 85 */       var3 = var5.copy();
/*  76: 87 */       if (par2 < this.field_111243_a.getSizeInventory())
/*  77:    */       {
/*  78: 89 */         if (!mergeItemStack(var5, this.field_111243_a.getSizeInventory(), this.inventorySlots.size(), true)) {
/*  79: 91 */           return null;
/*  80:    */         }
/*  81:    */       }
/*  82: 94 */       else if ((getSlot(1).isItemValid(var5)) && (!getSlot(1).getHasStack()))
/*  83:    */       {
/*  84: 96 */         if (!mergeItemStack(var5, 1, 2, false)) {
/*  85: 98 */           return null;
/*  86:    */         }
/*  87:    */       }
/*  88:101 */       else if (getSlot(0).isItemValid(var5))
/*  89:    */       {
/*  90:103 */         if (!mergeItemStack(var5, 0, 1, false)) {
/*  91:105 */           return null;
/*  92:    */         }
/*  93:    */       }
/*  94:108 */       else if ((this.field_111243_a.getSizeInventory() <= 2) || (!mergeItemStack(var5, 2, this.field_111243_a.getSizeInventory(), false))) {
/*  95:110 */         return null;
/*  96:    */       }
/*  97:113 */       if (var5.stackSize == 0) {
/*  98:115 */         var4.putStack(null);
/*  99:    */       } else {
/* 100:119 */         var4.onSlotChanged();
/* 101:    */       }
/* 102:    */     }
/* 103:123 */     return var3;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public void onContainerClosed(EntityPlayer par1EntityPlayer)
/* 107:    */   {
/* 108:131 */     super.onContainerClosed(par1EntityPlayer);
/* 109:132 */     this.field_111243_a.closeInventory();
/* 110:    */   }
/* 111:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.inventory.ContainerHorseInventory
 * JD-Core Version:    0.7.0.1
 */