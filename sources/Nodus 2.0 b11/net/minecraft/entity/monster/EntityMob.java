/*   1:    */ package net.minecraft.entity.monster;
/*   2:    */ 
/*   3:    */ import java.util.Random;
/*   4:    */ import net.minecraft.enchantment.EnchantmentHelper;
/*   5:    */ import net.minecraft.entity.Entity;
/*   6:    */ import net.minecraft.entity.EntityCreature;
/*   7:    */ import net.minecraft.entity.EntityLivingBase;
/*   8:    */ import net.minecraft.entity.SharedMonsterAttributes;
/*   9:    */ import net.minecraft.entity.ai.attributes.BaseAttributeMap;
/*  10:    */ import net.minecraft.entity.ai.attributes.IAttributeInstance;
/*  11:    */ import net.minecraft.entity.player.EntityPlayer;
/*  12:    */ import net.minecraft.util.AxisAlignedBB;
/*  13:    */ import net.minecraft.util.DamageSource;
/*  14:    */ import net.minecraft.util.MathHelper;
/*  15:    */ import net.minecraft.world.EnumDifficulty;
/*  16:    */ import net.minecraft.world.EnumSkyBlock;
/*  17:    */ import net.minecraft.world.World;
/*  18:    */ 
/*  19:    */ public abstract class EntityMob
/*  20:    */   extends EntityCreature
/*  21:    */   implements IMob
/*  22:    */ {
/*  23:    */   private static final String __OBFID = "CL_00001692";
/*  24:    */   
/*  25:    */   public EntityMob(World par1World)
/*  26:    */   {
/*  27: 21 */     super(par1World);
/*  28: 22 */     this.experienceValue = 5;
/*  29:    */   }
/*  30:    */   
/*  31:    */   public void onLivingUpdate()
/*  32:    */   {
/*  33: 31 */     updateArmSwingProgress();
/*  34: 32 */     float var1 = getBrightness(1.0F);
/*  35: 34 */     if (var1 > 0.5F) {
/*  36: 36 */       this.entityAge += 2;
/*  37:    */     }
/*  38: 39 */     super.onLivingUpdate();
/*  39:    */   }
/*  40:    */   
/*  41:    */   public void onUpdate()
/*  42:    */   {
/*  43: 47 */     super.onUpdate();
/*  44: 49 */     if ((!this.worldObj.isClient) && (this.worldObj.difficultySetting == EnumDifficulty.PEACEFUL)) {
/*  45: 51 */       setDead();
/*  46:    */     }
/*  47:    */   }
/*  48:    */   
/*  49:    */   protected String getSwimSound()
/*  50:    */   {
/*  51: 57 */     return "game.hostile.swim";
/*  52:    */   }
/*  53:    */   
/*  54:    */   protected String getSplashSound()
/*  55:    */   {
/*  56: 62 */     return "game.hostile.swim.splash";
/*  57:    */   }
/*  58:    */   
/*  59:    */   protected Entity findPlayerToAttack()
/*  60:    */   {
/*  61: 71 */     EntityPlayer var1 = this.worldObj.getClosestVulnerablePlayerToEntity(this, 16.0D);
/*  62: 72 */     return (var1 != null) && (canEntityBeSeen(var1)) ? var1 : null;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public boolean attackEntityFrom(DamageSource par1DamageSource, float par2)
/*  66:    */   {
/*  67: 80 */     if (isEntityInvulnerable()) {
/*  68: 82 */       return false;
/*  69:    */     }
/*  70: 84 */     if (super.attackEntityFrom(par1DamageSource, par2))
/*  71:    */     {
/*  72: 86 */       Entity var3 = par1DamageSource.getEntity();
/*  73: 88 */       if ((this.riddenByEntity != var3) && (this.ridingEntity != var3))
/*  74:    */       {
/*  75: 90 */         if (var3 != this) {
/*  76: 92 */           this.entityToAttack = var3;
/*  77:    */         }
/*  78: 95 */         return true;
/*  79:    */       }
/*  80: 99 */       return true;
/*  81:    */     }
/*  82:104 */     return false;
/*  83:    */   }
/*  84:    */   
/*  85:    */   protected String getHurtSound()
/*  86:    */   {
/*  87:113 */     return "game.hostile.hurt";
/*  88:    */   }
/*  89:    */   
/*  90:    */   protected String getDeathSound()
/*  91:    */   {
/*  92:121 */     return "game.hostile.die";
/*  93:    */   }
/*  94:    */   
/*  95:    */   protected String func_146067_o(int p_146067_1_)
/*  96:    */   {
/*  97:126 */     return p_146067_1_ > 4 ? "game.hostile.hurt.fall.big" : "game.hostile.hurt.fall.small";
/*  98:    */   }
/*  99:    */   
/* 100:    */   public boolean attackEntityAsMob(Entity par1Entity)
/* 101:    */   {
/* 102:131 */     float var2 = (float)getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue();
/* 103:132 */     int var3 = 0;
/* 104:134 */     if ((par1Entity instanceof EntityLivingBase))
/* 105:    */     {
/* 106:136 */       var2 += EnchantmentHelper.getEnchantmentModifierLiving(this, (EntityLivingBase)par1Entity);
/* 107:137 */       var3 += EnchantmentHelper.getKnockbackModifier(this, (EntityLivingBase)par1Entity);
/* 108:    */     }
/* 109:140 */     boolean var4 = par1Entity.attackEntityFrom(DamageSource.causeMobDamage(this), var2);
/* 110:142 */     if (var4)
/* 111:    */     {
/* 112:144 */       if (var3 > 0)
/* 113:    */       {
/* 114:146 */         par1Entity.addVelocity(-MathHelper.sin(this.rotationYaw * 3.141593F / 180.0F) * var3 * 0.5F, 0.1D, MathHelper.cos(this.rotationYaw * 3.141593F / 180.0F) * var3 * 0.5F);
/* 115:147 */         this.motionX *= 0.6D;
/* 116:148 */         this.motionZ *= 0.6D;
/* 117:    */       }
/* 118:151 */       int var5 = EnchantmentHelper.getFireAspectModifier(this);
/* 119:153 */       if (var5 > 0) {
/* 120:155 */         par1Entity.setFire(var5 * 4);
/* 121:    */       }
/* 122:158 */       if ((par1Entity instanceof EntityLivingBase)) {
/* 123:160 */         EnchantmentHelper.func_151384_a((EntityLivingBase)par1Entity, this);
/* 124:    */       }
/* 125:163 */       EnchantmentHelper.func_151385_b(this, par1Entity);
/* 126:    */     }
/* 127:166 */     return var4;
/* 128:    */   }
/* 129:    */   
/* 130:    */   protected void attackEntity(Entity par1Entity, float par2)
/* 131:    */   {
/* 132:174 */     if ((this.attackTime <= 0) && (par2 < 2.0F) && (par1Entity.boundingBox.maxY > this.boundingBox.minY) && (par1Entity.boundingBox.minY < this.boundingBox.maxY))
/* 133:    */     {
/* 134:176 */       this.attackTime = 20;
/* 135:177 */       attackEntityAsMob(par1Entity);
/* 136:    */     }
/* 137:    */   }
/* 138:    */   
/* 139:    */   public float getBlockPathWeight(int par1, int par2, int par3)
/* 140:    */   {
/* 141:187 */     return 0.5F - this.worldObj.getLightBrightness(par1, par2, par3);
/* 142:    */   }
/* 143:    */   
/* 144:    */   protected boolean isValidLightLevel()
/* 145:    */   {
/* 146:195 */     int var1 = MathHelper.floor_double(this.posX);
/* 147:196 */     int var2 = MathHelper.floor_double(this.boundingBox.minY);
/* 148:197 */     int var3 = MathHelper.floor_double(this.posZ);
/* 149:199 */     if (this.worldObj.getSavedLightValue(EnumSkyBlock.Sky, var1, var2, var3) > this.rand.nextInt(32)) {
/* 150:201 */       return false;
/* 151:    */     }
/* 152:205 */     int var4 = this.worldObj.getBlockLightValue(var1, var2, var3);
/* 153:207 */     if (this.worldObj.isThundering())
/* 154:    */     {
/* 155:209 */       int var5 = this.worldObj.skylightSubtracted;
/* 156:210 */       this.worldObj.skylightSubtracted = 10;
/* 157:211 */       var4 = this.worldObj.getBlockLightValue(var1, var2, var3);
/* 158:212 */       this.worldObj.skylightSubtracted = var5;
/* 159:    */     }
/* 160:215 */     return var4 <= this.rand.nextInt(8);
/* 161:    */   }
/* 162:    */   
/* 163:    */   public boolean getCanSpawnHere()
/* 164:    */   {
/* 165:224 */     return (this.worldObj.difficultySetting != EnumDifficulty.PEACEFUL) && (isValidLightLevel()) && (super.getCanSpawnHere());
/* 166:    */   }
/* 167:    */   
/* 168:    */   protected void applyEntityAttributes()
/* 169:    */   {
/* 170:229 */     super.applyEntityAttributes();
/* 171:230 */     getAttributeMap().registerAttribute(SharedMonsterAttributes.attackDamage);
/* 172:    */   }
/* 173:    */   
/* 174:    */   protected boolean func_146066_aG()
/* 175:    */   {
/* 176:235 */     return true;
/* 177:    */   }
/* 178:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.entity.monster.EntityMob
 * JD-Core Version:    0.7.0.1
 */