/*   1:    */ package net.minecraft.inventory;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.HashSet;
/*   5:    */ import java.util.Iterator;
/*   6:    */ import java.util.List;
/*   7:    */ import java.util.Set;
/*   8:    */ import net.minecraft.entity.player.EntityPlayer;
/*   9:    */ import net.minecraft.entity.player.InventoryPlayer;
/*  10:    */ import net.minecraft.entity.player.PlayerCapabilities;
/*  11:    */ import net.minecraft.item.Item;
/*  12:    */ import net.minecraft.item.ItemStack;
/*  13:    */ import net.minecraft.util.MathHelper;
/*  14:    */ 
/*  15:    */ public abstract class Container
/*  16:    */ {
/*  17: 17 */   public List inventoryItemStacks = new ArrayList();
/*  18: 20 */   public List inventorySlots = new ArrayList();
/*  19:    */   public int windowId;
/*  20:    */   private short transactionID;
/*  21: 23 */   private int field_94535_f = -1;
/*  22:    */   private int field_94536_g;
/*  23: 25 */   private final Set field_94537_h = new HashSet();
/*  24: 30 */   protected List crafters = new ArrayList();
/*  25: 31 */   private Set playerList = new HashSet();
/*  26:    */   private static final String __OBFID = "CL_00001730";
/*  27:    */   
/*  28:    */   protected Slot addSlotToContainer(Slot par1Slot)
/*  29:    */   {
/*  30: 39 */     par1Slot.slotNumber = this.inventorySlots.size();
/*  31: 40 */     this.inventorySlots.add(par1Slot);
/*  32: 41 */     this.inventoryItemStacks.add(null);
/*  33: 42 */     return par1Slot;
/*  34:    */   }
/*  35:    */   
/*  36:    */   public void addCraftingToCrafters(ICrafting par1ICrafting)
/*  37:    */   {
/*  38: 47 */     if (this.crafters.contains(par1ICrafting)) {
/*  39: 49 */       throw new IllegalArgumentException("Listener already listening");
/*  40:    */     }
/*  41: 53 */     this.crafters.add(par1ICrafting);
/*  42: 54 */     par1ICrafting.sendContainerAndContentsToPlayer(this, getInventory());
/*  43: 55 */     detectAndSendChanges();
/*  44:    */   }
/*  45:    */   
/*  46:    */   public void removeCraftingFromCrafters(ICrafting par1ICrafting)
/*  47:    */   {
/*  48: 64 */     this.crafters.remove(par1ICrafting);
/*  49:    */   }
/*  50:    */   
/*  51:    */   public List getInventory()
/*  52:    */   {
/*  53: 72 */     ArrayList var1 = new ArrayList();
/*  54: 74 */     for (int var2 = 0; var2 < this.inventorySlots.size(); var2++) {
/*  55: 76 */       var1.add(((Slot)this.inventorySlots.get(var2)).getStack());
/*  56:    */     }
/*  57: 79 */     return var1;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public void detectAndSendChanges()
/*  61:    */   {
/*  62: 87 */     for (int var1 = 0; var1 < this.inventorySlots.size(); var1++)
/*  63:    */     {
/*  64: 89 */       ItemStack var2 = ((Slot)this.inventorySlots.get(var1)).getStack();
/*  65: 90 */       ItemStack var3 = (ItemStack)this.inventoryItemStacks.get(var1);
/*  66: 92 */       if (!ItemStack.areItemStacksEqual(var3, var2))
/*  67:    */       {
/*  68: 94 */         var3 = var2 == null ? null : var2.copy();
/*  69: 95 */         this.inventoryItemStacks.set(var1, var3);
/*  70: 97 */         for (int var4 = 0; var4 < this.crafters.size(); var4++) {
/*  71: 99 */           ((ICrafting)this.crafters.get(var4)).sendSlotContents(this, var1, var3);
/*  72:    */         }
/*  73:    */       }
/*  74:    */     }
/*  75:    */   }
/*  76:    */   
/*  77:    */   public boolean enchantItem(EntityPlayer par1EntityPlayer, int par2)
/*  78:    */   {
/*  79:110 */     return false;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public Slot getSlotFromInventory(IInventory par1IInventory, int par2)
/*  83:    */   {
/*  84:115 */     for (int var3 = 0; var3 < this.inventorySlots.size(); var3++)
/*  85:    */     {
/*  86:117 */       Slot var4 = (Slot)this.inventorySlots.get(var3);
/*  87:119 */       if (var4.isSlotInInventory(par1IInventory, par2)) {
/*  88:121 */         return var4;
/*  89:    */       }
/*  90:    */     }
/*  91:125 */     return null;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public Slot getSlot(int par1)
/*  95:    */   {
/*  96:130 */     return (Slot)this.inventorySlots.get(par1);
/*  97:    */   }
/*  98:    */   
/*  99:    */   public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2)
/* 100:    */   {
/* 101:138 */     Slot var3 = (Slot)this.inventorySlots.get(par2);
/* 102:139 */     return var3 != null ? var3.getStack() : null;
/* 103:    */   }
/* 104:    */   
/* 105:    */   public ItemStack slotClick(int par1, int par2, int par3, EntityPlayer par4EntityPlayer)
/* 106:    */   {
/* 107:144 */     ItemStack var5 = null;
/* 108:145 */     InventoryPlayer var6 = par4EntityPlayer.inventory;
/* 109:149 */     if (par3 == 5)
/* 110:    */     {
/* 111:151 */       int var7 = this.field_94536_g;
/* 112:152 */       this.field_94536_g = func_94532_c(par2);
/* 113:154 */       if (((var7 != 1) || (this.field_94536_g != 2)) && (var7 != this.field_94536_g))
/* 114:    */       {
/* 115:156 */         func_94533_d();
/* 116:    */       }
/* 117:158 */       else if (var6.getItemStack() == null)
/* 118:    */       {
/* 119:160 */         func_94533_d();
/* 120:    */       }
/* 121:162 */       else if (this.field_94536_g == 0)
/* 122:    */       {
/* 123:164 */         this.field_94535_f = func_94529_b(par2);
/* 124:166 */         if (func_94528_d(this.field_94535_f))
/* 125:    */         {
/* 126:168 */           this.field_94536_g = 1;
/* 127:169 */           this.field_94537_h.clear();
/* 128:    */         }
/* 129:    */         else
/* 130:    */         {
/* 131:173 */           func_94533_d();
/* 132:    */         }
/* 133:    */       }
/* 134:176 */       else if (this.field_94536_g == 1)
/* 135:    */       {
/* 136:178 */         Slot var8 = (Slot)this.inventorySlots.get(par1);
/* 137:180 */         if ((var8 != null) && (func_94527_a(var8, var6.getItemStack(), true)) && (var8.isItemValid(var6.getItemStack())) && (var6.getItemStack().stackSize > this.field_94537_h.size()) && (canDragIntoSlot(var8))) {
/* 138:182 */           this.field_94537_h.add(var8);
/* 139:    */         }
/* 140:    */       }
/* 141:185 */       else if (this.field_94536_g == 2)
/* 142:    */       {
/* 143:187 */         if (!this.field_94537_h.isEmpty())
/* 144:    */         {
/* 145:189 */           ItemStack var17 = var6.getItemStack().copy();
/* 146:190 */           int var9 = var6.getItemStack().stackSize;
/* 147:191 */           Iterator var10 = this.field_94537_h.iterator();
/* 148:193 */           while (var10.hasNext())
/* 149:    */           {
/* 150:195 */             Slot var11 = (Slot)var10.next();
/* 151:197 */             if ((var11 != null) && (func_94527_a(var11, var6.getItemStack(), true)) && (var11.isItemValid(var6.getItemStack())) && (var6.getItemStack().stackSize >= this.field_94537_h.size()) && (canDragIntoSlot(var11)))
/* 152:    */             {
/* 153:199 */               ItemStack var12 = var17.copy();
/* 154:200 */               int var13 = var11.getHasStack() ? var11.getStack().stackSize : 0;
/* 155:201 */               func_94525_a(this.field_94537_h, this.field_94535_f, var12, var13);
/* 156:203 */               if (var12.stackSize > var12.getMaxStackSize()) {
/* 157:205 */                 var12.stackSize = var12.getMaxStackSize();
/* 158:    */               }
/* 159:208 */               if (var12.stackSize > var11.getSlotStackLimit()) {
/* 160:210 */                 var12.stackSize = var11.getSlotStackLimit();
/* 161:    */               }
/* 162:213 */               var9 -= var12.stackSize - var13;
/* 163:214 */               var11.putStack(var12);
/* 164:    */             }
/* 165:    */           }
/* 166:218 */           var17.stackSize = var9;
/* 167:220 */           if (var17.stackSize <= 0) {
/* 168:222 */             var17 = null;
/* 169:    */           }
/* 170:225 */           var6.setItemStack(var17);
/* 171:    */         }
/* 172:228 */         func_94533_d();
/* 173:    */       }
/* 174:    */       else
/* 175:    */       {
/* 176:232 */         func_94533_d();
/* 177:    */       }
/* 178:    */     }
/* 179:235 */     else if (this.field_94536_g != 0)
/* 180:    */     {
/* 181:237 */       func_94533_d();
/* 182:    */     }
/* 183:245 */     else if (((par3 == 0) || (par3 == 1)) && ((par2 == 0) || (par2 == 1)))
/* 184:    */     {
/* 185:247 */       if (par1 == -999)
/* 186:    */       {
/* 187:249 */         if ((var6.getItemStack() != null) && (par1 == -999))
/* 188:    */         {
/* 189:251 */           if (par2 == 0)
/* 190:    */           {
/* 191:253 */             par4EntityPlayer.dropPlayerItemWithRandomChoice(var6.getItemStack(), true);
/* 192:254 */             var6.setItemStack(null);
/* 193:    */           }
/* 194:257 */           if (par2 == 1)
/* 195:    */           {
/* 196:259 */             par4EntityPlayer.dropPlayerItemWithRandomChoice(var6.getItemStack().splitStack(1), true);
/* 197:261 */             if (var6.getItemStack().stackSize == 0) {
/* 198:263 */               var6.setItemStack(null);
/* 199:    */             }
/* 200:    */           }
/* 201:    */         }
/* 202:    */       }
/* 203:268 */       else if (par3 == 1)
/* 204:    */       {
/* 205:270 */         if (par1 < 0) {
/* 206:272 */           return null;
/* 207:    */         }
/* 208:275 */         Slot var16 = (Slot)this.inventorySlots.get(par1);
/* 209:277 */         if ((var16 != null) && (var16.canTakeStack(par4EntityPlayer)))
/* 210:    */         {
/* 211:279 */           ItemStack var17 = transferStackInSlot(par4EntityPlayer, par1);
/* 212:281 */           if (var17 != null)
/* 213:    */           {
/* 214:283 */             Item var19 = var17.getItem();
/* 215:284 */             var5 = var17.copy();
/* 216:286 */             if ((var16.getStack() != null) && (var16.getStack().getItem() == var19)) {
/* 217:288 */               retrySlotClick(par1, par2, true, par4EntityPlayer);
/* 218:    */             }
/* 219:    */           }
/* 220:    */         }
/* 221:    */       }
/* 222:    */       else
/* 223:    */       {
/* 224:295 */         if (par1 < 0) {
/* 225:297 */           return null;
/* 226:    */         }
/* 227:300 */         Slot var16 = (Slot)this.inventorySlots.get(par1);
/* 228:302 */         if (var16 != null)
/* 229:    */         {
/* 230:304 */           ItemStack var17 = var16.getStack();
/* 231:305 */           ItemStack var20 = var6.getItemStack();
/* 232:307 */           if (var17 != null) {
/* 233:309 */             var5 = var17.copy();
/* 234:    */           }
/* 235:312 */           if (var17 == null)
/* 236:    */           {
/* 237:314 */             if ((var20 != null) && (var16.isItemValid(var20)))
/* 238:    */             {
/* 239:316 */               int var22 = par2 == 0 ? var20.stackSize : 1;
/* 240:318 */               if (var22 > var16.getSlotStackLimit()) {
/* 241:320 */                 var22 = var16.getSlotStackLimit();
/* 242:    */               }
/* 243:323 */               if (var20.stackSize >= var22) {
/* 244:325 */                 var16.putStack(var20.splitStack(var22));
/* 245:    */               }
/* 246:328 */               if (var20.stackSize == 0) {
/* 247:330 */                 var6.setItemStack(null);
/* 248:    */               }
/* 249:    */             }
/* 250:    */           }
/* 251:334 */           else if (var16.canTakeStack(par4EntityPlayer)) {
/* 252:336 */             if (var20 == null)
/* 253:    */             {
/* 254:338 */               int var22 = par2 == 0 ? var17.stackSize : (var17.stackSize + 1) / 2;
/* 255:339 */               ItemStack var24 = var16.decrStackSize(var22);
/* 256:340 */               var6.setItemStack(var24);
/* 257:342 */               if (var17.stackSize == 0) {
/* 258:344 */                 var16.putStack(null);
/* 259:    */               }
/* 260:347 */               var16.onPickupFromSlot(par4EntityPlayer, var6.getItemStack());
/* 261:    */             }
/* 262:349 */             else if (var16.isItemValid(var20))
/* 263:    */             {
/* 264:351 */               if ((var17.getItem() == var20.getItem()) && (var17.getItemDamage() == var20.getItemDamage()) && (ItemStack.areItemStackTagsEqual(var17, var20)))
/* 265:    */               {
/* 266:353 */                 int var22 = par2 == 0 ? var20.stackSize : 1;
/* 267:355 */                 if (var22 > var16.getSlotStackLimit() - var17.stackSize) {
/* 268:357 */                   var22 = var16.getSlotStackLimit() - var17.stackSize;
/* 269:    */                 }
/* 270:360 */                 if (var22 > var20.getMaxStackSize() - var17.stackSize) {
/* 271:362 */                   var22 = var20.getMaxStackSize() - var17.stackSize;
/* 272:    */                 }
/* 273:365 */                 var20.splitStack(var22);
/* 274:367 */                 if (var20.stackSize == 0) {
/* 275:369 */                   var6.setItemStack(null);
/* 276:    */                 }
/* 277:372 */                 var17.stackSize += var22;
/* 278:    */               }
/* 279:374 */               else if (var20.stackSize <= var16.getSlotStackLimit())
/* 280:    */               {
/* 281:376 */                 var16.putStack(var20);
/* 282:377 */                 var6.setItemStack(var17);
/* 283:    */               }
/* 284:    */             }
/* 285:380 */             else if ((var17.getItem() == var20.getItem()) && (var20.getMaxStackSize() > 1) && ((!var17.getHasSubtypes()) || (var17.getItemDamage() == var20.getItemDamage())) && (ItemStack.areItemStackTagsEqual(var17, var20)))
/* 286:    */             {
/* 287:382 */               int var22 = var17.stackSize;
/* 288:384 */               if ((var22 > 0) && (var22 + var20.stackSize <= var20.getMaxStackSize()))
/* 289:    */               {
/* 290:386 */                 var20.stackSize += var22;
/* 291:387 */                 var17 = var16.decrStackSize(var22);
/* 292:389 */                 if (var17.stackSize == 0) {
/* 293:391 */                   var16.putStack(null);
/* 294:    */                 }
/* 295:394 */                 var16.onPickupFromSlot(par4EntityPlayer, var6.getItemStack());
/* 296:    */               }
/* 297:    */             }
/* 298:    */           }
/* 299:399 */           var16.onSlotChanged();
/* 300:    */         }
/* 301:    */       }
/* 302:    */     }
/* 303:403 */     else if ((par3 == 2) && (par2 >= 0) && (par2 < 9))
/* 304:    */     {
/* 305:405 */       Slot var16 = (Slot)this.inventorySlots.get(par1);
/* 306:407 */       if (var16.canTakeStack(par4EntityPlayer))
/* 307:    */       {
/* 308:409 */         ItemStack var17 = var6.getStackInSlot(par2);
/* 309:410 */         boolean var18 = (var17 == null) || ((var16.inventory == var6) && (var16.isItemValid(var17)));
/* 310:411 */         int var22 = -1;
/* 311:413 */         if (!var18)
/* 312:    */         {
/* 313:415 */           var22 = var6.getFirstEmptyStack();
/* 314:416 */           var18 |= var22 > -1;
/* 315:    */         }
/* 316:419 */         if ((var16.getHasStack()) && (var18))
/* 317:    */         {
/* 318:421 */           ItemStack var24 = var16.getStack();
/* 319:422 */           var6.setInventorySlotContents(par2, var24.copy());
/* 320:424 */           if (((var16.inventory != var6) || (!var16.isItemValid(var17))) && (var17 != null))
/* 321:    */           {
/* 322:426 */             if (var22 > -1)
/* 323:    */             {
/* 324:428 */               var6.addItemStackToInventory(var17);
/* 325:429 */               var16.decrStackSize(var24.stackSize);
/* 326:430 */               var16.putStack(null);
/* 327:431 */               var16.onPickupFromSlot(par4EntityPlayer, var24);
/* 328:    */             }
/* 329:    */           }
/* 330:    */           else
/* 331:    */           {
/* 332:436 */             var16.decrStackSize(var24.stackSize);
/* 333:437 */             var16.putStack(var17);
/* 334:438 */             var16.onPickupFromSlot(par4EntityPlayer, var24);
/* 335:    */           }
/* 336:    */         }
/* 337:441 */         else if ((!var16.getHasStack()) && (var17 != null) && (var16.isItemValid(var17)))
/* 338:    */         {
/* 339:443 */           var6.setInventorySlotContents(par2, null);
/* 340:444 */           var16.putStack(var17);
/* 341:    */         }
/* 342:    */       }
/* 343:    */     }
/* 344:448 */     else if ((par3 == 3) && (par4EntityPlayer.capabilities.isCreativeMode) && (var6.getItemStack() == null) && (par1 >= 0))
/* 345:    */     {
/* 346:450 */       Slot var16 = (Slot)this.inventorySlots.get(par1);
/* 347:452 */       if ((var16 != null) && (var16.getHasStack()))
/* 348:    */       {
/* 349:454 */         ItemStack var17 = var16.getStack().copy();
/* 350:455 */         var17.stackSize = var17.getMaxStackSize();
/* 351:456 */         var6.setItemStack(var17);
/* 352:    */       }
/* 353:    */     }
/* 354:459 */     else if ((par3 == 4) && (var6.getItemStack() == null) && (par1 >= 0))
/* 355:    */     {
/* 356:461 */       Slot var16 = (Slot)this.inventorySlots.get(par1);
/* 357:463 */       if ((var16 != null) && (var16.getHasStack()) && (var16.canTakeStack(par4EntityPlayer)))
/* 358:    */       {
/* 359:465 */         ItemStack var17 = var16.decrStackSize(par2 == 0 ? 1 : var16.getStack().stackSize);
/* 360:466 */         var16.onPickupFromSlot(par4EntityPlayer, var17);
/* 361:467 */         par4EntityPlayer.dropPlayerItemWithRandomChoice(var17, true);
/* 362:    */       }
/* 363:    */     }
/* 364:470 */     else if ((par3 == 6) && (par1 >= 0))
/* 365:    */     {
/* 366:472 */       Slot var16 = (Slot)this.inventorySlots.get(par1);
/* 367:473 */       ItemStack var17 = var6.getItemStack();
/* 368:475 */       if ((var17 != null) && ((var16 == null) || (!var16.getHasStack()) || (!var16.canTakeStack(par4EntityPlayer))))
/* 369:    */       {
/* 370:477 */         int var9 = par2 == 0 ? 0 : this.inventorySlots.size() - 1;
/* 371:478 */         int var22 = par2 == 0 ? 1 : -1;
/* 372:480 */         for (int var21 = 0; var21 < 2; var21++) {
/* 373:482 */           for (int var23 = var9; (var23 >= 0) && (var23 < this.inventorySlots.size()) && (var17.stackSize < var17.getMaxStackSize()); var23 += var22)
/* 374:    */           {
/* 375:484 */             Slot var25 = (Slot)this.inventorySlots.get(var23);
/* 376:486 */             if ((var25.getHasStack()) && (func_94527_a(var25, var17, true)) && (var25.canTakeStack(par4EntityPlayer)) && (func_94530_a(var17, var25)) && ((var21 != 0) || (var25.getStack().stackSize != var25.getStack().getMaxStackSize())))
/* 377:    */             {
/* 378:488 */               int var14 = Math.min(var17.getMaxStackSize() - var17.stackSize, var25.getStack().stackSize);
/* 379:489 */               ItemStack var15 = var25.decrStackSize(var14);
/* 380:490 */               var17.stackSize += var14;
/* 381:492 */               if (var15.stackSize <= 0) {
/* 382:494 */                 var25.putStack(null);
/* 383:    */               }
/* 384:497 */               var25.onPickupFromSlot(par4EntityPlayer, var15);
/* 385:    */             }
/* 386:    */           }
/* 387:    */         }
/* 388:    */       }
/* 389:503 */       detectAndSendChanges();
/* 390:    */     }
/* 391:507 */     return var5;
/* 392:    */   }
/* 393:    */   
/* 394:    */   public boolean func_94530_a(ItemStack par1ItemStack, Slot par2Slot)
/* 395:    */   {
/* 396:512 */     return true;
/* 397:    */   }
/* 398:    */   
/* 399:    */   protected void retrySlotClick(int par1, int par2, boolean par3, EntityPlayer par4EntityPlayer)
/* 400:    */   {
/* 401:517 */     slotClick(par1, par2, 1, par4EntityPlayer);
/* 402:    */   }
/* 403:    */   
/* 404:    */   public void onContainerClosed(EntityPlayer par1EntityPlayer)
/* 405:    */   {
/* 406:525 */     InventoryPlayer var2 = par1EntityPlayer.inventory;
/* 407:527 */     if (var2.getItemStack() != null)
/* 408:    */     {
/* 409:529 */       par1EntityPlayer.dropPlayerItemWithRandomChoice(var2.getItemStack(), false);
/* 410:530 */       var2.setItemStack(null);
/* 411:    */     }
/* 412:    */   }
/* 413:    */   
/* 414:    */   public void onCraftMatrixChanged(IInventory par1IInventory)
/* 415:    */   {
/* 416:539 */     detectAndSendChanges();
/* 417:    */   }
/* 418:    */   
/* 419:    */   public void putStackInSlot(int par1, ItemStack par2ItemStack)
/* 420:    */   {
/* 421:547 */     getSlot(par1).putStack(par2ItemStack);
/* 422:    */   }
/* 423:    */   
/* 424:    */   public void putStacksInSlots(ItemStack[] par1ArrayOfItemStack)
/* 425:    */   {
/* 426:555 */     for (int var2 = 0; var2 < par1ArrayOfItemStack.length; var2++) {
/* 427:557 */       getSlot(var2).putStack(par1ArrayOfItemStack[var2]);
/* 428:    */     }
/* 429:    */   }
/* 430:    */   
/* 431:    */   public void updateProgressBar(int par1, int par2) {}
/* 432:    */   
/* 433:    */   public short getNextTransactionID(InventoryPlayer par1InventoryPlayer)
/* 434:    */   {
/* 435:568 */     this.transactionID = ((short)(this.transactionID + 1));
/* 436:569 */     return this.transactionID;
/* 437:    */   }
/* 438:    */   
/* 439:    */   public boolean isPlayerNotUsingContainer(EntityPlayer par1EntityPlayer)
/* 440:    */   {
/* 441:577 */     return !this.playerList.contains(par1EntityPlayer);
/* 442:    */   }
/* 443:    */   
/* 444:    */   public void setPlayerIsPresent(EntityPlayer par1EntityPlayer, boolean par2)
/* 445:    */   {
/* 446:585 */     if (par2) {
/* 447:587 */       this.playerList.remove(par1EntityPlayer);
/* 448:    */     } else {
/* 449:591 */       this.playerList.add(par1EntityPlayer);
/* 450:    */     }
/* 451:    */   }
/* 452:    */   
/* 453:    */   public abstract boolean canInteractWith(EntityPlayer paramEntityPlayer);
/* 454:    */   
/* 455:    */   protected boolean mergeItemStack(ItemStack par1ItemStack, int par2, int par3, boolean par4)
/* 456:    */   {
/* 457:602 */     boolean var5 = false;
/* 458:603 */     int var6 = par2;
/* 459:605 */     if (par4) {
/* 460:607 */       var6 = par3 - 1;
/* 461:    */     }
/* 462:613 */     if (par1ItemStack.isStackable()) {
/* 463:615 */       while ((par1ItemStack.stackSize > 0) && (((!par4) && (var6 < par3)) || ((par4) && (var6 >= par2))))
/* 464:    */       {
/* 465:617 */         Slot var7 = (Slot)this.inventorySlots.get(var6);
/* 466:618 */         ItemStack var8 = var7.getStack();
/* 467:620 */         if ((var8 != null) && (var8.getItem() == par1ItemStack.getItem()) && ((!par1ItemStack.getHasSubtypes()) || (par1ItemStack.getItemDamage() == var8.getItemDamage())) && (ItemStack.areItemStackTagsEqual(par1ItemStack, var8)))
/* 468:    */         {
/* 469:622 */           int var9 = var8.stackSize + par1ItemStack.stackSize;
/* 470:624 */           if (var9 <= par1ItemStack.getMaxStackSize())
/* 471:    */           {
/* 472:626 */             par1ItemStack.stackSize = 0;
/* 473:627 */             var8.stackSize = var9;
/* 474:628 */             var7.onSlotChanged();
/* 475:629 */             var5 = true;
/* 476:    */           }
/* 477:631 */           else if (var8.stackSize < par1ItemStack.getMaxStackSize())
/* 478:    */           {
/* 479:633 */             par1ItemStack.stackSize -= par1ItemStack.getMaxStackSize() - var8.stackSize;
/* 480:634 */             var8.stackSize = par1ItemStack.getMaxStackSize();
/* 481:635 */             var7.onSlotChanged();
/* 482:636 */             var5 = true;
/* 483:    */           }
/* 484:    */         }
/* 485:640 */         if (par4) {
/* 486:642 */           var6--;
/* 487:    */         } else {
/* 488:646 */           var6++;
/* 489:    */         }
/* 490:    */       }
/* 491:    */     }
/* 492:651 */     if (par1ItemStack.stackSize > 0)
/* 493:    */     {
/* 494:653 */       if (par4) {
/* 495:655 */         var6 = par3 - 1;
/* 496:    */       } else {
/* 497:659 */         var6 = par2;
/* 498:    */       }
/* 499:662 */       while (((!par4) && (var6 < par3)) || ((par4) && (var6 >= par2)))
/* 500:    */       {
/* 501:664 */         Slot var7 = (Slot)this.inventorySlots.get(var6);
/* 502:665 */         ItemStack var8 = var7.getStack();
/* 503:667 */         if (var8 == null)
/* 504:    */         {
/* 505:669 */           var7.putStack(par1ItemStack.copy());
/* 506:670 */           var7.onSlotChanged();
/* 507:671 */           par1ItemStack.stackSize = 0;
/* 508:672 */           var5 = true;
/* 509:673 */           break;
/* 510:    */         }
/* 511:676 */         if (par4) {
/* 512:678 */           var6--;
/* 513:    */         } else {
/* 514:682 */           var6++;
/* 515:    */         }
/* 516:    */       }
/* 517:    */     }
/* 518:687 */     return var5;
/* 519:    */   }
/* 520:    */   
/* 521:    */   public static int func_94529_b(int par0)
/* 522:    */   {
/* 523:692 */     return par0 >> 2 & 0x3;
/* 524:    */   }
/* 525:    */   
/* 526:    */   public static int func_94532_c(int par0)
/* 527:    */   {
/* 528:697 */     return par0 & 0x3;
/* 529:    */   }
/* 530:    */   
/* 531:    */   public static int func_94534_d(int par0, int par1)
/* 532:    */   {
/* 533:702 */     return par0 & 0x3 | (par1 & 0x3) << 2;
/* 534:    */   }
/* 535:    */   
/* 536:    */   public static boolean func_94528_d(int par0)
/* 537:    */   {
/* 538:707 */     return (par0 == 0) || (par0 == 1);
/* 539:    */   }
/* 540:    */   
/* 541:    */   protected void func_94533_d()
/* 542:    */   {
/* 543:712 */     this.field_94536_g = 0;
/* 544:713 */     this.field_94537_h.clear();
/* 545:    */   }
/* 546:    */   
/* 547:    */   public static boolean func_94527_a(Slot par0Slot, ItemStack par1ItemStack, boolean par2)
/* 548:    */   {
/* 549:718 */     boolean var3 = (par0Slot == null) || (!par0Slot.getHasStack());
/* 550:720 */     if ((par0Slot != null) && (par0Slot.getHasStack()) && (par1ItemStack != null) && (par1ItemStack.isItemEqual(par0Slot.getStack())) && (ItemStack.areItemStackTagsEqual(par0Slot.getStack(), par1ItemStack)))
/* 551:    */     {
/* 552:722 */       int var10002 = par2 ? 0 : par1ItemStack.stackSize;
/* 553:723 */       var3 |= par0Slot.getStack().stackSize + var10002 <= par1ItemStack.getMaxStackSize();
/* 554:    */     }
/* 555:726 */     return var3;
/* 556:    */   }
/* 557:    */   
/* 558:    */   public static void func_94525_a(Set par0Set, int par1, ItemStack par2ItemStack, int par3)
/* 559:    */   {
/* 560:731 */     switch (par1)
/* 561:    */     {
/* 562:    */     case 0: 
/* 563:734 */       par2ItemStack.stackSize = MathHelper.floor_float(par2ItemStack.stackSize / par0Set.size());
/* 564:735 */       break;
/* 565:    */     case 1: 
/* 566:738 */       par2ItemStack.stackSize = 1;
/* 567:    */     }
/* 568:739 */     ItemStack tmp53_52 = par2ItemStack;
/* 569:    */     
/* 570:741 */     tmp53_52.stackSize = (tmp53_52.stackSize + par3);
/* 571:    */   }
/* 572:    */   
/* 573:    */   public boolean canDragIntoSlot(Slot par1Slot)
/* 574:    */   {
/* 575:750 */     return true;
/* 576:    */   }
/* 577:    */   
/* 578:    */   public static int calcRedstoneFromInventory(IInventory par0IInventory)
/* 579:    */   {
/* 580:755 */     if (par0IInventory == null) {
/* 581:757 */       return 0;
/* 582:    */     }
/* 583:761 */     int var1 = 0;
/* 584:762 */     float var2 = 0.0F;
/* 585:764 */     for (int var3 = 0; var3 < par0IInventory.getSizeInventory(); var3++)
/* 586:    */     {
/* 587:766 */       ItemStack var4 = par0IInventory.getStackInSlot(var3);
/* 588:768 */       if (var4 != null)
/* 589:    */       {
/* 590:770 */         var2 += var4.stackSize / Math.min(par0IInventory.getInventoryStackLimit(), var4.getMaxStackSize());
/* 591:771 */         var1++;
/* 592:    */       }
/* 593:    */     }
/* 594:775 */     var2 /= par0IInventory.getSizeInventory();
/* 595:776 */     return MathHelper.floor_float(var2 * 14.0F) + (var1 > 0 ? 1 : 0);
/* 596:    */   }
/* 597:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.inventory.Container
 * JD-Core Version:    0.7.0.1
 */