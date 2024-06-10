/*   1:    */ package net.minecraft.init;
/*   2:    */ 
/*   3:    */ import java.util.Random;
/*   4:    */ import net.minecraft.block.Block;
/*   5:    */ import net.minecraft.block.BlockDispenser;
/*   6:    */ import net.minecraft.block.BlockFire;
/*   7:    */ import net.minecraft.block.material.Material;
/*   8:    */ import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
/*   9:    */ import net.minecraft.dispenser.BehaviorProjectileDispense;
/*  10:    */ import net.minecraft.dispenser.IBehaviorDispenseItem;
/*  11:    */ import net.minecraft.dispenser.IBlockSource;
/*  12:    */ import net.minecraft.dispenser.IPosition;
/*  13:    */ import net.minecraft.entity.Entity;
/*  14:    */ import net.minecraft.entity.EntityLiving;
/*  15:    */ import net.minecraft.entity.EntityLivingBase;
/*  16:    */ import net.minecraft.entity.IProjectile;
/*  17:    */ import net.minecraft.entity.item.EntityBoat;
/*  18:    */ import net.minecraft.entity.item.EntityExpBottle;
/*  19:    */ import net.minecraft.entity.item.EntityFireworkRocket;
/*  20:    */ import net.minecraft.entity.item.EntityTNTPrimed;
/*  21:    */ import net.minecraft.entity.projectile.EntityArrow;
/*  22:    */ import net.minecraft.entity.projectile.EntityEgg;
/*  23:    */ import net.minecraft.entity.projectile.EntityPotion;
/*  24:    */ import net.minecraft.entity.projectile.EntitySmallFireball;
/*  25:    */ import net.minecraft.entity.projectile.EntitySnowball;
/*  26:    */ import net.minecraft.item.Item;
/*  27:    */ import net.minecraft.item.ItemBucket;
/*  28:    */ import net.minecraft.item.ItemDye;
/*  29:    */ import net.minecraft.item.ItemMonsterPlacer;
/*  30:    */ import net.minecraft.item.ItemPotion;
/*  31:    */ import net.minecraft.item.ItemStack;
/*  32:    */ import net.minecraft.stats.StatList;
/*  33:    */ import net.minecraft.tileentity.TileEntityDispenser;
/*  34:    */ import net.minecraft.util.EnumFacing;
/*  35:    */ import net.minecraft.util.IRegistry;
/*  36:    */ import net.minecraft.world.World;
/*  37:    */ 
/*  38:    */ public class Bootstrap
/*  39:    */ {
/*  40: 39 */   private static boolean field_151355_a = false;
/*  41:    */   private static final String __OBFID = "CL_00001397";
/*  42:    */   
/*  43:    */   static void func_151353_a()
/*  44:    */   {
/*  45: 44 */     BlockDispenser.dispenseBehaviorRegistry.putObject(Items.arrow, new BehaviorProjectileDispense()
/*  46:    */     {
/*  47:    */       private static final String __OBFID = "CL_00001398";
/*  48:    */       
/*  49:    */       protected IProjectile getProjectileEntity(World par1World, IPosition par2IPosition)
/*  50:    */       {
/*  51: 49 */         EntityArrow var3 = new EntityArrow(par1World, par2IPosition.getX(), par2IPosition.getY(), par2IPosition.getZ());
/*  52: 50 */         var3.canBePickedUp = 1;
/*  53: 51 */         return var3;
/*  54:    */       }
/*  55: 53 */     });
/*  56: 54 */     BlockDispenser.dispenseBehaviorRegistry.putObject(Items.egg, new BehaviorProjectileDispense()
/*  57:    */     {
/*  58:    */       private static final String __OBFID = "CL_00001404";
/*  59:    */       
/*  60:    */       protected IProjectile getProjectileEntity(World par1World, IPosition par2IPosition)
/*  61:    */       {
/*  62: 59 */         return new EntityEgg(par1World, par2IPosition.getX(), par2IPosition.getY(), par2IPosition.getZ());
/*  63:    */       }
/*  64: 61 */     });
/*  65: 62 */     BlockDispenser.dispenseBehaviorRegistry.putObject(Items.snowball, new BehaviorProjectileDispense()
/*  66:    */     {
/*  67:    */       private static final String __OBFID = "CL_00001405";
/*  68:    */       
/*  69:    */       protected IProjectile getProjectileEntity(World par1World, IPosition par2IPosition)
/*  70:    */       {
/*  71: 67 */         return new EntitySnowball(par1World, par2IPosition.getX(), par2IPosition.getY(), par2IPosition.getZ());
/*  72:    */       }
/*  73: 69 */     });
/*  74: 70 */     BlockDispenser.dispenseBehaviorRegistry.putObject(Items.experience_bottle, new BehaviorProjectileDispense()
/*  75:    */     {
/*  76:    */       private static final String __OBFID = "CL_00001406";
/*  77:    */       
/*  78:    */       protected IProjectile getProjectileEntity(World par1World, IPosition par2IPosition)
/*  79:    */       {
/*  80: 75 */         return new EntityExpBottle(par1World, par2IPosition.getX(), par2IPosition.getY(), par2IPosition.getZ());
/*  81:    */       }
/*  82:    */       
/*  83:    */       protected float func_82498_a()
/*  84:    */       {
/*  85: 79 */         return super.func_82498_a() * 0.5F;
/*  86:    */       }
/*  87:    */       
/*  88:    */       protected float func_82500_b()
/*  89:    */       {
/*  90: 83 */         return super.func_82500_b() * 1.25F;
/*  91:    */       }
/*  92: 85 */     });
/*  93: 86 */     BlockDispenser.dispenseBehaviorRegistry.putObject(Items.potionitem, new IBehaviorDispenseItem()
/*  94:    */     {
/*  95: 88 */       private final BehaviorDefaultDispenseItem field_150843_b = new BehaviorDefaultDispenseItem();
/*  96:    */       private static final String __OBFID = "CL_00001407";
/*  97:    */       
/*  98:    */       public ItemStack dispense(IBlockSource par1IBlockSource, final ItemStack par2ItemStack)
/*  99:    */       {
/* 100: 92 */         ItemPotion.isSplash(par2ItemStack.getItemDamage()) ? new BehaviorProjectileDispense()
/* 101:    */         {
/* 102:    */           private static final String __OBFID = "CL_00001408";
/* 103:    */           
/* 104:    */           protected IProjectile getProjectileEntity(World par1World, IPosition par2IPosition)
/* 105:    */           {
/* 106: 97 */             return new EntityPotion(par1World, par2IPosition.getX(), par2IPosition.getY(), par2IPosition.getZ(), par2ItemStack.copy());
/* 107:    */           }
/* 108:    */           
/* 109:    */           protected float func_82498_a()
/* 110:    */           {
/* 111:101 */             return super.func_82498_a() * 0.5F;
/* 112:    */           }
/* 113:    */           
/* 114:    */           protected float func_82500_b()
/* 115:    */           {
/* 116:105 */             return super.func_82500_b() * 1.25F;
/* 117:    */           }
/* 118:107 */         }.dispense(par1IBlockSource, par2ItemStack) : this.field_150843_b.dispense(par1IBlockSource, par2ItemStack);
/* 119:    */       }
/* 120:109 */     });
/* 121:110 */     BlockDispenser.dispenseBehaviorRegistry.putObject(Items.spawn_egg, new BehaviorDefaultDispenseItem()
/* 122:    */     {
/* 123:    */       private static final String __OBFID = "CL_00001410";
/* 124:    */       
/* 125:    */       public ItemStack dispenseStack(IBlockSource par1IBlockSource, ItemStack par2ItemStack)
/* 126:    */       {
/* 127:115 */         EnumFacing var3 = BlockDispenser.func_149937_b(par1IBlockSource.getBlockMetadata());
/* 128:116 */         double var4 = par1IBlockSource.getX() + var3.getFrontOffsetX();
/* 129:117 */         double var6 = par1IBlockSource.getYInt() + 0.2F;
/* 130:118 */         double var8 = par1IBlockSource.getZ() + var3.getFrontOffsetZ();
/* 131:119 */         Entity var10 = ItemMonsterPlacer.spawnCreature(par1IBlockSource.getWorld(), par2ItemStack.getItemDamage(), var4, var6, var8);
/* 132:121 */         if (((var10 instanceof EntityLivingBase)) && (par2ItemStack.hasDisplayName())) {
/* 133:123 */           ((EntityLiving)var10).setCustomNameTag(par2ItemStack.getDisplayName());
/* 134:    */         }
/* 135:126 */         par2ItemStack.splitStack(1);
/* 136:127 */         return par2ItemStack;
/* 137:    */       }
/* 138:129 */     });
/* 139:130 */     BlockDispenser.dispenseBehaviorRegistry.putObject(Items.fireworks, new BehaviorDefaultDispenseItem()
/* 140:    */     {
/* 141:    */       private static final String __OBFID = "CL_00001411";
/* 142:    */       
/* 143:    */       public ItemStack dispenseStack(IBlockSource par1IBlockSource, ItemStack par2ItemStack)
/* 144:    */       {
/* 145:135 */         EnumFacing var3 = BlockDispenser.func_149937_b(par1IBlockSource.getBlockMetadata());
/* 146:136 */         double var4 = par1IBlockSource.getX() + var3.getFrontOffsetX();
/* 147:137 */         double var6 = par1IBlockSource.getYInt() + 0.2F;
/* 148:138 */         double var8 = par1IBlockSource.getZ() + var3.getFrontOffsetZ();
/* 149:139 */         EntityFireworkRocket var10 = new EntityFireworkRocket(par1IBlockSource.getWorld(), var4, var6, var8, par2ItemStack);
/* 150:140 */         par1IBlockSource.getWorld().spawnEntityInWorld(var10);
/* 151:141 */         par2ItemStack.splitStack(1);
/* 152:142 */         return par2ItemStack;
/* 153:    */       }
/* 154:    */       
/* 155:    */       protected void playDispenseSound(IBlockSource par1IBlockSource)
/* 156:    */       {
/* 157:146 */         par1IBlockSource.getWorld().playAuxSFX(1002, par1IBlockSource.getXInt(), par1IBlockSource.getYInt(), par1IBlockSource.getZInt(), 0);
/* 158:    */       }
/* 159:148 */     });
/* 160:149 */     BlockDispenser.dispenseBehaviorRegistry.putObject(Items.fire_charge, new BehaviorDefaultDispenseItem()
/* 161:    */     {
/* 162:    */       private static final String __OBFID = "CL_00001412";
/* 163:    */       
/* 164:    */       public ItemStack dispenseStack(IBlockSource par1IBlockSource, ItemStack par2ItemStack)
/* 165:    */       {
/* 166:154 */         EnumFacing var3 = BlockDispenser.func_149937_b(par1IBlockSource.getBlockMetadata());
/* 167:155 */         IPosition var4 = BlockDispenser.func_149939_a(par1IBlockSource);
/* 168:156 */         double var5 = var4.getX() + var3.getFrontOffsetX() * 0.3F;
/* 169:157 */         double var7 = var4.getY() + var3.getFrontOffsetX() * 0.3F;
/* 170:158 */         double var9 = var4.getZ() + var3.getFrontOffsetZ() * 0.3F;
/* 171:159 */         World var11 = par1IBlockSource.getWorld();
/* 172:160 */         Random var12 = var11.rand;
/* 173:161 */         double var13 = var12.nextGaussian() * 0.05D + var3.getFrontOffsetX();
/* 174:162 */         double var15 = var12.nextGaussian() * 0.05D + var3.getFrontOffsetY();
/* 175:163 */         double var17 = var12.nextGaussian() * 0.05D + var3.getFrontOffsetZ();
/* 176:164 */         var11.spawnEntityInWorld(new EntitySmallFireball(var11, var5, var7, var9, var13, var15, var17));
/* 177:165 */         par2ItemStack.splitStack(1);
/* 178:166 */         return par2ItemStack;
/* 179:    */       }
/* 180:    */       
/* 181:    */       protected void playDispenseSound(IBlockSource par1IBlockSource)
/* 182:    */       {
/* 183:170 */         par1IBlockSource.getWorld().playAuxSFX(1009, par1IBlockSource.getXInt(), par1IBlockSource.getYInt(), par1IBlockSource.getZInt(), 0);
/* 184:    */       }
/* 185:172 */     });
/* 186:173 */     BlockDispenser.dispenseBehaviorRegistry.putObject(Items.boat, new BehaviorDefaultDispenseItem()
/* 187:    */     {
/* 188:175 */       private final BehaviorDefaultDispenseItem field_150842_b = new BehaviorDefaultDispenseItem();
/* 189:    */       private static final String __OBFID = "CL_00001413";
/* 190:    */       
/* 191:    */       public ItemStack dispenseStack(IBlockSource par1IBlockSource, ItemStack par2ItemStack)
/* 192:    */       {
/* 193:179 */         EnumFacing var3 = BlockDispenser.func_149937_b(par1IBlockSource.getBlockMetadata());
/* 194:180 */         World var4 = par1IBlockSource.getWorld();
/* 195:181 */         double var5 = par1IBlockSource.getX() + var3.getFrontOffsetX() * 1.125F;
/* 196:182 */         double var7 = par1IBlockSource.getY() + var3.getFrontOffsetY() * 1.125F;
/* 197:183 */         double var9 = par1IBlockSource.getZ() + var3.getFrontOffsetZ() * 1.125F;
/* 198:184 */         int var11 = par1IBlockSource.getXInt() + var3.getFrontOffsetX();
/* 199:185 */         int var12 = par1IBlockSource.getYInt() + var3.getFrontOffsetY();
/* 200:186 */         int var13 = par1IBlockSource.getZInt() + var3.getFrontOffsetZ();
/* 201:187 */         Material var14 = var4.getBlock(var11, var12, var13).getMaterial();
/* 202:    */         double var15;
/* 203:    */         double var15;
/* 204:190 */         if (Material.water.equals(var14))
/* 205:    */         {
/* 206:192 */           var15 = 1.0D;
/* 207:    */         }
/* 208:    */         else
/* 209:    */         {
/* 210:196 */           if ((!Material.air.equals(var14)) || (!Material.water.equals(var4.getBlock(var11, var12 - 1, var13).getMaterial()))) {
/* 211:198 */             return this.field_150842_b.dispense(par1IBlockSource, par2ItemStack);
/* 212:    */           }
/* 213:201 */           var15 = 0.0D;
/* 214:    */         }
/* 215:204 */         EntityBoat var17 = new EntityBoat(var4, var5, var7 + var15, var9);
/* 216:205 */         var4.spawnEntityInWorld(var17);
/* 217:206 */         par2ItemStack.splitStack(1);
/* 218:207 */         return par2ItemStack;
/* 219:    */       }
/* 220:    */       
/* 221:    */       protected void playDispenseSound(IBlockSource par1IBlockSource)
/* 222:    */       {
/* 223:211 */         par1IBlockSource.getWorld().playAuxSFX(1000, par1IBlockSource.getXInt(), par1IBlockSource.getYInt(), par1IBlockSource.getZInt(), 0);
/* 224:    */       }
/* 225:213 */     });
/* 226:214 */     BehaviorDefaultDispenseItem var0 = new BehaviorDefaultDispenseItem()
/* 227:    */     {
/* 228:216 */       private final BehaviorDefaultDispenseItem field_150841_b = new BehaviorDefaultDispenseItem();
/* 229:    */       private static final String __OBFID = "CL_00001399";
/* 230:    */       
/* 231:    */       public ItemStack dispenseStack(IBlockSource par1IBlockSource, ItemStack par2ItemStack)
/* 232:    */       {
/* 233:220 */         ItemBucket var3 = (ItemBucket)par2ItemStack.getItem();
/* 234:221 */         int var4 = par1IBlockSource.getXInt();
/* 235:222 */         int var5 = par1IBlockSource.getYInt();
/* 236:223 */         int var6 = par1IBlockSource.getZInt();
/* 237:224 */         EnumFacing var7 = BlockDispenser.func_149937_b(par1IBlockSource.getBlockMetadata());
/* 238:226 */         if (var3.tryPlaceContainedLiquid(par1IBlockSource.getWorld(), var4 + var7.getFrontOffsetX(), var5 + var7.getFrontOffsetY(), var6 + var7.getFrontOffsetZ()))
/* 239:    */         {
/* 240:228 */           par2ItemStack.func_150996_a(Items.bucket);
/* 241:229 */           par2ItemStack.stackSize = 1;
/* 242:230 */           return par2ItemStack;
/* 243:    */         }
/* 244:234 */         return this.field_150841_b.dispense(par1IBlockSource, par2ItemStack);
/* 245:    */       }
/* 246:237 */     };
/* 247:238 */     BlockDispenser.dispenseBehaviorRegistry.putObject(Items.lava_bucket, var0);
/* 248:239 */     BlockDispenser.dispenseBehaviorRegistry.putObject(Items.water_bucket, var0);
/* 249:240 */     BlockDispenser.dispenseBehaviorRegistry.putObject(Items.bucket, new BehaviorDefaultDispenseItem()
/* 250:    */     {
/* 251:242 */       private final BehaviorDefaultDispenseItem field_150840_b = new BehaviorDefaultDispenseItem();
/* 252:    */       private static final String __OBFID = "CL_00001400";
/* 253:    */       
/* 254:    */       public ItemStack dispenseStack(IBlockSource par1IBlockSource, ItemStack par2ItemStack)
/* 255:    */       {
/* 256:246 */         EnumFacing var3 = BlockDispenser.func_149937_b(par1IBlockSource.getBlockMetadata());
/* 257:247 */         World var4 = par1IBlockSource.getWorld();
/* 258:248 */         int var5 = par1IBlockSource.getXInt() + var3.getFrontOffsetX();
/* 259:249 */         int var6 = par1IBlockSource.getYInt() + var3.getFrontOffsetY();
/* 260:250 */         int var7 = par1IBlockSource.getZInt() + var3.getFrontOffsetZ();
/* 261:251 */         Material var8 = var4.getBlock(var5, var6, var7).getMaterial();
/* 262:252 */         int var9 = var4.getBlockMetadata(var5, var6, var7);
/* 263:    */         Item var10;
/* 264:    */         Item var10;
/* 265:255 */         if ((Material.water.equals(var8)) && (var9 == 0))
/* 266:    */         {
/* 267:257 */           var10 = Items.water_bucket;
/* 268:    */         }
/* 269:    */         else
/* 270:    */         {
/* 271:261 */           if ((!Material.lava.equals(var8)) || (var9 != 0)) {
/* 272:263 */             return super.dispenseStack(par1IBlockSource, par2ItemStack);
/* 273:    */           }
/* 274:266 */           var10 = Items.lava_bucket;
/* 275:    */         }
/* 276:269 */         var4.setBlockToAir(var5, var6, var7);
/* 277:271 */         if (--par2ItemStack.stackSize == 0)
/* 278:    */         {
/* 279:273 */           par2ItemStack.func_150996_a(var10);
/* 280:274 */           par2ItemStack.stackSize = 1;
/* 281:    */         }
/* 282:276 */         else if (((TileEntityDispenser)par1IBlockSource.getBlockTileEntity()).func_146019_a(new ItemStack(var10)) < 0)
/* 283:    */         {
/* 284:278 */           this.field_150840_b.dispense(par1IBlockSource, new ItemStack(var10));
/* 285:    */         }
/* 286:281 */         return par2ItemStack;
/* 287:    */       }
/* 288:283 */     });
/* 289:284 */     BlockDispenser.dispenseBehaviorRegistry.putObject(Items.flint_and_steel, new BehaviorDefaultDispenseItem()
/* 290:    */     {
/* 291:286 */       private boolean field_150839_b = true;
/* 292:    */       private static final String __OBFID = "CL_00001401";
/* 293:    */       
/* 294:    */       protected ItemStack dispenseStack(IBlockSource par1IBlockSource, ItemStack par2ItemStack)
/* 295:    */       {
/* 296:290 */         EnumFacing var3 = BlockDispenser.func_149937_b(par1IBlockSource.getBlockMetadata());
/* 297:291 */         World var4 = par1IBlockSource.getWorld();
/* 298:292 */         int var5 = par1IBlockSource.getXInt() + var3.getFrontOffsetX();
/* 299:293 */         int var6 = par1IBlockSource.getYInt() + var3.getFrontOffsetY();
/* 300:294 */         int var7 = par1IBlockSource.getZInt() + var3.getFrontOffsetZ();
/* 301:296 */         if (var4.isAirBlock(var5, var6, var7))
/* 302:    */         {
/* 303:298 */           var4.setBlock(var5, var6, var7, Blocks.fire);
/* 304:300 */           if (par2ItemStack.attemptDamageItem(1, var4.rand)) {
/* 305:302 */             par2ItemStack.stackSize = 0;
/* 306:    */           }
/* 307:    */         }
/* 308:305 */         else if (var4.getBlock(var5, var6, var7) == Blocks.tnt)
/* 309:    */         {
/* 310:307 */           Blocks.tnt.onBlockDestroyedByPlayer(var4, var5, var6, var7, 1);
/* 311:308 */           var4.setBlockToAir(var5, var6, var7);
/* 312:    */         }
/* 313:    */         else
/* 314:    */         {
/* 315:312 */           this.field_150839_b = false;
/* 316:    */         }
/* 317:315 */         return par2ItemStack;
/* 318:    */       }
/* 319:    */       
/* 320:    */       protected void playDispenseSound(IBlockSource par1IBlockSource)
/* 321:    */       {
/* 322:319 */         if (this.field_150839_b) {
/* 323:321 */           par1IBlockSource.getWorld().playAuxSFX(1000, par1IBlockSource.getXInt(), par1IBlockSource.getYInt(), par1IBlockSource.getZInt(), 0);
/* 324:    */         } else {
/* 325:325 */           par1IBlockSource.getWorld().playAuxSFX(1001, par1IBlockSource.getXInt(), par1IBlockSource.getYInt(), par1IBlockSource.getZInt(), 0);
/* 326:    */         }
/* 327:    */       }
/* 328:328 */     });
/* 329:329 */     BlockDispenser.dispenseBehaviorRegistry.putObject(Items.dye, new BehaviorDefaultDispenseItem()
/* 330:    */     {
/* 331:331 */       private boolean field_150838_b = true;
/* 332:    */       private static final String __OBFID = "CL_00001402";
/* 333:    */       
/* 334:    */       protected ItemStack dispenseStack(IBlockSource par1IBlockSource, ItemStack par2ItemStack)
/* 335:    */       {
/* 336:335 */         if (par2ItemStack.getItemDamage() == 15)
/* 337:    */         {
/* 338:337 */           EnumFacing var3 = BlockDispenser.func_149937_b(par1IBlockSource.getBlockMetadata());
/* 339:338 */           World var4 = par1IBlockSource.getWorld();
/* 340:339 */           int var5 = par1IBlockSource.getXInt() + var3.getFrontOffsetX();
/* 341:340 */           int var6 = par1IBlockSource.getYInt() + var3.getFrontOffsetY();
/* 342:341 */           int var7 = par1IBlockSource.getZInt() + var3.getFrontOffsetZ();
/* 343:343 */           if (ItemDye.func_150919_a(par2ItemStack, var4, var5, var6, var7))
/* 344:    */           {
/* 345:345 */             if (!var4.isClient) {
/* 346:347 */               var4.playAuxSFX(2005, var5, var6, var7, 0);
/* 347:    */             }
/* 348:    */           }
/* 349:    */           else {
/* 350:352 */             this.field_150838_b = false;
/* 351:    */           }
/* 352:355 */           return par2ItemStack;
/* 353:    */         }
/* 354:359 */         return super.dispenseStack(par1IBlockSource, par2ItemStack);
/* 355:    */       }
/* 356:    */       
/* 357:    */       protected void playDispenseSound(IBlockSource par1IBlockSource)
/* 358:    */       {
/* 359:364 */         if (this.field_150838_b) {
/* 360:366 */           par1IBlockSource.getWorld().playAuxSFX(1000, par1IBlockSource.getXInt(), par1IBlockSource.getYInt(), par1IBlockSource.getZInt(), 0);
/* 361:    */         } else {
/* 362:370 */           par1IBlockSource.getWorld().playAuxSFX(1001, par1IBlockSource.getXInt(), par1IBlockSource.getYInt(), par1IBlockSource.getZInt(), 0);
/* 363:    */         }
/* 364:    */       }
/* 365:373 */     });
/* 366:374 */     BlockDispenser.dispenseBehaviorRegistry.putObject(Item.getItemFromBlock(Blocks.tnt), new BehaviorDefaultDispenseItem()
/* 367:    */     {
/* 368:    */       private static final String __OBFID = "CL_00001403";
/* 369:    */       
/* 370:    */       protected ItemStack dispenseStack(IBlockSource par1IBlockSource, ItemStack par2ItemStack)
/* 371:    */       {
/* 372:379 */         EnumFacing var3 = BlockDispenser.func_149937_b(par1IBlockSource.getBlockMetadata());
/* 373:380 */         World var4 = par1IBlockSource.getWorld();
/* 374:381 */         int var5 = par1IBlockSource.getXInt() + var3.getFrontOffsetX();
/* 375:382 */         int var6 = par1IBlockSource.getYInt() + var3.getFrontOffsetY();
/* 376:383 */         int var7 = par1IBlockSource.getZInt() + var3.getFrontOffsetZ();
/* 377:384 */         EntityTNTPrimed var8 = new EntityTNTPrimed(var4, var5 + 0.5F, var6 + 0.5F, var7 + 0.5F, null);
/* 378:385 */         var4.spawnEntityInWorld(var8);
/* 379:386 */         par2ItemStack.stackSize -= 1;
/* 380:387 */         return par2ItemStack;
/* 381:    */       }
/* 382:    */     });
/* 383:    */   }
/* 384:    */   
/* 385:    */   public static void func_151354_b()
/* 386:    */   {
/* 387:394 */     if (!field_151355_a)
/* 388:    */     {
/* 389:396 */       field_151355_a = true;
/* 390:397 */       Block.registerBlocks();
/* 391:398 */       BlockFire.func_149843_e();
/* 392:399 */       Item.registerItems();
/* 393:400 */       StatList.func_151178_a();
/* 394:401 */       func_151353_a();
/* 395:    */     }
/* 396:    */   }
/* 397:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.init.Bootstrap
 * JD-Core Version:    0.7.0.1
 */