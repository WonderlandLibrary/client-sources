/*   1:    */ package net.minecraft.client.particle;
/*   2:    */ 
/*   3:    */ import java.util.Random;
/*   4:    */ import net.minecraft.client.renderer.Tessellator;
/*   5:    */ import net.minecraft.util.AxisAlignedBB;
/*   6:    */ import net.minecraft.world.World;
/*   7:    */ 
/*   8:    */ public class EntityFireworkSparkFX
/*   9:    */   extends EntityFX
/*  10:    */ {
/*  11:  9 */   private int baseTextureIndex = 160;
/*  12:    */   private boolean field_92054_ax;
/*  13:    */   private boolean field_92048_ay;
/*  14:    */   private final EffectRenderer field_92047_az;
/*  15:    */   private float fadeColourRed;
/*  16:    */   private float fadeColourGreen;
/*  17:    */   private float fadeColourBlue;
/*  18:    */   private boolean hasFadeColour;
/*  19:    */   private static final String __OBFID = "CL_00000905";
/*  20:    */   
/*  21:    */   public EntityFireworkSparkFX(World par1World, double par2, double par4, double par6, double par8, double par10, double par12, EffectRenderer par14EffectRenderer)
/*  22:    */   {
/*  23: 21 */     super(par1World, par2, par4, par6);
/*  24: 22 */     this.motionX = par8;
/*  25: 23 */     this.motionY = par10;
/*  26: 24 */     this.motionZ = par12;
/*  27: 25 */     this.field_92047_az = par14EffectRenderer;
/*  28: 26 */     this.particleScale *= 0.75F;
/*  29: 27 */     this.particleMaxAge = (48 + this.rand.nextInt(12));
/*  30: 28 */     this.noClip = false;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public void setTrail(boolean par1)
/*  34:    */   {
/*  35: 33 */     this.field_92054_ax = par1;
/*  36:    */   }
/*  37:    */   
/*  38:    */   public void setTwinkle(boolean par1)
/*  39:    */   {
/*  40: 38 */     this.field_92048_ay = par1;
/*  41:    */   }
/*  42:    */   
/*  43:    */   public void setColour(int par1)
/*  44:    */   {
/*  45: 43 */     float var2 = ((par1 & 0xFF0000) >> 16) / 255.0F;
/*  46: 44 */     float var3 = ((par1 & 0xFF00) >> 8) / 255.0F;
/*  47: 45 */     float var4 = ((par1 & 0xFF) >> 0) / 255.0F;
/*  48: 46 */     float var5 = 1.0F;
/*  49: 47 */     setRBGColorF(var2 * var5, var3 * var5, var4 * var5);
/*  50:    */   }
/*  51:    */   
/*  52:    */   public void setFadeColour(int par1)
/*  53:    */   {
/*  54: 52 */     this.fadeColourRed = (((par1 & 0xFF0000) >> 16) / 255.0F);
/*  55: 53 */     this.fadeColourGreen = (((par1 & 0xFF00) >> 8) / 255.0F);
/*  56: 54 */     this.fadeColourBlue = (((par1 & 0xFF) >> 0) / 255.0F);
/*  57: 55 */     this.hasFadeColour = true;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public AxisAlignedBB getBoundingBox()
/*  61:    */   {
/*  62: 63 */     return null;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public boolean canBePushed()
/*  66:    */   {
/*  67: 71 */     return false;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public void renderParticle(Tessellator par1Tessellator, float par2, float par3, float par4, float par5, float par6, float par7)
/*  71:    */   {
/*  72: 76 */     if ((!this.field_92048_ay) || (this.particleAge < this.particleMaxAge / 3) || ((this.particleAge + this.particleMaxAge) / 3 % 2 == 0)) {
/*  73: 78 */       super.renderParticle(par1Tessellator, par2, par3, par4, par5, par6, par7);
/*  74:    */     }
/*  75:    */   }
/*  76:    */   
/*  77:    */   public void onUpdate()
/*  78:    */   {
/*  79: 87 */     this.prevPosX = this.posX;
/*  80: 88 */     this.prevPosY = this.posY;
/*  81: 89 */     this.prevPosZ = this.posZ;
/*  82: 91 */     if (this.particleAge++ >= this.particleMaxAge) {
/*  83: 93 */       setDead();
/*  84:    */     }
/*  85: 96 */     if (this.particleAge > this.particleMaxAge / 2)
/*  86:    */     {
/*  87: 98 */       setAlphaF(1.0F - (this.particleAge - this.particleMaxAge / 2) / this.particleMaxAge);
/*  88:100 */       if (this.hasFadeColour)
/*  89:    */       {
/*  90:102 */         this.particleRed += (this.fadeColourRed - this.particleRed) * 0.2F;
/*  91:103 */         this.particleGreen += (this.fadeColourGreen - this.particleGreen) * 0.2F;
/*  92:104 */         this.particleBlue += (this.fadeColourBlue - this.particleBlue) * 0.2F;
/*  93:    */       }
/*  94:    */     }
/*  95:108 */     setParticleTextureIndex(this.baseTextureIndex + (7 - this.particleAge * 8 / this.particleMaxAge));
/*  96:109 */     this.motionY -= 0.004D;
/*  97:110 */     moveEntity(this.motionX, this.motionY, this.motionZ);
/*  98:111 */     this.motionX *= 0.910000026226044D;
/*  99:112 */     this.motionY *= 0.910000026226044D;
/* 100:113 */     this.motionZ *= 0.910000026226044D;
/* 101:115 */     if (this.onGround)
/* 102:    */     {
/* 103:117 */       this.motionX *= 0.699999988079071D;
/* 104:118 */       this.motionZ *= 0.699999988079071D;
/* 105:    */     }
/* 106:121 */     if ((this.field_92054_ax) && (this.particleAge < this.particleMaxAge / 2) && ((this.particleAge + this.particleMaxAge) % 2 == 0))
/* 107:    */     {
/* 108:123 */       EntityFireworkSparkFX var1 = new EntityFireworkSparkFX(this.worldObj, this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D, this.field_92047_az);
/* 109:124 */       var1.setRBGColorF(this.particleRed, this.particleGreen, this.particleBlue);
/* 110:125 */       var1.particleAge = (var1.particleMaxAge / 2);
/* 111:127 */       if (this.hasFadeColour)
/* 112:    */       {
/* 113:129 */         var1.hasFadeColour = true;
/* 114:130 */         var1.fadeColourRed = this.fadeColourRed;
/* 115:131 */         var1.fadeColourGreen = this.fadeColourGreen;
/* 116:132 */         var1.fadeColourBlue = this.fadeColourBlue;
/* 117:    */       }
/* 118:135 */       var1.field_92048_ay = this.field_92048_ay;
/* 119:136 */       this.field_92047_az.addEffect(var1);
/* 120:    */     }
/* 121:    */   }
/* 122:    */   
/* 123:    */   public int getBrightnessForRender(float par1)
/* 124:    */   {
/* 125:142 */     return 15728880;
/* 126:    */   }
/* 127:    */   
/* 128:    */   public float getBrightness(float par1)
/* 129:    */   {
/* 130:150 */     return 1.0F;
/* 131:    */   }
/* 132:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.particle.EntityFireworkSparkFX
 * JD-Core Version:    0.7.0.1
 */