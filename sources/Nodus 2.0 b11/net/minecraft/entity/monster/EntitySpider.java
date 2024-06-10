/*   1:    */ package net.minecraft.entity.monster;
/*   2:    */ 
/*   3:    */ import java.util.Random;
/*   4:    */ import net.minecraft.block.Block;
/*   5:    */ import net.minecraft.entity.DataWatcher;
/*   6:    */ import net.minecraft.entity.Entity;
/*   7:    */ import net.minecraft.entity.EnumCreatureAttribute;
/*   8:    */ import net.minecraft.entity.IEntityLivingData;
/*   9:    */ import net.minecraft.entity.SharedMonsterAttributes;
/*  10:    */ import net.minecraft.entity.ai.attributes.IAttributeInstance;
/*  11:    */ import net.minecraft.init.Items;
/*  12:    */ import net.minecraft.item.Item;
/*  13:    */ import net.minecraft.potion.Potion;
/*  14:    */ import net.minecraft.potion.PotionEffect;
/*  15:    */ import net.minecraft.util.MathHelper;
/*  16:    */ import net.minecraft.world.EnumDifficulty;
/*  17:    */ import net.minecraft.world.World;
/*  18:    */ 
/*  19:    */ public class EntitySpider
/*  20:    */   extends EntityMob
/*  21:    */ {
/*  22:    */   private static final String __OBFID = "CL_00001699";
/*  23:    */   
/*  24:    */   public EntitySpider(World par1World)
/*  25:    */   {
/*  26: 23 */     super(par1World);
/*  27: 24 */     setSize(1.4F, 0.9F);
/*  28:    */   }
/*  29:    */   
/*  30:    */   protected void entityInit()
/*  31:    */   {
/*  32: 29 */     super.entityInit();
/*  33: 30 */     this.dataWatcher.addObject(16, new Byte((byte)0));
/*  34:    */   }
/*  35:    */   
/*  36:    */   public void onUpdate()
/*  37:    */   {
/*  38: 38 */     super.onUpdate();
/*  39: 40 */     if (!this.worldObj.isClient) {
/*  40: 42 */       setBesideClimbableBlock(this.isCollidedHorizontally);
/*  41:    */     }
/*  42:    */   }
/*  43:    */   
/*  44:    */   protected void applyEntityAttributes()
/*  45:    */   {
/*  46: 48 */     super.applyEntityAttributes();
/*  47: 49 */     getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(16.0D);
/*  48: 50 */     getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.800000011920929D);
/*  49:    */   }
/*  50:    */   
/*  51:    */   protected Entity findPlayerToAttack()
/*  52:    */   {
/*  53: 59 */     float var1 = getBrightness(1.0F);
/*  54: 61 */     if (var1 < 0.5F)
/*  55:    */     {
/*  56: 63 */       double var2 = 16.0D;
/*  57: 64 */       return this.worldObj.getClosestVulnerablePlayerToEntity(this, var2);
/*  58:    */     }
/*  59: 68 */     return null;
/*  60:    */   }
/*  61:    */   
/*  62:    */   protected String getLivingSound()
/*  63:    */   {
/*  64: 77 */     return "mob.spider.say";
/*  65:    */   }
/*  66:    */   
/*  67:    */   protected String getHurtSound()
/*  68:    */   {
/*  69: 85 */     return "mob.spider.say";
/*  70:    */   }
/*  71:    */   
/*  72:    */   protected String getDeathSound()
/*  73:    */   {
/*  74: 93 */     return "mob.spider.death";
/*  75:    */   }
/*  76:    */   
/*  77:    */   protected void func_145780_a(int p_145780_1_, int p_145780_2_, int p_145780_3_, Block p_145780_4_)
/*  78:    */   {
/*  79: 98 */     playSound("mob.spider.step", 0.15F, 1.0F);
/*  80:    */   }
/*  81:    */   
/*  82:    */   protected void attackEntity(Entity par1Entity, float par2)
/*  83:    */   {
/*  84:106 */     float var3 = getBrightness(1.0F);
/*  85:108 */     if ((var3 > 0.5F) && (this.rand.nextInt(100) == 0)) {
/*  86:110 */       this.entityToAttack = null;
/*  87:114 */     } else if ((par2 > 2.0F) && (par2 < 6.0F) && (this.rand.nextInt(10) == 0))
/*  88:    */     {
/*  89:116 */       if (this.onGround)
/*  90:    */       {
/*  91:118 */         double var4 = par1Entity.posX - this.posX;
/*  92:119 */         double var6 = par1Entity.posZ - this.posZ;
/*  93:120 */         float var8 = MathHelper.sqrt_double(var4 * var4 + var6 * var6);
/*  94:121 */         this.motionX = (var4 / var8 * 0.5D * 0.800000011920929D + this.motionX * 0.2000000029802322D);
/*  95:122 */         this.motionZ = (var6 / var8 * 0.5D * 0.800000011920929D + this.motionZ * 0.2000000029802322D);
/*  96:123 */         this.motionY = 0.4000000059604645D;
/*  97:    */       }
/*  98:    */     }
/*  99:    */     else {
/* 100:128 */       super.attackEntity(par1Entity, par2);
/* 101:    */     }
/* 102:    */   }
/* 103:    */   
/* 104:    */   protected Item func_146068_u()
/* 105:    */   {
/* 106:135 */     return Items.string;
/* 107:    */   }
/* 108:    */   
/* 109:    */   protected void dropFewItems(boolean par1, int par2)
/* 110:    */   {
/* 111:143 */     super.dropFewItems(par1, par2);
/* 112:145 */     if ((par1) && ((this.rand.nextInt(3) == 0) || (this.rand.nextInt(1 + par2) > 0))) {
/* 113:147 */       func_145779_a(Items.spider_eye, 1);
/* 114:    */     }
/* 115:    */   }
/* 116:    */   
/* 117:    */   public boolean isOnLadder()
/* 118:    */   {
/* 119:156 */     return isBesideClimbableBlock();
/* 120:    */   }
/* 121:    */   
/* 122:    */   public void setInWeb() {}
/* 123:    */   
/* 124:    */   public EnumCreatureAttribute getCreatureAttribute()
/* 125:    */   {
/* 126:169 */     return EnumCreatureAttribute.ARTHROPOD;
/* 127:    */   }
/* 128:    */   
/* 129:    */   public boolean isPotionApplicable(PotionEffect par1PotionEffect)
/* 130:    */   {
/* 131:174 */     return par1PotionEffect.getPotionID() == Potion.poison.id ? false : super.isPotionApplicable(par1PotionEffect);
/* 132:    */   }
/* 133:    */   
/* 134:    */   public boolean isBesideClimbableBlock()
/* 135:    */   {
/* 136:183 */     return (this.dataWatcher.getWatchableObjectByte(16) & 0x1) != 0;
/* 137:    */   }
/* 138:    */   
/* 139:    */   public void setBesideClimbableBlock(boolean par1)
/* 140:    */   {
/* 141:192 */     byte var2 = this.dataWatcher.getWatchableObjectByte(16);
/* 142:194 */     if (par1) {
/* 143:196 */       var2 = (byte)(var2 | 0x1);
/* 144:    */     } else {
/* 145:200 */       var2 = (byte)(var2 & 0xFFFFFFFE);
/* 146:    */     }
/* 147:203 */     this.dataWatcher.updateObject(16, Byte.valueOf(var2));
/* 148:    */   }
/* 149:    */   
/* 150:    */   public IEntityLivingData onSpawnWithEgg(IEntityLivingData par1EntityLivingData)
/* 151:    */   {
/* 152:208 */     Object par1EntityLivingData1 = super.onSpawnWithEgg(par1EntityLivingData);
/* 153:210 */     if (this.worldObj.rand.nextInt(100) == 0)
/* 154:    */     {
/* 155:212 */       EntitySkeleton var2 = new EntitySkeleton(this.worldObj);
/* 156:213 */       var2.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0F);
/* 157:214 */       var2.onSpawnWithEgg(null);
/* 158:215 */       this.worldObj.spawnEntityInWorld(var2);
/* 159:216 */       var2.mountEntity(this);
/* 160:    */     }
/* 161:219 */     if (par1EntityLivingData1 == null)
/* 162:    */     {
/* 163:221 */       par1EntityLivingData1 = new GroupData();
/* 164:223 */       if ((this.worldObj.difficultySetting == EnumDifficulty.HARD) && (this.worldObj.rand.nextFloat() < 0.1F * this.worldObj.func_147462_b(this.posX, this.posY, this.posZ))) {
/* 165:225 */         ((GroupData)par1EntityLivingData1).func_111104_a(this.worldObj.rand);
/* 166:    */       }
/* 167:    */     }
/* 168:229 */     if ((par1EntityLivingData1 instanceof GroupData))
/* 169:    */     {
/* 170:231 */       int var4 = ((GroupData)par1EntityLivingData1).field_111105_a;
/* 171:233 */       if ((var4 > 0) && (Potion.potionTypes[var4] != null)) {
/* 172:235 */         addPotionEffect(new PotionEffect(var4, 2147483647));
/* 173:    */       }
/* 174:    */     }
/* 175:239 */     return (IEntityLivingData)par1EntityLivingData1;
/* 176:    */   }
/* 177:    */   
/* 178:    */   public static class GroupData
/* 179:    */     implements IEntityLivingData
/* 180:    */   {
/* 181:    */     public int field_111105_a;
/* 182:    */     private static final String __OBFID = "CL_00001700";
/* 183:    */     
/* 184:    */     public void func_111104_a(Random par1Random)
/* 185:    */     {
/* 186:249 */       int var2 = par1Random.nextInt(5);
/* 187:251 */       if (var2 <= 1) {
/* 188:253 */         this.field_111105_a = Potion.moveSpeed.id;
/* 189:255 */       } else if (var2 <= 2) {
/* 190:257 */         this.field_111105_a = Potion.damageBoost.id;
/* 191:259 */       } else if (var2 <= 3) {
/* 192:261 */         this.field_111105_a = Potion.regeneration.id;
/* 193:263 */       } else if (var2 <= 4) {
/* 194:265 */         this.field_111105_a = Potion.invisibility.id;
/* 195:    */       }
/* 196:    */     }
/* 197:    */   }
/* 198:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.entity.monster.EntitySpider
 * JD-Core Version:    0.7.0.1
 */