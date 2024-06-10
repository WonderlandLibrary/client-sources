/*   1:    */ package net.minecraft.inventory;
/*   2:    */ 
/*   3:    */ import net.minecraft.entity.player.EntityPlayer;
/*   4:    */ import net.minecraft.entity.player.InventoryPlayer;
/*   5:    */ import net.minecraft.init.Blocks;
/*   6:    */ import net.minecraft.init.Items;
/*   7:    */ import net.minecraft.item.Item;
/*   8:    */ import net.minecraft.item.Item.ToolMaterial;
/*   9:    */ import net.minecraft.item.ItemHoe;
/*  10:    */ import net.minecraft.item.ItemPickaxe;
/*  11:    */ import net.minecraft.item.ItemStack;
/*  12:    */ import net.minecraft.item.ItemSword;
/*  13:    */ import net.minecraft.stats.AchievementList;
/*  14:    */ 
/*  15:    */ public class SlotCrafting
/*  16:    */   extends Slot
/*  17:    */ {
/*  18:    */   private final IInventory craftMatrix;
/*  19:    */   private EntityPlayer thePlayer;
/*  20:    */   private int amountCrafted;
/*  21:    */   private static final String __OBFID = "CL_00001761";
/*  22:    */   
/*  23:    */   public SlotCrafting(EntityPlayer par1EntityPlayer, IInventory par2IInventory, IInventory par3IInventory, int par4, int par5, int par6)
/*  24:    */   {
/*  25: 29 */     super(par3IInventory, par4, par5, par6);
/*  26: 30 */     this.thePlayer = par1EntityPlayer;
/*  27: 31 */     this.craftMatrix = par2IInventory;
/*  28:    */   }
/*  29:    */   
/*  30:    */   public boolean isItemValid(ItemStack par1ItemStack)
/*  31:    */   {
/*  32: 39 */     return false;
/*  33:    */   }
/*  34:    */   
/*  35:    */   public ItemStack decrStackSize(int par1)
/*  36:    */   {
/*  37: 48 */     if (getHasStack()) {
/*  38: 50 */       this.amountCrafted += Math.min(par1, getStack().stackSize);
/*  39:    */     }
/*  40: 53 */     return super.decrStackSize(par1);
/*  41:    */   }
/*  42:    */   
/*  43:    */   protected void onCrafting(ItemStack par1ItemStack, int par2)
/*  44:    */   {
/*  45: 62 */     this.amountCrafted += par2;
/*  46: 63 */     onCrafting(par1ItemStack);
/*  47:    */   }
/*  48:    */   
/*  49:    */   protected void onCrafting(ItemStack par1ItemStack)
/*  50:    */   {
/*  51: 71 */     par1ItemStack.onCrafting(this.thePlayer.worldObj, this.thePlayer, this.amountCrafted);
/*  52: 72 */     this.amountCrafted = 0;
/*  53: 74 */     if (par1ItemStack.getItem() == Item.getItemFromBlock(Blocks.crafting_table)) {
/*  54: 76 */       this.thePlayer.addStat(AchievementList.buildWorkBench, 1);
/*  55:    */     }
/*  56: 79 */     if ((par1ItemStack.getItem() instanceof ItemPickaxe)) {
/*  57: 81 */       this.thePlayer.addStat(AchievementList.buildPickaxe, 1);
/*  58:    */     }
/*  59: 84 */     if (par1ItemStack.getItem() == Item.getItemFromBlock(Blocks.furnace)) {
/*  60: 86 */       this.thePlayer.addStat(AchievementList.buildFurnace, 1);
/*  61:    */     }
/*  62: 89 */     if ((par1ItemStack.getItem() instanceof ItemHoe)) {
/*  63: 91 */       this.thePlayer.addStat(AchievementList.buildHoe, 1);
/*  64:    */     }
/*  65: 94 */     if (par1ItemStack.getItem() == Items.bread) {
/*  66: 96 */       this.thePlayer.addStat(AchievementList.makeBread, 1);
/*  67:    */     }
/*  68: 99 */     if (par1ItemStack.getItem() == Items.cake) {
/*  69:101 */       this.thePlayer.addStat(AchievementList.bakeCake, 1);
/*  70:    */     }
/*  71:104 */     if (((par1ItemStack.getItem() instanceof ItemPickaxe)) && (((ItemPickaxe)par1ItemStack.getItem()).func_150913_i() != Item.ToolMaterial.WOOD)) {
/*  72:106 */       this.thePlayer.addStat(AchievementList.buildBetterPickaxe, 1);
/*  73:    */     }
/*  74:109 */     if ((par1ItemStack.getItem() instanceof ItemSword)) {
/*  75:111 */       this.thePlayer.addStat(AchievementList.buildSword, 1);
/*  76:    */     }
/*  77:114 */     if (par1ItemStack.getItem() == Item.getItemFromBlock(Blocks.enchanting_table)) {
/*  78:116 */       this.thePlayer.addStat(AchievementList.enchantments, 1);
/*  79:    */     }
/*  80:119 */     if (par1ItemStack.getItem() == Item.getItemFromBlock(Blocks.bookshelf)) {
/*  81:121 */       this.thePlayer.addStat(AchievementList.bookcase, 1);
/*  82:    */     }
/*  83:    */   }
/*  84:    */   
/*  85:    */   public void onPickupFromSlot(EntityPlayer par1EntityPlayer, ItemStack par2ItemStack)
/*  86:    */   {
/*  87:127 */     onCrafting(par2ItemStack);
/*  88:129 */     for (int var3 = 0; var3 < this.craftMatrix.getSizeInventory(); var3++)
/*  89:    */     {
/*  90:131 */       ItemStack var4 = this.craftMatrix.getStackInSlot(var3);
/*  91:133 */       if (var4 != null)
/*  92:    */       {
/*  93:135 */         this.craftMatrix.decrStackSize(var3, 1);
/*  94:137 */         if (var4.getItem().hasContainerItem())
/*  95:    */         {
/*  96:139 */           ItemStack var5 = new ItemStack(var4.getItem().getContainerItem());
/*  97:141 */           if ((!var4.getItem().doesContainerItemLeaveCraftingGrid(var4)) || (!this.thePlayer.inventory.addItemStackToInventory(var5))) {
/*  98:143 */             if (this.craftMatrix.getStackInSlot(var3) == null) {
/*  99:145 */               this.craftMatrix.setInventorySlotContents(var3, var5);
/* 100:    */             } else {
/* 101:149 */               this.thePlayer.dropPlayerItemWithRandomChoice(var5, false);
/* 102:    */             }
/* 103:    */           }
/* 104:    */         }
/* 105:    */       }
/* 106:    */     }
/* 107:    */   }
/* 108:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.inventory.SlotCrafting
 * JD-Core Version:    0.7.0.1
 */