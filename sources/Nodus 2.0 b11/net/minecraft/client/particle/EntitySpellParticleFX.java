/*  1:   */ package net.minecraft.client.particle;
/*  2:   */ 
/*  3:   */ import net.minecraft.client.renderer.Tessellator;
/*  4:   */ import net.minecraft.world.World;
/*  5:   */ 
/*  6:   */ public class EntitySpellParticleFX
/*  7:   */   extends EntityFX
/*  8:   */ {
/*  9: 9 */   private int baseSpellTextureIndex = 128;
/* 10:   */   private static final String __OBFID = "CL_00000926";
/* 11:   */   
/* 12:   */   public EntitySpellParticleFX(World par1World, double par2, double par4, double par6, double par8, double par10, double par12)
/* 13:   */   {
/* 14:14 */     super(par1World, par2, par4, par6, par8, par10, par12);
/* 15:15 */     this.motionY *= 0.2000000029802322D;
/* 16:17 */     if ((par8 == 0.0D) && (par12 == 0.0D))
/* 17:   */     {
/* 18:19 */       this.motionX *= 0.1000000014901161D;
/* 19:20 */       this.motionZ *= 0.1000000014901161D;
/* 20:   */     }
/* 21:23 */     this.particleScale *= 0.75F;
/* 22:24 */     this.particleMaxAge = ((int)(8.0D / (Math.random() * 0.8D + 0.2D)));
/* 23:25 */     this.noClip = false;
/* 24:   */   }
/* 25:   */   
/* 26:   */   public void renderParticle(Tessellator par1Tessellator, float par2, float par3, float par4, float par5, float par6, float par7)
/* 27:   */   {
/* 28:30 */     float var8 = (this.particleAge + par2) / this.particleMaxAge * 32.0F;
/* 29:32 */     if (var8 < 0.0F) {
/* 30:34 */       var8 = 0.0F;
/* 31:   */     }
/* 32:37 */     if (var8 > 1.0F) {
/* 33:39 */       var8 = 1.0F;
/* 34:   */     }
/* 35:42 */     super.renderParticle(par1Tessellator, par2, par3, par4, par5, par6, par7);
/* 36:   */   }
/* 37:   */   
/* 38:   */   public void onUpdate()
/* 39:   */   {
/* 40:50 */     this.prevPosX = this.posX;
/* 41:51 */     this.prevPosY = this.posY;
/* 42:52 */     this.prevPosZ = this.posZ;
/* 43:54 */     if (this.particleAge++ >= this.particleMaxAge) {
/* 44:56 */       setDead();
/* 45:   */     }
/* 46:59 */     setParticleTextureIndex(this.baseSpellTextureIndex + (7 - this.particleAge * 8 / this.particleMaxAge));
/* 47:60 */     this.motionY += 0.004D;
/* 48:61 */     moveEntity(this.motionX, this.motionY, this.motionZ);
/* 49:63 */     if (this.posY == this.prevPosY)
/* 50:   */     {
/* 51:65 */       this.motionX *= 1.1D;
/* 52:66 */       this.motionZ *= 1.1D;
/* 53:   */     }
/* 54:69 */     this.motionX *= 0.9599999785423279D;
/* 55:70 */     this.motionY *= 0.9599999785423279D;
/* 56:71 */     this.motionZ *= 0.9599999785423279D;
/* 57:73 */     if (this.onGround)
/* 58:   */     {
/* 59:75 */       this.motionX *= 0.699999988079071D;
/* 60:76 */       this.motionZ *= 0.699999988079071D;
/* 61:   */     }
/* 62:   */   }
/* 63:   */   
/* 64:   */   public void setBaseSpellTextureIndex(int par1)
/* 65:   */   {
/* 66:85 */     this.baseSpellTextureIndex = par1;
/* 67:   */   }
/* 68:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.particle.EntitySpellParticleFX
 * JD-Core Version:    0.7.0.1
 */