/*   1:    */ package net.minecraft.entity.item;
/*   2:    */ 
/*   3:    */ import java.util.Random;
/*   4:    */ import net.minecraft.entity.Entity;
/*   5:    */ import net.minecraft.init.Items;
/*   6:    */ import net.minecraft.item.ItemStack;
/*   7:    */ import net.minecraft.nbt.NBTTagCompound;
/*   8:    */ import net.minecraft.util.AxisAlignedBB;
/*   9:    */ import net.minecraft.util.MathHelper;
/*  10:    */ import net.minecraft.world.World;
/*  11:    */ 
/*  12:    */ public class EntityEnderEye
/*  13:    */   extends Entity
/*  14:    */ {
/*  15:    */   private double targetX;
/*  16:    */   private double targetY;
/*  17:    */   private double targetZ;
/*  18:    */   private int despawnTimer;
/*  19:    */   private boolean shatterOrDrop;
/*  20:    */   private static final String __OBFID = "CL_00001716";
/*  21:    */   
/*  22:    */   public EntityEnderEye(World par1World)
/*  23:    */   {
/*  24: 26 */     super(par1World);
/*  25: 27 */     setSize(0.25F, 0.25F);
/*  26:    */   }
/*  27:    */   
/*  28:    */   protected void entityInit() {}
/*  29:    */   
/*  30:    */   public boolean isInRangeToRenderDist(double par1)
/*  31:    */   {
/*  32: 38 */     double var3 = this.boundingBox.getAverageEdgeLength() * 4.0D;
/*  33: 39 */     var3 *= 64.0D;
/*  34: 40 */     return par1 < var3 * var3;
/*  35:    */   }
/*  36:    */   
/*  37:    */   public EntityEnderEye(World par1World, double par2, double par4, double par6)
/*  38:    */   {
/*  39: 45 */     super(par1World);
/*  40: 46 */     this.despawnTimer = 0;
/*  41: 47 */     setSize(0.25F, 0.25F);
/*  42: 48 */     setPosition(par2, par4, par6);
/*  43: 49 */     this.yOffset = 0.0F;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public void moveTowards(double par1, int par3, double par4)
/*  47:    */   {
/*  48: 58 */     double var6 = par1 - this.posX;
/*  49: 59 */     double var8 = par4 - this.posZ;
/*  50: 60 */     float var10 = MathHelper.sqrt_double(var6 * var6 + var8 * var8);
/*  51: 62 */     if (var10 > 12.0F)
/*  52:    */     {
/*  53: 64 */       this.targetX = (this.posX + var6 / var10 * 12.0D);
/*  54: 65 */       this.targetZ = (this.posZ + var8 / var10 * 12.0D);
/*  55: 66 */       this.targetY = (this.posY + 8.0D);
/*  56:    */     }
/*  57:    */     else
/*  58:    */     {
/*  59: 70 */       this.targetX = par1;
/*  60: 71 */       this.targetY = par3;
/*  61: 72 */       this.targetZ = par4;
/*  62:    */     }
/*  63: 75 */     this.despawnTimer = 0;
/*  64: 76 */     this.shatterOrDrop = (this.rand.nextInt(5) > 0);
/*  65:    */   }
/*  66:    */   
/*  67:    */   public void setVelocity(double par1, double par3, double par5)
/*  68:    */   {
/*  69: 84 */     this.motionX = par1;
/*  70: 85 */     this.motionY = par3;
/*  71: 86 */     this.motionZ = par5;
/*  72: 88 */     if ((this.prevRotationPitch == 0.0F) && (this.prevRotationYaw == 0.0F))
/*  73:    */     {
/*  74: 90 */       float var7 = MathHelper.sqrt_double(par1 * par1 + par5 * par5);
/*  75: 91 */       this.prevRotationYaw = (this.rotationYaw = (float)(Math.atan2(par1, par5) * 180.0D / 3.141592653589793D));
/*  76: 92 */       this.prevRotationPitch = (this.rotationPitch = (float)(Math.atan2(par3, var7) * 180.0D / 3.141592653589793D));
/*  77:    */     }
/*  78:    */   }
/*  79:    */   
/*  80:    */   public void onUpdate()
/*  81:    */   {
/*  82:101 */     this.lastTickPosX = this.posX;
/*  83:102 */     this.lastTickPosY = this.posY;
/*  84:103 */     this.lastTickPosZ = this.posZ;
/*  85:104 */     super.onUpdate();
/*  86:105 */     this.posX += this.motionX;
/*  87:106 */     this.posY += this.motionY;
/*  88:107 */     this.posZ += this.motionZ;
/*  89:108 */     float var1 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
/*  90:109 */     this.rotationYaw = ((float)(Math.atan2(this.motionX, this.motionZ) * 180.0D / 3.141592653589793D));
/*  91:111 */     for (this.rotationPitch = ((float)(Math.atan2(this.motionY, var1) * 180.0D / 3.141592653589793D)); this.rotationPitch - this.prevRotationPitch < -180.0F; this.prevRotationPitch -= 360.0F) {}
/*  92:116 */     while (this.rotationPitch - this.prevRotationPitch >= 180.0F) {
/*  93:118 */       this.prevRotationPitch += 360.0F;
/*  94:    */     }
/*  95:121 */     while (this.rotationYaw - this.prevRotationYaw < -180.0F) {
/*  96:123 */       this.prevRotationYaw -= 360.0F;
/*  97:    */     }
/*  98:126 */     while (this.rotationYaw - this.prevRotationYaw >= 180.0F) {
/*  99:128 */       this.prevRotationYaw += 360.0F;
/* 100:    */     }
/* 101:131 */     this.rotationPitch = (this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * 0.2F);
/* 102:132 */     this.rotationYaw = (this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * 0.2F);
/* 103:134 */     if (!this.worldObj.isClient)
/* 104:    */     {
/* 105:136 */       double var2 = this.targetX - this.posX;
/* 106:137 */       double var4 = this.targetZ - this.posZ;
/* 107:138 */       float var6 = (float)Math.sqrt(var2 * var2 + var4 * var4);
/* 108:139 */       float var7 = (float)Math.atan2(var4, var2);
/* 109:140 */       double var8 = var1 + (var6 - var1) * 0.0025D;
/* 110:142 */       if (var6 < 1.0F)
/* 111:    */       {
/* 112:144 */         var8 *= 0.8D;
/* 113:145 */         this.motionY *= 0.8D;
/* 114:    */       }
/* 115:148 */       this.motionX = (Math.cos(var7) * var8);
/* 116:149 */       this.motionZ = (Math.sin(var7) * var8);
/* 117:151 */       if (this.posY < this.targetY) {
/* 118:153 */         this.motionY += (1.0D - this.motionY) * 0.01499999966472387D;
/* 119:    */       } else {
/* 120:157 */         this.motionY += (-1.0D - this.motionY) * 0.01499999966472387D;
/* 121:    */       }
/* 122:    */     }
/* 123:161 */     float var10 = 0.25F;
/* 124:163 */     if (isInWater()) {
/* 125:165 */       for (int var3 = 0; var3 < 4; var3++) {
/* 126:167 */         this.worldObj.spawnParticle("bubble", this.posX - this.motionX * var10, this.posY - this.motionY * var10, this.posZ - this.motionZ * var10, this.motionX, this.motionY, this.motionZ);
/* 127:    */       }
/* 128:    */     } else {
/* 129:172 */       this.worldObj.spawnParticle("portal", this.posX - this.motionX * var10 + this.rand.nextDouble() * 0.6D - 0.3D, this.posY - this.motionY * var10 - 0.5D, this.posZ - this.motionZ * var10 + this.rand.nextDouble() * 0.6D - 0.3D, this.motionX, this.motionY, this.motionZ);
/* 130:    */     }
/* 131:175 */     if (!this.worldObj.isClient)
/* 132:    */     {
/* 133:177 */       setPosition(this.posX, this.posY, this.posZ);
/* 134:178 */       this.despawnTimer += 1;
/* 135:180 */       if ((this.despawnTimer > 80) && (!this.worldObj.isClient))
/* 136:    */       {
/* 137:182 */         setDead();
/* 138:184 */         if (this.shatterOrDrop) {
/* 139:186 */           this.worldObj.spawnEntityInWorld(new EntityItem(this.worldObj, this.posX, this.posY, this.posZ, new ItemStack(Items.ender_eye)));
/* 140:    */         } else {
/* 141:190 */           this.worldObj.playAuxSFX(2003, (int)Math.round(this.posX), (int)Math.round(this.posY), (int)Math.round(this.posZ), 0);
/* 142:    */         }
/* 143:    */       }
/* 144:    */     }
/* 145:    */   }
/* 146:    */   
/* 147:    */   public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {}
/* 148:    */   
/* 149:    */   public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {}
/* 150:    */   
/* 151:    */   public float getShadowSize()
/* 152:    */   {
/* 153:208 */     return 0.0F;
/* 154:    */   }
/* 155:    */   
/* 156:    */   public float getBrightness(float par1)
/* 157:    */   {
/* 158:216 */     return 1.0F;
/* 159:    */   }
/* 160:    */   
/* 161:    */   public int getBrightnessForRender(float par1)
/* 162:    */   {
/* 163:221 */     return 15728880;
/* 164:    */   }
/* 165:    */   
/* 166:    */   public boolean canAttackWithItem()
/* 167:    */   {
/* 168:229 */     return false;
/* 169:    */   }
/* 170:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.entity.item.EntityEnderEye
 * JD-Core Version:    0.7.0.1
 */