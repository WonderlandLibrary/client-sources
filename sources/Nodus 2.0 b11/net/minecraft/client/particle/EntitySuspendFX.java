/*  1:   */ package net.minecraft.client.particle;
/*  2:   */ 
/*  3:   */ import java.util.Random;
/*  4:   */ import net.minecraft.block.Block;
/*  5:   */ import net.minecraft.block.material.Material;
/*  6:   */ import net.minecraft.util.MathHelper;
/*  7:   */ import net.minecraft.world.World;
/*  8:   */ 
/*  9:   */ public class EntitySuspendFX
/* 10:   */   extends EntityFX
/* 11:   */ {
/* 12:   */   private static final String __OBFID = "CL_00000928";
/* 13:   */   
/* 14:   */   public EntitySuspendFX(World par1World, double par2, double par4, double par6, double par8, double par10, double par12)
/* 15:   */   {
/* 16:13 */     super(par1World, par2, par4 - 0.125D, par6, par8, par10, par12);
/* 17:14 */     this.particleRed = 0.4F;
/* 18:15 */     this.particleGreen = 0.4F;
/* 19:16 */     this.particleBlue = 0.7F;
/* 20:17 */     setParticleTextureIndex(0);
/* 21:18 */     setSize(0.01F, 0.01F);
/* 22:19 */     this.particleScale *= (this.rand.nextFloat() * 0.6F + 0.2F);
/* 23:20 */     this.motionX = (par8 * 0.0D);
/* 24:21 */     this.motionY = (par10 * 0.0D);
/* 25:22 */     this.motionZ = (par12 * 0.0D);
/* 26:23 */     this.particleMaxAge = ((int)(16.0D / (Math.random() * 0.8D + 0.2D)));
/* 27:   */   }
/* 28:   */   
/* 29:   */   public void onUpdate()
/* 30:   */   {
/* 31:31 */     this.prevPosX = this.posX;
/* 32:32 */     this.prevPosY = this.posY;
/* 33:33 */     this.prevPosZ = this.posZ;
/* 34:34 */     moveEntity(this.motionX, this.motionY, this.motionZ);
/* 35:36 */     if (this.worldObj.getBlock(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ)).getMaterial() != Material.water) {
/* 36:38 */       setDead();
/* 37:   */     }
/* 38:41 */     if (this.particleMaxAge-- <= 0) {
/* 39:43 */       setDead();
/* 40:   */     }
/* 41:   */   }
/* 42:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.particle.EntitySuspendFX
 * JD-Core Version:    0.7.0.1
 */