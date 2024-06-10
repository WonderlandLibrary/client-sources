/*  1:   */ package net.minecraft.client.particle;
/*  2:   */ 
/*  3:   */ import java.util.Random;
/*  4:   */ import net.minecraft.world.World;
/*  5:   */ 
/*  6:   */ public class EntityAuraFX
/*  7:   */   extends EntityFX
/*  8:   */ {
/*  9:   */   private static final String __OBFID = "CL_00000929";
/* 10:   */   
/* 11:   */   public EntityAuraFX(World par1World, double par2, double par4, double par6, double par8, double par10, double par12)
/* 12:   */   {
/* 13:11 */     super(par1World, par2, par4, par6, par8, par10, par12);
/* 14:12 */     float var14 = this.rand.nextFloat() * 0.1F + 0.2F;
/* 15:13 */     this.particleRed = var14;
/* 16:14 */     this.particleGreen = var14;
/* 17:15 */     this.particleBlue = var14;
/* 18:16 */     setParticleTextureIndex(0);
/* 19:17 */     setSize(0.02F, 0.02F);
/* 20:18 */     this.particleScale *= (this.rand.nextFloat() * 0.6F + 0.5F);
/* 21:19 */     this.motionX *= 0.01999999955296516D;
/* 22:20 */     this.motionY *= 0.01999999955296516D;
/* 23:21 */     this.motionZ *= 0.01999999955296516D;
/* 24:22 */     this.particleMaxAge = ((int)(20.0D / (Math.random() * 0.8D + 0.2D)));
/* 25:23 */     this.noClip = true;
/* 26:   */   }
/* 27:   */   
/* 28:   */   public void onUpdate()
/* 29:   */   {
/* 30:31 */     this.prevPosX = this.posX;
/* 31:32 */     this.prevPosY = this.posY;
/* 32:33 */     this.prevPosZ = this.posZ;
/* 33:34 */     moveEntity(this.motionX, this.motionY, this.motionZ);
/* 34:35 */     this.motionX *= 0.99D;
/* 35:36 */     this.motionY *= 0.99D;
/* 36:37 */     this.motionZ *= 0.99D;
/* 37:39 */     if (this.particleMaxAge-- <= 0) {
/* 38:41 */       setDead();
/* 39:   */     }
/* 40:   */   }
/* 41:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.particle.EntityAuraFX
 * JD-Core Version:    0.7.0.1
 */