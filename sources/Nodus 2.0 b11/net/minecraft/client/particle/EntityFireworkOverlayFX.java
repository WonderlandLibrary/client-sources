/*  1:   */ package net.minecraft.client.particle;
/*  2:   */ 
/*  3:   */ import net.minecraft.client.renderer.Tessellator;
/*  4:   */ import net.minecraft.util.MathHelper;
/*  5:   */ import net.minecraft.world.World;
/*  6:   */ 
/*  7:   */ public class EntityFireworkOverlayFX
/*  8:   */   extends EntityFX
/*  9:   */ {
/* 10:   */   private static final String __OBFID = "CL_00000904";
/* 11:   */   
/* 12:   */   protected EntityFireworkOverlayFX(World par1World, double par2, double par4, double par6)
/* 13:   */   {
/* 14:13 */     super(par1World, par2, par4, par6);
/* 15:14 */     this.particleMaxAge = 4;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public void renderParticle(Tessellator par1Tessellator, float par2, float par3, float par4, float par5, float par6, float par7)
/* 19:   */   {
/* 20:19 */     float var8 = 0.25F;
/* 21:20 */     float var9 = var8 + 0.25F;
/* 22:21 */     float var10 = 0.125F;
/* 23:22 */     float var11 = var10 + 0.25F;
/* 24:23 */     float var12 = 7.1F * MathHelper.sin((this.particleAge + par2 - 1.0F) * 0.25F * 3.141593F);
/* 25:24 */     this.particleAlpha = (0.6F - (this.particleAge + par2 - 1.0F) * 0.25F * 0.5F);
/* 26:25 */     float var13 = (float)(this.prevPosX + (this.posX - this.prevPosX) * par2 - interpPosX);
/* 27:26 */     float var14 = (float)(this.prevPosY + (this.posY - this.prevPosY) * par2 - interpPosY);
/* 28:27 */     float var15 = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * par2 - interpPosZ);
/* 29:28 */     par1Tessellator.setColorRGBA_F(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha);
/* 30:29 */     par1Tessellator.addVertexWithUV(var13 - par3 * var12 - par6 * var12, var14 - par4 * var12, var15 - par5 * var12 - par7 * var12, var9, var11);
/* 31:30 */     par1Tessellator.addVertexWithUV(var13 - par3 * var12 + par6 * var12, var14 + par4 * var12, var15 - par5 * var12 + par7 * var12, var9, var10);
/* 32:31 */     par1Tessellator.addVertexWithUV(var13 + par3 * var12 + par6 * var12, var14 + par4 * var12, var15 + par5 * var12 + par7 * var12, var8, var10);
/* 33:32 */     par1Tessellator.addVertexWithUV(var13 + par3 * var12 - par6 * var12, var14 - par4 * var12, var15 + par5 * var12 - par7 * var12, var8, var11);
/* 34:   */   }
/* 35:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.particle.EntityFireworkOverlayFX
 * JD-Core Version:    0.7.0.1
 */