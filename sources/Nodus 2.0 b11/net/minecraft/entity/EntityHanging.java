/*   1:    */ package net.minecraft.entity;
/*   2:    */ 
/*   3:    */ import java.util.Iterator;
/*   4:    */ import java.util.List;
/*   5:    */ import net.minecraft.block.Block;
/*   6:    */ import net.minecraft.block.material.Material;
/*   7:    */ import net.minecraft.entity.player.EntityPlayer;
/*   8:    */ import net.minecraft.nbt.NBTTagCompound;
/*   9:    */ import net.minecraft.util.AxisAlignedBB;
/*  10:    */ import net.minecraft.util.DamageSource;
/*  11:    */ import net.minecraft.util.MathHelper;
/*  12:    */ import net.minecraft.world.World;
/*  13:    */ 
/*  14:    */ public abstract class EntityHanging
/*  15:    */   extends Entity
/*  16:    */ {
/*  17:    */   private int tickCounter1;
/*  18:    */   public int hangingDirection;
/*  19:    */   public int field_146063_b;
/*  20:    */   public int field_146064_c;
/*  21:    */   public int field_146062_d;
/*  22:    */   private static final String __OBFID = "CL_00001546";
/*  23:    */   
/*  24:    */   public EntityHanging(World par1World)
/*  25:    */   {
/*  26: 24 */     super(par1World);
/*  27: 25 */     this.yOffset = 0.0F;
/*  28: 26 */     setSize(0.5F, 0.5F);
/*  29:    */   }
/*  30:    */   
/*  31:    */   public EntityHanging(World par1World, int par2, int par3, int par4, int par5)
/*  32:    */   {
/*  33: 31 */     this(par1World);
/*  34: 32 */     this.field_146063_b = par2;
/*  35: 33 */     this.field_146064_c = par3;
/*  36: 34 */     this.field_146062_d = par4;
/*  37:    */   }
/*  38:    */   
/*  39:    */   protected void entityInit() {}
/*  40:    */   
/*  41:    */   public void setDirection(int par1)
/*  42:    */   {
/*  43: 41 */     this.hangingDirection = par1;
/*  44: 42 */     this.prevRotationYaw = (this.rotationYaw = par1 * 90);
/*  45: 43 */     float var2 = getWidthPixels();
/*  46: 44 */     float var3 = getHeightPixels();
/*  47: 45 */     float var4 = getWidthPixels();
/*  48: 47 */     if ((par1 != 2) && (par1 != 0))
/*  49:    */     {
/*  50: 49 */       var2 = 0.5F;
/*  51:    */     }
/*  52:    */     else
/*  53:    */     {
/*  54: 53 */       var4 = 0.5F;
/*  55: 54 */       this.rotationYaw = (this.prevRotationYaw = net.minecraft.util.Direction.rotateOpposite[par1] * 90);
/*  56:    */     }
/*  57: 57 */     var2 /= 32.0F;
/*  58: 58 */     var3 /= 32.0F;
/*  59: 59 */     var4 /= 32.0F;
/*  60: 60 */     float var5 = this.field_146063_b + 0.5F;
/*  61: 61 */     float var6 = this.field_146064_c + 0.5F;
/*  62: 62 */     float var7 = this.field_146062_d + 0.5F;
/*  63: 63 */     float var8 = 0.5625F;
/*  64: 65 */     if (par1 == 2) {
/*  65: 67 */       var7 -= var8;
/*  66:    */     }
/*  67: 70 */     if (par1 == 1) {
/*  68: 72 */       var5 -= var8;
/*  69:    */     }
/*  70: 75 */     if (par1 == 0) {
/*  71: 77 */       var7 += var8;
/*  72:    */     }
/*  73: 80 */     if (par1 == 3) {
/*  74: 82 */       var5 += var8;
/*  75:    */     }
/*  76: 85 */     if (par1 == 2) {
/*  77: 87 */       var5 -= func_70517_b(getWidthPixels());
/*  78:    */     }
/*  79: 90 */     if (par1 == 1) {
/*  80: 92 */       var7 += func_70517_b(getWidthPixels());
/*  81:    */     }
/*  82: 95 */     if (par1 == 0) {
/*  83: 97 */       var5 += func_70517_b(getWidthPixels());
/*  84:    */     }
/*  85:100 */     if (par1 == 3) {
/*  86:102 */       var7 -= func_70517_b(getWidthPixels());
/*  87:    */     }
/*  88:105 */     var6 += func_70517_b(getHeightPixels());
/*  89:106 */     setPosition(var5, var6, var7);
/*  90:107 */     float var9 = -0.03125F;
/*  91:108 */     this.boundingBox.setBounds(var5 - var2 - var9, var6 - var3 - var9, var7 - var4 - var9, var5 + var2 + var9, var6 + var3 + var9, var7 + var4 + var9);
/*  92:    */   }
/*  93:    */   
/*  94:    */   private float func_70517_b(int par1)
/*  95:    */   {
/*  96:113 */     return par1 == 64 ? 0.5F : par1 == 32 ? 0.5F : 0.0F;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public void onUpdate()
/* 100:    */   {
/* 101:121 */     this.prevPosX = this.posX;
/* 102:122 */     this.prevPosY = this.posY;
/* 103:123 */     this.prevPosZ = this.posZ;
/* 104:125 */     if ((this.tickCounter1++ == 100) && (!this.worldObj.isClient))
/* 105:    */     {
/* 106:127 */       this.tickCounter1 = 0;
/* 107:129 */       if ((!this.isDead) && (!onValidSurface()))
/* 108:    */       {
/* 109:131 */         setDead();
/* 110:132 */         onBroken(null);
/* 111:    */       }
/* 112:    */     }
/* 113:    */   }
/* 114:    */   
/* 115:    */   public boolean onValidSurface()
/* 116:    */   {
/* 117:142 */     if (!this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox).isEmpty()) {
/* 118:144 */       return false;
/* 119:    */     }
/* 120:148 */     int var1 = Math.max(1, getWidthPixels() / 16);
/* 121:149 */     int var2 = Math.max(1, getHeightPixels() / 16);
/* 122:150 */     int var3 = this.field_146063_b;
/* 123:151 */     int var4 = this.field_146064_c;
/* 124:152 */     int var5 = this.field_146062_d;
/* 125:154 */     if (this.hangingDirection == 2) {
/* 126:156 */       var3 = MathHelper.floor_double(this.posX - getWidthPixels() / 32.0F);
/* 127:    */     }
/* 128:159 */     if (this.hangingDirection == 1) {
/* 129:161 */       var5 = MathHelper.floor_double(this.posZ - getWidthPixels() / 32.0F);
/* 130:    */     }
/* 131:164 */     if (this.hangingDirection == 0) {
/* 132:166 */       var3 = MathHelper.floor_double(this.posX - getWidthPixels() / 32.0F);
/* 133:    */     }
/* 134:169 */     if (this.hangingDirection == 3) {
/* 135:171 */       var5 = MathHelper.floor_double(this.posZ - getWidthPixels() / 32.0F);
/* 136:    */     }
/* 137:174 */     var4 = MathHelper.floor_double(this.posY - getHeightPixels() / 32.0F);
/* 138:176 */     for (int var6 = 0; var6 < var1; var6++) {
/* 139:178 */       for (int var7 = 0; var7 < var2; var7++)
/* 140:    */       {
/* 141:    */         Material var8;
/* 142:    */         Material var8;
/* 143:182 */         if ((this.hangingDirection != 2) && (this.hangingDirection != 0)) {
/* 144:184 */           var8 = this.worldObj.getBlock(this.field_146063_b, var4 + var7, var5 + var6).getMaterial();
/* 145:    */         } else {
/* 146:188 */           var8 = this.worldObj.getBlock(var3 + var6, var4 + var7, this.field_146062_d).getMaterial();
/* 147:    */         }
/* 148:191 */         if (!var8.isSolid()) {
/* 149:193 */           return false;
/* 150:    */         }
/* 151:    */       }
/* 152:    */     }
/* 153:198 */     List var9 = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox);
/* 154:199 */     Iterator var10 = var9.iterator();
/* 155:    */     Entity var11;
/* 156:    */     do
/* 157:    */     {
/* 158:204 */       if (!var10.hasNext()) {
/* 159:206 */         return true;
/* 160:    */       }
/* 161:209 */       var11 = (Entity)var10.next();
/* 162:211 */     } while (!(var11 instanceof EntityHanging));
/* 163:213 */     return false;
/* 164:    */   }
/* 165:    */   
/* 166:    */   public boolean canBeCollidedWith()
/* 167:    */   {
/* 168:222 */     return true;
/* 169:    */   }
/* 170:    */   
/* 171:    */   public boolean hitByEntity(Entity par1Entity)
/* 172:    */   {
/* 173:230 */     return (par1Entity instanceof EntityPlayer) ? attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer)par1Entity), 0.0F) : false;
/* 174:    */   }
/* 175:    */   
/* 176:    */   public void func_145781_i(int p_145781_1_)
/* 177:    */   {
/* 178:235 */     this.worldObj.func_147450_X();
/* 179:    */   }
/* 180:    */   
/* 181:    */   public boolean attackEntityFrom(DamageSource par1DamageSource, float par2)
/* 182:    */   {
/* 183:243 */     if (isEntityInvulnerable()) {
/* 184:245 */       return false;
/* 185:    */     }
/* 186:249 */     if ((!this.isDead) && (!this.worldObj.isClient))
/* 187:    */     {
/* 188:251 */       setDead();
/* 189:252 */       setBeenAttacked();
/* 190:253 */       onBroken(par1DamageSource.getEntity());
/* 191:    */     }
/* 192:256 */     return true;
/* 193:    */   }
/* 194:    */   
/* 195:    */   public void moveEntity(double par1, double par3, double par5)
/* 196:    */   {
/* 197:265 */     if ((!this.worldObj.isClient) && (!this.isDead) && (par1 * par1 + par3 * par3 + par5 * par5 > 0.0D))
/* 198:    */     {
/* 199:267 */       setDead();
/* 200:268 */       onBroken(null);
/* 201:    */     }
/* 202:    */   }
/* 203:    */   
/* 204:    */   public void addVelocity(double par1, double par3, double par5)
/* 205:    */   {
/* 206:277 */     if ((!this.worldObj.isClient) && (!this.isDead) && (par1 * par1 + par3 * par3 + par5 * par5 > 0.0D))
/* 207:    */     {
/* 208:279 */       setDead();
/* 209:280 */       onBroken(null);
/* 210:    */     }
/* 211:    */   }
/* 212:    */   
/* 213:    */   public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
/* 214:    */   {
/* 215:289 */     par1NBTTagCompound.setByte("Direction", (byte)this.hangingDirection);
/* 216:290 */     par1NBTTagCompound.setInteger("TileX", this.field_146063_b);
/* 217:291 */     par1NBTTagCompound.setInteger("TileY", this.field_146064_c);
/* 218:292 */     par1NBTTagCompound.setInteger("TileZ", this.field_146062_d);
/* 219:294 */     switch (this.hangingDirection)
/* 220:    */     {
/* 221:    */     case 0: 
/* 222:297 */       par1NBTTagCompound.setByte("Dir", (byte)2);
/* 223:298 */       break;
/* 224:    */     case 1: 
/* 225:301 */       par1NBTTagCompound.setByte("Dir", (byte)1);
/* 226:302 */       break;
/* 227:    */     case 2: 
/* 228:305 */       par1NBTTagCompound.setByte("Dir", (byte)0);
/* 229:306 */       break;
/* 230:    */     case 3: 
/* 231:309 */       par1NBTTagCompound.setByte("Dir", (byte)3);
/* 232:    */     }
/* 233:    */   }
/* 234:    */   
/* 235:    */   public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
/* 236:    */   {
/* 237:318 */     if (par1NBTTagCompound.func_150297_b("Direction", 99)) {
/* 238:320 */       this.hangingDirection = par1NBTTagCompound.getByte("Direction");
/* 239:    */     } else {
/* 240:324 */       switch (par1NBTTagCompound.getByte("Dir"))
/* 241:    */       {
/* 242:    */       case 0: 
/* 243:327 */         this.hangingDirection = 2;
/* 244:328 */         break;
/* 245:    */       case 1: 
/* 246:331 */         this.hangingDirection = 1;
/* 247:332 */         break;
/* 248:    */       case 2: 
/* 249:335 */         this.hangingDirection = 0;
/* 250:336 */         break;
/* 251:    */       case 3: 
/* 252:339 */         this.hangingDirection = 3;
/* 253:    */       }
/* 254:    */     }
/* 255:343 */     this.field_146063_b = par1NBTTagCompound.getInteger("TileX");
/* 256:344 */     this.field_146064_c = par1NBTTagCompound.getInteger("TileY");
/* 257:345 */     this.field_146062_d = par1NBTTagCompound.getInteger("TileZ");
/* 258:346 */     setDirection(this.hangingDirection);
/* 259:    */   }
/* 260:    */   
/* 261:    */   public abstract int getWidthPixels();
/* 262:    */   
/* 263:    */   public abstract int getHeightPixels();
/* 264:    */   
/* 265:    */   public abstract void onBroken(Entity paramEntity);
/* 266:    */   
/* 267:    */   protected boolean shouldSetPosAfterLoading()
/* 268:    */   {
/* 269:360 */     return false;
/* 270:    */   }
/* 271:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.entity.EntityHanging
 * JD-Core Version:    0.7.0.1
 */