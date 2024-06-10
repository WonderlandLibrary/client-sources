/*   1:    */ package net.minecraft.enchantment;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.HashMap;
/*   5:    */ import java.util.Iterator;
/*   6:    */ import java.util.LinkedHashMap;
/*   7:    */ import java.util.List;
/*   8:    */ import java.util.Map;
/*   9:    */ import java.util.Random;
/*  10:    */ import java.util.Set;
/*  11:    */ import net.minecraft.entity.Entity;
/*  12:    */ import net.minecraft.entity.EntityLivingBase;
/*  13:    */ import net.minecraft.entity.player.EntityPlayer;
/*  14:    */ import net.minecraft.init.Items;
/*  15:    */ import net.minecraft.item.Item;
/*  16:    */ import net.minecraft.item.ItemEnchantedBook;
/*  17:    */ import net.minecraft.item.ItemStack;
/*  18:    */ import net.minecraft.nbt.NBTTagCompound;
/*  19:    */ import net.minecraft.nbt.NBTTagList;
/*  20:    */ import net.minecraft.util.DamageSource;
/*  21:    */ import net.minecraft.util.WeightedRandom;
/*  22:    */ 
/*  23:    */ public class EnchantmentHelper
/*  24:    */ {
/*  25: 24 */   private static final Random enchantmentRand = new Random();
/*  26: 29 */   private static final ModifierDamage enchantmentModifierDamage = new ModifierDamage(null);
/*  27: 34 */   private static final ModifierLiving enchantmentModifierLiving = new ModifierLiving(null);
/*  28: 35 */   private static final HurtIterator field_151388_d = new HurtIterator(null);
/*  29: 36 */   private static final DamageIterator field_151389_e = new DamageIterator(null);
/*  30:    */   private static final String __OBFID = "CL_00000107";
/*  31:    */   
/*  32:    */   public static int getEnchantmentLevel(int par0, ItemStack par1ItemStack)
/*  33:    */   {
/*  34: 44 */     if (par1ItemStack == null) {
/*  35: 46 */       return 0;
/*  36:    */     }
/*  37: 50 */     NBTTagList var2 = par1ItemStack.getEnchantmentTagList();
/*  38: 52 */     if (var2 == null) {
/*  39: 54 */       return 0;
/*  40:    */     }
/*  41: 58 */     for (int var3 = 0; var3 < var2.tagCount(); var3++)
/*  42:    */     {
/*  43: 60 */       short var4 = var2.getCompoundTagAt(var3).getShort("id");
/*  44: 61 */       short var5 = var2.getCompoundTagAt(var3).getShort("lvl");
/*  45: 63 */       if (var4 == par0) {
/*  46: 65 */         return var5;
/*  47:    */       }
/*  48:    */     }
/*  49: 69 */     return 0;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public static Map getEnchantments(ItemStack par0ItemStack)
/*  53:    */   {
/*  54: 79 */     LinkedHashMap var1 = new LinkedHashMap();
/*  55: 80 */     NBTTagList var2 = par0ItemStack.getItem() == Items.enchanted_book ? Items.enchanted_book.func_92110_g(par0ItemStack) : par0ItemStack.getEnchantmentTagList();
/*  56: 82 */     if (var2 != null) {
/*  57: 84 */       for (int var3 = 0; var3 < var2.tagCount(); var3++)
/*  58:    */       {
/*  59: 86 */         short var4 = var2.getCompoundTagAt(var3).getShort("id");
/*  60: 87 */         short var5 = var2.getCompoundTagAt(var3).getShort("lvl");
/*  61: 88 */         var1.put(Integer.valueOf(var4), Integer.valueOf(var5));
/*  62:    */       }
/*  63:    */     }
/*  64: 92 */     return var1;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public static void setEnchantments(Map par0Map, ItemStack par1ItemStack)
/*  68:    */   {
/*  69:100 */     NBTTagList var2 = new NBTTagList();
/*  70:101 */     Iterator var3 = par0Map.keySet().iterator();
/*  71:103 */     while (var3.hasNext())
/*  72:    */     {
/*  73:105 */       int var4 = ((Integer)var3.next()).intValue();
/*  74:106 */       NBTTagCompound var5 = new NBTTagCompound();
/*  75:107 */       var5.setShort("id", (short)var4);
/*  76:108 */       var5.setShort("lvl", (short)((Integer)par0Map.get(Integer.valueOf(var4))).intValue());
/*  77:109 */       var2.appendTag(var5);
/*  78:111 */       if (par1ItemStack.getItem() == Items.enchanted_book) {
/*  79:113 */         Items.enchanted_book.addEnchantment(par1ItemStack, new EnchantmentData(var4, ((Integer)par0Map.get(Integer.valueOf(var4))).intValue()));
/*  80:    */       }
/*  81:    */     }
/*  82:117 */     if (var2.tagCount() > 0)
/*  83:    */     {
/*  84:119 */       if (par1ItemStack.getItem() != Items.enchanted_book) {
/*  85:121 */         par1ItemStack.setTagInfo("ench", var2);
/*  86:    */       }
/*  87:    */     }
/*  88:124 */     else if (par1ItemStack.hasTagCompound()) {
/*  89:126 */       par1ItemStack.getTagCompound().removeTag("ench");
/*  90:    */     }
/*  91:    */   }
/*  92:    */   
/*  93:    */   public static int getMaxEnchantmentLevel(int par0, ItemStack[] par1ArrayOfItemStack)
/*  94:    */   {
/*  95:135 */     if (par1ArrayOfItemStack == null) {
/*  96:137 */       return 0;
/*  97:    */     }
/*  98:141 */     int var2 = 0;
/*  99:142 */     ItemStack[] var3 = par1ArrayOfItemStack;
/* 100:143 */     int var4 = par1ArrayOfItemStack.length;
/* 101:145 */     for (int var5 = 0; var5 < var4; var5++)
/* 102:    */     {
/* 103:147 */       ItemStack var6 = var3[var5];
/* 104:148 */       int var7 = getEnchantmentLevel(par0, var6);
/* 105:150 */       if (var7 > var2) {
/* 106:152 */         var2 = var7;
/* 107:    */       }
/* 108:    */     }
/* 109:156 */     return var2;
/* 110:    */   }
/* 111:    */   
/* 112:    */   private static void applyEnchantmentModifier(IModifier par0IEnchantmentModifier, ItemStack par1ItemStack)
/* 113:    */   {
/* 114:165 */     if (par1ItemStack != null)
/* 115:    */     {
/* 116:167 */       NBTTagList var2 = par1ItemStack.getEnchantmentTagList();
/* 117:169 */       if (var2 != null) {
/* 118:171 */         for (int var3 = 0; var3 < var2.tagCount(); var3++)
/* 119:    */         {
/* 120:173 */           short var4 = var2.getCompoundTagAt(var3).getShort("id");
/* 121:174 */           short var5 = var2.getCompoundTagAt(var3).getShort("lvl");
/* 122:176 */           if (Enchantment.enchantmentsList[var4] != null) {
/* 123:178 */             par0IEnchantmentModifier.calculateModifier(Enchantment.enchantmentsList[var4], var5);
/* 124:    */           }
/* 125:    */         }
/* 126:    */       }
/* 127:    */     }
/* 128:    */   }
/* 129:    */   
/* 130:    */   private static void applyEnchantmentModifierArray(IModifier par0IEnchantmentModifier, ItemStack[] par1ArrayOfItemStack)
/* 131:    */   {
/* 132:190 */     ItemStack[] var2 = par1ArrayOfItemStack;
/* 133:191 */     int var3 = par1ArrayOfItemStack.length;
/* 134:193 */     for (int var4 = 0; var4 < var3; var4++)
/* 135:    */     {
/* 136:195 */       ItemStack var5 = var2[var4];
/* 137:196 */       applyEnchantmentModifier(par0IEnchantmentModifier, var5);
/* 138:    */     }
/* 139:    */   }
/* 140:    */   
/* 141:    */   public static int getEnchantmentModifierDamage(ItemStack[] par0ArrayOfItemStack, DamageSource par1DamageSource)
/* 142:    */   {
/* 143:205 */     enchantmentModifierDamage.damageModifier = 0;
/* 144:206 */     enchantmentModifierDamage.source = par1DamageSource;
/* 145:207 */     applyEnchantmentModifierArray(enchantmentModifierDamage, par0ArrayOfItemStack);
/* 146:209 */     if (enchantmentModifierDamage.damageModifier > 25) {
/* 147:211 */       enchantmentModifierDamage.damageModifier = 25;
/* 148:    */     }
/* 149:214 */     return (enchantmentModifierDamage.damageModifier + 1 >> 1) + enchantmentRand.nextInt((enchantmentModifierDamage.damageModifier >> 1) + 1);
/* 150:    */   }
/* 151:    */   
/* 152:    */   public static float getEnchantmentModifierLiving(EntityLivingBase par0EntityLivingBase, EntityLivingBase par1EntityLivingBase)
/* 153:    */   {
/* 154:222 */     enchantmentModifierLiving.livingModifier = 0.0F;
/* 155:223 */     enchantmentModifierLiving.entityLiving = par1EntityLivingBase;
/* 156:224 */     applyEnchantmentModifier(enchantmentModifierLiving, par0EntityLivingBase.getHeldItem());
/* 157:225 */     return enchantmentModifierLiving.livingModifier;
/* 158:    */   }
/* 159:    */   
/* 160:    */   public static void func_151384_a(EntityLivingBase p_151384_0_, Entity p_151384_1_)
/* 161:    */   {
/* 162:230 */     field_151388_d.field_151363_b = p_151384_1_;
/* 163:231 */     field_151388_d.field_151364_a = p_151384_0_;
/* 164:232 */     applyEnchantmentModifierArray(field_151388_d, p_151384_0_.getLastActiveItems());
/* 165:234 */     if ((p_151384_1_ instanceof EntityPlayer)) {
/* 166:236 */       applyEnchantmentModifier(field_151388_d, p_151384_0_.getHeldItem());
/* 167:    */     }
/* 168:    */   }
/* 169:    */   
/* 170:    */   public static void func_151385_b(EntityLivingBase p_151385_0_, Entity p_151385_1_)
/* 171:    */   {
/* 172:242 */     field_151389_e.field_151366_a = p_151385_0_;
/* 173:243 */     field_151389_e.field_151365_b = p_151385_1_;
/* 174:244 */     applyEnchantmentModifierArray(field_151389_e, p_151385_0_.getLastActiveItems());
/* 175:246 */     if ((p_151385_0_ instanceof EntityPlayer)) {
/* 176:248 */       applyEnchantmentModifier(field_151389_e, p_151385_0_.getHeldItem());
/* 177:    */     }
/* 178:    */   }
/* 179:    */   
/* 180:    */   public static int getKnockbackModifier(EntityLivingBase par0EntityLivingBase, EntityLivingBase par1EntityLivingBase)
/* 181:    */   {
/* 182:257 */     return getEnchantmentLevel(Enchantment.knockback.effectId, par0EntityLivingBase.getHeldItem());
/* 183:    */   }
/* 184:    */   
/* 185:    */   public static int getFireAspectModifier(EntityLivingBase par0EntityLivingBase)
/* 186:    */   {
/* 187:262 */     return getEnchantmentLevel(Enchantment.fireAspect.effectId, par0EntityLivingBase.getHeldItem());
/* 188:    */   }
/* 189:    */   
/* 190:    */   public static int getRespiration(EntityLivingBase par0EntityLivingBase)
/* 191:    */   {
/* 192:270 */     return getMaxEnchantmentLevel(Enchantment.respiration.effectId, par0EntityLivingBase.getLastActiveItems());
/* 193:    */   }
/* 194:    */   
/* 195:    */   public static int getEfficiencyModifier(EntityLivingBase par0EntityLivingBase)
/* 196:    */   {
/* 197:278 */     return getEnchantmentLevel(Enchantment.efficiency.effectId, par0EntityLivingBase.getHeldItem());
/* 198:    */   }
/* 199:    */   
/* 200:    */   public static boolean getSilkTouchModifier(EntityLivingBase par0EntityLivingBase)
/* 201:    */   {
/* 202:286 */     return getEnchantmentLevel(Enchantment.silkTouch.effectId, par0EntityLivingBase.getHeldItem()) > 0;
/* 203:    */   }
/* 204:    */   
/* 205:    */   public static int getFortuneModifier(EntityLivingBase par0EntityLivingBase)
/* 206:    */   {
/* 207:294 */     return getEnchantmentLevel(Enchantment.fortune.effectId, par0EntityLivingBase.getHeldItem());
/* 208:    */   }
/* 209:    */   
/* 210:    */   public static int func_151386_g(EntityLivingBase p_151386_0_)
/* 211:    */   {
/* 212:299 */     return getEnchantmentLevel(Enchantment.field_151370_z.effectId, p_151386_0_.getHeldItem());
/* 213:    */   }
/* 214:    */   
/* 215:    */   public static int func_151387_h(EntityLivingBase p_151387_0_)
/* 216:    */   {
/* 217:304 */     return getEnchantmentLevel(Enchantment.field_151369_A.effectId, p_151387_0_.getHeldItem());
/* 218:    */   }
/* 219:    */   
/* 220:    */   public static int getLootingModifier(EntityLivingBase par0EntityLivingBase)
/* 221:    */   {
/* 222:312 */     return getEnchantmentLevel(Enchantment.looting.effectId, par0EntityLivingBase.getHeldItem());
/* 223:    */   }
/* 224:    */   
/* 225:    */   public static boolean getAquaAffinityModifier(EntityLivingBase par0EntityLivingBase)
/* 226:    */   {
/* 227:320 */     return getMaxEnchantmentLevel(Enchantment.aquaAffinity.effectId, par0EntityLivingBase.getLastActiveItems()) > 0;
/* 228:    */   }
/* 229:    */   
/* 230:    */   public static ItemStack func_92099_a(Enchantment par0Enchantment, EntityLivingBase par1EntityLivingBase)
/* 231:    */   {
/* 232:325 */     ItemStack[] var2 = par1EntityLivingBase.getLastActiveItems();
/* 233:326 */     int var3 = var2.length;
/* 234:328 */     for (int var4 = 0; var4 < var3; var4++)
/* 235:    */     {
/* 236:330 */       ItemStack var5 = var2[var4];
/* 237:332 */       if ((var5 != null) && (getEnchantmentLevel(par0Enchantment.effectId, var5) > 0)) {
/* 238:334 */         return var5;
/* 239:    */       }
/* 240:    */     }
/* 241:338 */     return null;
/* 242:    */   }
/* 243:    */   
/* 244:    */   public static int calcItemStackEnchantability(Random par0Random, int par1, int par2, ItemStack par3ItemStack)
/* 245:    */   {
/* 246:347 */     Item var4 = par3ItemStack.getItem();
/* 247:348 */     int var5 = var4.getItemEnchantability();
/* 248:350 */     if (var5 <= 0) {
/* 249:352 */       return 0;
/* 250:    */     }
/* 251:356 */     if (par2 > 15) {
/* 252:358 */       par2 = 15;
/* 253:    */     }
/* 254:361 */     int var6 = par0Random.nextInt(8) + 1 + (par2 >> 1) + par0Random.nextInt(par2 + 1);
/* 255:362 */     return par1 == 1 ? var6 * 2 / 3 + 1 : par1 == 0 ? Math.max(var6 / 3, 1) : Math.max(var6, par2 * 2);
/* 256:    */   }
/* 257:    */   
/* 258:    */   public static ItemStack addRandomEnchantment(Random par0Random, ItemStack par1ItemStack, int par2)
/* 259:    */   {
/* 260:371 */     List var3 = buildEnchantmentList(par0Random, par1ItemStack, par2);
/* 261:372 */     boolean var4 = par1ItemStack.getItem() == Items.book;
/* 262:374 */     if (var4) {
/* 263:376 */       par1ItemStack.func_150996_a(Items.enchanted_book);
/* 264:    */     }
/* 265:379 */     if (var3 != null)
/* 266:    */     {
/* 267:381 */       Iterator var5 = var3.iterator();
/* 268:383 */       while (var5.hasNext())
/* 269:    */       {
/* 270:385 */         EnchantmentData var6 = (EnchantmentData)var5.next();
/* 271:387 */         if (var4) {
/* 272:389 */           Items.enchanted_book.addEnchantment(par1ItemStack, var6);
/* 273:    */         } else {
/* 274:393 */           par1ItemStack.addEnchantment(var6.enchantmentobj, var6.enchantmentLevel);
/* 275:    */         }
/* 276:    */       }
/* 277:    */     }
/* 278:398 */     return par1ItemStack;
/* 279:    */   }
/* 280:    */   
/* 281:    */   public static List buildEnchantmentList(Random par0Random, ItemStack par1ItemStack, int par2)
/* 282:    */   {
/* 283:407 */     Item var3 = par1ItemStack.getItem();
/* 284:408 */     int var4 = var3.getItemEnchantability();
/* 285:410 */     if (var4 <= 0) {
/* 286:412 */       return null;
/* 287:    */     }
/* 288:416 */     var4 /= 2;
/* 289:417 */     var4 = 1 + par0Random.nextInt((var4 >> 1) + 1) + par0Random.nextInt((var4 >> 1) + 1);
/* 290:418 */     int var5 = var4 + par2;
/* 291:419 */     float var6 = (par0Random.nextFloat() + par0Random.nextFloat() - 1.0F) * 0.15F;
/* 292:420 */     int var7 = (int)(var5 * (1.0F + var6) + 0.5F);
/* 293:422 */     if (var7 < 1) {
/* 294:424 */       var7 = 1;
/* 295:    */     }
/* 296:427 */     ArrayList var8 = null;
/* 297:428 */     Map var9 = mapEnchantmentData(var7, par1ItemStack);
/* 298:430 */     if ((var9 != null) && (!var9.isEmpty()))
/* 299:    */     {
/* 300:432 */       EnchantmentData var10 = (EnchantmentData)WeightedRandom.getRandomItem(par0Random, var9.values());
/* 301:434 */       if (var10 != null)
/* 302:    */       {
/* 303:436 */         var8 = new ArrayList();
/* 304:437 */         var8.add(var10);
/* 305:439 */         for (int var11 = var7; par0Random.nextInt(50) <= var11; var11 >>= 1)
/* 306:    */         {
/* 307:441 */           Iterator var12 = var9.keySet().iterator();
/* 308:443 */           while (var12.hasNext())
/* 309:    */           {
/* 310:445 */             Integer var13 = (Integer)var12.next();
/* 311:446 */             boolean var14 = true;
/* 312:447 */             Iterator var15 = var8.iterator();
/* 313:451 */             while (var15.hasNext())
/* 314:    */             {
/* 315:453 */               EnchantmentData var16 = (EnchantmentData)var15.next();
/* 316:455 */               if (!var16.enchantmentobj.canApplyTogether(Enchantment.enchantmentsList[var13.intValue()])) {
/* 317:460 */                 var14 = false;
/* 318:    */               }
/* 319:    */             }
/* 320:463 */             if (!var14) {
/* 321:465 */               var12.remove();
/* 322:    */             }
/* 323:    */           }
/* 324:472 */           if (!var9.isEmpty())
/* 325:    */           {
/* 326:474 */             EnchantmentData var17 = (EnchantmentData)WeightedRandom.getRandomItem(par0Random, var9.values());
/* 327:475 */             var8.add(var17);
/* 328:    */           }
/* 329:    */         }
/* 330:    */       }
/* 331:    */     }
/* 332:481 */     return var8;
/* 333:    */   }
/* 334:    */   
/* 335:    */   public static Map mapEnchantmentData(int par0, ItemStack par1ItemStack)
/* 336:    */   {
/* 337:491 */     Item var2 = par1ItemStack.getItem();
/* 338:492 */     HashMap var3 = null;
/* 339:493 */     boolean var4 = par1ItemStack.getItem() == Items.book;
/* 340:494 */     Enchantment[] var5 = Enchantment.enchantmentsList;
/* 341:495 */     int var6 = var5.length;
/* 342:497 */     for (int var7 = 0; var7 < var6; var7++)
/* 343:    */     {
/* 344:499 */       Enchantment var8 = var5[var7];
/* 345:501 */       if ((var8 != null) && ((var8.type.canEnchantItem(var2)) || (var4))) {
/* 346:503 */         for (int var9 = var8.getMinLevel(); var9 <= var8.getMaxLevel(); var9++) {
/* 347:505 */           if ((par0 >= var8.getMinEnchantability(var9)) && (par0 <= var8.getMaxEnchantability(var9)))
/* 348:    */           {
/* 349:507 */             if (var3 == null) {
/* 350:509 */               var3 = new HashMap();
/* 351:    */             }
/* 352:512 */             var3.put(Integer.valueOf(var8.effectId), new EnchantmentData(var8, var9));
/* 353:    */           }
/* 354:    */         }
/* 355:    */       }
/* 356:    */     }
/* 357:518 */     return var3;
/* 358:    */   }
/* 359:    */   
/* 360:    */   static final class ModifierDamage
/* 361:    */     implements EnchantmentHelper.IModifier
/* 362:    */   {
/* 363:    */     public int damageModifier;
/* 364:    */     public DamageSource source;
/* 365:    */     private static final String __OBFID = "CL_00000114";
/* 366:    */     
/* 367:    */     private ModifierDamage() {}
/* 368:    */     
/* 369:    */     public void calculateModifier(Enchantment par1Enchantment, int par2)
/* 370:    */     {
/* 371:531 */       this.damageModifier += par1Enchantment.calcModifierDamage(par2, this.source);
/* 372:    */     }
/* 373:    */     
/* 374:    */     ModifierDamage(Object par1Empty3)
/* 375:    */     {
/* 376:536 */       this();
/* 377:    */     }
/* 378:    */   }
/* 379:    */   
/* 380:    */   static final class ModifierLiving
/* 381:    */     implements EnchantmentHelper.IModifier
/* 382:    */   {
/* 383:    */     public float livingModifier;
/* 384:    */     public EntityLivingBase entityLiving;
/* 385:    */     private static final String __OBFID = "CL_00000112";
/* 386:    */     
/* 387:    */     private ModifierLiving() {}
/* 388:    */     
/* 389:    */     public void calculateModifier(Enchantment par1Enchantment, int par2)
/* 390:    */     {
/* 391:555 */       this.livingModifier += par1Enchantment.calcModifierLiving(par2, this.entityLiving);
/* 392:    */     }
/* 393:    */     
/* 394:    */     ModifierLiving(Object par1Empty3)
/* 395:    */     {
/* 396:560 */       this();
/* 397:    */     }
/* 398:    */   }
/* 399:    */   
/* 400:    */   static final class HurtIterator
/* 401:    */     implements EnchantmentHelper.IModifier
/* 402:    */   {
/* 403:    */     public EntityLivingBase field_151364_a;
/* 404:    */     public Entity field_151363_b;
/* 405:    */     private static final String __OBFID = "CL_00000110";
/* 406:    */     
/* 407:    */     private HurtIterator() {}
/* 408:    */     
/* 409:    */     public void calculateModifier(Enchantment par1Enchantment, int par2)
/* 410:    */     {
/* 411:574 */       par1Enchantment.func_151367_b(this.field_151364_a, this.field_151363_b, par2);
/* 412:    */     }
/* 413:    */     
/* 414:    */     HurtIterator(Object p_i45360_1_)
/* 415:    */     {
/* 416:579 */       this();
/* 417:    */     }
/* 418:    */   }
/* 419:    */   
/* 420:    */   static final class DamageIterator
/* 421:    */     implements EnchantmentHelper.IModifier
/* 422:    */   {
/* 423:    */     public EntityLivingBase field_151366_a;
/* 424:    */     public Entity field_151365_b;
/* 425:    */     private static final String __OBFID = "CL_00000109";
/* 426:    */     
/* 427:    */     private DamageIterator() {}
/* 428:    */     
/* 429:    */     public void calculateModifier(Enchantment par1Enchantment, int par2)
/* 430:    */     {
/* 431:593 */       par1Enchantment.func_151368_a(this.field_151366_a, this.field_151365_b, par2);
/* 432:    */     }
/* 433:    */     
/* 434:    */     DamageIterator(Object p_i45359_1_)
/* 435:    */     {
/* 436:598 */       this();
/* 437:    */     }
/* 438:    */   }
/* 439:    */   
/* 440:    */   static abstract interface IModifier
/* 441:    */   {
/* 442:    */     public abstract void calculateModifier(Enchantment paramEnchantment, int paramInt);
/* 443:    */   }
/* 444:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.enchantment.EnchantmentHelper
 * JD-Core Version:    0.7.0.1
 */