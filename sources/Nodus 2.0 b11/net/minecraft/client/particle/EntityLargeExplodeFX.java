/*  1:   */ package net.minecraft.client.particle;
/*  2:   */ 
/*  3:   */ import java.util.Random;
/*  4:   */ import net.minecraft.client.renderer.RenderHelper;
/*  5:   */ import net.minecraft.client.renderer.Tessellator;
/*  6:   */ import net.minecraft.client.renderer.texture.TextureManager;
/*  7:   */ import net.minecraft.util.ResourceLocation;
/*  8:   */ import net.minecraft.world.World;
/*  9:   */ import org.lwjgl.opengl.GL11;
/* 10:   */ 
/* 11:   */ public class EntityLargeExplodeFX
/* 12:   */   extends EntityFX
/* 13:   */ {
/* 14:12 */   private static final ResourceLocation field_110127_a = new ResourceLocation("textures/entity/explosion.png");
/* 15:   */   private int field_70581_a;
/* 16:   */   private int field_70584_aq;
/* 17:   */   private TextureManager theRenderEngine;
/* 18:   */   private float field_70582_as;
/* 19:   */   private static final String __OBFID = "CL_00000910";
/* 20:   */   
/* 21:   */   public EntityLargeExplodeFX(TextureManager par1TextureManager, World par2World, double par3, double par5, double par7, double par9, double par11, double par13)
/* 22:   */   {
/* 23:23 */     super(par2World, par3, par5, par7, 0.0D, 0.0D, 0.0D);
/* 24:24 */     this.theRenderEngine = par1TextureManager;
/* 25:25 */     this.field_70584_aq = (6 + this.rand.nextInt(4));
/* 26:26 */     this.particleRed = (this.particleGreen = this.particleBlue = this.rand.nextFloat() * 0.6F + 0.4F);
/* 27:27 */     this.field_70582_as = (1.0F - (float)par9 * 0.5F);
/* 28:   */   }
/* 29:   */   
/* 30:   */   public void renderParticle(Tessellator par1Tessellator, float par2, float par3, float par4, float par5, float par6, float par7)
/* 31:   */   {
/* 32:32 */     int var8 = (int)((this.field_70581_a + par2) * 15.0F / this.field_70584_aq);
/* 33:34 */     if (var8 <= 15)
/* 34:   */     {
/* 35:36 */       this.theRenderEngine.bindTexture(field_110127_a);
/* 36:37 */       float var9 = var8 % 4 / 4.0F;
/* 37:38 */       float var10 = var9 + 0.24975F;
/* 38:39 */       float var11 = var8 / 4 / 4.0F;
/* 39:40 */       float var12 = var11 + 0.24975F;
/* 40:41 */       float var13 = 2.0F * this.field_70582_as;
/* 41:42 */       float var14 = (float)(this.prevPosX + (this.posX - this.prevPosX) * par2 - interpPosX);
/* 42:43 */       float var15 = (float)(this.prevPosY + (this.posY - this.prevPosY) * par2 - interpPosY);
/* 43:44 */       float var16 = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * par2 - interpPosZ);
/* 44:45 */       GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 45:46 */       GL11.glDisable(2896);
/* 46:47 */       RenderHelper.disableStandardItemLighting();
/* 47:48 */       par1Tessellator.startDrawingQuads();
/* 48:49 */       par1Tessellator.setColorRGBA_F(this.particleRed, this.particleGreen, this.particleBlue, 1.0F);
/* 49:50 */       par1Tessellator.setNormal(0.0F, 1.0F, 0.0F);
/* 50:51 */       par1Tessellator.setBrightness(240);
/* 51:52 */       par1Tessellator.addVertexWithUV(var14 - par3 * var13 - par6 * var13, var15 - par4 * var13, var16 - par5 * var13 - par7 * var13, var10, var12);
/* 52:53 */       par1Tessellator.addVertexWithUV(var14 - par3 * var13 + par6 * var13, var15 + par4 * var13, var16 - par5 * var13 + par7 * var13, var10, var11);
/* 53:54 */       par1Tessellator.addVertexWithUV(var14 + par3 * var13 + par6 * var13, var15 + par4 * var13, var16 + par5 * var13 + par7 * var13, var9, var11);
/* 54:55 */       par1Tessellator.addVertexWithUV(var14 + par3 * var13 - par6 * var13, var15 - par4 * var13, var16 + par5 * var13 - par7 * var13, var9, var12);
/* 55:56 */       par1Tessellator.draw();
/* 56:57 */       GL11.glPolygonOffset(0.0F, 0.0F);
/* 57:58 */       GL11.glEnable(2896);
/* 58:   */     }
/* 59:   */   }
/* 60:   */   
/* 61:   */   public int getBrightnessForRender(float par1)
/* 62:   */   {
/* 63:64 */     return 61680;
/* 64:   */   }
/* 65:   */   
/* 66:   */   public void onUpdate()
/* 67:   */   {
/* 68:72 */     this.prevPosX = this.posX;
/* 69:73 */     this.prevPosY = this.posY;
/* 70:74 */     this.prevPosZ = this.posZ;
/* 71:75 */     this.field_70581_a += 1;
/* 72:77 */     if (this.field_70581_a == this.field_70584_aq) {
/* 73:79 */       setDead();
/* 74:   */     }
/* 75:   */   }
/* 76:   */   
/* 77:   */   public int getFXLayer()
/* 78:   */   {
/* 79:85 */     return 3;
/* 80:   */   }
/* 81:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.particle.EntityLargeExplodeFX
 * JD-Core Version:    0.7.0.1
 */