/*  1:   */ package net.minecraft.client.particle;
/*  2:   */ 
/*  3:   */ import java.util.Random;
/*  4:   */ import net.minecraft.world.World;
/*  5:   */ 
/*  6:   */ public class EntityEnchantmentTableParticleFX
/*  7:   */   extends EntityFX
/*  8:   */ {
/*  9:   */   private float field_70565_a;
/* 10:   */   private double field_70568_aq;
/* 11:   */   private double field_70567_ar;
/* 12:   */   private double field_70566_as;
/* 13:   */   private static final String __OBFID = "CL_00000902";
/* 14:   */   
/* 15:   */   public EntityEnchantmentTableParticleFX(World par1World, double par2, double par4, double par6, double par8, double par10, double par12)
/* 16:   */   {
/* 17:15 */     super(par1World, par2, par4, par6, par8, par10, par12);
/* 18:16 */     this.motionX = par8;
/* 19:17 */     this.motionY = par10;
/* 20:18 */     this.motionZ = par12;
/* 21:19 */     this.field_70568_aq = (this.posX = par2);
/* 22:20 */     this.field_70567_ar = (this.posY = par4);
/* 23:21 */     this.field_70566_as = (this.posZ = par6);
/* 24:22 */     float var14 = this.rand.nextFloat() * 0.6F + 0.4F;
/* 25:23 */     this.field_70565_a = (this.particleScale = this.rand.nextFloat() * 0.5F + 0.2F);
/* 26:24 */     this.particleRed = (this.particleGreen = this.particleBlue = 1.0F * var14);
/* 27:25 */     this.particleGreen *= 0.9F;
/* 28:26 */     this.particleRed *= 0.9F;
/* 29:27 */     this.particleMaxAge = ((int)(Math.random() * 10.0D) + 30);
/* 30:28 */     this.noClip = true;
/* 31:29 */     setParticleTextureIndex((int)(Math.random() * 26.0D + 1.0D + 224.0D));
/* 32:   */   }
/* 33:   */   
/* 34:   */   public int getBrightnessForRender(float par1)
/* 35:   */   {
/* 36:34 */     int var2 = super.getBrightnessForRender(par1);
/* 37:35 */     float var3 = this.particleAge / this.particleMaxAge;
/* 38:36 */     var3 *= var3;
/* 39:37 */     var3 *= var3;
/* 40:38 */     int var4 = var2 & 0xFF;
/* 41:39 */     int var5 = var2 >> 16 & 0xFF;
/* 42:40 */     var5 += (int)(var3 * 15.0F * 16.0F);
/* 43:42 */     if (var5 > 240) {
/* 44:44 */       var5 = 240;
/* 45:   */     }
/* 46:47 */     return var4 | var5 << 16;
/* 47:   */   }
/* 48:   */   
/* 49:   */   public float getBrightness(float par1)
/* 50:   */   {
/* 51:55 */     float var2 = super.getBrightness(par1);
/* 52:56 */     float var3 = this.particleAge / this.particleMaxAge;
/* 53:57 */     var3 *= var3;
/* 54:58 */     var3 *= var3;
/* 55:59 */     return var2 * (1.0F - var3) + var3;
/* 56:   */   }
/* 57:   */   
/* 58:   */   public void onUpdate()
/* 59:   */   {
/* 60:67 */     this.prevPosX = this.posX;
/* 61:68 */     this.prevPosY = this.posY;
/* 62:69 */     this.prevPosZ = this.posZ;
/* 63:70 */     float var1 = this.particleAge / this.particleMaxAge;
/* 64:71 */     var1 = 1.0F - var1;
/* 65:72 */     float var2 = 1.0F - var1;
/* 66:73 */     var2 *= var2;
/* 67:74 */     var2 *= var2;
/* 68:75 */     this.posX = (this.field_70568_aq + this.motionX * var1);
/* 69:76 */     this.posY = (this.field_70567_ar + this.motionY * var1 - var2 * 1.2F);
/* 70:77 */     this.posZ = (this.field_70566_as + this.motionZ * var1);
/* 71:79 */     if (this.particleAge++ >= this.particleMaxAge) {
/* 72:81 */       setDead();
/* 73:   */     }
/* 74:   */   }
/* 75:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.particle.EntityEnchantmentTableParticleFX
 * JD-Core Version:    0.7.0.1
 */