/*   1:    */ package net.minecraft.entity.monster;
/*   2:    */ 
/*   3:    */ import java.util.Random;
/*   4:    */ import net.minecraft.entity.DataWatcher;
/*   5:    */ import net.minecraft.entity.EntityLiving;
/*   6:    */ import net.minecraft.entity.SharedMonsterAttributes;
/*   7:    */ import net.minecraft.entity.ai.attributes.IAttributeInstance;
/*   8:    */ import net.minecraft.entity.player.EntityPlayer;
/*   9:    */ import net.minecraft.init.Items;
/*  10:    */ import net.minecraft.item.Item;
/*  11:    */ import net.minecraft.nbt.NBTTagCompound;
/*  12:    */ import net.minecraft.util.AxisAlignedBB;
/*  13:    */ import net.minecraft.util.DamageSource;
/*  14:    */ import net.minecraft.util.MathHelper;
/*  15:    */ import net.minecraft.world.EnumDifficulty;
/*  16:    */ import net.minecraft.world.World;
/*  17:    */ import net.minecraft.world.WorldType;
/*  18:    */ import net.minecraft.world.biome.BiomeGenBase;
/*  19:    */ import net.minecraft.world.chunk.Chunk;
/*  20:    */ import net.minecraft.world.storage.WorldInfo;
/*  21:    */ 
/*  22:    */ public class EntitySlime
/*  23:    */   extends EntityLiving
/*  24:    */   implements IMob
/*  25:    */ {
/*  26:    */   public float squishAmount;
/*  27:    */   public float squishFactor;
/*  28:    */   public float prevSquishFactor;
/*  29:    */   private int slimeJumpDelay;
/*  30:    */   private static final String __OBFID = "CL_00001698";
/*  31:    */   
/*  32:    */   public EntitySlime(World par1World)
/*  33:    */   {
/*  34: 29 */     super(par1World);
/*  35: 30 */     int var2 = 1 << this.rand.nextInt(3);
/*  36: 31 */     this.yOffset = 0.0F;
/*  37: 32 */     this.slimeJumpDelay = (this.rand.nextInt(20) + 10);
/*  38: 33 */     setSlimeSize(var2);
/*  39:    */   }
/*  40:    */   
/*  41:    */   protected void entityInit()
/*  42:    */   {
/*  43: 38 */     super.entityInit();
/*  44: 39 */     this.dataWatcher.addObject(16, new Byte((byte)1));
/*  45:    */   }
/*  46:    */   
/*  47:    */   protected void setSlimeSize(int par1)
/*  48:    */   {
/*  49: 44 */     this.dataWatcher.updateObject(16, new Byte((byte)par1));
/*  50: 45 */     setSize(0.6F * par1, 0.6F * par1);
/*  51: 46 */     setPosition(this.posX, this.posY, this.posZ);
/*  52: 47 */     getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(par1 * par1);
/*  53: 48 */     setHealth(getMaxHealth());
/*  54: 49 */     this.experienceValue = par1;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public int getSlimeSize()
/*  58:    */   {
/*  59: 57 */     return this.dataWatcher.getWatchableObjectByte(16);
/*  60:    */   }
/*  61:    */   
/*  62:    */   public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
/*  63:    */   {
/*  64: 65 */     super.writeEntityToNBT(par1NBTTagCompound);
/*  65: 66 */     par1NBTTagCompound.setInteger("Size", getSlimeSize() - 1);
/*  66:    */   }
/*  67:    */   
/*  68:    */   public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
/*  69:    */   {
/*  70: 74 */     super.readEntityFromNBT(par1NBTTagCompound);
/*  71: 75 */     setSlimeSize(par1NBTTagCompound.getInteger("Size") + 1);
/*  72:    */   }
/*  73:    */   
/*  74:    */   protected String getSlimeParticle()
/*  75:    */   {
/*  76: 83 */     return "slime";
/*  77:    */   }
/*  78:    */   
/*  79:    */   protected String getJumpSound()
/*  80:    */   {
/*  81: 91 */     return "mob.slime." + (getSlimeSize() > 1 ? "big" : "small");
/*  82:    */   }
/*  83:    */   
/*  84:    */   public void onUpdate()
/*  85:    */   {
/*  86: 99 */     if ((!this.worldObj.isClient) && (this.worldObj.difficultySetting == EnumDifficulty.PEACEFUL) && (getSlimeSize() > 0)) {
/*  87:101 */       this.isDead = true;
/*  88:    */     }
/*  89:104 */     this.squishFactor += (this.squishAmount - this.squishFactor) * 0.5F;
/*  90:105 */     this.prevSquishFactor = this.squishFactor;
/*  91:106 */     boolean var1 = this.onGround;
/*  92:107 */     super.onUpdate();
/*  93:110 */     if ((this.onGround) && (!var1))
/*  94:    */     {
/*  95:112 */       int var2 = getSlimeSize();
/*  96:114 */       for (int var3 = 0; var3 < var2 * 8; var3++)
/*  97:    */       {
/*  98:116 */         float var4 = this.rand.nextFloat() * 3.141593F * 2.0F;
/*  99:117 */         float var5 = this.rand.nextFloat() * 0.5F + 0.5F;
/* 100:118 */         float var6 = MathHelper.sin(var4) * var2 * 0.5F * var5;
/* 101:119 */         float var7 = MathHelper.cos(var4) * var2 * 0.5F * var5;
/* 102:120 */         this.worldObj.spawnParticle(getSlimeParticle(), this.posX + var6, this.boundingBox.minY, this.posZ + var7, 0.0D, 0.0D, 0.0D);
/* 103:    */       }
/* 104:123 */       if (makesSoundOnLand()) {
/* 105:125 */         playSound(getJumpSound(), getSoundVolume(), ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F) / 0.8F);
/* 106:    */       }
/* 107:128 */       this.squishAmount = -0.5F;
/* 108:    */     }
/* 109:130 */     else if ((!this.onGround) && (var1))
/* 110:    */     {
/* 111:132 */       this.squishAmount = 1.0F;
/* 112:    */     }
/* 113:135 */     alterSquishAmount();
/* 114:137 */     if (this.worldObj.isClient)
/* 115:    */     {
/* 116:139 */       int var2 = getSlimeSize();
/* 117:140 */       setSize(0.6F * var2, 0.6F * var2);
/* 118:    */     }
/* 119:    */   }
/* 120:    */   
/* 121:    */   protected void updateEntityActionState()
/* 122:    */   {
/* 123:146 */     despawnEntity();
/* 124:147 */     EntityPlayer var1 = this.worldObj.getClosestVulnerablePlayerToEntity(this, 16.0D);
/* 125:149 */     if (var1 != null) {
/* 126:151 */       faceEntity(var1, 10.0F, 20.0F);
/* 127:    */     }
/* 128:154 */     if ((this.onGround) && (this.slimeJumpDelay-- <= 0))
/* 129:    */     {
/* 130:156 */       this.slimeJumpDelay = getJumpDelay();
/* 131:158 */       if (var1 != null) {
/* 132:160 */         this.slimeJumpDelay /= 3;
/* 133:    */       }
/* 134:163 */       this.isJumping = true;
/* 135:165 */       if (makesSoundOnJump()) {
/* 136:167 */         playSound(getJumpSound(), getSoundVolume(), ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F) * 0.8F);
/* 137:    */       }
/* 138:170 */       this.moveStrafing = (1.0F - this.rand.nextFloat() * 2.0F);
/* 139:171 */       this.moveForward = (1 * getSlimeSize());
/* 140:    */     }
/* 141:    */     else
/* 142:    */     {
/* 143:175 */       this.isJumping = false;
/* 144:177 */       if (this.onGround) {
/* 145:179 */         this.moveStrafing = (this.moveForward = 0.0F);
/* 146:    */       }
/* 147:    */     }
/* 148:    */   }
/* 149:    */   
/* 150:    */   protected void alterSquishAmount()
/* 151:    */   {
/* 152:186 */     this.squishAmount *= 0.6F;
/* 153:    */   }
/* 154:    */   
/* 155:    */   protected int getJumpDelay()
/* 156:    */   {
/* 157:194 */     return this.rand.nextInt(20) + 10;
/* 158:    */   }
/* 159:    */   
/* 160:    */   protected EntitySlime createInstance()
/* 161:    */   {
/* 162:199 */     return new EntitySlime(this.worldObj);
/* 163:    */   }
/* 164:    */   
/* 165:    */   public void setDead()
/* 166:    */   {
/* 167:207 */     int var1 = getSlimeSize();
/* 168:209 */     if ((!this.worldObj.isClient) && (var1 > 1) && (getHealth() <= 0.0F))
/* 169:    */     {
/* 170:211 */       int var2 = 2 + this.rand.nextInt(3);
/* 171:213 */       for (int var3 = 0; var3 < var2; var3++)
/* 172:    */       {
/* 173:215 */         float var4 = (var3 % 2 - 0.5F) * var1 / 4.0F;
/* 174:216 */         float var5 = (var3 / 2 - 0.5F) * var1 / 4.0F;
/* 175:217 */         EntitySlime var6 = createInstance();
/* 176:218 */         var6.setSlimeSize(var1 / 2);
/* 177:219 */         var6.setLocationAndAngles(this.posX + var4, this.posY + 0.5D, this.posZ + var5, this.rand.nextFloat() * 360.0F, 0.0F);
/* 178:220 */         this.worldObj.spawnEntityInWorld(var6);
/* 179:    */       }
/* 180:    */     }
/* 181:224 */     super.setDead();
/* 182:    */   }
/* 183:    */   
/* 184:    */   public void onCollideWithPlayer(EntityPlayer par1EntityPlayer)
/* 185:    */   {
/* 186:232 */     if (canDamagePlayer())
/* 187:    */     {
/* 188:234 */       int var2 = getSlimeSize();
/* 189:236 */       if ((canEntityBeSeen(par1EntityPlayer)) && (getDistanceSqToEntity(par1EntityPlayer) < 0.6D * var2 * 0.6D * var2) && (par1EntityPlayer.attackEntityFrom(DamageSource.causeMobDamage(this), getAttackStrength()))) {
/* 190:238 */         playSound("mob.attack", 1.0F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
/* 191:    */       }
/* 192:    */     }
/* 193:    */   }
/* 194:    */   
/* 195:    */   protected boolean canDamagePlayer()
/* 196:    */   {
/* 197:248 */     return getSlimeSize() > 1;
/* 198:    */   }
/* 199:    */   
/* 200:    */   protected int getAttackStrength()
/* 201:    */   {
/* 202:256 */     return getSlimeSize();
/* 203:    */   }
/* 204:    */   
/* 205:    */   protected String getHurtSound()
/* 206:    */   {
/* 207:264 */     return "mob.slime." + (getSlimeSize() > 1 ? "big" : "small");
/* 208:    */   }
/* 209:    */   
/* 210:    */   protected String getDeathSound()
/* 211:    */   {
/* 212:272 */     return "mob.slime." + (getSlimeSize() > 1 ? "big" : "small");
/* 213:    */   }
/* 214:    */   
/* 215:    */   protected Item func_146068_u()
/* 216:    */   {
/* 217:277 */     return getSlimeSize() == 1 ? Items.slime_ball : Item.getItemById(0);
/* 218:    */   }
/* 219:    */   
/* 220:    */   public boolean getCanSpawnHere()
/* 221:    */   {
/* 222:285 */     Chunk var1 = this.worldObj.getChunkFromBlockCoords(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posZ));
/* 223:287 */     if ((this.worldObj.getWorldInfo().getTerrainType() == WorldType.FLAT) && (this.rand.nextInt(4) != 1)) {
/* 224:289 */       return false;
/* 225:    */     }
/* 226:293 */     if ((getSlimeSize() == 1) || (this.worldObj.difficultySetting != EnumDifficulty.PEACEFUL))
/* 227:    */     {
/* 228:295 */       BiomeGenBase var2 = this.worldObj.getBiomeGenForCoords(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posZ));
/* 229:297 */       if ((var2 == BiomeGenBase.swampland) && (this.posY > 50.0D) && (this.posY < 70.0D) && (this.rand.nextFloat() < 0.5F) && (this.rand.nextFloat() < this.worldObj.getCurrentMoonPhaseFactor()) && (this.worldObj.getBlockLightValue(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ)) <= this.rand.nextInt(8))) {
/* 230:299 */         return super.getCanSpawnHere();
/* 231:    */       }
/* 232:302 */       if ((this.rand.nextInt(10) == 0) && (var1.getRandomWithSeed(987234911L).nextInt(10) == 0) && (this.posY < 40.0D)) {
/* 233:304 */         return super.getCanSpawnHere();
/* 234:    */       }
/* 235:    */     }
/* 236:308 */     return false;
/* 237:    */   }
/* 238:    */   
/* 239:    */   protected float getSoundVolume()
/* 240:    */   {
/* 241:317 */     return 0.4F * getSlimeSize();
/* 242:    */   }
/* 243:    */   
/* 244:    */   public int getVerticalFaceSpeed()
/* 245:    */   {
/* 246:326 */     return 0;
/* 247:    */   }
/* 248:    */   
/* 249:    */   protected boolean makesSoundOnJump()
/* 250:    */   {
/* 251:334 */     return getSlimeSize() > 0;
/* 252:    */   }
/* 253:    */   
/* 254:    */   protected boolean makesSoundOnLand()
/* 255:    */   {
/* 256:342 */     return getSlimeSize() > 2;
/* 257:    */   }
/* 258:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.entity.monster.EntitySlime
 * JD-Core Version:    0.7.0.1
 */