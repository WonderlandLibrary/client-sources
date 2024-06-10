/*   1:    */ package net.minecraft.entity.item;
/*   2:    */ 
/*   3:    */ import java.util.Random;
/*   4:    */ import net.minecraft.entity.DataWatcher;
/*   5:    */ import net.minecraft.entity.Entity;
/*   6:    */ import net.minecraft.item.ItemStack;
/*   7:    */ import net.minecraft.nbt.NBTTagCompound;
/*   8:    */ import net.minecraft.util.MathHelper;
/*   9:    */ import net.minecraft.world.World;
/*  10:    */ 
/*  11:    */ public class EntityFireworkRocket
/*  12:    */   extends Entity
/*  13:    */ {
/*  14:    */   private int fireworkAge;
/*  15:    */   private int lifetime;
/*  16:    */   private static final String __OBFID = "CL_00001718";
/*  17:    */   
/*  18:    */   public EntityFireworkRocket(World par1World)
/*  19:    */   {
/*  20: 22 */     super(par1World);
/*  21: 23 */     setSize(0.25F, 0.25F);
/*  22:    */   }
/*  23:    */   
/*  24:    */   protected void entityInit()
/*  25:    */   {
/*  26: 28 */     this.dataWatcher.addObjectByDataType(8, 5);
/*  27:    */   }
/*  28:    */   
/*  29:    */   public boolean isInRangeToRenderDist(double par1)
/*  30:    */   {
/*  31: 37 */     return par1 < 4096.0D;
/*  32:    */   }
/*  33:    */   
/*  34:    */   public EntityFireworkRocket(World par1World, double par2, double par4, double par6, ItemStack par8ItemStack)
/*  35:    */   {
/*  36: 42 */     super(par1World);
/*  37: 43 */     this.fireworkAge = 0;
/*  38: 44 */     setSize(0.25F, 0.25F);
/*  39: 45 */     setPosition(par2, par4, par6);
/*  40: 46 */     this.yOffset = 0.0F;
/*  41: 47 */     int var9 = 1;
/*  42: 49 */     if ((par8ItemStack != null) && (par8ItemStack.hasTagCompound()))
/*  43:    */     {
/*  44: 51 */       this.dataWatcher.updateObject(8, par8ItemStack);
/*  45: 52 */       NBTTagCompound var10 = par8ItemStack.getTagCompound();
/*  46: 53 */       NBTTagCompound var11 = var10.getCompoundTag("Fireworks");
/*  47: 55 */       if (var11 != null) {
/*  48: 57 */         var9 += var11.getByte("Flight");
/*  49:    */       }
/*  50:    */     }
/*  51: 61 */     this.motionX = (this.rand.nextGaussian() * 0.001D);
/*  52: 62 */     this.motionZ = (this.rand.nextGaussian() * 0.001D);
/*  53: 63 */     this.motionY = 0.05D;
/*  54: 64 */     this.lifetime = (10 * var9 + this.rand.nextInt(6) + this.rand.nextInt(7));
/*  55:    */   }
/*  56:    */   
/*  57:    */   public void setVelocity(double par1, double par3, double par5)
/*  58:    */   {
/*  59: 72 */     this.motionX = par1;
/*  60: 73 */     this.motionY = par3;
/*  61: 74 */     this.motionZ = par5;
/*  62: 76 */     if ((this.prevRotationPitch == 0.0F) && (this.prevRotationYaw == 0.0F))
/*  63:    */     {
/*  64: 78 */       float var7 = MathHelper.sqrt_double(par1 * par1 + par5 * par5);
/*  65: 79 */       this.prevRotationYaw = (this.rotationYaw = (float)(Math.atan2(par1, par5) * 180.0D / 3.141592653589793D));
/*  66: 80 */       this.prevRotationPitch = (this.rotationPitch = (float)(Math.atan2(par3, var7) * 180.0D / 3.141592653589793D));
/*  67:    */     }
/*  68:    */   }
/*  69:    */   
/*  70:    */   public void onUpdate()
/*  71:    */   {
/*  72: 89 */     this.lastTickPosX = this.posX;
/*  73: 90 */     this.lastTickPosY = this.posY;
/*  74: 91 */     this.lastTickPosZ = this.posZ;
/*  75: 92 */     super.onUpdate();
/*  76: 93 */     this.motionX *= 1.15D;
/*  77: 94 */     this.motionZ *= 1.15D;
/*  78: 95 */     this.motionY += 0.04D;
/*  79: 96 */     moveEntity(this.motionX, this.motionY, this.motionZ);
/*  80: 97 */     float var1 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
/*  81: 98 */     this.rotationYaw = ((float)(Math.atan2(this.motionX, this.motionZ) * 180.0D / 3.141592653589793D));
/*  82:100 */     for (this.rotationPitch = ((float)(Math.atan2(this.motionY, var1) * 180.0D / 3.141592653589793D)); this.rotationPitch - this.prevRotationPitch < -180.0F; this.prevRotationPitch -= 360.0F) {}
/*  83:105 */     while (this.rotationPitch - this.prevRotationPitch >= 180.0F) {
/*  84:107 */       this.prevRotationPitch += 360.0F;
/*  85:    */     }
/*  86:110 */     while (this.rotationYaw - this.prevRotationYaw < -180.0F) {
/*  87:112 */       this.prevRotationYaw -= 360.0F;
/*  88:    */     }
/*  89:115 */     while (this.rotationYaw - this.prevRotationYaw >= 180.0F) {
/*  90:117 */       this.prevRotationYaw += 360.0F;
/*  91:    */     }
/*  92:120 */     this.rotationPitch = (this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * 0.2F);
/*  93:121 */     this.rotationYaw = (this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * 0.2F);
/*  94:123 */     if (this.fireworkAge == 0) {
/*  95:125 */       this.worldObj.playSoundAtEntity(this, "fireworks.launch", 3.0F, 1.0F);
/*  96:    */     }
/*  97:128 */     this.fireworkAge += 1;
/*  98:130 */     if ((this.worldObj.isClient) && (this.fireworkAge % 2 < 2)) {
/*  99:132 */       this.worldObj.spawnParticle("fireworksSpark", this.posX, this.posY - 0.3D, this.posZ, this.rand.nextGaussian() * 0.05D, -this.motionY * 0.5D, this.rand.nextGaussian() * 0.05D);
/* 100:    */     }
/* 101:135 */     if ((!this.worldObj.isClient) && (this.fireworkAge > this.lifetime))
/* 102:    */     {
/* 103:137 */       this.worldObj.setEntityState(this, (byte)17);
/* 104:138 */       setDead();
/* 105:    */     }
/* 106:    */   }
/* 107:    */   
/* 108:    */   public void handleHealthUpdate(byte par1)
/* 109:    */   {
/* 110:144 */     if ((par1 == 17) && (this.worldObj.isClient))
/* 111:    */     {
/* 112:146 */       ItemStack var2 = this.dataWatcher.getWatchableObjectItemStack(8);
/* 113:147 */       NBTTagCompound var3 = null;
/* 114:149 */       if ((var2 != null) && (var2.hasTagCompound())) {
/* 115:151 */         var3 = var2.getTagCompound().getCompoundTag("Fireworks");
/* 116:    */       }
/* 117:154 */       this.worldObj.makeFireworks(this.posX, this.posY, this.posZ, this.motionX, this.motionY, this.motionZ, var3);
/* 118:    */     }
/* 119:157 */     super.handleHealthUpdate(par1);
/* 120:    */   }
/* 121:    */   
/* 122:    */   public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
/* 123:    */   {
/* 124:165 */     par1NBTTagCompound.setInteger("Life", this.fireworkAge);
/* 125:166 */     par1NBTTagCompound.setInteger("LifeTime", this.lifetime);
/* 126:167 */     ItemStack var2 = this.dataWatcher.getWatchableObjectItemStack(8);
/* 127:169 */     if (var2 != null)
/* 128:    */     {
/* 129:171 */       NBTTagCompound var3 = new NBTTagCompound();
/* 130:172 */       var2.writeToNBT(var3);
/* 131:173 */       par1NBTTagCompound.setTag("FireworksItem", var3);
/* 132:    */     }
/* 133:    */   }
/* 134:    */   
/* 135:    */   public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
/* 136:    */   {
/* 137:182 */     this.fireworkAge = par1NBTTagCompound.getInteger("Life");
/* 138:183 */     this.lifetime = par1NBTTagCompound.getInteger("LifeTime");
/* 139:184 */     NBTTagCompound var2 = par1NBTTagCompound.getCompoundTag("FireworksItem");
/* 140:186 */     if (var2 != null)
/* 141:    */     {
/* 142:188 */       ItemStack var3 = ItemStack.loadItemStackFromNBT(var2);
/* 143:190 */       if (var3 != null) {
/* 144:192 */         this.dataWatcher.updateObject(8, var3);
/* 145:    */       }
/* 146:    */     }
/* 147:    */   }
/* 148:    */   
/* 149:    */   public float getShadowSize()
/* 150:    */   {
/* 151:199 */     return 0.0F;
/* 152:    */   }
/* 153:    */   
/* 154:    */   public float getBrightness(float par1)
/* 155:    */   {
/* 156:207 */     return super.getBrightness(par1);
/* 157:    */   }
/* 158:    */   
/* 159:    */   public int getBrightnessForRender(float par1)
/* 160:    */   {
/* 161:212 */     return super.getBrightnessForRender(par1);
/* 162:    */   }
/* 163:    */   
/* 164:    */   public boolean canAttackWithItem()
/* 165:    */   {
/* 166:220 */     return false;
/* 167:    */   }
/* 168:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.entity.item.EntityFireworkRocket
 * JD-Core Version:    0.7.0.1
 */