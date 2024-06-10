/*    1:     */ package net.minecraft.entity.item;
/*    2:     */ 
/*    3:     */ import java.util.List;
/*    4:     */ import net.minecraft.block.Block;
/*    5:     */ import net.minecraft.block.BlockRailBase;
/*    6:     */ import net.minecraft.block.material.Material;
/*    7:     */ import net.minecraft.entity.DataWatcher;
/*    8:     */ import net.minecraft.entity.Entity;
/*    9:     */ import net.minecraft.entity.EntityLivingBase;
/*   10:     */ import net.minecraft.entity.EntityMinecartCommandBlock;
/*   11:     */ import net.minecraft.entity.ai.EntityMinecartMobSpawner;
/*   12:     */ import net.minecraft.entity.monster.EntityIronGolem;
/*   13:     */ import net.minecraft.entity.player.EntityPlayer;
/*   14:     */ import net.minecraft.entity.player.PlayerCapabilities;
/*   15:     */ import net.minecraft.init.Blocks;
/*   16:     */ import net.minecraft.init.Items;
/*   17:     */ import net.minecraft.item.ItemStack;
/*   18:     */ import net.minecraft.nbt.NBTTagCompound;
/*   19:     */ import net.minecraft.profiler.Profiler;
/*   20:     */ import net.minecraft.server.MinecraftServer;
/*   21:     */ import net.minecraft.util.AxisAlignedBB;
/*   22:     */ import net.minecraft.util.DamageSource;
/*   23:     */ import net.minecraft.util.MathHelper;
/*   24:     */ import net.minecraft.util.Vec3;
/*   25:     */ import net.minecraft.util.Vec3Pool;
/*   26:     */ import net.minecraft.world.World;
/*   27:     */ import net.minecraft.world.WorldProvider;
/*   28:     */ import net.minecraft.world.WorldServer;
/*   29:     */ 
/*   30:     */ public abstract class EntityMinecart
/*   31:     */   extends Entity
/*   32:     */ {
/*   33:     */   private boolean isInReverse;
/*   34:     */   private String entityName;
/*   35:  31 */   private static final int[][][] matrix = { { { 0, 0, -1 }, { 0, 0, 1 } }, { { -1 }, { 1 } }, { { -1, -1 }, { 1 } }, { { -1 }, { 1, -1 } }, { { 0, 0, -1 }, { 0, -1, 1 } }, { { 0, -1, -1 }, { 0, 0, 1 } }, { { 0, 0, 1 }, { 1 } }, { { 0, 0, 1 }, { -1 } }, { { 0, 0, -1 }, { -1 } }, { { 0, 0, -1 }, { 1 } } };
/*   36:     */   private int turnProgress;
/*   37:     */   private double minecartX;
/*   38:     */   private double minecartY;
/*   39:     */   private double minecartZ;
/*   40:     */   private double minecartYaw;
/*   41:     */   private double minecartPitch;
/*   42:     */   private double velocityX;
/*   43:     */   private double velocityY;
/*   44:     */   private double velocityZ;
/*   45:     */   private static final String __OBFID = "CL_00001670";
/*   46:     */   
/*   47:     */   public EntityMinecart(World par1World)
/*   48:     */   {
/*   49:  47 */     super(par1World);
/*   50:  48 */     this.preventEntitySpawning = true;
/*   51:  49 */     setSize(0.98F, 0.7F);
/*   52:  50 */     this.yOffset = (this.height / 2.0F);
/*   53:     */   }
/*   54:     */   
/*   55:     */   public static EntityMinecart createMinecart(World par0World, double par1, double par3, double par5, int par7)
/*   56:     */   {
/*   57:  61 */     switch (par7)
/*   58:     */     {
/*   59:     */     case 1: 
/*   60:  64 */       return new EntityMinecartChest(par0World, par1, par3, par5);
/*   61:     */     case 2: 
/*   62:  67 */       return new EntityMinecartFurnace(par0World, par1, par3, par5);
/*   63:     */     case 3: 
/*   64:  70 */       return new EntityMinecartTNT(par0World, par1, par3, par5);
/*   65:     */     case 4: 
/*   66:  73 */       return new EntityMinecartMobSpawner(par0World, par1, par3, par5);
/*   67:     */     case 5: 
/*   68:  76 */       return new EntityMinecartHopper(par0World, par1, par3, par5);
/*   69:     */     case 6: 
/*   70:  79 */       return new EntityMinecartCommandBlock(par0World, par1, par3, par5);
/*   71:     */     }
/*   72:  82 */     return new EntityMinecartEmpty(par0World, par1, par3, par5);
/*   73:     */   }
/*   74:     */   
/*   75:     */   protected boolean canTriggerWalking()
/*   76:     */   {
/*   77:  92 */     return false;
/*   78:     */   }
/*   79:     */   
/*   80:     */   protected void entityInit()
/*   81:     */   {
/*   82:  97 */     this.dataWatcher.addObject(17, new Integer(0));
/*   83:  98 */     this.dataWatcher.addObject(18, new Integer(1));
/*   84:  99 */     this.dataWatcher.addObject(19, new Float(0.0F));
/*   85: 100 */     this.dataWatcher.addObject(20, new Integer(0));
/*   86: 101 */     this.dataWatcher.addObject(21, new Integer(6));
/*   87: 102 */     this.dataWatcher.addObject(22, Byte.valueOf((byte)0));
/*   88:     */   }
/*   89:     */   
/*   90:     */   public AxisAlignedBB getCollisionBox(Entity par1Entity)
/*   91:     */   {
/*   92: 111 */     return par1Entity.canBePushed() ? par1Entity.boundingBox : null;
/*   93:     */   }
/*   94:     */   
/*   95:     */   public AxisAlignedBB getBoundingBox()
/*   96:     */   {
/*   97: 119 */     return null;
/*   98:     */   }
/*   99:     */   
/*  100:     */   public boolean canBePushed()
/*  101:     */   {
/*  102: 127 */     return true;
/*  103:     */   }
/*  104:     */   
/*  105:     */   public EntityMinecart(World par1World, double par2, double par4, double par6)
/*  106:     */   {
/*  107: 132 */     this(par1World);
/*  108: 133 */     setPosition(par2, par4, par6);
/*  109: 134 */     this.motionX = 0.0D;
/*  110: 135 */     this.motionY = 0.0D;
/*  111: 136 */     this.motionZ = 0.0D;
/*  112: 137 */     this.prevPosX = par2;
/*  113: 138 */     this.prevPosY = par4;
/*  114: 139 */     this.prevPosZ = par6;
/*  115:     */   }
/*  116:     */   
/*  117:     */   public double getMountedYOffset()
/*  118:     */   {
/*  119: 147 */     return this.height * 0.0D - 0.300000011920929D;
/*  120:     */   }
/*  121:     */   
/*  122:     */   public boolean attackEntityFrom(DamageSource par1DamageSource, float par2)
/*  123:     */   {
/*  124: 155 */     if ((!this.worldObj.isClient) && (!this.isDead))
/*  125:     */     {
/*  126: 157 */       if (isEntityInvulnerable()) {
/*  127: 159 */         return false;
/*  128:     */       }
/*  129: 163 */       setRollingDirection(-getRollingDirection());
/*  130: 164 */       setRollingAmplitude(10);
/*  131: 165 */       setBeenAttacked();
/*  132: 166 */       setDamage(getDamage() + par2 * 10.0F);
/*  133: 167 */       boolean var3 = ((par1DamageSource.getEntity() instanceof EntityPlayer)) && (((EntityPlayer)par1DamageSource.getEntity()).capabilities.isCreativeMode);
/*  134: 169 */       if ((var3) || (getDamage() > 40.0F))
/*  135:     */       {
/*  136: 171 */         if (this.riddenByEntity != null) {
/*  137: 173 */           this.riddenByEntity.mountEntity(this);
/*  138:     */         }
/*  139: 176 */         if ((var3) && (!isInventoryNameLocalized())) {
/*  140: 178 */           setDead();
/*  141:     */         } else {
/*  142: 182 */           killMinecart(par1DamageSource);
/*  143:     */         }
/*  144:     */       }
/*  145: 186 */       return true;
/*  146:     */     }
/*  147: 191 */     return true;
/*  148:     */   }
/*  149:     */   
/*  150:     */   public void killMinecart(DamageSource par1DamageSource)
/*  151:     */   {
/*  152: 197 */     setDead();
/*  153: 198 */     ItemStack var2 = new ItemStack(Items.minecart, 1);
/*  154: 200 */     if (this.entityName != null) {
/*  155: 202 */       var2.setStackDisplayName(this.entityName);
/*  156:     */     }
/*  157: 205 */     entityDropItem(var2, 0.0F);
/*  158:     */   }
/*  159:     */   
/*  160:     */   public void performHurtAnimation()
/*  161:     */   {
/*  162: 213 */     setRollingDirection(-getRollingDirection());
/*  163: 214 */     setRollingAmplitude(10);
/*  164: 215 */     setDamage(getDamage() + getDamage() * 10.0F);
/*  165:     */   }
/*  166:     */   
/*  167:     */   public boolean canBeCollidedWith()
/*  168:     */   {
/*  169: 223 */     return !this.isDead;
/*  170:     */   }
/*  171:     */   
/*  172:     */   public void setDead()
/*  173:     */   {
/*  174: 231 */     super.setDead();
/*  175:     */   }
/*  176:     */   
/*  177:     */   public void onUpdate()
/*  178:     */   {
/*  179: 239 */     if (getRollingAmplitude() > 0) {
/*  180: 241 */       setRollingAmplitude(getRollingAmplitude() - 1);
/*  181:     */     }
/*  182: 244 */     if (getDamage() > 0.0F) {
/*  183: 246 */       setDamage(getDamage() - 1.0F);
/*  184:     */     }
/*  185: 249 */     if (this.posY < -64.0D) {
/*  186: 251 */       kill();
/*  187:     */     }
/*  188: 256 */     if ((!this.worldObj.isClient) && ((this.worldObj instanceof WorldServer)))
/*  189:     */     {
/*  190: 258 */       this.worldObj.theProfiler.startSection("portal");
/*  191: 259 */       MinecraftServer var1 = ((WorldServer)this.worldObj).func_73046_m();
/*  192: 260 */       int var2 = getMaxInPortalTime();
/*  193: 262 */       if (this.inPortal)
/*  194:     */       {
/*  195: 264 */         if (var1.getAllowNether())
/*  196:     */         {
/*  197: 266 */           if ((this.ridingEntity == null) && (this.portalCounter++ >= var2))
/*  198:     */           {
/*  199: 268 */             this.portalCounter = var2;
/*  200: 269 */             this.timeUntilPortal = getPortalCooldown();
/*  201:     */             byte var3;
/*  202:     */             byte var3;
/*  203: 272 */             if (this.worldObj.provider.dimensionId == -1) {
/*  204: 274 */               var3 = 0;
/*  205:     */             } else {
/*  206: 278 */               var3 = -1;
/*  207:     */             }
/*  208: 281 */             travelToDimension(var3);
/*  209:     */           }
/*  210: 284 */           this.inPortal = false;
/*  211:     */         }
/*  212:     */       }
/*  213:     */       else
/*  214:     */       {
/*  215: 289 */         if (this.portalCounter > 0) {
/*  216: 291 */           this.portalCounter -= 4;
/*  217:     */         }
/*  218: 294 */         if (this.portalCounter < 0) {
/*  219: 296 */           this.portalCounter = 0;
/*  220:     */         }
/*  221:     */       }
/*  222: 300 */       if (this.timeUntilPortal > 0) {
/*  223: 302 */         this.timeUntilPortal -= 1;
/*  224:     */       }
/*  225: 305 */       this.worldObj.theProfiler.endSection();
/*  226:     */     }
/*  227: 308 */     if (this.worldObj.isClient)
/*  228:     */     {
/*  229: 310 */       if (this.turnProgress > 0)
/*  230:     */       {
/*  231: 312 */         double var19 = this.posX + (this.minecartX - this.posX) / this.turnProgress;
/*  232: 313 */         double var21 = this.posY + (this.minecartY - this.posY) / this.turnProgress;
/*  233: 314 */         double var5 = this.posZ + (this.minecartZ - this.posZ) / this.turnProgress;
/*  234: 315 */         double var7 = MathHelper.wrapAngleTo180_double(this.minecartYaw - this.rotationYaw);
/*  235: 316 */         this.rotationYaw = ((float)(this.rotationYaw + var7 / this.turnProgress));
/*  236: 317 */         this.rotationPitch = ((float)(this.rotationPitch + (this.minecartPitch - this.rotationPitch) / this.turnProgress));
/*  237: 318 */         this.turnProgress -= 1;
/*  238: 319 */         setPosition(var19, var21, var5);
/*  239: 320 */         setRotation(this.rotationYaw, this.rotationPitch);
/*  240:     */       }
/*  241:     */       else
/*  242:     */       {
/*  243: 324 */         setPosition(this.posX, this.posY, this.posZ);
/*  244: 325 */         setRotation(this.rotationYaw, this.rotationPitch);
/*  245:     */       }
/*  246:     */     }
/*  247:     */     else
/*  248:     */     {
/*  249: 330 */       this.prevPosX = this.posX;
/*  250: 331 */       this.prevPosY = this.posY;
/*  251: 332 */       this.prevPosZ = this.posZ;
/*  252: 333 */       this.motionY -= 0.03999999910593033D;
/*  253: 334 */       int var18 = MathHelper.floor_double(this.posX);
/*  254: 335 */       int var2 = MathHelper.floor_double(this.posY);
/*  255: 336 */       int var20 = MathHelper.floor_double(this.posZ);
/*  256: 338 */       if (BlockRailBase.func_150049_b_(this.worldObj, var18, var2 - 1, var20)) {
/*  257: 340 */         var2--;
/*  258:     */       }
/*  259: 343 */       double var4 = 0.4D;
/*  260: 344 */       double var6 = 0.0078125D;
/*  261: 345 */       Block var8 = this.worldObj.getBlock(var18, var2, var20);
/*  262: 347 */       if (BlockRailBase.func_150051_a(var8))
/*  263:     */       {
/*  264: 349 */         int var9 = this.worldObj.getBlockMetadata(var18, var2, var20);
/*  265: 350 */         func_145821_a(var18, var2, var20, var4, var6, var8, var9);
/*  266: 352 */         if (var8 == Blocks.activator_rail) {
/*  267: 354 */           onActivatorRailPass(var18, var2, var20, (var9 & 0x8) != 0);
/*  268:     */         }
/*  269:     */       }
/*  270:     */       else
/*  271:     */       {
/*  272: 359 */         func_94088_b(var4);
/*  273:     */       }
/*  274: 362 */       func_145775_I();
/*  275: 363 */       this.rotationPitch = 0.0F;
/*  276: 364 */       double var22 = this.prevPosX - this.posX;
/*  277: 365 */       double var11 = this.prevPosZ - this.posZ;
/*  278: 367 */       if (var22 * var22 + var11 * var11 > 0.001D)
/*  279:     */       {
/*  280: 369 */         this.rotationYaw = ((float)(Math.atan2(var11, var22) * 180.0D / 3.141592653589793D));
/*  281: 371 */         if (this.isInReverse) {
/*  282: 373 */           this.rotationYaw += 180.0F;
/*  283:     */         }
/*  284:     */       }
/*  285: 377 */       double var13 = MathHelper.wrapAngleTo180_float(this.rotationYaw - this.prevRotationYaw);
/*  286: 379 */       if ((var13 < -170.0D) || (var13 >= 170.0D))
/*  287:     */       {
/*  288: 381 */         this.rotationYaw += 180.0F;
/*  289: 382 */         this.isInReverse = (!this.isInReverse);
/*  290:     */       }
/*  291: 385 */       setRotation(this.rotationYaw, this.rotationPitch);
/*  292: 386 */       List var15 = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.expand(0.2000000029802322D, 0.0D, 0.2000000029802322D));
/*  293: 388 */       if ((var15 != null) && (!var15.isEmpty())) {
/*  294: 390 */         for (int var16 = 0; var16 < var15.size(); var16++)
/*  295:     */         {
/*  296: 392 */           Entity var17 = (Entity)var15.get(var16);
/*  297: 394 */           if ((var17 != this.riddenByEntity) && (var17.canBePushed()) && ((var17 instanceof EntityMinecart))) {
/*  298: 396 */             var17.applyEntityCollision(this);
/*  299:     */           }
/*  300:     */         }
/*  301:     */       }
/*  302: 401 */       if ((this.riddenByEntity != null) && (this.riddenByEntity.isDead))
/*  303:     */       {
/*  304: 403 */         if (this.riddenByEntity.ridingEntity == this) {
/*  305: 405 */           this.riddenByEntity.ridingEntity = null;
/*  306:     */         }
/*  307: 408 */         this.riddenByEntity = null;
/*  308:     */       }
/*  309:     */     }
/*  310:     */   }
/*  311:     */   
/*  312:     */   public void onActivatorRailPass(int par1, int par2, int par3, boolean par4) {}
/*  313:     */   
/*  314:     */   protected void func_94088_b(double par1)
/*  315:     */   {
/*  316: 420 */     if (this.motionX < -par1) {
/*  317: 422 */       this.motionX = (-par1);
/*  318:     */     }
/*  319: 425 */     if (this.motionX > par1) {
/*  320: 427 */       this.motionX = par1;
/*  321:     */     }
/*  322: 430 */     if (this.motionZ < -par1) {
/*  323: 432 */       this.motionZ = (-par1);
/*  324:     */     }
/*  325: 435 */     if (this.motionZ > par1) {
/*  326: 437 */       this.motionZ = par1;
/*  327:     */     }
/*  328: 440 */     if (this.onGround)
/*  329:     */     {
/*  330: 442 */       this.motionX *= 0.5D;
/*  331: 443 */       this.motionY *= 0.5D;
/*  332: 444 */       this.motionZ *= 0.5D;
/*  333:     */     }
/*  334: 447 */     moveEntity(this.motionX, this.motionY, this.motionZ);
/*  335: 449 */     if (!this.onGround)
/*  336:     */     {
/*  337: 451 */       this.motionX *= 0.949999988079071D;
/*  338: 452 */       this.motionY *= 0.949999988079071D;
/*  339: 453 */       this.motionZ *= 0.949999988079071D;
/*  340:     */     }
/*  341:     */   }
/*  342:     */   
/*  343:     */   protected void func_145821_a(int p_145821_1_, int p_145821_2_, int p_145821_3_, double p_145821_4_, double p_145821_6_, Block p_145821_8_, int p_145821_9_)
/*  344:     */   {
/*  345: 459 */     this.fallDistance = 0.0F;
/*  346: 460 */     Vec3 var10 = func_70489_a(this.posX, this.posY, this.posZ);
/*  347: 461 */     this.posY = p_145821_2_;
/*  348: 462 */     boolean var11 = false;
/*  349: 463 */     boolean var12 = false;
/*  350: 465 */     if (p_145821_8_ == Blocks.golden_rail)
/*  351:     */     {
/*  352: 467 */       var11 = (p_145821_9_ & 0x8) != 0;
/*  353: 468 */       var12 = !var11;
/*  354:     */     }
/*  355: 471 */     if (((BlockRailBase)p_145821_8_).func_150050_e()) {
/*  356: 473 */       p_145821_9_ &= 0x7;
/*  357:     */     }
/*  358: 476 */     if ((p_145821_9_ >= 2) && (p_145821_9_ <= 5)) {
/*  359: 478 */       this.posY = (p_145821_2_ + 1);
/*  360:     */     }
/*  361: 481 */     if (p_145821_9_ == 2) {
/*  362: 483 */       this.motionX -= p_145821_6_;
/*  363:     */     }
/*  364: 486 */     if (p_145821_9_ == 3) {
/*  365: 488 */       this.motionX += p_145821_6_;
/*  366:     */     }
/*  367: 491 */     if (p_145821_9_ == 4) {
/*  368: 493 */       this.motionZ += p_145821_6_;
/*  369:     */     }
/*  370: 496 */     if (p_145821_9_ == 5) {
/*  371: 498 */       this.motionZ -= p_145821_6_;
/*  372:     */     }
/*  373: 501 */     int[][] var13 = matrix[p_145821_9_];
/*  374: 502 */     double var14 = var13[1][0] - var13[0][0];
/*  375: 503 */     double var16 = var13[1][2] - var13[0][2];
/*  376: 504 */     double var18 = Math.sqrt(var14 * var14 + var16 * var16);
/*  377: 505 */     double var20 = this.motionX * var14 + this.motionZ * var16;
/*  378: 507 */     if (var20 < 0.0D)
/*  379:     */     {
/*  380: 509 */       var14 = -var14;
/*  381: 510 */       var16 = -var16;
/*  382:     */     }
/*  383: 513 */     double var22 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
/*  384: 515 */     if (var22 > 2.0D) {
/*  385: 517 */       var22 = 2.0D;
/*  386:     */     }
/*  387: 520 */     this.motionX = (var22 * var14 / var18);
/*  388: 521 */     this.motionZ = (var22 * var16 / var18);
/*  389: 527 */     if ((this.riddenByEntity != null) && ((this.riddenByEntity instanceof EntityLivingBase)))
/*  390:     */     {
/*  391: 529 */       double var24 = ((EntityLivingBase)this.riddenByEntity).moveForward;
/*  392: 531 */       if (var24 > 0.0D)
/*  393:     */       {
/*  394: 533 */         double var26 = -Math.sin(this.riddenByEntity.rotationYaw * 3.141593F / 180.0F);
/*  395: 534 */         double var28 = Math.cos(this.riddenByEntity.rotationYaw * 3.141593F / 180.0F);
/*  396: 535 */         double var30 = this.motionX * this.motionX + this.motionZ * this.motionZ;
/*  397: 537 */         if (var30 < 0.01D)
/*  398:     */         {
/*  399: 539 */           this.motionX += var26 * 0.1D;
/*  400: 540 */           this.motionZ += var28 * 0.1D;
/*  401: 541 */           var12 = false;
/*  402:     */         }
/*  403:     */       }
/*  404:     */     }
/*  405: 546 */     if (var12)
/*  406:     */     {
/*  407: 548 */       double var24 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
/*  408: 550 */       if (var24 < 0.03D)
/*  409:     */       {
/*  410: 552 */         this.motionX *= 0.0D;
/*  411: 553 */         this.motionY *= 0.0D;
/*  412: 554 */         this.motionZ *= 0.0D;
/*  413:     */       }
/*  414:     */       else
/*  415:     */       {
/*  416: 558 */         this.motionX *= 0.5D;
/*  417: 559 */         this.motionY *= 0.0D;
/*  418: 560 */         this.motionZ *= 0.5D;
/*  419:     */       }
/*  420:     */     }
/*  421: 564 */     double var24 = 0.0D;
/*  422: 565 */     double var26 = p_145821_1_ + 0.5D + var13[0][0] * 0.5D;
/*  423: 566 */     double var28 = p_145821_3_ + 0.5D + var13[0][2] * 0.5D;
/*  424: 567 */     double var30 = p_145821_1_ + 0.5D + var13[1][0] * 0.5D;
/*  425: 568 */     double var32 = p_145821_3_ + 0.5D + var13[1][2] * 0.5D;
/*  426: 569 */     var14 = var30 - var26;
/*  427: 570 */     var16 = var32 - var28;
/*  428: 574 */     if (var14 == 0.0D)
/*  429:     */     {
/*  430: 576 */       this.posX = (p_145821_1_ + 0.5D);
/*  431: 577 */       var24 = this.posZ - p_145821_3_;
/*  432:     */     }
/*  433: 579 */     else if (var16 == 0.0D)
/*  434:     */     {
/*  435: 581 */       this.posZ = (p_145821_3_ + 0.5D);
/*  436: 582 */       var24 = this.posX - p_145821_1_;
/*  437:     */     }
/*  438:     */     else
/*  439:     */     {
/*  440: 586 */       double var34 = this.posX - var26;
/*  441: 587 */       double var36 = this.posZ - var28;
/*  442: 588 */       var24 = (var34 * var14 + var36 * var16) * 2.0D;
/*  443:     */     }
/*  444: 591 */     this.posX = (var26 + var14 * var24);
/*  445: 592 */     this.posZ = (var28 + var16 * var24);
/*  446: 593 */     setPosition(this.posX, this.posY + this.yOffset, this.posZ);
/*  447: 594 */     double var34 = this.motionX;
/*  448: 595 */     double var36 = this.motionZ;
/*  449: 597 */     if (this.riddenByEntity != null)
/*  450:     */     {
/*  451: 599 */       var34 *= 0.75D;
/*  452: 600 */       var36 *= 0.75D;
/*  453:     */     }
/*  454: 603 */     if (var34 < -p_145821_4_) {
/*  455: 605 */       var34 = -p_145821_4_;
/*  456:     */     }
/*  457: 608 */     if (var34 > p_145821_4_) {
/*  458: 610 */       var34 = p_145821_4_;
/*  459:     */     }
/*  460: 613 */     if (var36 < -p_145821_4_) {
/*  461: 615 */       var36 = -p_145821_4_;
/*  462:     */     }
/*  463: 618 */     if (var36 > p_145821_4_) {
/*  464: 620 */       var36 = p_145821_4_;
/*  465:     */     }
/*  466: 623 */     moveEntity(var34, 0.0D, var36);
/*  467: 625 */     if ((var13[0][1] != 0) && (MathHelper.floor_double(this.posX) - p_145821_1_ == var13[0][0]) && (MathHelper.floor_double(this.posZ) - p_145821_3_ == var13[0][2])) {
/*  468: 627 */       setPosition(this.posX, this.posY + var13[0][1], this.posZ);
/*  469: 629 */     } else if ((var13[1][1] != 0) && (MathHelper.floor_double(this.posX) - p_145821_1_ == var13[1][0]) && (MathHelper.floor_double(this.posZ) - p_145821_3_ == var13[1][2])) {
/*  470: 631 */       setPosition(this.posX, this.posY + var13[1][1], this.posZ);
/*  471:     */     }
/*  472: 634 */     applyDrag();
/*  473: 635 */     Vec3 var38 = func_70489_a(this.posX, this.posY, this.posZ);
/*  474: 637 */     if ((var38 != null) && (var10 != null))
/*  475:     */     {
/*  476: 639 */       double var39 = (var10.yCoord - var38.yCoord) * 0.05D;
/*  477: 640 */       var22 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
/*  478: 642 */       if (var22 > 0.0D)
/*  479:     */       {
/*  480: 644 */         this.motionX = (this.motionX / var22 * (var22 + var39));
/*  481: 645 */         this.motionZ = (this.motionZ / var22 * (var22 + var39));
/*  482:     */       }
/*  483: 648 */       setPosition(this.posX, var38.yCoord, this.posZ);
/*  484:     */     }
/*  485: 651 */     int var45 = MathHelper.floor_double(this.posX);
/*  486: 652 */     int var40 = MathHelper.floor_double(this.posZ);
/*  487: 654 */     if ((var45 != p_145821_1_) || (var40 != p_145821_3_))
/*  488:     */     {
/*  489: 656 */       var22 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
/*  490: 657 */       this.motionX = (var22 * (var45 - p_145821_1_));
/*  491: 658 */       this.motionZ = (var22 * (var40 - p_145821_3_));
/*  492:     */     }
/*  493: 661 */     if (var11)
/*  494:     */     {
/*  495: 663 */       double var41 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
/*  496: 665 */       if (var41 > 0.01D)
/*  497:     */       {
/*  498: 667 */         double var43 = 0.06D;
/*  499: 668 */         this.motionX += this.motionX / var41 * var43;
/*  500: 669 */         this.motionZ += this.motionZ / var41 * var43;
/*  501:     */       }
/*  502: 671 */       else if (p_145821_9_ == 1)
/*  503:     */       {
/*  504: 673 */         if (this.worldObj.getBlock(p_145821_1_ - 1, p_145821_2_, p_145821_3_).isNormalCube()) {
/*  505: 675 */           this.motionX = 0.02D;
/*  506: 677 */         } else if (this.worldObj.getBlock(p_145821_1_ + 1, p_145821_2_, p_145821_3_).isNormalCube()) {
/*  507: 679 */           this.motionX = -0.02D;
/*  508:     */         }
/*  509:     */       }
/*  510: 682 */       else if (p_145821_9_ == 0)
/*  511:     */       {
/*  512: 684 */         if (this.worldObj.getBlock(p_145821_1_, p_145821_2_, p_145821_3_ - 1).isNormalCube()) {
/*  513: 686 */           this.motionZ = 0.02D;
/*  514: 688 */         } else if (this.worldObj.getBlock(p_145821_1_, p_145821_2_, p_145821_3_ + 1).isNormalCube()) {
/*  515: 690 */           this.motionZ = -0.02D;
/*  516:     */         }
/*  517:     */       }
/*  518:     */     }
/*  519:     */   }
/*  520:     */   
/*  521:     */   protected void applyDrag()
/*  522:     */   {
/*  523: 698 */     if (this.riddenByEntity != null)
/*  524:     */     {
/*  525: 700 */       this.motionX *= 0.996999979019165D;
/*  526: 701 */       this.motionY *= 0.0D;
/*  527: 702 */       this.motionZ *= 0.996999979019165D;
/*  528:     */     }
/*  529:     */     else
/*  530:     */     {
/*  531: 706 */       this.motionX *= 0.9599999785423279D;
/*  532: 707 */       this.motionY *= 0.0D;
/*  533: 708 */       this.motionZ *= 0.9599999785423279D;
/*  534:     */     }
/*  535:     */   }
/*  536:     */   
/*  537:     */   public Vec3 func_70495_a(double par1, double par3, double par5, double par7)
/*  538:     */   {
/*  539: 714 */     int var9 = MathHelper.floor_double(par1);
/*  540: 715 */     int var10 = MathHelper.floor_double(par3);
/*  541: 716 */     int var11 = MathHelper.floor_double(par5);
/*  542: 718 */     if (BlockRailBase.func_150049_b_(this.worldObj, var9, var10 - 1, var11)) {
/*  543: 720 */       var10--;
/*  544:     */     }
/*  545: 723 */     Block var12 = this.worldObj.getBlock(var9, var10, var11);
/*  546: 725 */     if (!BlockRailBase.func_150051_a(var12)) {
/*  547: 727 */       return null;
/*  548:     */     }
/*  549: 731 */     int var13 = this.worldObj.getBlockMetadata(var9, var10, var11);
/*  550: 733 */     if (((BlockRailBase)var12).func_150050_e()) {
/*  551: 735 */       var13 &= 0x7;
/*  552:     */     }
/*  553: 738 */     par3 = var10;
/*  554: 740 */     if ((var13 >= 2) && (var13 <= 5)) {
/*  555: 742 */       par3 = var10 + 1;
/*  556:     */     }
/*  557: 745 */     int[][] var14 = matrix[var13];
/*  558: 746 */     double var15 = var14[1][0] - var14[0][0];
/*  559: 747 */     double var17 = var14[1][2] - var14[0][2];
/*  560: 748 */     double var19 = Math.sqrt(var15 * var15 + var17 * var17);
/*  561: 749 */     var15 /= var19;
/*  562: 750 */     var17 /= var19;
/*  563: 751 */     par1 += var15 * par7;
/*  564: 752 */     par5 += var17 * par7;
/*  565: 754 */     if ((var14[0][1] != 0) && (MathHelper.floor_double(par1) - var9 == var14[0][0]) && (MathHelper.floor_double(par5) - var11 == var14[0][2])) {
/*  566: 756 */       par3 += var14[0][1];
/*  567: 758 */     } else if ((var14[1][1] != 0) && (MathHelper.floor_double(par1) - var9 == var14[1][0]) && (MathHelper.floor_double(par5) - var11 == var14[1][2])) {
/*  568: 760 */       par3 += var14[1][1];
/*  569:     */     }
/*  570: 763 */     return func_70489_a(par1, par3, par5);
/*  571:     */   }
/*  572:     */   
/*  573:     */   public Vec3 func_70489_a(double par1, double par3, double par5)
/*  574:     */   {
/*  575: 769 */     int var7 = MathHelper.floor_double(par1);
/*  576: 770 */     int var8 = MathHelper.floor_double(par3);
/*  577: 771 */     int var9 = MathHelper.floor_double(par5);
/*  578: 773 */     if (BlockRailBase.func_150049_b_(this.worldObj, var7, var8 - 1, var9)) {
/*  579: 775 */       var8--;
/*  580:     */     }
/*  581: 778 */     Block var10 = this.worldObj.getBlock(var7, var8, var9);
/*  582: 780 */     if (BlockRailBase.func_150051_a(var10))
/*  583:     */     {
/*  584: 782 */       int var11 = this.worldObj.getBlockMetadata(var7, var8, var9);
/*  585: 783 */       par3 = var8;
/*  586: 785 */       if (((BlockRailBase)var10).func_150050_e()) {
/*  587: 787 */         var11 &= 0x7;
/*  588:     */       }
/*  589: 790 */       if ((var11 >= 2) && (var11 <= 5)) {
/*  590: 792 */         par3 = var8 + 1;
/*  591:     */       }
/*  592: 795 */       int[][] var12 = matrix[var11];
/*  593: 796 */       double var13 = 0.0D;
/*  594: 797 */       double var15 = var7 + 0.5D + var12[0][0] * 0.5D;
/*  595: 798 */       double var17 = var8 + 0.5D + var12[0][1] * 0.5D;
/*  596: 799 */       double var19 = var9 + 0.5D + var12[0][2] * 0.5D;
/*  597: 800 */       double var21 = var7 + 0.5D + var12[1][0] * 0.5D;
/*  598: 801 */       double var23 = var8 + 0.5D + var12[1][1] * 0.5D;
/*  599: 802 */       double var25 = var9 + 0.5D + var12[1][2] * 0.5D;
/*  600: 803 */       double var27 = var21 - var15;
/*  601: 804 */       double var29 = (var23 - var17) * 2.0D;
/*  602: 805 */       double var31 = var25 - var19;
/*  603: 807 */       if (var27 == 0.0D)
/*  604:     */       {
/*  605: 809 */         par1 = var7 + 0.5D;
/*  606: 810 */         var13 = par5 - var9;
/*  607:     */       }
/*  608: 812 */       else if (var31 == 0.0D)
/*  609:     */       {
/*  610: 814 */         par5 = var9 + 0.5D;
/*  611: 815 */         var13 = par1 - var7;
/*  612:     */       }
/*  613:     */       else
/*  614:     */       {
/*  615: 819 */         double var33 = par1 - var15;
/*  616: 820 */         double var35 = par5 - var19;
/*  617: 821 */         var13 = (var33 * var27 + var35 * var31) * 2.0D;
/*  618:     */       }
/*  619: 824 */       par1 = var15 + var27 * var13;
/*  620: 825 */       par3 = var17 + var29 * var13;
/*  621: 826 */       par5 = var19 + var31 * var13;
/*  622: 828 */       if (var29 < 0.0D) {
/*  623: 830 */         par3 += 1.0D;
/*  624:     */       }
/*  625: 833 */       if (var29 > 0.0D) {
/*  626: 835 */         par3 += 0.5D;
/*  627:     */       }
/*  628: 838 */       return this.worldObj.getWorldVec3Pool().getVecFromPool(par1, par3, par5);
/*  629:     */     }
/*  630: 842 */     return null;
/*  631:     */   }
/*  632:     */   
/*  633:     */   protected void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
/*  634:     */   {
/*  635: 851 */     if (par1NBTTagCompound.getBoolean("CustomDisplayTile"))
/*  636:     */     {
/*  637: 853 */       func_145819_k(par1NBTTagCompound.getInteger("DisplayTile"));
/*  638: 854 */       setDisplayTileData(par1NBTTagCompound.getInteger("DisplayData"));
/*  639: 855 */       setDisplayTileOffset(par1NBTTagCompound.getInteger("DisplayOffset"));
/*  640:     */     }
/*  641: 858 */     if ((par1NBTTagCompound.func_150297_b("CustomName", 8)) && (par1NBTTagCompound.getString("CustomName").length() > 0)) {
/*  642: 860 */       this.entityName = par1NBTTagCompound.getString("CustomName");
/*  643:     */     }
/*  644:     */   }
/*  645:     */   
/*  646:     */   protected void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
/*  647:     */   {
/*  648: 869 */     if (hasDisplayTile())
/*  649:     */     {
/*  650: 871 */       par1NBTTagCompound.setBoolean("CustomDisplayTile", true);
/*  651: 872 */       par1NBTTagCompound.setInteger("DisplayTile", func_145820_n().getMaterial() == Material.air ? 0 : Block.getIdFromBlock(func_145820_n()));
/*  652: 873 */       par1NBTTagCompound.setInteger("DisplayData", getDisplayTileData());
/*  653: 874 */       par1NBTTagCompound.setInteger("DisplayOffset", getDisplayTileOffset());
/*  654:     */     }
/*  655: 877 */     if ((this.entityName != null) && (this.entityName.length() > 0)) {
/*  656: 879 */       par1NBTTagCompound.setString("CustomName", this.entityName);
/*  657:     */     }
/*  658:     */   }
/*  659:     */   
/*  660:     */   public float getShadowSize()
/*  661:     */   {
/*  662: 885 */     return 0.0F;
/*  663:     */   }
/*  664:     */   
/*  665:     */   public void applyEntityCollision(Entity par1Entity)
/*  666:     */   {
/*  667: 893 */     if (!this.worldObj.isClient) {
/*  668: 895 */       if (par1Entity != this.riddenByEntity)
/*  669:     */       {
/*  670: 897 */         if (((par1Entity instanceof EntityLivingBase)) && (!(par1Entity instanceof EntityPlayer)) && (!(par1Entity instanceof EntityIronGolem)) && (getMinecartType() == 0) && (this.motionX * this.motionX + this.motionZ * this.motionZ > 0.01D) && (this.riddenByEntity == null) && (par1Entity.ridingEntity == null)) {
/*  671: 899 */           par1Entity.mountEntity(this);
/*  672:     */         }
/*  673: 902 */         double var2 = par1Entity.posX - this.posX;
/*  674: 903 */         double var4 = par1Entity.posZ - this.posZ;
/*  675: 904 */         double var6 = var2 * var2 + var4 * var4;
/*  676: 906 */         if (var6 >= 9.999999747378752E-005D)
/*  677:     */         {
/*  678: 908 */           var6 = MathHelper.sqrt_double(var6);
/*  679: 909 */           var2 /= var6;
/*  680: 910 */           var4 /= var6;
/*  681: 911 */           double var8 = 1.0D / var6;
/*  682: 913 */           if (var8 > 1.0D) {
/*  683: 915 */             var8 = 1.0D;
/*  684:     */           }
/*  685: 918 */           var2 *= var8;
/*  686: 919 */           var4 *= var8;
/*  687: 920 */           var2 *= 0.1000000014901161D;
/*  688: 921 */           var4 *= 0.1000000014901161D;
/*  689: 922 */           var2 *= (1.0F - this.entityCollisionReduction);
/*  690: 923 */           var4 *= (1.0F - this.entityCollisionReduction);
/*  691: 924 */           var2 *= 0.5D;
/*  692: 925 */           var4 *= 0.5D;
/*  693: 927 */           if ((par1Entity instanceof EntityMinecart))
/*  694:     */           {
/*  695: 929 */             double var10 = par1Entity.posX - this.posX;
/*  696: 930 */             double var12 = par1Entity.posZ - this.posZ;
/*  697: 931 */             Vec3 var14 = this.worldObj.getWorldVec3Pool().getVecFromPool(var10, 0.0D, var12).normalize();
/*  698: 932 */             Vec3 var15 = this.worldObj.getWorldVec3Pool().getVecFromPool(MathHelper.cos(this.rotationYaw * 3.141593F / 180.0F), 0.0D, MathHelper.sin(this.rotationYaw * 3.141593F / 180.0F)).normalize();
/*  699: 933 */             double var16 = Math.abs(var14.dotProduct(var15));
/*  700: 935 */             if (var16 < 0.800000011920929D) {
/*  701: 937 */               return;
/*  702:     */             }
/*  703: 940 */             double var18 = par1Entity.motionX + this.motionX;
/*  704: 941 */             double var20 = par1Entity.motionZ + this.motionZ;
/*  705: 943 */             if ((((EntityMinecart)par1Entity).getMinecartType() == 2) && (getMinecartType() != 2))
/*  706:     */             {
/*  707: 945 */               this.motionX *= 0.2000000029802322D;
/*  708: 946 */               this.motionZ *= 0.2000000029802322D;
/*  709: 947 */               addVelocity(par1Entity.motionX - var2, 0.0D, par1Entity.motionZ - var4);
/*  710: 948 */               par1Entity.motionX *= 0.949999988079071D;
/*  711: 949 */               par1Entity.motionZ *= 0.949999988079071D;
/*  712:     */             }
/*  713: 951 */             else if ((((EntityMinecart)par1Entity).getMinecartType() != 2) && (getMinecartType() == 2))
/*  714:     */             {
/*  715: 953 */               par1Entity.motionX *= 0.2000000029802322D;
/*  716: 954 */               par1Entity.motionZ *= 0.2000000029802322D;
/*  717: 955 */               par1Entity.addVelocity(this.motionX + var2, 0.0D, this.motionZ + var4);
/*  718: 956 */               this.motionX *= 0.949999988079071D;
/*  719: 957 */               this.motionZ *= 0.949999988079071D;
/*  720:     */             }
/*  721:     */             else
/*  722:     */             {
/*  723: 961 */               var18 /= 2.0D;
/*  724: 962 */               var20 /= 2.0D;
/*  725: 963 */               this.motionX *= 0.2000000029802322D;
/*  726: 964 */               this.motionZ *= 0.2000000029802322D;
/*  727: 965 */               addVelocity(var18 - var2, 0.0D, var20 - var4);
/*  728: 966 */               par1Entity.motionX *= 0.2000000029802322D;
/*  729: 967 */               par1Entity.motionZ *= 0.2000000029802322D;
/*  730: 968 */               par1Entity.addVelocity(var18 + var2, 0.0D, var20 + var4);
/*  731:     */             }
/*  732:     */           }
/*  733:     */           else
/*  734:     */           {
/*  735: 973 */             addVelocity(-var2, 0.0D, -var4);
/*  736: 974 */             par1Entity.addVelocity(var2 / 4.0D, 0.0D, var4 / 4.0D);
/*  737:     */           }
/*  738:     */         }
/*  739:     */       }
/*  740:     */     }
/*  741:     */   }
/*  742:     */   
/*  743:     */   public void setPositionAndRotation2(double par1, double par3, double par5, float par7, float par8, int par9)
/*  744:     */   {
/*  745: 987 */     this.minecartX = par1;
/*  746: 988 */     this.minecartY = par3;
/*  747: 989 */     this.minecartZ = par5;
/*  748: 990 */     this.minecartYaw = par7;
/*  749: 991 */     this.minecartPitch = par8;
/*  750: 992 */     this.turnProgress = (par9 + 2);
/*  751: 993 */     this.motionX = this.velocityX;
/*  752: 994 */     this.motionY = this.velocityY;
/*  753: 995 */     this.motionZ = this.velocityZ;
/*  754:     */   }
/*  755:     */   
/*  756:     */   public void setVelocity(double par1, double par3, double par5)
/*  757:     */   {
/*  758:1003 */     this.velocityX = (this.motionX = par1);
/*  759:1004 */     this.velocityY = (this.motionY = par3);
/*  760:1005 */     this.velocityZ = (this.motionZ = par5);
/*  761:     */   }
/*  762:     */   
/*  763:     */   public void setDamage(float par1)
/*  764:     */   {
/*  765:1014 */     this.dataWatcher.updateObject(19, Float.valueOf(par1));
/*  766:     */   }
/*  767:     */   
/*  768:     */   public float getDamage()
/*  769:     */   {
/*  770:1023 */     return this.dataWatcher.getWatchableObjectFloat(19);
/*  771:     */   }
/*  772:     */   
/*  773:     */   public void setRollingAmplitude(int par1)
/*  774:     */   {
/*  775:1031 */     this.dataWatcher.updateObject(17, Integer.valueOf(par1));
/*  776:     */   }
/*  777:     */   
/*  778:     */   public int getRollingAmplitude()
/*  779:     */   {
/*  780:1039 */     return this.dataWatcher.getWatchableObjectInt(17);
/*  781:     */   }
/*  782:     */   
/*  783:     */   public void setRollingDirection(int par1)
/*  784:     */   {
/*  785:1047 */     this.dataWatcher.updateObject(18, Integer.valueOf(par1));
/*  786:     */   }
/*  787:     */   
/*  788:     */   public int getRollingDirection()
/*  789:     */   {
/*  790:1055 */     return this.dataWatcher.getWatchableObjectInt(18);
/*  791:     */   }
/*  792:     */   
/*  793:     */   public abstract int getMinecartType();
/*  794:     */   
/*  795:     */   public Block func_145820_n()
/*  796:     */   {
/*  797:1062 */     if (!hasDisplayTile()) {
/*  798:1064 */       return func_145817_o();
/*  799:     */     }
/*  800:1068 */     int var1 = getDataWatcher().getWatchableObjectInt(20) & 0xFFFF;
/*  801:1069 */     return Block.getBlockById(var1);
/*  802:     */   }
/*  803:     */   
/*  804:     */   public Block func_145817_o()
/*  805:     */   {
/*  806:1075 */     return Blocks.air;
/*  807:     */   }
/*  808:     */   
/*  809:     */   public int getDisplayTileData()
/*  810:     */   {
/*  811:1080 */     return !hasDisplayTile() ? getDefaultDisplayTileData() : getDataWatcher().getWatchableObjectInt(20) >> 16;
/*  812:     */   }
/*  813:     */   
/*  814:     */   public int getDefaultDisplayTileData()
/*  815:     */   {
/*  816:1085 */     return 0;
/*  817:     */   }
/*  818:     */   
/*  819:     */   public int getDisplayTileOffset()
/*  820:     */   {
/*  821:1090 */     return !hasDisplayTile() ? getDefaultDisplayTileOffset() : getDataWatcher().getWatchableObjectInt(21);
/*  822:     */   }
/*  823:     */   
/*  824:     */   public int getDefaultDisplayTileOffset()
/*  825:     */   {
/*  826:1095 */     return 6;
/*  827:     */   }
/*  828:     */   
/*  829:     */   public void func_145819_k(int p_145819_1_)
/*  830:     */   {
/*  831:1100 */     getDataWatcher().updateObject(20, Integer.valueOf(p_145819_1_ & 0xFFFF | getDisplayTileData() << 16));
/*  832:1101 */     setHasDisplayTile(true);
/*  833:     */   }
/*  834:     */   
/*  835:     */   public void setDisplayTileData(int par1)
/*  836:     */   {
/*  837:1106 */     getDataWatcher().updateObject(20, Integer.valueOf(Block.getIdFromBlock(func_145820_n()) & 0xFFFF | par1 << 16));
/*  838:1107 */     setHasDisplayTile(true);
/*  839:     */   }
/*  840:     */   
/*  841:     */   public void setDisplayTileOffset(int par1)
/*  842:     */   {
/*  843:1112 */     getDataWatcher().updateObject(21, Integer.valueOf(par1));
/*  844:1113 */     setHasDisplayTile(true);
/*  845:     */   }
/*  846:     */   
/*  847:     */   public boolean hasDisplayTile()
/*  848:     */   {
/*  849:1118 */     return getDataWatcher().getWatchableObjectByte(22) == 1;
/*  850:     */   }
/*  851:     */   
/*  852:     */   public void setHasDisplayTile(boolean par1)
/*  853:     */   {
/*  854:1123 */     getDataWatcher().updateObject(22, Byte.valueOf((byte)(par1 ? 1 : 0)));
/*  855:     */   }
/*  856:     */   
/*  857:     */   public void setMinecartName(String par1Str)
/*  858:     */   {
/*  859:1131 */     this.entityName = par1Str;
/*  860:     */   }
/*  861:     */   
/*  862:     */   public String getCommandSenderName()
/*  863:     */   {
/*  864:1139 */     return this.entityName != null ? this.entityName : super.getCommandSenderName();
/*  865:     */   }
/*  866:     */   
/*  867:     */   public boolean isInventoryNameLocalized()
/*  868:     */   {
/*  869:1147 */     return this.entityName != null;
/*  870:     */   }
/*  871:     */   
/*  872:     */   public String func_95999_t()
/*  873:     */   {
/*  874:1152 */     return this.entityName;
/*  875:     */   }
/*  876:     */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.entity.item.EntityMinecart
 * JD-Core Version:    0.7.0.1
 */