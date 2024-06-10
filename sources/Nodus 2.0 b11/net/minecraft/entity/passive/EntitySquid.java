/*   1:    */ package net.minecraft.entity.passive;
/*   2:    */ 
/*   3:    */ import java.util.Random;
/*   4:    */ import net.minecraft.block.material.Material;
/*   5:    */ import net.minecraft.entity.SharedMonsterAttributes;
/*   6:    */ import net.minecraft.entity.ai.attributes.IAttributeInstance;
/*   7:    */ import net.minecraft.init.Items;
/*   8:    */ import net.minecraft.item.Item;
/*   9:    */ import net.minecraft.item.ItemStack;
/*  10:    */ import net.minecraft.util.AxisAlignedBB;
/*  11:    */ import net.minecraft.util.MathHelper;
/*  12:    */ import net.minecraft.world.World;
/*  13:    */ 
/*  14:    */ public class EntitySquid
/*  15:    */   extends EntityWaterMob
/*  16:    */ {
/*  17:    */   public float squidPitch;
/*  18:    */   public float prevSquidPitch;
/*  19:    */   public float squidYaw;
/*  20:    */   public float prevSquidYaw;
/*  21:    */   public float squidRotation;
/*  22:    */   public float prevSquidRotation;
/*  23:    */   public float tentacleAngle;
/*  24:    */   public float lastTentacleAngle;
/*  25:    */   private float randomMotionSpeed;
/*  26:    */   private float rotationVelocity;
/*  27:    */   private float field_70871_bB;
/*  28:    */   private float randomMotionVecX;
/*  29:    */   private float randomMotionVecY;
/*  30:    */   private float randomMotionVecZ;
/*  31:    */   private static final String __OBFID = "CL_00001651";
/*  32:    */   
/*  33:    */   public EntitySquid(World par1World)
/*  34:    */   {
/*  35: 43 */     super(par1World);
/*  36: 44 */     setSize(0.95F, 0.95F);
/*  37: 45 */     this.rotationVelocity = (1.0F / (this.rand.nextFloat() + 1.0F) * 0.2F);
/*  38:    */   }
/*  39:    */   
/*  40:    */   protected void applyEntityAttributes()
/*  41:    */   {
/*  42: 50 */     super.applyEntityAttributes();
/*  43: 51 */     getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(10.0D);
/*  44:    */   }
/*  45:    */   
/*  46:    */   protected String getLivingSound()
/*  47:    */   {
/*  48: 59 */     return null;
/*  49:    */   }
/*  50:    */   
/*  51:    */   protected String getHurtSound()
/*  52:    */   {
/*  53: 67 */     return null;
/*  54:    */   }
/*  55:    */   
/*  56:    */   protected String getDeathSound()
/*  57:    */   {
/*  58: 75 */     return null;
/*  59:    */   }
/*  60:    */   
/*  61:    */   protected float getSoundVolume()
/*  62:    */   {
/*  63: 83 */     return 0.4F;
/*  64:    */   }
/*  65:    */   
/*  66:    */   protected Item func_146068_u()
/*  67:    */   {
/*  68: 88 */     return Item.getItemById(0);
/*  69:    */   }
/*  70:    */   
/*  71:    */   protected boolean canTriggerWalking()
/*  72:    */   {
/*  73: 97 */     return false;
/*  74:    */   }
/*  75:    */   
/*  76:    */   protected void dropFewItems(boolean par1, int par2)
/*  77:    */   {
/*  78:105 */     int var3 = this.rand.nextInt(3 + par2) + 1;
/*  79:107 */     for (int var4 = 0; var4 < var3; var4++) {
/*  80:109 */       entityDropItem(new ItemStack(Items.dye, 1, 0), 0.0F);
/*  81:    */     }
/*  82:    */   }
/*  83:    */   
/*  84:    */   public boolean isInWater()
/*  85:    */   {
/*  86:119 */     return this.worldObj.handleMaterialAcceleration(this.boundingBox.expand(0.0D, -0.6000000238418579D, 0.0D), Material.water, this);
/*  87:    */   }
/*  88:    */   
/*  89:    */   public void onLivingUpdate()
/*  90:    */   {
/*  91:128 */     super.onLivingUpdate();
/*  92:129 */     this.prevSquidPitch = this.squidPitch;
/*  93:130 */     this.prevSquidYaw = this.squidYaw;
/*  94:131 */     this.prevSquidRotation = this.squidRotation;
/*  95:132 */     this.lastTentacleAngle = this.tentacleAngle;
/*  96:133 */     this.squidRotation += this.rotationVelocity;
/*  97:135 */     if (this.squidRotation > 6.283186F)
/*  98:    */     {
/*  99:137 */       this.squidRotation -= 6.283186F;
/* 100:139 */       if (this.rand.nextInt(10) == 0) {
/* 101:141 */         this.rotationVelocity = (1.0F / (this.rand.nextFloat() + 1.0F) * 0.2F);
/* 102:    */       }
/* 103:    */     }
/* 104:145 */     if (isInWater())
/* 105:    */     {
/* 106:149 */       if (this.squidRotation < 3.141593F)
/* 107:    */       {
/* 108:151 */         float var1 = this.squidRotation / 3.141593F;
/* 109:152 */         this.tentacleAngle = (MathHelper.sin(var1 * var1 * 3.141593F) * 3.141593F * 0.25F);
/* 110:154 */         if (var1 > 0.75D)
/* 111:    */         {
/* 112:156 */           this.randomMotionSpeed = 1.0F;
/* 113:157 */           this.field_70871_bB = 1.0F;
/* 114:    */         }
/* 115:    */         else
/* 116:    */         {
/* 117:161 */           this.field_70871_bB *= 0.8F;
/* 118:    */         }
/* 119:    */       }
/* 120:    */       else
/* 121:    */       {
/* 122:166 */         this.tentacleAngle = 0.0F;
/* 123:167 */         this.randomMotionSpeed *= 0.9F;
/* 124:168 */         this.field_70871_bB *= 0.99F;
/* 125:    */       }
/* 126:171 */       if (!this.worldObj.isClient)
/* 127:    */       {
/* 128:173 */         this.motionX = (this.randomMotionVecX * this.randomMotionSpeed);
/* 129:174 */         this.motionY = (this.randomMotionVecY * this.randomMotionSpeed);
/* 130:175 */         this.motionZ = (this.randomMotionVecZ * this.randomMotionSpeed);
/* 131:    */       }
/* 132:178 */       float var1 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
/* 133:179 */       this.renderYawOffset += (-(float)Math.atan2(this.motionX, this.motionZ) * 180.0F / 3.141593F - this.renderYawOffset) * 0.1F;
/* 134:180 */       this.rotationYaw = this.renderYawOffset;
/* 135:181 */       this.squidYaw += 3.141593F * this.field_70871_bB * 1.5F;
/* 136:182 */       this.squidPitch += (-(float)Math.atan2(var1, this.motionY) * 180.0F / 3.141593F - this.squidPitch) * 0.1F;
/* 137:    */     }
/* 138:    */     else
/* 139:    */     {
/* 140:186 */       this.tentacleAngle = (MathHelper.abs(MathHelper.sin(this.squidRotation)) * 3.141593F * 0.25F);
/* 141:188 */       if (!this.worldObj.isClient)
/* 142:    */       {
/* 143:190 */         this.motionX = 0.0D;
/* 144:191 */         this.motionY -= 0.08D;
/* 145:192 */         this.motionY *= 0.9800000190734863D;
/* 146:193 */         this.motionZ = 0.0D;
/* 147:    */       }
/* 148:196 */       this.squidPitch = ((float)(this.squidPitch + (-90.0F - this.squidPitch) * 0.02D));
/* 149:    */     }
/* 150:    */   }
/* 151:    */   
/* 152:    */   public void moveEntityWithHeading(float par1, float par2)
/* 153:    */   {
/* 154:205 */     moveEntity(this.motionX, this.motionY, this.motionZ);
/* 155:    */   }
/* 156:    */   
/* 157:    */   protected void updateEntityActionState()
/* 158:    */   {
/* 159:210 */     this.entityAge += 1;
/* 160:212 */     if (this.entityAge > 100)
/* 161:    */     {
/* 162:214 */       this.randomMotionVecX = (this.randomMotionVecY = this.randomMotionVecZ = 0.0F);
/* 163:    */     }
/* 164:216 */     else if ((this.rand.nextInt(50) == 0) || (!this.inWater) || ((this.randomMotionVecX == 0.0F) && (this.randomMotionVecY == 0.0F) && (this.randomMotionVecZ == 0.0F)))
/* 165:    */     {
/* 166:218 */       float var1 = this.rand.nextFloat() * 3.141593F * 2.0F;
/* 167:219 */       this.randomMotionVecX = (MathHelper.cos(var1) * 0.2F);
/* 168:220 */       this.randomMotionVecY = (-0.1F + this.rand.nextFloat() * 0.2F);
/* 169:221 */       this.randomMotionVecZ = (MathHelper.sin(var1) * 0.2F);
/* 170:    */     }
/* 171:224 */     despawnEntity();
/* 172:    */   }
/* 173:    */   
/* 174:    */   public boolean getCanSpawnHere()
/* 175:    */   {
/* 176:232 */     return (this.posY > 45.0D) && (this.posY < 63.0D) && (super.getCanSpawnHere());
/* 177:    */   }
/* 178:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.entity.passive.EntitySquid
 * JD-Core Version:    0.7.0.1
 */