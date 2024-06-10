/*  1:   */ package net.minecraft.inventory;
/*  2:   */ 
/*  3:   */ import java.util.List;
/*  4:   */ import net.minecraft.entity.player.EntityPlayer;
/*  5:   */ import net.minecraft.item.ItemStack;
/*  6:   */ import net.minecraft.tileentity.TileEntityDispenser;
/*  7:   */ 
/*  8:   */ public class ContainerDispenser
/*  9:   */   extends Container
/* 10:   */ {
/* 11:   */   private TileEntityDispenser tileEntityDispenser;
/* 12:   */   private static final String __OBFID = "CL_00001763";
/* 13:   */   
/* 14:   */   public ContainerDispenser(IInventory par1IInventory, TileEntityDispenser par2TileEntityDispenser)
/* 15:   */   {
/* 16:14 */     this.tileEntityDispenser = par2TileEntityDispenser;
/* 17:18 */     for (int var3 = 0; var3 < 3; var3++) {
/* 18:20 */       for (int var4 = 0; var4 < 3; var4++) {
/* 19:22 */         addSlotToContainer(new Slot(par2TileEntityDispenser, var4 + var3 * 3, 62 + var4 * 18, 17 + var3 * 18));
/* 20:   */       }
/* 21:   */     }
/* 22:26 */     for (var3 = 0; var3 < 3; var3++) {
/* 23:28 */       for (int var4 = 0; var4 < 9; var4++) {
/* 24:30 */         addSlotToContainer(new Slot(par1IInventory, var4 + var3 * 9 + 9, 8 + var4 * 18, 84 + var3 * 18));
/* 25:   */       }
/* 26:   */     }
/* 27:34 */     for (var3 = 0; var3 < 9; var3++) {
/* 28:36 */       addSlotToContainer(new Slot(par1IInventory, var3, 8 + var3 * 18, 142));
/* 29:   */     }
/* 30:   */   }
/* 31:   */   
/* 32:   */   public boolean canInteractWith(EntityPlayer par1EntityPlayer)
/* 33:   */   {
/* 34:42 */     return this.tileEntityDispenser.isUseableByPlayer(par1EntityPlayer);
/* 35:   */   }
/* 36:   */   
/* 37:   */   public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2)
/* 38:   */   {
/* 39:50 */     ItemStack var3 = null;
/* 40:51 */     Slot var4 = (Slot)this.inventorySlots.get(par2);
/* 41:53 */     if ((var4 != null) && (var4.getHasStack()))
/* 42:   */     {
/* 43:55 */       ItemStack var5 = var4.getStack();
/* 44:56 */       var3 = var5.copy();
/* 45:58 */       if (par2 < 9)
/* 46:   */       {
/* 47:60 */         if (!mergeItemStack(var5, 9, 45, true)) {
/* 48:62 */           return null;
/* 49:   */         }
/* 50:   */       }
/* 51:65 */       else if (!mergeItemStack(var5, 0, 9, false)) {
/* 52:67 */         return null;
/* 53:   */       }
/* 54:70 */       if (var5.stackSize == 0) {
/* 55:72 */         var4.putStack(null);
/* 56:   */       } else {
/* 57:76 */         var4.onSlotChanged();
/* 58:   */       }
/* 59:79 */       if (var5.stackSize == var3.stackSize) {
/* 60:81 */         return null;
/* 61:   */       }
/* 62:84 */       var4.onPickupFromSlot(par1EntityPlayer, var5);
/* 63:   */     }
/* 64:87 */     return var3;
/* 65:   */   }
/* 66:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.inventory.ContainerDispenser
 * JD-Core Version:    0.7.0.1
 */