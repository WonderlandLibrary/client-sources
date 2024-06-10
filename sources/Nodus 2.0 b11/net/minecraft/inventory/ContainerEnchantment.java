/*   1:    */ package net.minecraft.inventory;
/*   2:    */ 
/*   3:    */ import java.util.List;
/*   4:    */ import java.util.Random;
/*   5:    */ import net.minecraft.enchantment.EnchantmentData;
/*   6:    */ import net.minecraft.enchantment.EnchantmentHelper;
/*   7:    */ import net.minecraft.entity.player.EntityPlayer;
/*   8:    */ import net.minecraft.entity.player.InventoryPlayer;
/*   9:    */ import net.minecraft.entity.player.PlayerCapabilities;
/*  10:    */ import net.minecraft.init.Blocks;
/*  11:    */ import net.minecraft.init.Items;
/*  12:    */ import net.minecraft.item.ItemEnchantedBook;
/*  13:    */ import net.minecraft.item.ItemStack;
/*  14:    */ import net.minecraft.world.World;
/*  15:    */ 
/*  16:    */ public class ContainerEnchantment
/*  17:    */   extends Container
/*  18:    */ {
/*  19: 17 */   public IInventory tableInventory = new InventoryBasic("Enchant", true, 1)
/*  20:    */   {
/*  21:    */     private static final String __OBFID = "CL_00001746";
/*  22:    */     
/*  23:    */     public int getInventoryStackLimit()
/*  24:    */     {
/*  25: 22 */       return 1;
/*  26:    */     }
/*  27:    */     
/*  28:    */     public void onInventoryChanged()
/*  29:    */     {
/*  30: 26 */       super.onInventoryChanged();
/*  31: 27 */       ContainerEnchantment.this.onCraftMatrixChanged(this);
/*  32:    */     }
/*  33:    */   };
/*  34:    */   private World worldPointer;
/*  35:    */   private int posX;
/*  36:    */   private int posY;
/*  37:    */   private int posZ;
/*  38: 36 */   private Random rand = new Random();
/*  39:    */   public long nameSeed;
/*  40: 42 */   public int[] enchantLevels = new int[3];
/*  41:    */   private static final String __OBFID = "CL_00001745";
/*  42:    */   
/*  43:    */   public ContainerEnchantment(InventoryPlayer par1InventoryPlayer, World par2World, int par3, int par4, int par5)
/*  44:    */   {
/*  45: 47 */     this.worldPointer = par2World;
/*  46: 48 */     this.posX = par3;
/*  47: 49 */     this.posY = par4;
/*  48: 50 */     this.posZ = par5;
/*  49: 51 */     addSlotToContainer(new Slot(this.tableInventory, 0, 25, 47)
/*  50:    */     {
/*  51:    */       private static final String __OBFID = "CL_00001747";
/*  52:    */       
/*  53:    */       public boolean isItemValid(ItemStack par1ItemStack)
/*  54:    */       {
/*  55: 56 */         return true;
/*  56:    */       }
/*  57:    */     });
/*  58: 61 */     for (int var6 = 0; var6 < 3; var6++) {
/*  59: 63 */       for (int var7 = 0; var7 < 9; var7++) {
/*  60: 65 */         addSlotToContainer(new Slot(par1InventoryPlayer, var7 + var6 * 9 + 9, 8 + var7 * 18, 84 + var6 * 18));
/*  61:    */       }
/*  62:    */     }
/*  63: 69 */     for (var6 = 0; var6 < 9; var6++) {
/*  64: 71 */       addSlotToContainer(new Slot(par1InventoryPlayer, var6, 8 + var6 * 18, 142));
/*  65:    */     }
/*  66:    */   }
/*  67:    */   
/*  68:    */   public void addCraftingToCrafters(ICrafting par1ICrafting)
/*  69:    */   {
/*  70: 77 */     super.addCraftingToCrafters(par1ICrafting);
/*  71: 78 */     par1ICrafting.sendProgressBarUpdate(this, 0, this.enchantLevels[0]);
/*  72: 79 */     par1ICrafting.sendProgressBarUpdate(this, 1, this.enchantLevels[1]);
/*  73: 80 */     par1ICrafting.sendProgressBarUpdate(this, 2, this.enchantLevels[2]);
/*  74:    */   }
/*  75:    */   
/*  76:    */   public void detectAndSendChanges()
/*  77:    */   {
/*  78: 88 */     super.detectAndSendChanges();
/*  79: 90 */     for (int var1 = 0; var1 < this.crafters.size(); var1++)
/*  80:    */     {
/*  81: 92 */       ICrafting var2 = (ICrafting)this.crafters.get(var1);
/*  82: 93 */       var2.sendProgressBarUpdate(this, 0, this.enchantLevels[0]);
/*  83: 94 */       var2.sendProgressBarUpdate(this, 1, this.enchantLevels[1]);
/*  84: 95 */       var2.sendProgressBarUpdate(this, 2, this.enchantLevels[2]);
/*  85:    */     }
/*  86:    */   }
/*  87:    */   
/*  88:    */   public void updateProgressBar(int par1, int par2)
/*  89:    */   {
/*  90:101 */     if ((par1 >= 0) && (par1 <= 2)) {
/*  91:103 */       this.enchantLevels[par1] = par2;
/*  92:    */     } else {
/*  93:107 */       super.updateProgressBar(par1, par2);
/*  94:    */     }
/*  95:    */   }
/*  96:    */   
/*  97:    */   public void onCraftMatrixChanged(IInventory par1IInventory)
/*  98:    */   {
/*  99:116 */     if (par1IInventory == this.tableInventory)
/* 100:    */     {
/* 101:118 */       ItemStack var2 = par1IInventory.getStackInSlot(0);
/* 102:121 */       if ((var2 != null) && (var2.isItemEnchantable()))
/* 103:    */       {
/* 104:123 */         this.nameSeed = this.rand.nextLong();
/* 105:125 */         if (!this.worldPointer.isClient)
/* 106:    */         {
/* 107:127 */           int var3 = 0;
/* 108:130 */           for (int var4 = -1; var4 <= 1; var4++) {
/* 109:132 */             for (int var5 = -1; var5 <= 1; var5++) {
/* 110:134 */               if (((var4 != 0) || (var5 != 0)) && (this.worldPointer.isAirBlock(this.posX + var5, this.posY, this.posZ + var4)) && (this.worldPointer.isAirBlock(this.posX + var5, this.posY + 1, this.posZ + var4)))
/* 111:    */               {
/* 112:136 */                 if (this.worldPointer.getBlock(this.posX + var5 * 2, this.posY, this.posZ + var4 * 2) == Blocks.bookshelf) {
/* 113:138 */                   var3++;
/* 114:    */                 }
/* 115:141 */                 if (this.worldPointer.getBlock(this.posX + var5 * 2, this.posY + 1, this.posZ + var4 * 2) == Blocks.bookshelf) {
/* 116:143 */                   var3++;
/* 117:    */                 }
/* 118:146 */                 if ((var5 != 0) && (var4 != 0))
/* 119:    */                 {
/* 120:148 */                   if (this.worldPointer.getBlock(this.posX + var5 * 2, this.posY, this.posZ + var4) == Blocks.bookshelf) {
/* 121:150 */                     var3++;
/* 122:    */                   }
/* 123:153 */                   if (this.worldPointer.getBlock(this.posX + var5 * 2, this.posY + 1, this.posZ + var4) == Blocks.bookshelf) {
/* 124:155 */                     var3++;
/* 125:    */                   }
/* 126:158 */                   if (this.worldPointer.getBlock(this.posX + var5, this.posY, this.posZ + var4 * 2) == Blocks.bookshelf) {
/* 127:160 */                     var3++;
/* 128:    */                   }
/* 129:163 */                   if (this.worldPointer.getBlock(this.posX + var5, this.posY + 1, this.posZ + var4 * 2) == Blocks.bookshelf) {
/* 130:165 */                     var3++;
/* 131:    */                   }
/* 132:    */                 }
/* 133:    */               }
/* 134:    */             }
/* 135:    */           }
/* 136:172 */           for (var4 = 0; var4 < 3; var4++) {
/* 137:174 */             this.enchantLevels[var4] = EnchantmentHelper.calcItemStackEnchantability(this.rand, var4, var3, var2);
/* 138:    */           }
/* 139:177 */           detectAndSendChanges();
/* 140:    */         }
/* 141:    */       }
/* 142:    */       else
/* 143:    */       {
/* 144:182 */         for (int var3 = 0; var3 < 3; var3++) {
/* 145:184 */           this.enchantLevels[var3] = 0;
/* 146:    */         }
/* 147:    */       }
/* 148:    */     }
/* 149:    */   }
/* 150:    */   
/* 151:    */   public boolean enchantItem(EntityPlayer par1EntityPlayer, int par2)
/* 152:    */   {
/* 153:195 */     ItemStack var3 = this.tableInventory.getStackInSlot(0);
/* 154:197 */     if ((this.enchantLevels[par2] > 0) && (var3 != null) && ((par1EntityPlayer.experienceLevel >= this.enchantLevels[par2]) || (par1EntityPlayer.capabilities.isCreativeMode)))
/* 155:    */     {
/* 156:199 */       if (!this.worldPointer.isClient)
/* 157:    */       {
/* 158:201 */         List var4 = EnchantmentHelper.buildEnchantmentList(this.rand, var3, this.enchantLevels[par2]);
/* 159:202 */         boolean var5 = var3.getItem() == Items.book;
/* 160:204 */         if (var4 != null)
/* 161:    */         {
/* 162:206 */           par1EntityPlayer.addExperienceLevel(-this.enchantLevels[par2]);
/* 163:208 */           if (var5) {
/* 164:210 */             var3.func_150996_a(Items.enchanted_book);
/* 165:    */           }
/* 166:213 */           int var6 = (var5) && (var4.size() > 1) ? this.rand.nextInt(var4.size()) : -1;
/* 167:215 */           for (int var7 = 0; var7 < var4.size(); var7++)
/* 168:    */           {
/* 169:217 */             EnchantmentData var8 = (EnchantmentData)var4.get(var7);
/* 170:219 */             if ((!var5) || (var7 != var6)) {
/* 171:221 */               if (var5) {
/* 172:223 */                 Items.enchanted_book.addEnchantment(var3, var8);
/* 173:    */               } else {
/* 174:227 */                 var3.addEnchantment(var8.enchantmentobj, var8.enchantmentLevel);
/* 175:    */               }
/* 176:    */             }
/* 177:    */           }
/* 178:232 */           onCraftMatrixChanged(this.tableInventory);
/* 179:    */         }
/* 180:    */       }
/* 181:236 */       return true;
/* 182:    */     }
/* 183:240 */     return false;
/* 184:    */   }
/* 185:    */   
/* 186:    */   public void onContainerClosed(EntityPlayer par1EntityPlayer)
/* 187:    */   {
/* 188:249 */     super.onContainerClosed(par1EntityPlayer);
/* 189:251 */     if (!this.worldPointer.isClient)
/* 190:    */     {
/* 191:253 */       ItemStack var2 = this.tableInventory.getStackInSlotOnClosing(0);
/* 192:255 */       if (var2 != null) {
/* 193:257 */         par1EntityPlayer.dropPlayerItemWithRandomChoice(var2, false);
/* 194:    */       }
/* 195:    */     }
/* 196:    */   }
/* 197:    */   
/* 198:    */   public boolean canInteractWith(EntityPlayer par1EntityPlayer)
/* 199:    */   {
/* 200:264 */     return this.worldPointer.getBlock(this.posX, this.posY, this.posZ) == Blocks.enchanting_table;
/* 201:    */   }
/* 202:    */   
/* 203:    */   public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2)
/* 204:    */   {
/* 205:272 */     ItemStack var3 = null;
/* 206:273 */     Slot var4 = (Slot)this.inventorySlots.get(par2);
/* 207:275 */     if ((var4 != null) && (var4.getHasStack()))
/* 208:    */     {
/* 209:277 */       ItemStack var5 = var4.getStack();
/* 210:278 */       var3 = var5.copy();
/* 211:280 */       if (par2 == 0)
/* 212:    */       {
/* 213:282 */         if (!mergeItemStack(var5, 1, 37, true)) {
/* 214:284 */           return null;
/* 215:    */         }
/* 216:    */       }
/* 217:    */       else
/* 218:    */       {
/* 219:289 */         if ((((Slot)this.inventorySlots.get(0)).getHasStack()) || (!((Slot)this.inventorySlots.get(0)).isItemValid(var5))) {
/* 220:291 */           return null;
/* 221:    */         }
/* 222:294 */         if ((var5.hasTagCompound()) && (var5.stackSize == 1))
/* 223:    */         {
/* 224:296 */           ((Slot)this.inventorySlots.get(0)).putStack(var5.copy());
/* 225:297 */           var5.stackSize = 0;
/* 226:    */         }
/* 227:299 */         else if (var5.stackSize >= 1)
/* 228:    */         {
/* 229:301 */           ((Slot)this.inventorySlots.get(0)).putStack(new ItemStack(var5.getItem(), 1, var5.getItemDamage()));
/* 230:302 */           var5.stackSize -= 1;
/* 231:    */         }
/* 232:    */       }
/* 233:306 */       if (var5.stackSize == 0) {
/* 234:308 */         var4.putStack(null);
/* 235:    */       } else {
/* 236:312 */         var4.onSlotChanged();
/* 237:    */       }
/* 238:315 */       if (var5.stackSize == var3.stackSize) {
/* 239:317 */         return null;
/* 240:    */       }
/* 241:320 */       var4.onPickupFromSlot(par1EntityPlayer, var5);
/* 242:    */     }
/* 243:323 */     return var3;
/* 244:    */   }
/* 245:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.inventory.ContainerEnchantment
 * JD-Core Version:    0.7.0.1
 */