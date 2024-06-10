/*  1:   */ package net.minecraft.client.particle;
/*  2:   */ 
/*  3:   */ import net.minecraft.block.Block;
/*  4:   */ import net.minecraft.client.renderer.Tessellator;
/*  5:   */ import net.minecraft.init.Blocks;
/*  6:   */ import net.minecraft.util.IIcon;
/*  7:   */ import net.minecraft.world.World;
/*  8:   */ 
/*  9:   */ public class EntityDiggingFX
/* 10:   */   extends EntityFX
/* 11:   */ {
/* 12:   */   private Block field_145784_a;
/* 13:   */   private static final String __OBFID = "CL_00000932";
/* 14:   */   
/* 15:   */   public EntityDiggingFX(World par1World, double par2, double par4, double par6, double par8, double par10, double par12, Block par14Block, int par15)
/* 16:   */   {
/* 17:15 */     super(par1World, par2, par4, par6, par8, par10, par12);
/* 18:16 */     this.field_145784_a = par14Block;
/* 19:17 */     setParticleIcon(par14Block.getIcon(0, par15));
/* 20:18 */     this.particleGravity = par14Block.blockParticleGravity;
/* 21:19 */     this.particleRed = (this.particleGreen = this.particleBlue = 0.6F);
/* 22:20 */     this.particleScale /= 2.0F;
/* 23:   */   }
/* 24:   */   
/* 25:   */   public EntityDiggingFX applyColourMultiplier(int par1, int par2, int par3)
/* 26:   */   {
/* 27:28 */     if (this.field_145784_a == Blocks.grass) {
/* 28:30 */       return this;
/* 29:   */     }
/* 30:34 */     int var4 = this.field_145784_a.colorMultiplier(this.worldObj, par1, par2, par3);
/* 31:35 */     this.particleRed *= (var4 >> 16 & 0xFF) / 255.0F;
/* 32:36 */     this.particleGreen *= (var4 >> 8 & 0xFF) / 255.0F;
/* 33:37 */     this.particleBlue *= (var4 & 0xFF) / 255.0F;
/* 34:38 */     return this;
/* 35:   */   }
/* 36:   */   
/* 37:   */   public EntityDiggingFX applyRenderColor(int par1)
/* 38:   */   {
/* 39:47 */     if (this.field_145784_a == Blocks.grass) {
/* 40:49 */       return this;
/* 41:   */     }
/* 42:53 */     int var2 = this.field_145784_a.getRenderColor(par1);
/* 43:54 */     this.particleRed *= (var2 >> 16 & 0xFF) / 255.0F;
/* 44:55 */     this.particleGreen *= (var2 >> 8 & 0xFF) / 255.0F;
/* 45:56 */     this.particleBlue *= (var2 & 0xFF) / 255.0F;
/* 46:57 */     return this;
/* 47:   */   }
/* 48:   */   
/* 49:   */   public int getFXLayer()
/* 50:   */   {
/* 51:63 */     return 1;
/* 52:   */   }
/* 53:   */   
/* 54:   */   public void renderParticle(Tessellator par1Tessellator, float par2, float par3, float par4, float par5, float par6, float par7)
/* 55:   */   {
/* 56:68 */     float var8 = (this.particleTextureIndexX + this.particleTextureJitterX / 4.0F) / 16.0F;
/* 57:69 */     float var9 = var8 + 0.01560938F;
/* 58:70 */     float var10 = (this.particleTextureIndexY + this.particleTextureJitterY / 4.0F) / 16.0F;
/* 59:71 */     float var11 = var10 + 0.01560938F;
/* 60:72 */     float var12 = 0.1F * this.particleScale;
/* 61:74 */     if (this.particleIcon != null)
/* 62:   */     {
/* 63:76 */       var8 = this.particleIcon.getInterpolatedU(this.particleTextureJitterX / 4.0F * 16.0F);
/* 64:77 */       var9 = this.particleIcon.getInterpolatedU((this.particleTextureJitterX + 1.0F) / 4.0F * 16.0F);
/* 65:78 */       var10 = this.particleIcon.getInterpolatedV(this.particleTextureJitterY / 4.0F * 16.0F);
/* 66:79 */       var11 = this.particleIcon.getInterpolatedV((this.particleTextureJitterY + 1.0F) / 4.0F * 16.0F);
/* 67:   */     }
/* 68:82 */     float var13 = (float)(this.prevPosX + (this.posX - this.prevPosX) * par2 - interpPosX);
/* 69:83 */     float var14 = (float)(this.prevPosY + (this.posY - this.prevPosY) * par2 - interpPosY);
/* 70:84 */     float var15 = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * par2 - interpPosZ);
/* 71:85 */     par1Tessellator.setColorOpaque_F(this.particleRed, this.particleGreen, this.particleBlue);
/* 72:86 */     par1Tessellator.addVertexWithUV(var13 - par3 * var12 - par6 * var12, var14 - par4 * var12, var15 - par5 * var12 - par7 * var12, var8, var11);
/* 73:87 */     par1Tessellator.addVertexWithUV(var13 - par3 * var12 + par6 * var12, var14 + par4 * var12, var15 - par5 * var12 + par7 * var12, var8, var10);
/* 74:88 */     par1Tessellator.addVertexWithUV(var13 + par3 * var12 + par6 * var12, var14 + par4 * var12, var15 + par5 * var12 + par7 * var12, var9, var10);
/* 75:89 */     par1Tessellator.addVertexWithUV(var13 + par3 * var12 - par6 * var12, var14 - par4 * var12, var15 + par5 * var12 - par7 * var12, var9, var11);
/* 76:   */   }
/* 77:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.particle.EntityDiggingFX
 * JD-Core Version:    0.7.0.1
 */