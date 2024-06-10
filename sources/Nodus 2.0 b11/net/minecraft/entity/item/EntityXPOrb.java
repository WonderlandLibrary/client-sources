/*   1:    */ package net.minecraft.entity.item;
/*   2:    */ 
/*   3:    */ import java.util.Random;
/*   4:    */ import net.minecraft.block.Block;
/*   5:    */ import net.minecraft.block.material.Material;
/*   6:    */ import net.minecraft.entity.Entity;
/*   7:    */ import net.minecraft.entity.player.EntityPlayer;
/*   8:    */ import net.minecraft.nbt.NBTTagCompound;
/*   9:    */ import net.minecraft.util.AxisAlignedBB;
/*  10:    */ import net.minecraft.util.DamageSource;
/*  11:    */ import net.minecraft.util.MathHelper;
/*  12:    */ import net.minecraft.world.World;
/*  13:    */ 
/*  14:    */ public class EntityXPOrb
/*  15:    */   extends Entity
/*  16:    */ {
/*  17:    */   public int xpColor;
/*  18:    */   public int xpOrbAge;
/*  19:    */   public int field_70532_c;
/*  20: 23 */   private int xpOrbHealth = 5;
/*  21:    */   private int xpValue;
/*  22:    */   private EntityPlayer closestPlayer;
/*  23:    */   private int xpTargetColor;
/*  24:    */   private static final String __OBFID = "CL_00001544";
/*  25:    */   
/*  26:    */   public EntityXPOrb(World par1World, double par2, double par4, double par6, int par8)
/*  27:    */   {
/*  28: 37 */     super(par1World);
/*  29: 38 */     setSize(0.5F, 0.5F);
/*  30: 39 */     this.yOffset = (this.height / 2.0F);
/*  31: 40 */     setPosition(par2, par4, par6);
/*  32: 41 */     this.rotationYaw = ((float)(Math.random() * 360.0D));
/*  33: 42 */     this.motionX = ((float)(Math.random() * 0.2000000029802322D - 0.1000000014901161D) * 2.0F);
/*  34: 43 */     this.motionY = ((float)(Math.random() * 0.2D) * 2.0F);
/*  35: 44 */     this.motionZ = ((float)(Math.random() * 0.2000000029802322D - 0.1000000014901161D) * 2.0F);
/*  36: 45 */     this.xpValue = par8;
/*  37:    */   }
/*  38:    */   
/*  39:    */   protected boolean canTriggerWalking()
/*  40:    */   {
/*  41: 54 */     return false;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public EntityXPOrb(World par1World)
/*  45:    */   {
/*  46: 59 */     super(par1World);
/*  47: 60 */     setSize(0.25F, 0.25F);
/*  48: 61 */     this.yOffset = (this.height / 2.0F);
/*  49:    */   }
/*  50:    */   
/*  51:    */   protected void entityInit() {}
/*  52:    */   
/*  53:    */   public int getBrightnessForRender(float par1)
/*  54:    */   {
/*  55: 68 */     float var2 = 0.5F;
/*  56: 70 */     if (var2 < 0.0F) {
/*  57: 72 */       var2 = 0.0F;
/*  58:    */     }
/*  59: 75 */     if (var2 > 1.0F) {
/*  60: 77 */       var2 = 1.0F;
/*  61:    */     }
/*  62: 80 */     int var3 = super.getBrightnessForRender(par1);
/*  63: 81 */     int var4 = var3 & 0xFF;
/*  64: 82 */     int var5 = var3 >> 16 & 0xFF;
/*  65: 83 */     var4 += (int)(var2 * 15.0F * 16.0F);
/*  66: 85 */     if (var4 > 240) {
/*  67: 87 */       var4 = 240;
/*  68:    */     }
/*  69: 90 */     return var4 | var5 << 16;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public void onUpdate()
/*  73:    */   {
/*  74: 98 */     super.onUpdate();
/*  75:100 */     if (this.field_70532_c > 0) {
/*  76:102 */       this.field_70532_c -= 1;
/*  77:    */     }
/*  78:105 */     this.prevPosX = this.posX;
/*  79:106 */     this.prevPosY = this.posY;
/*  80:107 */     this.prevPosZ = this.posZ;
/*  81:108 */     this.motionY -= 0.02999999932944775D;
/*  82:110 */     if (this.worldObj.getBlock(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ)).getMaterial() == Material.lava)
/*  83:    */     {
/*  84:112 */       this.motionY = 0.2000000029802322D;
/*  85:113 */       this.motionX = ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F);
/*  86:114 */       this.motionZ = ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F);
/*  87:115 */       playSound("random.fizz", 0.4F, 2.0F + this.rand.nextFloat() * 0.4F);
/*  88:    */     }
/*  89:118 */     func_145771_j(this.posX, (this.boundingBox.minY + this.boundingBox.maxY) / 2.0D, this.posZ);
/*  90:119 */     double var1 = 8.0D;
/*  91:121 */     if (this.xpTargetColor < this.xpColor - 20 + getEntityId() % 100)
/*  92:    */     {
/*  93:123 */       if ((this.closestPlayer == null) || (this.closestPlayer.getDistanceSqToEntity(this) > var1 * var1)) {
/*  94:125 */         this.closestPlayer = this.worldObj.getClosestPlayerToEntity(this, var1);
/*  95:    */       }
/*  96:128 */       this.xpTargetColor = this.xpColor;
/*  97:    */     }
/*  98:131 */     if (this.closestPlayer != null)
/*  99:    */     {
/* 100:133 */       double var3 = (this.closestPlayer.posX - this.posX) / var1;
/* 101:134 */       double var5 = (this.closestPlayer.posY + this.closestPlayer.getEyeHeight() - this.posY) / var1;
/* 102:135 */       double var7 = (this.closestPlayer.posZ - this.posZ) / var1;
/* 103:136 */       double var9 = Math.sqrt(var3 * var3 + var5 * var5 + var7 * var7);
/* 104:137 */       double var11 = 1.0D - var9;
/* 105:139 */       if (var11 > 0.0D)
/* 106:    */       {
/* 107:141 */         var11 *= var11;
/* 108:142 */         this.motionX += var3 / var9 * var11 * 0.1D;
/* 109:143 */         this.motionY += var5 / var9 * var11 * 0.1D;
/* 110:144 */         this.motionZ += var7 / var9 * var11 * 0.1D;
/* 111:    */       }
/* 112:    */     }
/* 113:148 */     moveEntity(this.motionX, this.motionY, this.motionZ);
/* 114:149 */     float var13 = 0.98F;
/* 115:151 */     if (this.onGround) {
/* 116:153 */       var13 = this.worldObj.getBlock(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.boundingBox.minY) - 1, MathHelper.floor_double(this.posZ)).slipperiness * 0.98F;
/* 117:    */     }
/* 118:156 */     this.motionX *= var13;
/* 119:157 */     this.motionY *= 0.9800000190734863D;
/* 120:158 */     this.motionZ *= var13;
/* 121:160 */     if (this.onGround) {
/* 122:162 */       this.motionY *= -0.8999999761581421D;
/* 123:    */     }
/* 124:165 */     this.xpColor += 1;
/* 125:166 */     this.xpOrbAge += 1;
/* 126:168 */     if (this.xpOrbAge >= 6000) {
/* 127:170 */       setDead();
/* 128:    */     }
/* 129:    */   }
/* 130:    */   
/* 131:    */   public boolean handleWaterMovement()
/* 132:    */   {
/* 133:179 */     return this.worldObj.handleMaterialAcceleration(this.boundingBox, Material.water, this);
/* 134:    */   }
/* 135:    */   
/* 136:    */   protected void dealFireDamage(int par1)
/* 137:    */   {
/* 138:188 */     attackEntityFrom(DamageSource.inFire, par1);
/* 139:    */   }
/* 140:    */   
/* 141:    */   public boolean attackEntityFrom(DamageSource par1DamageSource, float par2)
/* 142:    */   {
/* 143:196 */     if (isEntityInvulnerable()) {
/* 144:198 */       return false;
/* 145:    */     }
/* 146:202 */     setBeenAttacked();
/* 147:203 */     this.xpOrbHealth = ((int)(this.xpOrbHealth - par2));
/* 148:205 */     if (this.xpOrbHealth <= 0) {
/* 149:207 */       setDead();
/* 150:    */     }
/* 151:210 */     return false;
/* 152:    */   }
/* 153:    */   
/* 154:    */   public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
/* 155:    */   {
/* 156:219 */     par1NBTTagCompound.setShort("Health", (short)(byte)this.xpOrbHealth);
/* 157:220 */     par1NBTTagCompound.setShort("Age", (short)this.xpOrbAge);
/* 158:221 */     par1NBTTagCompound.setShort("Value", (short)this.xpValue);
/* 159:    */   }
/* 160:    */   
/* 161:    */   public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
/* 162:    */   {
/* 163:229 */     this.xpOrbHealth = (par1NBTTagCompound.getShort("Health") & 0xFF);
/* 164:230 */     this.xpOrbAge = par1NBTTagCompound.getShort("Age");
/* 165:231 */     this.xpValue = par1NBTTagCompound.getShort("Value");
/* 166:    */   }
/* 167:    */   
/* 168:    */   public void onCollideWithPlayer(EntityPlayer par1EntityPlayer)
/* 169:    */   {
/* 170:239 */     if (!this.worldObj.isClient) {
/* 171:241 */       if ((this.field_70532_c == 0) && (par1EntityPlayer.xpCooldown == 0))
/* 172:    */       {
/* 173:243 */         par1EntityPlayer.xpCooldown = 2;
/* 174:244 */         this.worldObj.playSoundAtEntity(par1EntityPlayer, "random.orb", 0.1F, 0.5F * ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.7F + 1.8F));
/* 175:245 */         par1EntityPlayer.onItemPickup(this, 1);
/* 176:246 */         par1EntityPlayer.addExperience(this.xpValue);
/* 177:247 */         setDead();
/* 178:    */       }
/* 179:    */     }
/* 180:    */   }
/* 181:    */   
/* 182:    */   public int getXpValue()
/* 183:    */   {
/* 184:257 */     return this.xpValue;
/* 185:    */   }
/* 186:    */   
/* 187:    */   public int getTextureByXP()
/* 188:    */   {
/* 189:266 */     return this.xpValue >= 3 ? 1 : this.xpValue >= 7 ? 2 : this.xpValue >= 17 ? 3 : this.xpValue >= 37 ? 4 : this.xpValue >= 73 ? 5 : this.xpValue >= 149 ? 6 : this.xpValue >= 307 ? 7 : this.xpValue >= 617 ? 8 : this.xpValue >= 1237 ? 9 : this.xpValue >= 2477 ? 10 : 0;
/* 190:    */   }
/* 191:    */   
/* 192:    */   public static int getXPSplit(int par0)
/* 193:    */   {
/* 194:274 */     return par0 >= 3 ? 3 : par0 >= 7 ? 7 : par0 >= 17 ? 17 : par0 >= 37 ? 37 : par0 >= 73 ? 73 : par0 >= 149 ? 149 : par0 >= 307 ? 307 : par0 >= 617 ? 617 : par0 >= 1237 ? 1237 : par0 >= 2477 ? 2477 : 1;
/* 195:    */   }
/* 196:    */   
/* 197:    */   public boolean canAttackWithItem()
/* 198:    */   {
/* 199:282 */     return false;
/* 200:    */   }
/* 201:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.entity.item.EntityXPOrb
 * JD-Core Version:    0.7.0.1
 */