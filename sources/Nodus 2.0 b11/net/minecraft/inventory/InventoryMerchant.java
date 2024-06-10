/*   1:    */ package net.minecraft.inventory;
/*   2:    */ 
/*   3:    */ import net.minecraft.entity.IMerchant;
/*   4:    */ import net.minecraft.entity.player.EntityPlayer;
/*   5:    */ import net.minecraft.item.ItemStack;
/*   6:    */ import net.minecraft.village.MerchantRecipe;
/*   7:    */ import net.minecraft.village.MerchantRecipeList;
/*   8:    */ 
/*   9:    */ public class InventoryMerchant
/*  10:    */   implements IInventory
/*  11:    */ {
/*  12:    */   private final IMerchant theMerchant;
/*  13: 12 */   private ItemStack[] theInventory = new ItemStack[3];
/*  14:    */   private final EntityPlayer thePlayer;
/*  15:    */   private MerchantRecipe currentRecipe;
/*  16:    */   private int currentRecipeIndex;
/*  17:    */   private static final String __OBFID = "CL_00001756";
/*  18:    */   
/*  19:    */   public InventoryMerchant(EntityPlayer par1EntityPlayer, IMerchant par2IMerchant)
/*  20:    */   {
/*  21: 20 */     this.thePlayer = par1EntityPlayer;
/*  22: 21 */     this.theMerchant = par2IMerchant;
/*  23:    */   }
/*  24:    */   
/*  25:    */   public int getSizeInventory()
/*  26:    */   {
/*  27: 29 */     return this.theInventory.length;
/*  28:    */   }
/*  29:    */   
/*  30:    */   public ItemStack getStackInSlot(int par1)
/*  31:    */   {
/*  32: 37 */     return this.theInventory[par1];
/*  33:    */   }
/*  34:    */   
/*  35:    */   public ItemStack decrStackSize(int par1, int par2)
/*  36:    */   {
/*  37: 46 */     if (this.theInventory[par1] != null)
/*  38:    */     {
/*  39: 50 */       if (par1 == 2)
/*  40:    */       {
/*  41: 52 */         ItemStack var3 = this.theInventory[par1];
/*  42: 53 */         this.theInventory[par1] = null;
/*  43: 54 */         return var3;
/*  44:    */       }
/*  45: 56 */       if (this.theInventory[par1].stackSize <= par2)
/*  46:    */       {
/*  47: 58 */         ItemStack var3 = this.theInventory[par1];
/*  48: 59 */         this.theInventory[par1] = null;
/*  49: 61 */         if (inventoryResetNeededOnSlotChange(par1)) {
/*  50: 63 */           resetRecipeAndSlots();
/*  51:    */         }
/*  52: 66 */         return var3;
/*  53:    */       }
/*  54: 70 */       ItemStack var3 = this.theInventory[par1].splitStack(par2);
/*  55: 72 */       if (this.theInventory[par1].stackSize == 0) {
/*  56: 74 */         this.theInventory[par1] = null;
/*  57:    */       }
/*  58: 77 */       if (inventoryResetNeededOnSlotChange(par1)) {
/*  59: 79 */         resetRecipeAndSlots();
/*  60:    */       }
/*  61: 82 */       return var3;
/*  62:    */     }
/*  63: 87 */     return null;
/*  64:    */   }
/*  65:    */   
/*  66:    */   private boolean inventoryResetNeededOnSlotChange(int par1)
/*  67:    */   {
/*  68: 96 */     return (par1 == 0) || (par1 == 1);
/*  69:    */   }
/*  70:    */   
/*  71:    */   public ItemStack getStackInSlotOnClosing(int par1)
/*  72:    */   {
/*  73:105 */     if (this.theInventory[par1] != null)
/*  74:    */     {
/*  75:107 */       ItemStack var2 = this.theInventory[par1];
/*  76:108 */       this.theInventory[par1] = null;
/*  77:109 */       return var2;
/*  78:    */     }
/*  79:113 */     return null;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public void setInventorySlotContents(int par1, ItemStack par2ItemStack)
/*  83:    */   {
/*  84:122 */     this.theInventory[par1] = par2ItemStack;
/*  85:124 */     if ((par2ItemStack != null) && (par2ItemStack.stackSize > getInventoryStackLimit())) {
/*  86:126 */       par2ItemStack.stackSize = getInventoryStackLimit();
/*  87:    */     }
/*  88:129 */     if (inventoryResetNeededOnSlotChange(par1)) {
/*  89:131 */       resetRecipeAndSlots();
/*  90:    */     }
/*  91:    */   }
/*  92:    */   
/*  93:    */   public String getInventoryName()
/*  94:    */   {
/*  95:140 */     return "mob.villager";
/*  96:    */   }
/*  97:    */   
/*  98:    */   public boolean isInventoryNameLocalized()
/*  99:    */   {
/* 100:148 */     return false;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public int getInventoryStackLimit()
/* 104:    */   {
/* 105:156 */     return 64;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer)
/* 109:    */   {
/* 110:164 */     return this.theMerchant.getCustomer() == par1EntityPlayer;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public void openInventory() {}
/* 114:    */   
/* 115:    */   public void closeInventory() {}
/* 116:    */   
/* 117:    */   public boolean isItemValidForSlot(int par1, ItemStack par2ItemStack)
/* 118:    */   {
/* 119:176 */     return true;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public void onInventoryChanged()
/* 123:    */   {
/* 124:184 */     resetRecipeAndSlots();
/* 125:    */   }
/* 126:    */   
/* 127:    */   public void resetRecipeAndSlots()
/* 128:    */   {
/* 129:189 */     this.currentRecipe = null;
/* 130:190 */     ItemStack var1 = this.theInventory[0];
/* 131:191 */     ItemStack var2 = this.theInventory[1];
/* 132:193 */     if (var1 == null)
/* 133:    */     {
/* 134:195 */       var1 = var2;
/* 135:196 */       var2 = null;
/* 136:    */     }
/* 137:199 */     if (var1 == null)
/* 138:    */     {
/* 139:201 */       setInventorySlotContents(2, null);
/* 140:    */     }
/* 141:    */     else
/* 142:    */     {
/* 143:205 */       MerchantRecipeList var3 = this.theMerchant.getRecipes(this.thePlayer);
/* 144:207 */       if (var3 != null)
/* 145:    */       {
/* 146:209 */         MerchantRecipe var4 = var3.canRecipeBeUsed(var1, var2, this.currentRecipeIndex);
/* 147:211 */         if ((var4 != null) && (!var4.isRecipeDisabled()))
/* 148:    */         {
/* 149:213 */           this.currentRecipe = var4;
/* 150:214 */           setInventorySlotContents(2, var4.getItemToSell().copy());
/* 151:    */         }
/* 152:216 */         else if (var2 != null)
/* 153:    */         {
/* 154:218 */           var4 = var3.canRecipeBeUsed(var2, var1, this.currentRecipeIndex);
/* 155:220 */           if ((var4 != null) && (!var4.isRecipeDisabled()))
/* 156:    */           {
/* 157:222 */             this.currentRecipe = var4;
/* 158:223 */             setInventorySlotContents(2, var4.getItemToSell().copy());
/* 159:    */           }
/* 160:    */           else
/* 161:    */           {
/* 162:227 */             setInventorySlotContents(2, null);
/* 163:    */           }
/* 164:    */         }
/* 165:    */         else
/* 166:    */         {
/* 167:232 */           setInventorySlotContents(2, null);
/* 168:    */         }
/* 169:    */       }
/* 170:    */     }
/* 171:237 */     this.theMerchant.func_110297_a_(getStackInSlot(2));
/* 172:    */   }
/* 173:    */   
/* 174:    */   public MerchantRecipe getCurrentRecipe()
/* 175:    */   {
/* 176:242 */     return this.currentRecipe;
/* 177:    */   }
/* 178:    */   
/* 179:    */   public void setCurrentRecipeIndex(int par1)
/* 180:    */   {
/* 181:247 */     this.currentRecipeIndex = par1;
/* 182:248 */     resetRecipeAndSlots();
/* 183:    */   }
/* 184:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.inventory.InventoryMerchant
 * JD-Core Version:    0.7.0.1
 */