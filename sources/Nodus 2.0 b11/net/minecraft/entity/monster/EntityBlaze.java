/*   1:    */ package net.minecraft.entity.monster;
/*   2:    */ 
/*   3:    */ import java.util.Random;
/*   4:    */ import net.minecraft.entity.DataWatcher;
/*   5:    */ import net.minecraft.entity.Entity;
/*   6:    */ import net.minecraft.entity.SharedMonsterAttributes;
/*   7:    */ import net.minecraft.entity.ai.attributes.IAttributeInstance;
/*   8:    */ import net.minecraft.entity.projectile.EntitySmallFireball;
/*   9:    */ import net.minecraft.init.Items;
/*  10:    */ import net.minecraft.item.Item;
/*  11:    */ import net.minecraft.util.AxisAlignedBB;
/*  12:    */ import net.minecraft.util.DamageSource;
/*  13:    */ import net.minecraft.util.MathHelper;
/*  14:    */ import net.minecraft.world.World;
/*  15:    */ 
/*  16:    */ public class EntityBlaze
/*  17:    */   extends EntityMob
/*  18:    */ {
/*  19: 16 */   private float heightOffset = 0.5F;
/*  20:    */   private int heightOffsetUpdateTime;
/*  21:    */   private int field_70846_g;
/*  22:    */   private static final String __OBFID = "CL_00001682";
/*  23:    */   
/*  24:    */   public EntityBlaze(World par1World)
/*  25:    */   {
/*  26: 25 */     super(par1World);
/*  27: 26 */     this.isImmuneToFire = true;
/*  28: 27 */     this.experienceValue = 10;
/*  29:    */   }
/*  30:    */   
/*  31:    */   protected void applyEntityAttributes()
/*  32:    */   {
/*  33: 32 */     super.applyEntityAttributes();
/*  34: 33 */     getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(6.0D);
/*  35:    */   }
/*  36:    */   
/*  37:    */   protected void entityInit()
/*  38:    */   {
/*  39: 38 */     super.entityInit();
/*  40: 39 */     this.dataWatcher.addObject(16, new Byte((byte)0));
/*  41:    */   }
/*  42:    */   
/*  43:    */   protected String getLivingSound()
/*  44:    */   {
/*  45: 47 */     return "mob.blaze.breathe";
/*  46:    */   }
/*  47:    */   
/*  48:    */   protected String getHurtSound()
/*  49:    */   {
/*  50: 55 */     return "mob.blaze.hit";
/*  51:    */   }
/*  52:    */   
/*  53:    */   protected String getDeathSound()
/*  54:    */   {
/*  55: 63 */     return "mob.blaze.death";
/*  56:    */   }
/*  57:    */   
/*  58:    */   public int getBrightnessForRender(float par1)
/*  59:    */   {
/*  60: 68 */     return 15728880;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public float getBrightness(float par1)
/*  64:    */   {
/*  65: 76 */     return 1.0F;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public void onLivingUpdate()
/*  69:    */   {
/*  70: 85 */     if (!this.worldObj.isClient)
/*  71:    */     {
/*  72: 87 */       if (isWet()) {
/*  73: 89 */         attackEntityFrom(DamageSource.drown, 1.0F);
/*  74:    */       }
/*  75: 92 */       this.heightOffsetUpdateTime -= 1;
/*  76: 94 */       if (this.heightOffsetUpdateTime <= 0)
/*  77:    */       {
/*  78: 96 */         this.heightOffsetUpdateTime = 100;
/*  79: 97 */         this.heightOffset = (0.5F + (float)this.rand.nextGaussian() * 3.0F);
/*  80:    */       }
/*  81:100 */       if ((getEntityToAttack() != null) && (getEntityToAttack().posY + getEntityToAttack().getEyeHeight() > this.posY + getEyeHeight() + this.heightOffset)) {
/*  82:102 */         this.motionY += (0.300000011920929D - this.motionY) * 0.300000011920929D;
/*  83:    */       }
/*  84:    */     }
/*  85:106 */     if (this.rand.nextInt(24) == 0) {
/*  86:108 */       this.worldObj.playSoundEffect(this.posX + 0.5D, this.posY + 0.5D, this.posZ + 0.5D, "fire.fire", 1.0F + this.rand.nextFloat(), this.rand.nextFloat() * 0.7F + 0.3F);
/*  87:    */     }
/*  88:111 */     if ((!this.onGround) && (this.motionY < 0.0D)) {
/*  89:113 */       this.motionY *= 0.6D;
/*  90:    */     }
/*  91:116 */     for (int var1 = 0; var1 < 2; var1++) {
/*  92:118 */       this.worldObj.spawnParticle("largesmoke", this.posX + (this.rand.nextDouble() - 0.5D) * this.width, this.posY + this.rand.nextDouble() * this.height, this.posZ + (this.rand.nextDouble() - 0.5D) * this.width, 0.0D, 0.0D, 0.0D);
/*  93:    */     }
/*  94:121 */     super.onLivingUpdate();
/*  95:    */   }
/*  96:    */   
/*  97:    */   protected void attackEntity(Entity par1Entity, float par2)
/*  98:    */   {
/*  99:129 */     if ((this.attackTime <= 0) && (par2 < 2.0F) && (par1Entity.boundingBox.maxY > this.boundingBox.minY) && (par1Entity.boundingBox.minY < this.boundingBox.maxY))
/* 100:    */     {
/* 101:131 */       this.attackTime = 20;
/* 102:132 */       attackEntityAsMob(par1Entity);
/* 103:    */     }
/* 104:134 */     else if (par2 < 30.0F)
/* 105:    */     {
/* 106:136 */       double var3 = par1Entity.posX - this.posX;
/* 107:137 */       double var5 = par1Entity.boundingBox.minY + par1Entity.height / 2.0F - (this.posY + this.height / 2.0F);
/* 108:138 */       double var7 = par1Entity.posZ - this.posZ;
/* 109:140 */       if (this.attackTime == 0)
/* 110:    */       {
/* 111:142 */         this.field_70846_g += 1;
/* 112:144 */         if (this.field_70846_g == 1)
/* 113:    */         {
/* 114:146 */           this.attackTime = 60;
/* 115:147 */           func_70844_e(true);
/* 116:    */         }
/* 117:149 */         else if (this.field_70846_g <= 4)
/* 118:    */         {
/* 119:151 */           this.attackTime = 6;
/* 120:    */         }
/* 121:    */         else
/* 122:    */         {
/* 123:155 */           this.attackTime = 100;
/* 124:156 */           this.field_70846_g = 0;
/* 125:157 */           func_70844_e(false);
/* 126:    */         }
/* 127:160 */         if (this.field_70846_g > 1)
/* 128:    */         {
/* 129:162 */           float var9 = MathHelper.sqrt_float(par2) * 0.5F;
/* 130:163 */           this.worldObj.playAuxSFXAtEntity(null, 1009, (int)this.posX, (int)this.posY, (int)this.posZ, 0);
/* 131:165 */           for (int var10 = 0; var10 < 1; var10++)
/* 132:    */           {
/* 133:167 */             EntitySmallFireball var11 = new EntitySmallFireball(this.worldObj, this, var3 + this.rand.nextGaussian() * var9, var5, var7 + this.rand.nextGaussian() * var9);
/* 134:168 */             var11.posY = (this.posY + this.height / 2.0F + 0.5D);
/* 135:169 */             this.worldObj.spawnEntityInWorld(var11);
/* 136:    */           }
/* 137:    */         }
/* 138:    */       }
/* 139:174 */       this.rotationYaw = ((float)(Math.atan2(var7, var3) * 180.0D / 3.141592653589793D) - 90.0F);
/* 140:175 */       this.hasAttacked = true;
/* 141:    */     }
/* 142:    */   }
/* 143:    */   
/* 144:    */   protected void fall(float par1) {}
/* 145:    */   
/* 146:    */   protected Item func_146068_u()
/* 147:    */   {
/* 148:186 */     return Items.blaze_rod;
/* 149:    */   }
/* 150:    */   
/* 151:    */   public boolean isBurning()
/* 152:    */   {
/* 153:194 */     return func_70845_n();
/* 154:    */   }
/* 155:    */   
/* 156:    */   protected void dropFewItems(boolean par1, int par2)
/* 157:    */   {
/* 158:202 */     if (par1)
/* 159:    */     {
/* 160:204 */       int var3 = this.rand.nextInt(2 + par2);
/* 161:206 */       for (int var4 = 0; var4 < var3; var4++) {
/* 162:208 */         func_145779_a(Items.blaze_rod, 1);
/* 163:    */       }
/* 164:    */     }
/* 165:    */   }
/* 166:    */   
/* 167:    */   public boolean func_70845_n()
/* 168:    */   {
/* 169:215 */     return (this.dataWatcher.getWatchableObjectByte(16) & 0x1) != 0;
/* 170:    */   }
/* 171:    */   
/* 172:    */   public void func_70844_e(boolean par1)
/* 173:    */   {
/* 174:220 */     byte var2 = this.dataWatcher.getWatchableObjectByte(16);
/* 175:222 */     if (par1) {
/* 176:224 */       var2 = (byte)(var2 | 0x1);
/* 177:    */     } else {
/* 178:228 */       var2 = (byte)(var2 & 0xFFFFFFFE);
/* 179:    */     }
/* 180:231 */     this.dataWatcher.updateObject(16, Byte.valueOf(var2));
/* 181:    */   }
/* 182:    */   
/* 183:    */   protected boolean isValidLightLevel()
/* 184:    */   {
/* 185:239 */     return true;
/* 186:    */   }
/* 187:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.entity.monster.EntityBlaze
 * JD-Core Version:    0.7.0.1
 */