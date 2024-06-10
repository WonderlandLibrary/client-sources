/*   1:    */ package net.minecraft.entity.projectile;
/*   2:    */ 
/*   3:    */ import java.util.List;
/*   4:    */ import java.util.Random;
/*   5:    */ import net.minecraft.block.Block;
/*   6:    */ import net.minecraft.block.material.Material;
/*   7:    */ import net.minecraft.enchantment.EnchantmentHelper;
/*   8:    */ import net.minecraft.entity.DataWatcher;
/*   9:    */ import net.minecraft.entity.Entity;
/*  10:    */ import net.minecraft.entity.EntityLivingBase;
/*  11:    */ import net.minecraft.entity.IProjectile;
/*  12:    */ import net.minecraft.entity.monster.EntityEnderman;
/*  13:    */ import net.minecraft.entity.player.EntityPlayer;
/*  14:    */ import net.minecraft.entity.player.EntityPlayerMP;
/*  15:    */ import net.minecraft.entity.player.InventoryPlayer;
/*  16:    */ import net.minecraft.entity.player.PlayerCapabilities;
/*  17:    */ import net.minecraft.init.Items;
/*  18:    */ import net.minecraft.item.ItemStack;
/*  19:    */ import net.minecraft.nbt.NBTTagCompound;
/*  20:    */ import net.minecraft.network.NetHandlerPlayServer;
/*  21:    */ import net.minecraft.network.play.server.S2BPacketChangeGameState;
/*  22:    */ import net.minecraft.util.AxisAlignedBB;
/*  23:    */ import net.minecraft.util.DamageSource;
/*  24:    */ import net.minecraft.util.MathHelper;
/*  25:    */ import net.minecraft.util.MovingObjectPosition;
/*  26:    */ import net.minecraft.util.Vec3;
/*  27:    */ import net.minecraft.util.Vec3Pool;
/*  28:    */ import net.minecraft.world.World;
/*  29:    */ 
/*  30:    */ public class EntityArrow
/*  31:    */   extends Entity
/*  32:    */   implements IProjectile
/*  33:    */ {
/*  34: 26 */   private int field_145791_d = -1;
/*  35: 27 */   private int field_145792_e = -1;
/*  36: 28 */   private int field_145789_f = -1;
/*  37:    */   private Block field_145790_g;
/*  38:    */   private int inData;
/*  39:    */   private boolean inGround;
/*  40:    */   public int canBePickedUp;
/*  41:    */   public int arrowShake;
/*  42:    */   public Entity shootingEntity;
/*  43:    */   private int ticksInGround;
/*  44:    */   private int ticksInAir;
/*  45: 43 */   private double damage = 2.0D;
/*  46:    */   private int knockbackStrength;
/*  47:    */   private static final String __OBFID = "CL_00001715";
/*  48:    */   
/*  49:    */   public EntityArrow(World par1World)
/*  50:    */   {
/*  51: 51 */     super(par1World);
/*  52: 52 */     this.renderDistanceWeight = 10.0D;
/*  53: 53 */     setSize(0.5F, 0.5F);
/*  54:    */   }
/*  55:    */   
/*  56:    */   public EntityArrow(World par1World, double par2, double par4, double par6)
/*  57:    */   {
/*  58: 58 */     super(par1World);
/*  59: 59 */     this.renderDistanceWeight = 10.0D;
/*  60: 60 */     setSize(0.5F, 0.5F);
/*  61: 61 */     setPosition(par2, par4, par6);
/*  62: 62 */     this.yOffset = 0.0F;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public EntityArrow(World par1World, EntityLivingBase par2EntityLivingBase, EntityLivingBase par3EntityLivingBase, float par4, float par5)
/*  66:    */   {
/*  67: 67 */     super(par1World);
/*  68: 68 */     this.renderDistanceWeight = 10.0D;
/*  69: 69 */     this.shootingEntity = par2EntityLivingBase;
/*  70: 71 */     if ((par2EntityLivingBase instanceof EntityPlayer)) {
/*  71: 73 */       this.canBePickedUp = 1;
/*  72:    */     }
/*  73: 76 */     this.posY = (par2EntityLivingBase.posY + par2EntityLivingBase.getEyeHeight() - 0.1000000014901161D);
/*  74: 77 */     double var6 = par3EntityLivingBase.posX - par2EntityLivingBase.posX;
/*  75: 78 */     double var8 = par3EntityLivingBase.boundingBox.minY + par3EntityLivingBase.height / 3.0F - this.posY;
/*  76: 79 */     double var10 = par3EntityLivingBase.posZ - par2EntityLivingBase.posZ;
/*  77: 80 */     double var12 = MathHelper.sqrt_double(var6 * var6 + var10 * var10);
/*  78: 82 */     if (var12 >= 1.0E-007D)
/*  79:    */     {
/*  80: 84 */       float var14 = (float)(Math.atan2(var10, var6) * 180.0D / 3.141592653589793D) - 90.0F;
/*  81: 85 */       float var15 = (float)-(Math.atan2(var8, var12) * 180.0D / 3.141592653589793D);
/*  82: 86 */       double var16 = var6 / var12;
/*  83: 87 */       double var18 = var10 / var12;
/*  84: 88 */       setLocationAndAngles(par2EntityLivingBase.posX + var16, this.posY, par2EntityLivingBase.posZ + var18, var14, var15);
/*  85: 89 */       this.yOffset = 0.0F;
/*  86: 90 */       float var20 = (float)var12 * 0.2F;
/*  87: 91 */       setThrowableHeading(var6, var8 + var20, var10, par4, par5);
/*  88:    */     }
/*  89:    */   }
/*  90:    */   
/*  91:    */   public EntityArrow(World par1World, EntityLivingBase par2EntityLivingBase, float par3)
/*  92:    */   {
/*  93: 97 */     super(par1World);
/*  94: 98 */     this.renderDistanceWeight = 10.0D;
/*  95: 99 */     this.shootingEntity = par2EntityLivingBase;
/*  96:101 */     if ((par2EntityLivingBase instanceof EntityPlayer)) {
/*  97:103 */       this.canBePickedUp = 1;
/*  98:    */     }
/*  99:106 */     setSize(0.5F, 0.5F);
/* 100:107 */     setLocationAndAngles(par2EntityLivingBase.posX, par2EntityLivingBase.posY + par2EntityLivingBase.getEyeHeight(), par2EntityLivingBase.posZ, par2EntityLivingBase.rotationYaw, par2EntityLivingBase.rotationPitch);
/* 101:108 */     this.posX -= MathHelper.cos(this.rotationYaw / 180.0F * 3.141593F) * 0.16F;
/* 102:109 */     this.posY -= 0.1000000014901161D;
/* 103:110 */     this.posZ -= MathHelper.sin(this.rotationYaw / 180.0F * 3.141593F) * 0.16F;
/* 104:111 */     setPosition(this.posX, this.posY, this.posZ);
/* 105:112 */     this.yOffset = 0.0F;
/* 106:113 */     this.motionX = (-MathHelper.sin(this.rotationYaw / 180.0F * 3.141593F) * MathHelper.cos(this.rotationPitch / 180.0F * 3.141593F));
/* 107:114 */     this.motionZ = (MathHelper.cos(this.rotationYaw / 180.0F * 3.141593F) * MathHelper.cos(this.rotationPitch / 180.0F * 3.141593F));
/* 108:115 */     this.motionY = (-MathHelper.sin(this.rotationPitch / 180.0F * 3.141593F));
/* 109:116 */     setThrowableHeading(this.motionX, this.motionY, this.motionZ, par3 * 1.5F, 1.0F);
/* 110:    */   }
/* 111:    */   
/* 112:    */   protected void entityInit()
/* 113:    */   {
/* 114:121 */     this.dataWatcher.addObject(16, Byte.valueOf((byte)0));
/* 115:    */   }
/* 116:    */   
/* 117:    */   public void setThrowableHeading(double par1, double par3, double par5, float par7, float par8)
/* 118:    */   {
/* 119:129 */     float var9 = MathHelper.sqrt_double(par1 * par1 + par3 * par3 + par5 * par5);
/* 120:130 */     par1 /= var9;
/* 121:131 */     par3 /= var9;
/* 122:132 */     par5 /= var9;
/* 123:133 */     par1 += this.rand.nextGaussian() * (this.rand.nextBoolean() ? -1 : 1) * 0.007499999832361937D * par8;
/* 124:134 */     par3 += this.rand.nextGaussian() * (this.rand.nextBoolean() ? -1 : 1) * 0.007499999832361937D * par8;
/* 125:135 */     par5 += this.rand.nextGaussian() * (this.rand.nextBoolean() ? -1 : 1) * 0.007499999832361937D * par8;
/* 126:136 */     par1 *= par7;
/* 127:137 */     par3 *= par7;
/* 128:138 */     par5 *= par7;
/* 129:139 */     this.motionX = par1;
/* 130:140 */     this.motionY = par3;
/* 131:141 */     this.motionZ = par5;
/* 132:142 */     float var10 = MathHelper.sqrt_double(par1 * par1 + par5 * par5);
/* 133:143 */     this.prevRotationYaw = (this.rotationYaw = (float)(Math.atan2(par1, par5) * 180.0D / 3.141592653589793D));
/* 134:144 */     this.prevRotationPitch = (this.rotationPitch = (float)(Math.atan2(par3, var10) * 180.0D / 3.141592653589793D));
/* 135:145 */     this.ticksInGround = 0;
/* 136:    */   }
/* 137:    */   
/* 138:    */   public void setPositionAndRotation2(double par1, double par3, double par5, float par7, float par8, int par9)
/* 139:    */   {
/* 140:154 */     setPosition(par1, par3, par5);
/* 141:155 */     setRotation(par7, par8);
/* 142:    */   }
/* 143:    */   
/* 144:    */   public void setVelocity(double par1, double par3, double par5)
/* 145:    */   {
/* 146:163 */     this.motionX = par1;
/* 147:164 */     this.motionY = par3;
/* 148:165 */     this.motionZ = par5;
/* 149:167 */     if ((this.prevRotationPitch == 0.0F) && (this.prevRotationYaw == 0.0F))
/* 150:    */     {
/* 151:169 */       float var7 = MathHelper.sqrt_double(par1 * par1 + par5 * par5);
/* 152:170 */       this.prevRotationYaw = (this.rotationYaw = (float)(Math.atan2(par1, par5) * 180.0D / 3.141592653589793D));
/* 153:171 */       this.prevRotationPitch = (this.rotationPitch = (float)(Math.atan2(par3, var7) * 180.0D / 3.141592653589793D));
/* 154:172 */       this.prevRotationPitch = this.rotationPitch;
/* 155:173 */       this.prevRotationYaw = this.rotationYaw;
/* 156:174 */       setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
/* 157:175 */       this.ticksInGround = 0;
/* 158:    */     }
/* 159:    */   }
/* 160:    */   
/* 161:    */   public void onUpdate()
/* 162:    */   {
/* 163:184 */     super.onUpdate();
/* 164:186 */     if ((this.prevRotationPitch == 0.0F) && (this.prevRotationYaw == 0.0F))
/* 165:    */     {
/* 166:188 */       float var1 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
/* 167:189 */       this.prevRotationYaw = (this.rotationYaw = (float)(Math.atan2(this.motionX, this.motionZ) * 180.0D / 3.141592653589793D));
/* 168:190 */       this.prevRotationPitch = (this.rotationPitch = (float)(Math.atan2(this.motionY, var1) * 180.0D / 3.141592653589793D));
/* 169:    */     }
/* 170:193 */     Block var16 = this.worldObj.getBlock(this.field_145791_d, this.field_145792_e, this.field_145789_f);
/* 171:195 */     if (var16.getMaterial() != Material.air)
/* 172:    */     {
/* 173:197 */       var16.setBlockBoundsBasedOnState(this.worldObj, this.field_145791_d, this.field_145792_e, this.field_145789_f);
/* 174:198 */       AxisAlignedBB var2 = var16.getCollisionBoundingBoxFromPool(this.worldObj, this.field_145791_d, this.field_145792_e, this.field_145789_f);
/* 175:200 */       if ((var2 != null) && (var2.isVecInside(this.worldObj.getWorldVec3Pool().getVecFromPool(this.posX, this.posY, this.posZ)))) {
/* 176:202 */         this.inGround = true;
/* 177:    */       }
/* 178:    */     }
/* 179:206 */     if (this.arrowShake > 0) {
/* 180:208 */       this.arrowShake -= 1;
/* 181:    */     }
/* 182:211 */     if (this.inGround)
/* 183:    */     {
/* 184:213 */       int var18 = this.worldObj.getBlockMetadata(this.field_145791_d, this.field_145792_e, this.field_145789_f);
/* 185:215 */       if ((var16 == this.field_145790_g) && (var18 == this.inData))
/* 186:    */       {
/* 187:217 */         this.ticksInGround += 1;
/* 188:219 */         if (this.ticksInGround == 1200) {
/* 189:221 */           setDead();
/* 190:    */         }
/* 191:    */       }
/* 192:    */       else
/* 193:    */       {
/* 194:226 */         this.inGround = false;
/* 195:227 */         this.motionX *= this.rand.nextFloat() * 0.2F;
/* 196:228 */         this.motionY *= this.rand.nextFloat() * 0.2F;
/* 197:229 */         this.motionZ *= this.rand.nextFloat() * 0.2F;
/* 198:230 */         this.ticksInGround = 0;
/* 199:231 */         this.ticksInAir = 0;
/* 200:    */       }
/* 201:    */     }
/* 202:    */     else
/* 203:    */     {
/* 204:236 */       this.ticksInAir += 1;
/* 205:237 */       Vec3 var17 = this.worldObj.getWorldVec3Pool().getVecFromPool(this.posX, this.posY, this.posZ);
/* 206:238 */       Vec3 var3 = this.worldObj.getWorldVec3Pool().getVecFromPool(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
/* 207:239 */       MovingObjectPosition var4 = this.worldObj.func_147447_a(var17, var3, false, true, false);
/* 208:240 */       var17 = this.worldObj.getWorldVec3Pool().getVecFromPool(this.posX, this.posY, this.posZ);
/* 209:241 */       var3 = this.worldObj.getWorldVec3Pool().getVecFromPool(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
/* 210:243 */       if (var4 != null) {
/* 211:245 */         var3 = this.worldObj.getWorldVec3Pool().getVecFromPool(var4.hitVec.xCoord, var4.hitVec.yCoord, var4.hitVec.zCoord);
/* 212:    */       }
/* 213:248 */       Entity var5 = null;
/* 214:249 */       List var6 = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.addCoord(this.motionX, this.motionY, this.motionZ).expand(1.0D, 1.0D, 1.0D));
/* 215:250 */       double var7 = 0.0D;
/* 216:254 */       for (int var9 = 0; var9 < var6.size(); var9++)
/* 217:    */       {
/* 218:256 */         Entity var10 = (Entity)var6.get(var9);
/* 219:258 */         if ((var10.canBeCollidedWith()) && ((var10 != this.shootingEntity) || (this.ticksInAir >= 5)))
/* 220:    */         {
/* 221:260 */           float var11 = 0.3F;
/* 222:261 */           AxisAlignedBB var12 = var10.boundingBox.expand(var11, var11, var11);
/* 223:262 */           MovingObjectPosition var13 = var12.calculateIntercept(var17, var3);
/* 224:264 */           if (var13 != null)
/* 225:    */           {
/* 226:266 */             double var14 = var17.distanceTo(var13.hitVec);
/* 227:268 */             if ((var14 < var7) || (var7 == 0.0D))
/* 228:    */             {
/* 229:270 */               var5 = var10;
/* 230:271 */               var7 = var14;
/* 231:    */             }
/* 232:    */           }
/* 233:    */         }
/* 234:    */       }
/* 235:277 */       if (var5 != null) {
/* 236:279 */         var4 = new MovingObjectPosition(var5);
/* 237:    */       }
/* 238:282 */       if ((var4 != null) && (var4.entityHit != null) && ((var4.entityHit instanceof EntityPlayer)))
/* 239:    */       {
/* 240:284 */         EntityPlayer var20 = (EntityPlayer)var4.entityHit;
/* 241:286 */         if ((var20.capabilities.disableDamage) || (((this.shootingEntity instanceof EntityPlayer)) && (!((EntityPlayer)this.shootingEntity).canAttackPlayer(var20)))) {
/* 242:288 */           var4 = null;
/* 243:    */         }
/* 244:    */       }
/* 245:295 */       if (var4 != null) {
/* 246:297 */         if (var4.entityHit != null)
/* 247:    */         {
/* 248:299 */           float var19 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
/* 249:300 */           int var23 = MathHelper.ceiling_double_int(var19 * this.damage);
/* 250:302 */           if (getIsCritical()) {
/* 251:304 */             var23 += this.rand.nextInt(var23 / 2 + 2);
/* 252:    */           }
/* 253:307 */           DamageSource var21 = null;
/* 254:309 */           if (this.shootingEntity == null) {
/* 255:311 */             var21 = DamageSource.causeArrowDamage(this, this);
/* 256:    */           } else {
/* 257:315 */             var21 = DamageSource.causeArrowDamage(this, this.shootingEntity);
/* 258:    */           }
/* 259:318 */           if ((isBurning()) && (!(var4.entityHit instanceof EntityEnderman))) {
/* 260:320 */             var4.entityHit.setFire(5);
/* 261:    */           }
/* 262:323 */           if (var4.entityHit.attackEntityFrom(var21, var23))
/* 263:    */           {
/* 264:325 */             if ((var4.entityHit instanceof EntityLivingBase))
/* 265:    */             {
/* 266:327 */               EntityLivingBase var24 = (EntityLivingBase)var4.entityHit;
/* 267:329 */               if (!this.worldObj.isClient) {
/* 268:331 */                 var24.setArrowCountInEntity(var24.getArrowCountInEntity() + 1);
/* 269:    */               }
/* 270:334 */               if (this.knockbackStrength > 0)
/* 271:    */               {
/* 272:336 */                 float var26 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
/* 273:338 */                 if (var26 > 0.0F) {
/* 274:340 */                   var4.entityHit.addVelocity(this.motionX * this.knockbackStrength * 0.6000000238418579D / var26, 0.1D, this.motionZ * this.knockbackStrength * 0.6000000238418579D / var26);
/* 275:    */                 }
/* 276:    */               }
/* 277:344 */               if ((this.shootingEntity != null) && ((this.shootingEntity instanceof EntityLivingBase)))
/* 278:    */               {
/* 279:346 */                 EnchantmentHelper.func_151384_a(var24, this.shootingEntity);
/* 280:347 */                 EnchantmentHelper.func_151385_b((EntityLivingBase)this.shootingEntity, var24);
/* 281:    */               }
/* 282:350 */               if ((this.shootingEntity != null) && (var4.entityHit != this.shootingEntity) && ((var4.entityHit instanceof EntityPlayer)) && ((this.shootingEntity instanceof EntityPlayerMP))) {
/* 283:352 */                 ((EntityPlayerMP)this.shootingEntity).playerNetServerHandler.sendPacket(new S2BPacketChangeGameState(6, 0.0F));
/* 284:    */               }
/* 285:    */             }
/* 286:356 */             playSound("random.bowhit", 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
/* 287:358 */             if (!(var4.entityHit instanceof EntityEnderman)) {
/* 288:360 */               setDead();
/* 289:    */             }
/* 290:    */           }
/* 291:    */           else
/* 292:    */           {
/* 293:365 */             this.motionX *= -0.1000000014901161D;
/* 294:366 */             this.motionY *= -0.1000000014901161D;
/* 295:367 */             this.motionZ *= -0.1000000014901161D;
/* 296:368 */             this.rotationYaw += 180.0F;
/* 297:369 */             this.prevRotationYaw += 180.0F;
/* 298:370 */             this.ticksInAir = 0;
/* 299:    */           }
/* 300:    */         }
/* 301:    */         else
/* 302:    */         {
/* 303:375 */           this.field_145791_d = var4.blockX;
/* 304:376 */           this.field_145792_e = var4.blockY;
/* 305:377 */           this.field_145789_f = var4.blockZ;
/* 306:378 */           this.field_145790_g = var16;
/* 307:379 */           this.inData = this.worldObj.getBlockMetadata(this.field_145791_d, this.field_145792_e, this.field_145789_f);
/* 308:380 */           this.motionX = ((float)(var4.hitVec.xCoord - this.posX));
/* 309:381 */           this.motionY = ((float)(var4.hitVec.yCoord - this.posY));
/* 310:382 */           this.motionZ = ((float)(var4.hitVec.zCoord - this.posZ));
/* 311:383 */           float var19 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
/* 312:384 */           this.posX -= this.motionX / var19 * 0.0500000007450581D;
/* 313:385 */           this.posY -= this.motionY / var19 * 0.0500000007450581D;
/* 314:386 */           this.posZ -= this.motionZ / var19 * 0.0500000007450581D;
/* 315:387 */           playSound("random.bowhit", 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
/* 316:388 */           this.inGround = true;
/* 317:389 */           this.arrowShake = 7;
/* 318:390 */           setIsCritical(false);
/* 319:392 */           if (this.field_145790_g.getMaterial() != Material.air) {
/* 320:394 */             this.field_145790_g.onEntityCollidedWithBlock(this.worldObj, this.field_145791_d, this.field_145792_e, this.field_145789_f, this);
/* 321:    */           }
/* 322:    */         }
/* 323:    */       }
/* 324:399 */       if (getIsCritical()) {
/* 325:401 */         for (var9 = 0; var9 < 4; var9++) {
/* 326:403 */           this.worldObj.spawnParticle("crit", this.posX + this.motionX * var9 / 4.0D, this.posY + this.motionY * var9 / 4.0D, this.posZ + this.motionZ * var9 / 4.0D, -this.motionX, -this.motionY + 0.2D, -this.motionZ);
/* 327:    */         }
/* 328:    */       }
/* 329:407 */       this.posX += this.motionX;
/* 330:408 */       this.posY += this.motionY;
/* 331:409 */       this.posZ += this.motionZ;
/* 332:410 */       float var19 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
/* 333:411 */       this.rotationYaw = ((float)(Math.atan2(this.motionX, this.motionZ) * 180.0D / 3.141592653589793D));
/* 334:413 */       for (this.rotationPitch = ((float)(Math.atan2(this.motionY, var19) * 180.0D / 3.141592653589793D)); this.rotationPitch - this.prevRotationPitch < -180.0F; this.prevRotationPitch -= 360.0F) {}
/* 335:418 */       while (this.rotationPitch - this.prevRotationPitch >= 180.0F) {
/* 336:420 */         this.prevRotationPitch += 360.0F;
/* 337:    */       }
/* 338:423 */       while (this.rotationYaw - this.prevRotationYaw < -180.0F) {
/* 339:425 */         this.prevRotationYaw -= 360.0F;
/* 340:    */       }
/* 341:428 */       while (this.rotationYaw - this.prevRotationYaw >= 180.0F) {
/* 342:430 */         this.prevRotationYaw += 360.0F;
/* 343:    */       }
/* 344:433 */       this.rotationPitch = (this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * 0.2F);
/* 345:434 */       this.rotationYaw = (this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * 0.2F);
/* 346:435 */       float var22 = 0.99F;
/* 347:436 */       float var11 = 0.05F;
/* 348:438 */       if (isInWater())
/* 349:    */       {
/* 350:440 */         for (int var25 = 0; var25 < 4; var25++)
/* 351:    */         {
/* 352:442 */           float var26 = 0.25F;
/* 353:443 */           this.worldObj.spawnParticle("bubble", this.posX - this.motionX * var26, this.posY - this.motionY * var26, this.posZ - this.motionZ * var26, this.motionX, this.motionY, this.motionZ);
/* 354:    */         }
/* 355:446 */         var22 = 0.8F;
/* 356:    */       }
/* 357:449 */       if (isWet()) {
/* 358:451 */         extinguish();
/* 359:    */       }
/* 360:454 */       this.motionX *= var22;
/* 361:455 */       this.motionY *= var22;
/* 362:456 */       this.motionZ *= var22;
/* 363:457 */       this.motionY -= var11;
/* 364:458 */       setPosition(this.posX, this.posY, this.posZ);
/* 365:459 */       func_145775_I();
/* 366:    */     }
/* 367:    */   }
/* 368:    */   
/* 369:    */   public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
/* 370:    */   {
/* 371:468 */     par1NBTTagCompound.setShort("xTile", (short)this.field_145791_d);
/* 372:469 */     par1NBTTagCompound.setShort("yTile", (short)this.field_145792_e);
/* 373:470 */     par1NBTTagCompound.setShort("zTile", (short)this.field_145789_f);
/* 374:471 */     par1NBTTagCompound.setShort("life", (short)this.ticksInGround);
/* 375:472 */     par1NBTTagCompound.setByte("inTile", (byte)Block.getIdFromBlock(this.field_145790_g));
/* 376:473 */     par1NBTTagCompound.setByte("inData", (byte)this.inData);
/* 377:474 */     par1NBTTagCompound.setByte("shake", (byte)this.arrowShake);
/* 378:475 */     par1NBTTagCompound.setByte("inGround", (byte)(this.inGround ? 1 : 0));
/* 379:476 */     par1NBTTagCompound.setByte("pickup", (byte)this.canBePickedUp);
/* 380:477 */     par1NBTTagCompound.setDouble("damage", this.damage);
/* 381:    */   }
/* 382:    */   
/* 383:    */   public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
/* 384:    */   {
/* 385:485 */     this.field_145791_d = par1NBTTagCompound.getShort("xTile");
/* 386:486 */     this.field_145792_e = par1NBTTagCompound.getShort("yTile");
/* 387:487 */     this.field_145789_f = par1NBTTagCompound.getShort("zTile");
/* 388:488 */     this.ticksInGround = par1NBTTagCompound.getShort("life");
/* 389:489 */     this.field_145790_g = Block.getBlockById(par1NBTTagCompound.getByte("inTile") & 0xFF);
/* 390:490 */     this.inData = (par1NBTTagCompound.getByte("inData") & 0xFF);
/* 391:491 */     this.arrowShake = (par1NBTTagCompound.getByte("shake") & 0xFF);
/* 392:492 */     this.inGround = (par1NBTTagCompound.getByte("inGround") == 1);
/* 393:494 */     if (par1NBTTagCompound.func_150297_b("damage", 99)) {
/* 394:496 */       this.damage = par1NBTTagCompound.getDouble("damage");
/* 395:    */     }
/* 396:499 */     if (par1NBTTagCompound.func_150297_b("pickup", 99)) {
/* 397:501 */       this.canBePickedUp = par1NBTTagCompound.getByte("pickup");
/* 398:503 */     } else if (par1NBTTagCompound.func_150297_b("player", 99)) {
/* 399:505 */       this.canBePickedUp = (par1NBTTagCompound.getBoolean("player") ? 1 : 0);
/* 400:    */     }
/* 401:    */   }
/* 402:    */   
/* 403:    */   public void onCollideWithPlayer(EntityPlayer par1EntityPlayer)
/* 404:    */   {
/* 405:514 */     if ((!this.worldObj.isClient) && (this.inGround) && (this.arrowShake <= 0))
/* 406:    */     {
/* 407:516 */       boolean var2 = (this.canBePickedUp == 1) || ((this.canBePickedUp == 2) && (par1EntityPlayer.capabilities.isCreativeMode));
/* 408:518 */       if ((this.canBePickedUp == 1) && (!par1EntityPlayer.inventory.addItemStackToInventory(new ItemStack(Items.arrow, 1)))) {
/* 409:520 */         var2 = false;
/* 410:    */       }
/* 411:523 */       if (var2)
/* 412:    */       {
/* 413:525 */         playSound("random.pop", 0.2F, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
/* 414:526 */         par1EntityPlayer.onItemPickup(this, 1);
/* 415:527 */         setDead();
/* 416:    */       }
/* 417:    */     }
/* 418:    */   }
/* 419:    */   
/* 420:    */   protected boolean canTriggerWalking()
/* 421:    */   {
/* 422:538 */     return false;
/* 423:    */   }
/* 424:    */   
/* 425:    */   public float getShadowSize()
/* 426:    */   {
/* 427:543 */     return 0.0F;
/* 428:    */   }
/* 429:    */   
/* 430:    */   public void setDamage(double par1)
/* 431:    */   {
/* 432:548 */     this.damage = par1;
/* 433:    */   }
/* 434:    */   
/* 435:    */   public double getDamage()
/* 436:    */   {
/* 437:553 */     return this.damage;
/* 438:    */   }
/* 439:    */   
/* 440:    */   public void setKnockbackStrength(int par1)
/* 441:    */   {
/* 442:561 */     this.knockbackStrength = par1;
/* 443:    */   }
/* 444:    */   
/* 445:    */   public boolean canAttackWithItem()
/* 446:    */   {
/* 447:569 */     return false;
/* 448:    */   }
/* 449:    */   
/* 450:    */   public void setIsCritical(boolean par1)
/* 451:    */   {
/* 452:577 */     byte var2 = this.dataWatcher.getWatchableObjectByte(16);
/* 453:579 */     if (par1) {
/* 454:581 */       this.dataWatcher.updateObject(16, Byte.valueOf((byte)(var2 | 0x1)));
/* 455:    */     } else {
/* 456:585 */       this.dataWatcher.updateObject(16, Byte.valueOf((byte)(var2 & 0xFFFFFFFE)));
/* 457:    */     }
/* 458:    */   }
/* 459:    */   
/* 460:    */   public boolean getIsCritical()
/* 461:    */   {
/* 462:594 */     byte var1 = this.dataWatcher.getWatchableObjectByte(16);
/* 463:595 */     return (var1 & 0x1) != 0;
/* 464:    */   }
/* 465:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.entity.projectile.EntityArrow
 * JD-Core Version:    0.7.0.1
 */