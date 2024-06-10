/*   1:    */ package net.minecraft.client.particle;
/*   2:    */ 
/*   3:    */ import java.util.Random;
/*   4:    */ import net.minecraft.client.Minecraft;
/*   5:    */ import net.minecraft.client.renderer.Tessellator;
/*   6:    */ import net.minecraft.entity.EntityLivingBase;
/*   7:    */ import net.minecraft.nbt.NBTTagCompound;
/*   8:    */ import net.minecraft.nbt.NBTTagList;
/*   9:    */ import net.minecraft.util.MathHelper;
/*  10:    */ import net.minecraft.world.World;
/*  11:    */ 
/*  12:    */ public class EntityFireworkStarterFX
/*  13:    */   extends EntityFX
/*  14:    */ {
/*  15:    */   private int fireworkAge;
/*  16:    */   private final EffectRenderer theEffectRenderer;
/*  17:    */   private NBTTagList fireworkExplosions;
/*  18:    */   boolean twinkle;
/*  19:    */   private static final String __OBFID = "CL_00000906";
/*  20:    */   
/*  21:    */   public EntityFireworkStarterFX(World par1World, double par2, double par4, double par6, double par8, double par10, double par12, EffectRenderer par14EffectRenderer, NBTTagCompound par15NBTTagCompound)
/*  22:    */   {
/*  23: 20 */     super(par1World, par2, par4, par6, 0.0D, 0.0D, 0.0D);
/*  24: 21 */     this.motionX = par8;
/*  25: 22 */     this.motionY = par10;
/*  26: 23 */     this.motionZ = par12;
/*  27: 24 */     this.theEffectRenderer = par14EffectRenderer;
/*  28: 25 */     this.particleMaxAge = 8;
/*  29: 27 */     if (par15NBTTagCompound != null)
/*  30:    */     {
/*  31: 29 */       this.fireworkExplosions = par15NBTTagCompound.getTagList("Explosions", 10);
/*  32: 31 */       if (this.fireworkExplosions.tagCount() == 0)
/*  33:    */       {
/*  34: 33 */         this.fireworkExplosions = null;
/*  35:    */       }
/*  36:    */       else
/*  37:    */       {
/*  38: 37 */         this.particleMaxAge = (this.fireworkExplosions.tagCount() * 2 - 1);
/*  39: 39 */         for (int var16 = 0; var16 < this.fireworkExplosions.tagCount(); var16++)
/*  40:    */         {
/*  41: 41 */           NBTTagCompound var17 = this.fireworkExplosions.getCompoundTagAt(var16);
/*  42: 43 */           if (var17.getBoolean("Flicker"))
/*  43:    */           {
/*  44: 45 */             this.twinkle = true;
/*  45: 46 */             this.particleMaxAge += 15;
/*  46: 47 */             break;
/*  47:    */           }
/*  48:    */         }
/*  49:    */       }
/*  50:    */     }
/*  51:    */   }
/*  52:    */   
/*  53:    */   public void renderParticle(Tessellator par1Tessellator, float par2, float par3, float par4, float par5, float par6, float par7) {}
/*  54:    */   
/*  55:    */   public void onUpdate()
/*  56:    */   {
/*  57: 63 */     if ((this.fireworkAge == 0) && (this.fireworkExplosions != null))
/*  58:    */     {
/*  59: 65 */       boolean var1 = func_92037_i();
/*  60: 66 */       boolean var2 = false;
/*  61: 68 */       if (this.fireworkExplosions.tagCount() >= 3) {
/*  62: 70 */         var2 = true;
/*  63:    */       } else {
/*  64: 74 */         for (int var3 = 0; var3 < this.fireworkExplosions.tagCount(); var3++)
/*  65:    */         {
/*  66: 76 */           NBTTagCompound var4 = this.fireworkExplosions.getCompoundTagAt(var3);
/*  67: 78 */           if (var4.getByte("Type") == 1)
/*  68:    */           {
/*  69: 80 */             var2 = true;
/*  70: 81 */             break;
/*  71:    */           }
/*  72:    */         }
/*  73:    */       }
/*  74: 86 */       String var15 = "fireworks." + (var2 ? "largeBlast" : "blast") + (var1 ? "_far" : "");
/*  75: 87 */       this.worldObj.playSound(this.posX, this.posY, this.posZ, var15, 20.0F, 0.95F + this.rand.nextFloat() * 0.1F, true);
/*  76:    */     }
/*  77: 90 */     if ((this.fireworkAge % 2 == 0) && (this.fireworkExplosions != null) && (this.fireworkAge / 2 < this.fireworkExplosions.tagCount()))
/*  78:    */     {
/*  79: 92 */       int var13 = this.fireworkAge / 2;
/*  80: 93 */       NBTTagCompound var14 = this.fireworkExplosions.getCompoundTagAt(var13);
/*  81: 94 */       byte var17 = var14.getByte("Type");
/*  82: 95 */       boolean var18 = var14.getBoolean("Trail");
/*  83: 96 */       boolean var5 = var14.getBoolean("Flicker");
/*  84: 97 */       int[] var6 = var14.getIntArray("Colors");
/*  85: 98 */       int[] var7 = var14.getIntArray("FadeColors");
/*  86:100 */       if (var17 == 1) {
/*  87:102 */         createBall(0.5D, 4, var6, var7, var18, var5);
/*  88:104 */       } else if (var17 == 2) {
/*  89:106 */         createShaped(0.5D, new double[][] { { 0.0D, 1.0D }, { 0.3455D, 0.309D }, { 0.9511D, 0.309D }, { 0.3795918367346939D, -0.126530612244898D }, { 0.6122448979591837D, -0.8040816326530612D }, { 0.0D, -0.3591836734693877D } }, var6, var7, var18, var5, false);
/*  90:108 */       } else if (var17 == 3) {
/*  91:110 */         createShaped(0.5D, new double[][] { { 0.0D, 0.2D }, { 0.2D, 0.2D }, { 0.2D, 0.6D }, { 0.6D, 0.6D }, { 0.6D, 0.2D }, { 0.2D, 0.2D }, { 0.2D, 0.0D }, { 0.4D, 0.0D }, { 0.4D, -0.6D }, { 0.2D, -0.6D }, { 0.2D, -0.4D }, { 0.0D, -0.4D } }, var6, var7, var18, var5, true);
/*  92:112 */       } else if (var17 == 4) {
/*  93:114 */         createBurst(var6, var7, var18, var5);
/*  94:    */       } else {
/*  95:118 */         createBall(0.25D, 2, var6, var7, var18, var5);
/*  96:    */       }
/*  97:121 */       int var8 = var6[0];
/*  98:122 */       float var9 = ((var8 & 0xFF0000) >> 16) / 255.0F;
/*  99:123 */       float var10 = ((var8 & 0xFF00) >> 8) / 255.0F;
/* 100:124 */       float var11 = ((var8 & 0xFF) >> 0) / 255.0F;
/* 101:125 */       EntityFireworkOverlayFX var12 = new EntityFireworkOverlayFX(this.worldObj, this.posX, this.posY, this.posZ);
/* 102:126 */       var12.setRBGColorF(var9, var10, var11);
/* 103:127 */       this.theEffectRenderer.addEffect(var12);
/* 104:    */     }
/* 105:130 */     this.fireworkAge += 1;
/* 106:132 */     if (this.fireworkAge > this.particleMaxAge)
/* 107:    */     {
/* 108:134 */       if (this.twinkle)
/* 109:    */       {
/* 110:136 */         boolean var1 = func_92037_i();
/* 111:137 */         String var16 = "fireworks." + (var1 ? "twinkle_far" : "twinkle");
/* 112:138 */         this.worldObj.playSound(this.posX, this.posY, this.posZ, var16, 20.0F, 0.9F + this.rand.nextFloat() * 0.15F, true);
/* 113:    */       }
/* 114:141 */       setDead();
/* 115:    */     }
/* 116:    */   }
/* 117:    */   
/* 118:    */   private boolean func_92037_i()
/* 119:    */   {
/* 120:147 */     Minecraft var1 = Minecraft.getMinecraft();
/* 121:148 */     return (var1 == null) || (var1.renderViewEntity == null) || (var1.renderViewEntity.getDistanceSq(this.posX, this.posY, this.posZ) >= 256.0D);
/* 122:    */   }
/* 123:    */   
/* 124:    */   private void createParticle(double par1, double par3, double par5, double par7, double par9, double par11, int[] par13ArrayOfInteger, int[] par14ArrayOfInteger, boolean par15, boolean par16)
/* 125:    */   {
/* 126:157 */     EntityFireworkSparkFX var17 = new EntityFireworkSparkFX(this.worldObj, par1, par3, par5, par7, par9, par11, this.theEffectRenderer);
/* 127:158 */     var17.setTrail(par15);
/* 128:159 */     var17.setTwinkle(par16);
/* 129:160 */     int var18 = this.rand.nextInt(par13ArrayOfInteger.length);
/* 130:161 */     var17.setColour(par13ArrayOfInteger[var18]);
/* 131:163 */     if ((par14ArrayOfInteger != null) && (par14ArrayOfInteger.length > 0)) {
/* 132:165 */       var17.setFadeColour(par14ArrayOfInteger[this.rand.nextInt(par14ArrayOfInteger.length)]);
/* 133:    */     }
/* 134:168 */     this.theEffectRenderer.addEffect(var17);
/* 135:    */   }
/* 136:    */   
/* 137:    */   private void createBall(double par1, int par3, int[] par4ArrayOfInteger, int[] par5ArrayOfInteger, boolean par6, boolean par7)
/* 138:    */   {
/* 139:177 */     double var8 = this.posX;
/* 140:178 */     double var10 = this.posY;
/* 141:179 */     double var12 = this.posZ;
/* 142:181 */     for (int var14 = -par3; var14 <= par3; var14++) {
/* 143:183 */       for (int var15 = -par3; var15 <= par3; var15++) {
/* 144:185 */         for (int var16 = -par3; var16 <= par3; var16++)
/* 145:    */         {
/* 146:187 */           double var17 = var15 + (this.rand.nextDouble() - this.rand.nextDouble()) * 0.5D;
/* 147:188 */           double var19 = var14 + (this.rand.nextDouble() - this.rand.nextDouble()) * 0.5D;
/* 148:189 */           double var21 = var16 + (this.rand.nextDouble() - this.rand.nextDouble()) * 0.5D;
/* 149:190 */           double var23 = MathHelper.sqrt_double(var17 * var17 + var19 * var19 + var21 * var21) / par1 + this.rand.nextGaussian() * 0.05D;
/* 150:191 */           createParticle(var8, var10, var12, var17 / var23, var19 / var23, var21 / var23, par4ArrayOfInteger, par5ArrayOfInteger, par6, par7);
/* 151:193 */           if ((var14 != -par3) && (var14 != par3) && (var15 != -par3) && (var15 != par3)) {
/* 152:195 */             var16 += par3 * 2 - 1;
/* 153:    */           }
/* 154:    */         }
/* 155:    */       }
/* 156:    */     }
/* 157:    */   }
/* 158:    */   
/* 159:    */   private void createShaped(double par1, double[][] par3ArrayOfDouble, int[] par4ArrayOfInteger, int[] par5ArrayOfInteger, boolean par6, boolean par7, boolean par8)
/* 160:    */   {
/* 161:208 */     double var9 = par3ArrayOfDouble[0][0];
/* 162:209 */     double var11 = par3ArrayOfDouble[0][1];
/* 163:210 */     createParticle(this.posX, this.posY, this.posZ, var9 * par1, var11 * par1, 0.0D, par4ArrayOfInteger, par5ArrayOfInteger, par6, par7);
/* 164:211 */     float var13 = this.rand.nextFloat() * 3.141593F;
/* 165:212 */     double var14 = par8 ? 0.034D : 0.34D;
/* 166:214 */     for (int var16 = 0; var16 < 3; var16++)
/* 167:    */     {
/* 168:216 */       double var17 = var13 + var16 * 3.141593F * var14;
/* 169:217 */       double var19 = var9;
/* 170:218 */       double var21 = var11;
/* 171:220 */       for (int var23 = 1; var23 < par3ArrayOfDouble.length; var23++)
/* 172:    */       {
/* 173:222 */         double var24 = par3ArrayOfDouble[var23][0];
/* 174:223 */         double var26 = par3ArrayOfDouble[var23][1];
/* 175:225 */         for (double var28 = 0.25D; var28 <= 1.0D; var28 += 0.25D)
/* 176:    */         {
/* 177:227 */           double var30 = (var19 + (var24 - var19) * var28) * par1;
/* 178:228 */           double var32 = (var21 + (var26 - var21) * var28) * par1;
/* 179:229 */           double var34 = var30 * Math.sin(var17);
/* 180:230 */           var30 *= Math.cos(var17);
/* 181:232 */           for (double var36 = -1.0D; var36 <= 1.0D; var36 += 2.0D) {
/* 182:234 */             createParticle(this.posX, this.posY, this.posZ, var30 * var36, var32, var34 * var36, par4ArrayOfInteger, par5ArrayOfInteger, par6, par7);
/* 183:    */           }
/* 184:    */         }
/* 185:238 */         var19 = var24;
/* 186:239 */         var21 = var26;
/* 187:    */       }
/* 188:    */     }
/* 189:    */   }
/* 190:    */   
/* 191:    */   private void createBurst(int[] par1ArrayOfInteger, int[] par2ArrayOfInteger, boolean par3, boolean par4)
/* 192:    */   {
/* 193:249 */     double var5 = this.rand.nextGaussian() * 0.05D;
/* 194:250 */     double var7 = this.rand.nextGaussian() * 0.05D;
/* 195:252 */     for (int var9 = 0; var9 < 70; var9++)
/* 196:    */     {
/* 197:254 */       double var10 = this.motionX * 0.5D + this.rand.nextGaussian() * 0.15D + var5;
/* 198:255 */       double var12 = this.motionZ * 0.5D + this.rand.nextGaussian() * 0.15D + var7;
/* 199:256 */       double var14 = this.motionY * 0.5D + this.rand.nextDouble() * 0.5D;
/* 200:257 */       createParticle(this.posX, this.posY, this.posZ, var10, var14, var12, par1ArrayOfInteger, par2ArrayOfInteger, par3, par4);
/* 201:    */     }
/* 202:    */   }
/* 203:    */   
/* 204:    */   public int getFXLayer()
/* 205:    */   {
/* 206:263 */     return 0;
/* 207:    */   }
/* 208:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.particle.EntityFireworkStarterFX
 * JD-Core Version:    0.7.0.1
 */