/*  1:   */ package net.minecraft.client.particle;
/*  2:   */ 
/*  3:   */ import net.minecraft.client.renderer.Tessellator;
/*  4:   */ import net.minecraft.util.MathHelper;
/*  5:   */ import net.minecraft.world.World;
/*  6:   */ 
/*  7:   */ public class EntityNoteFX
/*  8:   */   extends EntityFX
/*  9:   */ {
/* 10:   */   float noteParticleScale;
/* 11:   */   private static final String __OBFID = "CL_00000913";
/* 12:   */   
/* 13:   */   public EntityNoteFX(World par1World, double par2, double par4, double par6, double par8, double par10, double par12)
/* 14:   */   {
/* 15:14 */     this(par1World, par2, par4, par6, par8, par10, par12, 2.0F);
/* 16:   */   }
/* 17:   */   
/* 18:   */   public EntityNoteFX(World par1World, double par2, double par4, double par6, double par8, double par10, double par12, float par14)
/* 19:   */   {
/* 20:19 */     super(par1World, par2, par4, par6, 0.0D, 0.0D, 0.0D);
/* 21:20 */     this.motionX *= 0.009999999776482582D;
/* 22:21 */     this.motionY *= 0.009999999776482582D;
/* 23:22 */     this.motionZ *= 0.009999999776482582D;
/* 24:23 */     this.motionY += 0.2D;
/* 25:24 */     this.particleRed = (MathHelper.sin(((float)par8 + 0.0F) * 3.141593F * 2.0F) * 0.65F + 0.35F);
/* 26:25 */     this.particleGreen = (MathHelper.sin(((float)par8 + 0.3333333F) * 3.141593F * 2.0F) * 0.65F + 0.35F);
/* 27:26 */     this.particleBlue = (MathHelper.sin(((float)par8 + 0.6666667F) * 3.141593F * 2.0F) * 0.65F + 0.35F);
/* 28:27 */     this.particleScale *= 0.75F;
/* 29:28 */     this.particleScale *= par14;
/* 30:29 */     this.noteParticleScale = this.particleScale;
/* 31:30 */     this.particleMaxAge = 6;
/* 32:31 */     this.noClip = false;
/* 33:32 */     setParticleTextureIndex(64);
/* 34:   */   }
/* 35:   */   
/* 36:   */   public void renderParticle(Tessellator par1Tessellator, float par2, float par3, float par4, float par5, float par6, float par7)
/* 37:   */   {
/* 38:37 */     float var8 = (this.particleAge + par2) / this.particleMaxAge * 32.0F;
/* 39:39 */     if (var8 < 0.0F) {
/* 40:41 */       var8 = 0.0F;
/* 41:   */     }
/* 42:44 */     if (var8 > 1.0F) {
/* 43:46 */       var8 = 1.0F;
/* 44:   */     }
/* 45:49 */     this.particleScale = (this.noteParticleScale * var8);
/* 46:50 */     super.renderParticle(par1Tessellator, par2, par3, par4, par5, par6, par7);
/* 47:   */   }
/* 48:   */   
/* 49:   */   public void onUpdate()
/* 50:   */   {
/* 51:58 */     this.prevPosX = this.posX;
/* 52:59 */     this.prevPosY = this.posY;
/* 53:60 */     this.prevPosZ = this.posZ;
/* 54:62 */     if (this.particleAge++ >= this.particleMaxAge) {
/* 55:64 */       setDead();
/* 56:   */     }
/* 57:67 */     moveEntity(this.motionX, this.motionY, this.motionZ);
/* 58:69 */     if (this.posY == this.prevPosY)
/* 59:   */     {
/* 60:71 */       this.motionX *= 1.1D;
/* 61:72 */       this.motionZ *= 1.1D;
/* 62:   */     }
/* 63:75 */     this.motionX *= 0.660000026226044D;
/* 64:76 */     this.motionY *= 0.660000026226044D;
/* 65:77 */     this.motionZ *= 0.660000026226044D;
/* 66:79 */     if (this.onGround)
/* 67:   */     {
/* 68:81 */       this.motionX *= 0.699999988079071D;
/* 69:82 */       this.motionZ *= 0.699999988079071D;
/* 70:   */     }
/* 71:   */   }
/* 72:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.particle.EntityNoteFX
 * JD-Core Version:    0.7.0.1
 */