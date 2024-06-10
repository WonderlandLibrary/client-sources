/*   1:    */ package net.minecraft.inventory;
/*   2:    */ 
/*   3:    */ import net.minecraft.entity.player.EntityPlayer;
/*   4:    */ import net.minecraft.item.ItemStack;
/*   5:    */ import net.minecraft.util.IIcon;
/*   6:    */ 
/*   7:    */ public class Slot
/*   8:    */ {
/*   9:    */   private final int slotIndex;
/*  10:    */   public final IInventory inventory;
/*  11:    */   public int slotNumber;
/*  12:    */   public int xDisplayPosition;
/*  13:    */   public int yDisplayPosition;
/*  14:    */   private static final String __OBFID = "CL_00001762";
/*  15:    */   
/*  16:    */   public Slot(IInventory par1IInventory, int par2, int par3, int par4)
/*  17:    */   {
/*  18: 27 */     this.inventory = par1IInventory;
/*  19: 28 */     this.slotIndex = par2;
/*  20: 29 */     this.xDisplayPosition = par3;
/*  21: 30 */     this.yDisplayPosition = par4;
/*  22:    */   }
/*  23:    */   
/*  24:    */   public void onSlotChange(ItemStack par1ItemStack, ItemStack par2ItemStack)
/*  25:    */   {
/*  26: 38 */     if ((par1ItemStack != null) && (par2ItemStack != null)) {
/*  27: 40 */       if (par1ItemStack.getItem() == par2ItemStack.getItem())
/*  28:    */       {
/*  29: 42 */         int var3 = par2ItemStack.stackSize - par1ItemStack.stackSize;
/*  30: 44 */         if (var3 > 0) {
/*  31: 46 */           onCrafting(par1ItemStack, var3);
/*  32:    */         }
/*  33:    */       }
/*  34:    */     }
/*  35:    */   }
/*  36:    */   
/*  37:    */   protected void onCrafting(ItemStack par1ItemStack, int par2) {}
/*  38:    */   
/*  39:    */   protected void onCrafting(ItemStack par1ItemStack) {}
/*  40:    */   
/*  41:    */   public void onPickupFromSlot(EntityPlayer par1EntityPlayer, ItemStack par2ItemStack)
/*  42:    */   {
/*  43: 65 */     onSlotChanged();
/*  44:    */   }
/*  45:    */   
/*  46:    */   public boolean isItemValid(ItemStack par1ItemStack)
/*  47:    */   {
/*  48: 73 */     return true;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public ItemStack getStack()
/*  52:    */   {
/*  53: 81 */     return this.inventory.getStackInSlot(this.slotIndex);
/*  54:    */   }
/*  55:    */   
/*  56:    */   public boolean getHasStack()
/*  57:    */   {
/*  58: 89 */     return getStack() != null;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public void putStack(ItemStack par1ItemStack)
/*  62:    */   {
/*  63: 97 */     this.inventory.setInventorySlotContents(this.slotIndex, par1ItemStack);
/*  64: 98 */     onSlotChanged();
/*  65:    */   }
/*  66:    */   
/*  67:    */   public void onSlotChanged()
/*  68:    */   {
/*  69:106 */     this.inventory.onInventoryChanged();
/*  70:    */   }
/*  71:    */   
/*  72:    */   public int getSlotStackLimit()
/*  73:    */   {
/*  74:115 */     return this.inventory.getInventoryStackLimit();
/*  75:    */   }
/*  76:    */   
/*  77:    */   public IIcon getBackgroundIconIndex()
/*  78:    */   {
/*  79:123 */     return null;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public ItemStack decrStackSize(int par1)
/*  83:    */   {
/*  84:132 */     return this.inventory.decrStackSize(this.slotIndex, par1);
/*  85:    */   }
/*  86:    */   
/*  87:    */   public boolean isSlotInInventory(IInventory par1IInventory, int par2)
/*  88:    */   {
/*  89:140 */     return (par1IInventory == this.inventory) && (par2 == this.slotIndex);
/*  90:    */   }
/*  91:    */   
/*  92:    */   public boolean canTakeStack(EntityPlayer par1EntityPlayer)
/*  93:    */   {
/*  94:148 */     return true;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public boolean func_111238_b()
/*  98:    */   {
/*  99:153 */     return true;
/* 100:    */   }
/* 101:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.inventory.Slot
 * JD-Core Version:    0.7.0.1
 */