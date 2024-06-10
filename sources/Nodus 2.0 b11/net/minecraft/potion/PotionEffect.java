/*   1:    */ package net.minecraft.potion;
/*   2:    */ 
/*   3:    */ import java.io.PrintStream;
/*   4:    */ import net.minecraft.entity.EntityLivingBase;
/*   5:    */ import net.minecraft.nbt.NBTTagCompound;
/*   6:    */ 
/*   7:    */ public class PotionEffect
/*   8:    */ {
/*   9:    */   private int potionID;
/*  10:    */   private int duration;
/*  11:    */   private int amplifier;
/*  12:    */   private boolean isSplashPotion;
/*  13:    */   private boolean isAmbient;
/*  14:    */   private boolean isPotionDurationMax;
/*  15:    */   private static final String __OBFID = "CL_00001529";
/*  16:    */   
/*  17:    */   public PotionEffect(int par1, int par2)
/*  18:    */   {
/*  19: 29 */     this(par1, par2, 0);
/*  20:    */   }
/*  21:    */   
/*  22:    */   public PotionEffect(int par1, int par2, int par3)
/*  23:    */   {
/*  24: 34 */     this(par1, par2, par3, false);
/*  25:    */   }
/*  26:    */   
/*  27:    */   public PotionEffect(int par1, int par2, int par3, boolean par4)
/*  28:    */   {
/*  29: 39 */     this.potionID = par1;
/*  30: 40 */     this.duration = par2;
/*  31: 41 */     this.amplifier = par3;
/*  32: 42 */     this.isAmbient = par4;
/*  33:    */   }
/*  34:    */   
/*  35:    */   public PotionEffect(PotionEffect par1PotionEffect)
/*  36:    */   {
/*  37: 47 */     this.potionID = par1PotionEffect.potionID;
/*  38: 48 */     this.duration = par1PotionEffect.duration;
/*  39: 49 */     this.amplifier = par1PotionEffect.amplifier;
/*  40:    */   }
/*  41:    */   
/*  42:    */   public void combine(PotionEffect par1PotionEffect)
/*  43:    */   {
/*  44: 58 */     if (this.potionID != par1PotionEffect.potionID) {
/*  45: 60 */       System.err.println("This method should only be called for matching effects!");
/*  46:    */     }
/*  47: 63 */     if (par1PotionEffect.amplifier > this.amplifier)
/*  48:    */     {
/*  49: 65 */       this.amplifier = par1PotionEffect.amplifier;
/*  50: 66 */       this.duration = par1PotionEffect.duration;
/*  51:    */     }
/*  52: 68 */     else if ((par1PotionEffect.amplifier == this.amplifier) && (this.duration < par1PotionEffect.duration))
/*  53:    */     {
/*  54: 70 */       this.duration = par1PotionEffect.duration;
/*  55:    */     }
/*  56: 72 */     else if ((!par1PotionEffect.isAmbient) && (this.isAmbient))
/*  57:    */     {
/*  58: 74 */       this.isAmbient = par1PotionEffect.isAmbient;
/*  59:    */     }
/*  60:    */   }
/*  61:    */   
/*  62:    */   public int getPotionID()
/*  63:    */   {
/*  64: 83 */     return this.potionID;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public int getDuration()
/*  68:    */   {
/*  69: 88 */     return this.duration;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public int getAmplifier()
/*  73:    */   {
/*  74: 93 */     return this.amplifier;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public void setSplashPotion(boolean par1)
/*  78:    */   {
/*  79:101 */     this.isSplashPotion = par1;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public boolean getIsAmbient()
/*  83:    */   {
/*  84:109 */     return this.isAmbient;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public boolean onUpdate(EntityLivingBase par1EntityLivingBase)
/*  88:    */   {
/*  89:114 */     if (this.duration > 0)
/*  90:    */     {
/*  91:116 */       if (Potion.potionTypes[this.potionID].isReady(this.duration, this.amplifier)) {
/*  92:118 */         performEffect(par1EntityLivingBase);
/*  93:    */       }
/*  94:121 */       deincrementDuration();
/*  95:    */     }
/*  96:124 */     return this.duration > 0;
/*  97:    */   }
/*  98:    */   
/*  99:    */   private int deincrementDuration()
/* 100:    */   {
/* 101:129 */     return --this.duration;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public void performEffect(EntityLivingBase par1EntityLivingBase)
/* 105:    */   {
/* 106:134 */     if (this.duration > 0) {
/* 107:136 */       Potion.potionTypes[this.potionID].performEffect(par1EntityLivingBase, this.amplifier);
/* 108:    */     }
/* 109:    */   }
/* 110:    */   
/* 111:    */   public String getEffectName()
/* 112:    */   {
/* 113:142 */     return Potion.potionTypes[this.potionID].getName();
/* 114:    */   }
/* 115:    */   
/* 116:    */   public int hashCode()
/* 117:    */   {
/* 118:147 */     return this.potionID;
/* 119:    */   }
/* 120:    */   
/* 121:    */   public String toString()
/* 122:    */   {
/* 123:152 */     String var1 = "";
/* 124:154 */     if (getAmplifier() > 0) {
/* 125:156 */       var1 = getEffectName() + " x " + (getAmplifier() + 1) + ", Duration: " + getDuration();
/* 126:    */     } else {
/* 127:160 */       var1 = getEffectName() + ", Duration: " + getDuration();
/* 128:    */     }
/* 129:163 */     if (this.isSplashPotion) {
/* 130:165 */       var1 = var1 + ", Splash: true";
/* 131:    */     }
/* 132:168 */     return Potion.potionTypes[this.potionID].isUsable() ? "(" + var1 + ")" : var1;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public boolean equals(Object par1Obj)
/* 136:    */   {
/* 137:173 */     if (!(par1Obj instanceof PotionEffect)) {
/* 138:175 */       return false;
/* 139:    */     }
/* 140:179 */     PotionEffect var2 = (PotionEffect)par1Obj;
/* 141:180 */     return (this.potionID == var2.potionID) && (this.amplifier == var2.amplifier) && (this.duration == var2.duration) && (this.isSplashPotion == var2.isSplashPotion) && (this.isAmbient == var2.isAmbient);
/* 142:    */   }
/* 143:    */   
/* 144:    */   public NBTTagCompound writeCustomPotionEffectToNBT(NBTTagCompound par1NBTTagCompound)
/* 145:    */   {
/* 146:189 */     par1NBTTagCompound.setByte("Id", (byte)getPotionID());
/* 147:190 */     par1NBTTagCompound.setByte("Amplifier", (byte)getAmplifier());
/* 148:191 */     par1NBTTagCompound.setInteger("Duration", getDuration());
/* 149:192 */     par1NBTTagCompound.setBoolean("Ambient", getIsAmbient());
/* 150:193 */     return par1NBTTagCompound;
/* 151:    */   }
/* 152:    */   
/* 153:    */   public static PotionEffect readCustomPotionEffectFromNBT(NBTTagCompound par0NBTTagCompound)
/* 154:    */   {
/* 155:201 */     byte var1 = par0NBTTagCompound.getByte("Id");
/* 156:203 */     if ((var1 >= 0) && (var1 < Potion.potionTypes.length) && (Potion.potionTypes[var1] != null))
/* 157:    */     {
/* 158:205 */       byte var2 = par0NBTTagCompound.getByte("Amplifier");
/* 159:206 */       int var3 = par0NBTTagCompound.getInteger("Duration");
/* 160:207 */       boolean var4 = par0NBTTagCompound.getBoolean("Ambient");
/* 161:208 */       return new PotionEffect(var1, var3, var2, var4);
/* 162:    */     }
/* 163:212 */     return null;
/* 164:    */   }
/* 165:    */   
/* 166:    */   public void setPotionDurationMax(boolean par1)
/* 167:    */   {
/* 168:221 */     this.isPotionDurationMax = par1;
/* 169:    */   }
/* 170:    */   
/* 171:    */   public boolean getIsPotionDurationMax()
/* 172:    */   {
/* 173:226 */     return this.isPotionDurationMax;
/* 174:    */   }
/* 175:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.potion.PotionEffect
 * JD-Core Version:    0.7.0.1
 */