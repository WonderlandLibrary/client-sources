/*   1:    */ package net.minecraft.entity.passive;
/*   2:    */ 
/*   3:    */ import java.util.Calendar;
/*   4:    */ import java.util.Random;
/*   5:    */ import net.minecraft.block.Block;
/*   6:    */ import net.minecraft.entity.DataWatcher;
/*   7:    */ import net.minecraft.entity.Entity;
/*   8:    */ import net.minecraft.entity.SharedMonsterAttributes;
/*   9:    */ import net.minecraft.entity.ai.attributes.IAttributeInstance;
/*  10:    */ import net.minecraft.nbt.NBTTagCompound;
/*  11:    */ import net.minecraft.util.AxisAlignedBB;
/*  12:    */ import net.minecraft.util.ChunkCoordinates;
/*  13:    */ import net.minecraft.util.DamageSource;
/*  14:    */ import net.minecraft.util.MathHelper;
/*  15:    */ import net.minecraft.world.World;
/*  16:    */ 
/*  17:    */ public class EntityBat
/*  18:    */   extends EntityAmbientCreature
/*  19:    */ {
/*  20:    */   private ChunkCoordinates spawnPosition;
/*  21:    */   private static final String __OBFID = "CL_00001637";
/*  22:    */   
/*  23:    */   public EntityBat(World par1World)
/*  24:    */   {
/*  25: 21 */     super(par1World);
/*  26: 22 */     setSize(0.5F, 0.9F);
/*  27: 23 */     setIsBatHanging(true);
/*  28:    */   }
/*  29:    */   
/*  30:    */   protected void entityInit()
/*  31:    */   {
/*  32: 28 */     super.entityInit();
/*  33: 29 */     this.dataWatcher.addObject(16, new Byte((byte)0));
/*  34:    */   }
/*  35:    */   
/*  36:    */   protected float getSoundVolume()
/*  37:    */   {
/*  38: 37 */     return 0.1F;
/*  39:    */   }
/*  40:    */   
/*  41:    */   protected float getSoundPitch()
/*  42:    */   {
/*  43: 45 */     return super.getSoundPitch() * 0.95F;
/*  44:    */   }
/*  45:    */   
/*  46:    */   protected String getLivingSound()
/*  47:    */   {
/*  48: 53 */     return (getIsBatHanging()) && (this.rand.nextInt(4) != 0) ? null : "mob.bat.idle";
/*  49:    */   }
/*  50:    */   
/*  51:    */   protected String getHurtSound()
/*  52:    */   {
/*  53: 61 */     return "mob.bat.hurt";
/*  54:    */   }
/*  55:    */   
/*  56:    */   protected String getDeathSound()
/*  57:    */   {
/*  58: 69 */     return "mob.bat.death";
/*  59:    */   }
/*  60:    */   
/*  61:    */   public boolean canBePushed()
/*  62:    */   {
/*  63: 77 */     return false;
/*  64:    */   }
/*  65:    */   
/*  66:    */   protected void collideWithEntity(Entity par1Entity) {}
/*  67:    */   
/*  68:    */   protected void collideWithNearbyEntities() {}
/*  69:    */   
/*  70:    */   protected void applyEntityAttributes()
/*  71:    */   {
/*  72: 86 */     super.applyEntityAttributes();
/*  73: 87 */     getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(6.0D);
/*  74:    */   }
/*  75:    */   
/*  76:    */   public boolean getIsBatHanging()
/*  77:    */   {
/*  78: 92 */     return (this.dataWatcher.getWatchableObjectByte(16) & 0x1) != 0;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public void setIsBatHanging(boolean par1)
/*  82:    */   {
/*  83: 97 */     byte var2 = this.dataWatcher.getWatchableObjectByte(16);
/*  84: 99 */     if (par1) {
/*  85:101 */       this.dataWatcher.updateObject(16, Byte.valueOf((byte)(var2 | 0x1)));
/*  86:    */     } else {
/*  87:105 */       this.dataWatcher.updateObject(16, Byte.valueOf((byte)(var2 & 0xFFFFFFFE)));
/*  88:    */     }
/*  89:    */   }
/*  90:    */   
/*  91:    */   protected boolean isAIEnabled()
/*  92:    */   {
/*  93:114 */     return true;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public void onUpdate()
/*  97:    */   {
/*  98:122 */     super.onUpdate();
/*  99:124 */     if (getIsBatHanging())
/* 100:    */     {
/* 101:126 */       this.motionX = (this.motionY = this.motionZ = 0.0D);
/* 102:127 */       this.posY = (MathHelper.floor_double(this.posY) + 1.0D - this.height);
/* 103:    */     }
/* 104:    */     else
/* 105:    */     {
/* 106:131 */       this.motionY *= 0.6000000238418579D;
/* 107:    */     }
/* 108:    */   }
/* 109:    */   
/* 110:    */   protected void updateAITasks()
/* 111:    */   {
/* 112:137 */     super.updateAITasks();
/* 113:139 */     if (getIsBatHanging())
/* 114:    */     {
/* 115:141 */       if (!this.worldObj.getBlock(MathHelper.floor_double(this.posX), (int)this.posY + 1, MathHelper.floor_double(this.posZ)).isNormalCube())
/* 116:    */       {
/* 117:143 */         setIsBatHanging(false);
/* 118:144 */         this.worldObj.playAuxSFXAtEntity(null, 1015, (int)this.posX, (int)this.posY, (int)this.posZ, 0);
/* 119:    */       }
/* 120:    */       else
/* 121:    */       {
/* 122:148 */         if (this.rand.nextInt(200) == 0) {
/* 123:150 */           this.rotationYawHead = this.rand.nextInt(360);
/* 124:    */         }
/* 125:153 */         if (this.worldObj.getClosestPlayerToEntity(this, 4.0D) != null)
/* 126:    */         {
/* 127:155 */           setIsBatHanging(false);
/* 128:156 */           this.worldObj.playAuxSFXAtEntity(null, 1015, (int)this.posX, (int)this.posY, (int)this.posZ, 0);
/* 129:    */         }
/* 130:    */       }
/* 131:    */     }
/* 132:    */     else
/* 133:    */     {
/* 134:162 */       if ((this.spawnPosition != null) && ((!this.worldObj.isAirBlock(this.spawnPosition.posX, this.spawnPosition.posY, this.spawnPosition.posZ)) || (this.spawnPosition.posY < 1))) {
/* 135:164 */         this.spawnPosition = null;
/* 136:    */       }
/* 137:167 */       if ((this.spawnPosition == null) || (this.rand.nextInt(30) == 0) || (this.spawnPosition.getDistanceSquared((int)this.posX, (int)this.posY, (int)this.posZ) < 4.0F)) {
/* 138:169 */         this.spawnPosition = new ChunkCoordinates((int)this.posX + this.rand.nextInt(7) - this.rand.nextInt(7), (int)this.posY + this.rand.nextInt(6) - 2, (int)this.posZ + this.rand.nextInt(7) - this.rand.nextInt(7));
/* 139:    */       }
/* 140:172 */       double var1 = this.spawnPosition.posX + 0.5D - this.posX;
/* 141:173 */       double var3 = this.spawnPosition.posY + 0.1D - this.posY;
/* 142:174 */       double var5 = this.spawnPosition.posZ + 0.5D - this.posZ;
/* 143:175 */       this.motionX += (Math.signum(var1) * 0.5D - this.motionX) * 0.1000000014901161D;
/* 144:176 */       this.motionY += (Math.signum(var3) * 0.699999988079071D - this.motionY) * 0.1000000014901161D;
/* 145:177 */       this.motionZ += (Math.signum(var5) * 0.5D - this.motionZ) * 0.1000000014901161D;
/* 146:178 */       float var7 = (float)(Math.atan2(this.motionZ, this.motionX) * 180.0D / 3.141592653589793D) - 90.0F;
/* 147:179 */       float var8 = MathHelper.wrapAngleTo180_float(var7 - this.rotationYaw);
/* 148:180 */       this.moveForward = 0.5F;
/* 149:181 */       this.rotationYaw += var8;
/* 150:183 */       if ((this.rand.nextInt(100) == 0) && (this.worldObj.getBlock(MathHelper.floor_double(this.posX), (int)this.posY + 1, MathHelper.floor_double(this.posZ)).isNormalCube())) {
/* 151:185 */         setIsBatHanging(true);
/* 152:    */       }
/* 153:    */     }
/* 154:    */   }
/* 155:    */   
/* 156:    */   protected boolean canTriggerWalking()
/* 157:    */   {
/* 158:196 */     return false;
/* 159:    */   }
/* 160:    */   
/* 161:    */   protected void fall(float par1) {}
/* 162:    */   
/* 163:    */   protected void updateFallState(double par1, boolean par3) {}
/* 164:    */   
/* 165:    */   public boolean doesEntityNotTriggerPressurePlate()
/* 166:    */   {
/* 167:212 */     return true;
/* 168:    */   }
/* 169:    */   
/* 170:    */   public boolean attackEntityFrom(DamageSource par1DamageSource, float par2)
/* 171:    */   {
/* 172:220 */     if (isEntityInvulnerable()) {
/* 173:222 */       return false;
/* 174:    */     }
/* 175:226 */     if ((!this.worldObj.isClient) && (getIsBatHanging())) {
/* 176:228 */       setIsBatHanging(false);
/* 177:    */     }
/* 178:231 */     return super.attackEntityFrom(par1DamageSource, par2);
/* 179:    */   }
/* 180:    */   
/* 181:    */   public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
/* 182:    */   {
/* 183:240 */     super.readEntityFromNBT(par1NBTTagCompound);
/* 184:241 */     this.dataWatcher.updateObject(16, Byte.valueOf(par1NBTTagCompound.getByte("BatFlags")));
/* 185:    */   }
/* 186:    */   
/* 187:    */   public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
/* 188:    */   {
/* 189:249 */     super.writeEntityToNBT(par1NBTTagCompound);
/* 190:250 */     par1NBTTagCompound.setByte("BatFlags", this.dataWatcher.getWatchableObjectByte(16));
/* 191:    */   }
/* 192:    */   
/* 193:    */   public boolean getCanSpawnHere()
/* 194:    */   {
/* 195:258 */     int var1 = MathHelper.floor_double(this.boundingBox.minY);
/* 196:260 */     if (var1 >= 63) {
/* 197:262 */       return false;
/* 198:    */     }
/* 199:266 */     int var2 = MathHelper.floor_double(this.posX);
/* 200:267 */     int var3 = MathHelper.floor_double(this.posZ);
/* 201:268 */     int var4 = this.worldObj.getBlockLightValue(var2, var1, var3);
/* 202:269 */     byte var5 = 4;
/* 203:270 */     Calendar var6 = this.worldObj.getCurrentDate();
/* 204:272 */     if (((var6.get(2) + 1 != 10) || (var6.get(5) < 20)) && ((var6.get(2) + 1 != 11) || (var6.get(5) > 3)))
/* 205:    */     {
/* 206:274 */       if (this.rand.nextBoolean()) {
/* 207:276 */         return false;
/* 208:    */       }
/* 209:    */     }
/* 210:    */     else {
/* 211:281 */       var5 = 7;
/* 212:    */     }
/* 213:284 */     return var4 > this.rand.nextInt(var5) ? false : super.getCanSpawnHere();
/* 214:    */   }
/* 215:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.entity.passive.EntityBat
 * JD-Core Version:    0.7.0.1
 */