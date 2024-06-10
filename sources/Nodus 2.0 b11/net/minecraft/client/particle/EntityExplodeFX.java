/*  1:   */ package net.minecraft.client.particle;
/*  2:   */ 
/*  3:   */ import java.util.Random;
/*  4:   */ import net.minecraft.world.World;
/*  5:   */ 
/*  6:   */ public class EntityExplodeFX
/*  7:   */   extends EntityFX
/*  8:   */ {
/*  9:   */   private static final String __OBFID = "CL_00000903";
/* 10:   */   
/* 11:   */   public EntityExplodeFX(World par1World, double par2, double par4, double par6, double par8, double par10, double par12)
/* 12:   */   {
/* 13:11 */     super(par1World, par2, par4, par6, par8, par10, par12);
/* 14:12 */     this.motionX = (par8 + (float)(Math.random() * 2.0D - 1.0D) * 0.05F);
/* 15:13 */     this.motionY = (par10 + (float)(Math.random() * 2.0D - 1.0D) * 0.05F);
/* 16:14 */     this.motionZ = (par12 + (float)(Math.random() * 2.0D - 1.0D) * 0.05F);
/* 17:15 */     this.particleRed = (this.particleGreen = this.particleBlue = this.rand.nextFloat() * 0.3F + 0.7F);
/* 18:16 */     this.particleScale = (this.rand.nextFloat() * this.rand.nextFloat() * 6.0F + 1.0F);
/* 19:17 */     this.particleMaxAge = ((int)(16.0D / (this.rand.nextFloat() * 0.8D + 0.2D)) + 2);
/* 20:   */   }
/* 21:   */   
/* 22:   */   public void onUpdate()
/* 23:   */   {
/* 24:25 */     this.prevPosX = this.posX;
/* 25:26 */     this.prevPosY = this.posY;
/* 26:27 */     this.prevPosZ = this.posZ;
/* 27:29 */     if (this.particleAge++ >= this.particleMaxAge) {
/* 28:31 */       setDead();
/* 29:   */     }
/* 30:34 */     setParticleTextureIndex(7 - this.particleAge * 8 / this.particleMaxAge);
/* 31:35 */     this.motionY += 0.004D;
/* 32:36 */     moveEntity(this.motionX, this.motionY, this.motionZ);
/* 33:37 */     this.motionX *= 0.8999999761581421D;
/* 34:38 */     this.motionY *= 0.8999999761581421D;
/* 35:39 */     this.motionZ *= 0.8999999761581421D;
/* 36:41 */     if (this.onGround)
/* 37:   */     {
/* 38:43 */       this.motionX *= 0.699999988079071D;
/* 39:44 */       this.motionZ *= 0.699999988079071D;
/* 40:   */     }
/* 41:   */   }
/* 42:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.particle.EntityExplodeFX
 * JD-Core Version:    0.7.0.1
 */