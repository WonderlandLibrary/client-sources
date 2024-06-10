/*   1:    */ package net.minecraft.inventory;
/*   2:    */ 
/*   3:    */ import java.util.List;
/*   4:    */ import net.minecraft.entity.player.EntityPlayer;
/*   5:    */ import net.minecraft.item.ItemStack;
/*   6:    */ 
/*   7:    */ public class ContainerChest
/*   8:    */   extends Container
/*   9:    */ {
/*  10:    */   private IInventory lowerChestInventory;
/*  11:    */   private int numRows;
/*  12:    */   private static final String __OBFID = "CL_00001742";
/*  13:    */   
/*  14:    */   public ContainerChest(IInventory par1IInventory, IInventory par2IInventory)
/*  15:    */   {
/*  16: 14 */     this.lowerChestInventory = par2IInventory;
/*  17: 15 */     this.numRows = (par2IInventory.getSizeInventory() / 9);
/*  18: 16 */     par2IInventory.openInventory();
/*  19: 17 */     int var3 = (this.numRows - 4) * 18;
/*  20: 21 */     for (int var4 = 0; var4 < this.numRows; var4++) {
/*  21: 23 */       for (int var5 = 0; var5 < 9; var5++) {
/*  22: 25 */         addSlotToContainer(new Slot(par2IInventory, var5 + var4 * 9, 8 + var5 * 18, 18 + var4 * 18));
/*  23:    */       }
/*  24:    */     }
/*  25: 29 */     for (var4 = 0; var4 < 3; var4++) {
/*  26: 31 */       for (int var5 = 0; var5 < 9; var5++) {
/*  27: 33 */         addSlotToContainer(new Slot(par1IInventory, var5 + var4 * 9 + 9, 8 + var5 * 18, 103 + var4 * 18 + var3));
/*  28:    */       }
/*  29:    */     }
/*  30: 37 */     for (var4 = 0; var4 < 9; var4++) {
/*  31: 39 */       addSlotToContainer(new Slot(par1IInventory, var4, 8 + var4 * 18, 161 + var3));
/*  32:    */     }
/*  33:    */   }
/*  34:    */   
/*  35:    */   public boolean canInteractWith(EntityPlayer par1EntityPlayer)
/*  36:    */   {
/*  37: 45 */     return this.lowerChestInventory.isUseableByPlayer(par1EntityPlayer);
/*  38:    */   }
/*  39:    */   
/*  40:    */   public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2)
/*  41:    */   {
/*  42: 53 */     ItemStack var3 = null;
/*  43: 54 */     Slot var4 = (Slot)this.inventorySlots.get(par2);
/*  44: 56 */     if ((var4 != null) && (var4.getHasStack()))
/*  45:    */     {
/*  46: 58 */       ItemStack var5 = var4.getStack();
/*  47: 59 */       var3 = var5.copy();
/*  48: 61 */       if (par2 < this.numRows * 9)
/*  49:    */       {
/*  50: 63 */         if (!mergeItemStack(var5, this.numRows * 9, this.inventorySlots.size(), true)) {
/*  51: 65 */           return null;
/*  52:    */         }
/*  53:    */       }
/*  54: 68 */       else if (!mergeItemStack(var5, 0, this.numRows * 9, false)) {
/*  55: 70 */         return null;
/*  56:    */       }
/*  57: 73 */       if (var5.stackSize == 0) {
/*  58: 75 */         var4.putStack(null);
/*  59:    */       } else {
/*  60: 79 */         var4.onSlotChanged();
/*  61:    */       }
/*  62:    */     }
/*  63: 83 */     return var3;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public void onContainerClosed(EntityPlayer par1EntityPlayer)
/*  67:    */   {
/*  68: 91 */     super.onContainerClosed(par1EntityPlayer);
/*  69: 92 */     this.lowerChestInventory.closeInventory();
/*  70:    */   }
/*  71:    */   
/*  72:    */   public IInventory getLowerChestInventory()
/*  73:    */   {
/*  74:100 */     return this.lowerChestInventory;
/*  75:    */   }
/*  76:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.inventory.ContainerChest
 * JD-Core Version:    0.7.0.1
 */