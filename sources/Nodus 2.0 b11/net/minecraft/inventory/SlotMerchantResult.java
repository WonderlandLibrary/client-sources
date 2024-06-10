/*   1:    */ package net.minecraft.inventory;
/*   2:    */ 
/*   3:    */ import net.minecraft.entity.IMerchant;
/*   4:    */ import net.minecraft.entity.player.EntityPlayer;
/*   5:    */ import net.minecraft.item.ItemStack;
/*   6:    */ import net.minecraft.village.MerchantRecipe;
/*   7:    */ 
/*   8:    */ public class SlotMerchantResult
/*   9:    */   extends Slot
/*  10:    */ {
/*  11:    */   private final InventoryMerchant theMerchantInventory;
/*  12:    */   private EntityPlayer thePlayer;
/*  13:    */   private int field_75231_g;
/*  14:    */   private final IMerchant theMerchant;
/*  15:    */   private static final String __OBFID = "CL_00001758";
/*  16:    */   
/*  17:    */   public SlotMerchantResult(EntityPlayer par1EntityPlayer, IMerchant par2IMerchant, InventoryMerchant par3InventoryMerchant, int par4, int par5, int par6)
/*  18:    */   {
/*  19: 23 */     super(par3InventoryMerchant, par4, par5, par6);
/*  20: 24 */     this.thePlayer = par1EntityPlayer;
/*  21: 25 */     this.theMerchant = par2IMerchant;
/*  22: 26 */     this.theMerchantInventory = par3InventoryMerchant;
/*  23:    */   }
/*  24:    */   
/*  25:    */   public boolean isItemValid(ItemStack par1ItemStack)
/*  26:    */   {
/*  27: 34 */     return false;
/*  28:    */   }
/*  29:    */   
/*  30:    */   public ItemStack decrStackSize(int par1)
/*  31:    */   {
/*  32: 43 */     if (getHasStack()) {
/*  33: 45 */       this.field_75231_g += Math.min(par1, getStack().stackSize);
/*  34:    */     }
/*  35: 48 */     return super.decrStackSize(par1);
/*  36:    */   }
/*  37:    */   
/*  38:    */   protected void onCrafting(ItemStack par1ItemStack, int par2)
/*  39:    */   {
/*  40: 57 */     this.field_75231_g += par2;
/*  41: 58 */     onCrafting(par1ItemStack);
/*  42:    */   }
/*  43:    */   
/*  44:    */   protected void onCrafting(ItemStack par1ItemStack)
/*  45:    */   {
/*  46: 66 */     par1ItemStack.onCrafting(this.thePlayer.worldObj, this.thePlayer, this.field_75231_g);
/*  47: 67 */     this.field_75231_g = 0;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public void onPickupFromSlot(EntityPlayer par1EntityPlayer, ItemStack par2ItemStack)
/*  51:    */   {
/*  52: 72 */     onCrafting(par2ItemStack);
/*  53: 73 */     MerchantRecipe var3 = this.theMerchantInventory.getCurrentRecipe();
/*  54: 75 */     if (var3 != null)
/*  55:    */     {
/*  56: 77 */       ItemStack var4 = this.theMerchantInventory.getStackInSlot(0);
/*  57: 78 */       ItemStack var5 = this.theMerchantInventory.getStackInSlot(1);
/*  58: 80 */       if ((func_75230_a(var3, var4, var5)) || (func_75230_a(var3, var5, var4)))
/*  59:    */       {
/*  60: 82 */         this.theMerchant.useRecipe(var3);
/*  61: 84 */         if ((var4 != null) && (var4.stackSize <= 0)) {
/*  62: 86 */           var4 = null;
/*  63:    */         }
/*  64: 89 */         if ((var5 != null) && (var5.stackSize <= 0)) {
/*  65: 91 */           var5 = null;
/*  66:    */         }
/*  67: 94 */         this.theMerchantInventory.setInventorySlotContents(0, var4);
/*  68: 95 */         this.theMerchantInventory.setInventorySlotContents(1, var5);
/*  69:    */       }
/*  70:    */     }
/*  71:    */   }
/*  72:    */   
/*  73:    */   private boolean func_75230_a(MerchantRecipe par1MerchantRecipe, ItemStack par2ItemStack, ItemStack par3ItemStack)
/*  74:    */   {
/*  75:102 */     ItemStack var4 = par1MerchantRecipe.getItemToBuy();
/*  76:103 */     ItemStack var5 = par1MerchantRecipe.getSecondItemToBuy();
/*  77:105 */     if ((par2ItemStack != null) && (par2ItemStack.getItem() == var4.getItem()))
/*  78:    */     {
/*  79:107 */       if ((var5 != null) && (par3ItemStack != null) && (var5.getItem() == par3ItemStack.getItem()))
/*  80:    */       {
/*  81:109 */         par2ItemStack.stackSize -= var4.stackSize;
/*  82:110 */         par3ItemStack.stackSize -= var5.stackSize;
/*  83:111 */         return true;
/*  84:    */       }
/*  85:114 */       if ((var5 == null) && (par3ItemStack == null))
/*  86:    */       {
/*  87:116 */         par2ItemStack.stackSize -= var4.stackSize;
/*  88:117 */         return true;
/*  89:    */       }
/*  90:    */     }
/*  91:121 */     return false;
/*  92:    */   }
/*  93:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.inventory.SlotMerchantResult
 * JD-Core Version:    0.7.0.1
 */