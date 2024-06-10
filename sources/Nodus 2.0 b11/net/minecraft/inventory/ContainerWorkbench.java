/*   1:    */ package net.minecraft.inventory;
/*   2:    */ 
/*   3:    */ import java.util.List;
/*   4:    */ import net.minecraft.entity.player.EntityPlayer;
/*   5:    */ import net.minecraft.entity.player.InventoryPlayer;
/*   6:    */ import net.minecraft.init.Blocks;
/*   7:    */ import net.minecraft.item.ItemStack;
/*   8:    */ import net.minecraft.item.crafting.CraftingManager;
/*   9:    */ import net.minecraft.world.World;
/*  10:    */ 
/*  11:    */ public class ContainerWorkbench
/*  12:    */   extends Container
/*  13:    */ {
/*  14: 13 */   public InventoryCrafting craftMatrix = new InventoryCrafting(this, 3, 3);
/*  15: 14 */   public IInventory craftResult = new InventoryCraftResult();
/*  16:    */   private World worldObj;
/*  17:    */   private int posX;
/*  18:    */   private int posY;
/*  19:    */   private int posZ;
/*  20:    */   private static final String __OBFID = "CL_00001744";
/*  21:    */   
/*  22:    */   public ContainerWorkbench(InventoryPlayer par1InventoryPlayer, World par2World, int par3, int par4, int par5)
/*  23:    */   {
/*  24: 23 */     this.worldObj = par2World;
/*  25: 24 */     this.posX = par3;
/*  26: 25 */     this.posY = par4;
/*  27: 26 */     this.posZ = par5;
/*  28: 27 */     addSlotToContainer(new SlotCrafting(par1InventoryPlayer.player, this.craftMatrix, this.craftResult, 0, 124, 35));
/*  29: 31 */     for (int var6 = 0; var6 < 3; var6++) {
/*  30: 33 */       for (int var7 = 0; var7 < 3; var7++) {
/*  31: 35 */         addSlotToContainer(new Slot(this.craftMatrix, var7 + var6 * 3, 30 + var7 * 18, 17 + var6 * 18));
/*  32:    */       }
/*  33:    */     }
/*  34: 39 */     for (var6 = 0; var6 < 3; var6++) {
/*  35: 41 */       for (int var7 = 0; var7 < 9; var7++) {
/*  36: 43 */         addSlotToContainer(new Slot(par1InventoryPlayer, var7 + var6 * 9 + 9, 8 + var7 * 18, 84 + var6 * 18));
/*  37:    */       }
/*  38:    */     }
/*  39: 47 */     for (var6 = 0; var6 < 9; var6++) {
/*  40: 49 */       addSlotToContainer(new Slot(par1InventoryPlayer, var6, 8 + var6 * 18, 142));
/*  41:    */     }
/*  42: 52 */     onCraftMatrixChanged(this.craftMatrix);
/*  43:    */   }
/*  44:    */   
/*  45:    */   public void onCraftMatrixChanged(IInventory par1IInventory)
/*  46:    */   {
/*  47: 60 */     this.craftResult.setInventorySlotContents(0, CraftingManager.getInstance().findMatchingRecipe(this.craftMatrix, this.worldObj));
/*  48:    */   }
/*  49:    */   
/*  50:    */   public void onContainerClosed(EntityPlayer par1EntityPlayer)
/*  51:    */   {
/*  52: 68 */     super.onContainerClosed(par1EntityPlayer);
/*  53: 70 */     if (!this.worldObj.isClient) {
/*  54: 72 */       for (int var2 = 0; var2 < 9; var2++)
/*  55:    */       {
/*  56: 74 */         ItemStack var3 = this.craftMatrix.getStackInSlotOnClosing(var2);
/*  57: 76 */         if (var3 != null) {
/*  58: 78 */           par1EntityPlayer.dropPlayerItemWithRandomChoice(var3, false);
/*  59:    */         }
/*  60:    */       }
/*  61:    */     }
/*  62:    */   }
/*  63:    */   
/*  64:    */   public boolean canInteractWith(EntityPlayer par1EntityPlayer)
/*  65:    */   {
/*  66: 86 */     return this.worldObj.getBlock(this.posX, this.posY, this.posZ) == Blocks.crafting_table;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2)
/*  70:    */   {
/*  71: 94 */     ItemStack var3 = null;
/*  72: 95 */     Slot var4 = (Slot)this.inventorySlots.get(par2);
/*  73: 97 */     if ((var4 != null) && (var4.getHasStack()))
/*  74:    */     {
/*  75: 99 */       ItemStack var5 = var4.getStack();
/*  76:100 */       var3 = var5.copy();
/*  77:102 */       if (par2 == 0)
/*  78:    */       {
/*  79:104 */         if (!mergeItemStack(var5, 10, 46, true)) {
/*  80:106 */           return null;
/*  81:    */         }
/*  82:109 */         var4.onSlotChange(var5, var3);
/*  83:    */       }
/*  84:111 */       else if ((par2 >= 10) && (par2 < 37))
/*  85:    */       {
/*  86:113 */         if (!mergeItemStack(var5, 37, 46, false)) {
/*  87:115 */           return null;
/*  88:    */         }
/*  89:    */       }
/*  90:118 */       else if ((par2 >= 37) && (par2 < 46))
/*  91:    */       {
/*  92:120 */         if (!mergeItemStack(var5, 10, 37, false)) {
/*  93:122 */           return null;
/*  94:    */         }
/*  95:    */       }
/*  96:125 */       else if (!mergeItemStack(var5, 10, 46, false))
/*  97:    */       {
/*  98:127 */         return null;
/*  99:    */       }
/* 100:130 */       if (var5.stackSize == 0) {
/* 101:132 */         var4.putStack(null);
/* 102:    */       } else {
/* 103:136 */         var4.onSlotChanged();
/* 104:    */       }
/* 105:139 */       if (var5.stackSize == var3.stackSize) {
/* 106:141 */         return null;
/* 107:    */       }
/* 108:144 */       var4.onPickupFromSlot(par1EntityPlayer, var5);
/* 109:    */     }
/* 110:147 */     return var3;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public boolean func_94530_a(ItemStack par1ItemStack, Slot par2Slot)
/* 114:    */   {
/* 115:152 */     return (par2Slot.inventory != this.craftResult) && (super.func_94530_a(par1ItemStack, par2Slot));
/* 116:    */   }
/* 117:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.inventory.ContainerWorkbench
 * JD-Core Version:    0.7.0.1
 */