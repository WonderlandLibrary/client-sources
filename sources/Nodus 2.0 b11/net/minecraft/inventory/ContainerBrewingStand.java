/*   1:    */ package net.minecraft.inventory;
/*   2:    */ 
/*   3:    */ import java.util.List;
/*   4:    */ import net.minecraft.entity.player.EntityPlayer;
/*   5:    */ import net.minecraft.entity.player.InventoryPlayer;
/*   6:    */ import net.minecraft.init.Items;
/*   7:    */ import net.minecraft.item.Item;
/*   8:    */ import net.minecraft.item.ItemStack;
/*   9:    */ import net.minecraft.stats.AchievementList;
/*  10:    */ import net.minecraft.tileentity.TileEntityBrewingStand;
/*  11:    */ 
/*  12:    */ public class ContainerBrewingStand
/*  13:    */   extends Container
/*  14:    */ {
/*  15:    */   private TileEntityBrewingStand tileBrewingStand;
/*  16:    */   private final Slot theSlot;
/*  17:    */   private int brewTime;
/*  18:    */   private static final String __OBFID = "CL_00001737";
/*  19:    */   
/*  20:    */   public ContainerBrewingStand(InventoryPlayer par1InventoryPlayer, TileEntityBrewingStand par2TileEntityBrewingStand)
/*  21:    */   {
/*  22: 21 */     this.tileBrewingStand = par2TileEntityBrewingStand;
/*  23: 22 */     addSlotToContainer(new Potion(par1InventoryPlayer.player, par2TileEntityBrewingStand, 0, 56, 46));
/*  24: 23 */     addSlotToContainer(new Potion(par1InventoryPlayer.player, par2TileEntityBrewingStand, 1, 79, 53));
/*  25: 24 */     addSlotToContainer(new Potion(par1InventoryPlayer.player, par2TileEntityBrewingStand, 2, 102, 46));
/*  26: 25 */     this.theSlot = addSlotToContainer(new Ingredient(par2TileEntityBrewingStand, 3, 79, 17));
/*  27: 28 */     for (int var3 = 0; var3 < 3; var3++) {
/*  28: 30 */       for (int var4 = 0; var4 < 9; var4++) {
/*  29: 32 */         addSlotToContainer(new Slot(par1InventoryPlayer, var4 + var3 * 9 + 9, 8 + var4 * 18, 84 + var3 * 18));
/*  30:    */       }
/*  31:    */     }
/*  32: 36 */     for (var3 = 0; var3 < 9; var3++) {
/*  33: 38 */       addSlotToContainer(new Slot(par1InventoryPlayer, var3, 8 + var3 * 18, 142));
/*  34:    */     }
/*  35:    */   }
/*  36:    */   
/*  37:    */   public void addCraftingToCrafters(ICrafting par1ICrafting)
/*  38:    */   {
/*  39: 44 */     super.addCraftingToCrafters(par1ICrafting);
/*  40: 45 */     par1ICrafting.sendProgressBarUpdate(this, 0, this.tileBrewingStand.func_145935_i());
/*  41:    */   }
/*  42:    */   
/*  43:    */   public void detectAndSendChanges()
/*  44:    */   {
/*  45: 53 */     super.detectAndSendChanges();
/*  46: 55 */     for (int var1 = 0; var1 < this.crafters.size(); var1++)
/*  47:    */     {
/*  48: 57 */       ICrafting var2 = (ICrafting)this.crafters.get(var1);
/*  49: 59 */       if (this.brewTime != this.tileBrewingStand.func_145935_i()) {
/*  50: 61 */         var2.sendProgressBarUpdate(this, 0, this.tileBrewingStand.func_145935_i());
/*  51:    */       }
/*  52:    */     }
/*  53: 65 */     this.brewTime = this.tileBrewingStand.func_145935_i();
/*  54:    */   }
/*  55:    */   
/*  56:    */   public void updateProgressBar(int par1, int par2)
/*  57:    */   {
/*  58: 70 */     if (par1 == 0) {
/*  59: 72 */       this.tileBrewingStand.func_145938_d(par2);
/*  60:    */     }
/*  61:    */   }
/*  62:    */   
/*  63:    */   public boolean canInteractWith(EntityPlayer par1EntityPlayer)
/*  64:    */   {
/*  65: 78 */     return this.tileBrewingStand.isUseableByPlayer(par1EntityPlayer);
/*  66:    */   }
/*  67:    */   
/*  68:    */   public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2)
/*  69:    */   {
/*  70: 86 */     ItemStack var3 = null;
/*  71: 87 */     Slot var4 = (Slot)this.inventorySlots.get(par2);
/*  72: 89 */     if ((var4 != null) && (var4.getHasStack()))
/*  73:    */     {
/*  74: 91 */       ItemStack var5 = var4.getStack();
/*  75: 92 */       var3 = var5.copy();
/*  76: 94 */       if (((par2 < 0) || (par2 > 2)) && (par2 != 3))
/*  77:    */       {
/*  78: 96 */         if ((!this.theSlot.getHasStack()) && (this.theSlot.isItemValid(var5)))
/*  79:    */         {
/*  80: 98 */           if (!mergeItemStack(var5, 3, 4, false)) {
/*  81:100 */             return null;
/*  82:    */           }
/*  83:    */         }
/*  84:103 */         else if (Potion.canHoldPotion(var3))
/*  85:    */         {
/*  86:105 */           if (!mergeItemStack(var5, 0, 3, false)) {
/*  87:107 */             return null;
/*  88:    */           }
/*  89:    */         }
/*  90:110 */         else if ((par2 >= 4) && (par2 < 31))
/*  91:    */         {
/*  92:112 */           if (!mergeItemStack(var5, 31, 40, false)) {
/*  93:114 */             return null;
/*  94:    */           }
/*  95:    */         }
/*  96:117 */         else if ((par2 >= 31) && (par2 < 40))
/*  97:    */         {
/*  98:119 */           if (!mergeItemStack(var5, 4, 31, false)) {
/*  99:121 */             return null;
/* 100:    */           }
/* 101:    */         }
/* 102:124 */         else if (!mergeItemStack(var5, 4, 40, false)) {
/* 103:126 */           return null;
/* 104:    */         }
/* 105:    */       }
/* 106:    */       else
/* 107:    */       {
/* 108:131 */         if (!mergeItemStack(var5, 4, 40, true)) {
/* 109:133 */           return null;
/* 110:    */         }
/* 111:136 */         var4.onSlotChange(var5, var3);
/* 112:    */       }
/* 113:139 */       if (var5.stackSize == 0) {
/* 114:141 */         var4.putStack(null);
/* 115:    */       } else {
/* 116:145 */         var4.onSlotChanged();
/* 117:    */       }
/* 118:148 */       if (var5.stackSize == var3.stackSize) {
/* 119:150 */         return null;
/* 120:    */       }
/* 121:153 */       var4.onPickupFromSlot(par1EntityPlayer, var5);
/* 122:    */     }
/* 123:156 */     return var3;
/* 124:    */   }
/* 125:    */   
/* 126:    */   class Ingredient
/* 127:    */     extends Slot
/* 128:    */   {
/* 129:    */     private static final String __OBFID = "CL_00001738";
/* 130:    */     
/* 131:    */     public Ingredient(IInventory par2IInventory, int par3, int par4, int par5)
/* 132:    */     {
/* 133:165 */       super(par3, par4, par5);
/* 134:    */     }
/* 135:    */     
/* 136:    */     public boolean isItemValid(ItemStack par1ItemStack)
/* 137:    */     {
/* 138:170 */       return par1ItemStack != null ? par1ItemStack.getItem().isPotionIngredient(par1ItemStack) : false;
/* 139:    */     }
/* 140:    */     
/* 141:    */     public int getSlotStackLimit()
/* 142:    */     {
/* 143:175 */       return 64;
/* 144:    */     }
/* 145:    */   }
/* 146:    */   
/* 147:    */   static class Potion
/* 148:    */     extends Slot
/* 149:    */   {
/* 150:    */     private EntityPlayer player;
/* 151:    */     private static final String __OBFID = "CL_00001740";
/* 152:    */     
/* 153:    */     public Potion(EntityPlayer par1EntityPlayer, IInventory par2IInventory, int par3, int par4, int par5)
/* 154:    */     {
/* 155:186 */       super(par3, par4, par5);
/* 156:187 */       this.player = par1EntityPlayer;
/* 157:    */     }
/* 158:    */     
/* 159:    */     public boolean isItemValid(ItemStack par1ItemStack)
/* 160:    */     {
/* 161:192 */       return canHoldPotion(par1ItemStack);
/* 162:    */     }
/* 163:    */     
/* 164:    */     public int getSlotStackLimit()
/* 165:    */     {
/* 166:197 */       return 1;
/* 167:    */     }
/* 168:    */     
/* 169:    */     public void onPickupFromSlot(EntityPlayer par1EntityPlayer, ItemStack par2ItemStack)
/* 170:    */     {
/* 171:202 */       if ((par2ItemStack.getItem() == Items.potionitem) && (par2ItemStack.getItemDamage() > 0)) {
/* 172:204 */         this.player.addStat(AchievementList.potion, 1);
/* 173:    */       }
/* 174:207 */       super.onPickupFromSlot(par1EntityPlayer, par2ItemStack);
/* 175:    */     }
/* 176:    */     
/* 177:    */     public static boolean canHoldPotion(ItemStack par0ItemStack)
/* 178:    */     {
/* 179:212 */       return (par0ItemStack != null) && ((par0ItemStack.getItem() == Items.potionitem) || (par0ItemStack.getItem() == Items.glass_bottle));
/* 180:    */     }
/* 181:    */   }
/* 182:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.inventory.ContainerBrewingStand
 * JD-Core Version:    0.7.0.1
 */