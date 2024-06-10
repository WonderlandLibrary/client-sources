/*   1:    */ package net.minecraft.entity.passive;
/*   2:    */ 
/*   3:    */ import java.util.Random;
/*   4:    */ import net.minecraft.entity.DataWatcher;
/*   5:    */ import net.minecraft.entity.EntityLivingBase;
/*   6:    */ import net.minecraft.entity.IEntityOwnable;
/*   7:    */ import net.minecraft.entity.ai.EntityAISit;
/*   8:    */ import net.minecraft.nbt.NBTTagCompound;
/*   9:    */ import net.minecraft.scoreboard.Team;
/*  10:    */ import net.minecraft.world.World;
/*  11:    */ 
/*  12:    */ public abstract class EntityTameable
/*  13:    */   extends EntityAnimal
/*  14:    */   implements IEntityOwnable
/*  15:    */ {
/*  16: 13 */   protected EntityAISit aiSit = new EntityAISit(this);
/*  17:    */   private static final String __OBFID = "CL_00001561";
/*  18:    */   
/*  19:    */   public EntityTameable(World par1World)
/*  20:    */   {
/*  21: 18 */     super(par1World);
/*  22:    */   }
/*  23:    */   
/*  24:    */   protected void entityInit()
/*  25:    */   {
/*  26: 23 */     super.entityInit();
/*  27: 24 */     this.dataWatcher.addObject(16, Byte.valueOf((byte)0));
/*  28: 25 */     this.dataWatcher.addObject(17, "");
/*  29:    */   }
/*  30:    */   
/*  31:    */   public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
/*  32:    */   {
/*  33: 33 */     super.writeEntityToNBT(par1NBTTagCompound);
/*  34: 35 */     if (getOwnerName() == null) {
/*  35: 37 */       par1NBTTagCompound.setString("Owner", "");
/*  36:    */     } else {
/*  37: 41 */       par1NBTTagCompound.setString("Owner", getOwnerName());
/*  38:    */     }
/*  39: 44 */     par1NBTTagCompound.setBoolean("Sitting", isSitting());
/*  40:    */   }
/*  41:    */   
/*  42:    */   public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
/*  43:    */   {
/*  44: 52 */     super.readEntityFromNBT(par1NBTTagCompound);
/*  45: 53 */     String var2 = par1NBTTagCompound.getString("Owner");
/*  46: 55 */     if (var2.length() > 0)
/*  47:    */     {
/*  48: 57 */       setOwner(var2);
/*  49: 58 */       setTamed(true);
/*  50:    */     }
/*  51: 61 */     this.aiSit.setSitting(par1NBTTagCompound.getBoolean("Sitting"));
/*  52: 62 */     setSitting(par1NBTTagCompound.getBoolean("Sitting"));
/*  53:    */   }
/*  54:    */   
/*  55:    */   protected void playTameEffect(boolean par1)
/*  56:    */   {
/*  57: 70 */     String var2 = "heart";
/*  58: 72 */     if (!par1) {
/*  59: 74 */       var2 = "smoke";
/*  60:    */     }
/*  61: 77 */     for (int var3 = 0; var3 < 7; var3++)
/*  62:    */     {
/*  63: 79 */       double var4 = this.rand.nextGaussian() * 0.02D;
/*  64: 80 */       double var6 = this.rand.nextGaussian() * 0.02D;
/*  65: 81 */       double var8 = this.rand.nextGaussian() * 0.02D;
/*  66: 82 */       this.worldObj.spawnParticle(var2, this.posX + this.rand.nextFloat() * this.width * 2.0F - this.width, this.posY + 0.5D + this.rand.nextFloat() * this.height, this.posZ + this.rand.nextFloat() * this.width * 2.0F - this.width, var4, var6, var8);
/*  67:    */     }
/*  68:    */   }
/*  69:    */   
/*  70:    */   public void handleHealthUpdate(byte par1)
/*  71:    */   {
/*  72: 88 */     if (par1 == 7) {
/*  73: 90 */       playTameEffect(true);
/*  74: 92 */     } else if (par1 == 6) {
/*  75: 94 */       playTameEffect(false);
/*  76:    */     } else {
/*  77: 98 */       super.handleHealthUpdate(par1);
/*  78:    */     }
/*  79:    */   }
/*  80:    */   
/*  81:    */   public boolean isTamed()
/*  82:    */   {
/*  83:104 */     return (this.dataWatcher.getWatchableObjectByte(16) & 0x4) != 0;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public void setTamed(boolean par1)
/*  87:    */   {
/*  88:109 */     byte var2 = this.dataWatcher.getWatchableObjectByte(16);
/*  89:111 */     if (par1) {
/*  90:113 */       this.dataWatcher.updateObject(16, Byte.valueOf((byte)(var2 | 0x4)));
/*  91:    */     } else {
/*  92:117 */       this.dataWatcher.updateObject(16, Byte.valueOf((byte)(var2 & 0xFFFFFFFB)));
/*  93:    */     }
/*  94:    */   }
/*  95:    */   
/*  96:    */   public boolean isSitting()
/*  97:    */   {
/*  98:123 */     return (this.dataWatcher.getWatchableObjectByte(16) & 0x1) != 0;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public void setSitting(boolean par1)
/* 102:    */   {
/* 103:128 */     byte var2 = this.dataWatcher.getWatchableObjectByte(16);
/* 104:130 */     if (par1) {
/* 105:132 */       this.dataWatcher.updateObject(16, Byte.valueOf((byte)(var2 | 0x1)));
/* 106:    */     } else {
/* 107:136 */       this.dataWatcher.updateObject(16, Byte.valueOf((byte)(var2 & 0xFFFFFFFE)));
/* 108:    */     }
/* 109:    */   }
/* 110:    */   
/* 111:    */   public String getOwnerName()
/* 112:    */   {
/* 113:142 */     return this.dataWatcher.getWatchableObjectString(17);
/* 114:    */   }
/* 115:    */   
/* 116:    */   public void setOwner(String par1Str)
/* 117:    */   {
/* 118:147 */     this.dataWatcher.updateObject(17, par1Str);
/* 119:    */   }
/* 120:    */   
/* 121:    */   public EntityLivingBase getOwner()
/* 122:    */   {
/* 123:152 */     return this.worldObj.getPlayerEntityByName(getOwnerName());
/* 124:    */   }
/* 125:    */   
/* 126:    */   public EntityAISit func_70907_r()
/* 127:    */   {
/* 128:157 */     return this.aiSit;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public boolean func_142018_a(EntityLivingBase par1EntityLivingBase, EntityLivingBase par2EntityLivingBase)
/* 132:    */   {
/* 133:162 */     return true;
/* 134:    */   }
/* 135:    */   
/* 136:    */   public Team getTeam()
/* 137:    */   {
/* 138:167 */     if (isTamed())
/* 139:    */     {
/* 140:169 */       EntityLivingBase var1 = getOwner();
/* 141:171 */       if (var1 != null) {
/* 142:173 */         return var1.getTeam();
/* 143:    */       }
/* 144:    */     }
/* 145:177 */     return super.getTeam();
/* 146:    */   }
/* 147:    */   
/* 148:    */   public boolean isOnSameTeam(EntityLivingBase par1EntityLivingBase)
/* 149:    */   {
/* 150:182 */     if (isTamed())
/* 151:    */     {
/* 152:184 */       EntityLivingBase var2 = getOwner();
/* 153:186 */       if (par1EntityLivingBase == var2) {
/* 154:188 */         return true;
/* 155:    */       }
/* 156:191 */       if (var2 != null) {
/* 157:193 */         return var2.isOnSameTeam(par1EntityLivingBase);
/* 158:    */       }
/* 159:    */     }
/* 160:197 */     return super.isOnSameTeam(par1EntityLivingBase);
/* 161:    */   }
/* 162:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.entity.passive.EntityTameable
 * JD-Core Version:    0.7.0.1
 */