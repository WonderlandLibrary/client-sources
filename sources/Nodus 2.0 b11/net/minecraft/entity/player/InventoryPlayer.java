/*   1:    */ package net.minecraft.entity.player;
/*   2:    */ 
/*   3:    */ import java.util.concurrent.Callable;
/*   4:    */ import net.minecraft.block.Block;
/*   5:    */ import net.minecraft.block.material.Material;
/*   6:    */ import net.minecraft.crash.CrashReport;
/*   7:    */ import net.minecraft.crash.CrashReportCategory;
/*   8:    */ import net.minecraft.inventory.IInventory;
/*   9:    */ import net.minecraft.item.Item;
/*  10:    */ import net.minecraft.item.ItemArmor;
/*  11:    */ import net.minecraft.item.ItemStack;
/*  12:    */ import net.minecraft.nbt.NBTTagCompound;
/*  13:    */ import net.minecraft.nbt.NBTTagList;
/*  14:    */ import net.minecraft.util.ReportedException;
/*  15:    */ 
/*  16:    */ public class InventoryPlayer
/*  17:    */   implements IInventory
/*  18:    */ {
/*  19: 20 */   public ItemStack[] mainInventory = new ItemStack[36];
/*  20: 23 */   public ItemStack[] armorInventory = new ItemStack[4];
/*  21:    */   public int currentItem;
/*  22:    */   private ItemStack currentItemStack;
/*  23:    */   public EntityPlayer player;
/*  24:    */   private ItemStack itemStack;
/*  25:    */   public boolean inventoryChanged;
/*  26:    */   private static final String __OBFID = "CL_00001709";
/*  27:    */   
/*  28:    */   public InventoryPlayer(EntityPlayer par1EntityPlayer)
/*  29:    */   {
/*  30: 44 */     this.player = par1EntityPlayer;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public ItemStack getCurrentItem()
/*  34:    */   {
/*  35: 52 */     return (this.currentItem < 9) && (this.currentItem >= 0) ? this.mainInventory[this.currentItem] : null;
/*  36:    */   }
/*  37:    */   
/*  38:    */   public static int getHotbarSize()
/*  39:    */   {
/*  40: 60 */     return 9;
/*  41:    */   }
/*  42:    */   
/*  43:    */   private int func_146029_c(Item p_146029_1_)
/*  44:    */   {
/*  45: 65 */     for (int var2 = 0; var2 < this.mainInventory.length; var2++) {
/*  46: 67 */       if ((this.mainInventory[var2] != null) && (this.mainInventory[var2].getItem() == p_146029_1_)) {
/*  47: 69 */         return var2;
/*  48:    */       }
/*  49:    */     }
/*  50: 73 */     return -1;
/*  51:    */   }
/*  52:    */   
/*  53:    */   private int func_146024_c(Item p_146024_1_, int p_146024_2_)
/*  54:    */   {
/*  55: 78 */     for (int var3 = 0; var3 < this.mainInventory.length; var3++) {
/*  56: 80 */       if ((this.mainInventory[var3] != null) && (this.mainInventory[var3].getItem() == p_146024_1_) && (this.mainInventory[var3].getItemDamage() == p_146024_2_)) {
/*  57: 82 */         return var3;
/*  58:    */       }
/*  59:    */     }
/*  60: 86 */     return -1;
/*  61:    */   }
/*  62:    */   
/*  63:    */   private int storeItemStack(ItemStack par1ItemStack)
/*  64:    */   {
/*  65: 94 */     for (int var2 = 0; var2 < this.mainInventory.length; var2++) {
/*  66: 96 */       if ((this.mainInventory[var2] != null) && (this.mainInventory[var2].getItem() == par1ItemStack.getItem()) && (this.mainInventory[var2].isStackable()) && (this.mainInventory[var2].stackSize < this.mainInventory[var2].getMaxStackSize()) && (this.mainInventory[var2].stackSize < getInventoryStackLimit()) && ((!this.mainInventory[var2].getHasSubtypes()) || (this.mainInventory[var2].getItemDamage() == par1ItemStack.getItemDamage())) && (ItemStack.areItemStackTagsEqual(this.mainInventory[var2], par1ItemStack))) {
/*  67: 98 */         return var2;
/*  68:    */       }
/*  69:    */     }
/*  70:102 */     return -1;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public int getFirstEmptyStack()
/*  74:    */   {
/*  75:110 */     for (int var1 = 0; var1 < this.mainInventory.length; var1++) {
/*  76:112 */       if (this.mainInventory[var1] == null) {
/*  77:114 */         return var1;
/*  78:    */       }
/*  79:    */     }
/*  80:118 */     return -1;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public void func_146030_a(Item p_146030_1_, int p_146030_2_, boolean p_146030_3_, boolean p_146030_4_)
/*  84:    */   {
/*  85:123 */     boolean var5 = true;
/*  86:124 */     this.currentItemStack = getCurrentItem();
/*  87:    */     int var7;
/*  88:    */     int var7;
/*  89:127 */     if (p_146030_3_) {
/*  90:129 */       var7 = func_146024_c(p_146030_1_, p_146030_2_);
/*  91:    */     } else {
/*  92:133 */       var7 = func_146029_c(p_146030_1_);
/*  93:    */     }
/*  94:136 */     if ((var7 >= 0) && (var7 < 9))
/*  95:    */     {
/*  96:138 */       this.currentItem = var7;
/*  97:    */     }
/*  98:142 */     else if ((p_146030_4_) && (p_146030_1_ != null))
/*  99:    */     {
/* 100:144 */       int var6 = getFirstEmptyStack();
/* 101:146 */       if ((var6 >= 0) && (var6 < 9)) {
/* 102:148 */         this.currentItem = var6;
/* 103:    */       }
/* 104:151 */       func_70439_a(p_146030_1_, p_146030_2_);
/* 105:    */     }
/* 106:    */   }
/* 107:    */   
/* 108:    */   public void changeCurrentItem(int par1)
/* 109:    */   {
/* 110:161 */     if (par1 > 0) {
/* 111:163 */       par1 = 1;
/* 112:    */     }
/* 113:166 */     if (par1 < 0) {
/* 114:168 */       par1 = -1;
/* 115:    */     }
/* 116:171 */     for (this.currentItem -= par1; this.currentItem < 0; this.currentItem += 9) {}
/* 117:176 */     while (this.currentItem >= 9) {
/* 118:178 */       this.currentItem -= 9;
/* 119:    */     }
/* 120:    */   }
/* 121:    */   
/* 122:    */   public int clearInventory(Item p_146027_1_, int p_146027_2_)
/* 123:    */   {
/* 124:187 */     int var3 = 0;
/* 125:191 */     for (int var4 = 0; var4 < this.mainInventory.length; var4++)
/* 126:    */     {
/* 127:193 */       ItemStack var5 = this.mainInventory[var4];
/* 128:195 */       if ((var5 != null) && ((p_146027_1_ == null) || (var5.getItem() == p_146027_1_)) && ((p_146027_2_ <= -1) || (var5.getItemDamage() == p_146027_2_)))
/* 129:    */       {
/* 130:197 */         var3 += var5.stackSize;
/* 131:198 */         this.mainInventory[var4] = null;
/* 132:    */       }
/* 133:    */     }
/* 134:202 */     for (var4 = 0; var4 < this.armorInventory.length; var4++)
/* 135:    */     {
/* 136:204 */       ItemStack var5 = this.armorInventory[var4];
/* 137:206 */       if ((var5 != null) && ((p_146027_1_ == null) || (var5.getItem() == p_146027_1_)) && ((p_146027_2_ <= -1) || (var5.getItemDamage() == p_146027_2_)))
/* 138:    */       {
/* 139:208 */         var3 += var5.stackSize;
/* 140:209 */         this.armorInventory[var4] = null;
/* 141:    */       }
/* 142:    */     }
/* 143:213 */     if (this.itemStack != null)
/* 144:    */     {
/* 145:215 */       if ((p_146027_1_ != null) && (this.itemStack.getItem() != p_146027_1_)) {
/* 146:217 */         return var3;
/* 147:    */       }
/* 148:220 */       if ((p_146027_2_ > -1) && (this.itemStack.getItemDamage() != p_146027_2_)) {
/* 149:222 */         return var3;
/* 150:    */       }
/* 151:225 */       var3 += this.itemStack.stackSize;
/* 152:226 */       setItemStack(null);
/* 153:    */     }
/* 154:229 */     return var3;
/* 155:    */   }
/* 156:    */   
/* 157:    */   public void func_70439_a(Item par1Item, int par2)
/* 158:    */   {
/* 159:234 */     if (par1Item != null)
/* 160:    */     {
/* 161:236 */       if ((this.currentItemStack != null) && (this.currentItemStack.isItemEnchantable()) && (func_146024_c(this.currentItemStack.getItem(), this.currentItemStack.getItemDamageForDisplay()) == this.currentItem)) {
/* 162:238 */         return;
/* 163:    */       }
/* 164:241 */       int var3 = func_146024_c(par1Item, par2);
/* 165:243 */       if (var3 >= 0)
/* 166:    */       {
/* 167:245 */         int var4 = this.mainInventory[var3].stackSize;
/* 168:246 */         this.mainInventory[var3] = this.mainInventory[this.currentItem];
/* 169:247 */         this.mainInventory[this.currentItem] = new ItemStack(par1Item, var4, par2);
/* 170:    */       }
/* 171:    */       else
/* 172:    */       {
/* 173:251 */         this.mainInventory[this.currentItem] = new ItemStack(par1Item, 1, par2);
/* 174:    */       }
/* 175:    */     }
/* 176:    */   }
/* 177:    */   
/* 178:    */   private int storePartialItemStack(ItemStack par1ItemStack)
/* 179:    */   {
/* 180:262 */     Item var2 = par1ItemStack.getItem();
/* 181:263 */     int var3 = par1ItemStack.stackSize;
/* 182:266 */     if (par1ItemStack.getMaxStackSize() == 1)
/* 183:    */     {
/* 184:268 */       int var4 = getFirstEmptyStack();
/* 185:270 */       if (var4 < 0) {
/* 186:272 */         return var3;
/* 187:    */       }
/* 188:276 */       if (this.mainInventory[var4] == null) {
/* 189:278 */         this.mainInventory[var4] = ItemStack.copyItemStack(par1ItemStack);
/* 190:    */       }
/* 191:281 */       return 0;
/* 192:    */     }
/* 193:286 */     int var4 = storeItemStack(par1ItemStack);
/* 194:288 */     if (var4 < 0) {
/* 195:290 */       var4 = getFirstEmptyStack();
/* 196:    */     }
/* 197:293 */     if (var4 < 0) {
/* 198:295 */       return var3;
/* 199:    */     }
/* 200:299 */     if (this.mainInventory[var4] == null)
/* 201:    */     {
/* 202:301 */       this.mainInventory[var4] = new ItemStack(var2, 0, par1ItemStack.getItemDamage());
/* 203:303 */       if (par1ItemStack.hasTagCompound()) {
/* 204:305 */         this.mainInventory[var4].setTagCompound((NBTTagCompound)par1ItemStack.getTagCompound().copy());
/* 205:    */       }
/* 206:    */     }
/* 207:309 */     int var5 = var3;
/* 208:311 */     if (var3 > this.mainInventory[var4].getMaxStackSize() - this.mainInventory[var4].stackSize) {
/* 209:313 */       var5 = this.mainInventory[var4].getMaxStackSize() - this.mainInventory[var4].stackSize;
/* 210:    */     }
/* 211:316 */     if (var5 > getInventoryStackLimit() - this.mainInventory[var4].stackSize) {
/* 212:318 */       var5 = getInventoryStackLimit() - this.mainInventory[var4].stackSize;
/* 213:    */     }
/* 214:321 */     if (var5 == 0) {
/* 215:323 */       return var3;
/* 216:    */     }
/* 217:327 */     var3 -= var5;
/* 218:328 */     this.mainInventory[var4].stackSize += var5;
/* 219:329 */     this.mainInventory[var4].animationsToGo = 5;
/* 220:330 */     return var3;
/* 221:    */   }
/* 222:    */   
/* 223:    */   public void decrementAnimations()
/* 224:    */   {
/* 225:342 */     for (int var1 = 0; var1 < this.mainInventory.length; var1++) {
/* 226:344 */       if (this.mainInventory[var1] != null) {
/* 227:346 */         this.mainInventory[var1].updateAnimation(this.player.worldObj, this.player, var1, this.currentItem == var1);
/* 228:    */       }
/* 229:    */     }
/* 230:    */   }
/* 231:    */   
/* 232:    */   public boolean consumeInventoryItem(Item p_146026_1_)
/* 233:    */   {
/* 234:353 */     int var2 = func_146029_c(p_146026_1_);
/* 235:355 */     if (var2 < 0) {
/* 236:357 */       return false;
/* 237:    */     }
/* 238:361 */     if (--this.mainInventory[var2].stackSize <= 0) {
/* 239:363 */       this.mainInventory[var2] = null;
/* 240:    */     }
/* 241:366 */     return true;
/* 242:    */   }
/* 243:    */   
/* 244:    */   public boolean hasItem(Item p_146028_1_)
/* 245:    */   {
/* 246:372 */     int var2 = func_146029_c(p_146028_1_);
/* 247:373 */     return var2 >= 0;
/* 248:    */   }
/* 249:    */   
/* 250:    */   public boolean addItemStackToInventory(final ItemStack par1ItemStack)
/* 251:    */   {
/* 252:381 */     if ((par1ItemStack != null) && (par1ItemStack.stackSize != 0) && (par1ItemStack.getItem() != null)) {
/* 253:    */       try
/* 254:    */       {
/* 255:387 */         if (par1ItemStack.isItemDamaged())
/* 256:    */         {
/* 257:389 */           int var2 = getFirstEmptyStack();
/* 258:391 */           if (var2 >= 0)
/* 259:    */           {
/* 260:393 */             this.mainInventory[var2] = ItemStack.copyItemStack(par1ItemStack);
/* 261:394 */             this.mainInventory[var2].animationsToGo = 5;
/* 262:395 */             par1ItemStack.stackSize = 0;
/* 263:396 */             return true;
/* 264:    */           }
/* 265:398 */           if (this.player.capabilities.isCreativeMode)
/* 266:    */           {
/* 267:400 */             par1ItemStack.stackSize = 0;
/* 268:401 */             return true;
/* 269:    */           }
/* 270:405 */           return false;
/* 271:    */         }
/* 272:    */         int var2;
/* 273:    */         do
/* 274:    */         {
/* 275:412 */           var2 = par1ItemStack.stackSize;
/* 276:413 */           par1ItemStack.stackSize = storePartialItemStack(par1ItemStack);
/* 277:415 */         } while ((par1ItemStack.stackSize > 0) && (par1ItemStack.stackSize < var2));
/* 278:417 */         if ((par1ItemStack.stackSize == var2) && (this.player.capabilities.isCreativeMode))
/* 279:    */         {
/* 280:419 */           par1ItemStack.stackSize = 0;
/* 281:420 */           return true;
/* 282:    */         }
/* 283:424 */         return par1ItemStack.stackSize < var2;
/* 284:    */       }
/* 285:    */       catch (Throwable var5)
/* 286:    */       {
/* 287:430 */         CrashReport var3 = CrashReport.makeCrashReport(var5, "Adding item to inventory");
/* 288:431 */         CrashReportCategory var4 = var3.makeCategory("Item being added");
/* 289:432 */         var4.addCrashSection("Item ID", Integer.valueOf(Item.getIdFromItem(par1ItemStack.getItem())));
/* 290:433 */         var4.addCrashSection("Item data", Integer.valueOf(par1ItemStack.getItemDamage()));
/* 291:434 */         var4.addCrashSectionCallable("Item name", new Callable()
/* 292:    */         {
/* 293:    */           private static final String __OBFID = "CL_00001710";
/* 294:    */           
/* 295:    */           public String call()
/* 296:    */           {
/* 297:439 */             return par1ItemStack.getDisplayName();
/* 298:    */           }
/* 299:441 */         });
/* 300:442 */         throw new ReportedException(var3);
/* 301:    */       }
/* 302:    */     }
/* 303:447 */     return false;
/* 304:    */   }
/* 305:    */   
/* 306:    */   public ItemStack decrStackSize(int par1, int par2)
/* 307:    */   {
/* 308:457 */     ItemStack[] var3 = this.mainInventory;
/* 309:459 */     if (par1 >= this.mainInventory.length)
/* 310:    */     {
/* 311:461 */       var3 = this.armorInventory;
/* 312:462 */       par1 -= this.mainInventory.length;
/* 313:    */     }
/* 314:465 */     if (var3[par1] != null)
/* 315:    */     {
/* 316:469 */       if (var3[par1].stackSize <= par2)
/* 317:    */       {
/* 318:471 */         ItemStack var4 = var3[par1];
/* 319:472 */         var3[par1] = null;
/* 320:473 */         return var4;
/* 321:    */       }
/* 322:477 */       ItemStack var4 = var3[par1].splitStack(par2);
/* 323:479 */       if (var3[par1].stackSize == 0) {
/* 324:481 */         var3[par1] = null;
/* 325:    */       }
/* 326:484 */       return var4;
/* 327:    */     }
/* 328:489 */     return null;
/* 329:    */   }
/* 330:    */   
/* 331:    */   public ItemStack getStackInSlotOnClosing(int par1)
/* 332:    */   {
/* 333:499 */     ItemStack[] var2 = this.mainInventory;
/* 334:501 */     if (par1 >= this.mainInventory.length)
/* 335:    */     {
/* 336:503 */       var2 = this.armorInventory;
/* 337:504 */       par1 -= this.mainInventory.length;
/* 338:    */     }
/* 339:507 */     if (var2[par1] != null)
/* 340:    */     {
/* 341:509 */       ItemStack var3 = var2[par1];
/* 342:510 */       var2[par1] = null;
/* 343:511 */       return var3;
/* 344:    */     }
/* 345:515 */     return null;
/* 346:    */   }
/* 347:    */   
/* 348:    */   public void setInventorySlotContents(int par1, ItemStack par2ItemStack)
/* 349:    */   {
/* 350:524 */     ItemStack[] var3 = this.mainInventory;
/* 351:526 */     if (par1 >= var3.length)
/* 352:    */     {
/* 353:528 */       par1 -= var3.length;
/* 354:529 */       var3 = this.armorInventory;
/* 355:    */     }
/* 356:532 */     var3[par1] = par2ItemStack;
/* 357:    */   }
/* 358:    */   
/* 359:    */   public float func_146023_a(Block p_146023_1_)
/* 360:    */   {
/* 361:537 */     float var2 = 1.0F;
/* 362:539 */     if (this.mainInventory[this.currentItem] != null) {
/* 363:541 */       var2 *= this.mainInventory[this.currentItem].func_150997_a(p_146023_1_);
/* 364:    */     }
/* 365:544 */     return var2;
/* 366:    */   }
/* 367:    */   
/* 368:    */   public NBTTagList writeToNBT(NBTTagList par1NBTTagList)
/* 369:    */   {
/* 370:556 */     for (int var2 = 0; var2 < this.mainInventory.length; var2++) {
/* 371:558 */       if (this.mainInventory[var2] != null)
/* 372:    */       {
/* 373:560 */         NBTTagCompound var3 = new NBTTagCompound();
/* 374:561 */         var3.setByte("Slot", (byte)var2);
/* 375:562 */         this.mainInventory[var2].writeToNBT(var3);
/* 376:563 */         par1NBTTagList.appendTag(var3);
/* 377:    */       }
/* 378:    */     }
/* 379:567 */     for (var2 = 0; var2 < this.armorInventory.length; var2++) {
/* 380:569 */       if (this.armorInventory[var2] != null)
/* 381:    */       {
/* 382:571 */         NBTTagCompound var3 = new NBTTagCompound();
/* 383:572 */         var3.setByte("Slot", (byte)(var2 + 100));
/* 384:573 */         this.armorInventory[var2].writeToNBT(var3);
/* 385:574 */         par1NBTTagList.appendTag(var3);
/* 386:    */       }
/* 387:    */     }
/* 388:578 */     return par1NBTTagList;
/* 389:    */   }
/* 390:    */   
/* 391:    */   public void readFromNBT(NBTTagList par1NBTTagList)
/* 392:    */   {
/* 393:586 */     this.mainInventory = new ItemStack[36];
/* 394:587 */     this.armorInventory = new ItemStack[4];
/* 395:589 */     for (int var2 = 0; var2 < par1NBTTagList.tagCount(); var2++)
/* 396:    */     {
/* 397:591 */       NBTTagCompound var3 = par1NBTTagList.getCompoundTagAt(var2);
/* 398:592 */       int var4 = var3.getByte("Slot") & 0xFF;
/* 399:593 */       ItemStack var5 = ItemStack.loadItemStackFromNBT(var3);
/* 400:595 */       if (var5 != null)
/* 401:    */       {
/* 402:597 */         if ((var4 >= 0) && (var4 < this.mainInventory.length)) {
/* 403:599 */           this.mainInventory[var4] = var5;
/* 404:    */         }
/* 405:602 */         if ((var4 >= 100) && (var4 < this.armorInventory.length + 100)) {
/* 406:604 */           this.armorInventory[(var4 - 100)] = var5;
/* 407:    */         }
/* 408:    */       }
/* 409:    */     }
/* 410:    */   }
/* 411:    */   
/* 412:    */   public int getSizeInventory()
/* 413:    */   {
/* 414:615 */     return this.mainInventory.length + 4;
/* 415:    */   }
/* 416:    */   
/* 417:    */   public ItemStack getStackInSlot(int par1)
/* 418:    */   {
/* 419:623 */     ItemStack[] var2 = this.mainInventory;
/* 420:625 */     if (par1 >= var2.length)
/* 421:    */     {
/* 422:627 */       par1 -= var2.length;
/* 423:628 */       var2 = this.armorInventory;
/* 424:    */     }
/* 425:631 */     return var2[par1];
/* 426:    */   }
/* 427:    */   
/* 428:    */   public String getInventoryName()
/* 429:    */   {
/* 430:639 */     return "container.inventory";
/* 431:    */   }
/* 432:    */   
/* 433:    */   public boolean isInventoryNameLocalized()
/* 434:    */   {
/* 435:647 */     return false;
/* 436:    */   }
/* 437:    */   
/* 438:    */   public int getInventoryStackLimit()
/* 439:    */   {
/* 440:655 */     return 64;
/* 441:    */   }
/* 442:    */   
/* 443:    */   public boolean func_146025_b(Block p_146025_1_)
/* 444:    */   {
/* 445:660 */     if (p_146025_1_.getMaterial().isToolNotRequired()) {
/* 446:662 */       return true;
/* 447:    */     }
/* 448:666 */     ItemStack var2 = getStackInSlot(this.currentItem);
/* 449:667 */     return var2 != null ? var2.func_150998_b(p_146025_1_) : false;
/* 450:    */   }
/* 451:    */   
/* 452:    */   public ItemStack armorItemInSlot(int par1)
/* 453:    */   {
/* 454:676 */     return this.armorInventory[par1];
/* 455:    */   }
/* 456:    */   
/* 457:    */   public int getTotalArmorValue()
/* 458:    */   {
/* 459:684 */     int var1 = 0;
/* 460:686 */     for (int var2 = 0; var2 < this.armorInventory.length; var2++) {
/* 461:688 */       if ((this.armorInventory[var2] != null) && ((this.armorInventory[var2].getItem() instanceof ItemArmor)))
/* 462:    */       {
/* 463:690 */         int var3 = ((ItemArmor)this.armorInventory[var2].getItem()).damageReduceAmount;
/* 464:691 */         var1 += var3;
/* 465:    */       }
/* 466:    */     }
/* 467:695 */     return var1;
/* 468:    */   }
/* 469:    */   
/* 470:    */   public void damageArmor(float par1)
/* 471:    */   {
/* 472:703 */     par1 /= 4.0F;
/* 473:705 */     if (par1 < 1.0F) {
/* 474:707 */       par1 = 1.0F;
/* 475:    */     }
/* 476:710 */     for (int var2 = 0; var2 < this.armorInventory.length; var2++) {
/* 477:712 */       if ((this.armorInventory[var2] != null) && ((this.armorInventory[var2].getItem() instanceof ItemArmor)))
/* 478:    */       {
/* 479:714 */         this.armorInventory[var2].damageItem((int)par1, this.player);
/* 480:716 */         if (this.armorInventory[var2].stackSize == 0) {
/* 481:718 */           this.armorInventory[var2] = null;
/* 482:    */         }
/* 483:    */       }
/* 484:    */     }
/* 485:    */   }
/* 486:    */   
/* 487:    */   public void dropAllItems()
/* 488:    */   {
/* 489:731 */     for (int var1 = 0; var1 < this.mainInventory.length; var1++) {
/* 490:733 */       if (this.mainInventory[var1] != null)
/* 491:    */       {
/* 492:735 */         this.player.func_146097_a(this.mainInventory[var1], true, false);
/* 493:736 */         this.mainInventory[var1] = null;
/* 494:    */       }
/* 495:    */     }
/* 496:740 */     for (var1 = 0; var1 < this.armorInventory.length; var1++) {
/* 497:742 */       if (this.armorInventory[var1] != null)
/* 498:    */       {
/* 499:744 */         this.player.func_146097_a(this.armorInventory[var1], true, false);
/* 500:745 */         this.armorInventory[var1] = null;
/* 501:    */       }
/* 502:    */     }
/* 503:    */   }
/* 504:    */   
/* 505:    */   public void onInventoryChanged()
/* 506:    */   {
/* 507:755 */     this.inventoryChanged = true;
/* 508:    */   }
/* 509:    */   
/* 510:    */   public void setItemStack(ItemStack par1ItemStack)
/* 511:    */   {
/* 512:760 */     this.itemStack = par1ItemStack;
/* 513:    */   }
/* 514:    */   
/* 515:    */   public ItemStack getItemStack()
/* 516:    */   {
/* 517:765 */     return this.itemStack;
/* 518:    */   }
/* 519:    */   
/* 520:    */   public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer)
/* 521:    */   {
/* 522:773 */     return !this.player.isDead;
/* 523:    */   }
/* 524:    */   
/* 525:    */   public boolean hasItemStack(ItemStack par1ItemStack)
/* 526:    */   {
/* 527:783 */     for (int var2 = 0; var2 < this.armorInventory.length; var2++) {
/* 528:785 */       if ((this.armorInventory[var2] != null) && (this.armorInventory[var2].isItemEqual(par1ItemStack))) {
/* 529:787 */         return true;
/* 530:    */       }
/* 531:    */     }
/* 532:791 */     for (var2 = 0; var2 < this.mainInventory.length; var2++) {
/* 533:793 */       if ((this.mainInventory[var2] != null) && (this.mainInventory[var2].isItemEqual(par1ItemStack))) {
/* 534:795 */         return true;
/* 535:    */       }
/* 536:    */     }
/* 537:799 */     return false;
/* 538:    */   }
/* 539:    */   
/* 540:    */   public void openInventory() {}
/* 541:    */   
/* 542:    */   public void closeInventory() {}
/* 543:    */   
/* 544:    */   public boolean isItemValidForSlot(int par1, ItemStack par2ItemStack)
/* 545:    */   {
/* 546:811 */     return true;
/* 547:    */   }
/* 548:    */   
/* 549:    */   public void copyInventory(InventoryPlayer par1InventoryPlayer)
/* 550:    */   {
/* 551:821 */     for (int var2 = 0; var2 < this.mainInventory.length; var2++) {
/* 552:823 */       this.mainInventory[var2] = ItemStack.copyItemStack(par1InventoryPlayer.mainInventory[var2]);
/* 553:    */     }
/* 554:826 */     for (var2 = 0; var2 < this.armorInventory.length; var2++) {
/* 555:828 */       this.armorInventory[var2] = ItemStack.copyItemStack(par1InventoryPlayer.armorInventory[var2]);
/* 556:    */     }
/* 557:831 */     this.currentItem = par1InventoryPlayer.currentItem;
/* 558:    */   }
/* 559:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.entity.player.InventoryPlayer
 * JD-Core Version:    0.7.0.1
 */