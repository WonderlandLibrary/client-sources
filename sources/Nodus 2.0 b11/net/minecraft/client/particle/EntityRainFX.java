/*  1:   */ package net.minecraft.client.particle;
/*  2:   */ 
/*  3:   */ import java.util.Random;
/*  4:   */ import net.minecraft.block.Block;
/*  5:   */ import net.minecraft.block.BlockLiquid;
/*  6:   */ import net.minecraft.block.material.Material;
/*  7:   */ import net.minecraft.util.MathHelper;
/*  8:   */ import net.minecraft.world.World;
/*  9:   */ 
/* 10:   */ public class EntityRainFX
/* 11:   */   extends EntityFX
/* 12:   */ {
/* 13:   */   private static final String __OBFID = "CL_00000934";
/* 14:   */   
/* 15:   */   public EntityRainFX(World par1World, double par2, double par4, double par6)
/* 16:   */   {
/* 17:14 */     super(par1World, par2, par4, par6, 0.0D, 0.0D, 0.0D);
/* 18:15 */     this.motionX *= 0.300000011920929D;
/* 19:16 */     this.motionY = ((float)Math.random() * 0.2F + 0.1F);
/* 20:17 */     this.motionZ *= 0.300000011920929D;
/* 21:18 */     this.particleRed = 1.0F;
/* 22:19 */     this.particleGreen = 1.0F;
/* 23:20 */     this.particleBlue = 1.0F;
/* 24:21 */     setParticleTextureIndex(19 + this.rand.nextInt(4));
/* 25:22 */     setSize(0.01F, 0.01F);
/* 26:23 */     this.particleGravity = 0.06F;
/* 27:24 */     this.particleMaxAge = ((int)(8.0D / (Math.random() * 0.8D + 0.2D)));
/* 28:   */   }
/* 29:   */   
/* 30:   */   public void onUpdate()
/* 31:   */   {
/* 32:32 */     this.prevPosX = this.posX;
/* 33:33 */     this.prevPosY = this.posY;
/* 34:34 */     this.prevPosZ = this.posZ;
/* 35:35 */     this.motionY -= this.particleGravity;
/* 36:36 */     moveEntity(this.motionX, this.motionY, this.motionZ);
/* 37:37 */     this.motionX *= 0.9800000190734863D;
/* 38:38 */     this.motionY *= 0.9800000190734863D;
/* 39:39 */     this.motionZ *= 0.9800000190734863D;
/* 40:41 */     if (this.particleMaxAge-- <= 0) {
/* 41:43 */       setDead();
/* 42:   */     }
/* 43:46 */     if (this.onGround)
/* 44:   */     {
/* 45:48 */       if (Math.random() < 0.5D) {
/* 46:50 */         setDead();
/* 47:   */       }
/* 48:53 */       this.motionX *= 0.699999988079071D;
/* 49:54 */       this.motionZ *= 0.699999988079071D;
/* 50:   */     }
/* 51:57 */     Material var1 = this.worldObj.getBlock(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ)).getMaterial();
/* 52:59 */     if ((var1.isLiquid()) || (var1.isSolid()))
/* 53:   */     {
/* 54:61 */       double var2 = MathHelper.floor_double(this.posY) + 1 - BlockLiquid.func_149801_b(this.worldObj.getBlockMetadata(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ)));
/* 55:63 */       if (this.posY < var2) {
/* 56:65 */         setDead();
/* 57:   */       }
/* 58:   */     }
/* 59:   */   }
/* 60:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.particle.EntityRainFX
 * JD-Core Version:    0.7.0.1
 */