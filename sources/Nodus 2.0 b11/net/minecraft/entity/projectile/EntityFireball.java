/*   1:    */ package net.minecraft.entity.projectile;
/*   2:    */ 
/*   3:    */ import java.util.List;
/*   4:    */ import java.util.Random;
/*   5:    */ import net.minecraft.block.Block;
/*   6:    */ import net.minecraft.entity.Entity;
/*   7:    */ import net.minecraft.entity.EntityLivingBase;
/*   8:    */ import net.minecraft.nbt.NBTTagCompound;
/*   9:    */ import net.minecraft.nbt.NBTTagList;
/*  10:    */ import net.minecraft.util.AxisAlignedBB;
/*  11:    */ import net.minecraft.util.DamageSource;
/*  12:    */ import net.minecraft.util.MathHelper;
/*  13:    */ import net.minecraft.util.MovingObjectPosition;
/*  14:    */ import net.minecraft.util.Vec3;
/*  15:    */ import net.minecraft.util.Vec3Pool;
/*  16:    */ import net.minecraft.world.World;
/*  17:    */ 
/*  18:    */ public abstract class EntityFireball
/*  19:    */   extends Entity
/*  20:    */ {
/*  21: 18 */   private int field_145795_e = -1;
/*  22: 19 */   private int field_145793_f = -1;
/*  23: 20 */   private int field_145794_g = -1;
/*  24:    */   private Block field_145796_h;
/*  25:    */   private boolean inGround;
/*  26:    */   public EntityLivingBase shootingEntity;
/*  27:    */   private int ticksAlive;
/*  28:    */   private int ticksInAir;
/*  29:    */   public double accelerationX;
/*  30:    */   public double accelerationY;
/*  31:    */   public double accelerationZ;
/*  32:    */   private static final String __OBFID = "CL_00001717";
/*  33:    */   
/*  34:    */   public EntityFireball(World par1World)
/*  35:    */   {
/*  36: 33 */     super(par1World);
/*  37: 34 */     setSize(1.0F, 1.0F);
/*  38:    */   }
/*  39:    */   
/*  40:    */   protected void entityInit() {}
/*  41:    */   
/*  42:    */   public boolean isInRangeToRenderDist(double par1)
/*  43:    */   {
/*  44: 45 */     double var3 = this.boundingBox.getAverageEdgeLength() * 4.0D;
/*  45: 46 */     var3 *= 64.0D;
/*  46: 47 */     return par1 < var3 * var3;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public EntityFireball(World par1World, double par2, double par4, double par6, double par8, double par10, double par12)
/*  50:    */   {
/*  51: 52 */     super(par1World);
/*  52: 53 */     setSize(1.0F, 1.0F);
/*  53: 54 */     setLocationAndAngles(par2, par4, par6, this.rotationYaw, this.rotationPitch);
/*  54: 55 */     setPosition(par2, par4, par6);
/*  55: 56 */     double var14 = MathHelper.sqrt_double(par8 * par8 + par10 * par10 + par12 * par12);
/*  56: 57 */     this.accelerationX = (par8 / var14 * 0.1D);
/*  57: 58 */     this.accelerationY = (par10 / var14 * 0.1D);
/*  58: 59 */     this.accelerationZ = (par12 / var14 * 0.1D);
/*  59:    */   }
/*  60:    */   
/*  61:    */   public EntityFireball(World par1World, EntityLivingBase par2EntityLivingBase, double par3, double par5, double par7)
/*  62:    */   {
/*  63: 64 */     super(par1World);
/*  64: 65 */     this.shootingEntity = par2EntityLivingBase;
/*  65: 66 */     setSize(1.0F, 1.0F);
/*  66: 67 */     setLocationAndAngles(par2EntityLivingBase.posX, par2EntityLivingBase.posY, par2EntityLivingBase.posZ, par2EntityLivingBase.rotationYaw, par2EntityLivingBase.rotationPitch);
/*  67: 68 */     setPosition(this.posX, this.posY, this.posZ);
/*  68: 69 */     this.yOffset = 0.0F;
/*  69: 70 */     this.motionX = (this.motionY = this.motionZ = 0.0D);
/*  70: 71 */     par3 += this.rand.nextGaussian() * 0.4D;
/*  71: 72 */     par5 += this.rand.nextGaussian() * 0.4D;
/*  72: 73 */     par7 += this.rand.nextGaussian() * 0.4D;
/*  73: 74 */     double var9 = MathHelper.sqrt_double(par3 * par3 + par5 * par5 + par7 * par7);
/*  74: 75 */     this.accelerationX = (par3 / var9 * 0.1D);
/*  75: 76 */     this.accelerationY = (par5 / var9 * 0.1D);
/*  76: 77 */     this.accelerationZ = (par7 / var9 * 0.1D);
/*  77:    */   }
/*  78:    */   
/*  79:    */   public void onUpdate()
/*  80:    */   {
/*  81: 85 */     if ((!this.worldObj.isClient) && (((this.shootingEntity != null) && (this.shootingEntity.isDead)) || (!this.worldObj.blockExists((int)this.posX, (int)this.posY, (int)this.posZ))))
/*  82:    */     {
/*  83: 87 */       setDead();
/*  84:    */     }
/*  85:    */     else
/*  86:    */     {
/*  87: 91 */       super.onUpdate();
/*  88: 92 */       setFire(1);
/*  89: 94 */       if (this.inGround)
/*  90:    */       {
/*  91: 96 */         if (this.worldObj.getBlock(this.field_145795_e, this.field_145793_f, this.field_145794_g) == this.field_145796_h)
/*  92:    */         {
/*  93: 98 */           this.ticksAlive += 1;
/*  94:100 */           if (this.ticksAlive == 600) {
/*  95:102 */             setDead();
/*  96:    */           }
/*  97:105 */           return;
/*  98:    */         }
/*  99:108 */         this.inGround = false;
/* 100:109 */         this.motionX *= this.rand.nextFloat() * 0.2F;
/* 101:110 */         this.motionY *= this.rand.nextFloat() * 0.2F;
/* 102:111 */         this.motionZ *= this.rand.nextFloat() * 0.2F;
/* 103:112 */         this.ticksAlive = 0;
/* 104:113 */         this.ticksInAir = 0;
/* 105:    */       }
/* 106:    */       else
/* 107:    */       {
/* 108:117 */         this.ticksInAir += 1;
/* 109:    */       }
/* 110:120 */       Vec3 var1 = this.worldObj.getWorldVec3Pool().getVecFromPool(this.posX, this.posY, this.posZ);
/* 111:121 */       Vec3 var2 = this.worldObj.getWorldVec3Pool().getVecFromPool(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
/* 112:122 */       MovingObjectPosition var3 = this.worldObj.rayTraceBlocks(var1, var2);
/* 113:123 */       var1 = this.worldObj.getWorldVec3Pool().getVecFromPool(this.posX, this.posY, this.posZ);
/* 114:124 */       var2 = this.worldObj.getWorldVec3Pool().getVecFromPool(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
/* 115:126 */       if (var3 != null) {
/* 116:128 */         var2 = this.worldObj.getWorldVec3Pool().getVecFromPool(var3.hitVec.xCoord, var3.hitVec.yCoord, var3.hitVec.zCoord);
/* 117:    */       }
/* 118:131 */       Entity var4 = null;
/* 119:132 */       List var5 = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.addCoord(this.motionX, this.motionY, this.motionZ).expand(1.0D, 1.0D, 1.0D));
/* 120:133 */       double var6 = 0.0D;
/* 121:135 */       for (int var8 = 0; var8 < var5.size(); var8++)
/* 122:    */       {
/* 123:137 */         Entity var9 = (Entity)var5.get(var8);
/* 124:139 */         if ((var9.canBeCollidedWith()) && ((!var9.isEntityEqual(this.shootingEntity)) || (this.ticksInAir >= 25)))
/* 125:    */         {
/* 126:141 */           float var10 = 0.3F;
/* 127:142 */           AxisAlignedBB var11 = var9.boundingBox.expand(var10, var10, var10);
/* 128:143 */           MovingObjectPosition var12 = var11.calculateIntercept(var1, var2);
/* 129:145 */           if (var12 != null)
/* 130:    */           {
/* 131:147 */             double var13 = var1.distanceTo(var12.hitVec);
/* 132:149 */             if ((var13 < var6) || (var6 == 0.0D))
/* 133:    */             {
/* 134:151 */               var4 = var9;
/* 135:152 */               var6 = var13;
/* 136:    */             }
/* 137:    */           }
/* 138:    */         }
/* 139:    */       }
/* 140:158 */       if (var4 != null) {
/* 141:160 */         var3 = new MovingObjectPosition(var4);
/* 142:    */       }
/* 143:163 */       if (var3 != null) {
/* 144:165 */         onImpact(var3);
/* 145:    */       }
/* 146:168 */       this.posX += this.motionX;
/* 147:169 */       this.posY += this.motionY;
/* 148:170 */       this.posZ += this.motionZ;
/* 149:171 */       float var15 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
/* 150:172 */       this.rotationYaw = ((float)(Math.atan2(this.motionZ, this.motionX) * 180.0D / 3.141592653589793D) + 90.0F);
/* 151:174 */       for (this.rotationPitch = ((float)(Math.atan2(var15, this.motionY) * 180.0D / 3.141592653589793D) - 90.0F); this.rotationPitch - this.prevRotationPitch < -180.0F; this.prevRotationPitch -= 360.0F) {}
/* 152:179 */       while (this.rotationPitch - this.prevRotationPitch >= 180.0F) {
/* 153:181 */         this.prevRotationPitch += 360.0F;
/* 154:    */       }
/* 155:184 */       while (this.rotationYaw - this.prevRotationYaw < -180.0F) {
/* 156:186 */         this.prevRotationYaw -= 360.0F;
/* 157:    */       }
/* 158:189 */       while (this.rotationYaw - this.prevRotationYaw >= 180.0F) {
/* 159:191 */         this.prevRotationYaw += 360.0F;
/* 160:    */       }
/* 161:194 */       this.rotationPitch = (this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * 0.2F);
/* 162:195 */       this.rotationYaw = (this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * 0.2F);
/* 163:196 */       float var16 = getMotionFactor();
/* 164:198 */       if (isInWater())
/* 165:    */       {
/* 166:200 */         for (int var18 = 0; var18 < 4; var18++)
/* 167:    */         {
/* 168:202 */           float var17 = 0.25F;
/* 169:203 */           this.worldObj.spawnParticle("bubble", this.posX - this.motionX * var17, this.posY - this.motionY * var17, this.posZ - this.motionZ * var17, this.motionX, this.motionY, this.motionZ);
/* 170:    */         }
/* 171:206 */         var16 = 0.8F;
/* 172:    */       }
/* 173:209 */       this.motionX += this.accelerationX;
/* 174:210 */       this.motionY += this.accelerationY;
/* 175:211 */       this.motionZ += this.accelerationZ;
/* 176:212 */       this.motionX *= var16;
/* 177:213 */       this.motionY *= var16;
/* 178:214 */       this.motionZ *= var16;
/* 179:215 */       this.worldObj.spawnParticle("smoke", this.posX, this.posY + 0.5D, this.posZ, 0.0D, 0.0D, 0.0D);
/* 180:216 */       setPosition(this.posX, this.posY, this.posZ);
/* 181:    */     }
/* 182:    */   }
/* 183:    */   
/* 184:    */   protected float getMotionFactor()
/* 185:    */   {
/* 186:225 */     return 0.95F;
/* 187:    */   }
/* 188:    */   
/* 189:    */   protected abstract void onImpact(MovingObjectPosition paramMovingObjectPosition);
/* 190:    */   
/* 191:    */   public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
/* 192:    */   {
/* 193:238 */     par1NBTTagCompound.setShort("xTile", (short)this.field_145795_e);
/* 194:239 */     par1NBTTagCompound.setShort("yTile", (short)this.field_145793_f);
/* 195:240 */     par1NBTTagCompound.setShort("zTile", (short)this.field_145794_g);
/* 196:241 */     par1NBTTagCompound.setByte("inTile", (byte)Block.getIdFromBlock(this.field_145796_h));
/* 197:242 */     par1NBTTagCompound.setByte("inGround", (byte)(this.inGround ? 1 : 0));
/* 198:243 */     par1NBTTagCompound.setTag("direction", newDoubleNBTList(new double[] { this.motionX, this.motionY, this.motionZ }));
/* 199:    */   }
/* 200:    */   
/* 201:    */   public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
/* 202:    */   {
/* 203:251 */     this.field_145795_e = par1NBTTagCompound.getShort("xTile");
/* 204:252 */     this.field_145793_f = par1NBTTagCompound.getShort("yTile");
/* 205:253 */     this.field_145794_g = par1NBTTagCompound.getShort("zTile");
/* 206:254 */     this.field_145796_h = Block.getBlockById(par1NBTTagCompound.getByte("inTile") & 0xFF);
/* 207:255 */     this.inGround = (par1NBTTagCompound.getByte("inGround") == 1);
/* 208:257 */     if (par1NBTTagCompound.func_150297_b("direction", 9))
/* 209:    */     {
/* 210:259 */       NBTTagList var2 = par1NBTTagCompound.getTagList("direction", 6);
/* 211:260 */       this.motionX = var2.func_150309_d(0);
/* 212:261 */       this.motionY = var2.func_150309_d(1);
/* 213:262 */       this.motionZ = var2.func_150309_d(2);
/* 214:    */     }
/* 215:    */     else
/* 216:    */     {
/* 217:266 */       setDead();
/* 218:    */     }
/* 219:    */   }
/* 220:    */   
/* 221:    */   public boolean canBeCollidedWith()
/* 222:    */   {
/* 223:275 */     return true;
/* 224:    */   }
/* 225:    */   
/* 226:    */   public float getCollisionBorderSize()
/* 227:    */   {
/* 228:280 */     return 1.0F;
/* 229:    */   }
/* 230:    */   
/* 231:    */   public boolean attackEntityFrom(DamageSource par1DamageSource, float par2)
/* 232:    */   {
/* 233:288 */     if (isEntityInvulnerable()) {
/* 234:290 */       return false;
/* 235:    */     }
/* 236:294 */     setBeenAttacked();
/* 237:296 */     if (par1DamageSource.getEntity() != null)
/* 238:    */     {
/* 239:298 */       Vec3 var3 = par1DamageSource.getEntity().getLookVec();
/* 240:300 */       if (var3 != null)
/* 241:    */       {
/* 242:302 */         this.motionX = var3.xCoord;
/* 243:303 */         this.motionY = var3.yCoord;
/* 244:304 */         this.motionZ = var3.zCoord;
/* 245:305 */         this.accelerationX = (this.motionX * 0.1D);
/* 246:306 */         this.accelerationY = (this.motionY * 0.1D);
/* 247:307 */         this.accelerationZ = (this.motionZ * 0.1D);
/* 248:    */       }
/* 249:310 */       if ((par1DamageSource.getEntity() instanceof EntityLivingBase)) {
/* 250:312 */         this.shootingEntity = ((EntityLivingBase)par1DamageSource.getEntity());
/* 251:    */       }
/* 252:315 */       return true;
/* 253:    */     }
/* 254:319 */     return false;
/* 255:    */   }
/* 256:    */   
/* 257:    */   public float getShadowSize()
/* 258:    */   {
/* 259:326 */     return 0.0F;
/* 260:    */   }
/* 261:    */   
/* 262:    */   public float getBrightness(float par1)
/* 263:    */   {
/* 264:334 */     return 1.0F;
/* 265:    */   }
/* 266:    */   
/* 267:    */   public int getBrightnessForRender(float par1)
/* 268:    */   {
/* 269:339 */     return 15728880;
/* 270:    */   }
/* 271:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.entity.projectile.EntityFireball
 * JD-Core Version:    0.7.0.1
 */