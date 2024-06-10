/*  1:   */ package net.minecraft.client.particle;
/*  2:   */ 
/*  3:   */ import java.util.Random;
/*  4:   */ import net.minecraft.client.renderer.Tessellator;
/*  5:   */ import net.minecraft.world.World;
/*  6:   */ 
/*  7:   */ public class EntityPortalFX
/*  8:   */   extends EntityFX
/*  9:   */ {
/* 10:   */   private float portalParticleScale;
/* 11:   */   private double portalPosX;
/* 12:   */   private double portalPosY;
/* 13:   */   private double portalPosZ;
/* 14:   */   private static final String __OBFID = "CL_00000921";
/* 15:   */   
/* 16:   */   public EntityPortalFX(World par1World, double par2, double par4, double par6, double par8, double par10, double par12)
/* 17:   */   {
/* 18:16 */     super(par1World, par2, par4, par6, par8, par10, par12);
/* 19:17 */     this.motionX = par8;
/* 20:18 */     this.motionY = par10;
/* 21:19 */     this.motionZ = par12;
/* 22:20 */     this.portalPosX = (this.posX = par2);
/* 23:21 */     this.portalPosY = (this.posY = par4);
/* 24:22 */     this.portalPosZ = (this.posZ = par6);
/* 25:23 */     float var14 = this.rand.nextFloat() * 0.6F + 0.4F;
/* 26:24 */     this.portalParticleScale = (this.particleScale = this.rand.nextFloat() * 0.2F + 0.5F);
/* 27:25 */     this.particleRed = (this.particleGreen = this.particleBlue = 1.0F * var14);
/* 28:26 */     this.particleGreen *= 0.3F;
/* 29:27 */     this.particleRed *= 0.9F;
/* 30:28 */     this.particleMaxAge = ((int)(Math.random() * 10.0D) + 40);
/* 31:29 */     this.noClip = true;
/* 32:30 */     setParticleTextureIndex((int)(Math.random() * 8.0D));
/* 33:   */   }
/* 34:   */   
/* 35:   */   public void renderParticle(Tessellator par1Tessellator, float par2, float par3, float par4, float par5, float par6, float par7)
/* 36:   */   {
/* 37:35 */     float var8 = (this.particleAge + par2) / this.particleMaxAge;
/* 38:36 */     var8 = 1.0F - var8;
/* 39:37 */     var8 *= var8;
/* 40:38 */     var8 = 1.0F - var8;
/* 41:39 */     this.particleScale = (this.portalParticleScale * var8);
/* 42:40 */     super.renderParticle(par1Tessellator, par2, par3, par4, par5, par6, par7);
/* 43:   */   }
/* 44:   */   
/* 45:   */   public int getBrightnessForRender(float par1)
/* 46:   */   {
/* 47:45 */     int var2 = super.getBrightnessForRender(par1);
/* 48:46 */     float var3 = this.particleAge / this.particleMaxAge;
/* 49:47 */     var3 *= var3;
/* 50:48 */     var3 *= var3;
/* 51:49 */     int var4 = var2 & 0xFF;
/* 52:50 */     int var5 = var2 >> 16 & 0xFF;
/* 53:51 */     var5 += (int)(var3 * 15.0F * 16.0F);
/* 54:53 */     if (var5 > 240) {
/* 55:55 */       var5 = 240;
/* 56:   */     }
/* 57:58 */     return var4 | var5 << 16;
/* 58:   */   }
/* 59:   */   
/* 60:   */   public float getBrightness(float par1)
/* 61:   */   {
/* 62:66 */     float var2 = super.getBrightness(par1);
/* 63:67 */     float var3 = this.particleAge / this.particleMaxAge;
/* 64:68 */     var3 = var3 * var3 * var3 * var3;
/* 65:69 */     return var2 * (1.0F - var3) + var3;
/* 66:   */   }
/* 67:   */   
/* 68:   */   public void onUpdate()
/* 69:   */   {
/* 70:77 */     this.prevPosX = this.posX;
/* 71:78 */     this.prevPosY = this.posY;
/* 72:79 */     this.prevPosZ = this.posZ;
/* 73:80 */     float var1 = this.particleAge / this.particleMaxAge;
/* 74:81 */     float var2 = var1;
/* 75:82 */     var1 = -var1 + var1 * var1 * 2.0F;
/* 76:83 */     var1 = 1.0F - var1;
/* 77:84 */     this.posX = (this.portalPosX + this.motionX * var1);
/* 78:85 */     this.posY = (this.portalPosY + this.motionY * var1 + (1.0F - var2));
/* 79:86 */     this.posZ = (this.portalPosZ + this.motionZ * var1);
/* 80:88 */     if (this.particleAge++ >= this.particleMaxAge) {
/* 81:90 */       setDead();
/* 82:   */     }
/* 83:   */   }
/* 84:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.particle.EntityPortalFX
 * JD-Core Version:    0.7.0.1
 */