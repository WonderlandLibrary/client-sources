/*  1:   */ package net.minecraft.client.particle;
/*  2:   */ 
/*  3:   */ import net.minecraft.client.renderer.Tessellator;
/*  4:   */ import net.minecraft.world.World;
/*  5:   */ 
/*  6:   */ public class EntityReddustFX
/*  7:   */   extends EntityFX
/*  8:   */ {
/*  9:   */   float reddustParticleScale;
/* 10:   */   private static final String __OBFID = "CL_00000923";
/* 11:   */   
/* 12:   */   public EntityReddustFX(World par1World, double par2, double par4, double par6, float par8, float par9, float par10)
/* 13:   */   {
/* 14:13 */     this(par1World, par2, par4, par6, 1.0F, par8, par9, par10);
/* 15:   */   }
/* 16:   */   
/* 17:   */   public EntityReddustFX(World par1World, double par2, double par4, double par6, float par8, float par9, float par10, float par11)
/* 18:   */   {
/* 19:18 */     super(par1World, par2, par4, par6, 0.0D, 0.0D, 0.0D);
/* 20:19 */     this.motionX *= 0.1000000014901161D;
/* 21:20 */     this.motionY *= 0.1000000014901161D;
/* 22:21 */     this.motionZ *= 0.1000000014901161D;
/* 23:23 */     if (par9 == 0.0F) {
/* 24:25 */       par9 = 1.0F;
/* 25:   */     }
/* 26:28 */     float var12 = (float)Math.random() * 0.4F + 0.6F;
/* 27:29 */     this.particleRed = (((float)(Math.random() * 0.2000000029802322D) + 0.8F) * par9 * var12);
/* 28:30 */     this.particleGreen = (((float)(Math.random() * 0.2000000029802322D) + 0.8F) * par10 * var12);
/* 29:31 */     this.particleBlue = (((float)(Math.random() * 0.2000000029802322D) + 0.8F) * par11 * var12);
/* 30:32 */     this.particleScale *= 0.75F;
/* 31:33 */     this.particleScale *= par8;
/* 32:34 */     this.reddustParticleScale = this.particleScale;
/* 33:35 */     this.particleMaxAge = ((int)(8.0D / (Math.random() * 0.8D + 0.2D)));
/* 34:36 */     this.particleMaxAge = ((int)(this.particleMaxAge * par8));
/* 35:37 */     this.noClip = false;
/* 36:   */   }
/* 37:   */   
/* 38:   */   public void renderParticle(Tessellator par1Tessellator, float par2, float par3, float par4, float par5, float par6, float par7)
/* 39:   */   {
/* 40:42 */     float var8 = (this.particleAge + par2) / this.particleMaxAge * 32.0F;
/* 41:44 */     if (var8 < 0.0F) {
/* 42:46 */       var8 = 0.0F;
/* 43:   */     }
/* 44:49 */     if (var8 > 1.0F) {
/* 45:51 */       var8 = 1.0F;
/* 46:   */     }
/* 47:54 */     this.particleScale = (this.reddustParticleScale * var8);
/* 48:55 */     super.renderParticle(par1Tessellator, par2, par3, par4, par5, par6, par7);
/* 49:   */   }
/* 50:   */   
/* 51:   */   public void onUpdate()
/* 52:   */   {
/* 53:63 */     this.prevPosX = this.posX;
/* 54:64 */     this.prevPosY = this.posY;
/* 55:65 */     this.prevPosZ = this.posZ;
/* 56:67 */     if (this.particleAge++ >= this.particleMaxAge) {
/* 57:69 */       setDead();
/* 58:   */     }
/* 59:72 */     setParticleTextureIndex(7 - this.particleAge * 8 / this.particleMaxAge);
/* 60:73 */     moveEntity(this.motionX, this.motionY, this.motionZ);
/* 61:75 */     if (this.posY == this.prevPosY)
/* 62:   */     {
/* 63:77 */       this.motionX *= 1.1D;
/* 64:78 */       this.motionZ *= 1.1D;
/* 65:   */     }
/* 66:81 */     this.motionX *= 0.9599999785423279D;
/* 67:82 */     this.motionY *= 0.9599999785423279D;
/* 68:83 */     this.motionZ *= 0.9599999785423279D;
/* 69:85 */     if (this.onGround)
/* 70:   */     {
/* 71:87 */       this.motionX *= 0.699999988079071D;
/* 72:88 */       this.motionZ *= 0.699999988079071D;
/* 73:   */     }
/* 74:   */   }
/* 75:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.particle.EntityReddustFX
 * JD-Core Version:    0.7.0.1
 */