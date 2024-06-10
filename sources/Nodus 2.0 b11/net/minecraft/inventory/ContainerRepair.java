/*   1:    */ package net.minecraft.inventory;
/*   2:    */ 
/*   3:    */ import java.util.Iterator;
/*   4:    */ import java.util.List;
/*   5:    */ import java.util.Map;
/*   6:    */ import java.util.Random;
/*   7:    */ import java.util.Set;
/*   8:    */ import net.minecraft.enchantment.Enchantment;
/*   9:    */ import net.minecraft.enchantment.EnchantmentHelper;
/*  10:    */ import net.minecraft.entity.player.EntityPlayer;
/*  11:    */ import net.minecraft.entity.player.InventoryPlayer;
/*  12:    */ import net.minecraft.entity.player.PlayerCapabilities;
/*  13:    */ import net.minecraft.init.Blocks;
/*  14:    */ import net.minecraft.init.Items;
/*  15:    */ import net.minecraft.item.Item;
/*  16:    */ import net.minecraft.item.ItemEnchantedBook;
/*  17:    */ import net.minecraft.item.ItemStack;
/*  18:    */ import net.minecraft.nbt.NBTTagList;
/*  19:    */ import net.minecraft.world.World;
/*  20:    */ import org.apache.commons.lang3.StringUtils;
/*  21:    */ import org.apache.logging.log4j.LogManager;
/*  22:    */ import org.apache.logging.log4j.Logger;
/*  23:    */ 
/*  24:    */ public class ContainerRepair
/*  25:    */   extends Container
/*  26:    */ {
/*  27: 19 */   private static final Logger logger = ;
/*  28: 22 */   private IInventory outputSlot = new InventoryCraftResult();
/*  29: 27 */   private IInventory inputSlots = new InventoryBasic("Repair", true, 2)
/*  30:    */   {
/*  31:    */     private static final String __OBFID = "CL_00001733";
/*  32:    */     
/*  33:    */     public void onInventoryChanged()
/*  34:    */     {
/*  35: 32 */       super.onInventoryChanged();
/*  36: 33 */       ContainerRepair.this.onCraftMatrixChanged(this);
/*  37:    */     }
/*  38:    */   };
/*  39:    */   private World theWorld;
/*  40:    */   private int field_82861_i;
/*  41:    */   private int field_82858_j;
/*  42:    */   private int field_82859_k;
/*  43:    */   public int maximumCost;
/*  44:    */   private int stackSizeToBeUsedInRepair;
/*  45:    */   private String repairedItemName;
/*  46:    */   private final EntityPlayer thePlayer;
/*  47:    */   private static final String __OBFID = "CL_00001732";
/*  48:    */   
/*  49:    */   public ContainerRepair(InventoryPlayer par1InventoryPlayer, final World par2World, final int par3, final int par4, final int par5, EntityPlayer par6EntityPlayer)
/*  50:    */   {
/*  51: 54 */     this.theWorld = par2World;
/*  52: 55 */     this.field_82861_i = par3;
/*  53: 56 */     this.field_82858_j = par4;
/*  54: 57 */     this.field_82859_k = par5;
/*  55: 58 */     this.thePlayer = par6EntityPlayer;
/*  56: 59 */     addSlotToContainer(new Slot(this.inputSlots, 0, 27, 47));
/*  57: 60 */     addSlotToContainer(new Slot(this.inputSlots, 1, 76, 47));
/*  58: 61 */     addSlotToContainer(new Slot(this.outputSlot, 2, 134, 47)
/*  59:    */     {
/*  60:    */       private static final String __OBFID = "CL_00001734";
/*  61:    */       
/*  62:    */       public boolean isItemValid(ItemStack par1ItemStack)
/*  63:    */       {
/*  64: 66 */         return false;
/*  65:    */       }
/*  66:    */       
/*  67:    */       public boolean canTakeStack(EntityPlayer par1EntityPlayer)
/*  68:    */       {
/*  69: 70 */         return ((par1EntityPlayer.capabilities.isCreativeMode) || (par1EntityPlayer.experienceLevel >= ContainerRepair.this.maximumCost)) && (ContainerRepair.this.maximumCost > 0) && (getHasStack());
/*  70:    */       }
/*  71:    */       
/*  72:    */       public void onPickupFromSlot(EntityPlayer par1EntityPlayer, ItemStack par2ItemStack)
/*  73:    */       {
/*  74: 74 */         if (!par1EntityPlayer.capabilities.isCreativeMode) {
/*  75: 76 */           par1EntityPlayer.addExperienceLevel(-ContainerRepair.this.maximumCost);
/*  76:    */         }
/*  77: 79 */         ContainerRepair.this.inputSlots.setInventorySlotContents(0, null);
/*  78: 81 */         if (ContainerRepair.this.stackSizeToBeUsedInRepair > 0)
/*  79:    */         {
/*  80: 83 */           ItemStack var3 = ContainerRepair.this.inputSlots.getStackInSlot(1);
/*  81: 85 */           if ((var3 != null) && (var3.stackSize > ContainerRepair.this.stackSizeToBeUsedInRepair))
/*  82:    */           {
/*  83: 87 */             var3.stackSize -= ContainerRepair.this.stackSizeToBeUsedInRepair;
/*  84: 88 */             ContainerRepair.this.inputSlots.setInventorySlotContents(1, var3);
/*  85:    */           }
/*  86:    */           else
/*  87:    */           {
/*  88: 92 */             ContainerRepair.this.inputSlots.setInventorySlotContents(1, null);
/*  89:    */           }
/*  90:    */         }
/*  91:    */         else
/*  92:    */         {
/*  93: 97 */           ContainerRepair.this.inputSlots.setInventorySlotContents(1, null);
/*  94:    */         }
/*  95:100 */         ContainerRepair.this.maximumCost = 0;
/*  96:102 */         if ((!par1EntityPlayer.capabilities.isCreativeMode) && (!par2World.isClient) && (par2World.getBlock(par3, par4, par5) == Blocks.anvil) && (par1EntityPlayer.getRNG().nextFloat() < 0.12F))
/*  97:    */         {
/*  98:104 */           int var6 = par2World.getBlockMetadata(par3, par4, par5);
/*  99:105 */           int var4 = var6 & 0x3;
/* 100:106 */           int var5 = var6 >> 2;
/* 101:107 */           var5++;
/* 102:109 */           if (var5 > 2)
/* 103:    */           {
/* 104:111 */             par2World.setBlockToAir(par3, par4, par5);
/* 105:112 */             par2World.playAuxSFX(1020, par3, par4, par5, 0);
/* 106:    */           }
/* 107:    */           else
/* 108:    */           {
/* 109:116 */             par2World.setBlockMetadataWithNotify(par3, par4, par5, var4 | var5 << 2, 2);
/* 110:117 */             par2World.playAuxSFX(1021, par3, par4, par5, 0);
/* 111:    */           }
/* 112:    */         }
/* 113:120 */         else if (!par2World.isClient)
/* 114:    */         {
/* 115:122 */           par2World.playAuxSFX(1021, par3, par4, par5, 0);
/* 116:    */         }
/* 117:    */       }
/* 118:    */     });
/* 119:128 */     for (int var7 = 0; var7 < 3; var7++) {
/* 120:130 */       for (int var8 = 0; var8 < 9; var8++) {
/* 121:132 */         addSlotToContainer(new Slot(par1InventoryPlayer, var8 + var7 * 9 + 9, 8 + var8 * 18, 84 + var7 * 18));
/* 122:    */       }
/* 123:    */     }
/* 124:136 */     for (var7 = 0; var7 < 9; var7++) {
/* 125:138 */       addSlotToContainer(new Slot(par1InventoryPlayer, var7, 8 + var7 * 18, 142));
/* 126:    */     }
/* 127:    */   }
/* 128:    */   
/* 129:    */   public void onCraftMatrixChanged(IInventory par1IInventory)
/* 130:    */   {
/* 131:147 */     super.onCraftMatrixChanged(par1IInventory);
/* 132:149 */     if (par1IInventory == this.inputSlots) {
/* 133:151 */       updateRepairOutput();
/* 134:    */     }
/* 135:    */   }
/* 136:    */   
/* 137:    */   public void updateRepairOutput()
/* 138:    */   {
/* 139:160 */     ItemStack var1 = this.inputSlots.getStackInSlot(0);
/* 140:161 */     this.maximumCost = 0;
/* 141:162 */     int var2 = 0;
/* 142:163 */     byte var3 = 0;
/* 143:164 */     int var4 = 0;
/* 144:166 */     if (var1 == null)
/* 145:    */     {
/* 146:168 */       this.outputSlot.setInventorySlotContents(0, null);
/* 147:169 */       this.maximumCost = 0;
/* 148:    */     }
/* 149:    */     else
/* 150:    */     {
/* 151:173 */       ItemStack var5 = var1.copy();
/* 152:174 */       ItemStack var6 = this.inputSlots.getStackInSlot(1);
/* 153:175 */       Map var7 = EnchantmentHelper.getEnchantments(var5);
/* 154:176 */       boolean var8 = false;
/* 155:177 */       int var19 = var3 + var1.getRepairCost() + (var6 == null ? 0 : var6.getRepairCost());
/* 156:178 */       this.stackSizeToBeUsedInRepair = 0;
/* 157:187 */       if (var6 != null)
/* 158:    */       {
/* 159:189 */         var8 = (var6.getItem() == Items.enchanted_book) && (Items.enchanted_book.func_92110_g(var6).tagCount() > 0);
/* 160:191 */         if ((var5.isItemStackDamageable()) && (var5.getItem().getIsRepairable(var1, var6)))
/* 161:    */         {
/* 162:193 */           int var9 = Math.min(var5.getItemDamageForDisplay(), var5.getMaxDamage() / 4);
/* 163:195 */           if (var9 <= 0)
/* 164:    */           {
/* 165:197 */             this.outputSlot.setInventorySlotContents(0, null);
/* 166:198 */             this.maximumCost = 0;
/* 167:199 */             return;
/* 168:    */           }
/* 169:202 */           for (int var10 = 0; (var9 > 0) && (var10 < var6.stackSize); var10++)
/* 170:    */           {
/* 171:204 */             int var11 = var5.getItemDamageForDisplay() - var9;
/* 172:205 */             var5.setItemDamage(var11);
/* 173:206 */             var2 += Math.max(1, var9 / 100) + var7.size();
/* 174:207 */             var9 = Math.min(var5.getItemDamageForDisplay(), var5.getMaxDamage() / 4);
/* 175:    */           }
/* 176:210 */           this.stackSizeToBeUsedInRepair = var10;
/* 177:    */         }
/* 178:    */         else
/* 179:    */         {
/* 180:214 */           if ((!var8) && ((var5.getItem() != var6.getItem()) || (!var5.isItemStackDamageable())))
/* 181:    */           {
/* 182:216 */             this.outputSlot.setInventorySlotContents(0, null);
/* 183:217 */             this.maximumCost = 0;
/* 184:218 */             return;
/* 185:    */           }
/* 186:221 */           if ((var5.isItemStackDamageable()) && (!var8))
/* 187:    */           {
/* 188:223 */             int var9 = var1.getMaxDamage() - var1.getItemDamageForDisplay();
/* 189:224 */             int var10 = var6.getMaxDamage() - var6.getItemDamageForDisplay();
/* 190:225 */             int var11 = var10 + var5.getMaxDamage() * 12 / 100;
/* 191:226 */             int var12 = var9 + var11;
/* 192:227 */             int var13 = var5.getMaxDamage() - var12;
/* 193:229 */             if (var13 < 0) {
/* 194:231 */               var13 = 0;
/* 195:    */             }
/* 196:234 */             if (var13 < var5.getItemDamage())
/* 197:    */             {
/* 198:236 */               var5.setItemDamage(var13);
/* 199:237 */               var2 += Math.max(1, var11 / 100);
/* 200:    */             }
/* 201:    */           }
/* 202:241 */           Map var20 = EnchantmentHelper.getEnchantments(var6);
/* 203:242 */           Iterator var21 = var20.keySet().iterator();
/* 204:244 */           while (var21.hasNext())
/* 205:    */           {
/* 206:246 */             int var11 = ((Integer)var21.next()).intValue();
/* 207:247 */             Enchantment var22 = Enchantment.enchantmentsList[var11];
/* 208:248 */             int var13 = var7.containsKey(Integer.valueOf(var11)) ? ((Integer)var7.get(Integer.valueOf(var11))).intValue() : 0;
/* 209:249 */             int var14 = ((Integer)var20.get(Integer.valueOf(var11))).intValue();
/* 210:    */             int var10000;
/* 211:    */             int var10000;
/* 212:252 */             if (var13 == var14)
/* 213:    */             {
/* 214:254 */               var14++;
/* 215:255 */               var10000 = var14;
/* 216:    */             }
/* 217:    */             else
/* 218:    */             {
/* 219:259 */               var10000 = Math.max(var14, var13);
/* 220:    */             }
/* 221:262 */             var14 = var10000;
/* 222:263 */             int var15 = var14 - var13;
/* 223:264 */             boolean var16 = var22.canApply(var1);
/* 224:266 */             if ((this.thePlayer.capabilities.isCreativeMode) || (var1.getItem() == Items.enchanted_book)) {
/* 225:268 */               var16 = true;
/* 226:    */             }
/* 227:271 */             Iterator var17 = var7.keySet().iterator();
/* 228:273 */             while (var17.hasNext())
/* 229:    */             {
/* 230:275 */               int var18 = ((Integer)var17.next()).intValue();
/* 231:277 */               if ((var18 != var11) && (!var22.canApplyTogether(Enchantment.enchantmentsList[var18])))
/* 232:    */               {
/* 233:279 */                 var16 = false;
/* 234:280 */                 var2 += var15;
/* 235:    */               }
/* 236:    */             }
/* 237:284 */             if (var16)
/* 238:    */             {
/* 239:286 */               if (var14 > var22.getMaxLevel()) {
/* 240:288 */                 var14 = var22.getMaxLevel();
/* 241:    */               }
/* 242:291 */               var7.put(Integer.valueOf(var11), Integer.valueOf(var14));
/* 243:292 */               int var23 = 0;
/* 244:294 */               switch (var22.getWeight())
/* 245:    */               {
/* 246:    */               case 1: 
/* 247:297 */                 var23 = 8;
/* 248:298 */                 break;
/* 249:    */               case 2: 
/* 250:301 */                 var23 = 4;
/* 251:    */               case 3: 
/* 252:    */               case 4: 
/* 253:    */               case 6: 
/* 254:    */               case 7: 
/* 255:    */               case 8: 
/* 256:    */               case 9: 
/* 257:    */               default: 
/* 258:    */                 break;
/* 259:    */               case 5: 
/* 260:313 */                 var23 = 2;
/* 261:314 */                 break;
/* 262:    */               case 10: 
/* 263:317 */                 var23 = 1;
/* 264:    */               }
/* 265:320 */               if (var8) {
/* 266:322 */                 var23 = Math.max(1, var23 / 2);
/* 267:    */               }
/* 268:325 */               var2 += var23 * var15;
/* 269:    */             }
/* 270:    */           }
/* 271:    */         }
/* 272:    */       }
/* 273:331 */       if (StringUtils.isBlank(this.repairedItemName))
/* 274:    */       {
/* 275:333 */         if (var1.hasDisplayName())
/* 276:    */         {
/* 277:335 */           var4 = var1.isItemStackDamageable() ? 7 : var1.stackSize * 5;
/* 278:336 */           var2 += var4;
/* 279:337 */           var5.func_135074_t();
/* 280:    */         }
/* 281:    */       }
/* 282:340 */       else if (!this.repairedItemName.equals(var1.getDisplayName()))
/* 283:    */       {
/* 284:342 */         var4 = var1.isItemStackDamageable() ? 7 : var1.stackSize * 5;
/* 285:343 */         var2 += var4;
/* 286:345 */         if (var1.hasDisplayName()) {
/* 287:347 */           var19 += var4 / 2;
/* 288:    */         }
/* 289:350 */         var5.setStackDisplayName(this.repairedItemName);
/* 290:    */       }
/* 291:353 */       int var9 = 0;
/* 292:    */       int var13;
/* 293:    */       int var14;
/* 294:355 */       for (Iterator var21 = var7.keySet().iterator(); var21.hasNext(); var19 += var9 + var13 * var14)
/* 295:    */       {
/* 296:357 */         int var11 = ((Integer)var21.next()).intValue();
/* 297:358 */         Enchantment var22 = Enchantment.enchantmentsList[var11];
/* 298:359 */         var13 = ((Integer)var7.get(Integer.valueOf(var11))).intValue();
/* 299:360 */         var14 = 0;
/* 300:361 */         var9++;
/* 301:363 */         switch (var22.getWeight())
/* 302:    */         {
/* 303:    */         case 1: 
/* 304:366 */           var14 = 8;
/* 305:367 */           break;
/* 306:    */         case 2: 
/* 307:370 */           var14 = 4;
/* 308:    */         case 3: 
/* 309:    */         case 4: 
/* 310:    */         case 6: 
/* 311:    */         case 7: 
/* 312:    */         case 8: 
/* 313:    */         case 9: 
/* 314:    */         default: 
/* 315:    */           break;
/* 316:    */         case 5: 
/* 317:382 */           var14 = 2;
/* 318:383 */           break;
/* 319:    */         case 10: 
/* 320:386 */           var14 = 1;
/* 321:    */         }
/* 322:389 */         if (var8) {
/* 323:391 */           var14 = Math.max(1, var14 / 2);
/* 324:    */         }
/* 325:    */       }
/* 326:395 */       if (var8) {
/* 327:397 */         var19 = Math.max(1, var19 / 2);
/* 328:    */       }
/* 329:400 */       this.maximumCost = (var19 + var2);
/* 330:402 */       if (var2 <= 0) {
/* 331:404 */         var5 = null;
/* 332:    */       }
/* 333:407 */       if ((var4 == var2) && (var4 > 0) && (this.maximumCost >= 40)) {
/* 334:409 */         this.maximumCost = 39;
/* 335:    */       }
/* 336:412 */       if ((this.maximumCost >= 40) && (!this.thePlayer.capabilities.isCreativeMode)) {
/* 337:414 */         var5 = null;
/* 338:    */       }
/* 339:417 */       if (var5 != null)
/* 340:    */       {
/* 341:419 */         int var10 = var5.getRepairCost();
/* 342:421 */         if ((var6 != null) && (var10 < var6.getRepairCost())) {
/* 343:423 */           var10 = var6.getRepairCost();
/* 344:    */         }
/* 345:426 */         if (var5.hasDisplayName()) {
/* 346:428 */           var10 -= 9;
/* 347:    */         }
/* 348:431 */         if (var10 < 0) {
/* 349:433 */           var10 = 0;
/* 350:    */         }
/* 351:436 */         var10 += 2;
/* 352:437 */         var5.setRepairCost(var10);
/* 353:438 */         EnchantmentHelper.setEnchantments(var7, var5);
/* 354:    */       }
/* 355:441 */       this.outputSlot.setInventorySlotContents(0, var5);
/* 356:442 */       detectAndSendChanges();
/* 357:    */     }
/* 358:    */   }
/* 359:    */   
/* 360:    */   public void addCraftingToCrafters(ICrafting par1ICrafting)
/* 361:    */   {
/* 362:448 */     super.addCraftingToCrafters(par1ICrafting);
/* 363:449 */     par1ICrafting.sendProgressBarUpdate(this, 0, this.maximumCost);
/* 364:    */   }
/* 365:    */   
/* 366:    */   public void updateProgressBar(int par1, int par2)
/* 367:    */   {
/* 368:454 */     if (par1 == 0) {
/* 369:456 */       this.maximumCost = par2;
/* 370:    */     }
/* 371:    */   }
/* 372:    */   
/* 373:    */   public void onContainerClosed(EntityPlayer par1EntityPlayer)
/* 374:    */   {
/* 375:465 */     super.onContainerClosed(par1EntityPlayer);
/* 376:467 */     if (!this.theWorld.isClient) {
/* 377:469 */       for (int var2 = 0; var2 < this.inputSlots.getSizeInventory(); var2++)
/* 378:    */       {
/* 379:471 */         ItemStack var3 = this.inputSlots.getStackInSlotOnClosing(var2);
/* 380:473 */         if (var3 != null) {
/* 381:475 */           par1EntityPlayer.dropPlayerItemWithRandomChoice(var3, false);
/* 382:    */         }
/* 383:    */       }
/* 384:    */     }
/* 385:    */   }
/* 386:    */   
/* 387:    */   public boolean canInteractWith(EntityPlayer par1EntityPlayer)
/* 388:    */   {
/* 389:483 */     return this.theWorld.getBlock(this.field_82861_i, this.field_82858_j, this.field_82859_k) == Blocks.anvil;
/* 390:    */   }
/* 391:    */   
/* 392:    */   public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2)
/* 393:    */   {
/* 394:491 */     ItemStack var3 = null;
/* 395:492 */     Slot var4 = (Slot)this.inventorySlots.get(par2);
/* 396:494 */     if ((var4 != null) && (var4.getHasStack()))
/* 397:    */     {
/* 398:496 */       ItemStack var5 = var4.getStack();
/* 399:497 */       var3 = var5.copy();
/* 400:499 */       if (par2 == 2)
/* 401:    */       {
/* 402:501 */         if (!mergeItemStack(var5, 3, 39, true)) {
/* 403:503 */           return null;
/* 404:    */         }
/* 405:506 */         var4.onSlotChange(var5, var3);
/* 406:    */       }
/* 407:508 */       else if ((par2 != 0) && (par2 != 1))
/* 408:    */       {
/* 409:510 */         if ((par2 >= 3) && (par2 < 39) && (!mergeItemStack(var5, 0, 2, false))) {
/* 410:512 */           return null;
/* 411:    */         }
/* 412:    */       }
/* 413:515 */       else if (!mergeItemStack(var5, 3, 39, false))
/* 414:    */       {
/* 415:517 */         return null;
/* 416:    */       }
/* 417:520 */       if (var5.stackSize == 0) {
/* 418:522 */         var4.putStack(null);
/* 419:    */       } else {
/* 420:526 */         var4.onSlotChanged();
/* 421:    */       }
/* 422:529 */       if (var5.stackSize == var3.stackSize) {
/* 423:531 */         return null;
/* 424:    */       }
/* 425:534 */       var4.onPickupFromSlot(par1EntityPlayer, var5);
/* 426:    */     }
/* 427:537 */     return var3;
/* 428:    */   }
/* 429:    */   
/* 430:    */   public void updateItemName(String par1Str)
/* 431:    */   {
/* 432:545 */     this.repairedItemName = par1Str;
/* 433:547 */     if (getSlot(2).getHasStack())
/* 434:    */     {
/* 435:549 */       ItemStack var2 = getSlot(2).getStack();
/* 436:551 */       if (StringUtils.isBlank(par1Str)) {
/* 437:553 */         var2.func_135074_t();
/* 438:    */       } else {
/* 439:557 */         var2.setStackDisplayName(this.repairedItemName);
/* 440:    */       }
/* 441:    */     }
/* 442:561 */     updateRepairOutput();
/* 443:    */   }
/* 444:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.inventory.ContainerRepair
 * JD-Core Version:    0.7.0.1
 */