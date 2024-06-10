/*   1:    */ package net.minecraft.entity.projectile;
/*   2:    */ 
/*   3:    */ import java.util.List;
/*   4:    */ import java.util.Random;
/*   5:    */ import net.minecraft.block.Block;
/*   6:    */ import net.minecraft.entity.Entity;
/*   7:    */ import net.minecraft.entity.EntityLivingBase;
/*   8:    */ import net.minecraft.entity.IProjectile;
/*   9:    */ import net.minecraft.entity.player.EntityPlayer;
/*  10:    */ import net.minecraft.init.Blocks;
/*  11:    */ import net.minecraft.nbt.NBTTagCompound;
/*  12:    */ import net.minecraft.util.AxisAlignedBB;
/*  13:    */ import net.minecraft.util.MathHelper;
/*  14:    */ import net.minecraft.util.MovingObjectPosition;
/*  15:    */ import net.minecraft.util.MovingObjectPosition.MovingObjectType;
/*  16:    */ import net.minecraft.util.Vec3;
/*  17:    */ import net.minecraft.util.Vec3Pool;
/*  18:    */ import net.minecraft.world.World;
/*  19:    */ 
/*  20:    */ public abstract class EntityThrowable
/*  21:    */   extends Entity
/*  22:    */   implements IProjectile
/*  23:    */ {
/*  24: 19 */   private int field_145788_c = -1;
/*  25: 20 */   private int field_145786_d = -1;
/*  26: 21 */   private int field_145787_e = -1;
/*  27:    */   private Block field_145785_f;
/*  28:    */   protected boolean inGround;
/*  29:    */   public int throwableShake;
/*  30:    */   private EntityLivingBase thrower;
/*  31:    */   private String throwerName;
/*  32:    */   private int ticksInGround;
/*  33:    */   private int ticksInAir;
/*  34:    */   private static final String __OBFID = "CL_00001723";
/*  35:    */   
/*  36:    */   public EntityThrowable(World par1World)
/*  37:    */   {
/*  38: 35 */     super(par1World);
/*  39: 36 */     setSize(0.25F, 0.25F);
/*  40:    */   }
/*  41:    */   
/*  42:    */   protected void entityInit() {}
/*  43:    */   
/*  44:    */   public boolean isInRangeToRenderDist(double par1)
/*  45:    */   {
/*  46: 47 */     double var3 = this.boundingBox.getAverageEdgeLength() * 4.0D;
/*  47: 48 */     var3 *= 64.0D;
/*  48: 49 */     return par1 < var3 * var3;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public EntityThrowable(World par1World, EntityLivingBase par2EntityLivingBase)
/*  52:    */   {
/*  53: 54 */     super(par1World);
/*  54: 55 */     this.thrower = par2EntityLivingBase;
/*  55: 56 */     setSize(0.25F, 0.25F);
/*  56: 57 */     setLocationAndAngles(par2EntityLivingBase.posX, par2EntityLivingBase.posY + par2EntityLivingBase.getEyeHeight(), par2EntityLivingBase.posZ, par2EntityLivingBase.rotationYaw, par2EntityLivingBase.rotationPitch);
/*  57: 58 */     this.posX -= MathHelper.cos(this.rotationYaw / 180.0F * 3.141593F) * 0.16F;
/*  58: 59 */     this.posY -= 0.1000000014901161D;
/*  59: 60 */     this.posZ -= MathHelper.sin(this.rotationYaw / 180.0F * 3.141593F) * 0.16F;
/*  60: 61 */     setPosition(this.posX, this.posY, this.posZ);
/*  61: 62 */     this.yOffset = 0.0F;
/*  62: 63 */     float var3 = 0.4F;
/*  63: 64 */     this.motionX = (-MathHelper.sin(this.rotationYaw / 180.0F * 3.141593F) * MathHelper.cos(this.rotationPitch / 180.0F * 3.141593F) * var3);
/*  64: 65 */     this.motionZ = (MathHelper.cos(this.rotationYaw / 180.0F * 3.141593F) * MathHelper.cos(this.rotationPitch / 180.0F * 3.141593F) * var3);
/*  65: 66 */     this.motionY = (-MathHelper.sin((this.rotationPitch + func_70183_g()) / 180.0F * 3.141593F) * var3);
/*  66: 67 */     setThrowableHeading(this.motionX, this.motionY, this.motionZ, func_70182_d(), 1.0F);
/*  67:    */   }
/*  68:    */   
/*  69:    */   public EntityThrowable(World par1World, double par2, double par4, double par6)
/*  70:    */   {
/*  71: 72 */     super(par1World);
/*  72: 73 */     this.ticksInGround = 0;
/*  73: 74 */     setSize(0.25F, 0.25F);
/*  74: 75 */     setPosition(par2, par4, par6);
/*  75: 76 */     this.yOffset = 0.0F;
/*  76:    */   }
/*  77:    */   
/*  78:    */   protected float func_70182_d()
/*  79:    */   {
/*  80: 81 */     return 1.5F;
/*  81:    */   }
/*  82:    */   
/*  83:    */   protected float func_70183_g()
/*  84:    */   {
/*  85: 86 */     return 0.0F;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public void setThrowableHeading(double par1, double par3, double par5, float par7, float par8)
/*  89:    */   {
/*  90: 94 */     float var9 = MathHelper.sqrt_double(par1 * par1 + par3 * par3 + par5 * par5);
/*  91: 95 */     par1 /= var9;
/*  92: 96 */     par3 /= var9;
/*  93: 97 */     par5 /= var9;
/*  94: 98 */     par1 += this.rand.nextGaussian() * 0.007499999832361937D * par8;
/*  95: 99 */     par3 += this.rand.nextGaussian() * 0.007499999832361937D * par8;
/*  96:100 */     par5 += this.rand.nextGaussian() * 0.007499999832361937D * par8;
/*  97:101 */     par1 *= par7;
/*  98:102 */     par3 *= par7;
/*  99:103 */     par5 *= par7;
/* 100:104 */     this.motionX = par1;
/* 101:105 */     this.motionY = par3;
/* 102:106 */     this.motionZ = par5;
/* 103:107 */     float var10 = MathHelper.sqrt_double(par1 * par1 + par5 * par5);
/* 104:108 */     this.prevRotationYaw = (this.rotationYaw = (float)(Math.atan2(par1, par5) * 180.0D / 3.141592653589793D));
/* 105:109 */     this.prevRotationPitch = (this.rotationPitch = (float)(Math.atan2(par3, var10) * 180.0D / 3.141592653589793D));
/* 106:110 */     this.ticksInGround = 0;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public void setVelocity(double par1, double par3, double par5)
/* 110:    */   {
/* 111:118 */     this.motionX = par1;
/* 112:119 */     this.motionY = par3;
/* 113:120 */     this.motionZ = par5;
/* 114:122 */     if ((this.prevRotationPitch == 0.0F) && (this.prevRotationYaw == 0.0F))
/* 115:    */     {
/* 116:124 */       float var7 = MathHelper.sqrt_double(par1 * par1 + par5 * par5);
/* 117:125 */       this.prevRotationYaw = (this.rotationYaw = (float)(Math.atan2(par1, par5) * 180.0D / 3.141592653589793D));
/* 118:126 */       this.prevRotationPitch = (this.rotationPitch = (float)(Math.atan2(par3, var7) * 180.0D / 3.141592653589793D));
/* 119:    */     }
/* 120:    */   }
/* 121:    */   
/* 122:    */   public void onUpdate()
/* 123:    */   {
/* 124:135 */     this.lastTickPosX = this.posX;
/* 125:136 */     this.lastTickPosY = this.posY;
/* 126:137 */     this.lastTickPosZ = this.posZ;
/* 127:138 */     super.onUpdate();
/* 128:140 */     if (this.throwableShake > 0) {
/* 129:142 */       this.throwableShake -= 1;
/* 130:    */     }
/* 131:145 */     if (this.inGround)
/* 132:    */     {
/* 133:147 */       if (this.worldObj.getBlock(this.field_145788_c, this.field_145786_d, this.field_145787_e) == this.field_145785_f)
/* 134:    */       {
/* 135:149 */         this.ticksInGround += 1;
/* 136:151 */         if (this.ticksInGround == 1200) {
/* 137:153 */           setDead();
/* 138:    */         }
/* 139:156 */         return;
/* 140:    */       }
/* 141:159 */       this.inGround = false;
/* 142:160 */       this.motionX *= this.rand.nextFloat() * 0.2F;
/* 143:161 */       this.motionY *= this.rand.nextFloat() * 0.2F;
/* 144:162 */       this.motionZ *= this.rand.nextFloat() * 0.2F;
/* 145:163 */       this.ticksInGround = 0;
/* 146:164 */       this.ticksInAir = 0;
/* 147:    */     }
/* 148:    */     else
/* 149:    */     {
/* 150:168 */       this.ticksInAir += 1;
/* 151:    */     }
/* 152:171 */     Vec3 var1 = this.worldObj.getWorldVec3Pool().getVecFromPool(this.posX, this.posY, this.posZ);
/* 153:172 */     Vec3 var2 = this.worldObj.getWorldVec3Pool().getVecFromPool(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
/* 154:173 */     MovingObjectPosition var3 = this.worldObj.rayTraceBlocks(var1, var2);
/* 155:174 */     var1 = this.worldObj.getWorldVec3Pool().getVecFromPool(this.posX, this.posY, this.posZ);
/* 156:175 */     var2 = this.worldObj.getWorldVec3Pool().getVecFromPool(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
/* 157:177 */     if (var3 != null) {
/* 158:179 */       var2 = this.worldObj.getWorldVec3Pool().getVecFromPool(var3.hitVec.xCoord, var3.hitVec.yCoord, var3.hitVec.zCoord);
/* 159:    */     }
/* 160:182 */     if (!this.worldObj.isClient)
/* 161:    */     {
/* 162:184 */       Entity var4 = null;
/* 163:185 */       List var5 = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.addCoord(this.motionX, this.motionY, this.motionZ).expand(1.0D, 1.0D, 1.0D));
/* 164:186 */       double var6 = 0.0D;
/* 165:187 */       EntityLivingBase var8 = getThrower();
/* 166:189 */       for (int var9 = 0; var9 < var5.size(); var9++)
/* 167:    */       {
/* 168:191 */         Entity var10 = (Entity)var5.get(var9);
/* 169:193 */         if ((var10.canBeCollidedWith()) && ((var10 != var8) || (this.ticksInAir >= 5)))
/* 170:    */         {
/* 171:195 */           float var11 = 0.3F;
/* 172:196 */           AxisAlignedBB var12 = var10.boundingBox.expand(var11, var11, var11);
/* 173:197 */           MovingObjectPosition var13 = var12.calculateIntercept(var1, var2);
/* 174:199 */           if (var13 != null)
/* 175:    */           {
/* 176:201 */             double var14 = var1.distanceTo(var13.hitVec);
/* 177:203 */             if ((var14 < var6) || (var6 == 0.0D))
/* 178:    */             {
/* 179:205 */               var4 = var10;
/* 180:206 */               var6 = var14;
/* 181:    */             }
/* 182:    */           }
/* 183:    */         }
/* 184:    */       }
/* 185:212 */       if (var4 != null) {
/* 186:214 */         var3 = new MovingObjectPosition(var4);
/* 187:    */       }
/* 188:    */     }
/* 189:218 */     if (var3 != null) {
/* 190:220 */       if ((var3.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) && (this.worldObj.getBlock(var3.blockX, var3.blockY, var3.blockZ) == Blocks.portal)) {
/* 191:222 */         setInPortal();
/* 192:    */       } else {
/* 193:226 */         onImpact(var3);
/* 194:    */       }
/* 195:    */     }
/* 196:230 */     this.posX += this.motionX;
/* 197:231 */     this.posY += this.motionY;
/* 198:232 */     this.posZ += this.motionZ;
/* 199:233 */     float var16 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
/* 200:234 */     this.rotationYaw = ((float)(Math.atan2(this.motionX, this.motionZ) * 180.0D / 3.141592653589793D));
/* 201:236 */     for (this.rotationPitch = ((float)(Math.atan2(this.motionY, var16) * 180.0D / 3.141592653589793D)); this.rotationPitch - this.prevRotationPitch < -180.0F; this.prevRotationPitch -= 360.0F) {}
/* 202:241 */     while (this.rotationPitch - this.prevRotationPitch >= 180.0F) {
/* 203:243 */       this.prevRotationPitch += 360.0F;
/* 204:    */     }
/* 205:246 */     while (this.rotationYaw - this.prevRotationYaw < -180.0F) {
/* 206:248 */       this.prevRotationYaw -= 360.0F;
/* 207:    */     }
/* 208:251 */     while (this.rotationYaw - this.prevRotationYaw >= 180.0F) {
/* 209:253 */       this.prevRotationYaw += 360.0F;
/* 210:    */     }
/* 211:256 */     this.rotationPitch = (this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * 0.2F);
/* 212:257 */     this.rotationYaw = (this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * 0.2F);
/* 213:258 */     float var17 = 0.99F;
/* 214:259 */     float var18 = getGravityVelocity();
/* 215:261 */     if (isInWater())
/* 216:    */     {
/* 217:263 */       for (int var7 = 0; var7 < 4; var7++)
/* 218:    */       {
/* 219:265 */         float var19 = 0.25F;
/* 220:266 */         this.worldObj.spawnParticle("bubble", this.posX - this.motionX * var19, this.posY - this.motionY * var19, this.posZ - this.motionZ * var19, this.motionX, this.motionY, this.motionZ);
/* 221:    */       }
/* 222:269 */       var17 = 0.8F;
/* 223:    */     }
/* 224:272 */     this.motionX *= var17;
/* 225:273 */     this.motionY *= var17;
/* 226:274 */     this.motionZ *= var17;
/* 227:275 */     this.motionY -= var18;
/* 228:276 */     setPosition(this.posX, this.posY, this.posZ);
/* 229:    */   }
/* 230:    */   
/* 231:    */   protected float getGravityVelocity()
/* 232:    */   {
/* 233:284 */     return 0.03F;
/* 234:    */   }
/* 235:    */   
/* 236:    */   protected abstract void onImpact(MovingObjectPosition paramMovingObjectPosition);
/* 237:    */   
/* 238:    */   public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
/* 239:    */   {
/* 240:297 */     par1NBTTagCompound.setShort("xTile", (short)this.field_145788_c);
/* 241:298 */     par1NBTTagCompound.setShort("yTile", (short)this.field_145786_d);
/* 242:299 */     par1NBTTagCompound.setShort("zTile", (short)this.field_145787_e);
/* 243:300 */     par1NBTTagCompound.setByte("inTile", (byte)Block.getIdFromBlock(this.field_145785_f));
/* 244:301 */     par1NBTTagCompound.setByte("shake", (byte)this.throwableShake);
/* 245:302 */     par1NBTTagCompound.setByte("inGround", (byte)(this.inGround ? 1 : 0));
/* 246:304 */     if (((this.throwerName == null) || (this.throwerName.length() == 0)) && (this.thrower != null) && ((this.thrower instanceof EntityPlayer))) {
/* 247:306 */       this.throwerName = this.thrower.getCommandSenderName();
/* 248:    */     }
/* 249:309 */     par1NBTTagCompound.setString("ownerName", this.throwerName == null ? "" : this.throwerName);
/* 250:    */   }
/* 251:    */   
/* 252:    */   public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
/* 253:    */   {
/* 254:317 */     this.field_145788_c = par1NBTTagCompound.getShort("xTile");
/* 255:318 */     this.field_145786_d = par1NBTTagCompound.getShort("yTile");
/* 256:319 */     this.field_145787_e = par1NBTTagCompound.getShort("zTile");
/* 257:320 */     this.field_145785_f = Block.getBlockById(par1NBTTagCompound.getByte("inTile") & 0xFF);
/* 258:321 */     this.throwableShake = (par1NBTTagCompound.getByte("shake") & 0xFF);
/* 259:322 */     this.inGround = (par1NBTTagCompound.getByte("inGround") == 1);
/* 260:323 */     this.throwerName = par1NBTTagCompound.getString("ownerName");
/* 261:325 */     if ((this.throwerName != null) && (this.throwerName.length() == 0)) {
/* 262:327 */       this.throwerName = null;
/* 263:    */     }
/* 264:    */   }
/* 265:    */   
/* 266:    */   public float getShadowSize()
/* 267:    */   {
/* 268:333 */     return 0.0F;
/* 269:    */   }
/* 270:    */   
/* 271:    */   public EntityLivingBase getThrower()
/* 272:    */   {
/* 273:338 */     if ((this.thrower == null) && (this.throwerName != null) && (this.throwerName.length() > 0)) {
/* 274:340 */       this.thrower = this.worldObj.getPlayerEntityByName(this.throwerName);
/* 275:    */     }
/* 276:343 */     return this.thrower;
/* 277:    */   }
/* 278:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.entity.projectile.EntityThrowable
 * JD-Core Version:    0.7.0.1
 */