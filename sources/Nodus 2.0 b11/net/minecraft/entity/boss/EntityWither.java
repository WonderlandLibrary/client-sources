/*   1:    */ package net.minecraft.entity.boss;
/*   2:    */ 
/*   3:    */ import java.util.Iterator;
/*   4:    */ import java.util.List;
/*   5:    */ import java.util.Random;
/*   6:    */ import net.minecraft.block.Block;
/*   7:    */ import net.minecraft.block.material.Material;
/*   8:    */ import net.minecraft.command.IEntitySelector;
/*   9:    */ import net.minecraft.entity.DataWatcher;
/*  10:    */ import net.minecraft.entity.Entity;
/*  11:    */ import net.minecraft.entity.EntityLiving;
/*  12:    */ import net.minecraft.entity.EntityLivingBase;
/*  13:    */ import net.minecraft.entity.EnumCreatureAttribute;
/*  14:    */ import net.minecraft.entity.IRangedAttackMob;
/*  15:    */ import net.minecraft.entity.SharedMonsterAttributes;
/*  16:    */ import net.minecraft.entity.ai.EntityAIArrowAttack;
/*  17:    */ import net.minecraft.entity.ai.EntityAIHurtByTarget;
/*  18:    */ import net.minecraft.entity.ai.EntityAILookIdle;
/*  19:    */ import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
/*  20:    */ import net.minecraft.entity.ai.EntityAISwimming;
/*  21:    */ import net.minecraft.entity.ai.EntityAITasks;
/*  22:    */ import net.minecraft.entity.ai.EntityAIWander;
/*  23:    */ import net.minecraft.entity.ai.EntityAIWatchClosest;
/*  24:    */ import net.minecraft.entity.ai.attributes.IAttributeInstance;
/*  25:    */ import net.minecraft.entity.monster.EntityMob;
/*  26:    */ import net.minecraft.entity.player.EntityPlayer;
/*  27:    */ import net.minecraft.entity.player.PlayerCapabilities;
/*  28:    */ import net.minecraft.entity.projectile.EntityArrow;
/*  29:    */ import net.minecraft.entity.projectile.EntityWitherSkull;
/*  30:    */ import net.minecraft.init.Blocks;
/*  31:    */ import net.minecraft.init.Items;
/*  32:    */ import net.minecraft.nbt.NBTTagCompound;
/*  33:    */ import net.minecraft.pathfinding.PathNavigate;
/*  34:    */ import net.minecraft.potion.PotionEffect;
/*  35:    */ import net.minecraft.stats.AchievementList;
/*  36:    */ import net.minecraft.util.AxisAlignedBB;
/*  37:    */ import net.minecraft.util.DamageSource;
/*  38:    */ import net.minecraft.util.MathHelper;
/*  39:    */ import net.minecraft.world.EnumDifficulty;
/*  40:    */ import net.minecraft.world.GameRules;
/*  41:    */ import net.minecraft.world.World;
/*  42:    */ 
/*  43:    */ public class EntityWither
/*  44:    */   extends EntityMob
/*  45:    */   implements IBossDisplayData, IRangedAttackMob
/*  46:    */ {
/*  47: 37 */   private float[] field_82220_d = new float[2];
/*  48: 38 */   private float[] field_82221_e = new float[2];
/*  49: 39 */   private float[] field_82217_f = new float[2];
/*  50: 40 */   private float[] field_82218_g = new float[2];
/*  51: 41 */   private int[] field_82223_h = new int[2];
/*  52: 42 */   private int[] field_82224_i = new int[2];
/*  53:    */   private int field_82222_j;
/*  54: 46 */   private static final IEntitySelector attackEntitySelector = new IEntitySelector()
/*  55:    */   {
/*  56:    */     private static final String __OBFID = "CL_00001662";
/*  57:    */     
/*  58:    */     public boolean isEntityApplicable(Entity par1Entity)
/*  59:    */     {
/*  60: 51 */       return ((par1Entity instanceof EntityLivingBase)) && (((EntityLivingBase)par1Entity).getCreatureAttribute() != EnumCreatureAttribute.UNDEAD);
/*  61:    */     }
/*  62:    */   };
/*  63:    */   private static final String __OBFID = "CL_00001661";
/*  64:    */   
/*  65:    */   public EntityWither(World par1World)
/*  66:    */   {
/*  67: 58 */     super(par1World);
/*  68: 59 */     setHealth(getMaxHealth());
/*  69: 60 */     setSize(0.9F, 4.0F);
/*  70: 61 */     this.isImmuneToFire = true;
/*  71: 62 */     getNavigator().setCanSwim(true);
/*  72: 63 */     this.tasks.addTask(0, new EntityAISwimming(this));
/*  73: 64 */     this.tasks.addTask(2, new EntityAIArrowAttack(this, 1.0D, 40, 20.0F));
/*  74: 65 */     this.tasks.addTask(5, new EntityAIWander(this, 1.0D));
/*  75: 66 */     this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
/*  76: 67 */     this.tasks.addTask(7, new EntityAILookIdle(this));
/*  77: 68 */     this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
/*  78: 69 */     this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityLiving.class, 0, false, false, attackEntitySelector));
/*  79: 70 */     this.experienceValue = 50;
/*  80:    */   }
/*  81:    */   
/*  82:    */   protected void entityInit()
/*  83:    */   {
/*  84: 75 */     super.entityInit();
/*  85: 76 */     this.dataWatcher.addObject(17, new Integer(0));
/*  86: 77 */     this.dataWatcher.addObject(18, new Integer(0));
/*  87: 78 */     this.dataWatcher.addObject(19, new Integer(0));
/*  88: 79 */     this.dataWatcher.addObject(20, new Integer(0));
/*  89:    */   }
/*  90:    */   
/*  91:    */   public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
/*  92:    */   {
/*  93: 87 */     super.writeEntityToNBT(par1NBTTagCompound);
/*  94: 88 */     par1NBTTagCompound.setInteger("Invul", func_82212_n());
/*  95:    */   }
/*  96:    */   
/*  97:    */   public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
/*  98:    */   {
/*  99: 96 */     super.readEntityFromNBT(par1NBTTagCompound);
/* 100: 97 */     func_82215_s(par1NBTTagCompound.getInteger("Invul"));
/* 101:    */   }
/* 102:    */   
/* 103:    */   public float getShadowSize()
/* 104:    */   {
/* 105:102 */     return this.height / 8.0F;
/* 106:    */   }
/* 107:    */   
/* 108:    */   protected String getLivingSound()
/* 109:    */   {
/* 110:110 */     return "mob.wither.idle";
/* 111:    */   }
/* 112:    */   
/* 113:    */   protected String getHurtSound()
/* 114:    */   {
/* 115:118 */     return "mob.wither.hurt";
/* 116:    */   }
/* 117:    */   
/* 118:    */   protected String getDeathSound()
/* 119:    */   {
/* 120:126 */     return "mob.wither.death";
/* 121:    */   }
/* 122:    */   
/* 123:    */   public void onLivingUpdate()
/* 124:    */   {
/* 125:135 */     this.motionY *= 0.6000000238418579D;
/* 126:140 */     if ((!this.worldObj.isClient) && (getWatchedTargetId(0) > 0))
/* 127:    */     {
/* 128:142 */       Entity var1 = this.worldObj.getEntityByID(getWatchedTargetId(0));
/* 129:144 */       if (var1 != null)
/* 130:    */       {
/* 131:146 */         if ((this.posY < var1.posY) || ((!isArmored()) && (this.posY < var1.posY + 5.0D)))
/* 132:    */         {
/* 133:148 */           if (this.motionY < 0.0D) {
/* 134:150 */             this.motionY = 0.0D;
/* 135:    */           }
/* 136:153 */           this.motionY += (0.5D - this.motionY) * 0.6000000238418579D;
/* 137:    */         }
/* 138:156 */         double var2 = var1.posX - this.posX;
/* 139:157 */         double var4 = var1.posZ - this.posZ;
/* 140:158 */         double var6 = var2 * var2 + var4 * var4;
/* 141:160 */         if (var6 > 9.0D)
/* 142:    */         {
/* 143:162 */           double var8 = MathHelper.sqrt_double(var6);
/* 144:163 */           this.motionX += (var2 / var8 * 0.5D - this.motionX) * 0.6000000238418579D;
/* 145:164 */           this.motionZ += (var4 / var8 * 0.5D - this.motionZ) * 0.6000000238418579D;
/* 146:    */         }
/* 147:    */       }
/* 148:    */     }
/* 149:169 */     if (this.motionX * this.motionX + this.motionZ * this.motionZ > 0.0500000007450581D) {
/* 150:171 */       this.rotationYaw = ((float)Math.atan2(this.motionZ, this.motionX) * 57.295776F - 90.0F);
/* 151:    */     }
/* 152:174 */     super.onLivingUpdate();
/* 153:177 */     for (int var20 = 0; var20 < 2; var20++)
/* 154:    */     {
/* 155:179 */       this.field_82218_g[var20] = this.field_82221_e[var20];
/* 156:180 */       this.field_82217_f[var20] = this.field_82220_d[var20];
/* 157:    */     }
/* 158:185 */     for (var20 = 0; var20 < 2; var20++)
/* 159:    */     {
/* 160:187 */       int var21 = getWatchedTargetId(var20 + 1);
/* 161:188 */       Entity var3 = null;
/* 162:190 */       if (var21 > 0) {
/* 163:192 */         var3 = this.worldObj.getEntityByID(var21);
/* 164:    */       }
/* 165:195 */       if (var3 != null)
/* 166:    */       {
/* 167:197 */         double var4 = func_82214_u(var20 + 1);
/* 168:198 */         double var6 = func_82208_v(var20 + 1);
/* 169:199 */         double var8 = func_82213_w(var20 + 1);
/* 170:200 */         double var10 = var3.posX - var4;
/* 171:201 */         double var12 = var3.posY + var3.getEyeHeight() - var6;
/* 172:202 */         double var14 = var3.posZ - var8;
/* 173:203 */         double var16 = MathHelper.sqrt_double(var10 * var10 + var14 * var14);
/* 174:204 */         float var18 = (float)(Math.atan2(var14, var10) * 180.0D / 3.141592653589793D) - 90.0F;
/* 175:205 */         float var19 = (float)-(Math.atan2(var12, var16) * 180.0D / 3.141592653589793D);
/* 176:206 */         this.field_82220_d[var20] = func_82204_b(this.field_82220_d[var20], var19, 40.0F);
/* 177:207 */         this.field_82221_e[var20] = func_82204_b(this.field_82221_e[var20], var18, 10.0F);
/* 178:    */       }
/* 179:    */       else
/* 180:    */       {
/* 181:211 */         this.field_82221_e[var20] = func_82204_b(this.field_82221_e[var20], this.renderYawOffset, 10.0F);
/* 182:    */       }
/* 183:    */     }
/* 184:215 */     boolean var22 = isArmored();
/* 185:217 */     for (int var21 = 0; var21 < 3; var21++)
/* 186:    */     {
/* 187:219 */       double var23 = func_82214_u(var21);
/* 188:220 */       double var5 = func_82208_v(var21);
/* 189:221 */       double var7 = func_82213_w(var21);
/* 190:222 */       this.worldObj.spawnParticle("smoke", var23 + this.rand.nextGaussian() * 0.300000011920929D, var5 + this.rand.nextGaussian() * 0.300000011920929D, var7 + this.rand.nextGaussian() * 0.300000011920929D, 0.0D, 0.0D, 0.0D);
/* 191:224 */       if ((var22) && (this.worldObj.rand.nextInt(4) == 0)) {
/* 192:226 */         this.worldObj.spawnParticle("mobSpell", var23 + this.rand.nextGaussian() * 0.300000011920929D, var5 + this.rand.nextGaussian() * 0.300000011920929D, var7 + this.rand.nextGaussian() * 0.300000011920929D, 0.699999988079071D, 0.699999988079071D, 0.5D);
/* 193:    */       }
/* 194:    */     }
/* 195:230 */     if (func_82212_n() > 0) {
/* 196:232 */       for (var21 = 0; var21 < 3; var21++) {
/* 197:234 */         this.worldObj.spawnParticle("mobSpell", this.posX + this.rand.nextGaussian() * 1.0D, this.posY + this.rand.nextFloat() * 3.3F, this.posZ + this.rand.nextGaussian() * 1.0D, 0.699999988079071D, 0.699999988079071D, 0.8999999761581421D);
/* 198:    */       }
/* 199:    */     }
/* 200:    */   }
/* 201:    */   
/* 202:    */   protected void updateAITasks()
/* 203:    */   {
/* 204:243 */     if (func_82212_n() > 0)
/* 205:    */     {
/* 206:245 */       int var1 = func_82212_n() - 1;
/* 207:247 */       if (var1 <= 0)
/* 208:    */       {
/* 209:249 */         this.worldObj.newExplosion(this, this.posX, this.posY + getEyeHeight(), this.posZ, 7.0F, false, this.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing"));
/* 210:250 */         this.worldObj.playBroadcastSound(1013, (int)this.posX, (int)this.posY, (int)this.posZ, 0);
/* 211:    */       }
/* 212:253 */       func_82215_s(var1);
/* 213:255 */       if (this.ticksExisted % 10 == 0) {
/* 214:257 */         heal(10.0F);
/* 215:    */       }
/* 216:    */     }
/* 217:    */     else
/* 218:    */     {
/* 219:262 */       super.updateAITasks();
/* 220:265 */       for (int var1 = 1; var1 < 3; var1++) {
/* 221:267 */         if (this.ticksExisted >= this.field_82223_h[(var1 - 1)])
/* 222:    */         {
/* 223:269 */           this.field_82223_h[(var1 - 1)] = (this.ticksExisted + 10 + this.rand.nextInt(10));
/* 224:271 */           if ((this.worldObj.difficultySetting == EnumDifficulty.NORMAL) || (this.worldObj.difficultySetting == EnumDifficulty.HARD))
/* 225:    */           {
/* 226:273 */             int var10001 = var1 - 1;
/* 227:274 */             int var10003 = this.field_82224_i[(var1 - 1)];
/* 228:275 */             this.field_82224_i[var10001] = (this.field_82224_i[(var1 - 1)] + 1);
/* 229:277 */             if (var10003 > 15)
/* 230:    */             {
/* 231:279 */               float var2 = 10.0F;
/* 232:280 */               float var3 = 5.0F;
/* 233:281 */               double var4 = MathHelper.getRandomDoubleInRange(this.rand, this.posX - var2, this.posX + var2);
/* 234:282 */               double var6 = MathHelper.getRandomDoubleInRange(this.rand, this.posY - var3, this.posY + var3);
/* 235:283 */               double var8 = MathHelper.getRandomDoubleInRange(this.rand, this.posZ - var2, this.posZ + var2);
/* 236:284 */               func_82209_a(var1 + 1, var4, var6, var8, true);
/* 237:285 */               this.field_82224_i[(var1 - 1)] = 0;
/* 238:    */             }
/* 239:    */           }
/* 240:289 */           int var12 = getWatchedTargetId(var1);
/* 241:291 */           if (var12 > 0)
/* 242:    */           {
/* 243:293 */             Entity var14 = this.worldObj.getEntityByID(var12);
/* 244:295 */             if ((var14 != null) && (var14.isEntityAlive()) && (getDistanceSqToEntity(var14) <= 900.0D) && (canEntityBeSeen(var14)))
/* 245:    */             {
/* 246:297 */               func_82216_a(var1 + 1, (EntityLivingBase)var14);
/* 247:298 */               this.field_82223_h[(var1 - 1)] = (this.ticksExisted + 40 + this.rand.nextInt(20));
/* 248:299 */               this.field_82224_i[(var1 - 1)] = 0;
/* 249:    */             }
/* 250:    */             else
/* 251:    */             {
/* 252:303 */               func_82211_c(var1, 0);
/* 253:    */             }
/* 254:    */           }
/* 255:    */           else
/* 256:    */           {
/* 257:308 */             List var13 = this.worldObj.selectEntitiesWithinAABB(EntityLivingBase.class, this.boundingBox.expand(20.0D, 8.0D, 20.0D), attackEntitySelector);
/* 258:310 */             for (int var16 = 0; (var16 < 10) && (!var13.isEmpty()); var16++)
/* 259:    */             {
/* 260:312 */               EntityLivingBase var5 = (EntityLivingBase)var13.get(this.rand.nextInt(var13.size()));
/* 261:314 */               if ((var5 != this) && (var5.isEntityAlive()) && (canEntityBeSeen(var5)))
/* 262:    */               {
/* 263:316 */                 if ((var5 instanceof EntityPlayer))
/* 264:    */                 {
/* 265:318 */                   if (((EntityPlayer)var5).capabilities.disableDamage) {
/* 266:    */                     break;
/* 267:    */                   }
/* 268:320 */                   func_82211_c(var1, var5.getEntityId());
/* 269:    */                   
/* 270:322 */                   break;
/* 271:    */                 }
/* 272:325 */                 func_82211_c(var1, var5.getEntityId());
/* 273:    */                 
/* 274:    */ 
/* 275:328 */                 break;
/* 276:    */               }
/* 277:331 */               var13.remove(var5);
/* 278:    */             }
/* 279:    */           }
/* 280:    */         }
/* 281:    */       }
/* 282:337 */       if (getAttackTarget() != null) {
/* 283:339 */         func_82211_c(0, getAttackTarget().getEntityId());
/* 284:    */       } else {
/* 285:343 */         func_82211_c(0, 0);
/* 286:    */       }
/* 287:346 */       if (this.field_82222_j > 0)
/* 288:    */       {
/* 289:348 */         this.field_82222_j -= 1;
/* 290:350 */         if ((this.field_82222_j == 0) && (this.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing")))
/* 291:    */         {
/* 292:352 */           var1 = MathHelper.floor_double(this.posY);
/* 293:353 */           int var12 = MathHelper.floor_double(this.posX);
/* 294:354 */           int var15 = MathHelper.floor_double(this.posZ);
/* 295:355 */           boolean var18 = false;
/* 296:357 */           for (int var17 = -1; var17 <= 1; var17++) {
/* 297:359 */             for (int var19 = -1; var19 <= 1; var19++) {
/* 298:361 */               for (int var7 = 0; var7 <= 3; var7++)
/* 299:    */               {
/* 300:363 */                 int var20 = var12 + var17;
/* 301:364 */                 int var9 = var1 + var7;
/* 302:365 */                 int var10 = var15 + var19;
/* 303:366 */                 Block var11 = this.worldObj.getBlock(var20, var9, var10);
/* 304:368 */                 if ((var11.getMaterial() != Material.air) && (var11 != Blocks.bedrock) && (var11 != Blocks.end_portal) && (var11 != Blocks.end_portal_frame) && (var11 != Blocks.command_block)) {
/* 305:370 */                   var18 = (this.worldObj.func_147480_a(var20, var9, var10, true)) || (var18);
/* 306:    */                 }
/* 307:    */               }
/* 308:    */             }
/* 309:    */           }
/* 310:376 */           if (var18) {
/* 311:378 */             this.worldObj.playAuxSFXAtEntity(null, 1012, (int)this.posX, (int)this.posY, (int)this.posZ, 0);
/* 312:    */           }
/* 313:    */         }
/* 314:    */       }
/* 315:383 */       if (this.ticksExisted % 20 == 0) {
/* 316:385 */         heal(1.0F);
/* 317:    */       }
/* 318:    */     }
/* 319:    */   }
/* 320:    */   
/* 321:    */   public void func_82206_m()
/* 322:    */   {
/* 323:392 */     func_82215_s(220);
/* 324:393 */     setHealth(getMaxHealth() / 3.0F);
/* 325:    */   }
/* 326:    */   
/* 327:    */   public void setInWeb() {}
/* 328:    */   
/* 329:    */   public int getTotalArmorValue()
/* 330:    */   {
/* 331:406 */     return 4;
/* 332:    */   }
/* 333:    */   
/* 334:    */   private double func_82214_u(int par1)
/* 335:    */   {
/* 336:411 */     if (par1 <= 0) {
/* 337:413 */       return this.posX;
/* 338:    */     }
/* 339:417 */     float var2 = (this.renderYawOffset + 180 * (par1 - 1)) / 180.0F * 3.141593F;
/* 340:418 */     float var3 = MathHelper.cos(var2);
/* 341:419 */     return this.posX + var3 * 1.3D;
/* 342:    */   }
/* 343:    */   
/* 344:    */   private double func_82208_v(int par1)
/* 345:    */   {
/* 346:425 */     return par1 <= 0 ? this.posY + 3.0D : this.posY + 2.2D;
/* 347:    */   }
/* 348:    */   
/* 349:    */   private double func_82213_w(int par1)
/* 350:    */   {
/* 351:430 */     if (par1 <= 0) {
/* 352:432 */       return this.posZ;
/* 353:    */     }
/* 354:436 */     float var2 = (this.renderYawOffset + 180 * (par1 - 1)) / 180.0F * 3.141593F;
/* 355:437 */     float var3 = MathHelper.sin(var2);
/* 356:438 */     return this.posZ + var3 * 1.3D;
/* 357:    */   }
/* 358:    */   
/* 359:    */   private float func_82204_b(float par1, float par2, float par3)
/* 360:    */   {
/* 361:444 */     float var4 = MathHelper.wrapAngleTo180_float(par2 - par1);
/* 362:446 */     if (var4 > par3) {
/* 363:448 */       var4 = par3;
/* 364:    */     }
/* 365:451 */     if (var4 < -par3) {
/* 366:453 */       var4 = -par3;
/* 367:    */     }
/* 368:456 */     return par1 + var4;
/* 369:    */   }
/* 370:    */   
/* 371:    */   private void func_82216_a(int par1, EntityLivingBase par2EntityLivingBase)
/* 372:    */   {
/* 373:461 */     func_82209_a(par1, par2EntityLivingBase.posX, par2EntityLivingBase.posY + par2EntityLivingBase.getEyeHeight() * 0.5D, par2EntityLivingBase.posZ, (par1 == 0) && (this.rand.nextFloat() < 0.001F));
/* 374:    */   }
/* 375:    */   
/* 376:    */   private void func_82209_a(int par1, double par2, double par4, double par6, boolean par8)
/* 377:    */   {
/* 378:466 */     this.worldObj.playAuxSFXAtEntity(null, 1014, (int)this.posX, (int)this.posY, (int)this.posZ, 0);
/* 379:467 */     double var9 = func_82214_u(par1);
/* 380:468 */     double var11 = func_82208_v(par1);
/* 381:469 */     double var13 = func_82213_w(par1);
/* 382:470 */     double var15 = par2 - var9;
/* 383:471 */     double var17 = par4 - var11;
/* 384:472 */     double var19 = par6 - var13;
/* 385:473 */     EntityWitherSkull var21 = new EntityWitherSkull(this.worldObj, this, var15, var17, var19);
/* 386:475 */     if (par8) {
/* 387:477 */       var21.setInvulnerable(true);
/* 388:    */     }
/* 389:480 */     var21.posY = var11;
/* 390:481 */     var21.posX = var9;
/* 391:482 */     var21.posZ = var13;
/* 392:483 */     this.worldObj.spawnEntityInWorld(var21);
/* 393:    */   }
/* 394:    */   
/* 395:    */   public void attackEntityWithRangedAttack(EntityLivingBase par1EntityLivingBase, float par2)
/* 396:    */   {
/* 397:491 */     func_82216_a(0, par1EntityLivingBase);
/* 398:    */   }
/* 399:    */   
/* 400:    */   public boolean attackEntityFrom(DamageSource par1DamageSource, float par2)
/* 401:    */   {
/* 402:499 */     if (isEntityInvulnerable()) {
/* 403:501 */       return false;
/* 404:    */     }
/* 405:503 */     if (par1DamageSource == DamageSource.drown) {
/* 406:505 */       return false;
/* 407:    */     }
/* 408:507 */     if (func_82212_n() > 0) {
/* 409:509 */       return false;
/* 410:    */     }
/* 411:515 */     if (isArmored())
/* 412:    */     {
/* 413:517 */       Entity var3 = par1DamageSource.getSourceOfDamage();
/* 414:519 */       if ((var3 instanceof EntityArrow)) {
/* 415:521 */         return false;
/* 416:    */       }
/* 417:    */     }
/* 418:525 */     Entity var3 = par1DamageSource.getEntity();
/* 419:527 */     if ((var3 != null) && (!(var3 instanceof EntityPlayer)) && ((var3 instanceof EntityLivingBase)) && (((EntityLivingBase)var3).getCreatureAttribute() == getCreatureAttribute())) {
/* 420:529 */       return false;
/* 421:    */     }
/* 422:533 */     if (this.field_82222_j <= 0) {
/* 423:535 */       this.field_82222_j = 20;
/* 424:    */     }
/* 425:538 */     for (int var4 = 0; var4 < this.field_82224_i.length; var4++) {
/* 426:540 */       this.field_82224_i[var4] += 3;
/* 427:    */     }
/* 428:543 */     return super.attackEntityFrom(par1DamageSource, par2);
/* 429:    */   }
/* 430:    */   
/* 431:    */   protected void dropFewItems(boolean par1, int par2)
/* 432:    */   {
/* 433:553 */     func_145779_a(Items.nether_star, 1);
/* 434:555 */     if (!this.worldObj.isClient)
/* 435:    */     {
/* 436:557 */       Iterator var3 = this.worldObj.getEntitiesWithinAABB(EntityPlayer.class, this.boundingBox.expand(50.0D, 100.0D, 50.0D)).iterator();
/* 437:559 */       while (var3.hasNext())
/* 438:    */       {
/* 439:561 */         EntityPlayer var4 = (EntityPlayer)var3.next();
/* 440:562 */         var4.triggerAchievement(AchievementList.field_150964_J);
/* 441:    */       }
/* 442:    */     }
/* 443:    */   }
/* 444:    */   
/* 445:    */   public void despawnEntity()
/* 446:    */   {
/* 447:572 */     this.entityAge = 0;
/* 448:    */   }
/* 449:    */   
/* 450:    */   public int getBrightnessForRender(float par1)
/* 451:    */   {
/* 452:577 */     return 15728880;
/* 453:    */   }
/* 454:    */   
/* 455:    */   protected void fall(float par1) {}
/* 456:    */   
/* 457:    */   public void addPotionEffect(PotionEffect par1PotionEffect) {}
/* 458:    */   
/* 459:    */   protected boolean isAIEnabled()
/* 460:    */   {
/* 461:595 */     return true;
/* 462:    */   }
/* 463:    */   
/* 464:    */   protected void applyEntityAttributes()
/* 465:    */   {
/* 466:600 */     super.applyEntityAttributes();
/* 467:601 */     getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(300.0D);
/* 468:602 */     getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.6000000238418579D);
/* 469:603 */     getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(40.0D);
/* 470:    */   }
/* 471:    */   
/* 472:    */   public float func_82207_a(int par1)
/* 473:    */   {
/* 474:608 */     return this.field_82221_e[par1];
/* 475:    */   }
/* 476:    */   
/* 477:    */   public float func_82210_r(int par1)
/* 478:    */   {
/* 479:613 */     return this.field_82220_d[par1];
/* 480:    */   }
/* 481:    */   
/* 482:    */   public int func_82212_n()
/* 483:    */   {
/* 484:618 */     return this.dataWatcher.getWatchableObjectInt(20);
/* 485:    */   }
/* 486:    */   
/* 487:    */   public void func_82215_s(int par1)
/* 488:    */   {
/* 489:623 */     this.dataWatcher.updateObject(20, Integer.valueOf(par1));
/* 490:    */   }
/* 491:    */   
/* 492:    */   public int getWatchedTargetId(int par1)
/* 493:    */   {
/* 494:631 */     return this.dataWatcher.getWatchableObjectInt(17 + par1);
/* 495:    */   }
/* 496:    */   
/* 497:    */   public void func_82211_c(int par1, int par2)
/* 498:    */   {
/* 499:636 */     this.dataWatcher.updateObject(17 + par1, Integer.valueOf(par2));
/* 500:    */   }
/* 501:    */   
/* 502:    */   public boolean isArmored()
/* 503:    */   {
/* 504:645 */     return getHealth() <= getMaxHealth() / 2.0F;
/* 505:    */   }
/* 506:    */   
/* 507:    */   public EnumCreatureAttribute getCreatureAttribute()
/* 508:    */   {
/* 509:653 */     return EnumCreatureAttribute.UNDEAD;
/* 510:    */   }
/* 511:    */   
/* 512:    */   public void mountEntity(Entity par1Entity)
/* 513:    */   {
/* 514:661 */     this.ridingEntity = null;
/* 515:    */   }
/* 516:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.entity.boss.EntityWither
 * JD-Core Version:    0.7.0.1
 */