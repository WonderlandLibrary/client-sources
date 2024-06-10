/*   1:    */ package net.minecraft.client.particle;
/*   2:    */ 
/*   3:    */ import java.util.Random;
/*   4:    */ import net.minecraft.client.renderer.Tessellator;
/*   5:    */ import net.minecraft.entity.Entity;
/*   6:    */ import net.minecraft.nbt.NBTTagCompound;
/*   7:    */ import net.minecraft.util.IIcon;
/*   8:    */ import net.minecraft.util.MathHelper;
/*   9:    */ import net.minecraft.world.World;
/*  10:    */ 
/*  11:    */ public class EntityFX
/*  12:    */   extends Entity
/*  13:    */ {
/*  14:    */   protected int particleTextureIndexX;
/*  15:    */   protected int particleTextureIndexY;
/*  16:    */   protected float particleTextureJitterX;
/*  17:    */   protected float particleTextureJitterY;
/*  18:    */   protected int particleAge;
/*  19:    */   protected int particleMaxAge;
/*  20:    */   protected float particleScale;
/*  21:    */   protected float particleGravity;
/*  22:    */   protected float particleRed;
/*  23:    */   protected float particleGreen;
/*  24:    */   protected float particleBlue;
/*  25:    */   protected float particleAlpha;
/*  26:    */   protected IIcon particleIcon;
/*  27:    */   public static double interpPosX;
/*  28:    */   public static double interpPosY;
/*  29:    */   public static double interpPosZ;
/*  30:    */   private static final String __OBFID = "CL_00000914";
/*  31:    */   
/*  32:    */   protected EntityFX(World par1World, double par2, double par4, double par6)
/*  33:    */   {
/*  34: 46 */     super(par1World);
/*  35: 47 */     this.particleAlpha = 1.0F;
/*  36: 48 */     setSize(0.2F, 0.2F);
/*  37: 49 */     this.yOffset = (this.height / 2.0F);
/*  38: 50 */     setPosition(par2, par4, par6);
/*  39: 51 */     this.lastTickPosX = par2;
/*  40: 52 */     this.lastTickPosY = par4;
/*  41: 53 */     this.lastTickPosZ = par6;
/*  42: 54 */     this.particleRed = (this.particleGreen = this.particleBlue = 1.0F);
/*  43: 55 */     this.particleTextureJitterX = (this.rand.nextFloat() * 3.0F);
/*  44: 56 */     this.particleTextureJitterY = (this.rand.nextFloat() * 3.0F);
/*  45: 57 */     this.particleScale = ((this.rand.nextFloat() * 0.5F + 0.5F) * 2.0F);
/*  46: 58 */     this.particleMaxAge = ((int)(4.0F / (this.rand.nextFloat() * 0.9F + 0.1F)));
/*  47: 59 */     this.particleAge = 0;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public EntityFX(World par1World, double par2, double par4, double par6, double par8, double par10, double par12)
/*  51:    */   {
/*  52: 64 */     this(par1World, par2, par4, par6);
/*  53: 65 */     this.motionX = (par8 + (float)(Math.random() * 2.0D - 1.0D) * 0.4F);
/*  54: 66 */     this.motionY = (par10 + (float)(Math.random() * 2.0D - 1.0D) * 0.4F);
/*  55: 67 */     this.motionZ = (par12 + (float)(Math.random() * 2.0D - 1.0D) * 0.4F);
/*  56: 68 */     float var14 = (float)(Math.random() + Math.random() + 1.0D) * 0.15F;
/*  57: 69 */     float var15 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
/*  58: 70 */     this.motionX = (this.motionX / var15 * var14 * 0.4000000059604645D);
/*  59: 71 */     this.motionY = (this.motionY / var15 * var14 * 0.4000000059604645D + 0.1000000014901161D);
/*  60: 72 */     this.motionZ = (this.motionZ / var15 * var14 * 0.4000000059604645D);
/*  61:    */   }
/*  62:    */   
/*  63:    */   public EntityFX multiplyVelocity(float par1)
/*  64:    */   {
/*  65: 77 */     this.motionX *= par1;
/*  66: 78 */     this.motionY = ((this.motionY - 0.1000000014901161D) * par1 + 0.1000000014901161D);
/*  67: 79 */     this.motionZ *= par1;
/*  68: 80 */     return this;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public EntityFX multipleParticleScaleBy(float par1)
/*  72:    */   {
/*  73: 85 */     setSize(0.2F * par1, 0.2F * par1);
/*  74: 86 */     this.particleScale *= par1;
/*  75: 87 */     return this;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public void setRBGColorF(float par1, float par2, float par3)
/*  79:    */   {
/*  80: 92 */     this.particleRed = par1;
/*  81: 93 */     this.particleGreen = par2;
/*  82: 94 */     this.particleBlue = par3;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public void setAlphaF(float par1)
/*  86:    */   {
/*  87:102 */     this.particleAlpha = par1;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public float getRedColorF()
/*  91:    */   {
/*  92:107 */     return this.particleRed;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public float getGreenColorF()
/*  96:    */   {
/*  97:112 */     return this.particleGreen;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public float getBlueColorF()
/* 101:    */   {
/* 102:117 */     return this.particleBlue;
/* 103:    */   }
/* 104:    */   
/* 105:    */   protected boolean canTriggerWalking()
/* 106:    */   {
/* 107:126 */     return false;
/* 108:    */   }
/* 109:    */   
/* 110:    */   protected void entityInit() {}
/* 111:    */   
/* 112:    */   public void onUpdate()
/* 113:    */   {
/* 114:136 */     this.prevPosX = this.posX;
/* 115:137 */     this.prevPosY = this.posY;
/* 116:138 */     this.prevPosZ = this.posZ;
/* 117:140 */     if (this.particleAge++ >= this.particleMaxAge) {
/* 118:142 */       setDead();
/* 119:    */     }
/* 120:145 */     this.motionY -= 0.04D * this.particleGravity;
/* 121:146 */     moveEntity(this.motionX, this.motionY, this.motionZ);
/* 122:147 */     this.motionX *= 0.9800000190734863D;
/* 123:148 */     this.motionY *= 0.9800000190734863D;
/* 124:149 */     this.motionZ *= 0.9800000190734863D;
/* 125:151 */     if (this.onGround)
/* 126:    */     {
/* 127:153 */       this.motionX *= 0.699999988079071D;
/* 128:154 */       this.motionZ *= 0.699999988079071D;
/* 129:    */     }
/* 130:    */   }
/* 131:    */   
/* 132:    */   public void renderParticle(Tessellator par1Tessellator, float par2, float par3, float par4, float par5, float par6, float par7)
/* 133:    */   {
/* 134:160 */     float var8 = this.particleTextureIndexX / 16.0F;
/* 135:161 */     float var9 = var8 + 0.0624375F;
/* 136:162 */     float var10 = this.particleTextureIndexY / 16.0F;
/* 137:163 */     float var11 = var10 + 0.0624375F;
/* 138:164 */     float var12 = 0.1F * this.particleScale;
/* 139:166 */     if (this.particleIcon != null)
/* 140:    */     {
/* 141:168 */       var8 = this.particleIcon.getMinU();
/* 142:169 */       var9 = this.particleIcon.getMaxU();
/* 143:170 */       var10 = this.particleIcon.getMinV();
/* 144:171 */       var11 = this.particleIcon.getMaxV();
/* 145:    */     }
/* 146:174 */     float var13 = (float)(this.prevPosX + (this.posX - this.prevPosX) * par2 - interpPosX);
/* 147:175 */     float var14 = (float)(this.prevPosY + (this.posY - this.prevPosY) * par2 - interpPosY);
/* 148:176 */     float var15 = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * par2 - interpPosZ);
/* 149:177 */     par1Tessellator.setColorRGBA_F(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha);
/* 150:178 */     par1Tessellator.addVertexWithUV(var13 - par3 * var12 - par6 * var12, var14 - par4 * var12, var15 - par5 * var12 - par7 * var12, var9, var11);
/* 151:179 */     par1Tessellator.addVertexWithUV(var13 - par3 * var12 + par6 * var12, var14 + par4 * var12, var15 - par5 * var12 + par7 * var12, var9, var10);
/* 152:180 */     par1Tessellator.addVertexWithUV(var13 + par3 * var12 + par6 * var12, var14 + par4 * var12, var15 + par5 * var12 + par7 * var12, var8, var10);
/* 153:181 */     par1Tessellator.addVertexWithUV(var13 + par3 * var12 - par6 * var12, var14 - par4 * var12, var15 + par5 * var12 - par7 * var12, var8, var11);
/* 154:    */   }
/* 155:    */   
/* 156:    */   public int getFXLayer()
/* 157:    */   {
/* 158:186 */     return 0;
/* 159:    */   }
/* 160:    */   
/* 161:    */   public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {}
/* 162:    */   
/* 163:    */   public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {}
/* 164:    */   
/* 165:    */   public void setParticleIcon(IIcon par1Icon)
/* 166:    */   {
/* 167:201 */     if (getFXLayer() == 1)
/* 168:    */     {
/* 169:203 */       this.particleIcon = par1Icon;
/* 170:    */     }
/* 171:    */     else
/* 172:    */     {
/* 173:207 */       if (getFXLayer() != 2) {
/* 174:209 */         throw new RuntimeException("Invalid call to Particle.setTex, use coordinate methods");
/* 175:    */       }
/* 176:212 */       this.particleIcon = par1Icon;
/* 177:    */     }
/* 178:    */   }
/* 179:    */   
/* 180:    */   public void setParticleTextureIndex(int par1)
/* 181:    */   {
/* 182:221 */     if (getFXLayer() != 0) {
/* 183:223 */       throw new RuntimeException("Invalid call to Particle.setMiscTex");
/* 184:    */     }
/* 185:227 */     this.particleTextureIndexX = (par1 % 16);
/* 186:228 */     this.particleTextureIndexY = (par1 / 16);
/* 187:    */   }
/* 188:    */   
/* 189:    */   public void nextTextureIndexX()
/* 190:    */   {
/* 191:234 */     this.particleTextureIndexX += 1;
/* 192:    */   }
/* 193:    */   
/* 194:    */   public boolean canAttackWithItem()
/* 195:    */   {
/* 196:242 */     return false;
/* 197:    */   }
/* 198:    */   
/* 199:    */   public String toString()
/* 200:    */   {
/* 201:247 */     return getClass().getSimpleName() + ", Pos (" + this.posX + "," + this.posY + "," + this.posZ + "), RGBA (" + this.particleRed + "," + this.particleGreen + "," + this.particleBlue + "," + this.particleAlpha + "), Age " + this.particleAge;
/* 202:    */   }
/* 203:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.particle.EntityFX
 * JD-Core Version:    0.7.0.1
 */