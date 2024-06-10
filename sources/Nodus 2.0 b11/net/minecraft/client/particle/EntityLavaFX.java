/*  1:   */ package net.minecraft.client.particle;
/*  2:   */ 
/*  3:   */ import java.util.Random;
/*  4:   */ import net.minecraft.client.renderer.Tessellator;
/*  5:   */ import net.minecraft.world.World;
/*  6:   */ 
/*  7:   */ public class EntityLavaFX
/*  8:   */   extends EntityFX
/*  9:   */ {
/* 10:   */   private float lavaParticleScale;
/* 11:   */   private static final String __OBFID = "CL_00000912";
/* 12:   */   
/* 13:   */   public EntityLavaFX(World par1World, double par2, double par4, double par6)
/* 14:   */   {
/* 15:13 */     super(par1World, par2, par4, par6, 0.0D, 0.0D, 0.0D);
/* 16:14 */     this.motionX *= 0.800000011920929D;
/* 17:15 */     this.motionY *= 0.800000011920929D;
/* 18:16 */     this.motionZ *= 0.800000011920929D;
/* 19:17 */     this.motionY = (this.rand.nextFloat() * 0.4F + 0.05F);
/* 20:18 */     this.particleRed = (this.particleGreen = this.particleBlue = 1.0F);
/* 21:19 */     this.particleScale *= (this.rand.nextFloat() * 2.0F + 0.2F);
/* 22:20 */     this.lavaParticleScale = this.particleScale;
/* 23:21 */     this.particleMaxAge = ((int)(16.0D / (Math.random() * 0.8D + 0.2D)));
/* 24:22 */     this.noClip = false;
/* 25:23 */     setParticleTextureIndex(49);
/* 26:   */   }
/* 27:   */   
/* 28:   */   public int getBrightnessForRender(float par1)
/* 29:   */   {
/* 30:28 */     float var2 = (this.particleAge + par1) / this.particleMaxAge;
/* 31:30 */     if (var2 < 0.0F) {
/* 32:32 */       var2 = 0.0F;
/* 33:   */     }
/* 34:35 */     if (var2 > 1.0F) {
/* 35:37 */       var2 = 1.0F;
/* 36:   */     }
/* 37:40 */     int var3 = super.getBrightnessForRender(par1);
/* 38:41 */     short var4 = 240;
/* 39:42 */     int var5 = var3 >> 16 & 0xFF;
/* 40:43 */     return var4 | var5 << 16;
/* 41:   */   }
/* 42:   */   
/* 43:   */   public float getBrightness(float par1)
/* 44:   */   {
/* 45:51 */     return 1.0F;
/* 46:   */   }
/* 47:   */   
/* 48:   */   public void renderParticle(Tessellator par1Tessellator, float par2, float par3, float par4, float par5, float par6, float par7)
/* 49:   */   {
/* 50:56 */     float var8 = (this.particleAge + par2) / this.particleMaxAge;
/* 51:57 */     this.particleScale = (this.lavaParticleScale * (1.0F - var8 * var8));
/* 52:58 */     super.renderParticle(par1Tessellator, par2, par3, par4, par5, par6, par7);
/* 53:   */   }
/* 54:   */   
/* 55:   */   public void onUpdate()
/* 56:   */   {
/* 57:66 */     this.prevPosX = this.posX;
/* 58:67 */     this.prevPosY = this.posY;
/* 59:68 */     this.prevPosZ = this.posZ;
/* 60:70 */     if (this.particleAge++ >= this.particleMaxAge) {
/* 61:72 */       setDead();
/* 62:   */     }
/* 63:75 */     float var1 = this.particleAge / this.particleMaxAge;
/* 64:77 */     if (this.rand.nextFloat() > var1) {
/* 65:79 */       this.worldObj.spawnParticle("smoke", this.posX, this.posY, this.posZ, this.motionX, this.motionY, this.motionZ);
/* 66:   */     }
/* 67:82 */     this.motionY -= 0.03D;
/* 68:83 */     moveEntity(this.motionX, this.motionY, this.motionZ);
/* 69:84 */     this.motionX *= 0.9990000128746033D;
/* 70:85 */     this.motionY *= 0.9990000128746033D;
/* 71:86 */     this.motionZ *= 0.9990000128746033D;
/* 72:88 */     if (this.onGround)
/* 73:   */     {
/* 74:90 */       this.motionX *= 0.699999988079071D;
/* 75:91 */       this.motionZ *= 0.699999988079071D;
/* 76:   */     }
/* 77:   */   }
/* 78:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.particle.EntityLavaFX
 * JD-Core Version:    0.7.0.1
 */