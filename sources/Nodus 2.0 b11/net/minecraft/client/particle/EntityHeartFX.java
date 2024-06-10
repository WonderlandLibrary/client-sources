/*  1:   */ package net.minecraft.client.particle;
/*  2:   */ 
/*  3:   */ import net.minecraft.client.renderer.Tessellator;
/*  4:   */ import net.minecraft.world.World;
/*  5:   */ 
/*  6:   */ public class EntityHeartFX
/*  7:   */   extends EntityFX
/*  8:   */ {
/*  9:   */   float particleScaleOverTime;
/* 10:   */   private static final String __OBFID = "CL_00000909";
/* 11:   */   
/* 12:   */   public EntityHeartFX(World par1World, double par2, double par4, double par6, double par8, double par10, double par12)
/* 13:   */   {
/* 14:13 */     this(par1World, par2, par4, par6, par8, par10, par12, 2.0F);
/* 15:   */   }
/* 16:   */   
/* 17:   */   public EntityHeartFX(World par1World, double par2, double par4, double par6, double par8, double par10, double par12, float par14)
/* 18:   */   {
/* 19:18 */     super(par1World, par2, par4, par6, 0.0D, 0.0D, 0.0D);
/* 20:19 */     this.motionX *= 0.009999999776482582D;
/* 21:20 */     this.motionY *= 0.009999999776482582D;
/* 22:21 */     this.motionZ *= 0.009999999776482582D;
/* 23:22 */     this.motionY += 0.1D;
/* 24:23 */     this.particleScale *= 0.75F;
/* 25:24 */     this.particleScale *= par14;
/* 26:25 */     this.particleScaleOverTime = this.particleScale;
/* 27:26 */     this.particleMaxAge = 16;
/* 28:27 */     this.noClip = false;
/* 29:28 */     setParticleTextureIndex(80);
/* 30:   */   }
/* 31:   */   
/* 32:   */   public void renderParticle(Tessellator par1Tessellator, float par2, float par3, float par4, float par5, float par6, float par7)
/* 33:   */   {
/* 34:33 */     float var8 = (this.particleAge + par2) / this.particleMaxAge * 32.0F;
/* 35:35 */     if (var8 < 0.0F) {
/* 36:37 */       var8 = 0.0F;
/* 37:   */     }
/* 38:40 */     if (var8 > 1.0F) {
/* 39:42 */       var8 = 1.0F;
/* 40:   */     }
/* 41:45 */     this.particleScale = (this.particleScaleOverTime * var8);
/* 42:46 */     super.renderParticle(par1Tessellator, par2, par3, par4, par5, par6, par7);
/* 43:   */   }
/* 44:   */   
/* 45:   */   public void onUpdate()
/* 46:   */   {
/* 47:54 */     this.prevPosX = this.posX;
/* 48:55 */     this.prevPosY = this.posY;
/* 49:56 */     this.prevPosZ = this.posZ;
/* 50:58 */     if (this.particleAge++ >= this.particleMaxAge) {
/* 51:60 */       setDead();
/* 52:   */     }
/* 53:63 */     moveEntity(this.motionX, this.motionY, this.motionZ);
/* 54:65 */     if (this.posY == this.prevPosY)
/* 55:   */     {
/* 56:67 */       this.motionX *= 1.1D;
/* 57:68 */       this.motionZ *= 1.1D;
/* 58:   */     }
/* 59:71 */     this.motionX *= 0.8600000143051148D;
/* 60:72 */     this.motionY *= 0.8600000143051148D;
/* 61:73 */     this.motionZ *= 0.8600000143051148D;
/* 62:75 */     if (this.onGround)
/* 63:   */     {
/* 64:77 */       this.motionX *= 0.699999988079071D;
/* 65:78 */       this.motionZ *= 0.699999988079071D;
/* 66:   */     }
/* 67:   */   }
/* 68:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.particle.EntityHeartFX
 * JD-Core Version:    0.7.0.1
 */