/*   1:    */ package net.minecraft.tileentity;
/*   2:    */ 
/*   3:    */ import java.util.List;
/*   4:    */ import java.util.Random;
/*   5:    */ import net.minecraft.block.Block;
/*   6:    */ import net.minecraft.block.BlockChest;
/*   7:    */ import net.minecraft.block.BlockHopper;
/*   8:    */ import net.minecraft.command.IEntitySelector;
/*   9:    */ import net.minecraft.entity.item.EntityItem;
/*  10:    */ import net.minecraft.entity.player.EntityPlayer;
/*  11:    */ import net.minecraft.inventory.IInventory;
/*  12:    */ import net.minecraft.inventory.ISidedInventory;
/*  13:    */ import net.minecraft.item.ItemStack;
/*  14:    */ import net.minecraft.nbt.NBTTagCompound;
/*  15:    */ import net.minecraft.nbt.NBTTagList;
/*  16:    */ import net.minecraft.util.AABBPool;
/*  17:    */ import net.minecraft.util.AxisAlignedBB;
/*  18:    */ import net.minecraft.util.MathHelper;
/*  19:    */ import net.minecraft.world.World;
/*  20:    */ 
/*  21:    */ public class TileEntityHopper
/*  22:    */   extends TileEntity
/*  23:    */   implements IHopper
/*  24:    */ {
/*  25: 23 */   private ItemStack[] field_145900_a = new ItemStack[5];
/*  26:    */   private String field_145902_i;
/*  27: 25 */   private int field_145901_j = -1;
/*  28:    */   private static final String __OBFID = "CL_00000359";
/*  29:    */   
/*  30:    */   public void readFromNBT(NBTTagCompound p_145839_1_)
/*  31:    */   {
/*  32: 30 */     super.readFromNBT(p_145839_1_);
/*  33: 31 */     NBTTagList var2 = p_145839_1_.getTagList("Items", 10);
/*  34: 32 */     this.field_145900_a = new ItemStack[getSizeInventory()];
/*  35: 34 */     if (p_145839_1_.func_150297_b("CustomName", 8)) {
/*  36: 36 */       this.field_145902_i = p_145839_1_.getString("CustomName");
/*  37:    */     }
/*  38: 39 */     this.field_145901_j = p_145839_1_.getInteger("TransferCooldown");
/*  39: 41 */     for (int var3 = 0; var3 < var2.tagCount(); var3++)
/*  40:    */     {
/*  41: 43 */       NBTTagCompound var4 = var2.getCompoundTagAt(var3);
/*  42: 44 */       byte var5 = var4.getByte("Slot");
/*  43: 46 */       if ((var5 >= 0) && (var5 < this.field_145900_a.length)) {
/*  44: 48 */         this.field_145900_a[var5] = ItemStack.loadItemStackFromNBT(var4);
/*  45:    */       }
/*  46:    */     }
/*  47:    */   }
/*  48:    */   
/*  49:    */   public void writeToNBT(NBTTagCompound p_145841_1_)
/*  50:    */   {
/*  51: 55 */     super.writeToNBT(p_145841_1_);
/*  52: 56 */     NBTTagList var2 = new NBTTagList();
/*  53: 58 */     for (int var3 = 0; var3 < this.field_145900_a.length; var3++) {
/*  54: 60 */       if (this.field_145900_a[var3] != null)
/*  55:    */       {
/*  56: 62 */         NBTTagCompound var4 = new NBTTagCompound();
/*  57: 63 */         var4.setByte("Slot", (byte)var3);
/*  58: 64 */         this.field_145900_a[var3].writeToNBT(var4);
/*  59: 65 */         var2.appendTag(var4);
/*  60:    */       }
/*  61:    */     }
/*  62: 69 */     p_145841_1_.setTag("Items", var2);
/*  63: 70 */     p_145841_1_.setInteger("TransferCooldown", this.field_145901_j);
/*  64: 72 */     if (isInventoryNameLocalized()) {
/*  65: 74 */       p_145841_1_.setString("CustomName", this.field_145902_i);
/*  66:    */     }
/*  67:    */   }
/*  68:    */   
/*  69:    */   public void onInventoryChanged()
/*  70:    */   {
/*  71: 83 */     super.onInventoryChanged();
/*  72:    */   }
/*  73:    */   
/*  74:    */   public int getSizeInventory()
/*  75:    */   {
/*  76: 91 */     return this.field_145900_a.length;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public ItemStack getStackInSlot(int par1)
/*  80:    */   {
/*  81: 99 */     return this.field_145900_a[par1];
/*  82:    */   }
/*  83:    */   
/*  84:    */   public ItemStack decrStackSize(int par1, int par2)
/*  85:    */   {
/*  86:108 */     if (this.field_145900_a[par1] != null)
/*  87:    */     {
/*  88:112 */       if (this.field_145900_a[par1].stackSize <= par2)
/*  89:    */       {
/*  90:114 */         ItemStack var3 = this.field_145900_a[par1];
/*  91:115 */         this.field_145900_a[par1] = null;
/*  92:116 */         return var3;
/*  93:    */       }
/*  94:120 */       ItemStack var3 = this.field_145900_a[par1].splitStack(par2);
/*  95:122 */       if (this.field_145900_a[par1].stackSize == 0) {
/*  96:124 */         this.field_145900_a[par1] = null;
/*  97:    */       }
/*  98:127 */       return var3;
/*  99:    */     }
/* 100:132 */     return null;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public ItemStack getStackInSlotOnClosing(int par1)
/* 104:    */   {
/* 105:142 */     if (this.field_145900_a[par1] != null)
/* 106:    */     {
/* 107:144 */       ItemStack var2 = this.field_145900_a[par1];
/* 108:145 */       this.field_145900_a[par1] = null;
/* 109:146 */       return var2;
/* 110:    */     }
/* 111:150 */     return null;
/* 112:    */   }
/* 113:    */   
/* 114:    */   public void setInventorySlotContents(int par1, ItemStack par2ItemStack)
/* 115:    */   {
/* 116:159 */     this.field_145900_a[par1] = par2ItemStack;
/* 117:161 */     if ((par2ItemStack != null) && (par2ItemStack.stackSize > getInventoryStackLimit())) {
/* 118:163 */       par2ItemStack.stackSize = getInventoryStackLimit();
/* 119:    */     }
/* 120:    */   }
/* 121:    */   
/* 122:    */   public String getInventoryName()
/* 123:    */   {
/* 124:172 */     return isInventoryNameLocalized() ? this.field_145902_i : "container.hopper";
/* 125:    */   }
/* 126:    */   
/* 127:    */   public boolean isInventoryNameLocalized()
/* 128:    */   {
/* 129:180 */     return (this.field_145902_i != null) && (this.field_145902_i.length() > 0);
/* 130:    */   }
/* 131:    */   
/* 132:    */   public void func_145886_a(String p_145886_1_)
/* 133:    */   {
/* 134:185 */     this.field_145902_i = p_145886_1_;
/* 135:    */   }
/* 136:    */   
/* 137:    */   public int getInventoryStackLimit()
/* 138:    */   {
/* 139:193 */     return 64;
/* 140:    */   }
/* 141:    */   
/* 142:    */   public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer)
/* 143:    */   {
/* 144:201 */     return this.worldObj.getTileEntity(this.field_145851_c, this.field_145848_d, this.field_145849_e) == this;
/* 145:    */   }
/* 146:    */   
/* 147:    */   public void openInventory() {}
/* 148:    */   
/* 149:    */   public void closeInventory() {}
/* 150:    */   
/* 151:    */   public boolean isItemValidForSlot(int par1, ItemStack par2ItemStack)
/* 152:    */   {
/* 153:213 */     return true;
/* 154:    */   }
/* 155:    */   
/* 156:    */   public void updateEntity()
/* 157:    */   {
/* 158:218 */     if ((this.worldObj != null) && (!this.worldObj.isClient))
/* 159:    */     {
/* 160:220 */       this.field_145901_j -= 1;
/* 161:222 */       if (!func_145888_j())
/* 162:    */       {
/* 163:224 */         func_145896_c(0);
/* 164:225 */         func_145887_i();
/* 165:    */       }
/* 166:    */     }
/* 167:    */   }
/* 168:    */   
/* 169:    */   public boolean func_145887_i()
/* 170:    */   {
/* 171:232 */     if ((this.worldObj != null) && (!this.worldObj.isClient))
/* 172:    */     {
/* 173:234 */       if ((!func_145888_j()) && (BlockHopper.func_149917_c(getBlockMetadata())))
/* 174:    */       {
/* 175:236 */         boolean var1 = func_145883_k();
/* 176:237 */         var1 = (func_145891_a(this)) || (var1);
/* 177:239 */         if (var1)
/* 178:    */         {
/* 179:241 */           func_145896_c(8);
/* 180:242 */           onInventoryChanged();
/* 181:243 */           return true;
/* 182:    */         }
/* 183:    */       }
/* 184:247 */       return false;
/* 185:    */     }
/* 186:251 */     return false;
/* 187:    */   }
/* 188:    */   
/* 189:    */   private boolean func_145883_k()
/* 190:    */   {
/* 191:257 */     IInventory var1 = func_145895_l();
/* 192:259 */     if (var1 == null) {
/* 193:261 */       return false;
/* 194:    */     }
/* 195:265 */     for (int var2 = 0; var2 < getSizeInventory(); var2++) {
/* 196:267 */       if (getStackInSlot(var2) != null)
/* 197:    */       {
/* 198:269 */         ItemStack var3 = getStackInSlot(var2).copy();
/* 199:270 */         ItemStack var4 = func_145889_a(var1, decrStackSize(var2, 1), net.minecraft.util.Facing.oppositeSide[BlockHopper.func_149918_b(getBlockMetadata())]);
/* 200:272 */         if ((var4 == null) || (var4.stackSize == 0))
/* 201:    */         {
/* 202:274 */           var1.onInventoryChanged();
/* 203:275 */           return true;
/* 204:    */         }
/* 205:278 */         setInventorySlotContents(var2, var3);
/* 206:    */       }
/* 207:    */     }
/* 208:282 */     return false;
/* 209:    */   }
/* 210:    */   
/* 211:    */   public static boolean func_145891_a(IHopper p_145891_0_)
/* 212:    */   {
/* 213:288 */     IInventory var1 = func_145884_b(p_145891_0_);
/* 214:290 */     if (var1 != null)
/* 215:    */     {
/* 216:292 */       byte var2 = 0;
/* 217:294 */       if (((var1 instanceof ISidedInventory)) && (var2 > -1))
/* 218:    */       {
/* 219:296 */         ISidedInventory var7 = (ISidedInventory)var1;
/* 220:297 */         int[] var8 = var7.getAccessibleSlotsFromSide(var2);
/* 221:299 */         for (int var5 = 0; var5 < var8.length; var5++) {
/* 222:301 */           if (func_145892_a(p_145891_0_, var1, var8[var5], var2)) {
/* 223:303 */             return true;
/* 224:    */           }
/* 225:    */         }
/* 226:    */       }
/* 227:    */       else
/* 228:    */       {
/* 229:309 */         int var3 = var1.getSizeInventory();
/* 230:311 */         for (int var4 = 0; var4 < var3; var4++) {
/* 231:313 */           if (func_145892_a(p_145891_0_, var1, var4, var2)) {
/* 232:315 */             return true;
/* 233:    */           }
/* 234:    */         }
/* 235:    */       }
/* 236:    */     }
/* 237:    */     else
/* 238:    */     {
/* 239:322 */       EntityItem var6 = func_145897_a(p_145891_0_.getWorldObj(), p_145891_0_.getXPos(), p_145891_0_.getYPos() + 1.0D, p_145891_0_.getZPos());
/* 240:324 */       if (var6 != null) {
/* 241:326 */         return func_145898_a(p_145891_0_, var6);
/* 242:    */       }
/* 243:    */     }
/* 244:330 */     return false;
/* 245:    */   }
/* 246:    */   
/* 247:    */   private static boolean func_145892_a(IHopper p_145892_0_, IInventory p_145892_1_, int p_145892_2_, int p_145892_3_)
/* 248:    */   {
/* 249:335 */     ItemStack var4 = p_145892_1_.getStackInSlot(p_145892_2_);
/* 250:337 */     if ((var4 != null) && (func_145890_b(p_145892_1_, var4, p_145892_2_, p_145892_3_)))
/* 251:    */     {
/* 252:339 */       ItemStack var5 = var4.copy();
/* 253:340 */       ItemStack var6 = func_145889_a(p_145892_0_, p_145892_1_.decrStackSize(p_145892_2_, 1), -1);
/* 254:342 */       if ((var6 == null) || (var6.stackSize == 0))
/* 255:    */       {
/* 256:344 */         p_145892_1_.onInventoryChanged();
/* 257:345 */         return true;
/* 258:    */       }
/* 259:348 */       p_145892_1_.setInventorySlotContents(p_145892_2_, var5);
/* 260:    */     }
/* 261:351 */     return false;
/* 262:    */   }
/* 263:    */   
/* 264:    */   public static boolean func_145898_a(IInventory p_145898_0_, EntityItem p_145898_1_)
/* 265:    */   {
/* 266:356 */     boolean var2 = false;
/* 267:358 */     if (p_145898_1_ == null) {
/* 268:360 */       return false;
/* 269:    */     }
/* 270:364 */     ItemStack var3 = p_145898_1_.getEntityItem().copy();
/* 271:365 */     ItemStack var4 = func_145889_a(p_145898_0_, var3, -1);
/* 272:367 */     if ((var4 != null) && (var4.stackSize != 0))
/* 273:    */     {
/* 274:369 */       p_145898_1_.setEntityItemStack(var4);
/* 275:    */     }
/* 276:    */     else
/* 277:    */     {
/* 278:373 */       var2 = true;
/* 279:374 */       p_145898_1_.setDead();
/* 280:    */     }
/* 281:377 */     return var2;
/* 282:    */   }
/* 283:    */   
/* 284:    */   public static ItemStack func_145889_a(IInventory p_145889_0_, ItemStack p_145889_1_, int p_145889_2_)
/* 285:    */   {
/* 286:383 */     if (((p_145889_0_ instanceof ISidedInventory)) && (p_145889_2_ > -1))
/* 287:    */     {
/* 288:385 */       ISidedInventory var6 = (ISidedInventory)p_145889_0_;
/* 289:386 */       int[] var7 = var6.getAccessibleSlotsFromSide(p_145889_2_);
/* 290:    */       
/* 291:388 */       int var5 = 0;
/* 292:    */       do
/* 293:    */       {
/* 294:390 */         p_145889_1_ = func_145899_c(p_145889_0_, p_145889_1_, var7[var5], p_145889_2_);var5++;
/* 295:388 */         if ((var5 >= var7.length) || (p_145889_1_ == null)) {
/* 296:    */           break;
/* 297:    */         }
/* 298:388 */       } while (p_145889_1_.stackSize > 0);
/* 299:    */     }
/* 300:    */     else
/* 301:    */     {
/* 302:395 */       int var3 = p_145889_0_.getSizeInventory();
/* 303:397 */       for (int var4 = 0; (var4 < var3) && (p_145889_1_ != null) && (p_145889_1_.stackSize > 0); var4++) {
/* 304:399 */         p_145889_1_ = func_145899_c(p_145889_0_, p_145889_1_, var4, p_145889_2_);
/* 305:    */       }
/* 306:    */     }
/* 307:403 */     if ((p_145889_1_ != null) && (p_145889_1_.stackSize == 0)) {
/* 308:405 */       p_145889_1_ = null;
/* 309:    */     }
/* 310:408 */     return p_145889_1_;
/* 311:    */   }
/* 312:    */   
/* 313:    */   private static boolean func_145885_a(IInventory p_145885_0_, ItemStack p_145885_1_, int p_145885_2_, int p_145885_3_)
/* 314:    */   {
/* 315:413 */     return p_145885_0_.isItemValidForSlot(p_145885_2_, p_145885_1_);
/* 316:    */   }
/* 317:    */   
/* 318:    */   private static boolean func_145890_b(IInventory p_145890_0_, ItemStack p_145890_1_, int p_145890_2_, int p_145890_3_)
/* 319:    */   {
/* 320:418 */     return (!(p_145890_0_ instanceof ISidedInventory)) || (((ISidedInventory)p_145890_0_).canExtractItem(p_145890_2_, p_145890_1_, p_145890_3_));
/* 321:    */   }
/* 322:    */   
/* 323:    */   private static ItemStack func_145899_c(IInventory p_145899_0_, ItemStack p_145899_1_, int p_145899_2_, int p_145899_3_)
/* 324:    */   {
/* 325:423 */     ItemStack var4 = p_145899_0_.getStackInSlot(p_145899_2_);
/* 326:425 */     if (func_145885_a(p_145899_0_, p_145899_1_, p_145899_2_, p_145899_3_))
/* 327:    */     {
/* 328:427 */       boolean var5 = false;
/* 329:429 */       if (var4 == null)
/* 330:    */       {
/* 331:431 */         p_145899_0_.setInventorySlotContents(p_145899_2_, p_145899_1_);
/* 332:432 */         p_145899_1_ = null;
/* 333:433 */         var5 = true;
/* 334:    */       }
/* 335:435 */       else if (func_145894_a(var4, p_145899_1_))
/* 336:    */       {
/* 337:437 */         int var6 = p_145899_1_.getMaxStackSize() - var4.stackSize;
/* 338:438 */         int var7 = Math.min(p_145899_1_.stackSize, var6);
/* 339:439 */         p_145899_1_.stackSize -= var7;
/* 340:440 */         var4.stackSize += var7;
/* 341:441 */         var5 = var7 > 0;
/* 342:    */       }
/* 343:444 */       if (var5)
/* 344:    */       {
/* 345:446 */         if ((p_145899_0_ instanceof TileEntityHopper))
/* 346:    */         {
/* 347:448 */           ((TileEntityHopper)p_145899_0_).func_145896_c(8);
/* 348:449 */           p_145899_0_.onInventoryChanged();
/* 349:    */         }
/* 350:452 */         p_145899_0_.onInventoryChanged();
/* 351:    */       }
/* 352:    */     }
/* 353:456 */     return p_145899_1_;
/* 354:    */   }
/* 355:    */   
/* 356:    */   private IInventory func_145895_l()
/* 357:    */   {
/* 358:461 */     int var1 = BlockHopper.func_149918_b(getBlockMetadata());
/* 359:462 */     return func_145893_b(getWorldObj(), this.field_145851_c + net.minecraft.util.Facing.offsetsXForSide[var1], this.field_145848_d + net.minecraft.util.Facing.offsetsYForSide[var1], this.field_145849_e + net.minecraft.util.Facing.offsetsZForSide[var1]);
/* 360:    */   }
/* 361:    */   
/* 362:    */   public static IInventory func_145884_b(IHopper p_145884_0_)
/* 363:    */   {
/* 364:467 */     return func_145893_b(p_145884_0_.getWorldObj(), p_145884_0_.getXPos(), p_145884_0_.getYPos() + 1.0D, p_145884_0_.getZPos());
/* 365:    */   }
/* 366:    */   
/* 367:    */   public static EntityItem func_145897_a(World p_145897_0_, double p_145897_1_, double p_145897_3_, double p_145897_5_)
/* 368:    */   {
/* 369:472 */     List var7 = p_145897_0_.selectEntitiesWithinAABB(EntityItem.class, AxisAlignedBB.getAABBPool().getAABB(p_145897_1_, p_145897_3_, p_145897_5_, p_145897_1_ + 1.0D, p_145897_3_ + 1.0D, p_145897_5_ + 1.0D), IEntitySelector.selectAnything);
/* 370:473 */     return var7.size() > 0 ? (EntityItem)var7.get(0) : null;
/* 371:    */   }
/* 372:    */   
/* 373:    */   public static IInventory func_145893_b(World p_145893_0_, double p_145893_1_, double p_145893_3_, double p_145893_5_)
/* 374:    */   {
/* 375:478 */     IInventory var7 = null;
/* 376:479 */     int var8 = MathHelper.floor_double(p_145893_1_);
/* 377:480 */     int var9 = MathHelper.floor_double(p_145893_3_);
/* 378:481 */     int var10 = MathHelper.floor_double(p_145893_5_);
/* 379:482 */     TileEntity var11 = p_145893_0_.getTileEntity(var8, var9, var10);
/* 380:484 */     if ((var11 != null) && ((var11 instanceof IInventory)))
/* 381:    */     {
/* 382:486 */       var7 = (IInventory)var11;
/* 383:488 */       if ((var7 instanceof TileEntityChest))
/* 384:    */       {
/* 385:490 */         Block var12 = p_145893_0_.getBlock(var8, var9, var10);
/* 386:492 */         if ((var12 instanceof BlockChest)) {
/* 387:494 */           var7 = ((BlockChest)var12).func_149951_m(p_145893_0_, var8, var9, var10);
/* 388:    */         }
/* 389:    */       }
/* 390:    */     }
/* 391:499 */     if (var7 == null)
/* 392:    */     {
/* 393:501 */       List var13 = p_145893_0_.getEntitiesWithinAABBExcludingEntity(null, AxisAlignedBB.getAABBPool().getAABB(p_145893_1_, p_145893_3_, p_145893_5_, p_145893_1_ + 1.0D, p_145893_3_ + 1.0D, p_145893_5_ + 1.0D), IEntitySelector.selectInventories);
/* 394:503 */       if ((var13 != null) && (var13.size() > 0)) {
/* 395:505 */         var7 = (IInventory)var13.get(p_145893_0_.rand.nextInt(var13.size()));
/* 396:    */       }
/* 397:    */     }
/* 398:509 */     return var7;
/* 399:    */   }
/* 400:    */   
/* 401:    */   private static boolean func_145894_a(ItemStack p_145894_0_, ItemStack p_145894_1_)
/* 402:    */   {
/* 403:514 */     return p_145894_0_.stackSize > p_145894_0_.getMaxStackSize() ? false : p_145894_0_.getItemDamage() != p_145894_1_.getItemDamage() ? false : p_145894_0_.getItem() != p_145894_1_.getItem() ? false : ItemStack.areItemStackTagsEqual(p_145894_0_, p_145894_1_);
/* 404:    */   }
/* 405:    */   
/* 406:    */   public double getXPos()
/* 407:    */   {
/* 408:522 */     return this.field_145851_c;
/* 409:    */   }
/* 410:    */   
/* 411:    */   public double getYPos()
/* 412:    */   {
/* 413:530 */     return this.field_145848_d;
/* 414:    */   }
/* 415:    */   
/* 416:    */   public double getZPos()
/* 417:    */   {
/* 418:538 */     return this.field_145849_e;
/* 419:    */   }
/* 420:    */   
/* 421:    */   public void func_145896_c(int p_145896_1_)
/* 422:    */   {
/* 423:543 */     this.field_145901_j = p_145896_1_;
/* 424:    */   }
/* 425:    */   
/* 426:    */   public boolean func_145888_j()
/* 427:    */   {
/* 428:548 */     return this.field_145901_j > 0;
/* 429:    */   }
/* 430:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.tileentity.TileEntityHopper
 * JD-Core Version:    0.7.0.1
 */