/*  1:   */ package net.minecraft.client.particle;
/*  2:   */ 
/*  3:   */ import net.minecraft.client.renderer.Tessellator;
/*  4:   */ import net.minecraft.entity.player.EntityPlayer;
/*  5:   */ import net.minecraft.util.AxisAlignedBB;
/*  6:   */ import net.minecraft.world.World;
/*  7:   */ 
/*  8:   */ public class EntityCloudFX
/*  9:   */   extends EntityFX
/* 10:   */ {
/* 11:   */   float field_70569_a;
/* 12:   */   private static final String __OBFID = "CL_00000920";
/* 13:   */   
/* 14:   */   public EntityCloudFX(World par1World, double par2, double par4, double par6, double par8, double par10, double par12)
/* 15:   */   {
/* 16:14 */     super(par1World, par2, par4, par6, 0.0D, 0.0D, 0.0D);
/* 17:15 */     float var14 = 2.5F;
/* 18:16 */     this.motionX *= 0.1000000014901161D;
/* 19:17 */     this.motionY *= 0.1000000014901161D;
/* 20:18 */     this.motionZ *= 0.1000000014901161D;
/* 21:19 */     this.motionX += par8;
/* 22:20 */     this.motionY += par10;
/* 23:21 */     this.motionZ += par12;
/* 24:22 */     this.particleRed = (this.particleGreen = this.particleBlue = 1.0F - (float)(Math.random() * 0.300000011920929D));
/* 25:23 */     this.particleScale *= 0.75F;
/* 26:24 */     this.particleScale *= var14;
/* 27:25 */     this.field_70569_a = this.particleScale;
/* 28:26 */     this.particleMaxAge = ((int)(8.0D / (Math.random() * 0.8D + 0.3D)));
/* 29:27 */     this.particleMaxAge = ((int)(this.particleMaxAge * var14));
/* 30:28 */     this.noClip = false;
/* 31:   */   }
/* 32:   */   
/* 33:   */   public void renderParticle(Tessellator par1Tessellator, float par2, float par3, float par4, float par5, float par6, float par7)
/* 34:   */   {
/* 35:33 */     float var8 = (this.particleAge + par2) / this.particleMaxAge * 32.0F;
/* 36:35 */     if (var8 < 0.0F) {
/* 37:37 */       var8 = 0.0F;
/* 38:   */     }
/* 39:40 */     if (var8 > 1.0F) {
/* 40:42 */       var8 = 1.0F;
/* 41:   */     }
/* 42:45 */     this.particleScale = (this.field_70569_a * var8);
/* 43:46 */     super.renderParticle(par1Tessellator, par2, par3, par4, par5, par6, par7);
/* 44:   */   }
/* 45:   */   
/* 46:   */   public void onUpdate()
/* 47:   */   {
/* 48:54 */     this.prevPosX = this.posX;
/* 49:55 */     this.prevPosY = this.posY;
/* 50:56 */     this.prevPosZ = this.posZ;
/* 51:58 */     if (this.particleAge++ >= this.particleMaxAge) {
/* 52:60 */       setDead();
/* 53:   */     }
/* 54:63 */     setParticleTextureIndex(7 - this.particleAge * 8 / this.particleMaxAge);
/* 55:64 */     moveEntity(this.motionX, this.motionY, this.motionZ);
/* 56:65 */     this.motionX *= 0.9599999785423279D;
/* 57:66 */     this.motionY *= 0.9599999785423279D;
/* 58:67 */     this.motionZ *= 0.9599999785423279D;
/* 59:68 */     EntityPlayer var1 = this.worldObj.getClosestPlayerToEntity(this, 2.0D);
/* 60:70 */     if ((var1 != null) && (this.posY > var1.boundingBox.minY))
/* 61:   */     {
/* 62:72 */       this.posY += (var1.boundingBox.minY - this.posY) * 0.2D;
/* 63:73 */       this.motionY += (var1.motionY - this.motionY) * 0.2D;
/* 64:74 */       setPosition(this.posX, this.posY, this.posZ);
/* 65:   */     }
/* 66:77 */     if (this.onGround)
/* 67:   */     {
/* 68:79 */       this.motionX *= 0.699999988079071D;
/* 69:80 */       this.motionZ *= 0.699999988079071D;
/* 70:   */     }
/* 71:   */   }
/* 72:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.particle.EntityCloudFX
 * JD-Core Version:    0.7.0.1
 */