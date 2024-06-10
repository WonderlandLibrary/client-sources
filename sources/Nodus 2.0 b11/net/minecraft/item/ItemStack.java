/*   1:    */ package net.minecraft.item;
/*   2:    */ 
/*   3:    */ import com.google.common.collect.HashMultimap;
/*   4:    */ import com.google.common.collect.Multimap;
/*   5:    */ import java.text.DecimalFormat;
/*   6:    */ import java.util.ArrayList;
/*   7:    */ import java.util.Collection;
/*   8:    */ import java.util.Iterator;
/*   9:    */ import java.util.List;
/*  10:    */ import java.util.Map.Entry;
/*  11:    */ import java.util.Random;
/*  12:    */ import java.util.UUID;
/*  13:    */ import net.minecraft.block.Block;
/*  14:    */ import net.minecraft.enchantment.Enchantment;
/*  15:    */ import net.minecraft.enchantment.EnchantmentDurability;
/*  16:    */ import net.minecraft.enchantment.EnchantmentHelper;
/*  17:    */ import net.minecraft.entity.Entity;
/*  18:    */ import net.minecraft.entity.EntityLivingBase;
/*  19:    */ import net.minecraft.entity.SharedMonsterAttributes;
/*  20:    */ import net.minecraft.entity.ai.attributes.AttributeModifier;
/*  21:    */ import net.minecraft.entity.item.EntityItemFrame;
/*  22:    */ import net.minecraft.entity.player.EntityPlayer;
/*  23:    */ import net.minecraft.entity.player.PlayerCapabilities;
/*  24:    */ import net.minecraft.event.HoverEvent;
/*  25:    */ import net.minecraft.event.HoverEvent.Action;
/*  26:    */ import net.minecraft.init.Items;
/*  27:    */ import net.minecraft.nbt.NBTBase;
/*  28:    */ import net.minecraft.nbt.NBTTagCompound;
/*  29:    */ import net.minecraft.nbt.NBTTagList;
/*  30:    */ import net.minecraft.util.ChatComponentText;
/*  31:    */ import net.minecraft.util.ChatStyle;
/*  32:    */ import net.minecraft.util.EnumChatFormatting;
/*  33:    */ import net.minecraft.util.IChatComponent;
/*  34:    */ import net.minecraft.util.IIcon;
/*  35:    */ import net.minecraft.util.StatCollector;
/*  36:    */ import net.minecraft.world.World;
/*  37:    */ 
/*  38:    */ public final class ItemStack
/*  39:    */ {
/*  40: 36 */   public static final DecimalFormat field_111284_a = new DecimalFormat("#.###");
/*  41:    */   public int stackSize;
/*  42:    */   public int animationsToGo;
/*  43:    */   private Item field_151002_e;
/*  44:    */   public NBTTagCompound stackTagCompound;
/*  45:    */   private int itemDamage;
/*  46:    */   private EntityItemFrame itemFrame;
/*  47:    */   private static final String __OBFID = "CL_00000043";
/*  48:    */   
/*  49:    */   public ItemStack(Block par1Block)
/*  50:    */   {
/*  51: 61 */     this(par1Block, 1);
/*  52:    */   }
/*  53:    */   
/*  54:    */   public ItemStack(Block par1Block, int par2)
/*  55:    */   {
/*  56: 66 */     this(par1Block, par2, 0);
/*  57:    */   }
/*  58:    */   
/*  59:    */   public ItemStack(Block par1Block, int par2, int par3)
/*  60:    */   {
/*  61: 71 */     this(Item.getItemFromBlock(par1Block), par2, par3);
/*  62:    */   }
/*  63:    */   
/*  64:    */   public ItemStack(Item par1Item)
/*  65:    */   {
/*  66: 76 */     this(par1Item, 1);
/*  67:    */   }
/*  68:    */   
/*  69:    */   public ItemStack(Item par1Item, int par2)
/*  70:    */   {
/*  71: 81 */     this(par1Item, par2, 0);
/*  72:    */   }
/*  73:    */   
/*  74:    */   public ItemStack(Item par1Item, int par2, int par3)
/*  75:    */   {
/*  76: 86 */     this.field_151002_e = par1Item;
/*  77: 87 */     this.stackSize = par2;
/*  78: 88 */     this.itemDamage = par3;
/*  79: 90 */     if (this.itemDamage < 0) {
/*  80: 92 */       this.itemDamage = 0;
/*  81:    */     }
/*  82:    */   }
/*  83:    */   
/*  84:    */   public static ItemStack loadItemStackFromNBT(NBTTagCompound par0NBTTagCompound)
/*  85:    */   {
/*  86: 98 */     ItemStack var1 = new ItemStack();
/*  87: 99 */     var1.readFromNBT(par0NBTTagCompound);
/*  88:100 */     return var1.getItem() != null ? var1 : null;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public ItemStack() {}
/*  92:    */   
/*  93:    */   public ItemStack splitStack(int par1)
/*  94:    */   {
/*  95:110 */     ItemStack var2 = new ItemStack(this.field_151002_e, par1, this.itemDamage);
/*  96:112 */     if (this.stackTagCompound != null) {
/*  97:114 */       var2.stackTagCompound = ((NBTTagCompound)this.stackTagCompound.copy());
/*  98:    */     }
/*  99:117 */     this.stackSize -= par1;
/* 100:118 */     return var2;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public Item getItem()
/* 104:    */   {
/* 105:126 */     return this.field_151002_e;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public IIcon getIconIndex()
/* 109:    */   {
/* 110:134 */     return getItem().getIconIndex(this);
/* 111:    */   }
/* 112:    */   
/* 113:    */   public int getItemSpriteNumber()
/* 114:    */   {
/* 115:139 */     return getItem().getSpriteNumber();
/* 116:    */   }
/* 117:    */   
/* 118:    */   public boolean tryPlaceItemIntoWorld(EntityPlayer par1EntityPlayer, World par2World, int par3, int par4, int par5, int par6, float par7, float par8, float par9)
/* 119:    */   {
/* 120:144 */     boolean var10 = getItem().onItemUse(this, par1EntityPlayer, par2World, par3, par4, par5, par6, par7, par8, par9);
/* 121:146 */     if (var10) {
/* 122:148 */       par1EntityPlayer.addStat(net.minecraft.stats.StatList.objectUseStats[Item.getIdFromItem(this.field_151002_e)], 1);
/* 123:    */     }
/* 124:151 */     return var10;
/* 125:    */   }
/* 126:    */   
/* 127:    */   public float func_150997_a(Block p_150997_1_)
/* 128:    */   {
/* 129:156 */     return getItem().func_150893_a(this, p_150997_1_);
/* 130:    */   }
/* 131:    */   
/* 132:    */   public ItemStack useItemRightClick(World par1World, EntityPlayer par2EntityPlayer)
/* 133:    */   {
/* 134:165 */     return getItem().onItemRightClick(this, par1World, par2EntityPlayer);
/* 135:    */   }
/* 136:    */   
/* 137:    */   public ItemStack onFoodEaten(World par1World, EntityPlayer par2EntityPlayer)
/* 138:    */   {
/* 139:170 */     return getItem().onEaten(this, par1World, par2EntityPlayer);
/* 140:    */   }
/* 141:    */   
/* 142:    */   public NBTTagCompound writeToNBT(NBTTagCompound par1NBTTagCompound)
/* 143:    */   {
/* 144:178 */     par1NBTTagCompound.setShort("id", (short)Item.getIdFromItem(this.field_151002_e));
/* 145:179 */     par1NBTTagCompound.setByte("Count", (byte)this.stackSize);
/* 146:180 */     par1NBTTagCompound.setShort("Damage", (short)this.itemDamage);
/* 147:182 */     if (this.stackTagCompound != null) {
/* 148:184 */       par1NBTTagCompound.setTag("tag", this.stackTagCompound);
/* 149:    */     }
/* 150:187 */     return par1NBTTagCompound;
/* 151:    */   }
/* 152:    */   
/* 153:    */   public void readFromNBT(NBTTagCompound par1NBTTagCompound)
/* 154:    */   {
/* 155:195 */     this.field_151002_e = Item.getItemById(par1NBTTagCompound.getShort("id"));
/* 156:196 */     this.stackSize = par1NBTTagCompound.getByte("Count");
/* 157:197 */     this.itemDamage = par1NBTTagCompound.getShort("Damage");
/* 158:199 */     if (this.itemDamage < 0) {
/* 159:201 */       this.itemDamage = 0;
/* 160:    */     }
/* 161:204 */     if (par1NBTTagCompound.func_150297_b("tag", 10)) {
/* 162:206 */       this.stackTagCompound = par1NBTTagCompound.getCompoundTag("tag");
/* 163:    */     }
/* 164:    */   }
/* 165:    */   
/* 166:    */   public int getMaxStackSize()
/* 167:    */   {
/* 168:215 */     return getItem().getItemStackLimit();
/* 169:    */   }
/* 170:    */   
/* 171:    */   public boolean isStackable()
/* 172:    */   {
/* 173:223 */     return (getMaxStackSize() > 1) && ((!isItemStackDamageable()) || (!isItemDamaged()));
/* 174:    */   }
/* 175:    */   
/* 176:    */   public boolean isItemStackDamageable()
/* 177:    */   {
/* 178:231 */     return this.field_151002_e.getMaxDamage() > 0;
/* 179:    */   }
/* 180:    */   
/* 181:    */   public boolean getHasSubtypes()
/* 182:    */   {
/* 183:236 */     return this.field_151002_e.getHasSubtypes();
/* 184:    */   }
/* 185:    */   
/* 186:    */   public boolean isItemDamaged()
/* 187:    */   {
/* 188:244 */     return (isItemStackDamageable()) && (this.itemDamage > 0);
/* 189:    */   }
/* 190:    */   
/* 191:    */   public int getItemDamageForDisplay()
/* 192:    */   {
/* 193:252 */     return this.itemDamage;
/* 194:    */   }
/* 195:    */   
/* 196:    */   public int getItemDamage()
/* 197:    */   {
/* 198:260 */     return this.itemDamage;
/* 199:    */   }
/* 200:    */   
/* 201:    */   public void setItemDamage(int par1)
/* 202:    */   {
/* 203:268 */     this.itemDamage = par1;
/* 204:270 */     if (this.itemDamage < 0) {
/* 205:272 */       this.itemDamage = 0;
/* 206:    */     }
/* 207:    */   }
/* 208:    */   
/* 209:    */   public int getMaxDamage()
/* 210:    */   {
/* 211:281 */     return this.field_151002_e.getMaxDamage();
/* 212:    */   }
/* 213:    */   
/* 214:    */   public boolean attemptDamageItem(int par1, Random par2Random)
/* 215:    */   {
/* 216:292 */     if (!isItemStackDamageable()) {
/* 217:294 */       return false;
/* 218:    */     }
/* 219:298 */     if (par1 > 0)
/* 220:    */     {
/* 221:300 */       int var3 = EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, this);
/* 222:301 */       int var4 = 0;
/* 223:303 */       for (int var5 = 0; (var3 > 0) && (var5 < par1); var5++) {
/* 224:305 */         if (EnchantmentDurability.negateDamage(this, var3, par2Random)) {
/* 225:307 */           var4++;
/* 226:    */         }
/* 227:    */       }
/* 228:311 */       par1 -= var4;
/* 229:313 */       if (par1 <= 0) {
/* 230:315 */         return false;
/* 231:    */       }
/* 232:    */     }
/* 233:319 */     this.itemDamage += par1;
/* 234:320 */     return this.itemDamage > getMaxDamage();
/* 235:    */   }
/* 236:    */   
/* 237:    */   public void damageItem(int par1, EntityLivingBase par2EntityLivingBase)
/* 238:    */   {
/* 239:329 */     if ((!(par2EntityLivingBase instanceof EntityPlayer)) || (!((EntityPlayer)par2EntityLivingBase).capabilities.isCreativeMode)) {
/* 240:331 */       if (isItemStackDamageable()) {
/* 241:333 */         if (attemptDamageItem(par1, par2EntityLivingBase.getRNG()))
/* 242:    */         {
/* 243:335 */           par2EntityLivingBase.renderBrokenItemStack(this);
/* 244:336 */           this.stackSize -= 1;
/* 245:338 */           if ((par2EntityLivingBase instanceof EntityPlayer))
/* 246:    */           {
/* 247:340 */             EntityPlayer var3 = (EntityPlayer)par2EntityLivingBase;
/* 248:341 */             var3.addStat(net.minecraft.stats.StatList.objectBreakStats[Item.getIdFromItem(this.field_151002_e)], 1);
/* 249:343 */             if ((this.stackSize == 0) && ((getItem() instanceof ItemBow))) {
/* 250:345 */               var3.destroyCurrentEquippedItem();
/* 251:    */             }
/* 252:    */           }
/* 253:349 */           if (this.stackSize < 0) {
/* 254:351 */             this.stackSize = 0;
/* 255:    */           }
/* 256:354 */           this.itemDamage = 0;
/* 257:    */         }
/* 258:    */       }
/* 259:    */     }
/* 260:    */   }
/* 261:    */   
/* 262:    */   public void hitEntity(EntityLivingBase par1EntityLivingBase, EntityPlayer par2EntityPlayer)
/* 263:    */   {
/* 264:365 */     boolean var3 = this.field_151002_e.hitEntity(this, par1EntityLivingBase, par2EntityPlayer);
/* 265:367 */     if (var3) {
/* 266:369 */       par2EntityPlayer.addStat(net.minecraft.stats.StatList.objectUseStats[Item.getIdFromItem(this.field_151002_e)], 1);
/* 267:    */     }
/* 268:    */   }
/* 269:    */   
/* 270:    */   public void func_150999_a(World p_150999_1_, Block p_150999_2_, int p_150999_3_, int p_150999_4_, int p_150999_5_, EntityPlayer p_150999_6_)
/* 271:    */   {
/* 272:375 */     boolean var7 = this.field_151002_e.onBlockDestroyed(this, p_150999_1_, p_150999_2_, p_150999_3_, p_150999_4_, p_150999_5_, p_150999_6_);
/* 273:377 */     if (var7) {
/* 274:379 */       p_150999_6_.addStat(net.minecraft.stats.StatList.objectUseStats[Item.getIdFromItem(this.field_151002_e)], 1);
/* 275:    */     }
/* 276:    */   }
/* 277:    */   
/* 278:    */   public boolean func_150998_b(Block p_150998_1_)
/* 279:    */   {
/* 280:385 */     return this.field_151002_e.func_150897_b(p_150998_1_);
/* 281:    */   }
/* 282:    */   
/* 283:    */   public boolean interactWithEntity(EntityPlayer par1EntityPlayer, EntityLivingBase par2EntityLivingBase)
/* 284:    */   {
/* 285:390 */     return this.field_151002_e.itemInteractionForEntity(this, par1EntityPlayer, par2EntityLivingBase);
/* 286:    */   }
/* 287:    */   
/* 288:    */   public ItemStack copy()
/* 289:    */   {
/* 290:398 */     ItemStack var1 = new ItemStack(this.field_151002_e, this.stackSize, this.itemDamage);
/* 291:400 */     if (this.stackTagCompound != null) {
/* 292:402 */       var1.stackTagCompound = ((NBTTagCompound)this.stackTagCompound.copy());
/* 293:    */     }
/* 294:405 */     return var1;
/* 295:    */   }
/* 296:    */   
/* 297:    */   public static boolean areItemStackTagsEqual(ItemStack par0ItemStack, ItemStack par1ItemStack)
/* 298:    */   {
/* 299:410 */     return (par0ItemStack == null) && (par1ItemStack == null);
/* 300:    */   }
/* 301:    */   
/* 302:    */   public static boolean areItemStacksEqual(ItemStack par0ItemStack, ItemStack par1ItemStack)
/* 303:    */   {
/* 304:418 */     return (par0ItemStack == null) && (par1ItemStack == null);
/* 305:    */   }
/* 306:    */   
/* 307:    */   private boolean isItemStackEqual(ItemStack par1ItemStack)
/* 308:    */   {
/* 309:426 */     return this.stackSize == par1ItemStack.stackSize;
/* 310:    */   }
/* 311:    */   
/* 312:    */   public boolean isItemEqual(ItemStack par1ItemStack)
/* 313:    */   {
/* 314:435 */     return (this.field_151002_e == par1ItemStack.field_151002_e) && (this.itemDamage == par1ItemStack.itemDamage);
/* 315:    */   }
/* 316:    */   
/* 317:    */   public String getUnlocalizedName()
/* 318:    */   {
/* 319:440 */     return this.field_151002_e.getUnlocalizedName(this);
/* 320:    */   }
/* 321:    */   
/* 322:    */   public static ItemStack copyItemStack(ItemStack par0ItemStack)
/* 323:    */   {
/* 324:448 */     return par0ItemStack == null ? null : par0ItemStack.copy();
/* 325:    */   }
/* 326:    */   
/* 327:    */   public String toString()
/* 328:    */   {
/* 329:453 */     return this.stackSize + "x" + this.field_151002_e.getUnlocalizedName() + "@" + this.itemDamage;
/* 330:    */   }
/* 331:    */   
/* 332:    */   public void updateAnimation(World par1World, Entity par2Entity, int par3, boolean par4)
/* 333:    */   {
/* 334:462 */     if (this.animationsToGo > 0) {
/* 335:464 */       this.animationsToGo -= 1;
/* 336:    */     }
/* 337:467 */     this.field_151002_e.onUpdate(this, par1World, par2Entity, par3, par4);
/* 338:    */   }
/* 339:    */   
/* 340:    */   public void onCrafting(World par1World, EntityPlayer par2EntityPlayer, int par3)
/* 341:    */   {
/* 342:472 */     par2EntityPlayer.addStat(net.minecraft.stats.StatList.objectCraftStats[Item.getIdFromItem(this.field_151002_e)], par3);
/* 343:473 */     this.field_151002_e.onCreated(this, par1World, par2EntityPlayer);
/* 344:    */   }
/* 345:    */   
/* 346:    */   public int getMaxItemUseDuration()
/* 347:    */   {
/* 348:478 */     return getItem().getMaxItemUseDuration(this);
/* 349:    */   }
/* 350:    */   
/* 351:    */   public EnumAction getItemUseAction()
/* 352:    */   {
/* 353:483 */     return getItem().getItemUseAction(this);
/* 354:    */   }
/* 355:    */   
/* 356:    */   public void onPlayerStoppedUsing(World par1World, EntityPlayer par2EntityPlayer, int par3)
/* 357:    */   {
/* 358:491 */     getItem().onPlayerStoppedUsing(this, par1World, par2EntityPlayer, par3);
/* 359:    */   }
/* 360:    */   
/* 361:    */   public boolean hasTagCompound()
/* 362:    */   {
/* 363:499 */     return this.stackTagCompound != null;
/* 364:    */   }
/* 365:    */   
/* 366:    */   public NBTTagCompound getTagCompound()
/* 367:    */   {
/* 368:507 */     return this.stackTagCompound;
/* 369:    */   }
/* 370:    */   
/* 371:    */   public NBTTagList getEnchantmentTagList()
/* 372:    */   {
/* 373:512 */     return this.stackTagCompound == null ? null : this.stackTagCompound.getTagList("ench", 10);
/* 374:    */   }
/* 375:    */   
/* 376:    */   public void setTagCompound(NBTTagCompound par1NBTTagCompound)
/* 377:    */   {
/* 378:520 */     this.stackTagCompound = par1NBTTagCompound;
/* 379:    */   }
/* 380:    */   
/* 381:    */   public String getDisplayName()
/* 382:    */   {
/* 383:528 */     String var1 = getItem().getItemStackDisplayName(this);
/* 384:530 */     if ((this.stackTagCompound != null) && (this.stackTagCompound.func_150297_b("display", 10)))
/* 385:    */     {
/* 386:532 */       NBTTagCompound var2 = this.stackTagCompound.getCompoundTag("display");
/* 387:534 */       if (var2.func_150297_b("Name", 8)) {
/* 388:536 */         var1 = var2.getString("Name");
/* 389:    */       }
/* 390:    */     }
/* 391:540 */     return var1;
/* 392:    */   }
/* 393:    */   
/* 394:    */   public ItemStack setStackDisplayName(String p_151001_1_)
/* 395:    */   {
/* 396:545 */     if (this.stackTagCompound == null) {
/* 397:547 */       this.stackTagCompound = new NBTTagCompound();
/* 398:    */     }
/* 399:550 */     if (!this.stackTagCompound.func_150297_b("display", 10)) {
/* 400:552 */       this.stackTagCompound.setTag("display", new NBTTagCompound());
/* 401:    */     }
/* 402:555 */     this.stackTagCompound.getCompoundTag("display").setString("Name", p_151001_1_);
/* 403:556 */     return this;
/* 404:    */   }
/* 405:    */   
/* 406:    */   public void func_135074_t()
/* 407:    */   {
/* 408:561 */     if (this.stackTagCompound != null) {
/* 409:563 */       if (this.stackTagCompound.func_150297_b("display", 10))
/* 410:    */       {
/* 411:565 */         NBTTagCompound var1 = this.stackTagCompound.getCompoundTag("display");
/* 412:566 */         var1.removeTag("Name");
/* 413:568 */         if (var1.hasNoTags())
/* 414:    */         {
/* 415:570 */           this.stackTagCompound.removeTag("display");
/* 416:572 */           if (this.stackTagCompound.hasNoTags()) {
/* 417:574 */             setTagCompound(null);
/* 418:    */           }
/* 419:    */         }
/* 420:    */       }
/* 421:    */     }
/* 422:    */   }
/* 423:    */   
/* 424:    */   public boolean hasDisplayName()
/* 425:    */   {
/* 426:586 */     return !this.stackTagCompound.func_150297_b("display", 10) ? false : this.stackTagCompound == null ? false : this.stackTagCompound.getCompoundTag("display").func_150297_b("Name", 8);
/* 427:    */   }
/* 428:    */   
/* 429:    */   public List getTooltip(EntityPlayer par1EntityPlayer, boolean par2)
/* 430:    */   {
/* 431:594 */     ArrayList var3 = new ArrayList();
/* 432:595 */     String var4 = getDisplayName();
/* 433:597 */     if (hasDisplayName()) {
/* 434:599 */       var4 = EnumChatFormatting.ITALIC + var4 + EnumChatFormatting.RESET;
/* 435:    */     }
/* 436:604 */     if (par2)
/* 437:    */     {
/* 438:606 */       String var5 = "";
/* 439:608 */       if (var4.length() > 0)
/* 440:    */       {
/* 441:610 */         var4 = var4 + " (";
/* 442:611 */         var5 = ")";
/* 443:    */       }
/* 444:614 */       int var6 = Item.getIdFromItem(this.field_151002_e);
/* 445:616 */       if (getHasSubtypes()) {
/* 446:618 */         var4 = var4 + String.format("#%04d/%d%s", new Object[] { Integer.valueOf(var6), Integer.valueOf(this.itemDamage), var5 });
/* 447:    */       } else {
/* 448:622 */         var4 = var4 + String.format("#%04d%s", new Object[] { Integer.valueOf(var6), var5 });
/* 449:    */       }
/* 450:    */     }
/* 451:625 */     else if ((!hasDisplayName()) && (this.field_151002_e == Items.filled_map))
/* 452:    */     {
/* 453:627 */       var4 = var4 + " #" + this.itemDamage;
/* 454:    */     }
/* 455:630 */     var3.add(var4);
/* 456:631 */     this.field_151002_e.addInformation(this, par1EntityPlayer, var3, par2);
/* 457:633 */     if (hasTagCompound())
/* 458:    */     {
/* 459:635 */       NBTTagList var14 = getEnchantmentTagList();
/* 460:637 */       if (var14 != null) {
/* 461:639 */         for (int var6 = 0; var6 < var14.tagCount(); var6++)
/* 462:    */         {
/* 463:641 */           short var7 = var14.getCompoundTagAt(var6).getShort("id");
/* 464:642 */           short var8 = var14.getCompoundTagAt(var6).getShort("lvl");
/* 465:644 */           if (Enchantment.enchantmentsList[var7] != null) {
/* 466:646 */             var3.add(Enchantment.enchantmentsList[var7].getTranslatedName(var8));
/* 467:    */           }
/* 468:    */         }
/* 469:    */       }
/* 470:651 */       if (this.stackTagCompound.func_150297_b("display", 10))
/* 471:    */       {
/* 472:653 */         NBTTagCompound var17 = this.stackTagCompound.getCompoundTag("display");
/* 473:655 */         if (var17.func_150297_b("color", 3)) {
/* 474:657 */           if (par2) {
/* 475:659 */             var3.add("Color: #" + Integer.toHexString(var17.getInteger("color")).toUpperCase());
/* 476:    */           } else {
/* 477:663 */             var3.add(EnumChatFormatting.ITALIC + StatCollector.translateToLocal("item.dyed"));
/* 478:    */           }
/* 479:    */         }
/* 480:667 */         if (var17.func_150299_b("Lore") == 9)
/* 481:    */         {
/* 482:669 */           NBTTagList var15 = var17.getTagList("Lore", 8);
/* 483:671 */           if (var15.tagCount() > 0) {
/* 484:673 */             for (int var19 = 0; var19 < var15.tagCount(); var19++) {
/* 485:675 */               var3.add(EnumChatFormatting.DARK_PURPLE + EnumChatFormatting.ITALIC + var15.getStringTagAt(var19));
/* 486:    */             }
/* 487:    */           }
/* 488:    */         }
/* 489:    */       }
/* 490:    */     }
/* 491:682 */     Multimap var13 = getAttributeModifiers();
/* 492:684 */     if (!var13.isEmpty())
/* 493:    */     {
/* 494:686 */       var3.add("");
/* 495:687 */       Iterator var16 = var13.entries().iterator();
/* 496:689 */       while (var16.hasNext())
/* 497:    */       {
/* 498:691 */         Map.Entry var18 = (Map.Entry)var16.next();
/* 499:692 */         AttributeModifier var20 = (AttributeModifier)var18.getValue();
/* 500:693 */         double var9 = var20.getAmount();
/* 501:    */         double var11;
/* 502:    */         double var11;
/* 503:696 */         if ((var20.getOperation() != 1) && (var20.getOperation() != 2)) {
/* 504:698 */           var11 = var20.getAmount();
/* 505:    */         } else {
/* 506:702 */           var11 = var20.getAmount() * 100.0D;
/* 507:    */         }
/* 508:705 */         if (var9 > 0.0D)
/* 509:    */         {
/* 510:707 */           var3.add(EnumChatFormatting.BLUE + StatCollector.translateToLocalFormatted(new StringBuilder("attribute.modifier.plus.").append(var20.getOperation()).toString(), new Object[] { field_111284_a.format(var11), StatCollector.translateToLocal("attribute.name." + (String)var18.getKey()) }));
/* 511:    */         }
/* 512:709 */         else if (var9 < 0.0D)
/* 513:    */         {
/* 514:711 */           var11 *= -1.0D;
/* 515:712 */           var3.add(EnumChatFormatting.RED + StatCollector.translateToLocalFormatted(new StringBuilder("attribute.modifier.take.").append(var20.getOperation()).toString(), new Object[] { field_111284_a.format(var11), StatCollector.translateToLocal("attribute.name." + (String)var18.getKey()) }));
/* 516:    */         }
/* 517:    */       }
/* 518:    */     }
/* 519:717 */     if ((hasTagCompound()) && (getTagCompound().getBoolean("Unbreakable"))) {
/* 520:719 */       var3.add(EnumChatFormatting.BLUE + StatCollector.translateToLocal("item.unbreakable"));
/* 521:    */     }
/* 522:722 */     if ((par2) && (isItemDamaged())) {
/* 523:724 */       var3.add("Durability: " + (getMaxDamage() - getItemDamageForDisplay()) + " / " + getMaxDamage());
/* 524:    */     }
/* 525:727 */     return var3;
/* 526:    */   }
/* 527:    */   
/* 528:    */   public boolean hasEffect()
/* 529:    */   {
/* 530:732 */     return getItem().hasEffect(this);
/* 531:    */   }
/* 532:    */   
/* 533:    */   public EnumRarity getRarity()
/* 534:    */   {
/* 535:737 */     return getItem().getRarity(this);
/* 536:    */   }
/* 537:    */   
/* 538:    */   public boolean isItemEnchantable()
/* 539:    */   {
/* 540:745 */     return getItem().isItemTool(this);
/* 541:    */   }
/* 542:    */   
/* 543:    */   public void addEnchantment(Enchantment par1Enchantment, int par2)
/* 544:    */   {
/* 545:753 */     if (this.stackTagCompound == null) {
/* 546:755 */       setTagCompound(new NBTTagCompound());
/* 547:    */     }
/* 548:758 */     if (!this.stackTagCompound.func_150297_b("ench", 9)) {
/* 549:760 */       this.stackTagCompound.setTag("ench", new NBTTagList());
/* 550:    */     }
/* 551:763 */     NBTTagList var3 = this.stackTagCompound.getTagList("ench", 10);
/* 552:764 */     NBTTagCompound var4 = new NBTTagCompound();
/* 553:765 */     var4.setShort("id", (short)par1Enchantment.effectId);
/* 554:766 */     var4.setShort("lvl", (short)(byte)par2);
/* 555:767 */     var3.appendTag(var4);
/* 556:    */   }
/* 557:    */   
/* 558:    */   public boolean isItemEnchanted()
/* 559:    */   {
/* 560:775 */     return (this.stackTagCompound != null) && (this.stackTagCompound.func_150297_b("ench", 9));
/* 561:    */   }
/* 562:    */   
/* 563:    */   public void setTagInfo(String par1Str, NBTBase par2NBTBase)
/* 564:    */   {
/* 565:780 */     if (this.stackTagCompound == null) {
/* 566:782 */       setTagCompound(new NBTTagCompound());
/* 567:    */     }
/* 568:785 */     this.stackTagCompound.setTag(par1Str, par2NBTBase);
/* 569:    */   }
/* 570:    */   
/* 571:    */   public boolean canEditBlocks()
/* 572:    */   {
/* 573:790 */     return getItem().canItemEditBlocks();
/* 574:    */   }
/* 575:    */   
/* 576:    */   public boolean isOnItemFrame()
/* 577:    */   {
/* 578:798 */     return this.itemFrame != null;
/* 579:    */   }
/* 580:    */   
/* 581:    */   public void setItemFrame(EntityItemFrame par1EntityItemFrame)
/* 582:    */   {
/* 583:806 */     this.itemFrame = par1EntityItemFrame;
/* 584:    */   }
/* 585:    */   
/* 586:    */   public EntityItemFrame getItemFrame()
/* 587:    */   {
/* 588:814 */     return this.itemFrame;
/* 589:    */   }
/* 590:    */   
/* 591:    */   public int getRepairCost()
/* 592:    */   {
/* 593:822 */     return (hasTagCompound()) && (this.stackTagCompound.func_150297_b("RepairCost", 3)) ? this.stackTagCompound.getInteger("RepairCost") : 0;
/* 594:    */   }
/* 595:    */   
/* 596:    */   public void setRepairCost(int par1)
/* 597:    */   {
/* 598:830 */     if (!hasTagCompound()) {
/* 599:832 */       this.stackTagCompound = new NBTTagCompound();
/* 600:    */     }
/* 601:835 */     this.stackTagCompound.setInteger("RepairCost", par1);
/* 602:    */   }
/* 603:    */   
/* 604:    */   public Multimap getAttributeModifiers()
/* 605:    */   {
/* 606:    */     Object var1;
/* 607:846 */     if ((hasTagCompound()) && (this.stackTagCompound.func_150297_b("AttributeModifiers", 9)))
/* 608:    */     {
/* 609:848 */       Object var1 = HashMultimap.create();
/* 610:849 */       NBTTagList var2 = this.stackTagCompound.getTagList("AttributeModifiers", 10);
/* 611:851 */       for (int var3 = 0; var3 < var2.tagCount(); var3++)
/* 612:    */       {
/* 613:853 */         NBTTagCompound var4 = var2.getCompoundTagAt(var3);
/* 614:854 */         AttributeModifier var5 = SharedMonsterAttributes.readAttributeModifierFromNBT(var4);
/* 615:856 */         if ((var5.getID().getLeastSignificantBits() != 0L) && (var5.getID().getMostSignificantBits() != 0L)) {
/* 616:858 */           ((Multimap)var1).put(var4.getString("AttributeName"), var5);
/* 617:    */         }
/* 618:    */       }
/* 619:    */     }
/* 620:    */     else
/* 621:    */     {
/* 622:864 */       var1 = getItem().getItemAttributeModifiers();
/* 623:    */     }
/* 624:867 */     return (Multimap)var1;
/* 625:    */   }
/* 626:    */   
/* 627:    */   public void func_150996_a(Item p_150996_1_)
/* 628:    */   {
/* 629:872 */     this.field_151002_e = p_150996_1_;
/* 630:    */   }
/* 631:    */   
/* 632:    */   public IChatComponent func_151000_E()
/* 633:    */   {
/* 634:877 */     IChatComponent var1 = new ChatComponentText("[").appendText(getDisplayName()).appendText("]");
/* 635:879 */     if (this.field_151002_e != null)
/* 636:    */     {
/* 637:881 */       NBTTagCompound var2 = new NBTTagCompound();
/* 638:882 */       writeToNBT(var2);
/* 639:883 */       var1.getChatStyle().setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_ITEM, new ChatComponentText(var2.toString())));
/* 640:884 */       var1.getChatStyle().setColor(getRarity().rarityColor);
/* 641:    */     }
/* 642:887 */     return var1;
/* 643:    */   }
/* 644:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.item.ItemStack
 * JD-Core Version:    0.7.0.1
 */