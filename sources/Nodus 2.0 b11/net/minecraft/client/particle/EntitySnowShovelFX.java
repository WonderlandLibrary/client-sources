/*  1:   */ package net.minecraft.client.particle;
/*  2:   */ 
/*  3:   */ import net.minecraft.client.renderer.Tessellator;
/*  4:   */ import net.minecraft.world.World;
/*  5:   */ 
/*  6:   */ public class EntitySnowShovelFX
/*  7:   */   extends EntityFX
/*  8:   */ {
/*  9:   */   float snowDigParticleScale;
/* 10:   */   private static final String __OBFID = "CL_00000925";
/* 11:   */   
/* 12:   */   public EntitySnowShovelFX(World par1World, double par2, double par4, double par6, double par8, double par10, double par12)
/* 13:   */   {
/* 14:13 */     this(par1World, par2, par4, par6, par8, par10, par12, 1.0F);
/* 15:   */   }
/* 16:   */   
/* 17:   */   public EntitySnowShovelFX(World par1World, double par2, double par4, double par6, double par8, double par10, double par12, float par14)
/* 18:   */   {
/* 19:18 */     super(par1World, par2, par4, par6, par8, par10, par12);
/* 20:19 */     this.motionX *= 0.1000000014901161D;
/* 21:20 */     this.motionY *= 0.1000000014901161D;
/* 22:21 */     this.motionZ *= 0.1000000014901161D;
/* 23:22 */     this.motionX += par8;
/* 24:23 */     this.motionY += par10;
/* 25:24 */     this.motionZ += par12;
/* 26:25 */     this.particleRed = (this.particleGreen = this.particleBlue = 1.0F - (float)(Math.random() * 0.300000011920929D));
/* 27:26 */     this.particleScale *= 0.75F;
/* 28:27 */     this.particleScale *= par14;
/* 29:28 */     this.snowDigParticleScale = this.particleScale;
/* 30:29 */     this.particleMaxAge = ((int)(8.0D / (Math.random() * 0.8D + 0.2D)));
/* 31:30 */     this.particleMaxAge = ((int)(this.particleMaxAge * par14));
/* 32:31 */     this.noClip = false;
/* 33:   */   }
/* 34:   */   
/* 35:   */   public void renderParticle(Tessellator par1Tessellator, float par2, float par3, float par4, float par5, float par6, float par7)
/* 36:   */   {
/* 37:36 */     float var8 = (this.particleAge + par2) / this.particleMaxAge * 32.0F;
/* 38:38 */     if (var8 < 0.0F) {
/* 39:40 */       var8 = 0.0F;
/* 40:   */     }
/* 41:43 */     if (var8 > 1.0F) {
/* 42:45 */       var8 = 1.0F;
/* 43:   */     }
/* 44:48 */     this.particleScale = (this.snowDigParticleScale * var8);
/* 45:49 */     super.renderParticle(par1Tessellator, par2, par3, par4, par5, par6, par7);
/* 46:   */   }
/* 47:   */   
/* 48:   */   public void onUpdate()
/* 49:   */   {
/* 50:57 */     this.prevPosX = this.posX;
/* 51:58 */     this.prevPosY = this.posY;
/* 52:59 */     this.prevPosZ = this.posZ;
/* 53:61 */     if (this.particleAge++ >= this.particleMaxAge) {
/* 54:63 */       setDead();
/* 55:   */     }
/* 56:66 */     setParticleTextureIndex(7 - this.particleAge * 8 / this.particleMaxAge);
/* 57:67 */     this.motionY -= 0.03D;
/* 58:68 */     moveEntity(this.motionX, this.motionY, this.motionZ);
/* 59:69 */     this.motionX *= 0.9900000095367432D;
/* 60:70 */     this.motionY *= 0.9900000095367432D;
/* 61:71 */     this.motionZ *= 0.9900000095367432D;
/* 62:73 */     if (this.onGround)
/* 63:   */     {
/* 64:75 */       this.motionX *= 0.699999988079071D;
/* 65:76 */       this.motionZ *= 0.699999988079071D;
/* 66:   */     }
/* 67:   */   }
/* 68:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.particle.EntitySnowShovelFX
 * JD-Core Version:    0.7.0.1
 */