/*  1:   */ package net.minecraft.inventory;
/*  2:   */ 
/*  3:   */ import java.util.List;
/*  4:   */ import net.minecraft.entity.player.EntityPlayer;
/*  5:   */ import net.minecraft.entity.player.InventoryPlayer;
/*  6:   */ import net.minecraft.item.ItemStack;
/*  7:   */ 
/*  8:   */ public class ContainerHopper
/*  9:   */   extends Container
/* 10:   */ {
/* 11:   */   private final IInventory field_94538_a;
/* 12:   */   private static final String __OBFID = "CL_00001750";
/* 13:   */   
/* 14:   */   public ContainerHopper(InventoryPlayer par1InventoryPlayer, IInventory par2IInventory)
/* 15:   */   {
/* 16:14 */     this.field_94538_a = par2IInventory;
/* 17:15 */     par2IInventory.openInventory();
/* 18:16 */     byte var3 = 51;
/* 19:19 */     for (int var4 = 0; var4 < par2IInventory.getSizeInventory(); var4++) {
/* 20:21 */       addSlotToContainer(new Slot(par2IInventory, var4, 44 + var4 * 18, 20));
/* 21:   */     }
/* 22:24 */     for (var4 = 0; var4 < 3; var4++) {
/* 23:26 */       for (int var5 = 0; var5 < 9; var5++) {
/* 24:28 */         addSlotToContainer(new Slot(par1InventoryPlayer, var5 + var4 * 9 + 9, 8 + var5 * 18, var4 * 18 + var3));
/* 25:   */       }
/* 26:   */     }
/* 27:32 */     for (var4 = 0; var4 < 9; var4++) {
/* 28:34 */       addSlotToContainer(new Slot(par1InventoryPlayer, var4, 8 + var4 * 18, 58 + var3));
/* 29:   */     }
/* 30:   */   }
/* 31:   */   
/* 32:   */   public boolean canInteractWith(EntityPlayer par1EntityPlayer)
/* 33:   */   {
/* 34:40 */     return this.field_94538_a.isUseableByPlayer(par1EntityPlayer);
/* 35:   */   }
/* 36:   */   
/* 37:   */   public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2)
/* 38:   */   {
/* 39:48 */     ItemStack var3 = null;
/* 40:49 */     Slot var4 = (Slot)this.inventorySlots.get(par2);
/* 41:51 */     if ((var4 != null) && (var4.getHasStack()))
/* 42:   */     {
/* 43:53 */       ItemStack var5 = var4.getStack();
/* 44:54 */       var3 = var5.copy();
/* 45:56 */       if (par2 < this.field_94538_a.getSizeInventory())
/* 46:   */       {
/* 47:58 */         if (!mergeItemStack(var5, this.field_94538_a.getSizeInventory(), this.inventorySlots.size(), true)) {
/* 48:60 */           return null;
/* 49:   */         }
/* 50:   */       }
/* 51:63 */       else if (!mergeItemStack(var5, 0, this.field_94538_a.getSizeInventory(), false)) {
/* 52:65 */         return null;
/* 53:   */       }
/* 54:68 */       if (var5.stackSize == 0) {
/* 55:70 */         var4.putStack(null);
/* 56:   */       } else {
/* 57:74 */         var4.onSlotChanged();
/* 58:   */       }
/* 59:   */     }
/* 60:78 */     return var3;
/* 61:   */   }
/* 62:   */   
/* 63:   */   public void onContainerClosed(EntityPlayer par1EntityPlayer)
/* 64:   */   {
/* 65:86 */     super.onContainerClosed(par1EntityPlayer);
/* 66:87 */     this.field_94538_a.closeInventory();
/* 67:   */   }
/* 68:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.inventory.ContainerHopper
 * JD-Core Version:    0.7.0.1
 */