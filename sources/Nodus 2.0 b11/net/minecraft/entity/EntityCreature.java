/*   1:    */ package net.minecraft.entity;
/*   2:    */ 
/*   3:    */ import java.util.Random;
/*   4:    */ import java.util.UUID;
/*   5:    */ import net.minecraft.entity.ai.EntityAIBase;
/*   6:    */ import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
/*   7:    */ import net.minecraft.entity.ai.EntityAITasks;
/*   8:    */ import net.minecraft.entity.ai.attributes.AttributeModifier;
/*   9:    */ import net.minecraft.entity.ai.attributes.IAttributeInstance;
/*  10:    */ import net.minecraft.entity.passive.EntityTameable;
/*  11:    */ import net.minecraft.entity.player.EntityPlayerMP;
/*  12:    */ import net.minecraft.pathfinding.PathEntity;
/*  13:    */ import net.minecraft.pathfinding.PathNavigate;
/*  14:    */ import net.minecraft.profiler.Profiler;
/*  15:    */ import net.minecraft.server.management.ItemInWorldManager;
/*  16:    */ import net.minecraft.util.AxisAlignedBB;
/*  17:    */ import net.minecraft.util.ChunkCoordinates;
/*  18:    */ import net.minecraft.util.MathHelper;
/*  19:    */ import net.minecraft.util.Vec3;
/*  20:    */ import net.minecraft.world.World;
/*  21:    */ 
/*  22:    */ public abstract class EntityCreature
/*  23:    */   extends EntityLiving
/*  24:    */ {
/*  25: 18 */   public static final UUID field_110179_h = UUID.fromString("E199AD21-BA8A-4C53-8D13-6182D5C69D3A");
/*  26: 19 */   public static final AttributeModifier field_110181_i = new AttributeModifier(field_110179_h, "Fleeing speed bonus", 2.0D, 2).setSaved(false);
/*  27:    */   private PathEntity pathToEntity;
/*  28:    */   protected Entity entityToAttack;
/*  29:    */   protected boolean hasAttacked;
/*  30:    */   protected int fleeingTick;
/*  31: 32 */   private ChunkCoordinates homePosition = new ChunkCoordinates(0, 0, 0);
/*  32: 35 */   private float maximumHomeDistance = -1.0F;
/*  33: 36 */   private EntityAIBase field_110178_bs = new EntityAIMoveTowardsRestriction(this, 1.0D);
/*  34:    */   private boolean field_110180_bt;
/*  35:    */   private static final String __OBFID = "CL_00001558";
/*  36:    */   
/*  37:    */   public EntityCreature(World par1World)
/*  38:    */   {
/*  39: 42 */     super(par1World);
/*  40:    */   }
/*  41:    */   
/*  42:    */   protected boolean isMovementCeased()
/*  43:    */   {
/*  44: 50 */     return false;
/*  45:    */   }
/*  46:    */   
/*  47:    */   protected void updateEntityActionState()
/*  48:    */   {
/*  49: 55 */     this.worldObj.theProfiler.startSection("ai");
/*  50: 57 */     if ((this.fleeingTick > 0) && (--this.fleeingTick == 0))
/*  51:    */     {
/*  52: 59 */       IAttributeInstance var1 = getEntityAttribute(SharedMonsterAttributes.movementSpeed);
/*  53: 60 */       var1.removeModifier(field_110181_i);
/*  54:    */     }
/*  55: 63 */     this.hasAttacked = isMovementCeased();
/*  56: 64 */     float var21 = 16.0F;
/*  57: 66 */     if (this.entityToAttack == null)
/*  58:    */     {
/*  59: 68 */       this.entityToAttack = findPlayerToAttack();
/*  60: 70 */       if (this.entityToAttack != null) {
/*  61: 72 */         this.pathToEntity = this.worldObj.getPathEntityToEntity(this, this.entityToAttack, var21, true, false, false, true);
/*  62:    */       }
/*  63:    */     }
/*  64: 75 */     else if (this.entityToAttack.isEntityAlive())
/*  65:    */     {
/*  66: 77 */       float var2 = this.entityToAttack.getDistanceToEntity(this);
/*  67: 79 */       if (canEntityBeSeen(this.entityToAttack)) {
/*  68: 81 */         attackEntity(this.entityToAttack, var2);
/*  69:    */       }
/*  70:    */     }
/*  71:    */     else
/*  72:    */     {
/*  73: 86 */       this.entityToAttack = null;
/*  74:    */     }
/*  75: 89 */     if (((this.entityToAttack instanceof EntityPlayerMP)) && (((EntityPlayerMP)this.entityToAttack).theItemInWorldManager.isCreative())) {
/*  76: 91 */       this.entityToAttack = null;
/*  77:    */     }
/*  78: 94 */     this.worldObj.theProfiler.endSection();
/*  79: 96 */     if ((!this.hasAttacked) && (this.entityToAttack != null) && ((this.pathToEntity == null) || (this.rand.nextInt(20) == 0))) {
/*  80: 98 */       this.pathToEntity = this.worldObj.getPathEntityToEntity(this, this.entityToAttack, var21, true, false, false, true);
/*  81:100 */     } else if ((!this.hasAttacked) && (((this.pathToEntity == null) && (this.rand.nextInt(180) == 0)) || (((this.rand.nextInt(120) == 0) || (this.fleeingTick > 0)) && (this.entityAge < 100)))) {
/*  82:102 */       updateWanderPath();
/*  83:    */     }
/*  84:105 */     int var22 = MathHelper.floor_double(this.boundingBox.minY + 0.5D);
/*  85:106 */     boolean var3 = isInWater();
/*  86:107 */     boolean var4 = handleLavaMovement();
/*  87:108 */     this.rotationPitch = 0.0F;
/*  88:110 */     if ((this.pathToEntity != null) && (this.rand.nextInt(100) != 0))
/*  89:    */     {
/*  90:112 */       this.worldObj.theProfiler.startSection("followpath");
/*  91:113 */       Vec3 var5 = this.pathToEntity.getPosition(this);
/*  92:114 */       double var6 = this.width * 2.0F;
/*  93:116 */       while ((var5 != null) && (var5.squareDistanceTo(this.posX, var5.yCoord, this.posZ) < var6 * var6))
/*  94:    */       {
/*  95:118 */         this.pathToEntity.incrementPathIndex();
/*  96:120 */         if (this.pathToEntity.isFinished())
/*  97:    */         {
/*  98:122 */           var5 = null;
/*  99:123 */           this.pathToEntity = null;
/* 100:    */         }
/* 101:    */         else
/* 102:    */         {
/* 103:127 */           var5 = this.pathToEntity.getPosition(this);
/* 104:    */         }
/* 105:    */       }
/* 106:131 */       this.isJumping = false;
/* 107:133 */       if (var5 != null)
/* 108:    */       {
/* 109:135 */         double var8 = var5.xCoord - this.posX;
/* 110:136 */         double var10 = var5.zCoord - this.posZ;
/* 111:137 */         double var12 = var5.yCoord - var22;
/* 112:138 */         float var14 = (float)(Math.atan2(var10, var8) * 180.0D / 3.141592653589793D) - 90.0F;
/* 113:139 */         float var15 = MathHelper.wrapAngleTo180_float(var14 - this.rotationYaw);
/* 114:140 */         this.moveForward = ((float)getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue());
/* 115:142 */         if (var15 > 30.0F) {
/* 116:144 */           var15 = 30.0F;
/* 117:    */         }
/* 118:147 */         if (var15 < -30.0F) {
/* 119:149 */           var15 = -30.0F;
/* 120:    */         }
/* 121:152 */         this.rotationYaw += var15;
/* 122:154 */         if ((this.hasAttacked) && (this.entityToAttack != null))
/* 123:    */         {
/* 124:156 */           double var16 = this.entityToAttack.posX - this.posX;
/* 125:157 */           double var18 = this.entityToAttack.posZ - this.posZ;
/* 126:158 */           float var20 = this.rotationYaw;
/* 127:159 */           this.rotationYaw = ((float)(Math.atan2(var18, var16) * 180.0D / 3.141592653589793D) - 90.0F);
/* 128:160 */           var15 = (var20 - this.rotationYaw + 90.0F) * 3.141593F / 180.0F;
/* 129:161 */           this.moveStrafing = (-MathHelper.sin(var15) * this.moveForward * 1.0F);
/* 130:162 */           this.moveForward = (MathHelper.cos(var15) * this.moveForward * 1.0F);
/* 131:    */         }
/* 132:165 */         if (var12 > 0.0D) {
/* 133:167 */           this.isJumping = true;
/* 134:    */         }
/* 135:    */       }
/* 136:171 */       if (this.entityToAttack != null) {
/* 137:173 */         faceEntity(this.entityToAttack, 30.0F, 30.0F);
/* 138:    */       }
/* 139:176 */       if ((this.isCollidedHorizontally) && (!hasPath())) {
/* 140:178 */         this.isJumping = true;
/* 141:    */       }
/* 142:181 */       if ((this.rand.nextFloat() < 0.8F) && ((var3) || (var4))) {
/* 143:183 */         this.isJumping = true;
/* 144:    */       }
/* 145:186 */       this.worldObj.theProfiler.endSection();
/* 146:    */     }
/* 147:    */     else
/* 148:    */     {
/* 149:190 */       super.updateEntityActionState();
/* 150:191 */       this.pathToEntity = null;
/* 151:    */     }
/* 152:    */   }
/* 153:    */   
/* 154:    */   protected void updateWanderPath()
/* 155:    */   {
/* 156:200 */     this.worldObj.theProfiler.startSection("stroll");
/* 157:201 */     boolean var1 = false;
/* 158:202 */     int var2 = -1;
/* 159:203 */     int var3 = -1;
/* 160:204 */     int var4 = -1;
/* 161:205 */     float var5 = -99999.0F;
/* 162:207 */     for (int var6 = 0; var6 < 10; var6++)
/* 163:    */     {
/* 164:209 */       int var7 = MathHelper.floor_double(this.posX + this.rand.nextInt(13) - 6.0D);
/* 165:210 */       int var8 = MathHelper.floor_double(this.posY + this.rand.nextInt(7) - 3.0D);
/* 166:211 */       int var9 = MathHelper.floor_double(this.posZ + this.rand.nextInt(13) - 6.0D);
/* 167:212 */       float var10 = getBlockPathWeight(var7, var8, var9);
/* 168:214 */       if (var10 > var5)
/* 169:    */       {
/* 170:216 */         var5 = var10;
/* 171:217 */         var2 = var7;
/* 172:218 */         var3 = var8;
/* 173:219 */         var4 = var9;
/* 174:220 */         var1 = true;
/* 175:    */       }
/* 176:    */     }
/* 177:224 */     if (var1) {
/* 178:226 */       this.pathToEntity = this.worldObj.getEntityPathToXYZ(this, var2, var3, var4, 10.0F, true, false, false, true);
/* 179:    */     }
/* 180:229 */     this.worldObj.theProfiler.endSection();
/* 181:    */   }
/* 182:    */   
/* 183:    */   protected void attackEntity(Entity par1Entity, float par2) {}
/* 184:    */   
/* 185:    */   public float getBlockPathWeight(int par1, int par2, int par3)
/* 186:    */   {
/* 187:243 */     return 0.0F;
/* 188:    */   }
/* 189:    */   
/* 190:    */   protected Entity findPlayerToAttack()
/* 191:    */   {
/* 192:252 */     return null;
/* 193:    */   }
/* 194:    */   
/* 195:    */   public boolean getCanSpawnHere()
/* 196:    */   {
/* 197:260 */     int var1 = MathHelper.floor_double(this.posX);
/* 198:261 */     int var2 = MathHelper.floor_double(this.boundingBox.minY);
/* 199:262 */     int var3 = MathHelper.floor_double(this.posZ);
/* 200:263 */     return (super.getCanSpawnHere()) && (getBlockPathWeight(var1, var2, var3) >= 0.0F);
/* 201:    */   }
/* 202:    */   
/* 203:    */   public boolean hasPath()
/* 204:    */   {
/* 205:271 */     return this.pathToEntity != null;
/* 206:    */   }
/* 207:    */   
/* 208:    */   public void setPathToEntity(PathEntity par1PathEntity)
/* 209:    */   {
/* 210:279 */     this.pathToEntity = par1PathEntity;
/* 211:    */   }
/* 212:    */   
/* 213:    */   public Entity getEntityToAttack()
/* 214:    */   {
/* 215:287 */     return this.entityToAttack;
/* 216:    */   }
/* 217:    */   
/* 218:    */   public void setTarget(Entity par1Entity)
/* 219:    */   {
/* 220:295 */     this.entityToAttack = par1Entity;
/* 221:    */   }
/* 222:    */   
/* 223:    */   public boolean isWithinHomeDistanceCurrentPosition()
/* 224:    */   {
/* 225:300 */     return isWithinHomeDistance(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ));
/* 226:    */   }
/* 227:    */   
/* 228:    */   public boolean isWithinHomeDistance(int par1, int par2, int par3)
/* 229:    */   {
/* 230:305 */     return this.maximumHomeDistance == -1.0F;
/* 231:    */   }
/* 232:    */   
/* 233:    */   public void setHomeArea(int par1, int par2, int par3, int par4)
/* 234:    */   {
/* 235:310 */     this.homePosition.set(par1, par2, par3);
/* 236:311 */     this.maximumHomeDistance = par4;
/* 237:    */   }
/* 238:    */   
/* 239:    */   public ChunkCoordinates getHomePosition()
/* 240:    */   {
/* 241:319 */     return this.homePosition;
/* 242:    */   }
/* 243:    */   
/* 244:    */   public float func_110174_bM()
/* 245:    */   {
/* 246:324 */     return this.maximumHomeDistance;
/* 247:    */   }
/* 248:    */   
/* 249:    */   public void detachHome()
/* 250:    */   {
/* 251:329 */     this.maximumHomeDistance = -1.0F;
/* 252:    */   }
/* 253:    */   
/* 254:    */   public boolean hasHome()
/* 255:    */   {
/* 256:337 */     return this.maximumHomeDistance != -1.0F;
/* 257:    */   }
/* 258:    */   
/* 259:    */   protected void updateLeashedState()
/* 260:    */   {
/* 261:345 */     super.updateLeashedState();
/* 262:347 */     if ((getLeashed()) && (getLeashedToEntity() != null) && (getLeashedToEntity().worldObj == this.worldObj))
/* 263:    */     {
/* 264:349 */       Entity var1 = getLeashedToEntity();
/* 265:350 */       setHomeArea((int)var1.posX, (int)var1.posY, (int)var1.posZ, 5);
/* 266:351 */       float var2 = getDistanceToEntity(var1);
/* 267:353 */       if (((this instanceof EntityTameable)) && (((EntityTameable)this).isSitting()))
/* 268:    */       {
/* 269:355 */         if (var2 > 10.0F) {
/* 270:357 */           clearLeashed(true, true);
/* 271:    */         }
/* 272:360 */         return;
/* 273:    */       }
/* 274:363 */       if (!this.field_110180_bt)
/* 275:    */       {
/* 276:365 */         this.tasks.addTask(2, this.field_110178_bs);
/* 277:366 */         getNavigator().setAvoidsWater(false);
/* 278:367 */         this.field_110180_bt = true;
/* 279:    */       }
/* 280:370 */       func_142017_o(var2);
/* 281:372 */       if (var2 > 4.0F) {
/* 282:374 */         getNavigator().tryMoveToEntityLiving(var1, 1.0D);
/* 283:    */       }
/* 284:377 */       if (var2 > 6.0F)
/* 285:    */       {
/* 286:379 */         double var3 = (var1.posX - this.posX) / var2;
/* 287:380 */         double var5 = (var1.posY - this.posY) / var2;
/* 288:381 */         double var7 = (var1.posZ - this.posZ) / var2;
/* 289:382 */         this.motionX += var3 * Math.abs(var3) * 0.4D;
/* 290:383 */         this.motionY += var5 * Math.abs(var5) * 0.4D;
/* 291:384 */         this.motionZ += var7 * Math.abs(var7) * 0.4D;
/* 292:    */       }
/* 293:387 */       if (var2 > 10.0F) {
/* 294:389 */         clearLeashed(true, true);
/* 295:    */       }
/* 296:    */     }
/* 297:392 */     else if ((!getLeashed()) && (this.field_110180_bt))
/* 298:    */     {
/* 299:394 */       this.field_110180_bt = false;
/* 300:395 */       this.tasks.removeTask(this.field_110178_bs);
/* 301:396 */       getNavigator().setAvoidsWater(true);
/* 302:397 */       detachHome();
/* 303:    */     }
/* 304:    */   }
/* 305:    */   
/* 306:    */   protected void func_142017_o(float par1) {}
/* 307:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.entity.EntityCreature
 * JD-Core Version:    0.7.0.1
 */