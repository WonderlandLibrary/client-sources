/*   1:    */ package net.minecraft.entity.monster;
/*   2:    */ 
/*   3:    */ import java.util.List;
/*   4:    */ import java.util.Random;
/*   5:    */ import net.minecraft.entity.DataWatcher;
/*   6:    */ import net.minecraft.entity.Entity;
/*   7:    */ import net.minecraft.entity.EntityFlying;
/*   8:    */ import net.minecraft.entity.SharedMonsterAttributes;
/*   9:    */ import net.minecraft.entity.ai.attributes.IAttributeInstance;
/*  10:    */ import net.minecraft.entity.player.EntityPlayer;
/*  11:    */ import net.minecraft.entity.projectile.EntityLargeFireball;
/*  12:    */ import net.minecraft.init.Items;
/*  13:    */ import net.minecraft.item.Item;
/*  14:    */ import net.minecraft.nbt.NBTTagCompound;
/*  15:    */ import net.minecraft.stats.AchievementList;
/*  16:    */ import net.minecraft.util.AxisAlignedBB;
/*  17:    */ import net.minecraft.util.DamageSource;
/*  18:    */ import net.minecraft.util.MathHelper;
/*  19:    */ import net.minecraft.util.Vec3;
/*  20:    */ import net.minecraft.world.EnumDifficulty;
/*  21:    */ import net.minecraft.world.World;
/*  22:    */ 
/*  23:    */ public class EntityGhast
/*  24:    */   extends EntityFlying
/*  25:    */   implements IMob
/*  26:    */ {
/*  27:    */   public int courseChangeCooldown;
/*  28:    */   public double waypointX;
/*  29:    */   public double waypointY;
/*  30:    */   public double waypointZ;
/*  31:    */   private Entity targetedEntity;
/*  32:    */   private int aggroCooldown;
/*  33:    */   public int prevAttackCounter;
/*  34:    */   public int attackCounter;
/*  35: 33 */   private int explosionStrength = 1;
/*  36:    */   private static final String __OBFID = "CL_00001689";
/*  37:    */   
/*  38:    */   public EntityGhast(World par1World)
/*  39:    */   {
/*  40: 38 */     super(par1World);
/*  41: 39 */     setSize(4.0F, 4.0F);
/*  42: 40 */     this.isImmuneToFire = true;
/*  43: 41 */     this.experienceValue = 5;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public boolean func_110182_bF()
/*  47:    */   {
/*  48: 46 */     return this.dataWatcher.getWatchableObjectByte(16) != 0;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public boolean attackEntityFrom(DamageSource par1DamageSource, float par2)
/*  52:    */   {
/*  53: 54 */     if (isEntityInvulnerable()) {
/*  54: 56 */       return false;
/*  55:    */     }
/*  56: 58 */     if (("fireball".equals(par1DamageSource.getDamageType())) && ((par1DamageSource.getEntity() instanceof EntityPlayer)))
/*  57:    */     {
/*  58: 60 */       super.attackEntityFrom(par1DamageSource, 1000.0F);
/*  59: 61 */       ((EntityPlayer)par1DamageSource.getEntity()).triggerAchievement(AchievementList.ghast);
/*  60: 62 */       return true;
/*  61:    */     }
/*  62: 66 */     return super.attackEntityFrom(par1DamageSource, par2);
/*  63:    */   }
/*  64:    */   
/*  65:    */   protected void entityInit()
/*  66:    */   {
/*  67: 72 */     super.entityInit();
/*  68: 73 */     this.dataWatcher.addObject(16, Byte.valueOf((byte)0));
/*  69:    */   }
/*  70:    */   
/*  71:    */   protected void applyEntityAttributes()
/*  72:    */   {
/*  73: 78 */     super.applyEntityAttributes();
/*  74: 79 */     getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(10.0D);
/*  75:    */   }
/*  76:    */   
/*  77:    */   protected void updateEntityActionState()
/*  78:    */   {
/*  79: 84 */     if ((!this.worldObj.isClient) && (this.worldObj.difficultySetting == EnumDifficulty.PEACEFUL)) {
/*  80: 86 */       setDead();
/*  81:    */     }
/*  82: 89 */     despawnEntity();
/*  83: 90 */     this.prevAttackCounter = this.attackCounter;
/*  84: 91 */     double var1 = this.waypointX - this.posX;
/*  85: 92 */     double var3 = this.waypointY - this.posY;
/*  86: 93 */     double var5 = this.waypointZ - this.posZ;
/*  87: 94 */     double var7 = var1 * var1 + var3 * var3 + var5 * var5;
/*  88: 96 */     if ((var7 < 1.0D) || (var7 > 3600.0D))
/*  89:    */     {
/*  90: 98 */       this.waypointX = (this.posX + (this.rand.nextFloat() * 2.0F - 1.0F) * 16.0F);
/*  91: 99 */       this.waypointY = (this.posY + (this.rand.nextFloat() * 2.0F - 1.0F) * 16.0F);
/*  92:100 */       this.waypointZ = (this.posZ + (this.rand.nextFloat() * 2.0F - 1.0F) * 16.0F);
/*  93:    */     }
/*  94:103 */     if (this.courseChangeCooldown-- <= 0)
/*  95:    */     {
/*  96:105 */       this.courseChangeCooldown += this.rand.nextInt(5) + 2;
/*  97:106 */       var7 = MathHelper.sqrt_double(var7);
/*  98:108 */       if (isCourseTraversable(this.waypointX, this.waypointY, this.waypointZ, var7))
/*  99:    */       {
/* 100:110 */         this.motionX += var1 / var7 * 0.1D;
/* 101:111 */         this.motionY += var3 / var7 * 0.1D;
/* 102:112 */         this.motionZ += var5 / var7 * 0.1D;
/* 103:    */       }
/* 104:    */       else
/* 105:    */       {
/* 106:116 */         this.waypointX = this.posX;
/* 107:117 */         this.waypointY = this.posY;
/* 108:118 */         this.waypointZ = this.posZ;
/* 109:    */       }
/* 110:    */     }
/* 111:122 */     if ((this.targetedEntity != null) && (this.targetedEntity.isDead)) {
/* 112:124 */       this.targetedEntity = null;
/* 113:    */     }
/* 114:127 */     if ((this.targetedEntity == null) || (this.aggroCooldown-- <= 0))
/* 115:    */     {
/* 116:129 */       this.targetedEntity = this.worldObj.getClosestVulnerablePlayerToEntity(this, 100.0D);
/* 117:131 */       if (this.targetedEntity != null) {
/* 118:133 */         this.aggroCooldown = 20;
/* 119:    */       }
/* 120:    */     }
/* 121:137 */     double var9 = 64.0D;
/* 122:139 */     if ((this.targetedEntity != null) && (this.targetedEntity.getDistanceSqToEntity(this) < var9 * var9))
/* 123:    */     {
/* 124:141 */       double var11 = this.targetedEntity.posX - this.posX;
/* 125:142 */       double var13 = this.targetedEntity.boundingBox.minY + this.targetedEntity.height / 2.0F - (this.posY + this.height / 2.0F);
/* 126:143 */       double var15 = this.targetedEntity.posZ - this.posZ;
/* 127:144 */       this.renderYawOffset = (this.rotationYaw = -(float)Math.atan2(var11, var15) * 180.0F / 3.141593F);
/* 128:146 */       if (canEntityBeSeen(this.targetedEntity))
/* 129:    */       {
/* 130:148 */         if (this.attackCounter == 10) {
/* 131:150 */           this.worldObj.playAuxSFXAtEntity(null, 1007, (int)this.posX, (int)this.posY, (int)this.posZ, 0);
/* 132:    */         }
/* 133:153 */         this.attackCounter += 1;
/* 134:155 */         if (this.attackCounter == 20)
/* 135:    */         {
/* 136:157 */           this.worldObj.playAuxSFXAtEntity(null, 1008, (int)this.posX, (int)this.posY, (int)this.posZ, 0);
/* 137:158 */           EntityLargeFireball var17 = new EntityLargeFireball(this.worldObj, this, var11, var13, var15);
/* 138:159 */           var17.field_92057_e = this.explosionStrength;
/* 139:160 */           double var18 = 4.0D;
/* 140:161 */           Vec3 var20 = getLook(1.0F);
/* 141:162 */           var17.posX = (this.posX + var20.xCoord * var18);
/* 142:163 */           var17.posY = (this.posY + this.height / 2.0F + 0.5D);
/* 143:164 */           var17.posZ = (this.posZ + var20.zCoord * var18);
/* 144:165 */           this.worldObj.spawnEntityInWorld(var17);
/* 145:166 */           this.attackCounter = -40;
/* 146:    */         }
/* 147:    */       }
/* 148:169 */       else if (this.attackCounter > 0)
/* 149:    */       {
/* 150:171 */         this.attackCounter -= 1;
/* 151:    */       }
/* 152:    */     }
/* 153:    */     else
/* 154:    */     {
/* 155:176 */       this.renderYawOffset = (this.rotationYaw = -(float)Math.atan2(this.motionX, this.motionZ) * 180.0F / 3.141593F);
/* 156:178 */       if (this.attackCounter > 0) {
/* 157:180 */         this.attackCounter -= 1;
/* 158:    */       }
/* 159:    */     }
/* 160:184 */     if (!this.worldObj.isClient)
/* 161:    */     {
/* 162:186 */       byte var21 = this.dataWatcher.getWatchableObjectByte(16);
/* 163:187 */       byte var12 = (byte)(this.attackCounter > 10 ? 1 : 0);
/* 164:189 */       if (var21 != var12) {
/* 165:191 */         this.dataWatcher.updateObject(16, Byte.valueOf(var12));
/* 166:    */       }
/* 167:    */     }
/* 168:    */   }
/* 169:    */   
/* 170:    */   private boolean isCourseTraversable(double par1, double par3, double par5, double par7)
/* 171:    */   {
/* 172:201 */     double var9 = (this.waypointX - this.posX) / par7;
/* 173:202 */     double var11 = (this.waypointY - this.posY) / par7;
/* 174:203 */     double var13 = (this.waypointZ - this.posZ) / par7;
/* 175:204 */     AxisAlignedBB var15 = this.boundingBox.copy();
/* 176:206 */     for (int var16 = 1; var16 < par7; var16++)
/* 177:    */     {
/* 178:208 */       var15.offset(var9, var11, var13);
/* 179:210 */       if (!this.worldObj.getCollidingBoundingBoxes(this, var15).isEmpty()) {
/* 180:212 */         return false;
/* 181:    */       }
/* 182:    */     }
/* 183:216 */     return true;
/* 184:    */   }
/* 185:    */   
/* 186:    */   protected String getLivingSound()
/* 187:    */   {
/* 188:224 */     return "mob.ghast.moan";
/* 189:    */   }
/* 190:    */   
/* 191:    */   protected String getHurtSound()
/* 192:    */   {
/* 193:232 */     return "mob.ghast.scream";
/* 194:    */   }
/* 195:    */   
/* 196:    */   protected String getDeathSound()
/* 197:    */   {
/* 198:240 */     return "mob.ghast.death";
/* 199:    */   }
/* 200:    */   
/* 201:    */   protected Item func_146068_u()
/* 202:    */   {
/* 203:245 */     return Items.gunpowder;
/* 204:    */   }
/* 205:    */   
/* 206:    */   protected void dropFewItems(boolean par1, int par2)
/* 207:    */   {
/* 208:253 */     int var3 = this.rand.nextInt(2) + this.rand.nextInt(1 + par2);
/* 209:256 */     for (int var4 = 0; var4 < var3; var4++) {
/* 210:258 */       func_145779_a(Items.ghast_tear, 1);
/* 211:    */     }
/* 212:261 */     var3 = this.rand.nextInt(3) + this.rand.nextInt(1 + par2);
/* 213:263 */     for (var4 = 0; var4 < var3; var4++) {
/* 214:265 */       func_145779_a(Items.gunpowder, 1);
/* 215:    */     }
/* 216:    */   }
/* 217:    */   
/* 218:    */   protected float getSoundVolume()
/* 219:    */   {
/* 220:274 */     return 10.0F;
/* 221:    */   }
/* 222:    */   
/* 223:    */   public boolean getCanSpawnHere()
/* 224:    */   {
/* 225:282 */     return (this.rand.nextInt(20) == 0) && (super.getCanSpawnHere()) && (this.worldObj.difficultySetting != EnumDifficulty.PEACEFUL);
/* 226:    */   }
/* 227:    */   
/* 228:    */   public int getMaxSpawnedInChunk()
/* 229:    */   {
/* 230:290 */     return 1;
/* 231:    */   }
/* 232:    */   
/* 233:    */   public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
/* 234:    */   {
/* 235:298 */     super.writeEntityToNBT(par1NBTTagCompound);
/* 236:299 */     par1NBTTagCompound.setInteger("ExplosionPower", this.explosionStrength);
/* 237:    */   }
/* 238:    */   
/* 239:    */   public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
/* 240:    */   {
/* 241:307 */     super.readEntityFromNBT(par1NBTTagCompound);
/* 242:309 */     if (par1NBTTagCompound.func_150297_b("ExplosionPower", 99)) {
/* 243:311 */       this.explosionStrength = par1NBTTagCompound.getInteger("ExplosionPower");
/* 244:    */     }
/* 245:    */   }
/* 246:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.entity.monster.EntityGhast
 * JD-Core Version:    0.7.0.1
 */