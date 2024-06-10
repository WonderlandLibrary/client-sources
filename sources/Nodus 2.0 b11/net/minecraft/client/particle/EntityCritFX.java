/*  1:   */ package net.minecraft.client.particle;
/*  2:   */ 
/*  3:   */ import net.minecraft.client.renderer.Tessellator;
/*  4:   */ import net.minecraft.world.World;
/*  5:   */ 
/*  6:   */ public class EntityCritFX
/*  7:   */   extends EntityFX
/*  8:   */ {
/*  9:   */   float initialParticleScale;
/* 10:   */   private static final String __OBFID = "CL_00000900";
/* 11:   */   
/* 12:   */   public EntityCritFX(World par1World, double par2, double par4, double par6, double par8, double par10, double par12)
/* 13:   */   {
/* 14:13 */     this(par1World, par2, par4, par6, par8, par10, par12, 1.0F);
/* 15:   */   }
/* 16:   */   
/* 17:   */   public EntityCritFX(World par1World, double par2, double par4, double par6, double par8, double par10, double par12, float par14)
/* 18:   */   {
/* 19:18 */     super(par1World, par2, par4, par6, 0.0D, 0.0D, 0.0D);
/* 20:19 */     this.motionX *= 0.1000000014901161D;
/* 21:20 */     this.motionY *= 0.1000000014901161D;
/* 22:21 */     this.motionZ *= 0.1000000014901161D;
/* 23:22 */     this.motionX += par8 * 0.4D;
/* 24:23 */     this.motionY += par10 * 0.4D;
/* 25:24 */     this.motionZ += par12 * 0.4D;
/* 26:25 */     this.particleRed = (this.particleGreen = this.particleBlue = (float)(Math.random() * 0.300000011920929D + 0.6000000238418579D));
/* 27:26 */     this.particleScale *= 0.75F;
/* 28:27 */     this.particleScale *= par14;
/* 29:28 */     this.initialParticleScale = this.particleScale;
/* 30:29 */     this.particleMaxAge = ((int)(6.0D / (Math.random() * 0.8D + 0.6D)));
/* 31:30 */     this.particleMaxAge = ((int)(this.particleMaxAge * par14));
/* 32:31 */     this.noClip = false;
/* 33:32 */     setParticleTextureIndex(65);
/* 34:33 */     onUpdate();
/* 35:   */   }
/* 36:   */   
/* 37:   */   public void renderParticle(Tessellator par1Tessellator, float par2, float par3, float par4, float par5, float par6, float par7)
/* 38:   */   {
/* 39:38 */     float var8 = (this.particleAge + par2) / this.particleMaxAge * 32.0F;
/* 40:40 */     if (var8 < 0.0F) {
/* 41:42 */       var8 = 0.0F;
/* 42:   */     }
/* 43:45 */     if (var8 > 1.0F) {
/* 44:47 */       var8 = 1.0F;
/* 45:   */     }
/* 46:50 */     this.particleScale = (this.initialParticleScale * var8);
/* 47:51 */     super.renderParticle(par1Tessellator, par2, par3, par4, par5, par6, par7);
/* 48:   */   }
/* 49:   */   
/* 50:   */   public void onUpdate()
/* 51:   */   {
/* 52:59 */     this.prevPosX = this.posX;
/* 53:60 */     this.prevPosY = this.posY;
/* 54:61 */     this.prevPosZ = this.posZ;
/* 55:63 */     if (this.particleAge++ >= this.particleMaxAge) {
/* 56:65 */       setDead();
/* 57:   */     }
/* 58:68 */     moveEntity(this.motionX, this.motionY, this.motionZ);
/* 59:69 */     this.particleGreen = ((float)(this.particleGreen * 0.96D));
/* 60:70 */     this.particleBlue = ((float)(this.particleBlue * 0.9D));
/* 61:71 */     this.motionX *= 0.699999988079071D;
/* 62:72 */     this.motionY *= 0.699999988079071D;
/* 63:73 */     this.motionZ *= 0.699999988079071D;
/* 64:74 */     this.motionY -= 0.01999999955296516D;
/* 65:76 */     if (this.onGround)
/* 66:   */     {
/* 67:78 */       this.motionX *= 0.699999988079071D;
/* 68:79 */       this.motionZ *= 0.699999988079071D;
/* 69:   */     }
/* 70:   */   }
/* 71:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.particle.EntityCritFX
 * JD-Core Version:    0.7.0.1
 */