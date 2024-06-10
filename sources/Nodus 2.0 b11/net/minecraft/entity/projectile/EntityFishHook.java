/*   1:    */ package net.minecraft.entity.projectile;
/*   2:    */ 
/*   3:    */ import java.util.Arrays;
/*   4:    */ import java.util.List;
/*   5:    */ import java.util.Random;
/*   6:    */ import net.minecraft.block.Block;
/*   7:    */ import net.minecraft.block.material.Material;
/*   8:    */ import net.minecraft.enchantment.EnchantmentHelper;
/*   9:    */ import net.minecraft.entity.Entity;
/*  10:    */ import net.minecraft.entity.item.EntityItem;
/*  11:    */ import net.minecraft.entity.item.EntityXPOrb;
/*  12:    */ import net.minecraft.entity.player.EntityPlayer;
/*  13:    */ import net.minecraft.init.Blocks;
/*  14:    */ import net.minecraft.init.Items;
/*  15:    */ import net.minecraft.item.ItemFishFood.FishType;
/*  16:    */ import net.minecraft.item.ItemStack;
/*  17:    */ import net.minecraft.nbt.NBTTagCompound;
/*  18:    */ import net.minecraft.stats.StatList;
/*  19:    */ import net.minecraft.util.AABBPool;
/*  20:    */ import net.minecraft.util.AxisAlignedBB;
/*  21:    */ import net.minecraft.util.DamageSource;
/*  22:    */ import net.minecraft.util.MathHelper;
/*  23:    */ import net.minecraft.util.MovingObjectPosition;
/*  24:    */ import net.minecraft.util.Vec3;
/*  25:    */ import net.minecraft.util.Vec3Pool;
/*  26:    */ import net.minecraft.util.WeightedRandom;
/*  27:    */ import net.minecraft.util.WeightedRandomFishable;
/*  28:    */ import net.minecraft.world.World;
/*  29:    */ import net.minecraft.world.WorldServer;
/*  30:    */ 
/*  31:    */ public class EntityFishHook
/*  32:    */   extends Entity
/*  33:    */ {
/*  34: 30 */   private static final List field_146039_d = Arrays.asList(new WeightedRandomFishable[] { new WeightedRandomFishable(new ItemStack(Items.leather_boots), 10).func_150709_a(0.9F), new WeightedRandomFishable(new ItemStack(Items.leather), 10), new WeightedRandomFishable(new ItemStack(Items.bone), 10), new WeightedRandomFishable(new ItemStack(Items.potionitem), 10), new WeightedRandomFishable(new ItemStack(Items.string), 5), new WeightedRandomFishable(new ItemStack(Items.fishing_rod), 2).func_150709_a(0.9F), new WeightedRandomFishable(new ItemStack(Items.bowl), 10), new WeightedRandomFishable(new ItemStack(Items.stick), 5), new WeightedRandomFishable(new ItemStack(Items.dye, 10, 0), 1), new WeightedRandomFishable(new ItemStack(Blocks.tripwire_hook), 10), new WeightedRandomFishable(new ItemStack(Items.rotten_flesh), 10) });
/*  35: 31 */   private static final List field_146041_e = Arrays.asList(new WeightedRandomFishable[] { new WeightedRandomFishable(new ItemStack(Blocks.waterlily), 1), new WeightedRandomFishable(new ItemStack(Items.name_tag), 1), new WeightedRandomFishable(new ItemStack(Items.saddle), 1), new WeightedRandomFishable(new ItemStack(Items.bow), 1).func_150709_a(0.25F).func_150707_a(), new WeightedRandomFishable(new ItemStack(Items.fishing_rod), 1).func_150709_a(0.25F).func_150707_a(), new WeightedRandomFishable(new ItemStack(Items.book), 1).func_150707_a() });
/*  36: 32 */   private static final List field_146036_f = Arrays.asList(new WeightedRandomFishable[] { new WeightedRandomFishable(new ItemStack(Items.fish, 1, ItemFishFood.FishType.COD.func_150976_a()), 60), new WeightedRandomFishable(new ItemStack(Items.fish, 1, ItemFishFood.FishType.SALMON.func_150976_a()), 25), new WeightedRandomFishable(new ItemStack(Items.fish, 1, ItemFishFood.FishType.CLOWNFISH.func_150976_a()), 2), new WeightedRandomFishable(new ItemStack(Items.fish, 1, ItemFishFood.FishType.PUFFERFISH.func_150976_a()), 13) });
/*  37:    */   private int field_146037_g;
/*  38:    */   private int field_146048_h;
/*  39:    */   private int field_146050_i;
/*  40:    */   private Block field_146046_j;
/*  41:    */   private boolean field_146051_au;
/*  42:    */   public int field_146044_a;
/*  43:    */   public EntityPlayer field_146042_b;
/*  44:    */   private int field_146049_av;
/*  45:    */   private int field_146047_aw;
/*  46:    */   private int field_146045_ax;
/*  47:    */   private int field_146040_ay;
/*  48:    */   private int field_146038_az;
/*  49:    */   private float field_146054_aA;
/*  50:    */   public Entity field_146043_c;
/*  51:    */   private int field_146055_aB;
/*  52:    */   private double field_146056_aC;
/*  53:    */   private double field_146057_aD;
/*  54:    */   private double field_146058_aE;
/*  55:    */   private double field_146059_aF;
/*  56:    */   private double field_146060_aG;
/*  57:    */   private double field_146061_aH;
/*  58:    */   private double field_146052_aI;
/*  59:    */   private double field_146053_aJ;
/*  60:    */   private static final String __OBFID = "CL_00001663";
/*  61:    */   
/*  62:    */   public EntityFishHook(World par1World)
/*  63:    */   {
/*  64: 60 */     super(par1World);
/*  65: 61 */     this.field_146037_g = -1;
/*  66: 62 */     this.field_146048_h = -1;
/*  67: 63 */     this.field_146050_i = -1;
/*  68: 64 */     setSize(0.25F, 0.25F);
/*  69: 65 */     this.ignoreFrustumCheck = true;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public EntityFishHook(World par1World, double par2, double par4, double par6, EntityPlayer par8EntityPlayer)
/*  73:    */   {
/*  74: 70 */     this(par1World);
/*  75: 71 */     setPosition(par2, par4, par6);
/*  76: 72 */     this.ignoreFrustumCheck = true;
/*  77: 73 */     this.field_146042_b = par8EntityPlayer;
/*  78: 74 */     par8EntityPlayer.fishEntity = this;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public EntityFishHook(World par1World, EntityPlayer par2EntityPlayer)
/*  82:    */   {
/*  83: 79 */     super(par1World);
/*  84: 80 */     this.field_146037_g = -1;
/*  85: 81 */     this.field_146048_h = -1;
/*  86: 82 */     this.field_146050_i = -1;
/*  87: 83 */     this.ignoreFrustumCheck = true;
/*  88: 84 */     this.field_146042_b = par2EntityPlayer;
/*  89: 85 */     this.field_146042_b.fishEntity = this;
/*  90: 86 */     setSize(0.25F, 0.25F);
/*  91: 87 */     setLocationAndAngles(par2EntityPlayer.posX, par2EntityPlayer.posY + 1.62D - par2EntityPlayer.yOffset, par2EntityPlayer.posZ, par2EntityPlayer.rotationYaw, par2EntityPlayer.rotationPitch);
/*  92: 88 */     this.posX -= MathHelper.cos(this.rotationYaw / 180.0F * 3.141593F) * 0.16F;
/*  93: 89 */     this.posY -= 0.1000000014901161D;
/*  94: 90 */     this.posZ -= MathHelper.sin(this.rotationYaw / 180.0F * 3.141593F) * 0.16F;
/*  95: 91 */     setPosition(this.posX, this.posY, this.posZ);
/*  96: 92 */     this.yOffset = 0.0F;
/*  97: 93 */     float var3 = 0.4F;
/*  98: 94 */     this.motionX = (-MathHelper.sin(this.rotationYaw / 180.0F * 3.141593F) * MathHelper.cos(this.rotationPitch / 180.0F * 3.141593F) * var3);
/*  99: 95 */     this.motionZ = (MathHelper.cos(this.rotationYaw / 180.0F * 3.141593F) * MathHelper.cos(this.rotationPitch / 180.0F * 3.141593F) * var3);
/* 100: 96 */     this.motionY = (-MathHelper.sin(this.rotationPitch / 180.0F * 3.141593F) * var3);
/* 101: 97 */     func_146035_c(this.motionX, this.motionY, this.motionZ, 1.5F, 1.0F);
/* 102:    */   }
/* 103:    */   
/* 104:    */   protected void entityInit() {}
/* 105:    */   
/* 106:    */   public boolean isInRangeToRenderDist(double par1)
/* 107:    */   {
/* 108:108 */     double var3 = this.boundingBox.getAverageEdgeLength() * 4.0D;
/* 109:109 */     var3 *= 64.0D;
/* 110:110 */     return par1 < var3 * var3;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public void func_146035_c(double p_146035_1_, double p_146035_3_, double p_146035_5_, float p_146035_7_, float p_146035_8_)
/* 114:    */   {
/* 115:115 */     float var9 = MathHelper.sqrt_double(p_146035_1_ * p_146035_1_ + p_146035_3_ * p_146035_3_ + p_146035_5_ * p_146035_5_);
/* 116:116 */     p_146035_1_ /= var9;
/* 117:117 */     p_146035_3_ /= var9;
/* 118:118 */     p_146035_5_ /= var9;
/* 119:119 */     p_146035_1_ += this.rand.nextGaussian() * 0.007499999832361937D * p_146035_8_;
/* 120:120 */     p_146035_3_ += this.rand.nextGaussian() * 0.007499999832361937D * p_146035_8_;
/* 121:121 */     p_146035_5_ += this.rand.nextGaussian() * 0.007499999832361937D * p_146035_8_;
/* 122:122 */     p_146035_1_ *= p_146035_7_;
/* 123:123 */     p_146035_3_ *= p_146035_7_;
/* 124:124 */     p_146035_5_ *= p_146035_7_;
/* 125:125 */     this.motionX = p_146035_1_;
/* 126:126 */     this.motionY = p_146035_3_;
/* 127:127 */     this.motionZ = p_146035_5_;
/* 128:128 */     float var10 = MathHelper.sqrt_double(p_146035_1_ * p_146035_1_ + p_146035_5_ * p_146035_5_);
/* 129:129 */     this.prevRotationYaw = (this.rotationYaw = (float)(Math.atan2(p_146035_1_, p_146035_5_) * 180.0D / 3.141592653589793D));
/* 130:130 */     this.prevRotationPitch = (this.rotationPitch = (float)(Math.atan2(p_146035_3_, var10) * 180.0D / 3.141592653589793D));
/* 131:131 */     this.field_146049_av = 0;
/* 132:    */   }
/* 133:    */   
/* 134:    */   public void setPositionAndRotation2(double par1, double par3, double par5, float par7, float par8, int par9)
/* 135:    */   {
/* 136:140 */     this.field_146056_aC = par1;
/* 137:141 */     this.field_146057_aD = par3;
/* 138:142 */     this.field_146058_aE = par5;
/* 139:143 */     this.field_146059_aF = par7;
/* 140:144 */     this.field_146060_aG = par8;
/* 141:145 */     this.field_146055_aB = par9;
/* 142:146 */     this.motionX = this.field_146061_aH;
/* 143:147 */     this.motionY = this.field_146052_aI;
/* 144:148 */     this.motionZ = this.field_146053_aJ;
/* 145:    */   }
/* 146:    */   
/* 147:    */   public void setVelocity(double par1, double par3, double par5)
/* 148:    */   {
/* 149:156 */     this.field_146061_aH = (this.motionX = par1);
/* 150:157 */     this.field_146052_aI = (this.motionY = par3);
/* 151:158 */     this.field_146053_aJ = (this.motionZ = par5);
/* 152:    */   }
/* 153:    */   
/* 154:    */   public void onUpdate()
/* 155:    */   {
/* 156:166 */     super.onUpdate();
/* 157:168 */     if (this.field_146055_aB > 0)
/* 158:    */     {
/* 159:170 */       double var27 = this.posX + (this.field_146056_aC - this.posX) / this.field_146055_aB;
/* 160:171 */       double var28 = this.posY + (this.field_146057_aD - this.posY) / this.field_146055_aB;
/* 161:172 */       double var29 = this.posZ + (this.field_146058_aE - this.posZ) / this.field_146055_aB;
/* 162:173 */       double var7 = MathHelper.wrapAngleTo180_double(this.field_146059_aF - this.rotationYaw);
/* 163:174 */       this.rotationYaw = ((float)(this.rotationYaw + var7 / this.field_146055_aB));
/* 164:175 */       this.rotationPitch = ((float)(this.rotationPitch + (this.field_146060_aG - this.rotationPitch) / this.field_146055_aB));
/* 165:176 */       this.field_146055_aB -= 1;
/* 166:177 */       setPosition(var27, var28, var29);
/* 167:178 */       setRotation(this.rotationYaw, this.rotationPitch);
/* 168:    */     }
/* 169:    */     else
/* 170:    */     {
/* 171:182 */       if (!this.worldObj.isClient)
/* 172:    */       {
/* 173:184 */         ItemStack var1 = this.field_146042_b.getCurrentEquippedItem();
/* 174:186 */         if ((this.field_146042_b.isDead) || (!this.field_146042_b.isEntityAlive()) || (var1 == null) || (var1.getItem() != Items.fishing_rod) || (getDistanceSqToEntity(this.field_146042_b) > 1024.0D))
/* 175:    */         {
/* 176:188 */           setDead();
/* 177:189 */           this.field_146042_b.fishEntity = null;
/* 178:190 */           return;
/* 179:    */         }
/* 180:193 */         if (this.field_146043_c != null)
/* 181:    */         {
/* 182:195 */           if (!this.field_146043_c.isDead)
/* 183:    */           {
/* 184:197 */             this.posX = this.field_146043_c.posX;
/* 185:198 */             this.posY = (this.field_146043_c.boundingBox.minY + this.field_146043_c.height * 0.8D);
/* 186:199 */             this.posZ = this.field_146043_c.posZ;
/* 187:200 */             return;
/* 188:    */           }
/* 189:203 */           this.field_146043_c = null;
/* 190:    */         }
/* 191:    */       }
/* 192:207 */       if (this.field_146044_a > 0) {
/* 193:209 */         this.field_146044_a -= 1;
/* 194:    */       }
/* 195:212 */       if (this.field_146051_au)
/* 196:    */       {
/* 197:214 */         if (this.worldObj.getBlock(this.field_146037_g, this.field_146048_h, this.field_146050_i) == this.field_146046_j)
/* 198:    */         {
/* 199:216 */           this.field_146049_av += 1;
/* 200:218 */           if (this.field_146049_av == 1200) {
/* 201:220 */             setDead();
/* 202:    */           }
/* 203:223 */           return;
/* 204:    */         }
/* 205:226 */         this.field_146051_au = false;
/* 206:227 */         this.motionX *= this.rand.nextFloat() * 0.2F;
/* 207:228 */         this.motionY *= this.rand.nextFloat() * 0.2F;
/* 208:229 */         this.motionZ *= this.rand.nextFloat() * 0.2F;
/* 209:230 */         this.field_146049_av = 0;
/* 210:231 */         this.field_146047_aw = 0;
/* 211:    */       }
/* 212:    */       else
/* 213:    */       {
/* 214:235 */         this.field_146047_aw += 1;
/* 215:    */       }
/* 216:238 */       Vec3 var26 = this.worldObj.getWorldVec3Pool().getVecFromPool(this.posX, this.posY, this.posZ);
/* 217:239 */       Vec3 var2 = this.worldObj.getWorldVec3Pool().getVecFromPool(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
/* 218:240 */       MovingObjectPosition var3 = this.worldObj.rayTraceBlocks(var26, var2);
/* 219:241 */       var26 = this.worldObj.getWorldVec3Pool().getVecFromPool(this.posX, this.posY, this.posZ);
/* 220:242 */       var2 = this.worldObj.getWorldVec3Pool().getVecFromPool(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
/* 221:244 */       if (var3 != null) {
/* 222:246 */         var2 = this.worldObj.getWorldVec3Pool().getVecFromPool(var3.hitVec.xCoord, var3.hitVec.yCoord, var3.hitVec.zCoord);
/* 223:    */       }
/* 224:249 */       Entity var4 = null;
/* 225:250 */       List var5 = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.addCoord(this.motionX, this.motionY, this.motionZ).expand(1.0D, 1.0D, 1.0D));
/* 226:251 */       double var6 = 0.0D;
/* 227:254 */       for (int var8 = 0; var8 < var5.size(); var8++)
/* 228:    */       {
/* 229:256 */         Entity var9 = (Entity)var5.get(var8);
/* 230:258 */         if ((var9.canBeCollidedWith()) && ((var9 != this.field_146042_b) || (this.field_146047_aw >= 5)))
/* 231:    */         {
/* 232:260 */           float var10 = 0.3F;
/* 233:261 */           AxisAlignedBB var11 = var9.boundingBox.expand(var10, var10, var10);
/* 234:262 */           MovingObjectPosition var12 = var11.calculateIntercept(var26, var2);
/* 235:264 */           if (var12 != null)
/* 236:    */           {
/* 237:266 */             double var13 = var26.distanceTo(var12.hitVec);
/* 238:268 */             if ((var13 < var6) || (var6 == 0.0D))
/* 239:    */             {
/* 240:270 */               var4 = var9;
/* 241:271 */               var6 = var13;
/* 242:    */             }
/* 243:    */           }
/* 244:    */         }
/* 245:    */       }
/* 246:277 */       if (var4 != null) {
/* 247:279 */         var3 = new MovingObjectPosition(var4);
/* 248:    */       }
/* 249:282 */       if (var3 != null) {
/* 250:284 */         if (var3.entityHit != null)
/* 251:    */         {
/* 252:286 */           if (var3.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.field_146042_b), 0.0F)) {
/* 253:288 */             this.field_146043_c = var3.entityHit;
/* 254:    */           }
/* 255:    */         }
/* 256:    */         else {
/* 257:293 */           this.field_146051_au = true;
/* 258:    */         }
/* 259:    */       }
/* 260:297 */       if (!this.field_146051_au)
/* 261:    */       {
/* 262:299 */         moveEntity(this.motionX, this.motionY, this.motionZ);
/* 263:300 */         float var30 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
/* 264:301 */         this.rotationYaw = ((float)(Math.atan2(this.motionX, this.motionZ) * 180.0D / 3.141592653589793D));
/* 265:303 */         for (this.rotationPitch = ((float)(Math.atan2(this.motionY, var30) * 180.0D / 3.141592653589793D)); this.rotationPitch - this.prevRotationPitch < -180.0F; this.prevRotationPitch -= 360.0F) {}
/* 266:308 */         while (this.rotationPitch - this.prevRotationPitch >= 180.0F) {
/* 267:310 */           this.prevRotationPitch += 360.0F;
/* 268:    */         }
/* 269:313 */         while (this.rotationYaw - this.prevRotationYaw < -180.0F) {
/* 270:315 */           this.prevRotationYaw -= 360.0F;
/* 271:    */         }
/* 272:318 */         while (this.rotationYaw - this.prevRotationYaw >= 180.0F) {
/* 273:320 */           this.prevRotationYaw += 360.0F;
/* 274:    */         }
/* 275:323 */         this.rotationPitch = (this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * 0.2F);
/* 276:324 */         this.rotationYaw = (this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * 0.2F);
/* 277:325 */         float var31 = 0.92F;
/* 278:327 */         if ((this.onGround) || (this.isCollidedHorizontally)) {
/* 279:329 */           var31 = 0.5F;
/* 280:    */         }
/* 281:332 */         byte var33 = 5;
/* 282:333 */         double var32 = 0.0D;
/* 283:335 */         for (int var35 = 0; var35 < var33; var35++)
/* 284:    */         {
/* 285:337 */           double var14 = this.boundingBox.minY + (this.boundingBox.maxY - this.boundingBox.minY) * (var35 + 0) / var33 - 0.125D + 0.125D;
/* 286:338 */           double var16 = this.boundingBox.minY + (this.boundingBox.maxY - this.boundingBox.minY) * (var35 + 1) / var33 - 0.125D + 0.125D;
/* 287:339 */           AxisAlignedBB var18 = AxisAlignedBB.getAABBPool().getAABB(this.boundingBox.minX, var14, this.boundingBox.minZ, this.boundingBox.maxX, var16, this.boundingBox.maxZ);
/* 288:341 */           if (this.worldObj.isAABBInMaterial(var18, Material.water)) {
/* 289:343 */             var32 += 1.0D / var33;
/* 290:    */           }
/* 291:    */         }
/* 292:347 */         if ((!this.worldObj.isClient) && (var32 > 0.0D))
/* 293:    */         {
/* 294:349 */           WorldServer var34 = (WorldServer)this.worldObj;
/* 295:350 */           int var36 = 1;
/* 296:352 */           if ((this.rand.nextFloat() < 0.25F) && (this.worldObj.canLightningStrikeAt(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY) + 1, MathHelper.floor_double(this.posZ)))) {
/* 297:354 */             var36 = 2;
/* 298:    */           }
/* 299:357 */           if ((this.rand.nextFloat() < 0.5F) && (!this.worldObj.canBlockSeeTheSky(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY) + 1, MathHelper.floor_double(this.posZ)))) {
/* 300:359 */             var36--;
/* 301:    */           }
/* 302:362 */           if (this.field_146045_ax > 0)
/* 303:    */           {
/* 304:364 */             this.field_146045_ax -= 1;
/* 305:366 */             if (this.field_146045_ax <= 0)
/* 306:    */             {
/* 307:368 */               this.field_146040_ay = 0;
/* 308:369 */               this.field_146038_az = 0;
/* 309:    */             }
/* 310:    */           }
/* 311:381 */           else if (this.field_146038_az > 0)
/* 312:    */           {
/* 313:383 */             this.field_146038_az -= var36;
/* 314:385 */             if (this.field_146038_az <= 0)
/* 315:    */             {
/* 316:387 */               this.motionY -= 0.2000000029802322D;
/* 317:388 */               playSound("random.splash", 0.25F, 1.0F + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.4F);
/* 318:389 */               float var15 = MathHelper.floor_double(this.boundingBox.minY);
/* 319:390 */               var34.func_147487_a("bubble", this.posX, var15 + 1.0F, this.posZ, (int)(1.0F + this.width * 20.0F), this.width, 0.0D, this.width, 0.2000000029802322D);
/* 320:391 */               var34.func_147487_a("wake", this.posX, var15 + 1.0F, this.posZ, (int)(1.0F + this.width * 20.0F), this.width, 0.0D, this.width, 0.2000000029802322D);
/* 321:392 */               this.field_146045_ax = MathHelper.getRandomIntegerInRange(this.rand, 10, 30);
/* 322:    */             }
/* 323:    */             else
/* 324:    */             {
/* 325:396 */               this.field_146054_aA = ((float)(this.field_146054_aA + this.rand.nextGaussian() * 4.0D));
/* 326:397 */               float var15 = this.field_146054_aA * 0.01745329F;
/* 327:398 */               float var37 = MathHelper.sin(var15);
/* 328:399 */               float var17 = MathHelper.cos(var15);
/* 329:400 */               double var38 = this.posX + var37 * this.field_146038_az * 0.1F;
/* 330:401 */               double var20 = MathHelper.floor_double(this.boundingBox.minY) + 1.0F;
/* 331:402 */               double var22 = this.posZ + var17 * this.field_146038_az * 0.1F;
/* 332:404 */               if (this.rand.nextFloat() < 0.15F) {
/* 333:406 */                 var34.func_147487_a("bubble", var38, var20 - 0.1000000014901161D, var22, 1, var37, 0.1D, var17, 0.0D);
/* 334:    */               }
/* 335:409 */               float var24 = var37 * 0.04F;
/* 336:410 */               float var25 = var17 * 0.04F;
/* 337:411 */               var34.func_147487_a("wake", var38, var20, var22, 0, var25, 0.01D, -var24, 1.0D);
/* 338:412 */               var34.func_147487_a("wake", var38, var20, var22, 0, -var25, 0.01D, var24, 1.0D);
/* 339:    */             }
/* 340:    */           }
/* 341:415 */           else if (this.field_146040_ay > 0)
/* 342:    */           {
/* 343:417 */             this.field_146040_ay -= var36;
/* 344:418 */             float var15 = 0.15F;
/* 345:420 */             if (this.field_146040_ay < 20) {
/* 346:422 */               var15 = (float)(var15 + (20 - this.field_146040_ay) * 0.05D);
/* 347:424 */             } else if (this.field_146040_ay < 40) {
/* 348:426 */               var15 = (float)(var15 + (40 - this.field_146040_ay) * 0.02D);
/* 349:428 */             } else if (this.field_146040_ay < 60) {
/* 350:430 */               var15 = (float)(var15 + (60 - this.field_146040_ay) * 0.01D);
/* 351:    */             }
/* 352:433 */             if (this.rand.nextFloat() < var15)
/* 353:    */             {
/* 354:435 */               float var37 = MathHelper.randomFloatClamp(this.rand, 0.0F, 360.0F) * 0.01745329F;
/* 355:436 */               float var17 = MathHelper.randomFloatClamp(this.rand, 25.0F, 60.0F);
/* 356:437 */               double var38 = this.posX + MathHelper.sin(var37) * var17 * 0.1F;
/* 357:438 */               double var20 = MathHelper.floor_double(this.boundingBox.minY) + 1.0F;
/* 358:439 */               double var22 = this.posZ + MathHelper.cos(var37) * var17 * 0.1F;
/* 359:440 */               var34.func_147487_a("splash", var38, var20, var22, 2 + this.rand.nextInt(2), 0.1000000014901161D, 0.0D, 0.1000000014901161D, 0.0D);
/* 360:    */             }
/* 361:443 */             if (this.field_146040_ay <= 0)
/* 362:    */             {
/* 363:445 */               this.field_146054_aA = MathHelper.randomFloatClamp(this.rand, 0.0F, 360.0F);
/* 364:446 */               this.field_146038_az = MathHelper.getRandomIntegerInRange(this.rand, 20, 80);
/* 365:    */             }
/* 366:    */           }
/* 367:    */           else
/* 368:    */           {
/* 369:451 */             this.field_146040_ay = MathHelper.getRandomIntegerInRange(this.rand, 100, 900);
/* 370:452 */             this.field_146040_ay -= EnchantmentHelper.func_151387_h(this.field_146042_b) * 20 * 5;
/* 371:    */           }
/* 372:456 */           if (this.field_146045_ax > 0) {
/* 373:458 */             this.motionY -= this.rand.nextFloat() * this.rand.nextFloat() * this.rand.nextFloat() * 0.2D;
/* 374:    */           }
/* 375:    */         }
/* 376:462 */         double var13 = var32 * 2.0D - 1.0D;
/* 377:463 */         this.motionY += 0.03999999910593033D * var13;
/* 378:465 */         if (var32 > 0.0D)
/* 379:    */         {
/* 380:467 */           var31 = (float)(var31 * 0.9D);
/* 381:468 */           this.motionY *= 0.8D;
/* 382:    */         }
/* 383:471 */         this.motionX *= var31;
/* 384:472 */         this.motionY *= var31;
/* 385:473 */         this.motionZ *= var31;
/* 386:474 */         setPosition(this.posX, this.posY, this.posZ);
/* 387:    */       }
/* 388:    */     }
/* 389:    */   }
/* 390:    */   
/* 391:    */   public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
/* 392:    */   {
/* 393:484 */     par1NBTTagCompound.setShort("xTile", (short)this.field_146037_g);
/* 394:485 */     par1NBTTagCompound.setShort("yTile", (short)this.field_146048_h);
/* 395:486 */     par1NBTTagCompound.setShort("zTile", (short)this.field_146050_i);
/* 396:487 */     par1NBTTagCompound.setByte("inTile", (byte)Block.getIdFromBlock(this.field_146046_j));
/* 397:488 */     par1NBTTagCompound.setByte("shake", (byte)this.field_146044_a);
/* 398:489 */     par1NBTTagCompound.setByte("inGround", (byte)(this.field_146051_au ? 1 : 0));
/* 399:    */   }
/* 400:    */   
/* 401:    */   public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
/* 402:    */   {
/* 403:497 */     this.field_146037_g = par1NBTTagCompound.getShort("xTile");
/* 404:498 */     this.field_146048_h = par1NBTTagCompound.getShort("yTile");
/* 405:499 */     this.field_146050_i = par1NBTTagCompound.getShort("zTile");
/* 406:500 */     this.field_146046_j = Block.getBlockById(par1NBTTagCompound.getByte("inTile") & 0xFF);
/* 407:501 */     this.field_146044_a = (par1NBTTagCompound.getByte("shake") & 0xFF);
/* 408:502 */     this.field_146051_au = (par1NBTTagCompound.getByte("inGround") == 1);
/* 409:    */   }
/* 410:    */   
/* 411:    */   public float getShadowSize()
/* 412:    */   {
/* 413:507 */     return 0.0F;
/* 414:    */   }
/* 415:    */   
/* 416:    */   public int func_146034_e()
/* 417:    */   {
/* 418:512 */     if (this.worldObj.isClient) {
/* 419:514 */       return 0;
/* 420:    */     }
/* 421:518 */     byte var1 = 0;
/* 422:520 */     if (this.field_146043_c != null)
/* 423:    */     {
/* 424:522 */       double var2 = this.field_146042_b.posX - this.posX;
/* 425:523 */       double var4 = this.field_146042_b.posY - this.posY;
/* 426:524 */       double var6 = this.field_146042_b.posZ - this.posZ;
/* 427:525 */       double var8 = MathHelper.sqrt_double(var2 * var2 + var4 * var4 + var6 * var6);
/* 428:526 */       double var10 = 0.1D;
/* 429:527 */       this.field_146043_c.motionX += var2 * var10;
/* 430:528 */       this.field_146043_c.motionY += var4 * var10 + MathHelper.sqrt_double(var8) * 0.08D;
/* 431:529 */       this.field_146043_c.motionZ += var6 * var10;
/* 432:530 */       var1 = 3;
/* 433:    */     }
/* 434:532 */     else if (this.field_146045_ax > 0)
/* 435:    */     {
/* 436:534 */       EntityItem var13 = new EntityItem(this.worldObj, this.posX, this.posY, this.posZ, func_146033_f());
/* 437:535 */       double var3 = this.field_146042_b.posX - this.posX;
/* 438:536 */       double var5 = this.field_146042_b.posY - this.posY;
/* 439:537 */       double var7 = this.field_146042_b.posZ - this.posZ;
/* 440:538 */       double var9 = MathHelper.sqrt_double(var3 * var3 + var5 * var5 + var7 * var7);
/* 441:539 */       double var11 = 0.1D;
/* 442:540 */       var13.motionX = (var3 * var11);
/* 443:541 */       var13.motionY = (var5 * var11 + MathHelper.sqrt_double(var9) * 0.08D);
/* 444:542 */       var13.motionZ = (var7 * var11);
/* 445:543 */       this.worldObj.spawnEntityInWorld(var13);
/* 446:544 */       this.field_146042_b.worldObj.spawnEntityInWorld(new EntityXPOrb(this.field_146042_b.worldObj, this.field_146042_b.posX, this.field_146042_b.posY + 0.5D, this.field_146042_b.posZ + 0.5D, this.rand.nextInt(6) + 1));
/* 447:545 */       var1 = 1;
/* 448:    */     }
/* 449:548 */     if (this.field_146051_au) {
/* 450:550 */       var1 = 2;
/* 451:    */     }
/* 452:553 */     setDead();
/* 453:554 */     this.field_146042_b.fishEntity = null;
/* 454:555 */     return var1;
/* 455:    */   }
/* 456:    */   
/* 457:    */   private ItemStack func_146033_f()
/* 458:    */   {
/* 459:561 */     float var1 = this.worldObj.rand.nextFloat();
/* 460:562 */     int var2 = EnchantmentHelper.func_151386_g(this.field_146042_b);
/* 461:563 */     int var3 = EnchantmentHelper.func_151387_h(this.field_146042_b);
/* 462:564 */     float var4 = 0.1F - var2 * 0.025F - var3 * 0.01F;
/* 463:565 */     float var5 = 0.05F + var2 * 0.01F - var3 * 0.01F;
/* 464:566 */     var4 = MathHelper.clamp_float(var4, 0.0F, 1.0F);
/* 465:567 */     var5 = MathHelper.clamp_float(var5, 0.0F, 1.0F);
/* 466:569 */     if (var1 < var4)
/* 467:    */     {
/* 468:571 */       this.field_146042_b.addStat(StatList.field_151183_A, 1);
/* 469:572 */       return ((WeightedRandomFishable)WeightedRandom.getRandomItem(this.rand, field_146039_d)).func_150708_a(this.rand);
/* 470:    */     }
/* 471:576 */     var1 -= var4;
/* 472:578 */     if (var1 < var5)
/* 473:    */     {
/* 474:580 */       this.field_146042_b.addStat(StatList.field_151184_B, 1);
/* 475:581 */       return ((WeightedRandomFishable)WeightedRandom.getRandomItem(this.rand, field_146041_e)).func_150708_a(this.rand);
/* 476:    */     }
/* 477:585 */     float var10000 = var1 - var5;
/* 478:586 */     this.field_146042_b.addStat(StatList.fishCaughtStat, 1);
/* 479:587 */     return ((WeightedRandomFishable)WeightedRandom.getRandomItem(this.rand, field_146036_f)).func_150708_a(this.rand);
/* 480:    */   }
/* 481:    */   
/* 482:    */   public void setDead()
/* 483:    */   {
/* 484:597 */     super.setDead();
/* 485:599 */     if (this.field_146042_b != null) {
/* 486:601 */       this.field_146042_b.fishEntity = null;
/* 487:    */     }
/* 488:    */   }
/* 489:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.entity.projectile.EntityFishHook
 * JD-Core Version:    0.7.0.1
 */