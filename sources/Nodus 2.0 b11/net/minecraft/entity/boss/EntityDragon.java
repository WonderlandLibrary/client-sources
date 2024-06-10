/*   1:    */ package net.minecraft.entity.boss;
/*   2:    */ 
/*   3:    */ import java.util.Iterator;
/*   4:    */ import java.util.List;
/*   5:    */ import java.util.Random;
/*   6:    */ import net.minecraft.block.Block;
/*   7:    */ import net.minecraft.block.material.Material;
/*   8:    */ import net.minecraft.entity.Entity;
/*   9:    */ import net.minecraft.entity.EntityLiving;
/*  10:    */ import net.minecraft.entity.EntityLivingBase;
/*  11:    */ import net.minecraft.entity.IEntityMultiPart;
/*  12:    */ import net.minecraft.entity.SharedMonsterAttributes;
/*  13:    */ import net.minecraft.entity.ai.attributes.IAttributeInstance;
/*  14:    */ import net.minecraft.entity.item.EntityEnderCrystal;
/*  15:    */ import net.minecraft.entity.item.EntityXPOrb;
/*  16:    */ import net.minecraft.entity.monster.IMob;
/*  17:    */ import net.minecraft.entity.player.EntityPlayer;
/*  18:    */ import net.minecraft.init.Blocks;
/*  19:    */ import net.minecraft.util.AxisAlignedBB;
/*  20:    */ import net.minecraft.util.DamageSource;
/*  21:    */ import net.minecraft.util.MathHelper;
/*  22:    */ import net.minecraft.util.Vec3;
/*  23:    */ import net.minecraft.util.Vec3Pool;
/*  24:    */ import net.minecraft.world.GameRules;
/*  25:    */ import net.minecraft.world.World;
/*  26:    */ 
/*  27:    */ public class EntityDragon
/*  28:    */   extends EntityLiving
/*  29:    */   implements IBossDisplayData, IEntityMultiPart, IMob
/*  30:    */ {
/*  31:    */   public double targetX;
/*  32:    */   public double targetY;
/*  33:    */   public double targetZ;
/*  34: 34 */   public double[][] ringBuffer = new double[64][3];
/*  35: 39 */   public int ringBufferIndex = -1;
/*  36:    */   public EntityDragonPart[] dragonPartArray;
/*  37:    */   public EntityDragonPart dragonPartHead;
/*  38:    */   public EntityDragonPart dragonPartBody;
/*  39:    */   public EntityDragonPart dragonPartTail1;
/*  40:    */   public EntityDragonPart dragonPartTail2;
/*  41:    */   public EntityDragonPart dragonPartTail3;
/*  42:    */   public EntityDragonPart dragonPartWing1;
/*  43:    */   public EntityDragonPart dragonPartWing2;
/*  44:    */   public float prevAnimTime;
/*  45:    */   public float animTime;
/*  46:    */   public boolean forceNewTarget;
/*  47:    */   public boolean slowed;
/*  48:    */   private Entity target;
/*  49:    */   public int deathTicks;
/*  50:    */   public EntityEnderCrystal healingEnderCrystal;
/*  51:    */   private static final String __OBFID = "CL_00001659";
/*  52:    */   
/*  53:    */   public EntityDragon(World par1World)
/*  54:    */   {
/*  55: 79 */     super(par1World); EntityDragonPart[] 
/*  56: 80 */       tmp27_24 = new EntityDragonPart[7]; void tmp44_41 = new EntityDragonPart(this, "head", 6.0F, 6.0F);this.dragonPartHead = tmp44_41;tmp27_24[0] = tmp44_41; EntityDragonPart[] tmp49_27 = tmp27_24; void tmp66_63 = new EntityDragonPart(this, "body", 8.0F, 8.0F);this.dragonPartBody = tmp66_63;tmp49_27[1] = tmp66_63; EntityDragonPart[] tmp71_49 = tmp49_27; void tmp88_85 = new EntityDragonPart(this, "tail", 4.0F, 4.0F);this.dragonPartTail1 = tmp88_85;tmp71_49[2] = tmp88_85; EntityDragonPart[] tmp93_71 = tmp71_49; void tmp110_107 = new EntityDragonPart(this, "tail", 4.0F, 4.0F);this.dragonPartTail2 = tmp110_107;tmp93_71[3] = tmp110_107; EntityDragonPart[] tmp115_93 = tmp93_71; void tmp132_129 = new EntityDragonPart(this, "tail", 4.0F, 4.0F);this.dragonPartTail3 = tmp132_129;tmp115_93[4] = tmp132_129; EntityDragonPart[] tmp137_115 = tmp115_93; void tmp154_151 = new EntityDragonPart(this, "wing", 4.0F, 4.0F);this.dragonPartWing1 = tmp154_151;tmp137_115[5] = tmp154_151; EntityDragonPart[] tmp159_137 = tmp137_115; void tmp177_174 = new EntityDragonPart(this, "wing", 4.0F, 4.0F);this.dragonPartWing2 = tmp177_174;tmp159_137[6] = tmp177_174;this.dragonPartArray = tmp159_137;
/*  57: 81 */     setHealth(getMaxHealth());
/*  58: 82 */     setSize(16.0F, 8.0F);
/*  59: 83 */     this.noClip = true;
/*  60: 84 */     this.isImmuneToFire = true;
/*  61: 85 */     this.targetY = 100.0D;
/*  62: 86 */     this.ignoreFrustumCheck = true;
/*  63:    */   }
/*  64:    */   
/*  65:    */   protected void applyEntityAttributes()
/*  66:    */   {
/*  67: 91 */     super.applyEntityAttributes();
/*  68: 92 */     getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(200.0D);
/*  69:    */   }
/*  70:    */   
/*  71:    */   protected void entityInit()
/*  72:    */   {
/*  73: 97 */     super.entityInit();
/*  74:    */   }
/*  75:    */   
/*  76:    */   public double[] getMovementOffsets(int par1, float par2)
/*  77:    */   {
/*  78:106 */     if (getHealth() <= 0.0F) {
/*  79:108 */       par2 = 0.0F;
/*  80:    */     }
/*  81:111 */     par2 = 1.0F - par2;
/*  82:112 */     int var3 = this.ringBufferIndex - par1 * 1 & 0x3F;
/*  83:113 */     int var4 = this.ringBufferIndex - par1 * 1 - 1 & 0x3F;
/*  84:114 */     double[] var5 = new double[3];
/*  85:115 */     double var6 = this.ringBuffer[var3][0];
/*  86:116 */     double var8 = MathHelper.wrapAngleTo180_double(this.ringBuffer[var4][0] - var6);
/*  87:117 */     var5[0] = (var6 + var8 * par2);
/*  88:118 */     var6 = this.ringBuffer[var3][1];
/*  89:119 */     var8 = this.ringBuffer[var4][1] - var6;
/*  90:120 */     var5[1] = (var6 + var8 * par2);
/*  91:121 */     var5[2] = (this.ringBuffer[var3][2] + (this.ringBuffer[var4][2] - this.ringBuffer[var3][2]) * par2);
/*  92:122 */     return var5;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public void onLivingUpdate()
/*  96:    */   {
/*  97:134 */     if (this.worldObj.isClient)
/*  98:    */     {
/*  99:136 */       float var1 = MathHelper.cos(this.animTime * 3.141593F * 2.0F);
/* 100:137 */       float var2 = MathHelper.cos(this.prevAnimTime * 3.141593F * 2.0F);
/* 101:139 */       if ((var2 <= -0.3F) && (var1 >= -0.3F)) {
/* 102:141 */         this.worldObj.playSound(this.posX, this.posY, this.posZ, "mob.enderdragon.wings", 5.0F, 0.8F + this.rand.nextFloat() * 0.3F, false);
/* 103:    */       }
/* 104:    */     }
/* 105:145 */     this.prevAnimTime = this.animTime;
/* 106:148 */     if (getHealth() <= 0.0F)
/* 107:    */     {
/* 108:150 */       float var1 = (this.rand.nextFloat() - 0.5F) * 8.0F;
/* 109:151 */       float var2 = (this.rand.nextFloat() - 0.5F) * 4.0F;
/* 110:152 */       float var3 = (this.rand.nextFloat() - 0.5F) * 8.0F;
/* 111:153 */       this.worldObj.spawnParticle("largeexplode", this.posX + var1, this.posY + 2.0D + var2, this.posZ + var3, 0.0D, 0.0D, 0.0D);
/* 112:    */     }
/* 113:    */     else
/* 114:    */     {
/* 115:157 */       updateDragonEnderCrystal();
/* 116:158 */       float var1 = 0.2F / (MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ) * 10.0F + 1.0F);
/* 117:159 */       var1 *= (float)Math.pow(2.0D, this.motionY);
/* 118:161 */       if (this.slowed) {
/* 119:163 */         this.animTime += var1 * 0.5F;
/* 120:    */       } else {
/* 121:167 */         this.animTime += var1;
/* 122:    */       }
/* 123:170 */       this.rotationYaw = MathHelper.wrapAngleTo180_float(this.rotationYaw);
/* 124:172 */       if (this.ringBufferIndex < 0) {
/* 125:174 */         for (int var25 = 0; var25 < this.ringBuffer.length; var25++)
/* 126:    */         {
/* 127:176 */           this.ringBuffer[var25][0] = this.rotationYaw;
/* 128:177 */           this.ringBuffer[var25][1] = this.posY;
/* 129:    */         }
/* 130:    */       }
/* 131:181 */       if (++this.ringBufferIndex == this.ringBuffer.length) {
/* 132:183 */         this.ringBufferIndex = 0;
/* 133:    */       }
/* 134:186 */       this.ringBuffer[this.ringBufferIndex][0] = this.rotationYaw;
/* 135:187 */       this.ringBuffer[this.ringBufferIndex][1] = this.posY;
/* 136:194 */       if (this.worldObj.isClient)
/* 137:    */       {
/* 138:196 */         if (this.newPosRotationIncrements > 0)
/* 139:    */         {
/* 140:198 */           double var26 = this.posX + (this.newPosX - this.posX) / this.newPosRotationIncrements;
/* 141:199 */           double var4 = this.posY + (this.newPosY - this.posY) / this.newPosRotationIncrements;
/* 142:200 */           double var6 = this.posZ + (this.newPosZ - this.posZ) / this.newPosRotationIncrements;
/* 143:201 */           double var8 = MathHelper.wrapAngleTo180_double(this.newRotationYaw - this.rotationYaw);
/* 144:202 */           this.rotationYaw = ((float)(this.rotationYaw + var8 / this.newPosRotationIncrements));
/* 145:203 */           this.rotationPitch = ((float)(this.rotationPitch + (this.newRotationPitch - this.rotationPitch) / this.newPosRotationIncrements));
/* 146:204 */           this.newPosRotationIncrements -= 1;
/* 147:205 */           setPosition(var26, var4, var6);
/* 148:206 */           setRotation(this.rotationYaw, this.rotationPitch);
/* 149:    */         }
/* 150:    */       }
/* 151:    */       else
/* 152:    */       {
/* 153:211 */         double var26 = this.targetX - this.posX;
/* 154:212 */         double var4 = this.targetY - this.posY;
/* 155:213 */         double var6 = this.targetZ - this.posZ;
/* 156:214 */         double var8 = var26 * var26 + var4 * var4 + var6 * var6;
/* 157:216 */         if (this.target != null)
/* 158:    */         {
/* 159:218 */           this.targetX = this.target.posX;
/* 160:219 */           this.targetZ = this.target.posZ;
/* 161:220 */           double var10 = this.targetX - this.posX;
/* 162:221 */           double var12 = this.targetZ - this.posZ;
/* 163:222 */           double var14 = Math.sqrt(var10 * var10 + var12 * var12);
/* 164:223 */           double var16 = 0.4000000059604645D + var14 / 80.0D - 1.0D;
/* 165:225 */           if (var16 > 10.0D) {
/* 166:227 */             var16 = 10.0D;
/* 167:    */           }
/* 168:230 */           this.targetY = (this.target.boundingBox.minY + var16);
/* 169:    */         }
/* 170:    */         else
/* 171:    */         {
/* 172:234 */           this.targetX += this.rand.nextGaussian() * 2.0D;
/* 173:235 */           this.targetZ += this.rand.nextGaussian() * 2.0D;
/* 174:    */         }
/* 175:238 */         if ((this.forceNewTarget) || (var8 < 100.0D) || (var8 > 22500.0D) || (this.isCollidedHorizontally) || (this.isCollidedVertically)) {
/* 176:240 */           setNewTarget();
/* 177:    */         }
/* 178:243 */         var4 /= MathHelper.sqrt_double(var26 * var26 + var6 * var6);
/* 179:244 */         float var33 = 0.6F;
/* 180:246 */         if (var4 < -var33) {
/* 181:248 */           var4 = -var33;
/* 182:    */         }
/* 183:251 */         if (var4 > var33) {
/* 184:253 */           var4 = var33;
/* 185:    */         }
/* 186:256 */         this.motionY += var4 * 0.1000000014901161D;
/* 187:257 */         this.rotationYaw = MathHelper.wrapAngleTo180_float(this.rotationYaw);
/* 188:258 */         double var11 = 180.0D - Math.atan2(var26, var6) * 180.0D / 3.141592653589793D;
/* 189:259 */         double var13 = MathHelper.wrapAngleTo180_double(var11 - this.rotationYaw);
/* 190:261 */         if (var13 > 50.0D) {
/* 191:263 */           var13 = 50.0D;
/* 192:    */         }
/* 193:266 */         if (var13 < -50.0D) {
/* 194:268 */           var13 = -50.0D;
/* 195:    */         }
/* 196:271 */         Vec3 var15 = this.worldObj.getWorldVec3Pool().getVecFromPool(this.targetX - this.posX, this.targetY - this.posY, this.targetZ - this.posZ).normalize();
/* 197:272 */         Vec3 var40 = this.worldObj.getWorldVec3Pool().getVecFromPool(MathHelper.sin(this.rotationYaw * 3.141593F / 180.0F), this.motionY, -MathHelper.cos(this.rotationYaw * 3.141593F / 180.0F)).normalize();
/* 198:273 */         float var17 = (float)(var40.dotProduct(var15) + 0.5D) / 1.5F;
/* 199:275 */         if (var17 < 0.0F) {
/* 200:277 */           var17 = 0.0F;
/* 201:    */         }
/* 202:280 */         this.randomYawVelocity *= 0.8F;
/* 203:281 */         float var18 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ) * 1.0F + 1.0F;
/* 204:282 */         double var19 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ) * 1.0D + 1.0D;
/* 205:284 */         if (var19 > 40.0D) {
/* 206:286 */           var19 = 40.0D;
/* 207:    */         }
/* 208:289 */         this.randomYawVelocity = ((float)(this.randomYawVelocity + var13 * (0.699999988079071D / var19 / var18)));
/* 209:290 */         this.rotationYaw += this.randomYawVelocity * 0.1F;
/* 210:291 */         float var21 = (float)(2.0D / (var19 + 1.0D));
/* 211:292 */         float var22 = 0.06F;
/* 212:293 */         moveFlying(0.0F, -1.0F, var22 * (var17 * var21 + (1.0F - var21)));
/* 213:295 */         if (this.slowed) {
/* 214:297 */           moveEntity(this.motionX * 0.800000011920929D, this.motionY * 0.800000011920929D, this.motionZ * 0.800000011920929D);
/* 215:    */         } else {
/* 216:301 */           moveEntity(this.motionX, this.motionY, this.motionZ);
/* 217:    */         }
/* 218:304 */         Vec3 var23 = this.worldObj.getWorldVec3Pool().getVecFromPool(this.motionX, this.motionY, this.motionZ).normalize();
/* 219:305 */         float var24 = (float)(var23.dotProduct(var40) + 1.0D) / 2.0F;
/* 220:306 */         var24 = 0.8F + 0.15F * var24;
/* 221:307 */         this.motionX *= var24;
/* 222:308 */         this.motionZ *= var24;
/* 223:309 */         this.motionY *= 0.910000026226044D;
/* 224:    */       }
/* 225:312 */       this.renderYawOffset = this.rotationYaw;
/* 226:313 */       this.dragonPartHead.width = (this.dragonPartHead.height = 3.0F);
/* 227:314 */       this.dragonPartTail1.width = (this.dragonPartTail1.height = 2.0F);
/* 228:315 */       this.dragonPartTail2.width = (this.dragonPartTail2.height = 2.0F);
/* 229:316 */       this.dragonPartTail3.width = (this.dragonPartTail3.height = 2.0F);
/* 230:317 */       this.dragonPartBody.height = 3.0F;
/* 231:318 */       this.dragonPartBody.width = 5.0F;
/* 232:319 */       this.dragonPartWing1.height = 2.0F;
/* 233:320 */       this.dragonPartWing1.width = 4.0F;
/* 234:321 */       this.dragonPartWing2.height = 3.0F;
/* 235:322 */       this.dragonPartWing2.width = 4.0F;
/* 236:323 */       float var2 = (float)(getMovementOffsets(5, 1.0F)[1] - getMovementOffsets(10, 1.0F)[1]) * 10.0F / 180.0F * 3.141593F;
/* 237:324 */       float var3 = MathHelper.cos(var2);
/* 238:325 */       float var28 = -MathHelper.sin(var2);
/* 239:326 */       float var5 = this.rotationYaw * 3.141593F / 180.0F;
/* 240:327 */       float var27 = MathHelper.sin(var5);
/* 241:328 */       float var7 = MathHelper.cos(var5);
/* 242:329 */       this.dragonPartBody.onUpdate();
/* 243:330 */       this.dragonPartBody.setLocationAndAngles(this.posX + var27 * 0.5F, this.posY, this.posZ - var7 * 0.5F, 0.0F, 0.0F);
/* 244:331 */       this.dragonPartWing1.onUpdate();
/* 245:332 */       this.dragonPartWing1.setLocationAndAngles(this.posX + var7 * 4.5F, this.posY + 2.0D, this.posZ + var27 * 4.5F, 0.0F, 0.0F);
/* 246:333 */       this.dragonPartWing2.onUpdate();
/* 247:334 */       this.dragonPartWing2.setLocationAndAngles(this.posX - var7 * 4.5F, this.posY + 2.0D, this.posZ - var27 * 4.5F, 0.0F, 0.0F);
/* 248:336 */       if ((!this.worldObj.isClient) && (this.hurtTime == 0))
/* 249:    */       {
/* 250:338 */         collideWithEntities(this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.dragonPartWing1.boundingBox.expand(4.0D, 2.0D, 4.0D).offset(0.0D, -2.0D, 0.0D)));
/* 251:339 */         collideWithEntities(this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.dragonPartWing2.boundingBox.expand(4.0D, 2.0D, 4.0D).offset(0.0D, -2.0D, 0.0D)));
/* 252:340 */         attackEntitiesInList(this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.dragonPartHead.boundingBox.expand(1.0D, 1.0D, 1.0D)));
/* 253:    */       }
/* 254:343 */       double[] var29 = getMovementOffsets(5, 1.0F);
/* 255:344 */       double[] var9 = getMovementOffsets(0, 1.0F);
/* 256:345 */       float var33 = MathHelper.sin(this.rotationYaw * 3.141593F / 180.0F - this.randomYawVelocity * 0.01F);
/* 257:346 */       float var32 = MathHelper.cos(this.rotationYaw * 3.141593F / 180.0F - this.randomYawVelocity * 0.01F);
/* 258:347 */       this.dragonPartHead.onUpdate();
/* 259:348 */       this.dragonPartHead.setLocationAndAngles(this.posX + var33 * 5.5F * var3, this.posY + (var9[1] - var29[1]) * 1.0D + var28 * 5.5F, this.posZ - var32 * 5.5F * var3, 0.0F, 0.0F);
/* 260:350 */       for (int var30 = 0; var30 < 3; var30++)
/* 261:    */       {
/* 262:352 */         EntityDragonPart var31 = null;
/* 263:354 */         if (var30 == 0) {
/* 264:356 */           var31 = this.dragonPartTail1;
/* 265:    */         }
/* 266:359 */         if (var30 == 1) {
/* 267:361 */           var31 = this.dragonPartTail2;
/* 268:    */         }
/* 269:364 */         if (var30 == 2) {
/* 270:366 */           var31 = this.dragonPartTail3;
/* 271:    */         }
/* 272:369 */         double[] var35 = getMovementOffsets(12 + var30 * 2, 1.0F);
/* 273:370 */         float var34 = this.rotationYaw * 3.141593F / 180.0F + simplifyAngle(var35[0] - var29[0]) * 3.141593F / 180.0F * 1.0F;
/* 274:371 */         float var38 = MathHelper.sin(var34);
/* 275:372 */         float var37 = MathHelper.cos(var34);
/* 276:373 */         float var36 = 1.5F;
/* 277:374 */         float var39 = (var30 + 1) * 2.0F;
/* 278:375 */         var31.onUpdate();
/* 279:376 */         var31.setLocationAndAngles(this.posX - (var27 * var36 + var38 * var39) * var3, this.posY + (var35[1] - var29[1]) * 1.0D - (var39 + var36) * var28 + 1.5D, this.posZ + (var7 * var36 + var37 * var39) * var3, 0.0F, 0.0F);
/* 280:    */       }
/* 281:379 */       if (!this.worldObj.isClient) {
/* 282:381 */         this.slowed = (destroyBlocksInAABB(this.dragonPartHead.boundingBox) | destroyBlocksInAABB(this.dragonPartBody.boundingBox));
/* 283:    */       }
/* 284:    */     }
/* 285:    */   }
/* 286:    */   
/* 287:    */   private void updateDragonEnderCrystal()
/* 288:    */   {
/* 289:391 */     if (this.healingEnderCrystal != null) {
/* 290:393 */       if (this.healingEnderCrystal.isDead)
/* 291:    */       {
/* 292:395 */         if (!this.worldObj.isClient) {
/* 293:397 */           attackEntityFromPart(this.dragonPartHead, DamageSource.setExplosionSource(null), 10.0F);
/* 294:    */         }
/* 295:400 */         this.healingEnderCrystal = null;
/* 296:    */       }
/* 297:402 */       else if ((this.ticksExisted % 10 == 0) && (getHealth() < getMaxHealth()))
/* 298:    */       {
/* 299:404 */         setHealth(getHealth() + 1.0F);
/* 300:    */       }
/* 301:    */     }
/* 302:408 */     if (this.rand.nextInt(10) == 0)
/* 303:    */     {
/* 304:410 */       float var1 = 32.0F;
/* 305:411 */       List var2 = this.worldObj.getEntitiesWithinAABB(EntityEnderCrystal.class, this.boundingBox.expand(var1, var1, var1));
/* 306:412 */       EntityEnderCrystal var3 = null;
/* 307:413 */       double var4 = 1.7976931348623157E+308D;
/* 308:414 */       Iterator var6 = var2.iterator();
/* 309:416 */       while (var6.hasNext())
/* 310:    */       {
/* 311:418 */         EntityEnderCrystal var7 = (EntityEnderCrystal)var6.next();
/* 312:419 */         double var8 = var7.getDistanceSqToEntity(this);
/* 313:421 */         if (var8 < var4)
/* 314:    */         {
/* 315:423 */           var4 = var8;
/* 316:424 */           var3 = var7;
/* 317:    */         }
/* 318:    */       }
/* 319:428 */       this.healingEnderCrystal = var3;
/* 320:    */     }
/* 321:    */   }
/* 322:    */   
/* 323:    */   private void collideWithEntities(List par1List)
/* 324:    */   {
/* 325:437 */     double var2 = (this.dragonPartBody.boundingBox.minX + this.dragonPartBody.boundingBox.maxX) / 2.0D;
/* 326:438 */     double var4 = (this.dragonPartBody.boundingBox.minZ + this.dragonPartBody.boundingBox.maxZ) / 2.0D;
/* 327:439 */     Iterator var6 = par1List.iterator();
/* 328:441 */     while (var6.hasNext())
/* 329:    */     {
/* 330:443 */       Entity var7 = (Entity)var6.next();
/* 331:445 */       if ((var7 instanceof EntityLivingBase))
/* 332:    */       {
/* 333:447 */         double var8 = var7.posX - var2;
/* 334:448 */         double var10 = var7.posZ - var4;
/* 335:449 */         double var12 = var8 * var8 + var10 * var10;
/* 336:450 */         var7.addVelocity(var8 / var12 * 4.0D, 0.2000000029802322D, var10 / var12 * 4.0D);
/* 337:    */       }
/* 338:    */     }
/* 339:    */   }
/* 340:    */   
/* 341:    */   private void attackEntitiesInList(List par1List)
/* 342:    */   {
/* 343:460 */     for (int var2 = 0; var2 < par1List.size(); var2++)
/* 344:    */     {
/* 345:462 */       Entity var3 = (Entity)par1List.get(var2);
/* 346:464 */       if ((var3 instanceof EntityLivingBase)) {
/* 347:466 */         var3.attackEntityFrom(DamageSource.causeMobDamage(this), 10.0F);
/* 348:    */       }
/* 349:    */     }
/* 350:    */   }
/* 351:    */   
/* 352:    */   private void setNewTarget()
/* 353:    */   {
/* 354:476 */     this.forceNewTarget = false;
/* 355:478 */     if ((this.rand.nextInt(2) == 0) && (!this.worldObj.playerEntities.isEmpty()))
/* 356:    */     {
/* 357:480 */       this.target = ((Entity)this.worldObj.playerEntities.get(this.rand.nextInt(this.worldObj.playerEntities.size())));
/* 358:    */     }
/* 359:    */     else
/* 360:    */     {
/* 361:484 */       boolean var1 = false;
/* 362:    */       do
/* 363:    */       {
/* 364:488 */         this.targetX = 0.0D;
/* 365:489 */         this.targetY = (70.0F + this.rand.nextFloat() * 50.0F);
/* 366:490 */         this.targetZ = 0.0D;
/* 367:491 */         this.targetX += this.rand.nextFloat() * 120.0F - 60.0F;
/* 368:492 */         this.targetZ += this.rand.nextFloat() * 120.0F - 60.0F;
/* 369:493 */         double var2 = this.posX - this.targetX;
/* 370:494 */         double var4 = this.posY - this.targetY;
/* 371:495 */         double var6 = this.posZ - this.targetZ;
/* 372:496 */         var1 = var2 * var2 + var4 * var4 + var6 * var6 > 100.0D;
/* 373:498 */       } while (!var1);
/* 374:500 */       this.target = null;
/* 375:    */     }
/* 376:    */   }
/* 377:    */   
/* 378:    */   private float simplifyAngle(double par1)
/* 379:    */   {
/* 380:509 */     return (float)MathHelper.wrapAngleTo180_double(par1);
/* 381:    */   }
/* 382:    */   
/* 383:    */   private boolean destroyBlocksInAABB(AxisAlignedBB par1AxisAlignedBB)
/* 384:    */   {
/* 385:517 */     int var2 = MathHelper.floor_double(par1AxisAlignedBB.minX);
/* 386:518 */     int var3 = MathHelper.floor_double(par1AxisAlignedBB.minY);
/* 387:519 */     int var4 = MathHelper.floor_double(par1AxisAlignedBB.minZ);
/* 388:520 */     int var5 = MathHelper.floor_double(par1AxisAlignedBB.maxX);
/* 389:521 */     int var6 = MathHelper.floor_double(par1AxisAlignedBB.maxY);
/* 390:522 */     int var7 = MathHelper.floor_double(par1AxisAlignedBB.maxZ);
/* 391:523 */     boolean var8 = false;
/* 392:524 */     boolean var9 = false;
/* 393:526 */     for (int var10 = var2; var10 <= var5; var10++) {
/* 394:528 */       for (int var11 = var3; var11 <= var6; var11++) {
/* 395:530 */         for (int var12 = var4; var12 <= var7; var12++)
/* 396:    */         {
/* 397:532 */           Block var13 = this.worldObj.getBlock(var10, var11, var12);
/* 398:534 */           if (var13.getMaterial() != Material.air) {
/* 399:536 */             if ((var13 != Blocks.obsidian) && (var13 != Blocks.end_stone) && (var13 != Blocks.bedrock) && (this.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing"))) {
/* 400:538 */               var9 = (this.worldObj.setBlockToAir(var10, var11, var12)) || (var9);
/* 401:    */             } else {
/* 402:542 */               var8 = true;
/* 403:    */             }
/* 404:    */           }
/* 405:    */         }
/* 406:    */       }
/* 407:    */     }
/* 408:549 */     if (var9)
/* 409:    */     {
/* 410:551 */       double var16 = par1AxisAlignedBB.minX + (par1AxisAlignedBB.maxX - par1AxisAlignedBB.minX) * this.rand.nextFloat();
/* 411:552 */       double var17 = par1AxisAlignedBB.minY + (par1AxisAlignedBB.maxY - par1AxisAlignedBB.minY) * this.rand.nextFloat();
/* 412:553 */       double var14 = par1AxisAlignedBB.minZ + (par1AxisAlignedBB.maxZ - par1AxisAlignedBB.minZ) * this.rand.nextFloat();
/* 413:554 */       this.worldObj.spawnParticle("largeexplode", var16, var17, var14, 0.0D, 0.0D, 0.0D);
/* 414:    */     }
/* 415:557 */     return var8;
/* 416:    */   }
/* 417:    */   
/* 418:    */   public boolean attackEntityFromPart(EntityDragonPart par1EntityDragonPart, DamageSource par2DamageSource, float par3)
/* 419:    */   {
/* 420:562 */     if (par1EntityDragonPart != this.dragonPartHead) {
/* 421:564 */       par3 = par3 / 4.0F + 1.0F;
/* 422:    */     }
/* 423:567 */     float var4 = this.rotationYaw * 3.141593F / 180.0F;
/* 424:568 */     float var5 = MathHelper.sin(var4);
/* 425:569 */     float var6 = MathHelper.cos(var4);
/* 426:570 */     this.targetX = (this.posX + var5 * 5.0F + (this.rand.nextFloat() - 0.5F) * 2.0F);
/* 427:571 */     this.targetY = (this.posY + this.rand.nextFloat() * 3.0F + 1.0D);
/* 428:572 */     this.targetZ = (this.posZ - var6 * 5.0F + (this.rand.nextFloat() - 0.5F) * 2.0F);
/* 429:573 */     this.target = null;
/* 430:575 */     if (((par2DamageSource.getEntity() instanceof EntityPlayer)) || (par2DamageSource.isExplosion())) {
/* 431:577 */       func_82195_e(par2DamageSource, par3);
/* 432:    */     }
/* 433:580 */     return true;
/* 434:    */   }
/* 435:    */   
/* 436:    */   public boolean attackEntityFrom(DamageSource par1DamageSource, float par2)
/* 437:    */   {
/* 438:588 */     return false;
/* 439:    */   }
/* 440:    */   
/* 441:    */   protected boolean func_82195_e(DamageSource par1DamageSource, float par2)
/* 442:    */   {
/* 443:593 */     return super.attackEntityFrom(par1DamageSource, par2);
/* 444:    */   }
/* 445:    */   
/* 446:    */   protected void onDeathUpdate()
/* 447:    */   {
/* 448:601 */     this.deathTicks += 1;
/* 449:603 */     if ((this.deathTicks >= 180) && (this.deathTicks <= 200))
/* 450:    */     {
/* 451:605 */       float var1 = (this.rand.nextFloat() - 0.5F) * 8.0F;
/* 452:606 */       float var2 = (this.rand.nextFloat() - 0.5F) * 4.0F;
/* 453:607 */       float var3 = (this.rand.nextFloat() - 0.5F) * 8.0F;
/* 454:608 */       this.worldObj.spawnParticle("hugeexplosion", this.posX + var1, this.posY + 2.0D + var2, this.posZ + var3, 0.0D, 0.0D, 0.0D);
/* 455:    */     }
/* 456:614 */     if (!this.worldObj.isClient)
/* 457:    */     {
/* 458:616 */       if ((this.deathTicks > 150) && (this.deathTicks % 5 == 0))
/* 459:    */       {
/* 460:618 */         int var4 = 1000;
/* 461:620 */         while (var4 > 0)
/* 462:    */         {
/* 463:622 */           int var5 = EntityXPOrb.getXPSplit(var4);
/* 464:623 */           var4 -= var5;
/* 465:624 */           this.worldObj.spawnEntityInWorld(new EntityXPOrb(this.worldObj, this.posX, this.posY, this.posZ, var5));
/* 466:    */         }
/* 467:    */       }
/* 468:628 */       if (this.deathTicks == 1) {
/* 469:630 */         this.worldObj.playBroadcastSound(1018, (int)this.posX, (int)this.posY, (int)this.posZ, 0);
/* 470:    */       }
/* 471:    */     }
/* 472:634 */     moveEntity(0.0D, 0.1000000014901161D, 0.0D);
/* 473:635 */     this.renderYawOffset = (this.rotationYaw += 20.0F);
/* 474:637 */     if ((this.deathTicks == 200) && (!this.worldObj.isClient))
/* 475:    */     {
/* 476:639 */       int var4 = 2000;
/* 477:641 */       while (var4 > 0)
/* 478:    */       {
/* 479:643 */         int var5 = EntityXPOrb.getXPSplit(var4);
/* 480:644 */         var4 -= var5;
/* 481:645 */         this.worldObj.spawnEntityInWorld(new EntityXPOrb(this.worldObj, this.posX, this.posY, this.posZ, var5));
/* 482:    */       }
/* 483:648 */       createEnderPortal(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posZ));
/* 484:649 */       setDead();
/* 485:    */     }
/* 486:    */   }
/* 487:    */   
/* 488:    */   private void createEnderPortal(int par1, int par2)
/* 489:    */   {
/* 490:658 */     byte var3 = 64;
/* 491:659 */     net.minecraft.block.BlockEndPortal.field_149948_a = true;
/* 492:660 */     byte var4 = 4;
/* 493:662 */     for (int var5 = var3 - 1; var5 <= var3 + 32; var5++) {
/* 494:664 */       for (int var6 = par1 - var4; var6 <= par1 + var4; var6++) {
/* 495:666 */         for (int var7 = par2 - var4; var7 <= par2 + var4; var7++)
/* 496:    */         {
/* 497:668 */           double var8 = var6 - par1;
/* 498:669 */           double var10 = var7 - par2;
/* 499:670 */           double var12 = var8 * var8 + var10 * var10;
/* 500:672 */           if (var12 <= (var4 - 0.5D) * (var4 - 0.5D)) {
/* 501:674 */             if (var5 < var3)
/* 502:    */             {
/* 503:676 */               if (var12 <= (var4 - 1 - 0.5D) * (var4 - 1 - 0.5D)) {
/* 504:678 */                 this.worldObj.setBlock(var6, var5, var7, Blocks.bedrock);
/* 505:    */               }
/* 506:    */             }
/* 507:681 */             else if (var5 > var3) {
/* 508:683 */               this.worldObj.setBlock(var6, var5, var7, Blocks.air);
/* 509:685 */             } else if (var12 > (var4 - 1 - 0.5D) * (var4 - 1 - 0.5D)) {
/* 510:687 */               this.worldObj.setBlock(var6, var5, var7, Blocks.bedrock);
/* 511:    */             } else {
/* 512:691 */               this.worldObj.setBlock(var6, var5, var7, Blocks.end_portal);
/* 513:    */             }
/* 514:    */           }
/* 515:    */         }
/* 516:    */       }
/* 517:    */     }
/* 518:698 */     this.worldObj.setBlock(par1, var3 + 0, par2, Blocks.bedrock);
/* 519:699 */     this.worldObj.setBlock(par1, var3 + 1, par2, Blocks.bedrock);
/* 520:700 */     this.worldObj.setBlock(par1, var3 + 2, par2, Blocks.bedrock);
/* 521:701 */     this.worldObj.setBlock(par1 - 1, var3 + 2, par2, Blocks.torch);
/* 522:702 */     this.worldObj.setBlock(par1 + 1, var3 + 2, par2, Blocks.torch);
/* 523:703 */     this.worldObj.setBlock(par1, var3 + 2, par2 - 1, Blocks.torch);
/* 524:704 */     this.worldObj.setBlock(par1, var3 + 2, par2 + 1, Blocks.torch);
/* 525:705 */     this.worldObj.setBlock(par1, var3 + 3, par2, Blocks.bedrock);
/* 526:706 */     this.worldObj.setBlock(par1, var3 + 4, par2, Blocks.dragon_egg);
/* 527:707 */     net.minecraft.block.BlockEndPortal.field_149948_a = false;
/* 528:    */   }
/* 529:    */   
/* 530:    */   public void despawnEntity() {}
/* 531:    */   
/* 532:    */   public Entity[] getParts()
/* 533:    */   {
/* 534:720 */     return this.dragonPartArray;
/* 535:    */   }
/* 536:    */   
/* 537:    */   public boolean canBeCollidedWith()
/* 538:    */   {
/* 539:728 */     return false;
/* 540:    */   }
/* 541:    */   
/* 542:    */   public World func_82194_d()
/* 543:    */   {
/* 544:733 */     return this.worldObj;
/* 545:    */   }
/* 546:    */   
/* 547:    */   protected String getLivingSound()
/* 548:    */   {
/* 549:741 */     return "mob.enderdragon.growl";
/* 550:    */   }
/* 551:    */   
/* 552:    */   protected String getHurtSound()
/* 553:    */   {
/* 554:749 */     return "mob.enderdragon.hit";
/* 555:    */   }
/* 556:    */   
/* 557:    */   protected float getSoundVolume()
/* 558:    */   {
/* 559:757 */     return 5.0F;
/* 560:    */   }
/* 561:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.entity.boss.EntityDragon
 * JD-Core Version:    0.7.0.1
 */