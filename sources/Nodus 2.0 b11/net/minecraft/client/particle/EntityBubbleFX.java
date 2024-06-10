/*  1:   */ package net.minecraft.client.particle;
/*  2:   */ 
/*  3:   */ import java.util.Random;
/*  4:   */ import net.minecraft.block.Block;
/*  5:   */ import net.minecraft.block.material.Material;
/*  6:   */ import net.minecraft.util.MathHelper;
/*  7:   */ import net.minecraft.world.World;
/*  8:   */ 
/*  9:   */ public class EntityBubbleFX
/* 10:   */   extends EntityFX
/* 11:   */ {
/* 12:   */   private static final String __OBFID = "CL_00000898";
/* 13:   */   
/* 14:   */   public EntityBubbleFX(World par1World, double par2, double par4, double par6, double par8, double par10, double par12)
/* 15:   */   {
/* 16:13 */     super(par1World, par2, par4, par6, par8, par10, par12);
/* 17:14 */     this.particleRed = 1.0F;
/* 18:15 */     this.particleGreen = 1.0F;
/* 19:16 */     this.particleBlue = 1.0F;
/* 20:17 */     setParticleTextureIndex(32);
/* 21:18 */     setSize(0.02F, 0.02F);
/* 22:19 */     this.particleScale *= (this.rand.nextFloat() * 0.6F + 0.2F);
/* 23:20 */     this.motionX = (par8 * 0.2000000029802322D + (float)(Math.random() * 2.0D - 1.0D) * 0.02F);
/* 24:21 */     this.motionY = (par10 * 0.2000000029802322D + (float)(Math.random() * 2.0D - 1.0D) * 0.02F);
/* 25:22 */     this.motionZ = (par12 * 0.2000000029802322D + (float)(Math.random() * 2.0D - 1.0D) * 0.02F);
/* 26:23 */     this.particleMaxAge = ((int)(8.0D / (Math.random() * 0.8D + 0.2D)));
/* 27:   */   }
/* 28:   */   
/* 29:   */   public void onUpdate()
/* 30:   */   {
/* 31:31 */     this.prevPosX = this.posX;
/* 32:32 */     this.prevPosY = this.posY;
/* 33:33 */     this.prevPosZ = this.posZ;
/* 34:34 */     this.motionY += 0.002D;
/* 35:35 */     moveEntity(this.motionX, this.motionY, this.motionZ);
/* 36:36 */     this.motionX *= 0.8500000238418579D;
/* 37:37 */     this.motionY *= 0.8500000238418579D;
/* 38:38 */     this.motionZ *= 0.8500000238418579D;
/* 39:40 */     if (this.worldObj.getBlock(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ)).getMaterial() != Material.water) {
/* 40:42 */       setDead();
/* 41:   */     }
/* 42:45 */     if (this.particleMaxAge-- <= 0) {
/* 43:47 */       setDead();
/* 44:   */     }
/* 45:   */   }
/* 46:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.particle.EntityBubbleFX
 * JD-Core Version:    0.7.0.1
 */