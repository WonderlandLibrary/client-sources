/*   1:    */ package net.minecraft.client.particle;
/*   2:    */ 
/*   3:    */ import java.util.Random;
/*   4:    */ import net.minecraft.client.renderer.Tessellator;
/*   5:    */ import net.minecraft.world.World;
/*   6:    */ 
/*   7:    */ public class EntityFlameFX
/*   8:    */   extends EntityFX
/*   9:    */ {
/*  10:    */   private float flameScale;
/*  11:    */   private static final String __OBFID = "CL_00000907";
/*  12:    */   
/*  13:    */   public EntityFlameFX(World par1World, double par2, double par4, double par6, double par8, double par10, double par12)
/*  14:    */   {
/*  15: 14 */     super(par1World, par2, par4, par6, par8, par10, par12);
/*  16: 15 */     this.motionX = (this.motionX * 0.009999999776482582D + par8);
/*  17: 16 */     this.motionY = (this.motionY * 0.009999999776482582D + par10);
/*  18: 17 */     this.motionZ = (this.motionZ * 0.009999999776482582D + par12);
/*  19: 18 */     double var10000 = par2 + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.05F;
/*  20: 19 */     var10000 = par4 + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.05F;
/*  21: 20 */     var10000 = par6 + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.05F;
/*  22: 21 */     this.flameScale = this.particleScale;
/*  23: 22 */     this.particleRed = (this.particleGreen = this.particleBlue = 1.0F);
/*  24: 23 */     this.particleMaxAge = ((int)(8.0D / (Math.random() * 0.8D + 0.2D)) + 4);
/*  25: 24 */     this.noClip = true;
/*  26: 25 */     setParticleTextureIndex(48);
/*  27:    */   }
/*  28:    */   
/*  29:    */   public void renderParticle(Tessellator par1Tessellator, float par2, float par3, float par4, float par5, float par6, float par7)
/*  30:    */   {
/*  31: 30 */     float var8 = (this.particleAge + par2) / this.particleMaxAge;
/*  32: 31 */     this.particleScale = (this.flameScale * (1.0F - var8 * var8 * 0.5F));
/*  33: 32 */     super.renderParticle(par1Tessellator, par2, par3, par4, par5, par6, par7);
/*  34:    */   }
/*  35:    */   
/*  36:    */   public int getBrightnessForRender(float par1)
/*  37:    */   {
/*  38: 37 */     float var2 = (this.particleAge + par1) / this.particleMaxAge;
/*  39: 39 */     if (var2 < 0.0F) {
/*  40: 41 */       var2 = 0.0F;
/*  41:    */     }
/*  42: 44 */     if (var2 > 1.0F) {
/*  43: 46 */       var2 = 1.0F;
/*  44:    */     }
/*  45: 49 */     int var3 = super.getBrightnessForRender(par1);
/*  46: 50 */     int var4 = var3 & 0xFF;
/*  47: 51 */     int var5 = var3 >> 16 & 0xFF;
/*  48: 52 */     var4 += (int)(var2 * 15.0F * 16.0F);
/*  49: 54 */     if (var4 > 240) {
/*  50: 56 */       var4 = 240;
/*  51:    */     }
/*  52: 59 */     return var4 | var5 << 16;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public float getBrightness(float par1)
/*  56:    */   {
/*  57: 67 */     float var2 = (this.particleAge + par1) / this.particleMaxAge;
/*  58: 69 */     if (var2 < 0.0F) {
/*  59: 71 */       var2 = 0.0F;
/*  60:    */     }
/*  61: 74 */     if (var2 > 1.0F) {
/*  62: 76 */       var2 = 1.0F;
/*  63:    */     }
/*  64: 79 */     float var3 = super.getBrightness(par1);
/*  65: 80 */     return var3 * var2 + (1.0F - var2);
/*  66:    */   }
/*  67:    */   
/*  68:    */   public void onUpdate()
/*  69:    */   {
/*  70: 88 */     this.prevPosX = this.posX;
/*  71: 89 */     this.prevPosY = this.posY;
/*  72: 90 */     this.prevPosZ = this.posZ;
/*  73: 92 */     if (this.particleAge++ >= this.particleMaxAge) {
/*  74: 94 */       setDead();
/*  75:    */     }
/*  76: 97 */     moveEntity(this.motionX, this.motionY, this.motionZ);
/*  77: 98 */     this.motionX *= 0.9599999785423279D;
/*  78: 99 */     this.motionY *= 0.9599999785423279D;
/*  79:100 */     this.motionZ *= 0.9599999785423279D;
/*  80:102 */     if (this.onGround)
/*  81:    */     {
/*  82:104 */       this.motionX *= 0.699999988079071D;
/*  83:105 */       this.motionZ *= 0.699999988079071D;
/*  84:    */     }
/*  85:    */   }
/*  86:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.particle.EntityFlameFX
 * JD-Core Version:    0.7.0.1
 */