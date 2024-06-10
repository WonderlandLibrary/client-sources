/*  1:   */ package net.minecraft.client.particle;
/*  2:   */ 
/*  3:   */ import net.minecraft.block.Block;
/*  4:   */ import net.minecraft.client.renderer.Tessellator;
/*  5:   */ import net.minecraft.init.Blocks;
/*  6:   */ import net.minecraft.item.Item;
/*  7:   */ import net.minecraft.util.IIcon;
/*  8:   */ import net.minecraft.world.World;
/*  9:   */ 
/* 10:   */ public class EntityBreakingFX
/* 11:   */   extends EntityFX
/* 12:   */ {
/* 13:   */   private static final String __OBFID = "CL_00000897";
/* 14:   */   
/* 15:   */   public EntityBreakingFX(World par1World, double par2, double par4, double par6, Item par8Item)
/* 16:   */   {
/* 17:14 */     this(par1World, par2, par4, par6, par8Item, 0);
/* 18:   */   }
/* 19:   */   
/* 20:   */   public EntityBreakingFX(World par1World, double par2, double par4, double par6, Item par8Item, int par9)
/* 21:   */   {
/* 22:19 */     super(par1World, par2, par4, par6, 0.0D, 0.0D, 0.0D);
/* 23:20 */     setParticleIcon(par8Item.getIconFromDamage(par9));
/* 24:21 */     this.particleRed = (this.particleGreen = this.particleBlue = 1.0F);
/* 25:22 */     this.particleGravity = Blocks.snow.blockParticleGravity;
/* 26:23 */     this.particleScale /= 2.0F;
/* 27:   */   }
/* 28:   */   
/* 29:   */   public EntityBreakingFX(World par1World, double par2, double par4, double par6, double par8, double par10, double par12, Item par14Item, int par15)
/* 30:   */   {
/* 31:28 */     this(par1World, par2, par4, par6, par14Item, par15);
/* 32:29 */     this.motionX *= 0.1000000014901161D;
/* 33:30 */     this.motionY *= 0.1000000014901161D;
/* 34:31 */     this.motionZ *= 0.1000000014901161D;
/* 35:32 */     this.motionX += par8;
/* 36:33 */     this.motionY += par10;
/* 37:34 */     this.motionZ += par12;
/* 38:   */   }
/* 39:   */   
/* 40:   */   public int getFXLayer()
/* 41:   */   {
/* 42:39 */     return 2;
/* 43:   */   }
/* 44:   */   
/* 45:   */   public void renderParticle(Tessellator par1Tessellator, float par2, float par3, float par4, float par5, float par6, float par7)
/* 46:   */   {
/* 47:44 */     float var8 = (this.particleTextureIndexX + this.particleTextureJitterX / 4.0F) / 16.0F;
/* 48:45 */     float var9 = var8 + 0.01560938F;
/* 49:46 */     float var10 = (this.particleTextureIndexY + this.particleTextureJitterY / 4.0F) / 16.0F;
/* 50:47 */     float var11 = var10 + 0.01560938F;
/* 51:48 */     float var12 = 0.1F * this.particleScale;
/* 52:50 */     if (this.particleIcon != null)
/* 53:   */     {
/* 54:52 */       var8 = this.particleIcon.getInterpolatedU(this.particleTextureJitterX / 4.0F * 16.0F);
/* 55:53 */       var9 = this.particleIcon.getInterpolatedU((this.particleTextureJitterX + 1.0F) / 4.0F * 16.0F);
/* 56:54 */       var10 = this.particleIcon.getInterpolatedV(this.particleTextureJitterY / 4.0F * 16.0F);
/* 57:55 */       var11 = this.particleIcon.getInterpolatedV((this.particleTextureJitterY + 1.0F) / 4.0F * 16.0F);
/* 58:   */     }
/* 59:58 */     float var13 = (float)(this.prevPosX + (this.posX - this.prevPosX) * par2 - interpPosX);
/* 60:59 */     float var14 = (float)(this.prevPosY + (this.posY - this.prevPosY) * par2 - interpPosY);
/* 61:60 */     float var15 = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * par2 - interpPosZ);
/* 62:61 */     par1Tessellator.setColorOpaque_F(this.particleRed, this.particleGreen, this.particleBlue);
/* 63:62 */     par1Tessellator.addVertexWithUV(var13 - par3 * var12 - par6 * var12, var14 - par4 * var12, var15 - par5 * var12 - par7 * var12, var8, var11);
/* 64:63 */     par1Tessellator.addVertexWithUV(var13 - par3 * var12 + par6 * var12, var14 + par4 * var12, var15 - par5 * var12 + par7 * var12, var8, var10);
/* 65:64 */     par1Tessellator.addVertexWithUV(var13 + par3 * var12 + par6 * var12, var14 + par4 * var12, var15 + par5 * var12 + par7 * var12, var9, var10);
/* 66:65 */     par1Tessellator.addVertexWithUV(var13 + par3 * var12 - par6 * var12, var14 - par4 * var12, var15 + par5 * var12 - par7 * var12, var9, var11);
/* 67:   */   }
/* 68:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.particle.EntityBreakingFX
 * JD-Core Version:    0.7.0.1
 */