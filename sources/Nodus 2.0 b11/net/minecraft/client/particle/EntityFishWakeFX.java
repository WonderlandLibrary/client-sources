/*  1:   */ package net.minecraft.client.particle;
/*  2:   */ 
/*  3:   */ import net.minecraft.world.World;
/*  4:   */ 
/*  5:   */ public class EntityFishWakeFX
/*  6:   */   extends EntityFX
/*  7:   */ {
/*  8:   */   private static final String __OBFID = "CL_00000933";
/*  9:   */   
/* 10:   */   public EntityFishWakeFX(World p_i45073_1_, double p_i45073_2_, double p_i45073_4_, double p_i45073_6_, double p_i45073_8_, double p_i45073_10_, double p_i45073_12_)
/* 11:   */   {
/* 12:11 */     super(p_i45073_1_, p_i45073_2_, p_i45073_4_, p_i45073_6_, 0.0D, 0.0D, 0.0D);
/* 13:12 */     this.motionX *= 0.300000011920929D;
/* 14:13 */     this.motionY = ((float)Math.random() * 0.2F + 0.1F);
/* 15:14 */     this.motionZ *= 0.300000011920929D;
/* 16:15 */     this.particleRed = 1.0F;
/* 17:16 */     this.particleGreen = 1.0F;
/* 18:17 */     this.particleBlue = 1.0F;
/* 19:18 */     setParticleTextureIndex(19);
/* 20:19 */     setSize(0.01F, 0.01F);
/* 21:20 */     this.particleMaxAge = ((int)(8.0D / (Math.random() * 0.8D + 0.2D)));
/* 22:21 */     this.particleGravity = 0.0F;
/* 23:22 */     this.motionX = p_i45073_8_;
/* 24:23 */     this.motionY = p_i45073_10_;
/* 25:24 */     this.motionZ = p_i45073_12_;
/* 26:   */   }
/* 27:   */   
/* 28:   */   public void onUpdate()
/* 29:   */   {
/* 30:32 */     this.prevPosX = this.posX;
/* 31:33 */     this.prevPosY = this.posY;
/* 32:34 */     this.prevPosZ = this.posZ;
/* 33:35 */     this.motionY -= this.particleGravity;
/* 34:36 */     moveEntity(this.motionX, this.motionY, this.motionZ);
/* 35:37 */     this.motionX *= 0.9800000190734863D;
/* 36:38 */     this.motionY *= 0.9800000190734863D;
/* 37:39 */     this.motionZ *= 0.9800000190734863D;
/* 38:40 */     int var1 = 60 - this.particleMaxAge;
/* 39:41 */     float var2 = var1 * 0.001F;
/* 40:42 */     setSize(var2, var2);
/* 41:43 */     setParticleTextureIndex(19 + var1 % 4);
/* 42:45 */     if (this.particleMaxAge-- <= 0) {
/* 43:47 */       setDead();
/* 44:   */     }
/* 45:   */   }
/* 46:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.particle.EntityFishWakeFX
 * JD-Core Version:    0.7.0.1
 */